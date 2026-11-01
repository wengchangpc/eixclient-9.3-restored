import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Load every class in a directory with the JDK's own verifier:
 *   defineClass(...)  -> proves the class-file BYTES are structurally valid
 *   Class.getName()   -> proves this_class matches what we loaded
 * Then, with -resolve, link them (resolution needs the referenced classes to exist),
 * which catches dangling internal references introduced by a rename.
 * The jar references Minecraft/Forge/Skija, so plain loadClass is expected to fail on
 * missing externals; we therefore load with resolve=false by default and report
 * "NoClassDefFoundError for an EXTERNAL type" as expected, not as a defect.
 */
public class LoadCheck {
    static class Loader extends ClassLoader {
        final Path root;
        final Set<String> own;
        final Map<String, Class<?>> done = new HashMap<>();
        Loader(Path root, Set<String> own) { super(LoadCheck.class.getClassLoader()); this.root = root; this.own = own; }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            String internal = name.replace('.', '/');
            if (!own.contains(internal)) throw new ClassNotFoundException("external:" + name);
            Path p = root.resolve(internal + ".class");
            if (!Files.exists(p)) throw new ClassNotFoundException("missing_on_disk:" + name);
            try {
                byte[] b = Files.readAllBytes(p);
                Class<?> c = defineClass(name, b, 0, b.length);
                done.put(name, c);
                if (!c.getName().equals(name)) throw new RuntimeException("NAME_MISMATCH " + c.getName() + " != " + name);
                return c;
            } catch (IOException e) { throw new ClassNotFoundException("io:" + name, e); }
        }

        Class<?> load(String internalName) throws ClassNotFoundException { return loadClass(internalName.replace('/', '.')); }
    }

    public static void main(String[] args) throws Exception {
        Path root = Paths.get(args[0]);
        boolean resolve = args.length > 1 && args[1].equals("-resolve");
        List<Path> files = new ArrayList<>();
        try (var w = Files.walk(root)) { w.filter(p -> p.toString().endsWith(".class")).forEach(files::add); }
        Collections.sort(files);
        Set<String> own = new HashSet<>();
        for (Path p : files) own.add(root.relativize(p).toString().replace(File.separatorChar, '/').replaceAll("\\.class$", ""));

        Loader l = new Loader(root, own);
        int loaded = 0, byteDefect = 0, externalMissing = 0;
        Map<String, Integer> other = new TreeMap<>();
        List<String> bad = new ArrayList<>();
        for (Path p : files) {
            String internal = root.relativize(p).toString().replace(File.separatorChar, '/').replaceAll("\\.class$", "");
            String binary = internal.replace('/', '.');
            try {
                l.findClass(binary);
                loaded++;
            } catch (ClassFormatError e) {
                byteDefect++;                                    // JVM rejected our bytes => real defect
                bad.add("CLASSFORMAT " + internal + " : " + e);
            } catch (NoClassDefFoundError e) {
                String m = String.valueOf(e.getMessage()).replace('/', '.');
                if (own.contains(m.replace('.', '/'))) { byteDefect++; bad.add("DANGLING_OWN " + internal + " -> " + m); }
                else externalMissing++;                          // Minecraft/Netty/etc. not on classpath: expected
            } catch (Throwable e) {
                String k = e.getClass().getSimpleName();
                other.merge(k, 1, Integer::sum);
                if (e instanceof LinkageError) {
                    String m = String.valueOf(e.getMessage());
                    if (m != null && !own.contains(m.replace('.', '/'))) externalMissing++;
                    else { byteDefect++; bad.add("LINKAGE " + internal + " : " + e); }
                }
            }
        }
        System.out.println("=== LOAD CHECK " + (resolve ? "(resolve/link)" : "(bytes only)") + " : " + root.getFileName() + " ===");
        System.out.println("classes=" + files.size() + " defined_with_jvm_verifier=" + loaded
                + " byte_defects=" + byteDefect + " expected_external_missing=" + externalMissing);
        if (!other.isEmpty()) System.out.println("other_throwable_kinds=" + other);
        bad.stream().limit(12).forEach(System.out::println);

        if (resolve) {
            int linked = 0, linkFail = 0;
            List<String> lf = new ArrayList<>();
            for (Path p : files) {
                String internal = root.relativize(p).toString().replace(File.separatorChar, '/').replaceAll("\\.class$", "");
                try {
                    Class<?> c = l.load(internal);
                    // force resolution of the constant pool
                    c.getDeclaredFields();
                    c.getDeclaredMethods();
                    c.getInterfaces();
                    if (c.getSuperclass() != null) Class.forName(c.getSuperclass().getName(), false, l);
                    linked++;
                } catch (NoClassDefFoundError e) {
                    String m = String.valueOf(e.getMessage());
                    if (m.startsWith("external:") || m.startsWith("missing_on_disk:")) { /* external dep, fine */ }
                    else { linkFail++; lf.add("DANGLING " + internal + " -> " + m); }
                } catch (Throwable e) {
                    linkFail++;
                    lf.add("LINK " + internal + " : " + e.getClass().getSimpleName() + " " + String.valueOf(e.getMessage()).substring(0, Math.min(90, String.valueOf(e.getMessage()).length())));
                }
            }
            System.out.println("linked=" + linked + " link_failures=" + linkFail);
            lf.stream().limit(20).forEach(System.out::println);
        }
    }
}
