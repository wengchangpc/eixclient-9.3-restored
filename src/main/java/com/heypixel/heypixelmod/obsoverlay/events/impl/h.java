package com.heypixel.heypixelmod.obsoverlay.events.impl;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;

public class h extends com.heypixel.heypixelmod.obsoverlay.events.api.a.a.a {
    private Packet<ClientGamePacketListener> b;

    public h(Packet<ClientGamePacketListener> var1) {
        this.b = var1;
    }

    public Packet<ClientGamePacketListener> b() {
        return this.b;
    }

    public void a(Packet<ClientGamePacketListener> var1) {
        this.b = var1;
    }
}
