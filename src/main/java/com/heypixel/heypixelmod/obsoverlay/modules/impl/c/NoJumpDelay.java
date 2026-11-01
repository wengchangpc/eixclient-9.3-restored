package com.heypixel.heypixelmod.obsoverlay.modules.impl.c;

import com.heypixel.heypixelmod.mixin.O.accessors.LivingEntityAccessor;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.k;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.a;
import com.heypixel.heypixelmod.obsoverlay.modules.b;

@ModuleInfo(
    a = "NoJumpDelay",
    b = "无跳跃延迟",
    c = "Removes the delay when jumping",
    d = com.heypixel.heypixelmod.obsoverlay.modules.a.MOVEMENT
)
public class NoJumpDelay extends b {
    @EventTarget
    public void onMotion(k var1) {
        if (var1.b() == com.heypixel.heypixelmod.obsoverlay.events.api.b.a.a) {
            ((LivingEntityAccessor)a.player).setNoJumpDelay(0);
        }
    }
}
