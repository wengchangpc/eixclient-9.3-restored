package com.heypixel.heypixelmod.obsoverlay.events.impl;

import net.minecraft.network.protocol.Packet;

public class g extends com.heypixel.heypixelmod.obsoverlay.events.api.a.a.a {
    private final com.heypixel.heypixelmod.obsoverlay.events.api.b.a b;
    private Packet<?> c;

    public g(com.heypixel.heypixelmod.obsoverlay.events.api.b.a var1, Packet<?> var2) {
        this.b = var1;
        this.c = var2;
    }

    public com.heypixel.heypixelmod.obsoverlay.events.api.b.a b() {
        return this.b;
    }

    public Packet<?> c() {
        return this.c;
    }

    public void a(Packet<?> var1) {
        this.c = var1;
    }
}
