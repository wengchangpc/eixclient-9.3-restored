import java.lang.reflect.*;

/** Dump IIdentifierRenamer incl. default methods and the Type enum, to honour the contract. */
public class InspectRenamer2 {
    public static void main(String[] a) throws Exception {
        Class<?> c = Class.forName("org.jetbrains.java.decompiler.main.extern.IIdentifierRenamer");
        System.out.println("== methods (declared) ==");
        for (Method m : c.getDeclaredMethods()) {
            System.out.println((m.isDefault() ? "DEFAULT  " : "ABSTRACT ") + m);
        }
        Class<?> t = Class.forName("org.jetbrains.java.decompiler.main.extern.IIdentifierRenamer$Type");
        System.out.println("== Type enum ==");
        for (Object o : t.getEnumConstants()) System.out.println("   " + o);
        System.out.println("== constructors ==");
        for (Constructor<?> k : c.getDeclaredConstructors()) System.out.println("   " + k);
    }
}
