package com.heypixel.heypixelmod.obsoverlay.d.a;

import com.heypixel.heypixelmod.obsoverlay.d.NamedHolder;
import com.heypixel.heypixelmod.obsoverlay.utils.s;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class c extends com.heypixel.heypixelmod.obsoverlay.d.c {
    private final float a;
    private final float b;
    private final float c;
    private final float d;
    private final Consumer<com.heypixel.heypixelmod.obsoverlay.d.c> e;
    private float f;

    public c(
        NamedHolder var1,
        String var2,
        float var3,
        float var4,
        float var5,
        float var6,
        Consumer<com.heypixel.heypixelmod.obsoverlay.d.c> var7,
        Supplier<Boolean> var8
    ) {
        super(var1, var2, var8);
        this.e = var7;
        this.f = this.a = var3;
        this.b = var4;
        this.c = var5;
        this.d = var6;
    }

    @Override
    public com.heypixel.heypixelmod.obsoverlay.d.f a() {
        return com.heypixel.heypixelmod.obsoverlay.d.f.b;
    }

    @Override
    public c c() {
        return this;
    }

    public float l() {
        return this.a;
    }

    public float m() {
        return this.b;
    }

    public float n() {
        return this.c;
    }

    public float o() {
        return this.d;
    }

    public Consumer<com.heypixel.heypixelmod.obsoverlay.d.c> p() {
        return this.e;
    }

    public float q() {
        return this.f;
    }

    public void a(float var1) {
        this.f = s.b(var1, this.b, this.c);
        if (this.e != null) {
            this.e.accept(this);
        }
    }
}
