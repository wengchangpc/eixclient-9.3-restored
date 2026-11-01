package com.heypixel.heypixelmod.obsoverlay.modules.impl.render;

import com.heypixel.heypixelmod.obsoverlay.d.d;
import com.heypixel.heypixelmod.obsoverlay.d.a.c;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.k;
import com.heypixel.heypixelmod.obsoverlay.events.impl.m;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.a;
import com.heypixel.heypixelmod.obsoverlay.modules.b;
import net.minecraft.network.protocol.game.ClientboundSetTimePacket;

@ModuleInfo(
    a = "TimeChanger",
    b = "时间更改器",
    c = "Change the time of the world",
    d = com.heypixel.heypixelmod.obsoverlay.modules.a.RENDER
)
public class TimeChanger extends b {
    c c = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "World Time").a(8000.0F).d(1.0F).b(0.0F).c(24000.0F).a().c();

    @EventTarget
    public void onMotion(k var1) {
        if (var1.b() == com.heypixel.heypixelmod.obsoverlay.events.api.b.a.a) {
            a.level.setDayTime((long)this.c.q());
        }
    }

    @EventTarget
    public void onPacket(m var1) {
        if (var1.c() instanceof ClientboundSetTimePacket) {
            var1.a(true);
        }
    }
}
