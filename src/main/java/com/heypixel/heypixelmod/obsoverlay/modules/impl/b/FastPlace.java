package com.heypixel.heypixelmod.obsoverlay.modules.impl.b;

import com.heypixel.heypixelmod.mixin.O.accessors.MinecraftAccessor;
import com.heypixel.heypixelmod.obsoverlay.d.d;
import com.heypixel.heypixelmod.obsoverlay.d.a.c;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.k;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.a;
import com.heypixel.heypixelmod.obsoverlay.modules.b;
import net.minecraft.world.item.BlockItem;

@ModuleInfo(
    a = "FastPlace",
    b = "快速放置",
    c = "Place blocks faster",
    d = com.heypixel.heypixelmod.obsoverlay.modules.a.MISC
)
public class FastPlace extends b {
    private final c c = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "CPS").a(10.0F).d(1.0F).b(5.0F).c(20.0F).a().c();
    private float d = 0.0F;

    @EventTarget
    public void onMotion(k var1) {
        if (var1.b() == com.heypixel.heypixelmod.obsoverlay.events.api.b.a.a) {
            MinecraftAccessor var2 = (MinecraftAccessor)a;
            if (a.options.keyUse.isDown() && a.player.getMainHandItem().getItem() instanceof BlockItem) {
                this.d = this.d + this.c.q() / 20.0F;
                if (this.d >= 1.0F / this.c.q()) {
                    var2.setRightClickDelay(0);
                    this.d--;
                }
            } else {
                this.d = 0.0F;
            }
        }
    }
}
