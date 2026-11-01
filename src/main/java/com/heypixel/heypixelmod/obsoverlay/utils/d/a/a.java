package com.heypixel.heypixelmod.obsoverlay.utils.d.a;

import com.heypixel.heypixelmod.obsoverlay.EixClient;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.PostProcess;
import com.heypixel.heypixelmod.obsoverlay.utils.am;
import com.heypixel.heypixelmod.obsoverlay.utils.d.Framebuffer;
import com.heypixel.heypixelmod.obsoverlay.utils.d.b;
import com.heypixel.heypixelmod.obsoverlay.utils.d.c;
import com.heypixel.heypixelmod.obsoverlay.utils.d.d;
import it.unimi.dsi.fastutil.ints.IntDoubleImmutablePair;
import net.minecraft.client.Minecraft;

public class a {
    public static final a a = new a();
    public static final a b = new a();
    private static final IntDoubleImmutablePair[] c = new IntDoubleImmutablePair[]{
        IntDoubleImmutablePair.of(1, 1.25),
        IntDoubleImmutablePair.of(1, 2.25),
        IntDoubleImmutablePair.of(2, 2.0),
        IntDoubleImmutablePair.of(2, 3.0),
        IntDoubleImmutablePair.of(2, 4.25),
        IntDoubleImmutablePair.of(3, 2.5),
        IntDoubleImmutablePair.of(3, 3.25),
        IntDoubleImmutablePair.of(3, 4.25),
        IntDoubleImmutablePair.of(3, 5.5),
        IntDoubleImmutablePair.of(4, 3.25),
        IntDoubleImmutablePair.of(4, 4.0),
        IntDoubleImmutablePair.of(4, 5.0),
        IntDoubleImmutablePair.of(4, 6.0),
        IntDoubleImmutablePair.of(4, 7.25),
        IntDoubleImmutablePair.of(4, 8.25),
        IntDoubleImmutablePair.of(5, 4.5),
        IntDoubleImmutablePair.of(5, 5.25),
        IntDoubleImmutablePair.of(5, 6.25),
        IntDoubleImmutablePair.of(5, 7.25),
        IntDoubleImmutablePair.of(5, 8.5)
    };
    private static c d;
    private static c e;
    private static c f;
    private final Framebuffer[] g = new Framebuffer[6];
    private final am h = new am();
    private boolean i = true;

    public void a() {
        for (int var1 = 0; var1 < this.g.length; var1++) {
            if (this.g[var1] != null) {
                this.g[var1].d();
            } else {
                this.g[var1] = new Framebuffer(1.0 / Math.pow(2.0, var1));
            }
        }
    }

    public void a(int var1) {
        if (d == null) {
            d = new c("blur.vert", "blur_down.frag");
            e = new c("blur.vert", "blur_up.frag");
            f = new c("passthrough.vert", "passthrough.frag");
        }

        if (this.i) {
            for (int var2 = 0; var2 < this.g.length; var2++) {
                if (this.g[var2] == null) {
                    this.g[var2] = new Framebuffer(1.0 / Math.pow(2.0, var2));
                }
            }

            this.i = false;
        }

        if (EixClient.a().g().a(PostProcess.class).r()) {
            if (!this.h.a((long)(1000 / EixClient.a().g().a(PostProcess.class).q()))) {
                return;
            }

            this.h.a();
        }

        IntDoubleImmutablePair var7 = c[var1 - 1];
        int var3 = var7.leftInt();
        double var4 = var7.rightDouble();
        com.heypixel.heypixelmod.obsoverlay.utils.d.b.a();
        this.a(this.g[0], Minecraft.getInstance().getMainRenderTarget().getColorTextureId(), d, var4);

        for (int var6 = 0; var6 < var3; var6++) {
            this.a(this.g[var6 + 1], this.g[var6].a, d, var4);
        }

        for (int var8 = var3; var8 >= 1; var8--) {
            this.a(this.g[var8 - 1], this.g[var8].a, e, var4);
        }

        Minecraft.getInstance().getMainRenderTarget().bindWrite(true);
        f.a();
        com.heypixel.heypixelmod.obsoverlay.utils.d.d.e(this.g[0].a);
        f.a("uTexture", 0);
        com.heypixel.heypixelmod.obsoverlay.utils.d.b.c();
    }

    public int b() {
        return this.g[0].a;
    }

    private void a(Framebuffer var1, int var2, c var3, double var4) {
        var1.a();
        var1.b();
        var3.a();
        com.heypixel.heypixelmod.obsoverlay.utils.d.d.e(var2);
        var3.a("uTexture", 0);
        var3.a("uHalfTexelSize", 0.5 / var1.c, 0.5 / var1.d);
        var3.a("uOffset", var4);
        com.heypixel.heypixelmod.obsoverlay.utils.d.b.b();
    }
}
