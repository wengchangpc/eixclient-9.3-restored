import org.jetbrains.java.decompiler.main.extern.IIdentifierRenamer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

/**
 * AST-level class renamer for Vineflower.
 * Renames ONLY the classes listed in the rename map (modules/commands whose real names
 * survived in @ModuleInfo / @CommandInfo). Fields and methods are left untouched,
 * because no reliable original names exist for them.
 *
 * map format (TSV, from MetaExtract2): internalOldName \t NewName \t KIND \t ...
 */
public class Renamer implements IIdentifierRenamer {

    static final Map<String, String> byUniqueDot = new HashMap<>();   // "com.foo.Bar" -> "KillAura"
    static final Map<String, String> byUniqueSlash = new HashMap<>(); // "com/foo/Bar" -> "KillAura"
    static final Map<String, String> bySimpleName = new HashMap<>();  // "a" -> set-like (ambiguous dropped)
    static PrintStream log;
    static int askedClass = 0, renamed = 0, missed = 0;

    static {
        try {
            String map = System.getenv("VF_RENAME_MAP");
            String lg = System.getenv("VF_RENAME_LOG");
            if (map != null) load(Paths.get(map));
            if (lg != null) { Files.createDirectories(Paths.get(lg).getParent()); log = new PrintStream(new FileOutputStream(lg, false), true, StandardCharsets.UTF_8); }
        } catch (Exception e) {
            if (log != null) e.printStackTrace(log);
        }
    }

    static void load(Path p) throws IOException {
        for (String line : Files.readAllLines(p, StandardCharsets.UTF_8)) {
            if (line.isBlank()) continue;
            String[] f = line.split("\t");
            if (f.length < 2) continue;
            String internal = f[0].trim();            // com/heypixel/.../impl/a/a
            String nn = f[1].trim();                   // AimAssist
            byUniqueSlash.put(internal, nn);
            byUniqueDot.put(internal.replace('/', '.'), nn);
            String simple = internal.substring(internal.lastIndexOf('/') + 1);
            bySimpleName.merge(simple, nn, (a, b) -> a.equals(b) ? a : "\u0000AMBIG");
        }
    }

    @Override
    public boolean toBeRenamed(Type kind, String owner, String name, String exception) {
        if (kind != Type.ELEMENT_CLASS) return false;      // never touch fields / methods
        askedClass++;
        String hit = lookup(owner, name);
        if (hit == null) { missed++; return false; }
        return true;
    }

    @Override
    public String getNextClassName(String prevName, String uniqueName) {
        String hit = lookup(uniqueName, prevName);
        if (hit == null) {
            if (log != null) log.println("NOHIT class prev=" + prevName + " unique=" + uniqueName);
            renamed++;
            return prevName;
        }
        renamed++;
        if (log != null) log.println("RENAME " + uniqueName + " (" + prevName + ") -> " + hit);
        return hit;
    }

    String lookup(String a, String b) {
        if (a != null) {
            String k = a.startsWith("a.") ? a.substring(2) : a;
            String v = byUniqueDot.get(k);
            if (v != null) return v;
            v = byUniqueSlash.get(k.replace('.', '/'));
            if (v != null) return v;
            v = byUniqueDot.get(a);
            if (v != null) return v;
        }
        if (b != null) {
            String v = bySimpleName.get(b);
            if (v != null && !v.equals("\u0000AMBIG")) return v;
        }
        return null;
    }

    @Override
    public String getNextFieldName(String prevName, String owner, String name) { return prevName; }

    @Override
    public String getNextMethodName(String prevName, String owner, String name) { return prevName; }
}
