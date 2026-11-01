package com.heypixel.heypixelmod.obsoverlay.c;

import com.heypixel.heypixelmod.obsoverlay.EixClient;
import com.heypixel.heypixelmod.obsoverlay.d.f;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.PostProcess;
import com.heypixel.heypixelmod.obsoverlay.utils.ad;
import com.heypixel.heypixelmod.obsoverlay.utils.d.a.a;
import com.heypixel.heypixelmod.obsoverlay.utils.e.SkijaDraw;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class b extends Screen {
    private final List<com.heypixel.heypixelmod.obsoverlay.d.a.b> a = new ArrayList<>();
    private com.heypixel.heypixelmod.obsoverlay.d.a.b b = null;
    private float c = 0.0F;
    private float d = 0.0F;

    public b() {
        super(Component.literal("HUD Editor"));
    }

    protected void a() {
        this.a.clear();
        com.heypixel.heypixelmod.obsoverlay.modules.c var1 = EixClient.a().g();

        for (com.heypixel.heypixelmod.obsoverlay.modules.b var3 : var1.a()) {
            for (com.heypixel.heypixelmod.obsoverlay.d.c var5 : EixClient.a().d().a()) {
                if (var5.i() == var3 && var5.a() == f.e) {
                    this.a.add(var5.f());
                }
            }
        }
    }

    public void a(GuiGraphics var1, int var2, int var3, float var4) {
        com.heypixel.heypixelmod.obsoverlay.utils.d.a.a.a.a(EixClient.a().g().a(PostProcess.class).p());
        com.heypixel.heypixelmod.obsoverlay.utils.e.a.a.a(var3x -> {
            SkijaDraw.c();
            SkijaDraw.a((float)Minecraft.getInstance().getWindow().getGuiScale());

            for (com.heypixel.heypixelmod.obsoverlay.d.a.b var5 : this.a) {
                float var6 = var5.n();
                float var7 = var5.o();
                float var8 = var5.p();
                float var9 = var5.q();
                float var10 = var8 <= 0.0F ? 80.0F : var8;
                float var11 = var9 <= 0.0F ? 20.0F : var9;
                if (this.b == var5) {
                    var5.a(var2 - this.c);
                    var5.b(var3 - this.d);
                    var6 = var5.n();
                    var7 = var5.o();
                }

                boolean var12 = ad.b(var2, var3, var6, var7, var10, var11);
                if (var12) {
                    SkijaDraw.a(var6 - 5.0F, var7 - 5.0F, var10 + 10.0F, var11 + 10.0F, 5.0F, 2.0F, new Color(255, 255, 255));
                }
            }

            SkijaDraw.d();
        });
        super.render(var1, var2, var3, var4);
    }

    public boolean a(double var1, double var3, int var5) {
        if (var5 == 0) {
            for (int var6 = this.a.size() - 1; var6 >= 0; var6--) {
                com.heypixel.heypixelmod.obsoverlay.d.a.b var7 = this.a.get(var6);
                float var8 = var7.p();
                float var9 = var7.q();
                float var10 = var8 <= 0.0F ? 80.0F : var8;
                float var11 = var9 <= 0.0F ? 20.0F : var9;
                if (ad.b((int)var1, (int)var3, var7.n(), var7.o(), var10, var11)) {
                    this.b = var7;
                    this.c = (float)var1 - var7.n();
                    this.d = (float)var3 - var7.o();
                    return true;
                }
            }
        }

        return super.mouseClicked(var1, var3, var5);
    }

    public boolean b(double var1, double var3, int var5) {
        if (var5 == 0 && this.b != null) {
            this.b = null;
            return true;
        } else {
            return super.mouseReleased(var1, var3, var5);
        }
    }

    public boolean a(double var1, double var3, int var5, double var6, double var8) {
        if (var5 == 0 && this.b != null) {
            this.b.a((float)var1 - this.c);
            this.b.b((float)var3 - this.d);
            return true;
        } else {
            return super.mouseDragged(var1, var3, var5, var6, var8);
        }
    }

    public void b() {
        EixClient.a().i().c();
        super.onClose();
    }

    public boolean c() {
        return false;
    }
}
