package com.heypixel.heypixelmod.obsoverlay.utils.e.b;

public enum b {
    a("ttf"),
    b("otf");

    private final String c;

    private b(String var3) {
        this.c = var3;
    }

    public static b a(String var0) {
        String var1 = var0.toLowerCase();

        return switch (var1) {
            case "ttf" -> a;
            case "otf" -> b;
            default -> throw new IllegalArgumentException("Unsupported font type: " + var0);
        };
    }

    @Override
    public String toString() {
        return this.c;
    }
}
