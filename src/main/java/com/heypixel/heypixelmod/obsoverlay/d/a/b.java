package com.heypixel.heypixelmod.obsoverlay.d.a;

import com.heypixel.heypixelmod.obsoverlay.d.NamedHolder;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class b extends com.heypixel.heypixelmod.obsoverlay.d.c {
    private final float a;
    private final float b;
    private final Consumer<com.heypixel.heypixelmod.obsoverlay.d.c> c;
    private float d;
    private float e;
    private float f;
    private float g;

    public b(NamedHolder var1, String var2, float var3, float var4, Consumer<com.heypixel.heypixelmod.obsoverlay.d.c> var5, Supplier<Boolean> var6) {
        super(var1, var2, var6);
        this.a = var3;
        this.b = var4;
        this.d = var3;
        this.e = var4;
        this.c = var5;
    }

    @Override
    public com.heypixel.heypixelmod.obsoverlay.d.f a() {
        return com.heypixel.heypixelmod.obsoverlay.d.f.e;
    }

    @Override
    public b f() {
        return this;
    }

    public float l() {
        return this.a;
    }

    public float m() {
        return this.b;
    }

    public float n() {
        return this.d;
    }

    public void a(float var1) {
        this.d = var1;
        if (this.c != null) {
            this.c.accept(this);
        }
    }

    public float o() {
        return this.e;
    }

    public void b(float var1) {
        this.e = var1;
        if (this.c != null) {
            this.c.accept(this);
        }
    }

    public float p() {
        return this.f;
    }

    public void c(float var1) {
        this.f = var1;
    }

    public float q() {
        return this.g;
    }

    public void d(float var1) {
        this.g = var1;
    }

    public void a(float var1, float var2) {
        this.d = var1;
        this.e = var2;
        if (this.c != null) {
            this.c.accept(this);
        }
    }
}
