import java.lang.reflect.*;

/** How does Vineflower decide to use the user renamer? Inspect DecompilerOptions + ClassWriter. */
public class InspectWiring {
    public static void main(String[] a) throws Exception {
        for (String cn : new String[]{
                "org.jetbrains.java.decompiler.main.DecompilerOptions",
                "org.jetbrains.java.decompiler.main.extern.IIdentifierRenamer",
                "org.jetbrains.java.decompiler.struct.StructContext",
                "org.jetbrains.java.decompiler.main.renaming.ClassNamingMode"}) {
            try { Class<?> c = Class.forName(cn); System.out.println("FOUND " + c); }
            catch (Throwable t) { System.out.println("no " + cn); }
        }
        Class<?> dopt = Class.forName("org.jetbrains.java.decompiler.main.DecompilerOptions");
        for (Field f : dopt.getDeclaredFields()) System.out.println("  field " + f.getName() + " : " + f.getType().getSimpleName());
        for (Method m : dopt.getDeclaredMethods()) {
            String s = m.toString();
            if (s.toLowerCase().contains("renam")) System.out.println("  METHOD " + s);
        }
    }
}
