package com.heypixel.heypixelmod.obsoverlay.events.impl;

import net.minecraft.network.chat.Component;

public class t implements com.heypixel.heypixelmod.obsoverlay.events.api.a.b {
    private com.heypixel.heypixelmod.obsoverlay.events.api.b.a a;
    private Component b;

    public t(com.heypixel.heypixelmod.obsoverlay.events.api.b.a var1, Component var2) {
        this.a = var1;
        this.b = var2;
    }

    public com.heypixel.heypixelmod.obsoverlay.events.api.b.a a() {
        return this.a;
    }

    public void a(com.heypixel.heypixelmod.obsoverlay.events.api.b.a var1) {
        this.a = var1;
    }

    public Component b() {
        return this.b;
    }

    public void a(Component var1) {
        this.b = var1;
    }
}
