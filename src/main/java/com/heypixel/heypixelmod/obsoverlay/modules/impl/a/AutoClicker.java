package com.heypixel.heypixelmod.obsoverlay.modules.impl.a;

import com.heypixel.heypixelmod.mixin.O.accessors.MinecraftAccessor;
import com.heypixel.heypixelmod.obsoverlay.d.d;
import com.heypixel.heypixelmod.obsoverlay.d.a.c;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.k;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.a;
import com.heypixel.heypixelmod.obsoverlay.modules.b;
import com.heypixel.heypixelmod.obsoverlay.utils.s;
import net.minecraft.client.KeyMapping;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.phys.HitResult.Type;

@ModuleInfo(
    a = "AutoClicker",
    b = "自动点击",
    c = "Automatically clicks for you",
    d = com.heypixel.heypixelmod.obsoverlay.modules.a.COMBAT
)
public class AutoClicker extends b {
    private final c c = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Min CPS").a(12.0F).d(1.0F).b(1.0F).c(20.0F).a().c();
    private final c d = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Max CPS").a(18.0F).d(1.0F).b(1.0F).c(20.0F).a().c();
    private final com.heypixel.heypixelmod.obsoverlay.d.a.a e = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Item Check").a(false).a().b();
    private long f = 0L;

    @EventTarget
    public void onMotion(k var1) {
        if (var1.b() == com.heypixel.heypixelmod.obsoverlay.events.api.b.a.a) {
            MinecraftAccessor var2 = (MinecraftAccessor)a;
            Item var3 = a.player.getMainHandItem().getItem();
            if (a.options.keyAttack.isDown() && (var3 instanceof SwordItem || var3 instanceof AxeItem || !this.e.m()) && a.hitResult.getType() != Type.BLOCK) {
                long var4 = System.currentTimeMillis();
                double var6 = 1000.0 / s.a((int)this.c.q(), (int)this.d.q());
                long var8 = (long)(var6 + (Math.random() - 0.5) * var6 * 0.4);
                if (var4 - this.f >= var8) {
                    var2.setMissTime(0);
                    KeyMapping.click(a.options.keyAttack.getKey());
                    this.f = var4;
                }
            }
        }
    }
}
