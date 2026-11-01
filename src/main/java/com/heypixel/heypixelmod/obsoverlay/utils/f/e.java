package com.heypixel.heypixelmod.obsoverlay.utils.f;

import lombok.Generated;

public class e {
    public int a;
    public int b;
    public int c;

    public e(int var1, int var2, int var3) {
        this.a = var1;
        this.b = var2;
        this.c = var3;
    }

    public e a(int var1, int var2, int var3) {
        return new e(this.a + var1, this.b + var2, this.c + var3);
    }

    public e a(e var1) {
        return this.a(var1.a, var1.b, var1.c);
    }

    public e b(int var1, int var2, int var3) {
        return this.a(-var1, -var2, -var3);
    }

    public e b(e var1) {
        return this.a(-var1.a, -var1.b, -var1.c);
    }

    public double a() {
        return Math.sqrt(this.a * this.a + this.b * this.b + this.c * this.c);
    }

    public int b() {
        return this.a;
    }

    public int c() {
        return this.b;
    }

    public int d() {
        return this.c;
    }

    public d a(double var1) {
        return new d(this.a * var1, this.b * var1, this.c * var1);
    }

    public double c(e var1) {
        return Math.sqrt(Math.pow(var1.a - this.a, 2.0) + Math.pow(var1.b - this.b, 2.0) + Math.pow(var1.c - this.c, 2.0));
    }

    @Override
    public boolean equals(Object var1) {
        return !(var1 instanceof d var2) ? false : this.a == Math.floor(var2.a) && this.b == Math.floor(var2.b) && this.c == Math.floor(var2.c);
    }

    @Generated
    public void a(int var1) {
        this.a = var1;
    }

    @Generated
    public void b(int var1) {
        this.b = var1;
    }

    @Generated
    public void c(int var1) {
        this.c = var1;
    }
}
