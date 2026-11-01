package com.heypixel.heypixelmod.obsoverlay.d.a;

import com.heypixel.heypixelmod.obsoverlay.d.NamedHolder;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class e extends com.heypixel.heypixelmod.obsoverlay.d.c {
    private final String[] a;
    private final Consumer<com.heypixel.heypixelmod.obsoverlay.d.c> b;
    private int c;

    public e(NamedHolder var1, String var2, String[] var3, int var4, Consumer<com.heypixel.heypixelmod.obsoverlay.d.c> var5, Supplier<Boolean> var6) {
        super(var1, var2, var6);
        this.b = var5;
        this.a = var3;
        this.c = var4;
    }

    public boolean a(String var1) {
        return this.l().equalsIgnoreCase(var1);
    }

    @Override
    public com.heypixel.heypixelmod.obsoverlay.d.f a() {
        return com.heypixel.heypixelmod.obsoverlay.d.f.c;
    }

    @Override
    public e e() {
        return this;
    }

    public String l() {
        return this.a[this.c];
    }

    public String[] m() {
        return this.a;
    }

    public Consumer<com.heypixel.heypixelmod.obsoverlay.d.c> n() {
        return this.b;
    }

    public int o() {
        return this.c;
    }

    public void a(int var1) {
        this.c = var1;
        if (this.b != null) {
            this.b.accept(this);
        }
    }
}
