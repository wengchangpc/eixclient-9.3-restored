package com.heypixel.heypixelmod.obsoverlay.utils.f;

import lombok.Generated;

public final class b {
    public float a;
    public float b;

    public b(b var1) {
        this(var1.a, var1.b);
    }

    public b a(float var1, float var2) {
        return new b(this.a + var1, this.b + var2);
    }

    @Generated
    public float a() {
        return this.a;
    }

    @Generated
    public float b() {
        return this.b;
    }

    @Generated
    public void a(float var1) {
        this.a = var1;
    }

    @Generated
    public void b(float var1) {
        this.b = var1;
    }

    @Generated
    public b(float var1, float var2) {
        this.a = var1;
        this.b = var2;
    }
}
