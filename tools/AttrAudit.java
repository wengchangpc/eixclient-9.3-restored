import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

/** Final attribute audit: which debug/metadata survived the obfuscator's strip pass. */
public class AttrAudit {
    public static void main(String[] a) throws Exception {
        Path root = Paths.get(a[0]);
        List<Path> fs = new ArrayList<>();
        try (var w = Files.walk(root)) { w.filter(p -> p.toString().endsWith(".class")).forEach(fs::add); }
        Collections.sort(fs);
        int n = 0, srcFile = 0, lvt = 0, mparams = 0, lineNumbers = 0, locals = 0, inner = 0, annotations = 0, sig = 0;
        Map<String, Integer> sourceFileValues = new TreeMap<>();
        for (Path p : fs) {
            Map<String, Object> r;
            try { r = one(Files.readAllBytes(p)); }
            catch (Exception e) { System.out.println("FAIL " + p + " " + e); continue; }
            n++;
            if (Boolean.TRUE.equals(r.get("SourceFile"))) { srcFile++; sourceFileValues.merge(String.valueOf(r.get("sf")), 1, Integer::sum); }
            if (Boolean.TRUE.equals(r.get("LocalVariableTable"))) lvt++;
            if (Boolean.TRUE.equals(r.get("MethodParameters"))) mparams++;
            if (Boolean.TRUE.equals(r.get("LineNumberTable"))) lineNumbers++;
            if (Boolean.TRUE.equals(r.get("LocalVariableTypeTable"))) locals++;
            if (Boolean.TRUE.equals(r.get("InnerClasses"))) inner++;
            if (Boolean.TRUE.equals(r.get("RuntimeVisibleAnnotations"))) annotations++;
            if (Boolean.TRUE.equals(r.get("Signature"))) sig++;
        }
        System.out.println("classes=" + n);
        System.out.println("with_SourceFile=" + srcFile + "  distinct values=" + sourceFileValues);
        System.out.println("with_LocalVariableTable=" + lvt + "  (=> recoverable local/param names)");
        System.out.println("with_MethodParameters=" + mparams);
        System.out.println("with_LineNumberTable=" + lineNumbers);
        System.out.println("with_LocalVariableTypeTable=" + locals);
        System.out.println("with_InnerClasses=" + inner);
        System.out.println("with_RuntimeVisibleAnnotations=" + annotations);
        System.out.println("with_Signature(any)=" + sig);
    }

    static Map<String, Object> one(byte[] b) throws IOException {
        Map<String, Object> out = new HashMap<>();
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(b));
        if (in.readInt() != 0xCAFEBABE) throw new IOException("magic");
        in.readUnsignedShort(); in.readUnsignedShort();
        int n = in.readUnsignedShort();
        String[] utf = new String[n];
        for (int i = 1; i < n; i++) {
            int t = in.readUnsignedByte();
            switch (t) {
                case 1 -> { int l = in.readUnsignedShort(); byte[] d = new byte[l]; in.readFully(d); utf[i] = new String(d, StandardCharsets.UTF_8); }
                case 7, 8, 16, 19, 20 -> in.readUnsignedShort();
                case 3, 4 -> in.readInt();
                case 12 -> { in.readUnsignedShort(); in.readUnsignedShort(); }
                case 15 -> { in.readUnsignedByte(); in.readUnsignedShort(); }
                case 17, 18 -> { in.readUnsignedShort(); in.readUnsignedShort(); }
                case 5, 6 -> { in.readLong(); i++; }
                case 9, 10, 11 -> { in.readUnsignedShort(); in.readUnsignedShort(); }
                default -> throw new IOException("tag " + t);
            }
        }
        in.readUnsignedShort(); in.readUnsignedShort(); in.readUnsignedShort();
        int ifc = in.readUnsignedShort();
        for (int i = 0; i < ifc; i++) in.readUnsignedShort();
        // fields and methods share the layout: access, name_index, descriptor_index, attributes
        for (int section = 0; section < 2; section++) {
            int cnt = in.readUnsignedShort();
            for (int i = 0; i < cnt; i++) {
                in.readUnsignedShort(); in.readUnsignedShort(); in.readUnsignedShort();
                readAttrs(in, utf, n, out);
            }
        }
        // class attributes: name_index + length + info (no access/name/descriptor prefix)
        readAttrs(in, utf, n, out);
        return out;
    }

    static void readAttrs(DataInputStream in, String[] utf, int n, Map<String, Object> out) throws IOException {
        int an = in.readUnsignedShort();
        for (int j = 0; j < an; j++) {
            int ni = in.readUnsignedShort();
            int len = in.readInt();
            byte[] d = new byte[len];
            in.readFully(d);
            String nm = (ni > 0 && ni < n && utf[ni] != null) ? utf[ni] : "";
            out.put(nm, true);
            if ("SourceFile".equals(nm) && len == 2) {
                int si = ((d[0] & 0xFF) << 8) | (d[1] & 0xFF);
                out.put("sf", (si > 0 && si < n && utf[si] != null) ? utf[si] : "?");
            }
        }
    }
}
