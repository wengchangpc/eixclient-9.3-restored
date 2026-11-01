import java.lang.reflect.*;

/** Print the shape of Vineflower's IIdentifierRenamer so we can implement it correctly. */
public class InspectRenamer {
    public static void main(String[] a) throws Exception {
        String cn = a.length > 0 ? a[0] : "org.jetbrains.java.decompiler.main.extern.IIdentifierRenamer";
        Class<?> c = Class.forName(cn);
        System.out.println(c + "  interface=" + c.isInterface());
        for (Method m : c.getDeclaredMethods()) {
            System.out.println("   " + ret(m) + " " + m.getName() + "(" + params(m) + ")");
        }
        for (Class<?> inner : c.getDeclaredClasses()) System.out.println("   nested: " + inner);
        // also dump the ClassContext type if referenced
        for (Type p : c.getDeclaredMethods()[0].getParameterTypes()) {
            Class<?> pc = p instanceof Class ? (Class<?>) p : null;
            if (pc != null && pc.getName().contains("ClassContext")) {
                System.out.println("  --- " + pc.getName() + " ---");
                for (Method m : pc.getDeclaredMethods()) System.out.println("     " + ret(m) + " " + m.getName() + "(" + params(m) + ")");
            }
        }
    }
    static String ret(Method m) { return m.getReturnType().getSimpleName(); }
    static String params(Method m) {
        StringBuilder sb = new StringBuilder();
        for (Type t : m.getGenericParameterTypes()) { if (sb.length() > 0) sb.append(", "); sb.append(t.getTypeName()); }
        return sb.toString();
    }
}
