import java.io.*;
import java.nio.file.*;
import java.util.*;

/** Like LoadCheck but prints the exact missing-type message per class, so two trees can be diffed. */
public class LoadDiff {
    static class L extends ClassLoader {
        final Path root; final Set<String> own;
        L(Path r, Set<String> o) { super(LoadDiff.class.getClassLoader()); root = r; own = o; }
        @Override protected Class<?> findClass(String n) throws ClassNotFoundException {
            String i = n.replace('.', '/');
            if (!own.contains(i)) throw new ClassNotFoundException("external:" + n);
            Path p = root.resolve(i + ".class");
            if (!Files.exists(p)) throw new ClassNotFoundException("missing_on_disk:" + n);
            try { byte[] b = Files.readAllBytes(p); return defineClass(n, b, 0, b.length); }
            catch (IOException e) { throw new ClassNotFoundException("io", e); }
        }
        Class<?> tryLoad(String internal) throws Exception { return findClass(internal.replace('/', '.')); }
    }

    public static void main(String[] a) throws Exception {
        Path root = Paths.get(a[0]);
        List<Path> fs = new ArrayList<>();
        try (var w = Files.walk(root)) { w.filter(p -> p.toString().endsWith(".class")).forEach(fs::add); }
        Collections.sort(fs);
        Set<String> own = new TreeSet<>();
        for (Path p : fs) own.add(root.relativize(p).toString().replace(File.separatorChar, '/').replaceAll("\\.class$", ""));
        L l = new L(root, own);
        // normalize: report only the MISSING TYPE (last path component set), not the referring class
        TreeMap<String, Integer> missing = new TreeMap<>();
        int ok = 0, cfe = 0;
        for (String i : own) {
            try { l.tryLoad(i); ok++; }
            catch (LinkageError e) {
                String m = e.getMessage();
                if (m == null) m = e.toString();
                m = m.replace('/', '.');
                if (m.startsWith("Failed executing") || m.contains("Bytes")) { cfe++; System.out.println("CLASSFORMAT " + i + " : " + m); continue; }
                // extract the type name from "... : <type>" or "NoClassDefFoundError: <type>"
                String t = m;
                int c = t.lastIndexOf(": ");
                if (c >= 0) t = t.substring(c + 2).trim();
                boolean isOwn = own.contains(t.replace('.', '/'));
                missing.merge((isOwn ? "OWN!! " : "ext ") + t, 1, Integer::sum);
            } catch (Throwable e) { missing.merge("other " + e.getClass().getSimpleName(), 1, Integer::sum); }
        }
        System.out.println("=== " + root.getFileName() + " defined=" + ok + " hard_classformat=" + cfe);
        System.out.println("missing types (own-referencing would be prefixed OWN!!):");
        missing.forEach((k, v) -> System.out.println("   " + v + "  " + k));
    }
}
