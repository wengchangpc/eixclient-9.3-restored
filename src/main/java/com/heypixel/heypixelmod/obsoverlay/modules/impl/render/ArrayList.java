package com.heypixel.heypixelmod.obsoverlay.modules.impl.render;

import com.heypixel.heypixelmod.obsoverlay.EixClient;
import com.heypixel.heypixelmod.obsoverlay.d.d;
import com.heypixel.heypixelmod.obsoverlay.d.a.c;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.s;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.a;
import com.heypixel.heypixelmod.obsoverlay.modules.b;
import com.heypixel.heypixelmod.obsoverlay.utils.e.SkijaDraw;
import io.github.humbleui.skija.Font;
import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ModuleInfo(
    a = "ArrayList",
    b = "模块列表",
    c = "显示你打开的模块",
    d = com.heypixel.heypixelmod.obsoverlay.modules.a.RENDER
)
public class ArrayList extends b {
    private static final float h = 5.0F;
    private static final float i = 5.0F;
    public com.heypixel.heypixelmod.obsoverlay.d.a.a c = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Pretty Module Name")
        .a(var0 -> com.heypixel.heypixelmod.obsoverlay.modules.b.b = true)
        .a(false)
        .a()
        .b();
    public com.heypixel.heypixelmod.obsoverlay.d.a.a d = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Hide Render Modules")
        .a(var0 -> com.heypixel.heypixelmod.obsoverlay.modules.b.b = true)
        .a(true)
        .a()
        .b();
    public com.heypixel.heypixelmod.obsoverlay.d.a.a e = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Sync Color").a(true).a().b();
    public c f = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Speed").b(1.0F).c(15.0F).a(4.0F).d(0.5F).a(() -> this.e.m()).a().c();
    public c g = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "ArrayList Size").a(10.0F).d(1.0F).b(1.0F).c(32.0F).a().c();
    private List<b> j;
    private final Map<b, String> k = new HashMap<>();
    private final Map<b, Float> l = new HashMap<>();
    private float m = -1.0F;
    private long n = 0L;
    private static final long o = 200L;

    @EventTarget
    public void onSkia(s var1) {
        Font var2 = com.heypixel.heypixelmod.obsoverlay.utils.e.b.c.a(this.g.q());
        float var3 = this.g.q();
        float var4 = var3 * 1.35F;
        float var5 = var3 * 0.18F;
        float var6 = var3 * 0.32F;
        long var7 = System.currentTimeMillis();
        if ((com.heypixel.heypixelmod.obsoverlay.modules.b.b || this.m != var3) && var7 - this.n > 200L) {
            this.a(var2);
            this.m = var3;
            com.heypixel.heypixelmod.obsoverlay.modules.b.b = false;
            this.n = var7;
        }

        float var9 = 5.0F;
        SkijaDraw.a("EixClient GV1.0", 5.0F, var9, new Color(255, 255, 255), var2);
        var9 += var4 + var6;

        for (b var11 : this.j) {
            if (var11.m()) {
                String var12 = this.k.getOrDefault(var11, var11.h());
                Color var13;
                if (this.e.m()) {
                    var13 = com.heypixel.heypixelmod.obsoverlay.utils.renderer.b.a(
                        (int)this.f.q(), (int)var9, new Color(120, 255, 160), new Color(255, 255, 140), false
                    );
                } else {
                    var13 = new Color(140, 255, 180);
                }

                SkijaDraw.a(var12, 5.0F, var9, var13, var2);
                var9 += var4 + var5;
            }
        }
    }

    private void a(Font var1) {
        com.heypixel.heypixelmod.obsoverlay.modules.c var2 = EixClient.a().g();
        this.j = new java.util.ArrayList<>();
        this.k.clear();
        this.l.clear();

        for (b var4 : var2.a()) {
            if (var4.m() && (!this.d.m() || var4.l() != com.heypixel.heypixelmod.obsoverlay.modules.a.c)) {
                String var5 = this.a(var4);
                float var6 = SkijaDraw.a(var5, var1);
                this.j.add(var4);
                this.k.put(var4, var5);
                this.l.put(var4, var6);
            }
        }

        this.j.sort((var1x, var2x) -> {
            float var3 = this.l.getOrDefault(var1x, 0.0F);
            float var4x = this.l.getOrDefault(var2x, 0.0F);
            return Float.compare(var4x, var3);
        });
    }

    public String a(b var1) {
        String var2 = this.c.m() ? var1.i() : var1.h();
        return var2 + (var1.k() == null ? "" : " §7" + var1.k());
    }
}
