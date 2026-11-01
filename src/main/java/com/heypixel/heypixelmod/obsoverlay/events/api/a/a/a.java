package com.heypixel.heypixelmod.obsoverlay.events.api.a.a;

import com.heypixel.heypixelmod.obsoverlay.events.api.a.Cancellable;

public abstract class a implements Cancellable, com.heypixel.heypixelmod.obsoverlay.events.api.a.b {
    public boolean a;

    protected a() {
    }

    @Override
    public boolean a() {
        return this.a;
    }

    @Override
    public void a(boolean var1) {
        this.a = var1;
    }
}
