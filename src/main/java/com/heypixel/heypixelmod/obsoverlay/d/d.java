package com.heypixel.heypixelmod.obsoverlay.d;

import com.heypixel.heypixelmod.obsoverlay.d.a.a;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class d {
    private final NamedHolder a;
    private final String b;
    private f c;
    private Consumer<c> d;
    private Supplier<Boolean> e;
    private boolean f;
    private float g;
    private float h;
    private float i;
    private float j;
    private String[] k;
    private int l;
    private String m;
    private float n;
    private float o;
    private int p;

    private d(NamedHolder var1, String var2) {
        this.a = var1;
        this.b = var2;
    }

    public static d a(NamedHolder var0, String var1) {
        return new d(var0, var1);
    }

    public d a(f var1) {
        this.c = var1;
        return this;
    }

    public d a(boolean var1) {
        if (this.c == null) {
            this.a(com.heypixel.heypixelmod.obsoverlay.d.f.a);
        }

        if (this.c != com.heypixel.heypixelmod.obsoverlay.d.f.a) {
            throw new IllegalStateException("Value type is not boolean");
        } else {
            this.f = var1;
            return this;
        }
    }

    public d a(float var1) {
        if (this.c == null) {
            this.a(com.heypixel.heypixelmod.obsoverlay.d.f.b);
        }

        if (this.c != com.heypixel.heypixelmod.obsoverlay.d.f.b) {
            throw new IllegalStateException("Value type is not float");
        } else {
            this.g = var1;
            return this;
        }
    }

    public d b(float var1) {
        if (this.c == null) {
            this.a(com.heypixel.heypixelmod.obsoverlay.d.f.b);
        }

        if (this.c != com.heypixel.heypixelmod.obsoverlay.d.f.b) {
            throw new IllegalStateException("Value type is not float");
        } else {
            this.h = var1;
            return this;
        }
    }

    public d c(float var1) {
        if (this.c == null) {
            this.a(com.heypixel.heypixelmod.obsoverlay.d.f.b);
        }

        if (this.c != com.heypixel.heypixelmod.obsoverlay.d.f.b) {
            throw new IllegalStateException("Value type is not float");
        } else {
            this.i = var1;
            return this;
        }
    }

    public d d(float var1) {
        if (this.c == null) {
            this.a(com.heypixel.heypixelmod.obsoverlay.d.f.b);
        }

        if (this.c != com.heypixel.heypixelmod.obsoverlay.d.f.b) {
            throw new IllegalStateException("Value type is not float");
        } else {
            this.j = var1;
            return this;
        }
    }

    public d a(String... var1) {
        if (this.c == null) {
            this.a(com.heypixel.heypixelmod.obsoverlay.d.f.c);
        }

        if (this.c != com.heypixel.heypixelmod.obsoverlay.d.f.c) {
            throw new IllegalStateException("Value type is not mode");
        } else {
            this.k = var1;
            return this;
        }
    }

    public d a(int var1) {
        if (this.c == null) {
            this.a(com.heypixel.heypixelmod.obsoverlay.d.f.c);
        }

        if (this.c != com.heypixel.heypixelmod.obsoverlay.d.f.c) {
            throw new IllegalStateException("Value type is not mode");
        } else {
            this.l = var1;
            return this;
        }
    }

    public d a(String var1) {
        if (this.c == null) {
            this.a(com.heypixel.heypixelmod.obsoverlay.d.f.d);
        }

        if (this.c != com.heypixel.heypixelmod.obsoverlay.d.f.d) {
            throw new IllegalStateException("Value type is not string");
        } else {
            this.m = var1;
            return this;
        }
    }

    public d e(float var1) {
        if (this.c == null) {
            this.a(com.heypixel.heypixelmod.obsoverlay.d.f.e);
        }

        if (this.c != com.heypixel.heypixelmod.obsoverlay.d.f.e) {
            throw new IllegalStateException("Value type is not drag");
        } else {
            this.n = var1;
            return this;
        }
    }

    public d f(float var1) {
        if (this.c == null) {
            this.a(com.heypixel.heypixelmod.obsoverlay.d.f.e);
        }

        if (this.c != com.heypixel.heypixelmod.obsoverlay.d.f.e) {
            throw new IllegalStateException("Value type is not drag");
        } else {
            this.o = var1;
            return this;
        }
    }

    public d b(int var1) {
        if (this.c == null) {
            this.a(com.heypixel.heypixelmod.obsoverlay.d.f.f);
        }

        if (this.c != com.heypixel.heypixelmod.obsoverlay.d.f.f) {
            throw new IllegalStateException("Value type is not key");
        } else {
            this.p = var1;
            return this;
        }
    }

    public d a(Consumer<c> var1) {
        this.d = var1;
        return this;
    }

    public d a(Supplier<Boolean> var1) {
        this.e = var1;
        return this;
    }

    public c a() {
        if (this.c == null) {
            throw new IllegalStateException("Value type is not set");
        } else {
            switch (this.c) {
                case a:
                    return new a(this.a, this.b, this.f, this.d, this.e);
                case b:
                    return new com.heypixel.heypixelmod.obsoverlay.d.a.c(this.a, this.b, this.g, this.h, this.i, this.j, this.d, this.e);
                case c:
                    if (this.k == null) {
                        throw new IllegalStateException("Modes are not set");
                    }

                    return new com.heypixel.heypixelmod.obsoverlay.d.a.e(this.a, this.b, this.k, this.l, this.d, this.e);
                case d:
                    if (this.m == null) {
                        throw new IllegalStateException("Default string value is not set");
                    }

                    return new com.heypixel.heypixelmod.obsoverlay.d.a.f(this.a, this.b, this.m, this.d, this.e);
                case e:
                    return new com.heypixel.heypixelmod.obsoverlay.d.a.b(this.a, this.b, this.n, this.o, this.d, this.e);
                case f:
                    return new com.heypixel.heypixelmod.obsoverlay.d.a.d(this.a, this.b, this.p, this.d, this.e);
                default:
                    throw new IllegalStateException("Unknown value type");
            }
        }
    }
}
