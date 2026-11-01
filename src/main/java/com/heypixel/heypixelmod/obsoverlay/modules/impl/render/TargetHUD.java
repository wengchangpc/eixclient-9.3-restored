package com.heypixel.heypixelmod.obsoverlay.modules.impl.render;

import com.heypixel.heypixelmod.obsoverlay.EixClient;
import com.heypixel.heypixelmod.obsoverlay.d.d;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventRunTicks;
import com.heypixel.heypixelmod.obsoverlay.events.impl.s;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.a;
import com.heypixel.heypixelmod.obsoverlay.modules.b;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.a.KillAura;
import com.heypixel.heypixelmod.obsoverlay.utils.ag;
import com.heypixel.heypixelmod.obsoverlay.utils.e.SkijaDraw;
import com.heypixel.heypixelmod.obsoverlay.utils.e.b.c;
import io.github.humbleui.skija.ClipMode;
import io.github.humbleui.skija.Font;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.Path;
import io.github.humbleui.types.RRect;
import io.github.humbleui.types.Rect;
import java.awt.Color;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.phys.EntityHitResult;

@ModuleInfo(
    a = "TargetHUD",
    b = "目标状态显示",
    c = "Display target info.",
    d = com.heypixel.heypixelmod.obsoverlay.modules.a.RENDER
)
public class TargetHUD extends b {
    private final ag c = new ag(20.0F);
    private final ag d = new ag(255.0F);
    private final ag e = new ag(0.0F);
    private final ag f = new ag(20.0F);
    private final com.heypixel.heypixelmod.obsoverlay.d.a.b g = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Position").e(500.0F).f(200.0F).a().f();
    private final com.heypixel.heypixelmod.obsoverlay.d.a.a h = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "HurtFlash").a(true).a().b();
    private final com.heypixel.heypixelmod.obsoverlay.d.a.a i = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "ShowAbsorption").a(true).a().b();
    private AbstractClientPlayer j;

    @EventTarget
    public void onRenderSkia(s var1) {
        if (a.screen instanceof com.heypixel.heypixelmod.obsoverlay.c.b) {
            this.j = a.player;
            this.d.c = 255.0F;
        }

        this.d.a(true);
        if (!(this.d.c < 1.0F)) {
            this.c.a(true);
            this.e.a(true);
            this.f.a(true);
            float var2 = this.g.n();
            float var3 = this.g.o();
            float var4 = 240.0F;
            float var5 = 40.0F;
            Font var6 = com.heypixel.heypixelmod.obsoverlay.utils.e.b.c.a(10.0F);
            Font var7 = com.heypixel.heypixelmod.obsoverlay.utils.e.b.c.b(10.0F);
            float var8 = var7.measureTextWidth("\ue87d");
            String var9 = this.j.getName().getString();
            float var10 = SkijaDraw.a(var9, var6);
            String var11 = String.format("%.1f / 20", this.c.c);
            float var12 = SkijaDraw.a(var11, var6);
            float var13 = var3 + 11.0F;
            float var14 = var10 + 10.0F;
            float var15 = var2 + 44.0F + var14 + var8 + 4.0F + var12;
            float var16 = 160.0F;
            float var17 = var16;
            float var18 = 46.0F + var16;
            float var19 = Math.max(var4, var18 + 10.0F);
            Paint var20 = new Paint();

            try {
                var20.setAlpha((int)this.d.c);
                SkijaDraw.e().saveLayer(Rect.makeXYWH(var2 - 14.0F, var3 - 14.0F, var19 + 28.0F, var5 + 28.0F), var20);
                SkijaDraw.c(var2, var3, var19, var5, 8.0F);
                SkijaDraw.a(var2, var3, var19, var5, 8.0F);
                SkijaDraw.a(var2, var3, var19, var5, 8.0F, new Color(0, 0, 0, 70));
                SkijaDraw.a(this.j, var2 + 8.0F, var3 + 6.0F, 28.0F, 28.0F, 4.0F);
                if (this.e.c > 1.0F) {
                    SkijaDraw.a(var2 + 8.0F, var3 + 6.0F, 28.0F, 28.0F, 4.0F, new Color(255, 60, 60, (int)this.e.c));
                }

                SkijaDraw.a(var9, var2 + 44.0F, var13, new Color(255, 255, 255), var6);
                SkijaDraw.a("\ue87d", var2 + 44.0F + var14, var13 - 0.5F, new Color(215, 215, 215), var7);
                SkijaDraw.a(var11, var2 + 44.0F + var14 + var8 + 4.0F, var13, new Color(215, 215, 215), var6);
                Path var21 = new Path();
                var21.addRRect(RRect.makeXYWH(var2 + 44.0F, var3 + 24.0F, var16, 5.5F, 0.8F));
                SkijaDraw.c();
                SkijaDraw.e().clipPath(var21, ClipMode.INTERSECT, true);
                SkijaDraw.a(var2 + 44.0F, var3 + 24.0F, var16, 5.5F, new Color(0, 0, 0, 75));
                float var22 = var16 / 20.0F * this.c.c;
                float var23 = Math.min(255, (int)(255.0F - this.c.c / 20.0F * 190.0F));
                float var24 = Math.min(255, (int)(70.0F + this.c.c / 20.0F * 185.0F));
                SkijaDraw.a(var2 + 44.0F, var3 + 24.0F, var22, 5.5F, new Color((int)var23, (int)var24, 35, 225));
                SkijaDraw.d();
                var21.close();
                if (this.i.m() && this.f.c > 0.1F) {
                    Path var25 = new Path();
                    var25.addRRect(RRect.makeXYWH(var2 + 44.0F, var3 + 31.0F, var17, 4.0F, 0.6F));
                    SkijaDraw.c();
                    SkijaDraw.e().clipPath(var25, ClipMode.INTERSECT, true);
                    SkijaDraw.a(var2 + 44.0F, var3 + 31.0F, var17, 4.0F, new Color(0, 0, 0, 65));
                    float var26 = var17 / 8.0F * this.f.c;
                    SkijaDraw.a(var2 + 44.0F, var3 + 31.0F, var26, 4.0F, new Color(255, 215, 70, 235));
                    SkijaDraw.d();
                    var25.close();
                }

                SkijaDraw.d();
            } catch (Throwable var28) {
                try {
                    var20.close();
                } catch (Throwable var27) {
                    var28.addSuppressed(var27);
                }

                throw var28;
            }

            var20.close();
            this.g.c(var19);
            this.g.d(var5);
        }
    }

    @EventTarget
    public void onPreTick(EventRunTicks var1) {
        if (a.player != null && var1.type() == com.heypixel.heypixelmod.obsoverlay.events.api.b.a.a) {
            KillAura var2 = EixClient.a().g().a(KillAura.class);
            AbstractClientPlayer var3 = null;
            if (a.hitResult instanceof EntityHitResult var4 && var4.getEntity() instanceof AbstractClientPlayer var5) {
                var3 = var5;
            } else if (var2.m() && var2.c instanceof AbstractClientPlayer var6) {
                var3 = var6;
            }

            if (var3 != null) {
                this.j = var3;
                this.d.a = 255.0F;
                this.c.a = this.j.getHealth();
                this.f.a = this.j.getAbsorptionAmount();
                this.e.a = this.h.m() && this.j.hurtTime > 0 ? 140.0F : 0.0F;
            } else {
                this.d.a = 0.0F;
                this.e.a = 0.0F;
                this.f.a = 0.0F;
                this.j = null;
            }
        }
    }
}
