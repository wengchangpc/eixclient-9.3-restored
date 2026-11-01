package com.heypixel.heypixelmod.obsoverlay.events.impl;

public class aa implements com.heypixel.heypixelmod.obsoverlay.events.api.a.b {
    private boolean a;

    public aa(boolean var1) {
        this.a = var1;
    }

    public boolean a() {
        return this.a;
    }

    public void a(boolean var1) {
        this.a = var1;
    }

    @Override
    public boolean equals(Object var1) {
        if (var1 == this) {
            return true;
        } else {
            return !(var1 instanceof aa var2) ? false : var2.a(this) && this.a() == var2.a();
        }
    }

    protected boolean a(Object var1) {
        return var1 instanceof aa;
    }

    @Override
    public int hashCode() {
        byte var1 = 59;
        byte var2 = 1;
        return var2 * 59 + (this.a() ? 79 : 97);
    }

    @Override
    public String toString() {
        return "EventStayingOnGroundSurface(stay=" + this.a() + ")";
    }
}
