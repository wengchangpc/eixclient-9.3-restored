import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

/**
 * Extract @ModuleInfo / @CommandInfo metadata (attribute order independent) and emit a
 * rename map:  <internalClassOldName> \t <proposedCamelCaseName> \t <kind> \t <displayName> \t <extra>
 */
public class MetaExtract2 {
    public static void main(String[] args) throws Exception {
        Path root = Paths.get(args[0]);
        Path out = Paths.get(args[1]);
        List<Path> files = new ArrayList<>();
        try (var w = Files.walk(root)) { w.filter(p -> p.toString().endsWith(".java")).forEach(files::add); }
        Collections.sort(files);

        List<String[]> rows = new ArrayList<>();
        Map<String, Integer> used = new TreeMap<>();
        int mods = 0, cmds = 0;

        for (Path p : files) {
            String t = Files.readString(p, StandardCharsets.UTF_8);
            String pkg = valueAfter(t, "package ");
            if (pkg == null) continue;
            pkg = pkg.trim().substring(0, pkg.indexOf(';')).trim();
            String cls = topLevelName(t, pkg);
            if (cls == null) continue;
            String internal = pkg.replace('.', '/') + "/" + cls;

            String block = annotationBlock(t, "@ModuleInfo");
            if (block != null) {
                mods++;
                String en = strAttr(block, "a");
                String cn = strAttr(block, "b");
                String desc = strAttr(block, "c");
                String cat = constAttr(block, "d");
                String cand = toIdent(en != null ? en : cn, cls);
                String fin = uniquify(cand, internal, used);
                rows.add(new String[]{internal, fin, "MODULE", nz(en), nz(cn), nz(cat), nz(desc)});
            }
            block = annotationBlock(t, "@CommandInfo");
            if (block != null) {
                cmds++;
                String name = strAttr(block, "a");
                String desc = strAttr(block, "b");
                List<String> alias = arrAttr(block, "c");
                String cand = toIdent(name, cls);
                String fin = uniquify(cand, internal, used);
                rows.add(new String[]{internal, fin, "COMMAND", nz(name), "", String.join(",", alias), nz(desc)});
            }
        }

        List<String> lines = new ArrayList<>();
        for (String[] r : rows) lines.add(String.join("\t", r));
        Files.write(out, lines, StandardCharsets.UTF_8);

        System.out.println("ModuleInfo=" + mods + " CommandInfo=" + cmds + " mapped_classes=" + rows.size());
        System.out.println("name_collisions=" + collisions);
        System.out.println("sample:");
        rows.stream().limit(14).forEach(r -> System.out.println("   " + r[0] + "  ->  " + r[1] + "   [" + r[2] + " " + r[3] + "/" + r[4] + " cat=" + r[5] + "]"));
    }

    static int collisions = 0;

    static String uniquify(String cand, String internal, Map<String, Integer> used) {
        if (!used.containsKey(cand)) { used.put(cand, 1); return cand; }
        int n = used.merge(cand, 1, Integer::sum);
        String alt = cand + "_" + n;
        while (used.containsKey(alt)) alt = alt + "x";
        used.put(alt, 1);
        collisions++;
        System.out.println("COLLISION " + internal + ": " + cand + " -> " + alt);
        return alt;
    }

    static String toIdent(String display, String fallback) {
        String s = display == null || display.isBlank() ? fallback : display;
        StringBuilder sb = new StringBuilder();
        boolean up = true;
        for (char c : s.toCharArray()) {
            if (Character.isLetterOrDigit(c) && c < 128) { sb.append(up ? Character.toUpperCase(c) : c); up = false; }
            else if (c == ' ' || c == '-' || c == '_' || c == '.') { up = true; }
            // drop non-ascii / symbols
        }
        String r = sb.toString();
        if (r.isEmpty()) r = fallback;
        if (Character.isDigit(r.charAt(0))) r = "_" + r;
        return r;
    }

    static String topLevelName(String t, String pkg) {
        String marker = "\npublic ";
        int i = t.indexOf(marker);
        // robust: find the class/interface/enum decl at depth 0 whose name matches the file
        java.util.regex.Matcher m = java.util.regex.Pattern.compile(
                "(?m)^(?:public\\s+)?(?:final\\s+)?(?:abstract\\s+)?(?:strictfp\\s+)?(?:class|interface|enum|record)\\s+([A-Za-z_$][\\w$]*)").matcher(t);
        if (m.find()) return m.group(1);
        return null;
    }

    static String valueAfter(String t, String kw) {
        int i = t.indexOf(kw);
        return i < 0 ? null : t.substring(i + kw.length());
    }

    /** return the balanced body of an annotation, e.g. text inside @ModuleInfo( ... ) */
    static String annotationBlock(String t, String ann) {
        int i = t.indexOf(ann);
        if (i < 0) return null;
        int j = t.indexOf('(', i);
        if (j < 0) return null;
        int depth = 0;
        boolean inStr = false;
        for (int k = j; k < t.length(); k++) {
            char c = t.charAt(k);
            if (c == '"' && (k == 0 || t.charAt(k - 1) != '\\')) inStr = !inStr;
            if (inStr) continue;
            if (c == '(') depth++;
            else if (c == ')') { depth--; if (depth == 0) return t.substring(j + 1, k); }
        }
        return null;
    }

    static String strAttr(String block, String key) {
        java.util.regex.Matcher m = java.util.regex.Pattern.compile(
                "(?:^|[,{\\s])" + key + "\\s*=\\s*\"((?:[^\"\\\\]|\\\\.)*)\"").matcher(block);
        return m.find() ? unescape(m.group(1)) : null;
    }

    static String constAttr(String block, String key) {
        java.util.regex.Matcher m = java.util.regex.Pattern.compile(
                "(?:^|[,{\\s])" + key + "\\s*=\\s*([\\w.$]+)").matcher(block);
        if (!m.find()) return null;
        String v = m.group(1);
        return v.contains(".") ? v.substring(v.lastIndexOf('.') + 1) : v;
    }

    static List<String> arrAttr(String block, String key) {
        java.util.regex.Matcher m = java.util.regex.Pattern.compile(
                "(?:^|[,{\\s])" + key + "\\s*=\\s*\\{([^}]*)\\}").matcher(block);
        List<String> out = new ArrayList<>();
        if (!m.find()) return out;
        for (String s : m.group(1).split(",")) {
            s = s.trim();
            if (s.startsWith("\"") && s.endsWith("\"") && s.length() > 1) out.add(unescape(s.substring(1, s.length() - 1)));
        }
        return out;
    }

    static String unescape(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '\\' && i + 1 < s.length()) {
                char n = s.charAt(++i);
                sb.append(switch (n) { case 'n' -> '\n'; case 't' -> '\t'; case 'r' -> '\r'; case '"' -> '"'; case '\\' -> '\\'; case 'u' -> {
                    if (i + 4 < s.length()) { yield String.valueOf((char) Integer.parseInt(s.substring(i + 1, i + 5), 16)); }
                    yield 'u'; } default -> n; });
                if (n == 'u') i += 4;
            } else sb.append(c);
        }
        return sb.toString();
    }

    static String nz(String s) { return s == null ? "" : s.replace('\t', ' ').replace('\n', ' '); }
}
