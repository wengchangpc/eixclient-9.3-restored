package com.heypixel.heypixelmod.obsoverlay.modules.impl.a;

import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.a;
import com.heypixel.heypixelmod.obsoverlay.modules.b;

@ModuleInfo(
    a = "KeepSprint",
    b = "保持疾跑",
    c = "Maintain a sprinting state while attacking.",
    d = com.heypixel.heypixelmod.obsoverlay.modules.a.COMBAT
)
public class KeepSprint extends b {
    @EventTarget
    public void onAttackSlowdown(com.heypixel.heypixelmod.obsoverlay.events.impl.b var1) {
        var1.a(true);
    }
}
