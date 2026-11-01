package com.heypixel.heypixelmod.obsoverlay.modules.impl.render;

import com.heypixel.heypixelmod.obsoverlay.EixClient;
import com.heypixel.heypixelmod.obsoverlay.d.d;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.s;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.a;
import com.heypixel.heypixelmod.obsoverlay.modules.b;

@ModuleInfo(
    a = "Notification",
    b = "通知",
    c = "",
    d = com.heypixel.heypixelmod.obsoverlay.modules.a.RENDER
)
public class Notification extends b {
    public com.heypixel.heypixelmod.obsoverlay.d.a.b c = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Position").e(10.0F).f(50.0F).a().f();

    @EventTarget
    public void onRenderSkia(s var1) {
        EixClient.a().j().a(var1, this.c);
    }
}
