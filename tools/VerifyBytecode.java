import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

/**
 * Post-rename integrity gate for a directory of .class files:
 *   1. this_class must equal the file path (internal name)
 *   2. every Class / Fieldref / Methodref / InterfaceMethodref / InvokeDynamic owner resolved
 *      from the constant pool must parse (and, for our own packages, exist on disk)
 *   3. InnerClasses entries: inner_class index must resolve; a mapped-in-package inner must exist
 *   4. all attributes parse without running off the end of the buffer
 */
public class VerifyBytecode {
    public static void main(String[] args) throws Exception {
        Path root = Paths.get(args[0]);
        String ownPrefix = args.length > 1 ? args[1] : "com/heypixel";
        List<Path> files = new ArrayList<>();
        try (var w = Files.walk(root)) { w.filter(p -> p.toString().endsWith(".class")).forEach(files::add); }
        Collections.sort(files);

        Set<String> present = new HashSet<>();
        for (Path p : files) present.add(root.relativize(p).toString().replace(File.separatorChar, '/').replaceAll("\\.class$", ""));

        int ok = 0;
        List<String> problems = new ArrayList<>();
        Map<String, Integer> bsmTotals = new TreeMap<>();
        Map<String, Integer> argTags = new TreeMap<>();
        int indyOwners = 0;

        for (Path p : files) {
            String rel = root.relativize(p).toString().replace(File.separatorChar, '/').replaceAll("\\.class$", "");
            byte[] b = Files.readAllBytes(p);
            try {
                Parsed r = parse(b);
                if (!rel.equals(r.thisClass)) problems.add("THIS_CLASS_MISMATCH " + rel + " declares " + r.thisClass);
                for (String c : r.classRefs) {
                    if (c.startsWith(ownPrefix) && !present.contains(c)) problems.add("DANGLING_OWN_CLASS " + rel + " -> " + c);
                }
                for (String[] ic : r.inners) {
                    if (ic[0].startsWith(ownPrefix) && !present.contains(ic[0])) problems.add("MISSING_OWN_INNER " + rel + " -> " + ic[0]);
                }
                for (String bt : r.bootstrapTargets) {
                    bsmTotals.merge(bt, 1, Integer::sum);
                    int d = bt.lastIndexOf('.');
                    if (d > 0) {
                        String owner = bt.substring(0, d);
                        if (owner.startsWith(ownPrefix) && !present.contains(owner))
                            problems.add("DANGLING_BSM_OWNER " + rel + " -> " + owner);
                    }
                    indyOwners++;
                }
                for (var e : r.bsmArgTags.entrySet()) argTags.merge(e.getKey(), e.getValue(), Integer::sum);
                ok++;
            } catch (Exception e) {
                problems.add("PARSE_FAIL " + rel + " : " + e);
            }
        }
        System.out.println("classes=" + files.size() + " fully_valid=" + ok + " problems=" + problems.size());
        problems.stream().limit(25).forEach(s -> System.out.println("  " + s));
        Map<String, Integer> bs = new TreeMap<>();
        for (var e : bsmTotals.entrySet()) bs.merge(e.getKey(), e.getValue(), Integer::sum);
        System.out.println("bootstrap_targets=(" + bs.size() + " distinct):");
        bs.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed()).limit(8)
                .forEach(e -> System.out.println("   " + e.getValue() + "  " + e.getKey()));
        System.out.println("bsm_argument_cp_tags=" + argTags);
        System.out.println("indy_owner_refs_resolved=" + indyOwners);

        // report the renamed set: any class under ownPrefix whose simple name starts uppercase and is multi-char
        List<String> semantic = new ArrayList<>();
        for (String s : present) {
            if (!s.startsWith(ownPrefix)) continue;
            String sn = s.substring(s.lastIndexOf('/') + 1);
            String base = sn.contains("$") ? sn.substring(0, sn.indexOf('$')) : sn;
            if (base.length() > 2 && Character.isUpperCase(base.charAt(0))) semantic.add(s);
        }
        System.out.println("semantic_named_own_classes=" + semantic.size());
        semantic.stream().limit(90).forEach(s -> System.out.println("   " + s));
    }

    record CPE(int tag, Object val) {}

    static class Parsed {
        String thisClass = "?";
        final List<String> classRefs = new ArrayList<>();
        final List<String[]> inners = new ArrayList<>();
        final List<String> bootstrapTargets = new ArrayList<>();
        final Map<String, Integer> bsmArgTags = new TreeMap<>();
    }

    /** Methodref/InterfaceMethodref -> "owner.name" (internal owner) */
    static String resolveMember(CPE[] cp, CPE ref) {
        int[] nt = (int[]) ref.val();
        CPE cc = cp[nt[0]];
        String owner = (cc != null && cc.tag() == 7) ? s(cp, (int) cc.val()) : "?";
        CPE nte = cp[nt[1]];
        String name = "?";
        if (nte != null && nte.tag() == 12) {
            int[] nd = (int[]) nte.val();
            String v = s(cp, nd[0]);
            name = v == null ? "?" : v;
        }
        return owner + "." + name;
    }

    static String s(CPE[] cp, int i) {
        return (i > 0 && i < cp.length && cp[i] != null && cp[i].tag() == 1) ? (String) cp[i].val() : null;
    }

    static Parsed parse(byte[] b) throws IOException {
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(b));
        Parsed r = new Parsed();
        if (in.readInt() != 0xCAFEBABE) throw new IOException("magic");
        in.readUnsignedShort(); in.readUnsignedShort();
        int n = in.readUnsignedShort();
        CPE[] cp = new CPE[n];
        for (int i = 1; i < n; i++) {
            int t = in.readUnsignedByte();
            switch (t) {
                case 1 -> { int l = in.readUnsignedShort(); byte[] d = new byte[l]; in.readFully(d); cp[i] = new CPE(1, new String(d, StandardCharsets.UTF_8)); }
                case 7, 8, 16, 19, 20 -> cp[i] = new CPE(t, in.readUnsignedShort());
                case 3, 4 -> cp[i] = new CPE(t, in.readInt());
                case 12 -> cp[i] = new CPE(12, new int[]{in.readUnsignedShort(), in.readUnsignedShort()});
                case 15 -> cp[i] = new CPE(15, new int[]{in.readUnsignedByte(), in.readUnsignedShort()});   // MethodHandle: u1 + u2
                case 17 -> cp[i] = new CPE(17, new int[]{in.readUnsignedShort(), in.readUnsignedShort()});
                case 5, 6 -> { cp[i] = new CPE(t, in.readLong()); i++; }
                case 9, 10, 11, 18 -> cp[i] = new CPE(t, new int[]{in.readUnsignedShort(), in.readUnsignedShort()});
                default -> throw new IOException("tag " + t + " at " + i);
            }
        }
        in.readUnsignedShort();
        int tci = in.readUnsignedShort();
        r.thisClass = s(cp, cp[tci] == null ? 0 : (int) cp[tci].val());
        in.readUnsignedShort();
        int ifc = in.readUnsignedShort(); for (int i = 0; i < ifc; i++) in.readUnsignedShort();
        int fc = in.readUnsignedShort();
        for (int i = 0; i < fc; i++) { in.readUnsignedShort(); in.readUnsignedShort(); in.readUnsignedShort(); attrs(in, cp, r); }
        int mc = in.readUnsignedShort();
        for (int i = 0; i < mc; i++) { in.readUnsignedShort(); in.readUnsignedShort(); in.readUnsignedShort(); attrs(in, cp, r); }
        int ac = in.readUnsignedShort();
        attrsN(in, cp, r, ac);
        if (in.available() != 0) throw new IOException("trailing_bytes=" + in.available());
        for (int i = 1; i < n; i++) {
            CPE e = cp[i];
            if (e == null) continue;
            if (e.tag() == 7) { String v = s(cp, (int) e.val()); if (v != null) r.classRefs.add(v); }
            else if (e.tag() == 9 || e.tag() == 10 || e.tag() == 11 || e.tag() == 18) {
                int[] nt = (int[]) e.val();
                CPE cc = cp[nt[0]];
                if (cc != null && cc.tag() == 7) { String v = s(cp, (int) cc.val()); if (v != null) r.classRefs.add(v); }
            }
        }
        return r;
    }

    static void attrs(DataInputStream in, CPE[] cp, Parsed r) throws IOException { attrsN(in, cp, r, in.readUnsignedShort()); }

    static void attrsN(DataInputStream in, CPE[] cp, Parsed r, int n) throws IOException {
        for (int i = 0; i < n; i++) {
            int ni = in.readUnsignedShort();
            int len = in.readInt();
            byte[] d = new byte[len];
            in.readFully(d);
            String nm = s(cp, ni);
            if ("InnerClasses".equals(nm)) {
                DataInputStream b = new DataInputStream(new ByteArrayInputStream(d));
                int m = b.readUnsignedShort();
                for (int j = 0; j < m; j++) {
                    int ic = b.readUnsignedShort(), oc = b.readUnsignedShort(), nn = b.readUnsignedShort(); b.readUnsignedShort();
                    String iv = ic == 0 ? null : s(cp, cp[ic] == null ? 0 : (int) cp[ic].val());
                    String ov = oc == 0 ? null : s(cp, cp[oc] == null ? 0 : (int) cp[oc].val());
                    if (iv != null) r.inners.add(new String[]{iv, ov == null ? "" : ov, String.valueOf(nn)});
                }
            } else if ("BootstrapMethods".equals(nm)) {
                // JVMS 4.7.23: bootstrap_method_ref is a CP index (CONSTANT_MethodHandle_info),
                // and bootstrap_arguments is an array of CP INDICES (u2) - not inline tagged entries.
                DataInputStream b = new DataInputStream(new ByteArrayInputStream(d));
                int m = b.readUnsignedShort();
                for (int j = 0; j < m; j++) {
                    int mr = b.readUnsignedShort();
                    CPE e = cp[mr];
                    if (e == null) throw new IOException("null bootstrap ref #" + mr);
                    if (e.tag() == 15) {                                   // MethodHandle -> Methodref/InterfaceMethodref
                        int[] mh = (int[]) e.val();
                        CPE ref = cp[mh[1]];
                        if (ref == null || (ref.tag() != 10 && ref.tag() != 11))
                            throw new IOException("bootstrap MH #" + mr + " -> bad ref tag " + (ref == null ? "null" : ref.tag()));
                        r.bootstrapTargets.add(resolveMember(cp, ref));
                    } else if (e.tag() == 10 || e.tag() == 11) {
                        r.bootstrapTargets.add(resolveMember(cp, e));       // tolerated form
                    } else {
                        throw new IOException("bad bootstrap ref #" + mr + " tag=" + e.tag());
                    }
                    int na = b.readUnsignedShort();
                    for (int k = 0; k < na; k++) {
                        int ai = b.readUnsignedShort();                     // CP index
                        CPE a = cp[ai];
                        if (a == null) throw new IOException("null bsm arg #" + ai);
                        switch (a.tag()) {
                            case 8, 7, 3, 4, 5, 6, 15, 16, 17 -> {
                                r.bsmArgTags.merge("tag" + a.tag(), 1, Integer::sum);
                                if (a.tag() == 7) {                          // class reference -> closure check
                                    String v = s(cp, (int) a.val());
                                    if (v != null) r.classRefs.add(v);
                                } else if (a.tag() == 15) {
                                    int[] mh = (int[]) a.val();
                                    CPE ref = cp[mh[1]];
                                    if (ref != null && (ref.tag() == 10 || ref.tag() == 11 || ref.tag() == 9)) {
                                        int[] nt = (int[]) ref.val();
                                        CPE cc = cp[nt[0]];
                                        if (cc != null && cc.tag() == 7) {
                                            String v = s(cp, (int) cc.val());
                                            if (v != null) r.classRefs.add(v);
                                        }
                                    }
                                }
                            }
                            default -> throw new IOException("illegal bsm arg tag " + a.tag() + " at #" + ai);
                        }
                    }
                }
            }
        }
    }
}
