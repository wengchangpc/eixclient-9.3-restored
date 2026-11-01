import java.lang.reflect.*;
import java.util.*;

/** Find the option(s) controlling user-renamer activation in Vineflower. */
public class InspectWiring2 {
    public static void main(String[] a) throws Exception {
        for (String cn : new String[]{
                "org.jetbrains.java.decompiler.main.DecompilerContext",
                "org.jetbrains.java.decompiler.modules.renamer.IdentifierConverter",
                "org.jetbrains.java.decompiler.modules.renamer.ConverterHelper",
                "org.jetbrains.java.decompiler.main.IdentityRenamerFactory",
                "org.jetbrains.java.decompiler.main.decompiler.ConsoleDecompiler"}) {
            try {
                Class<?> c = Class.forName(cn);
                System.out.println("=== " + cn);
                for (Field f : c.getDeclaredFields()) System.out.println("    F " + f.getName() + " : " + f.getType().getSimpleName());
                for (Method m : c.getDeclaredMethods()) {
                    String s = m.getName();
                    if (s.toLowerCase().contains("renam")) System.out.println("    M " + m);
                }
            } catch (Throwable t) { System.out.println("MISSING " + cn); }
        }
        // dump the option enum keys that mention renaming
        try {
            Class<?> so = Class.forName("org.jetbrains.java.decompiler.main.SOptions");
            System.out.println("=== SOptions constants mentioning renam ===");
            for (Object o : so.getEnumConstants()) {
                String s = o.toString();
                if (s.toLowerCase().contains("renam")) System.out.println("    " + o + "  ->  " + s);
            }
            System.out.println("--- all SOptions names ---");
            for (Object o : so.getEnumConstants()) System.out.println("    " + ((Enum<?>) o).name());
        } catch (Throwable t) { System.out.println("no SOptions: " + t); }
    }
}
