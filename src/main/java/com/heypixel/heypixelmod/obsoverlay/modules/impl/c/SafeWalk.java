package com.heypixel.heypixelmod.obsoverlay.modules.impl.c;

import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.k;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.a;
import com.heypixel.heypixelmod.obsoverlay.modules.b;
import com.mojang.blaze3d.platform.InputConstants;

@ModuleInfo(
    a = "SafeWalk",
    b = "安全行走",
    c = "Prevents you from falling off blocks",
    d = com.heypixel.heypixelmod.obsoverlay.modules.a.MOVEMENT
)
public class SafeWalk extends b {
    public static boolean a(float var0) {
        return !a.level.getCollisions(a.player, a.player.getBoundingBox().move(0.0, -0.5, 0.0).inflate(-var0, 0.0, -var0)).iterator().hasNext();
    }

    @EventTarget
    public void onMotion(k var1) {
        if (var1.b() == com.heypixel.heypixelmod.obsoverlay.events.api.b.a.a) {
            a.options.keyShift.setDown(a.player.onGround() && a(0.3F));
        }
    }

    @Override
    public void e() {
        boolean var1 = InputConstants.isKeyDown(a.getWindow().getWindow(), a.options.keyShift.getKey().getValue());
        a.options.keyShift.setDown(var1);
    }
}
