package com.heypixel.heypixelmod.obsoverlay.modules.impl.c;

import com.heypixel.heypixelmod.mixin.O.accessors.MinecraftAccessor;
import com.heypixel.heypixelmod.obsoverlay.d.d;
import com.heypixel.heypixelmod.obsoverlay.d.a.c;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.k;
import com.heypixel.heypixelmod.obsoverlay.events.impl.l;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.a;
import com.heypixel.heypixelmod.obsoverlay.modules.b;
import com.heypixel.heypixelmod.obsoverlay.utils.ak;
import com.heypixel.heypixelmod.obsoverlay.utils.s;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.BlockItem;

@ModuleInfo(
    a = "Eagle",
    b = "安全蹲搭",
    c = "Legit trick to build faster. Auto-sneak near edges.",
    d = com.heypixel.heypixelmod.obsoverlay.modules.a.MOVEMENT
)
public class Eagle extends b {
    private final c c = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "MinDelay (Ticks)").a(2.0F).d(1.0F).b(0.0F).c(10.0F).a().c();
    private final c d = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "MaxDelay (Ticks)").a(3.0F).d(1.0F).b(0.0F).c(10.0F).a().c();
    private final com.heypixel.heypixelmod.obsoverlay.d.a.a e = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "OnlyBackwards").a(false).a().b();
    private final com.heypixel.heypixelmod.obsoverlay.d.a.a f = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "OnlyWithBlocks").a(false).a().b();
    private final com.heypixel.heypixelmod.obsoverlay.d.a.a g = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "FastPlace").a(false).a().b();
    private final c h = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "CPS").a(10.0F).d(1.0F).b(5.0F).c(20.0F).a(this.g::m).a().c();
    private static final ak i = new ak();
    private float j = 0.0F;
    private boolean k;
    private float l = 0.0F;

    public static boolean a(float var0) {
        return !a.level.getCollisions(a.player, a.player.getBoundingBox().move(0.0, -0.5, 0.0).inflate(-var0, 0.0, -var0)).iterator().hasNext();
    }

    @EventTarget
    public void onMoveInput(l var1) {
        LocalPlayer var2 = a.player;
        if (var2 != null) {
            if (var1.a() != 0.0F) {
                this.j = var1.a();
            }

            if (this.e.m() && this.j > 0.0F) {
                this.k = false;
            } else if (!this.f.m() || !a.player.getMainHandItem().isEmpty() && a.player.getMainHandItem().getItem() instanceof BlockItem) {
                this.k = true;
                if (var2.onGround()) {
                    boolean var3 = a(0.3F);
                    if (!var2.getAbilities().flying && var3) {
                        i.b();
                    }

                    if (!i.a(s.a((int)this.d.q(), (int)this.c.q()))) {
                        var1.b(true);
                    }
                }
            } else {
                this.k = false;
            }
        }
    }

    @EventTarget
    public void onMotion(k var1) {
        if (this.k || !i.a(s.a((int)this.d.q(), (int)this.c.q()))) {
            if (var1.b() == com.heypixel.heypixelmod.obsoverlay.events.api.b.a.a) {
                MinecraftAccessor var2 = (MinecraftAccessor)a;
                if (a.options.keyUse.isDown() && a.player.getMainHandItem().getItem() instanceof BlockItem) {
                    this.l = this.l + this.h.q() / 20.0F;
                    if (this.l >= 1.0F / this.h.q()) {
                        var2.setRightClickDelay(0);
                        this.l--;
                    }
                } else {
                    this.l = 0.0F;
                }
            }
        }
    }

    @Override
    public void d() {
        this.j = 0.0F;
    }

    @Override
    public void e() {
        this.j = 0.0F;
    }
}
