package com.heypixel.heypixelmod.obsoverlay.modules.impl.render;

import com.heypixel.heypixelmod.obsoverlay.d.d;
import com.heypixel.heypixelmod.obsoverlay.d.a.c;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.p;
import com.heypixel.heypixelmod.obsoverlay.events.impl.s;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.a;
import com.heypixel.heypixelmod.obsoverlay.modules.b;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.Teams;
import com.heypixel.heypixelmod.obsoverlay.utils.aa;
import com.heypixel.heypixelmod.obsoverlay.utils.m;
import com.heypixel.heypixelmod.obsoverlay.utils.e.SkijaDraw;
import io.github.humbleui.skija.Font;
import java.awt.Color;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

@ModuleInfo(
    a = "NameTags",
    b = "名称标签",
    d = com.heypixel.heypixelmod.obsoverlay.modules.a.RENDER,
    c = "Renders name tags"
)
public class NameTags extends b {
    private final Map<Entity, com.heypixel.heypixelmod.obsoverlay.utils.f.b> f = new ConcurrentHashMap<>();
    public c c = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Scale").a(0.3F).d(0.01F).b(0.1F).c(1.0F).a().c();
    public com.heypixel.heypixelmod.obsoverlay.d.a.a d = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Blur").a(true).a().b();
    public com.heypixel.heypixelmod.obsoverlay.d.a.a e = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Bloom").a(true).a().b();
    private static final float g = 10.0F;
    private static final float h = 8.0F;
    private static final float i = 16.0F;
    private static final Color j = new Color(0, 0, 0, 100);
    private static final Color k = new Color(200, 200, 200);
    private static final Color l = new Color(255, 85, 85);
    private final List<String> m = new java.util.ArrayList<>();

    @EventTarget
    public void update(p var1) {
        try {
            this.a(var1.a());
        } catch (Exception var3) {
        }
    }

    @EventTarget
    public void onRenderSkia(s var1) {
        for (Entry var3 : this.f.entrySet()) {
            if (var3.getKey() != a.player && var3.getKey() instanceof Player var4) {
                float var26 = var4.getHealth();
                if (var26 > 20.0F) {
                    var4.setHealth(20.0F);
                }

                com.heypixel.heypixelmod.obsoverlay.utils.f.b var6 = (com.heypixel.heypixelmod.obsoverlay.utils.f.b)var3.getValue();
                float var7 = 6.0F * this.c.q();
                float var8 = 6.0F * this.c.q();
                float var9 = 4.0F * this.c.q();
                float var10 = 3.0F * this.c.q();
                this.m.clear();
                if (Teams.a(var4)) {
                    this.m.add("§aTeam");
                }

                if (com.heypixel.heypixelmod.obsoverlay.utils.m.a((Entity)var4)) {
                    this.m.add("§aFriend");
                }

                String var11 = String.join(" ", this.m);
                String var12 = var4.getName().getString();
                String var13 = String.valueOf(Math.round(var26));
                if (var4.getAbsorptionAmount() > 0.0F) {
                    var13 = var13 + "+" + Math.round(var4.getAbsorptionAmount());
                }

                float var14 = 14.0F * this.c.q();
                Font var15 = com.heypixel.heypixelmod.obsoverlay.utils.e.b.c.a(var14);
                Font var16 = com.heypixel.heypixelmod.obsoverlay.utils.e.b.c.b(var14);
                float var17 = var11.isEmpty() ? 0.0F : SkijaDraw.a(var11, var15) + var7 * 2.0F;
                float var18 = SkijaDraw.a(var12, var15) + var7 * 2.0F;
                float var19 = var16.measureTextWidth("\ue87d");
                float var20 = SkijaDraw.a(var13, var15) + var19 + var7 * 2.0F + var10;
                float var21 = var18 + var20 + (var17 > 0.0F ? var17 + var9 : 0.0F) + var9;
                float var22 = var14 + var7 * 1.5F;
                float var23 = var6.a - var21 / 2.0F;
                float var24 = var6.b - var22 / 2.0F;
                float var25 = var23;
                if (!var11.isEmpty()) {
                    this.a(var23, var24, var17, var22, var8, var7, var10, var11, var15, Color.WHITE, null, null);
                    var25 = var23 + (var17 + var9);
                }

                this.a(var25, var24, var18, var22, var8, var7, var10, var12, var15, Color.WHITE, null, null);
                var25 += var18 + var9;
                this.a(var25, var24, var20, var22, var8, var7, var10, var13, var15, k, "\ue87d", var16);
            }
        }
    }

    private float a(double var1) {
        double var3 = Math.max(8.0, Math.min(var1, 16.0));
        return (float)(10.0F * this.c.q() / var3);
    }

    private void a(
        float var1, float var2, float var3, float var4, float var5, float var6, float var7, String var8, Font var9, Color var10, String var11, Font var12
    ) {
        if (this.e.m()) {
            SkijaDraw.c(var1, var2, var3, var4, var5);
        }

        if (this.d.m()) {
            SkijaDraw.a(var1, var2, var3, var4, var5);
        }

        SkijaDraw.a(var1, var2, var3, var4, var5, j);
        float var13 = var2 + var4 / 2.0F - var9.getMetrics().getCapHeight() / 2.0F;
        float var14 = var1 + var6;
        SkijaDraw.a(var8, var14, var13, var10, var9);
        if (var11 != null && var12 != null) {
            float var15 = SkijaDraw.a(var8, var9);
            SkijaDraw.a(var11, var14 + var15 + var7, var13, l, var12);
        }
    }

    private void a(float var1) {
        this.f.clear();

        for (Entity var3 : a.level.entitiesForRendering()) {
            if (var3 instanceof Player && !var3.getName().getString().startsWith("CIT-")) {
                double var4 = com.heypixel.heypixelmod.obsoverlay.utils.s.b(var3.xo, var3.getX(), (double)var1);
                double var6 = com.heypixel.heypixelmod.obsoverlay.utils.s.b(var3.yo, var3.getY(), (double)var1) + var3.getBbHeight() + 0.5;
                double var8 = com.heypixel.heypixelmod.obsoverlay.utils.s.b(var3.zo, var3.getZ(), (double)var1);
                com.heypixel.heypixelmod.obsoverlay.utils.f.b var10 = aa.a(var4, var6, var8, var1);
                var10.b(var10.b() - 2.0F);
                this.f.put(var3, var10);
            }
        }
    }
}
