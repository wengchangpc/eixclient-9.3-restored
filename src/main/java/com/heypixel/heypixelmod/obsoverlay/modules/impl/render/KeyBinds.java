package com.heypixel.heypixelmod.obsoverlay.modules.impl.render;

import com.heypixel.heypixelmod.obsoverlay.EixClient;
import com.heypixel.heypixelmod.obsoverlay.d.d;
import com.heypixel.heypixelmod.obsoverlay.d.a.c;
import com.heypixel.heypixelmod.obsoverlay.d.a.e;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.s;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.a;
import com.heypixel.heypixelmod.obsoverlay.modules.b;
import com.heypixel.heypixelmod.obsoverlay.utils.ag;
import com.heypixel.heypixelmod.obsoverlay.utils.e.SkijaDraw;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.InputConstants.Key;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Font;
import io.github.humbleui.types.RRect;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

@ModuleInfo(
    a = "KeyBinds",
    b = "按键显示",
    c = "显示所有按键绑定",
    d = com.heypixel.heypixelmod.obsoverlay.modules.a.RENDER
)
public class KeyBinds extends b {
    private static final Color e = new Color(0, 0, 0, 100);
    private final com.heypixel.heypixelmod.obsoverlay.d.a.b f = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Position").e(10.0F).f(80.0F).a().f();
    public c c = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "KeyBinds Size").a(10.0F).d(1.0F).b(6.0F).c(24.0F).a().c();
    public e d = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Style").a("Style A", "Style B").a(0).a().e();
    private final Map<b, ag> g = new HashMap<>();
    private final Map<b, ag> h = new HashMap<>();
    private final Map<b, ag> i = new HashMap<>();
    private final Map<b, ag> j = new HashMap<>();
    private final ag k = new ag(100.0F, 100.0F, 0.2F);
    private final ag l = new ag(20.0F, 20.0F, 0.2F);

    @EventTarget
    public void onSkia(s var1) {
        if (this.d.a("Style A")) {
            this.q();
        } else if (this.d.a("Style B")) {
            this.p();
        }
    }

    private void p() {
        float var1 = this.c.q() * 1.0F;
        float var2 = this.c.q() * 0.9F;
        Font var3 = com.heypixel.heypixelmod.obsoverlay.utils.e.b.c.a(var1);
        Font var4 = com.heypixel.heypixelmod.obsoverlay.utils.e.b.c.b(var1);
        Font var5 = com.heypixel.heypixelmod.obsoverlay.utils.e.b.c.a(var2);
        java.util.ArrayList var6 = new java.util.ArrayList<>(EixClient.a().g().a());
        boolean var7 = a.screen instanceof com.heypixel.heypixelmod.obsoverlay.c.b;
        float var8 = var1 * 1.5F;
        float var9 = var2 * 1.5F;
        float var10 = this.c.q() / 3.0F;
        float var11 = var10 * 0.6F;
        float var12 = var10 * 2.0F;
        Color var13 = new Color(0, 0, 0, 70);
        boolean var14 = this.f.o() < a.getWindow().getGuiScaledHeight() / 2.0F;
        var6.sort((var4x, var5x) -> {
            float var6x = 0.0F;
            if (var4x != this && var4x.o() != 0) {
                float var7x = SkijaDraw.a(var4x.h(), var5);
                float var8x = SkijaDraw.a(this.c(var4x.o()), var5) + var10 * 2.0F;
                var6x = var7x + var10 * 2.0F + var10 + var8x;
            }

            float var10x = 0.0F;
            if (var5x != this && var5x.o() != 0) {
                float var11x = SkijaDraw.a(var5x.h(), var5);
                float var9x = SkijaDraw.a(this.c(var5x.o()), var5) + var10 * 2.0F;
                var10x = var11x + var10 * 2.0F + var10 + var9x;
            }

            return var14 ? Float.compare(var10x, var6x) : Float.compare(var6x, var10x);
        });
        java.util.ArrayList var15 = new java.util.ArrayList();

        for (b var17 : var6) {
            if (var17 != this && var17.o() != 0 && (var7 || !var17.m())) {
                var15.add(var17);
            }
        }

        String var44 = "Key Bind";
        float var45 = SkijaDraw.a(var44, var3);
        float var18 = SkijaDraw.a("\ue312", var4);
        float var19 = var45 + var10 * 4.0F + var18;

        for (b var21 : var15) {
            String var22 = var21.h();
            String var23 = this.c(var21.o());
            float var24 = SkijaDraw.a(var22, var5);
            float var25 = SkijaDraw.a(var23, var5);
            float var26 = var24 + var10 * 4.0F + var25;
            if (var26 > var19) {
                var19 = var26;
            }
        }

        float var46 = var19 + var10 * 2.0F;
        float var47 = var8 + var11 + var15.size() * var9 + var10 * 2.0F;
        this.k.a = var46;
        this.l.a = var47;
        this.k.a(true);
        this.l.a(true);
        float var48 = this.k.c;
        float var49 = this.l.c;
        float var50 = this.f.n();
        float var51 = this.f.o();
        SkijaDraw.c(var50, var51, var48, var49, var12);
        SkijaDraw.a(var50, var51, var48, var49, var12);
        SkijaDraw.a(var50, var51, var48, var49, var12, var13);
        Canvas var52 = SkijaDraw.e();
        int var27 = var52.save();
        RRect var28 = RRect.makeXYWH(var50, var51, var48, var49, var12);
        var52.clipRRect(var28, true);
        float var29 = var51 + var10 + var8 / 2.0F;
        SkijaDraw.c(var44, var50 + var10, var29, Color.WHITE, var3);
        SkijaDraw.c("\ue312", var50 + var48 - var10 - var18, var29, Color.WHITE, var4);
        HashMap var30 = new HashMap();
        float var31 = var10 + var8 + var11;

        for (b var33 : var15) {
            var30.put(var33, var31);
            var31 += var9;
        }

        for (b var54 : var6) {
            if (var54 != this && var54.o() != 0) {
                boolean var34 = var15.contains(var54);
                ag var35 = this.i.computeIfAbsent(var54, var0 -> new ag(0.0F, 0.0F, 0.2F));
                ag var36 = this.j.computeIfAbsent(var54, var0 -> new ag(0.0F, 0.0F, 0.2F));
                if (var34) {
                    var35.a = (Float)var30.get(var54);
                    var36.a = 255.0F;
                } else {
                    var35.a = var35.c;
                    var36.a = 0.0F;
                }

                var35.a(true);
                var36.a(true);
                float var37 = var36.c;
                if (!(var37 < 1.0F)) {
                    float var38 = var35.c;
                    float var39 = var51 + var38 + var9 / 2.0F;
                    Color var40 = new Color(255, 255, 255, (int)Math.min(255.0F, Math.max(0.0F, var37)));
                    String var41 = var54.h();
                    String var42 = this.c(var54.o());
                    SkijaDraw.c(var41, var50 + var10, var39, var40, var5);
                    float var43 = SkijaDraw.a(var42, var5);
                    SkijaDraw.c(var42, var50 + var48 - var10 - var43, var39, var40, var5);
                }
            }
        }

        var52.restoreToCount(var27);
        this.f.c(var48);
        this.f.d(var49);
    }

    private void q() {
        float var1 = this.c.q() * 1.0F;
        float var2 = this.c.q() * 0.9F;
        Font var3 = com.heypixel.heypixelmod.obsoverlay.utils.e.b.c.a(var1);
        Font var4 = com.heypixel.heypixelmod.obsoverlay.utils.e.b.c.b(var1);
        Font var5 = com.heypixel.heypixelmod.obsoverlay.utils.e.b.c.a(var2);
        java.util.ArrayList var6 = new java.util.ArrayList<>(EixClient.a().g().a());
        boolean var7 = a.screen instanceof com.heypixel.heypixelmod.obsoverlay.c.b;
        float var8 = var1 * 1.5F;
        float var9 = var2 * 1.5F;
        float var10 = this.c.q() / 3.0F;
        float var11 = var10 * 0.6F;
        boolean var12 = this.f.o() < a.getWindow().getGuiScaledHeight() / 2.0F;
        var6.sort((var4x, var5x) -> {
            float var6x = 0.0F;
            if (var4x != this && var4x.o() != 0) {
                float var7x = SkijaDraw.a(var4x.h(), var5);
                float var8x = SkijaDraw.a(this.c(var4x.o()), var5) + var10 * 2.0F;
                var6x = var7x + var10 * 2.0F + var10 + var8x;
            }

            float var10x = 0.0F;
            if (var5x != this && var5x.o() != 0) {
                float var11x = SkijaDraw.a(var5x.h(), var5);
                float var9x = SkijaDraw.a(this.c(var5x.o()), var5) + var10 * 2.0F;
                var10x = var11x + var10 * 2.0F + var10 + var9x;
            }

            return var12 ? Float.compare(var10x, var6x) : Float.compare(var6x, var10x);
        });
        String var13 = "Key Binds";
        float var14 = SkijaDraw.a(var13, var3);
        float var15 = SkijaDraw.a("\ue312", var4);
        float var16 = var15 + var10 + var14 + var10 * 2.0F;
        float var17 = var16;

        for (b var19 : var6) {
            if (var19 != this) {
                int var20 = var19.o();
                if (var20 != 0) {
                    String var21 = var19.h();
                    String var22 = this.c(var20);
                    float var23 = SkijaDraw.a(var21, var5);
                    float var24 = SkijaDraw.a(var22, var5) + var10 * 2.0F;
                    float var25 = var23 + var10 * 2.0F;
                    float var26 = var25 + var10 + var24;
                    if (var26 > var17) {
                        var17 = var26;
                    }
                }
            }
        }

        boolean var45 = this.f.n() >= a.getWindow().getGuiScaledWidth() / 2.0F;
        float var46 = this.f.o();
        float var48 = var45 ? this.f.n() + var17 - var16 : this.f.n();
        SkijaDraw.c(var48, var46, var16, var8, var10);
        SkijaDraw.a(var48, var46, var16, var8, var10);
        SkijaDraw.a(var48, var46, var16, var8, var10, e);
        float var49 = var48 + var10;
        float var50 = var49 + var15 + var10;
        float var51 = var46 + var8 / 2.0F;
        SkijaDraw.c("\ue312", var49, var51, Color.WHITE, var4);
        SkijaDraw.c(var13, var50, var51, Color.WHITE, var3);
        var46 += var8 + var10 + var11;

        for (b var53 : var6) {
            if (var53 != this) {
                int var54 = var53.o();
                if (var54 != 0) {
                    String var27 = var53.h();
                    String var28 = this.c(var54);
                    float var29 = SkijaDraw.a(var27, var5);
                    float var30 = SkijaDraw.a(var28, var5) + var10 * 2.0F;
                    float var31 = var29 + var10 * 2.0F;
                    float var32 = var31 + var10 + var30;
                    float var33 = var45 ? this.f.n() + var17 - var32 : this.f.n();
                    float var34 = var45 ? this.f.n() + var17 + var32 : this.f.n() - var32 - var10;
                    ag var35 = this.g.get(var53);
                    if (var35 == null) {
                        var35 = new ag(var34, var34, 0.2F);
                        this.g.put(var53, var35);
                    }

                    ag var36 = this.h.get(var53);
                    if (var36 == null) {
                        var36 = new ag(var46, var46, 0.2F);
                        this.h.put(var53, var36);
                    }

                    ag var37 = this.j.get(var53);
                    if (var37 == null) {
                        var37 = new ag(0.0F, 0.0F, 0.25F);
                        this.j.put(var53, var37);
                    }

                    boolean var38 = var7 || !var53.m();
                    var35.a = var38 ? var33 : var34;
                    var35.a(true);
                    var37.a = var38 ? 255.0F : 0.0F;
                    var37.a(true);
                    if (var38) {
                        var36.a = var46;
                        var36.a(true);
                        var46 += var9 + var10;
                    } else {
                        var36.a = var36.c;
                        var36.a(true);
                    }

                    if (var38 || !var37.b(true) || !var35.b(true)) {
                        float var39 = Math.min(1.0F, var37.c / 255.0F);
                        if (!(var39 <= 0.01F)) {
                            float var40 = var35.c;
                            float var41 = var36.c;
                            float var42 = var40 + var31 + var10;
                            if (var39 > 0.1F) {
                                SkijaDraw.c(var40, var41, var31, var9, var10);
                                SkijaDraw.a(var40, var41, var31, var9, var10);
                                SkijaDraw.c(var42, var41, var30, var9, var10);
                                SkijaDraw.a(var42, var41, var30, var9, var10);
                            }

                            Color var43 = new Color(0, 0, 0, Math.min(255, Math.max(0, (int)(e.getAlpha() * var39))));
                            Color var44 = new Color(255, 255, 255, Math.min(255, Math.max(0, (int)(255.0F * var39))));
                            SkijaDraw.a(var40, var41, var31, var9, var10, var43);
                            SkijaDraw.a(var42, var41, var30, var9, var10, var43);
                            SkijaDraw.a(var27, var40 + var10, var41 + var10, var44, var5);
                            SkijaDraw.a(var28, var42 + var10, var41 + var10, var44, var5);
                        }
                    }
                }
            }
        }

        this.f.c(var17);
        this.f.d(var46);
    }

    private String c(int var1) {
        if (var1 == 0) {
            return "NONE";
        } else if (var1 < 0) {
            return "M" + -var1;
        } else {
            Key var2 = InputConstants.getKey(var1, 0);
            if (var2 == InputConstants.UNKNOWN) {
                return "NONE";
            } else {
                String var3 = var2.getDisplayName().getString();
                return var3 != null && !var3.isEmpty() ? var3.toUpperCase() : "NONE";
            }
        }
    }
}
