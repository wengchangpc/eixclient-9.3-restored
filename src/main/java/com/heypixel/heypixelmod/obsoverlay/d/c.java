package com.heypixel.heypixelmod.obsoverlay.d;

import com.heypixel.heypixelmod.obsoverlay.EixClient;
import com.heypixel.heypixelmod.obsoverlay.d.a.a;
import java.util.function.Supplier;

public abstract class c {
    private final NamedHolder a;
    private final String b;
    private final Supplier<Boolean> c;

    protected c(NamedHolder var1, String var2, Supplier<Boolean> var3) {
        this.a = var1;
        this.b = var2;
        this.c = var3;
        EixClient.a().d().a(this);
    }

    public abstract f a();

    public a b() {
        throw new com.heypixel.heypixelmod.obsoverlay.a.a();
    }

    public com.heypixel.heypixelmod.obsoverlay.d.a.c c() {
        throw new com.heypixel.heypixelmod.obsoverlay.a.a();
    }

    public com.heypixel.heypixelmod.obsoverlay.d.a.f d() {
        throw new com.heypixel.heypixelmod.obsoverlay.a.a();
    }

    public com.heypixel.heypixelmod.obsoverlay.d.a.e e() {
        throw new com.heypixel.heypixelmod.obsoverlay.a.a();
    }

    public com.heypixel.heypixelmod.obsoverlay.d.a.b f() {
        throw new com.heypixel.heypixelmod.obsoverlay.a.a();
    }

    public com.heypixel.heypixelmod.obsoverlay.d.a.d g() {
        throw new com.heypixel.heypixelmod.obsoverlay.a.a();
    }

    public boolean h() {
        return this.c == null || this.c.get();
    }

    public NamedHolder i() {
        return this.a;
    }

    public String j() {
        return this.b;
    }

    public Supplier<Boolean> k() {
        return this.c;
    }
}
