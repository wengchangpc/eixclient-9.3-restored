package com.heypixel.heypixelmod.obsoverlay.utils;

import java.awt.Color;

public enum h {
    a(-16711423),
    b(-12028161),
    c(-12621684),
    d(-9830551),
    e(-9320847),
    f(-65794),
    g(-7820064),
    h(-12621684),
    i(-9868951),
    j(-14342875),
    k(-65536),
    l(-8388608),
    m(-29696),
    n(-2263808),
    o(-256),
    p(-2702025),
    q(-18751),
    r(-2252579);

    public int s;

    private h(int var3) {
        this.s = var3;
    }

    public static int a(Color var0) {
        return a(var0.getRed(), var0.getGreen(), var0.getBlue(), var0.getAlpha());
    }

    public static int a(int var0) {
        return a(var0, var0, var0, 255);
    }

    public static int a(int var0, int var1) {
        return a(var0, var0, var0, var1);
    }

    public static int a(int var0, int var1, int var2) {
        return a(var0, var1, var2, 255);
    }

    public static int a(int var0, int var1, int var2, int var3) {
        int var4 = 0;
        var4 |= var3 << 24;
        var4 |= var0 << 16;
        var4 |= var1 << 8;
        return var4 | var2;
    }
}
