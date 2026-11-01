package com.heypixel.heypixelmod.obsoverlay.modules.impl.c;

import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.k;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.a;
import com.heypixel.heypixelmod.obsoverlay.modules.b;

@ModuleInfo(
    a = "Sprint",
    b = "自动疾跑",
    c = "Automatically sprints",
    d = com.heypixel.heypixelmod.obsoverlay.modules.a.MOVEMENT
)
public class Sprint extends b {
    @EventTarget(
        a = 0
    )
    public void onMotion(k var1) {
        if (var1.b() == com.heypixel.heypixelmod.obsoverlay.events.api.b.a.a) {
            a.options.keySprint.setDown(true);
            a.options.toggleSprint().set(false);
        }
    }

    @Override
    public void e() {
        a.options.keySprint.setDown(false);
    }
}
