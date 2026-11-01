package com.heypixel.heypixelmod.obsoverlay.utils.renderer;

import net.minecraft.client.Minecraft;

public class e {
    public int a;
    public double b = 1.0;
    public int c;
    public int d;
    private int e;

    public e(double var1) {
        this.b = var1;
        this.e();
    }

    public e() {
        this.e();
    }

    private void e() {
        this.e = f.d();
        this.a();
        this.a = f.c();
        f.q(this.a);
        f.h();
        f.c(3553, 10242, 33071);
        f.c(3553, 10243, 33071);
        f.c(3553, 10241, 9729);
        f.c(3553, 10240, 9729);
        Minecraft var1 = Minecraft.getInstance();
        this.c = (int)(var1.getWindow().getWidth() * this.b);
        this.d = (int)(var1.getWindow().getHeight() * this.b);
        f.a(3553, 0, 6408, this.c, this.d, 0, 6408, 5121, null);
        f.a(36160, 36064, 3553, this.a, 0);
        this.c();
    }

    public void a() {
        f.j(this.e);
    }

    public void b() {
        f.a(0, 0, this.c, this.d);
    }

    public void c() {
        Minecraft.getInstance().getMainRenderTarget().bindWrite(false);
    }

    public void d() {
        f.e(this.e);
        f.d(this.a);
        this.e();
    }
}
