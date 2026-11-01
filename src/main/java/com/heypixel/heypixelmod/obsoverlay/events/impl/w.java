package com.heypixel.heypixelmod.obsoverlay.events.impl;

import net.minecraft.network.protocol.Packet;

public class w implements com.heypixel.heypixelmod.obsoverlay.events.api.a.b {
    private Packet<?> a;

    public w(Packet<?> var1) {
        this.a = var1;
    }

    public Packet<?> a() {
        return this.a;
    }

    public void a(Packet<?> var1) {
        this.a = var1;
    }
}
