package com.heypixel.heypixelmod.obsoverlay.modules;

import com.heypixel.heypixelmod.obsoverlay.EixClient;
import com.heypixel.heypixelmod.obsoverlay.d.NamedHolder;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.ClickGUI;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.HUD;
import com.heypixel.heypixelmod.obsoverlay.utils.ag;
import com.heypixel.heypixelmod.obsoverlay.utils.ah;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class b extends NamedHolder {
    private static final Logger c = LogManager.getLogger(b.class);
    protected static final Minecraft a = Minecraft.getInstance();
    public static boolean b = true;
    private final ag d = new ag(100.0F);
    private String e;
    private String f;
    private String g;
    private String h;
    private String i;
    private a j;
    private boolean k;
    private int l = 0;
    private int m;

    public b(String var1, String var2, a var3) {
        this.e = var1;
        this.h = var2;
        this.j = var3;
        super.c(var1);
        this.p();
    }

    public b(String var1, String var2, String var3, a var4) {
        this.e = var1;
        this.f = var2;
        this.h = var3;
        this.j = var4;
        super.c(var1);
        this.p();
    }

    public b() {
    }

    public String a() {
        return this.f;
    }

    @Override
    public String b() {
        return this.e;
    }

    private void p() {
        StringBuilder var1 = new StringBuilder();
        char[] var2 = this.e.toCharArray();

        for (int var3 = 0; var3 < var2.length - 1; var3++) {
            if (Character.isLowerCase(var2[var3]) && Character.isUpperCase(var2[var3 + 1])) {
                var1.append(var2[var3]).append(" ");
            } else {
                var1.append(var2[var3]);
            }
        }

        var1.append(var2[var2.length - 1]);
        this.g = var1.toString();
    }

    protected void c() {
        if (this.getClass().isAnnotationPresent(ModuleInfo.class)) {
            ModuleInfo var1 = this.getClass().getAnnotation(ModuleInfo.class);
            this.e = var1.a();
            this.f = var1.b();
            this.h = var1.c();
            this.j = var1.d();
            super.c(this.e);
            this.p();
            EixClient.a().e().a(this);
        }
    }

    public void d() {
    }

    public void e() {
    }

    public void f() {
        this.a(!this.k);
    }

    public ag g() {
        return this.d;
    }

    @Override
    public String h() {
        try {
            EixClient var1 = EixClient.a();
            if (var1 != null && var1.g() != null) {
                ClickGUI var2 = var1.g().a(ClickGUI.class);
                if (var2 != null && var2.c != null) {
                    if (var2.c.l().equals("English")) {
                        return this.e;
                    } else {
                        return this.f != null && !this.f.isEmpty() ? this.f : this.e;
                    }
                } else {
                    return this.e;
                }
            } else {
                return this.e;
            }
        } catch (Exception var3) {
            return this.e;
        }
    }

    public String i() {
        return this.g;
    }

    public String j() {
        return this.h;
    }

    public String k() {
        return this.i;
    }

    public void a(String var1) {
        if (var1 == null) {
            this.i = null;
            b = true;
        } else if (!var1.equals(this.i)) {
            this.i = var1;
            b = true;
        }
    }

    public a l() {
        return this.j;
    }

    public boolean m() {
        return this.k;
    }

    public void a(boolean var1) {
        if (this.k != var1) {
            try {
                EixClient var2 = EixClient.a();
                if (var1) {
                    this.k = true;
                    var2.b().a(this);
                    this.d();
                    DynamicIsland.a(this, true);
                    if (!(this instanceof ClickGUI)) {
                        HUD var3 = EixClient.a().g().a(HUD.class);
                        if (var3 != null && var3.f.m()) {
                            ah.a("enable.wav", 1.0F);
                        }

                        if (var2.j() != null) {
                            com.heypixel.heypixelmod.obsoverlay.c.a.a var4 = new com.heypixel.heypixelmod.obsoverlay.c.a.a(
                                com.heypixel.heypixelmod.obsoverlay.c.a.b.a, this.h() + " Enabled!", 3000L
                            );
                            var2.j().a(var4);
                        }
                    }
                } else {
                    this.k = false;
                    var2.b().b(this);
                    this.e();
                    DynamicIsland.a(this, false);
                    if (!(this instanceof ClickGUI)) {
                        HUD var6 = EixClient.a().g().a(HUD.class);
                        if (var6 != null && var6.f.m()) {
                            ah.a("disable.wav", 1.0F);
                        }

                        if (var2.j() != null) {
                            com.heypixel.heypixelmod.obsoverlay.c.a.a var7 = new com.heypixel.heypixelmod.obsoverlay.c.a.a(
                                com.heypixel.heypixelmod.obsoverlay.c.a.b.d, this.h() + " Disabled!", 3000L
                            );
                            var2.j().a(var7);
                        }
                    }
                }

                com.heypixel.heypixelmod.obsoverlay.utils.c.c.d();
            } catch (Exception var5) {
                c.warn("切换模块状态失败: " + this.e, var5);
            }
        }
    }

    public int n() {
        return this.l;
    }

    public void a(int var1) {
        this.l = var1;
    }

    public int o() {
        return this.m;
    }

    public void b(int var1) {
        this.m = var1;
    }
}
