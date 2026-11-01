import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

/**
 * Class-file constant-pool + attribute probe.
 * Per class: CP tag histogram, InvokeDynamic/Dynamic site counts, plain String constants,
 * Code-attribute byte totals, and every BootstrapMethods target (class.name(desc)).
 */
public class CpProbe {

    record E(int tag, Object val) {}

    static final class Parsed {
        int major;
        int indy;
        int methodHandle;
        int condy;
        int strings;
        int codeBytes;
        int methods;
        final List<String> bsmTargets = new ArrayList<>();
    }

    public static void main(String[] args) throws Exception {
        Path root = Paths.get(args[0]);
        String label = args.length > 1 ? args[1] : root.toString();
        List<Path> files = new ArrayList<>();
        try (var w = Files.walk(root)) { w.filter(p -> p.toString().endsWith(".class")).forEach(files::add); }
        Collections.sort(files);

        Parsed agg = new Parsed();
        int classes = 0, failed = 0, withIndy = 0;
        Set<Integer> majors = new TreeSet<>();
        Map<String, Integer> bsmHistogram = new TreeMap<>();
        Map<String, Integer> tagHistogram = new TreeMap<>();

        for (Path p : files) {
            Parsed r;
            try { r = parse(Files.readAllBytes(p)); }
            catch (Exception e) { failed++; if (failed <= 5) System.out.println("PARSE_FAIL " + root.relativize(p) + " : " + e); continue; }
            classes++;
            majors.add(r.major);
            agg.indy += r.indy; agg.condy += r.condy; agg.strings += r.strings; agg.codeBytes += r.codeBytes; agg.methods += r.methods; agg.methodHandle += r.methodHandle;
            if (r.indy > 0) withIndy++;
            for (String b : r.bsmTargets) bsmHistogram.merge(b, 1, Integer::sum);
        }

        System.out.println("=== CP PROBE: " + label + " ===");
        System.out.printf("class_files=%d failed=%d%n", classes, failed);
        System.out.println("class_versions=" + majors + "  (52=Java8 55=Java11 61=Java17)");
        System.out.println("classes_with_invokedynamic=" + withIndy);
        System.out.println("invokedynamic_sites=" + agg.indy);
        System.out.println("methodhandle_cp_entries=" + agg.methodHandle);
        System.out.println("constantdynamic_sites=" + agg.condy);
        System.out.println("plain_string_constants=" + agg.strings);
        System.out.println("methods=" + agg.methods + "  code_bytes=" + agg.codeBytes);
        System.out.println("bootstrap_method_targets (" + bsmHistogram.size() + " distinct, ordered by count):");
        bsmHistogram.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed()).limit(15)
                .forEach(e -> System.out.printf("   %6d  %s%n", e.getValue(), e.getKey()));
        System.out.println();
    }

    static class Holder {
        static String s(E[] cp, int i) {
            return (i > 0 && i < cp.length && cp[i] != null && cp[i].tag() == 1) ? (String) cp[i].val() : "<#" + i + ">";
        }
    }

    static String memberRef(E[] cp, int idx) {
        if (idx <= 0 || idx >= cp.length || cp[idx] == null) return "?";
        E e = cp[idx];
        if (e.tag() < 9 || e.tag() > 11) return "?tag" + e.tag();
        int[] nt = (int[]) e.val();
        String cls = Holder.s(cp, cp[nt[0]] == null ? 0 : (int) cp[nt[0]].val());
        int[] nd = (int[]) cp[nt[1]].val();
        return cls.replace('/', '.') + "." + Holder.s(cp, nd[0]) + Holder.s(cp, nd[1]);
    }

    static Parsed parse(byte[] bytes) throws IOException {
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(bytes));
        Parsed r = new Parsed();
        if (in.readInt() != 0xCAFEBABE) throw new IOException("bad magic");
        in.readUnsignedShort();
        r.major = in.readUnsignedShort();
        int cpCount = in.readUnsignedShort();
        E[] cp = new E[cpCount];
        for (int i = 1; i < cpCount; i++) {
            int t = in.readUnsignedByte();
            switch (t) {
                case 1 -> { int l = in.readUnsignedShort(); byte[] d = new byte[l]; in.readFully(d); cp[i] = new E(1, new String(d, StandardCharsets.UTF_8)); }
                case 7, 8, 16, 19, 20 -> cp[i] = new E(t, in.readUnsignedShort());
                case 3, 4 -> cp[i] = new E(t, in.readInt());
                case 12 -> cp[i] = new E(12, new int[]{in.readUnsignedShort(), in.readUnsignedShort()});
                case 15 -> { cp[i] = new E(15, new int[]{in.readUnsignedByte(), in.readUnsignedShort()}); r.methodHandle++; }   // MethodHandle: u1 kind + u2 ref
                case 17 -> { cp[i] = new E(17, new int[]{in.readUnsignedShort(), in.readUnsignedShort()}); r.condy++; }
                case 5, 6 -> { cp[i] = new E(t, in.readLong()); i++; }
                case 9, 10, 11 -> cp[i] = new E(t, new int[]{in.readUnsignedShort(), in.readUnsignedShort()});
                case 18 -> { cp[i] = new E(18, new int[]{in.readUnsignedShort(), in.readUnsignedShort()}); r.indy++; }              // InvokeDynamic: u2 bsm + u2 NT
                default -> throw new IOException("unknown CP tag " + t + " at #" + i);
            }
        }
        in.readUnsignedShort();          // access_flags
        in.readUnsignedShort();          // this_class
        in.readUnsignedShort();          // super_class
        int ifc = in.readUnsignedShort();
        for (int i = 0; i < ifc; i++) in.readUnsignedShort();
        int fc = in.readUnsignedShort();
        for (int i = 0; i < fc; i++) { in.readUnsignedShort(); in.readUnsignedShort(); in.readUnsignedShort(); attrs(in, cp, r); }
        int mc = in.readUnsignedShort();
        for (int i = 0; i < mc; i++) { in.readUnsignedShort(); in.readUnsignedShort(); in.readUnsignedShort(); r.methods++; attrs(in, cp, r); }
        int ac = in.readUnsignedShort();
        attrsN(in, cp, r, ac);
        for (int i = 1; i < cpCount; i++) { E e = cp[i]; if (e != null && e.tag() == 8) r.strings++; }
        return r;
    }

    static void attrs(DataInputStream in, E[] cp, Parsed r) throws IOException { attrsN(in, cp, r, in.readUnsignedShort()); }

    static void attrsN(DataInputStream in, E[] cp, Parsed r, int n) throws IOException {
        for (int i = 0; i < n; i++) {
            int ni = in.readUnsignedShort();
            int len = in.readInt();
            byte[] d = new byte[len];
            in.readFully(d);
            String nm = Holder.s(cp, ni);
            if ("Code".equals(nm)) {
                DataInputStream c = new DataInputStream(new ByteArrayInputStream(d));
                c.readUnsignedShort(); c.readUnsignedShort();
                r.codeBytes += c.readInt();
            } else if ("BootstrapMethods".equals(nm)) {
                DataInputStream b = new DataInputStream(new ByteArrayInputStream(d));
                int nb = b.readUnsignedShort();
                for (int j = 0; j < nb; j++) {
                    r.bsmTargets.add(memberRef(cp, b.readUnsignedShort()));
                    int na = b.readUnsignedShort();
                    for (int k = 0; k < na; k++) {
                        int t = b.readUnsignedByte();
                        switch (t) {
                            case 1, 7, 8, 16, 19, 20 -> b.readUnsignedShort();
                            case 3, 4, 12 -> b.readInt();
                            case 5 -> { b.readInt(); b.readInt(); }
                            case 6 -> b.readLong();
                            case 15, 17 -> { b.readUnsignedShort(); b.readUnsignedShort(); }
                            case 9, 10, 11, 18 -> { b.readUnsignedShort(); b.readUnsignedShort(); }
                            default -> { return; }
                        }
                    }
                }
            }
        }
    }
}
