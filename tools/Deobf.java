import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

/**
 * SRG -> Mojang-official deobfuscator for decompiled Forge 1.20.1 source.
 *
 *   joined.tsrg  "tsrg2 obf srg id" : <obfClass> <srgClass>   /   <obfName> <desc(obf)> <srgName> <id>
 *   client.txt   Mojang mappings    : <officialClass> -> <obfClass>  /  <type> <officialName>(params) -> <obfName>
 *
 * Chain built:  srg member name  <-  (obfClass, obfName, desc)  ->  official member name
 * Then every \b[mf]_\d+_?\b identifier in the decompiled sources is rewritten to the official name.
 */
public class Deobf {

    static final Map<String, String> obfToOfficialClass = new HashMap<>();  // "enn" -> "net/minecraft/client/Minecraft"
    static final Map<String, String> officialToObfClass = new HashMap<>();  // inverse
    /** "obfClass.obfName#desc(obf)" -> official method name */
    static final Map<String, String> methodByKey = new HashMap<>();
    /** "obfClass.obfName" -> official method name, kept only when that class has exactly ONE such method */
    static final Map<String, String> methodByNameUnique = new HashMap<>();
    static final Map<String, Integer> methodObfNameCount = new HashMap<>();
    /** "obfClass.obfName" -> official field name (field obf names are unique per class) */
    static final Map<String, String> fieldByName = new HashMap<>();
    /** srg name (m_12345_ / f_12345_) -> official name */
    static final Map<String, String> srgToOfficial = new HashMap<>();

    public static void main(String[] args) throws Exception {
        Path tsrg = Paths.get(args[0]);
        Path mojang = Paths.get(args[1]);
        Path srcRoot = Paths.get(args[2]);
        boolean dryRun = args.length > 3 && args[3].equals("-dry");

        pass1Classes(mojang);
        System.out.println("mojang classes: obf->official = " + obfToOfficialClass.size());
        pass2Members(mojang);
        System.out.println("mojang: methods=" + methodByKey.size() + " unambiguous_names=" + methodByNameUnique.size()
                + " fields=" + fieldByName.size());
        loadTsrg(tsrg);
        System.out.println("srg->official resolved = " + srgToOfficial.size());

        int files = 0, touched = 0, renamed = 0, unresolvedRefs = 0;
        Map<String, Integer> unresolved = new TreeMap<>();
        List<Path> java = new ArrayList<>();
        try (var w = Files.walk(srcRoot)) { w.filter(p -> p.toString().endsWith(".java")).forEach(java::add); }

        for (Path p : java) {
            String s = Files.readString(p, StandardCharsets.UTF_8);
            Matcher m = Pattern.compile("\\b([mf]_\\d+_?)\\b").matcher(s);
            StringBuilder out = new StringBuilder();
            int last = 0, local = 0;
            while (m.find()) {
                out.append(s, last, m.start());
                String srg = m.group(1);
                String off = srgToOfficial.get(srg);
                if (off == null) {
                    unresolved.merge(srg, 1, Integer::sum);
                    unresolvedRefs++;
                    out.append(srg);
                } else if (!off.matches("[A-Za-z_$][A-Za-z0-9_$]*") || Keyword.is(off) || off.equals(srg)) {
                    out.append(srg);
                } else {
                    out.append(off);
                    local++;
                    renamed++;
                }
                last = m.end();
            }
            out.append(s.substring(last));
            files++;
            if (local > 0) {
                touched++;
                if (!dryRun) Files.writeString(p, out.toString(), StandardCharsets.UTF_8);
            }
        }

        System.out.println("--- SOURCE REWRITE ---");
        System.out.println("java_files=" + files + " modified=" + touched);
        System.out.println("identifiers_renamed=" + renamed + " srg_refs_without_mapping=" + unresolvedRefs);
        System.out.println("distinct_unresolved=" + unresolved.size());
        unresolved.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed()).limit(12)
                .forEach(e -> System.out.println("   " + e.getValue() + "  " + e.getKey()));
    }

    // ---------------- client.txt ----------------

    static void pass1Classes(Path p) throws Exception {
        for (String line : Files.readAllLines(p, StandardCharsets.UTF_8)) {
            if (line.isBlank() || line.startsWith("#") || line.startsWith("    ")) continue;
            int arrow = line.lastIndexOf(" -> ");
            if (arrow < 0) continue;
            String official = line.substring(0, arrow).trim().replace('.', '/');
            String obf = line.substring(arrow + 4).trim();
            if (obf.endsWith(":")) obf = obf.substring(0, obf.length() - 1);
            // client.txt writes some obf classes dotted (com.mojang.blaze3d...$b); tsrg writes them internal.
            obf = obf.replace('.', '/');
            if (obf.isEmpty() || official.isEmpty()) continue;
            obfToOfficialClass.put(obf, official);
            officialToObfClass.put(official, obf);
        }
    }

    static void pass2Members(Path p) throws Exception {
        String obfClass = null;
        for (String line : Files.readAllLines(p, StandardCharsets.UTF_8)) {
            if (line.isBlank() || line.startsWith("#")) continue;
            if (!line.startsWith("    ")) {
                int arrow = line.lastIndexOf(" -> ");
                obfClass = arrow < 0 ? null : line.substring(arrow + 4).trim().replaceAll(":$", "").replace('.', '/');
                continue;
            }
            if (obfClass == null) continue;
            String t = line.trim();
            int arrow = t.lastIndexOf(" -> ");
            if (arrow < 0) continue;
            String obfName = t.substring(arrow + 4).trim();
            String decl = t.substring(0, arrow).trim();
            int c1 = decl.indexOf(':');
            if (c1 >= 0) {                                  // "12:18:type name(...) -> a"
                int c2 = decl.indexOf(':', c1 + 1);
                if (c2 > 0) decl = decl.substring(c2 + 1).trim();
            }
            int lp = decl.indexOf('(');
            if (lp >= 0) {                                  // method
                int rp = decl.lastIndexOf(')');
                String before = decl.substring(0, lp).trim();
                String params = rp > lp ? decl.substring(lp + 1, rp).trim() : "";
                int sp = before.lastIndexOf(' ');
                String name = sp >= 0 ? before.substring(sp + 1).trim() : before;
                String ret = sp >= 0 ? before.substring(0, sp).trim() : "void";
                String desc = desc(ret, params);
                String kn = obfClass + "." + obfName;
                methodByKey.put(kn + "#" + desc, name);
                int seen = methodObfNameCount.merge(kn, 1, Integer::sum);
                if (seen == 1) methodByNameUnique.put(kn, name);
                else methodByNameUnique.remove(kn);          // overloaded: ambiguous, never guess
            } else {                                        // field: "type name -> a"  (may carry modifiers)
                String[] tk = decl.split("\\s+");
                if (tk.length < 2) continue;
                String name = tk[tk.length - 1];
                String type = String.join(" ", Arrays.copyOf(tk, tk.length - 1));
                fieldByName.put(obfClass + "." + obfName, name);
            }
        }
    }

    static String desc(String type, String params) {
        StringBuilder sb = new StringBuilder("(");
        String pr = params.trim();
        if (!pr.isEmpty()) for (String q : splitParams(pr)) sb.append(toDesc(q));
        sb.append(')').append(toDesc(type));
        return sb.toString();
    }

    static String[] splitParams(String s) {
        List<String> out = new ArrayList<>();
        int depth = 0, start = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '<') depth++;
            else if (c == '>') depth--;
            else if (c == ',' && depth == 0) { out.add(s.substring(start, i).trim()); start = i + 1; }
        }
        out.add(s.substring(start).trim());
        return out.toArray(new String[0]);
    }

    static String toDesc(String t) {
        t = t.trim();
        int g = t.indexOf('<');
        if (g >= 0) t = t.substring(0, g).trim();
        int arr = 0;
        while (t.endsWith("[]")) { arr++; t = t.substring(0, t.length() - 2).trim(); }
        String prim = PRIM.get(t);
        String base;
        if (prim != null) {
            base = prim;
        } else {
            String off = t.replace('.', '/');
            String obf = officialToObfClass.get(off);
            base = "L" + (obf != null ? obf : off) + ";";
        }
        return "[".repeat(arr) + base;
    }

    static final Map<String, String> PRIM = Map.of(
            "void", "V", "boolean", "Z", "byte", "B", "char", "C", "short", "S",
            "int", "I", "long", "J", "float", "F", "double", "D");

    // ---------------- joined.tsrg ----------------

    static final Pattern P_FIELD = Pattern.compile("^(\\S+)\\s+(f_\\d+_?)\\s+(\\d+)$");
    static final Pattern P_METHOD = Pattern.compile("^(\\S+)\\s+(\\S+)\\s+(m_\\d+_?)\\s+(\\d+)$");

    static void loadTsrg(Path p) throws Exception {
        String obfClass = null;
        int fieldLines = 0, methodLines = 0, resolvedM = 0, resolvedF = 0;
        for (String line : Files.readAllLines(p, StandardCharsets.UTF_8)) {
            if (line.isBlank() || line.startsWith("tsrg2")) continue;
            if (!line.startsWith("\t")) {
                String[] tk = line.trim().split("\\s+");
                obfClass = tk.length >= 2 ? tk[0] : null;
                continue;
            }
            if (obfClass == null) continue;
            String t = line.trim();
            if (t.startsWith("static") || t.startsWith("final") || t.startsWith("abstract")) continue;
            Matcher mm = P_METHOD.matcher(t);
            if (mm.matches()) {
                methodLines++;
                String obfName = mm.group(1), mdesc = mm.group(2), srg = mm.group(3);
                String off = methodByKey.get(obfClass + "." + obfName + "#" + mdesc);
                if (off == null) off = methodByNameUnique.get(obfClass + "." + obfName);
                if (off != null) { srgToOfficial.put(srg, off); resolvedM++; }
                continue;
            }
            Matcher fm = P_FIELD.matcher(t);
            if (fm.matches()) {
                fieldLines++;
                String obfName = fm.group(1), srg = fm.group(2);
                String off = fieldByName.get(obfClass + "." + obfName);
                if (off != null) { srgToOfficial.put(srg, off); resolvedF++; }
            }
        }
        System.out.println("tsrg: method_lines=" + methodLines + " resolved=" + resolvedM
                + "  field_lines=" + fieldLines + " resolved=" + resolvedF);
    }

    static final class Keyword {
        static final Set<String> K = Set.of("new", "class", "int", "long", "float", "double", "boolean", "byte",
                "char", "short", "void", "return", "if", "else", "for", "while", "do", "switch", "case", "default",
                "break", "continue", "try", "catch", "finally", "throw", "throws", "static", "final", "public",
                "private", "protected", "abstract", "synchronized", "interface", "extends", "implements", "this",
                "super", "null", "true", "false", "package", "import", "enum", "record", "var", "instanceof", "assert",
                "native", "transient", "volatile", "strictfp", "const", "goto");
        static boolean is(String s) { return K.contains(s); }
    }
}
