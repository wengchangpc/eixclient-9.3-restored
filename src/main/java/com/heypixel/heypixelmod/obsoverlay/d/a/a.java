package com.heypixel.heypixelmod.obsoverlay.d.a;

import com.heypixel.heypixelmod.obsoverlay.d.NamedHolder;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class a extends com.heypixel.heypixelmod.obsoverlay.d.c {
    private final boolean a;
    private final Consumer<com.heypixel.heypixelmod.obsoverlay.d.c> b;
    private boolean c;

    public a(NamedHolder var1, String var2, boolean var3, Consumer<com.heypixel.heypixelmod.obsoverlay.d.c> var4, Supplier<Boolean> var5) {
        super(var1, var2, var5);
        this.b = var4;
        this.c = this.a = var3;
    }

    @Override
    public com.heypixel.heypixelmod.obsoverlay.d.f a() {
        return com.heypixel.heypixelmod.obsoverlay.d.f.a;
    }

    @Override
    public a b() {
        return this;
    }

    public boolean l() {
        return this.a;
    }

    public boolean m() {
        return this.c;
    }

    public void a(boolean var1) {
        this.c = var1;
        if (this.b != null) {
            this.b.accept(this);
        }
    }
}
