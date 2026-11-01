package com.heypixel.heypixelmod.obsoverlay.d.a;

import com.heypixel.heypixelmod.obsoverlay.d.NamedHolder;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class f extends com.heypixel.heypixelmod.obsoverlay.d.c {
    private final String a;
    private final Consumer<com.heypixel.heypixelmod.obsoverlay.d.c> b;
    private String c;

    public f(NamedHolder var1, String var2, String var3, Consumer<com.heypixel.heypixelmod.obsoverlay.d.c> var4, Supplier<Boolean> var5) {
        super(var1, var2, var5);
        this.b = var4;
        this.a = var3;
        this.c = var3;
    }

    @Override
    public com.heypixel.heypixelmod.obsoverlay.d.f a() {
        return com.heypixel.heypixelmod.obsoverlay.d.f.d;
    }

    @Override
    public f d() {
        return this;
    }

    public String l() {
        return this.a;
    }

    public Consumer<com.heypixel.heypixelmod.obsoverlay.d.c> m() {
        return this.b;
    }

    public String n() {
        return this.c;
    }

    public void a(String var1) {
        if (var1 != null) {
            this.c = var1;
            if (this.b != null) {
                this.b.accept(this);
            }
        }
    }
}
