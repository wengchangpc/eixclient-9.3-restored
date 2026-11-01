package com.heypixel.heypixelmod.obsoverlay.modules.impl.c;

import com.heypixel.heypixelmod.obsoverlay.EixClient;
import com.heypixel.heypixelmod.obsoverlay.d.d;
import com.heypixel.heypixelmod.obsoverlay.d.a.c;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventRunTicks;
import com.heypixel.heypixelmod.obsoverlay.events.impl.ad;
import com.heypixel.heypixelmod.obsoverlay.events.impl.l;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.a;
import com.heypixel.heypixelmod.obsoverlay.modules.b;
import com.heypixel.heypixelmod.obsoverlay.utils.ChatUtil;

@ModuleInfo(
    a = "GrimLowHop",
    b = "严峻的嘻嘻哈哈",
    c = "Movement speed adjustments with Grim modes",
    d = com.heypixel.heypixelmod.obsoverlay.modules.a.MOVEMENT
)
public class GrimLowHop extends b {
    private final com.heypixel.heypixelmod.obsoverlay.d.a.a c = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Logging").a(false).a().b();
    private final c d = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Start Tick").a(2.0F).b(0.0F).c(10.0F).d(1.0F).a().c();
    private final c e = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Skip Ticks").a(2.0F).b(1.0F).c(10.0F).d(1.0F).a().c();
    private final c f = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Ticks").a(3.0F).b(1.0F).c(10.0F).d(1.0F).a().c();
    private int g;
    private boolean h;
    private boolean i;

    private void b(String var1) {
        if (this.c.m()) {
            ChatUtil.a(var1);
        }
    }

    @Override
    public void d() {
        this.g = 0;
        this.h = false;
    }

    @Override
    public void e() {
        this.g = 0;
        this.h = false;
    }

    @EventTarget
    public void onPreTick(EventRunTicks var1) {
        if (a.player != null && var1.type() == com.heypixel.heypixelmod.obsoverlay.events.api.b.a.a) {
            if (this.g >= this.d.q() && !this.h) {
                EixClient.d = (int)(EixClient.d + this.e.q());
                this.h = true;
                this.i = false;
            }
        }
    }

    @EventTarget
    public void onUpdate(ad var1) {
        if (a.player.onGround()) {
            this.g = 0;
            this.h = false;
        } else {
            this.g++;
        }

        if (this.h && !this.i) {
            this.i = true;

            for (int var2 = 0; var2 < (int)this.f.q(); var2++) {
                a.player.tick();
            }
        }
    }

    @EventTarget
    public void onMoveInput(l var1) {
        if (a.player != null && a.level != null) {
            var1.a(true);
        }
    }
}
