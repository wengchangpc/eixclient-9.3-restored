package com.heypixel.heypixelmod.obsoverlay.events.impl;

import net.minecraft.network.chat.Component;

public class x extends com.heypixel.heypixelmod.obsoverlay.events.api.a.a.a {
    private com.heypixel.heypixelmod.obsoverlay.events.api.b.a b;
    private Component c;

    public x(com.heypixel.heypixelmod.obsoverlay.events.api.b.a var1, Component var2) {
        this.b = var1;
        this.c = var2;
    }

    public com.heypixel.heypixelmod.obsoverlay.events.api.b.a b() {
        return this.b;
    }

    public void a(com.heypixel.heypixelmod.obsoverlay.events.api.b.a var1) {
        this.b = var1;
    }

    public Component c() {
        return this.c;
    }

    public void a(Component var1) {
        this.c = var1;
    }
}
