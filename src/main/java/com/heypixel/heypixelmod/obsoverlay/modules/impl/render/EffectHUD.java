package com.heypixel.heypixelmod.obsoverlay.modules.impl.render;

import com.heypixel.heypixelmod.obsoverlay.d.d;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.s;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.b;
import com.heypixel.heypixelmod.obsoverlay.utils.ag;
import com.heypixel.heypixelmod.obsoverlay.utils.e.SkijaDraw;
import com.heypixel.heypixelmod.obsoverlay.utils.e.b.c;
import io.github.humbleui.skija.ClipMode;
import io.github.humbleui.skija.Path;
import io.github.humbleui.types.RRect;
import java.awt.Color;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.util.StringUtil;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

@ModuleInfo(
    a = "EffectHUD",
    b = "效果显示",
    c = "Displays potion effects on the HUD",
    d = com.heypixel.heypixelmod.obsoverlay.modules.a.RENDER
)
public class EffectHUD extends b {
    private final Map<MobEffect, EffectHUD.a> c = new ConcurrentHashMap<>();
    private final com.heypixel.heypixelmod.obsoverlay.d.a.b d = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Position").e(10.0F).f(250.0F).a().f();

    @EventTarget
    public void onRender(s var1) {
        for (MobEffectInstance var3 : a.player.getActiveEffects()) {
            EffectHUD.a var4;
            if (this.c.containsKey(var3.getEffect())) {
                var4 = this.c.get(var3.getEffect());
            } else {
                var4 = new EffectHUD.a();
                this.c.put(var3.getEffect(), var4);
            }

            var4.d = Math.max(var4.d, var3.getDuration());
            var4.e = var3.getDuration();
            var4.f = var3.getAmplifier();
            var4.g = false;
        }

        int var19 = (int)this.d.o();

        for (Entry var21 : this.c.entrySet()) {
            EffectHUD.a var5 = (EffectHUD.a)var21.getValue();
            String var6 = this.a((MobEffect)var21.getKey(), var5);
            if (var5.b.c == -1.0F) {
                var5.b.c = var19;
            }

            float var7 = var5.a.c;
            float var8 = var5.b.c;
            String var9 = StringUtil.formatTickDuration(var5.e);
            float var12 = 20.0F;
            float var13 = 20.0F;
            float var14 = var7 + 25.0F;
            float var16 = SkijaDraw.a(var6, com.heypixel.heypixelmod.obsoverlay.utils.e.b.c.a(10.0F))
                + SkijaDraw.a(var9, com.heypixel.heypixelmod.obsoverlay.utils.e.b.c.a(8.0F))
                + 12.0F;
            float var17 = 20.0F;
            var5.h = 25.0F + var16;
            var5.g = !a.player.hasEffect((MobEffect)var21.getKey());
            if (var5.g) {
                var5.a.a = -var5.h - 20.0F;
                if (var7 <= -var5.h - 20.0F) {
                    this.c.remove(var21.getKey());
                }
            } else {
                var5.c.a = (float)var5.e / var5.d * var5.h;
                if (var5.c.c <= 0.0F) {
                    var5.c.c = var5.c.a;
                }

                var5.a.a = this.d.n();
                var5.b.a = var19;
                var5.b.a(true);
            }

            var5.c.a(true);
            var5.a.a(true);
            SkijaDraw.c(var7, var8, var12, var13, 5.0F);
            SkijaDraw.a(var7, var8, var12, var13, 5.0F);
            SkijaDraw.c(var14, var8, var16, var17, 5.0F);
            SkijaDraw.a(var14, var8, var16, var17, 5.0F);
            Path var18 = new Path();
            var18.addRRect(RRect.makeXYWH(var7, var8, var12, var13, 5.0F));
            var18.addRRect(RRect.makeXYWH(var14, var8, var16, var17, 5.0F));
            SkijaDraw.c();
            SkijaDraw.e().clipPath(var18, ClipMode.INTERSECT, true);
            SkijaDraw.a(var7, var8, var5.h, 30.0F, new Color(0, 0, 0, 50));
            SkijaDraw.a(var7, var8, var5.c.c, 30.0F, new Color(0, 0, 0, 50));
            SkijaDraw.d();
            SkijaDraw.a((MobEffect)var21.getKey(), var7 + 2.0F, var8 + 2.0F, 16.0F, 16.0F);
            SkijaDraw.a(var6, var14 + 5.0F, var8 + 5.0F, new Color(255, 255, 255), com.heypixel.heypixelmod.obsoverlay.utils.e.b.c.a(10.0F));
            SkijaDraw.a(
                var9,
                var14 + 7.0F + SkijaDraw.a(var6, com.heypixel.heypixelmod.obsoverlay.utils.e.b.c.a(10.0F)),
                var8 + 8.0F,
                new Color(200, 200, 200),
                com.heypixel.heypixelmod.obsoverlay.utils.e.b.c.a(8.0F)
            );
            var19 += 28;
            this.d.c(Math.max(var5.h, this.d.p()));
            this.d.d(var19 - this.d.o());
        }
    }

    public String a(MobEffect var1, EffectHUD.a var2) {
        String var3 = var1.getDisplayName().getString();
        String var4;
        if (var2.f == 0) {
            var4 = "";
        } else if (var2.f == 1) {
            var4 = " " + I18n.get("enchantment.level.2", new Object[0]);
        } else if (var2.f == 2) {
            var4 = " " + I18n.get("enchantment.level.3", new Object[0]);
        } else if (var2.f == 3) {
            var4 = " " + I18n.get("enchantment.level.4", new Object[0]);
        } else {
            var4 = " " + var2.f;
        }

        return var3 + var4;
    }

    public static class a {
        public ag a = new ag(-60.0F, 0.2F);
        public ag b = new ag(-1.0F, 0.2F);
        public ag c = new ag(-1.0F, 0.2F);
        public int d = -1;
        public int e = 0;
        public int f = 0;
        public boolean g = false;
        public float h;
    }
}
