package com.heypixel.heypixelmod.obsoverlay.events.impl;

import com.mojang.blaze3d.vertex.PoseStack;

public class p implements com.heypixel.heypixelmod.obsoverlay.events.api.a.b {
    private final float a;
    private final PoseStack b;

    public p(float var1, PoseStack var2) {
        this.a = var1;
        this.b = var2;
    }

    public float a() {
        return this.a;
    }

    public PoseStack b() {
        return this.b;
    }
}
