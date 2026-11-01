package com.heypixel.heypixelmod.obsoverlay.utils.a;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;

public final class c {
    private static final Minecraft a = Minecraft.getInstance();
    private static final double b = 1.0E-7;
    private static final double c = 1.0 + Math.random() * 1.0E-7;
    private static float d = Float.NaN;
    private static float e = Float.NaN;
    private static float f = Float.NaN;
    private static float g = Float.NaN;
    private static float h = Float.NaN;
    private static float i = Float.NaN;

    private c() {
    }

    public static double a() {
        double var0 = (Double)a.options.sensitivity().get();
        double var2 = var0 * c * 0.6 + 0.2;
        return var2 * var2 * var2 * 8.0 * 0.15;
    }

    public static double b() {
        double var0 = (Double)a.options.sensitivity().get();
        double var2 = var0 * 0.6 + 0.2;
        return var2 * var2 * var2 * 8.0 * 0.15;
    }

    public static float a(float var0, float var1) {
        if (var0 == d && var1 == f && !Float.isNaN(h)) {
            return h;
        } else {
            double var2 = a();
            float var4;
            if (var2 <= 0.0) {
                var4 = var1;
            } else {
                double var5 = Mth.wrapDegrees(var1 - var0);
                var4 = var0 + (float)(Math.round(var5 / var2) * var2);
            }

            d = var0;
            f = var1;
            h = var4;
            return var4;
        }
    }

    public static float b(float var0, float var1) {
        if (var0 == e && var1 == g && !Float.isNaN(i)) {
            return i;
        } else {
            double var2 = a();
            float var4;
            if (var2 <= 0.0) {
                var4 = Mth.clamp(var1, -90.0F, 90.0F);
            } else {
                double var5 = var1 - var0;
                var4 = Mth.clamp(var0 + (float)(Math.round(var5 / var2) * var2), -90.0F, 90.0F);
            }

            e = var0;
            g = var1;
            i = var4;
            return var4;
        }
    }

    public static void c() {
        d = Float.NaN;
        e = Float.NaN;
        f = Float.NaN;
        g = Float.NaN;
        h = Float.NaN;
        i = Float.NaN;
    }

    public static com.heypixel.heypixelmod.obsoverlay.utils.c.b a(
        com.heypixel.heypixelmod.obsoverlay.utils.c.b var0, com.heypixel.heypixelmod.obsoverlay.utils.c.b var1
    ) {
        if (var1 == null) {
            return null;
        } else {
            return var0 == null
                ? new com.heypixel.heypixelmod.obsoverlay.utils.c.b(var1.a(), Mth.clamp(var1.b(), -90.0F, 90.0F))
                : new com.heypixel.heypixelmod.obsoverlay.utils.c.b(a(var0.a(), var1.a()), b(var0.b(), var1.b()));
        }
    }

    public static boolean a(float var0, double var1) {
        double var3 = b();
        if (var3 <= 0.0) {
            return true;
        } else {
            double var5 = Math.abs(var0 / var3 - Math.round(var0 / var3));
            return var5 <= var1;
        }
    }
}
