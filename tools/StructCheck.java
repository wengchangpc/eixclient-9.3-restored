import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

/**
 * Structural analysis of a decompiled source tree, independent of external dependencies.
 * Reports the two javac-rejectable self-issues that obfuscation creates:
 *   1. package / class name clashes  (JLS 7.1)
 *   2. type or member names that are Java keywords
 * Also: for keyword-named members, whether any OTHER file references them (rename safety).
 */
public class StructCheck {
    static final Set<String> KW = Set.of("abstract", "assert", "boolean", "break", "byte", "case", "catch", "char",
            "class", "const", "continue", "default", "do", "double", "else", "enum", "extends", "final", "finally",
            "float", "for", "goto", "if", "implements", "import", "instanceof", "int", "interface", "long", "native",
            "new", "package", "private", "protected", "public", "return", "short", "static", "strictfp", "super",
            "switch", "synchronized", "this", "throw", "throws", "transient", "try", "void", "volatile", "while");

    public static void main(String[] args) throws Exception {
        Path root = Paths.get(args[0]);
        // rename candidates (simple-name based): pkg -> set of candidate names
        Map<String, Set<String>> candidates = new TreeMap<>();
        for (int i = 1; i < args.length; i++) {
            for (String line : Files.readAllLines(Paths.get(args[i]), StandardCharsets.UTF_8)) {
                if (line.isBlank()) continue;
                String[] f = line.split("\t");
                if (f.length < 2) continue;
                String internal = f[0].trim();
                int s = internal.lastIndexOf('/');
                candidates.computeIfAbsent(internal.substring(0, s).replace('/', '.'), k -> new TreeSet<>()).add(internal.substring(s + 1));
            }
        }
        List<Path> files = new ArrayList<>();
        try (var w = Files.walk(root)) { w.filter(p -> p.toString().endsWith(".java")).forEach(files::add); }
        Collections.sort(files);

        // --- collect declared types: pkg -> set(simple names) ---
        Map<String, Set<String>> typesByPkg = new TreeMap<>();
        Map<String, String> typeToPkg = new TreeMap<>();          // pkg.Simple -> pkg
        Map<String, List<String>> members = new TreeMap<>();      // pkg.Simple -> member names
        for (Path p : files) {
            String t = Files.readString(p, StandardCharsets.UTF_8);
            String pkg = grab(t, "(?m)^package\\s+([\\w.]+)\\s*;");
            if (pkg == null) pkg = "";
            String cls = grab(t, "(?m)^(?:public\\s+)?(?:final\\s+)?(?:abstract\\s+)?(?:strictfp\\s+)?(?:class|interface|enum|record|@interface)\\s+([A-Za-z_$][\\w$]*)");
            if (cls == null) continue;
            typesByPkg.computeIfAbsent(pkg, k -> new TreeSet<>()).add(cls);
            typeToPkg.put(pkg.isEmpty() ? cls : pkg + "." + cls, pkg);
            // members: field/method declarations at one indent level
            List<String> ms = new ArrayList<>();
            java.util.regex.Matcher m = java.util.regex.Pattern.compile(
                    "(?m)^\\s{4}(?:public|private|protected|static|final|synchronized|abstract|native|volatile|transient)+" +
                            "[\\w\\s<>,\\.\\[\\]?]*?[\\w<>\\.\\[\\]]+\\s+([A-Za-z_$][\\w$]*)\\s*[=(;]").matcher(t);
            while (m.find()) ms.add(m.group(1));
            members.put(pkg.isEmpty() ? cls : pkg + "." + cls, ms);
        }

        // --- 1. package vs class clash: a type named X in package P, and also a package P.X ---
        Set<String> allPkgs = new TreeSet<>(typesByPkg.keySet());
        for (String pkg : typesByPkg.keySet()) for (String c : typesByPkg.get(pkg)) allPkgs.add(pkg.isEmpty() ? c : pkg + "." + c);
        List<String> clashes = new ArrayList<>();
        for (var e : typesByPkg.entrySet()) {
            String pkg = e.getKey();
            for (String c : e.getValue()) {
                String asPkg = pkg.isEmpty() ? c : pkg + "." + c;
                if (typesByPkg.containsKey(asPkg)) {
                    Set<String> cand = candidates.get(pkg);
                    boolean planned = cand != null && cand.contains(c);
                    clashes.add((planned ? "FIXED-BY-PLAN " : "UNFIXED ") + "TYPE " + asPkg + "  <->  PACKAGE " + asPkg
                            + " (" + typesByPkg.get(asPkg).size() + " types inside)"
                            + (planned ? " -> rename " + c + " to " + String.join("/", cand) : ""));
                }
            }
        }
        System.out.println("=== package/type name clashes: " + clashes.size() + " ===");
        clashes.forEach(s -> System.out.println("  " + s));

        // --- 2. keyword-named members ---
        List<String> kw = new ArrayList<>();
        for (var e : members.entrySet())
            for (String m : e.getValue()) if (KW.contains(m)) kw.add(e.getKey() + "." + m);
        System.out.println("=== keyword-named members: " + kw.size() + " ===");
        kw.forEach(s -> System.out.println("  " + s));

        // --- keyword-named TYPES (rare but fatal) ---
        List<String> kwTypes = new ArrayList<>();
        for (var e : typesByPkg.entrySet()) for (String c : e.getValue()) if (KW.contains(c)) kwTypes.add(e.getKey() + "." + c);
        System.out.println("=== keyword-named types: " + kwTypes.size() + " ===");
        kwTypes.forEach(System.out::println);

        // --- rename-safety: does any OTHER file mention the keyword member or the clashing type? ---
        System.out.println("=== cross-file references to keyword members (must be 0 for a safe local rename) ===");
        for (String q : kw) {
            String owner = q.substring(0, q.lastIndexOf('.'));
            String simple = owner.substring(owner.lastIndexOf('.') + 1);
            String member = q.substring(q.lastIndexOf('.') + 1);
            int ext = 0;
            for (Path p : files) {
                String full = p.toString();
                String t = Files.readString(p, StandardCharsets.UTF_8);
                boolean self = full.endsWith(simple + ".java") && owner.replace('.', '/').endsWith(pathOf(full, root));
                int hits = count(t, "(?<![\\w$])" + java.util.regex.Pattern.quote(simple) + "\\s*\\.\\s*" + java.util.regex.Pattern.quote(member) + "(?![\\w$])")
                        + count(t, java.util.regex.Pattern.quote(owner) + "\\." + java.util.regex.Pattern.quote(member) + "(?![\\w$])");
                if (self) hits -= count(t, "^\\s{4}.*\\s" + java.util.regex.Pattern.quote(member) + "\\s*[=(;]");
                else ext += hits;
            }
            System.out.println("   " + q + "  external_refs=" + ext);
        }
    }

    static String pathOf(String file, Path root) {
        return root.relativize(Paths.get(file)).toString().replace(File.separatorChar, '/');
    }

    static int count(String t, String re) { return occurrences(t, re); }

    static int occurrences(String t, String re) {
        java.util.regex.Matcher m = java.util.regex.Pattern.compile(re).matcher(t);
        int n = 0;
        while (m.find()) n++;
        return n;
    }

    static String grab(String t, String re) {
        java.util.regex.Matcher m = java.util.regex.Pattern.compile(re).matcher(t);
        return m.find() ? m.group(1) : null;
    }
}
