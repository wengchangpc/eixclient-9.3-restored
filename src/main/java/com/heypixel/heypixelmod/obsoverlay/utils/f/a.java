package com.heypixel.heypixelmod.obsoverlay.utils.f;

import lombok.Generated;

public final class a {
    public double a;
    public double b;

    public a() {
    }

    public a(double var1, double var3) {
        this.a = var1;
        this.b = var3;
    }

    public a a(double var1, double var3) {
        return new a(this.a + var1, this.b + var3);
    }

    public a a(a var1) {
        return this.a(var1.a, var1.b);
    }

    @Generated
    public double a() {
        return this.a;
    }

    @Generated
    public double b() {
        return this.b;
    }

    @Generated
    public void a(double var1) {
        this.a = var1;
    }

    @Generated
    public void b(double var1) {
        this.b = var1;
    }
}
