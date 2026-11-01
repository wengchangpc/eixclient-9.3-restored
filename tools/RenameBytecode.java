import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

/**
 * Bytecode-level class renamer (no external deps).
 *
 * Only CLASS names are renamed, driven by an explicit map of full internal names, so there is
 * never ambiguity between same-simple-name classes in different packages (which is what makes
 * Vineflower's own IIdentifierRenamer unusable here - toBeRenamed() only receives the simple name).
 *
 * Rewrites the constant pool only:
 *   - Utf8 entries whose exact value is a mapped internal name (this_class, Class, NameAndType owner...)
 *   - Utf8 descriptors / generic signatures embedding "L<old>;" or "L<old>."
 * Everything else (including untouched Utf8 bytes) is copied verbatim, so unmodified classes
 * round-trip byte-for-byte. Class files store modified UTF-8, so changed values are re-encoded
 * with DataOutputStream.writeUTF (the JDK's own modified UTF-8), never java.nio UTF-8.
 *
 * Every output file is re-parsed as a safety gate.
 */
public class RenameBytecode {

    static final class CPE {
        final int tag;
        final Object val;     // Integer / Long / int[] / Utf8{byte[] raw, String text}
        final byte[] raw;
        CPE(int tag, Object val) { this(tag, val, null); }
        CPE(int tag, Object val, byte[] raw) { this.tag = tag; this.val = val; this.raw = raw; }
        String text() { return (String) val; }
        int i() { return (Integer) val; }
        int[] a() { return (int[]) val; }
    }

    static Map<String, String> map = new LinkedHashMap<>();      // old internal -> new internal (simple part only applied per package)
    static final Set<String> existing = new HashSet<>();
    static final Set<String> KEYWORDS = Set.of("abstract", "assert", "boolean", "break", "byte", "case", "catch",
            "char", "class", "const", "continue", "default", "do", "double", "else", "enum", "extends", "final",
            "finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int", "interface",
            "long", "native", "new", "package", "private", "protected", "public", "return", "short", "static",
            "strictfp", "super", "switch", "synchronized", "this", "throw", "throws", "transient", "try", "void",
            "volatile", "while", "record", "var", "yield");

    public static void main(String[] args) throws Exception {
        Path in = Paths.get(args[0]);
        Path out = Paths.get(args[1]);
        Path mapFile = Paths.get(args[2]);

        List<Path> files = new ArrayList<>();
        try (var w = Files.walk(in)) { w.filter(p -> p.toString().endsWith(".class")).forEach(files::add); }
        Collections.sort(files);
        for (Path p : files) existing.add(relToInternal(in, p));

        int skippedMissing = 0, skippedBad = 0, skippedClash = 0;
        for (String line : Files.readAllLines(mapFile, StandardCharsets.UTF_8)) {
            if (line.isBlank()) continue;
            String[] f = line.split("\t");
            if (f.length < 2) continue;
            String oldInt = f[0].trim(), nn = f[1].trim();
            if (!existing.contains(oldInt)) { skippedMissing++; continue; }
            if (KEYWORDS.contains(nn) || !nn.matches("[A-Za-z_$][A-Za-z0-9_$]*")) { skippedBad++; continue; }
            String pkg = oldInt.substring(0, oldInt.lastIndexOf('/') + 1);
            String newInt = pkg + nn;
            if (newInt.equals(oldInt)) continue;
            if (existing.contains(newInt) || map.containsValue(newInt)) { skippedClash++; continue; }
            map.put(oldInt, newInt);
        }
        System.out.println("active_renames=" + map.size()
                + " skipped(absent=" + skippedMissing + ",bad_ident=" + skippedBad + ",clash=" + skippedClash + ")");

        // inner classes of a renamed class follow their outer
        Map<String, String> full = new LinkedHashMap<>(map);
        for (String e : new ArrayList<>(existing)) {
            int d = e.lastIndexOf('$');
            if (d < 0) continue;
            String nn = map.get(e.substring(0, d));
            if (nn != null) full.put(e, nn + e.substring(d));
        }
        System.out.println("with_inner_classes=" + full.size());

        // longest-first so "…/render/a" cannot be matched inside "…/render/aa"
        List<String> keys = new ArrayList<>(full.keySet());
        keys.sort(Comparator.comparingInt(String::length).reversed());

        int written = 0, renamedFiles = 0, failed = 0, cpHits = 0;
        for (Path p : files) {
            byte[] b = Files.readAllBytes(p);
            byte[] nb;
            try {
                ClassReader cr = new ClassReader(b);
                int hits = cr.applyNames(full, keys);
                cpHits += hits;
                nb = cr.serialize();
            } catch (Exception e) {
                failed++;
                System.out.println("REWRITE_FAIL " + in.relativize(p) + " " + e);
                continue;
            }
            String self = relToInternal(in, p);
            String target = full.getOrDefault(self, self);
            Path dest = out.resolve(target + ".class");
            Files.createDirectories(dest.getParent());
            Files.write(dest, nb);
            written++;
            if (!target.equals(self)) renamedFiles++;
            try { new ClassReader(nb).read(); }
            catch (Exception e) { failed++; System.out.println("PARSE_BACK_FAIL " + in.relativize(p) + " " + e); }
        }
        System.out.println("files_written=" + written + " renamed_classes=" + renamedFiles
                + " cp_entries_changed=" + cpHits + " failures=" + failed);
    }

    static String relToInternal(Path root, Path p) {
        return root.relativize(p).toString().replace(java.io.File.separatorChar, '/').replaceAll("\\.class$", "");
    }

    /**
     * Rewrite one Utf8 value. Keys are pre-sorted longest-first; matched spans are replaced
     * with placeholders that cannot collide with any class name, so a fresh replacement can
     * never be re-matched by a shorter key.
     */
    static String sub(String s, Map<String, String> full, List<String> keys) {
        if (s.indexOf('/') < 0) return s;                     // fast path: member names, primitives
        String exact = full.get(s);
        if (exact != null) return exact;                      // this_class / Class-info form
        String work = s;
        List<String> reps = new ArrayList<>();
        for (String o : keys) {
            String n = full.get(o);
            for (String pat : new String[]{"L" + o + ";", "L" + o + "."}) {
                int idx;
                while ((idx = work.indexOf(pat)) >= 0) {
                    // pat begins with 'L' and ends with ';' or '.', so the replacement must
                    // re-emit the 'L' we consumed: "L" + newName + tail
                    reps.add("L" + n + pat.substring(o.length() + 1));
                    work = work.substring(0, idx) + "\u0000" + (reps.size() - 1) + "\u0000"
                            + work.substring(idx + pat.length());
                }
            }
        }
        if (reps.isEmpty()) return s;
        for (int i = 0; i < reps.size(); i++) work = work.replace("\u0000" + i + "\u0000", reps.get(i));
        return work;
    }

    static class ClassReader {
        byte[] src;
        CPE[] cp;
        int cpCount;
        int cpEnd;

        ClassReader() {}
        ClassReader(byte[] b) { this.src = b; read(); }

        void read() {
            try {
                DataInputStream in = new DataInputStream(new ByteArrayInputStream(src));
                if (in.readInt() != 0xCAFEBABE) throw new IOException("magic");
                in.readUnsignedShort(); in.readUnsignedShort();
                cpCount = in.readUnsignedShort();
                cp = new CPE[cpCount];
                for (int i = 1; i < cpCount; i++) {
                    int t = in.readUnsignedByte();
                    switch (t) {
                        case 1 -> {
                            int l = in.readUnsignedShort();
                            byte[] d = new byte[l];
                            in.readFully(d);
                            cp[i] = new CPE(1, decodeUtf8(d), d);
                        }
                        case 7, 8, 16, 19, 20 -> cp[i] = new CPE(t, in.readUnsignedShort());
                        case 3, 4 -> cp[i] = new CPE(t, in.readInt());
                        case 12 -> cp[i] = new CPE(12, new int[]{in.readUnsignedShort(), in.readUnsignedShort()});
                        case 15 -> cp[i] = new CPE(15, new int[]{in.readUnsignedByte(), in.readUnsignedShort()});   // MethodHandle: u1 kind + u2 ref
                        case 18 -> cp[i] = new CPE(18, new int[]{in.readUnsignedShort(), in.readUnsignedShort()});  // InvokeDynamic: u2 bsm + u2 NT
                        case 17 -> cp[i] = new CPE(17, new int[]{in.readUnsignedShort(), in.readUnsignedShort()});
                        case 5, 6 -> { cp[i] = new CPE(t, in.readLong()); i++; }
                        case 9, 10, 11 -> cp[i] = new CPE(t, new int[]{in.readUnsignedShort(), in.readUnsignedShort()});
                        default -> throw new IOException("tag " + t + " at " + i);
                    }
                }
                cpEnd = src.length - in.available();
            } catch (IOException e) { throw new UncheckedIOException(e); }
        }

        int applyNames(Map<String, String> full, List<String> keys) {
            int hits = 0;
            for (int i = 1; i < cpCount; i++) {
                CPE e = cp[i];
                if (e == null || e.tag != 1) continue;
                String r = sub(e.text(), full, keys);
                if (!r.equals(e.text())) { cp[i] = new CPE(1, r, null); hits++; }
            }
            return hits;
        }

        byte[] serialize() throws IOException {
            ByteArrayOutputStream bo = new ByteArrayOutputStream(src.length + 512);
            DataOutputStream d = new DataOutputStream(bo);
            DataInputStream head = new DataInputStream(new ByteArrayInputStream(src));
            d.writeInt(head.readInt());
            d.writeShort(head.readUnsignedShort());
            d.writeShort(head.readUnsignedShort());
            d.writeShort(cpCount);
            for (int i = 1; i < cpCount; i++) {
                CPE e = cp[i];
                if (e == null) continue;                      // phantom slot after Long/Double
                d.writeByte(e.tag);
                switch (e.tag) {
                    case 1 -> {
                        if (e.raw != null) { d.writeShort(e.raw.length); d.write(e.raw); }   // untouched: verbatim
                        else { byte[] b = encodeUtf8(e.text()); d.writeShort(b.length); d.write(b); }
                    }
                    case 7, 8, 16, 19, 20 -> d.writeShort(e.i());
                    case 3, 4 -> d.writeInt(e.i());
                    case 12, 17, 9, 10, 11, 18 -> { d.writeShort(e.a()[0]); d.writeShort(e.a()[1]); }
                    case 15 -> { d.writeByte(e.a()[0]); d.writeShort(e.a()[1]); }              // MethodHandle: u1 + u2
                    case 5, 6 -> { d.writeLong((Long) e.val); i++; }
                    default -> throw new IOException("tag " + e.tag + " at " + i);
                }
            }
            DataInputStream rest = new DataInputStream(new ByteArrayInputStream(src));
            rest.skipNBytes(cpEnd);
            rest.transferTo(d);
            d.flush();
            return bo.toByteArray();
        }
    }

    /** JDK's own modified UTF-8 (DataInput.readUTF) - handles U+0000 and 3-byte forms like class files */
    static String decodeUtf8(byte[] raw) throws IOException {
        ByteArrayOutputStream pre = new ByteArrayOutputStream(raw.length + 2);
        DataOutputStream po = new DataOutputStream(pre);
        po.writeShort(raw.length);
        po.write(raw);
        return new DataInputStream(new ByteArrayInputStream(pre.toByteArray())).readUTF();
    }

    static byte[] encodeUtf8(String s) throws IOException {
        ByteArrayOutputStream bo = new ByteArrayOutputStream(s.length() + 2);
        new DataOutputStream(bo).writeUTF(s);
        byte[] all = bo.toByteArray();
        return Arrays.copyOfRange(all, 2, all.length);       // drop the 2-byte length we re-add on write
    }
}
