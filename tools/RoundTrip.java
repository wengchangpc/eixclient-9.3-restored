import java.io.*;
import java.nio.file.*;
import java.util.*;

/** Round-trip test: rewriting with an EMPTY map must reproduce the original bytes exactly. */
public class RoundTrip {
    public static void main(String[] a) throws Exception {
        Path in = Paths.get(a[0]);
        List<Path> fs = new ArrayList<>();
        try (var w = Files.walk(in)) { w.filter(p -> p.toString().endsWith(".class")).forEach(fs::add); }
        Collections.sort(fs);
        int same = 0, diff = 0, err = 0;
        for (Path p : fs) {
            byte[] b = Files.readAllBytes(p);
            try {
                RenameBytecode.ClassReader cr = new RenameBytecode.ClassReader(b);
                byte[] nb = cr.serialize();
                if (Arrays.equals(b, nb)) same++;
                else {
                    diff++;
                    if (diff <= 3) {
                        int i = 0;
                        while (i < Math.min(b.length, nb.length) && b[i] == nb[i]) i++;
                        System.out.println("DIFF " + in.relativize(p) + " first_mismatch_at=" + i
                                + " orig_len=" + b.length + " new_len=" + nb.length
                                + " orig[" + i + "]=" + (b[i] & 0xFF) + " new[" + i + "]=" + (nb[i] & 0xFF));
                    }
                }
            } catch (Exception e) {
                err++;
                if (err <= 3) System.out.println("ERR " + in.relativize(p) + " " + e);
            }
        }
        System.out.println("identical=" + same + " different=" + diff + " errors=" + err + " total=" + fs.size());
    }
}
