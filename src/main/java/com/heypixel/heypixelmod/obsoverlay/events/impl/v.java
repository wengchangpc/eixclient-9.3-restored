package com.heypixel.heypixelmod.obsoverlay.events.impl;

import net.minecraft.world.entity.Entity;

public class v implements com.heypixel.heypixelmod.obsoverlay.events.api.a.b {
    public static Entity a;
    private float b;
    private float c;
    private float d;
    private float e;

    public v(float var1, float var2, float var3, float var4) {
        this.b = var1;
        this.c = var2;
        this.d = var3;
        this.e = var4;
    }

    public float a() {
        return this.b;
    }

    public void a(float var1) {
        this.b = var1;
    }

    public float b() {
        return this.c;
    }

    public void b(float var1) {
        this.c = var1;
    }

    public float c() {
        return this.d;
    }

    public void c(float var1) {
        this.d = var1;
    }

    public float d() {
        return this.e;
    }

    public void d(float var1) {
        this.e = var1;
    }
}
