package com.heypixel.heypixelmod.obsoverlay.events.impl;

import net.minecraft.network.protocol.Packet;

public class n extends com.heypixel.heypixelmod.obsoverlay.events.api.a.a.a {
    private Packet<?> b;

    public n(Packet<?> var1) {
        this.b = var1;
    }

    public Packet<?> b() {
        return this.b;
    }

    public void a(Packet<?> var1) {
        this.b = var1;
    }
}
