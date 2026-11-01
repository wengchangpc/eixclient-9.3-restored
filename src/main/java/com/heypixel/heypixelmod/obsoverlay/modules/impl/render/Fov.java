package com.heypixel.heypixelmod.obsoverlay.modules.impl.render;

import com.heypixel.heypixelmod.obsoverlay.d.d;
import com.heypixel.heypixelmod.obsoverlay.d.a.c;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.ae;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.a;
import com.heypixel.heypixelmod.obsoverlay.modules.b;

@ModuleInfo(
    a = "Fov",
    b = "视场角",
    c = "Change fov.",
    d = com.heypixel.heypixelmod.obsoverlay.modules.a.RENDER
)
public class Fov extends b {
    public c c = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Fov").a(120.0F).d(1.0F).b(1.0F).c(150.0F).a().c();

    @EventTarget
    public void onFovUpdate(ae var1) {
        var1.a(this.c.q() / 100.0F);
    }
}
