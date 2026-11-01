package com.heypixel.heypixelmod.obsoverlay.modules;

import com.heypixel.heypixelmod.obsoverlay.EixClient;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.ClickGUI;
import com.heypixel.heypixelmod.obsoverlay.utils.l;

public enum a {
    a("Combat", "战斗", l.b),
    b("Movement", "移动", l.c),
    c("Render", "视觉", l.d),
    d("Misc", "杂项", l.e);

    private final String e;
    private final String f;
    private final String g;

    private a(String var3, String var4, String var5) {
        this.e = var3;
        this.f = var4;
        this.g = var5;
    }

    public String a() {
        EixClient var1 = EixClient.a();
        if (var1 != null && var1.g() != null) {
            ClickGUI var2 = var1.g().a(ClickGUI.class);
            if (var2 != null && var2.c != null) {
                return var2.c.l().equals("English") ? this.e : this.f;
            } else {
                return this.e;
            }
        } else {
            return this.e;
        }
    }

    public String b() {
        return this.g;
    }
}
