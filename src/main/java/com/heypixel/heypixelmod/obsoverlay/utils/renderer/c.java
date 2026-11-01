package com.heypixel.heypixelmod.obsoverlay.utils.renderer;

public enum c {
    a(2),
    b(3);

    public final int c;

    private c(int var3) {
        this.c = var3;
    }

    public int a() {
        if (this == a) {
            return 1;
        } else {
            return this == b ? 4 : 0;
        }
    }
}
