package com.heypixel.heypixelmod.obsoverlay.d;

import java.util.ArrayList;
import java.util.List;

public class e {
    private final List<c> a = new ArrayList<>();

    public void a(c var1) {
        this.a.add(var1);
    }

    public List<c> a(NamedHolder var1) {
        ArrayList var2 = new ArrayList();

        for (c var4 : this.a) {
            if (var4.i() == var1) {
                var2.add(var4);
            }
        }

        return var2;
    }

    public c a(NamedHolder var1, String var2) {
        for (c var4 : this.a) {
            if (var4.i() == var1 && var4.j().equals(var2)) {
                return var4;
            }
        }

        return null;
    }

    public List<c> a() {
        return this.a;
    }
}
