package com.heypixel.heypixelmod.obsoverlay.c.a;

import com.heypixel.heypixelmod.obsoverlay.utils.ag;
import com.heypixel.heypixelmod.obsoverlay.utils.e.SkijaDraw;
import java.awt.Color;
import java.util.Objects;

public class a {
    public static byte[] a;
    private b b;
    private String c;
    private long d;
    private long e = System.currentTimeMillis();
    private ag f = new ag(0.0F);
    private ag g = new ag(0.0F);

    public a(b var1, String var2, long var3) {
        this.b = var1;
        this.c = var2;
        this.d = var3;
    }

    public void a(float var1, float var2) {
        SkijaDraw.b(var1 + 2.0F, var2 + 4.0F, this.a(), 20.0F, 5.0F, this.b.a());
        SkijaDraw.a(var1 + 2.0F, var2 + 4.0F, this.a(), 20.0F, 5.0F);
        SkijaDraw.a(var1 + 2.0F, var2 + 4.0F, this.a(), 20.0F, 5.0F, new Color(0, 0, 0, 100));
        SkijaDraw.a(this.c, var1 + 6.0F, var2 + 9.0F, Color.WHITE, com.heypixel.heypixelmod.obsoverlay.utils.e.b.c.a(12.0F));
    }

    public float a() {
        float var1 = SkijaDraw.a(this.c, com.heypixel.heypixelmod.obsoverlay.utils.e.b.c.a(12.0F));
        return var1 + 12.0F;
    }

    public float b() {
        return 24.0F;
    }

    public b c() {
        return this.b;
    }

    public void a(b var1) {
        this.b = var1;
    }

    public String d() {
        return this.c;
    }

    public void a(String var1) {
        this.c = var1;
    }

    public long e() {
        return this.d;
    }

    public void a(long var1) {
        this.d = var1;
    }

    public long f() {
        return this.e;
    }

    public void b(long var1) {
        this.e = var1;
    }

    public ag g() {
        return this.f;
    }

    public void a(ag var1) {
        this.f = var1;
    }

    public ag h() {
        return this.g;
    }

    public void b(ag var1) {
        this.g = var1;
    }

    @Override
    public boolean equals(Object var1) {
        if (var1 == this) {
            return true;
        } else {
            return !(var1 instanceof a var2) ? false : this.d == var2.d && this.e == var2.e && Objects.equals(this.b, var2.b) && Objects.equals(this.c, var2.c);
        }
    }

    protected boolean a(Object var1) {
        return var1 instanceof a;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.b, this.c, this.d, this.e);
    }

    @Override
    public String toString() {
        return "Notification(level="
            + this.c()
            + ", message="
            + this.d()
            + ", maxAge="
            + this.e()
            + ", createTime="
            + this.f()
            + ", widthTimer="
            + this.g()
            + ", heightTimer="
            + this.h()
            + ")";
    }
}
