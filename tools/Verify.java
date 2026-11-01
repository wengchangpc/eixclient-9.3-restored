import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

/**
 * Verifier: every .class in the input must be represented by a type declaration
 * in the matching decompiled .java file (inner classes are merged into the outer file).
 */
public class Verify {
    public static void main(String[] a) throws Exception {
        Path cls = Paths.get(a[0]), src = Paths.get(a[1]);
        int total = 0, ok = 0;
        List<String> missing = new ArrayList<>();
        try (var w = Files.walk(cls)) {
            List<Path> fs = new ArrayList<>();
            w.filter(p -> p.toString().endsWith(".class")).forEach(fs::add);
            Collections.sort(fs);
            for (Path p : fs) {
                total++;
                String rel = cls.relativize(p).toString();                     // a\b\c$1.class
                String noExt = rel.substring(0, rel.length() - 6);
                String outer = noExt.contains("$") ? noExt.substring(0, noExt.indexOf('$')) : noExt;
                String simple = noExt.contains("$") ? noExt.substring(noExt.indexOf('$') + 1) : outer.substring(outer.lastIndexOf('\\') + 1);
                Path j = src.resolve(outer + ".java");
                if (!Files.exists(j)) { missing.add(rel + "  ->  " + outer + ".java MISSING"); continue; }
                String body = Files.readString(j, StandardCharsets.UTF_8);
                String name = simple.replace('$', '.');
                String tail = name.contains(".") ? name.substring(name.lastIndexOf('.') + 1) : name;
                if (body.contains("class " + tail) || body.contains("interface " + tail)
                        || body.contains("enum " + tail) || body.contains("record " + tail)
                        || body.contains("@interface " + tail) || tail.matches("\\d+")) {
                    ok++;
                } else {
                    missing.add(rel + "  ->  declared type '" + tail + "' not found in " + j.getFileName());
                }
            }
        }
        System.out.println("classes_total=" + total + " verified_in_source=" + ok + " unmatched=" + missing.size());
        System.out.println("java_files=" + Files.walk(src).filter(p -> p.toString().endsWith(".java")).count());
        missing.stream().limit(40).forEach(System.out::println);
    }
}
