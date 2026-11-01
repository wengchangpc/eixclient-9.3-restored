package com.heypixel.heypixelmod.obsoverlay.modules.impl.render;

import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.a;
import com.heypixel.heypixelmod.obsoverlay.modules.b;

@ModuleInfo(
    a = "HUDEditor",
    b = "HUD编辑器",
    d = com.heypixel.heypixelmod.obsoverlay.modules.a.RENDER,
    c = "HUD editor screen."
)
public class HUDEditor extends b {
    com.heypixel.heypixelmod.obsoverlay.c.b c = null;

    @Override
    protected void c() {
        super.c();
        this.b(345);
    }

    @Override
    public void d() {
        if (this.c == null) {
            this.c = new com.heypixel.heypixelmod.obsoverlay.c.b();
        }

        a.setScreen(this.c);
        this.f();
    }
}
