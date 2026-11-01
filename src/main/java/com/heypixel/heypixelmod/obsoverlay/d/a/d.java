package com.heypixel.heypixelmod.obsoverlay.d.a;

import com.heypixel.heypixelmod.obsoverlay.d.NamedHolder;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class d extends com.heypixel.heypixelmod.obsoverlay.d.c {
    private final int a;
    private final Consumer<com.heypixel.heypixelmod.obsoverlay.d.c> b;
    private int c;

    public d(NamedHolder var1, String var2, int var3, Consumer<com.heypixel.heypixelmod.obsoverlay.d.c> var4, Supplier<Boolean> var5) {
        super(var1, var2, var5);
        this.a = var3;
        this.c = var3;
        this.b = var4;
    }

    @Override
    public com.heypixel.heypixelmod.obsoverlay.d.f a() {
        return com.heypixel.heypixelmod.obsoverlay.d.f.f;
    }

    @Override
    public d g() {
        return this;
    }

    public int l() {
        return this.a;
    }

    public int m() {
        return this.c;
    }

    public void a(int var1) {
        this.c = var1;
        if (this.b != null) {
            this.b.accept(this);
        }
    }
}
