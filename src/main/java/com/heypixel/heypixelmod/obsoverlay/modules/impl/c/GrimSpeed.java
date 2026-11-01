package com.heypixel.heypixelmod.obsoverlay.modules.impl.c;

import com.heypixel.heypixelmod.obsoverlay.EixClient;
import com.heypixel.heypixelmod.obsoverlay.d.d;
import com.heypixel.heypixelmod.obsoverlay.d.a.c;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventRunTicks;
import com.heypixel.heypixelmod.obsoverlay.events.impl.k;
import com.heypixel.heypixelmod.obsoverlay.events.impl.l;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.a;
import com.heypixel.heypixelmod.obsoverlay.modules.b;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.a.KillAura;
import com.heypixel.heypixelmod.obsoverlay.utils.ChatUtil;
import com.heypixel.heypixelmod.obsoverlay.utils.s;
import com.heypixel.heypixelmod.obsoverlay.utils.u;
import com.heypixel.heypixelmod.obsoverlay.utils.w;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket.Action;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

@ModuleInfo(
    a = "GrimSpeed",
    b = "严峻的嘻嘻哈哈",
    c = "Movement speed adjustments with Grim modes",
    d = com.heypixel.heypixelmod.obsoverlay.modules.a.MOVEMENT
)
public class GrimSpeed extends b {
    private final com.heypixel.heypixelmod.obsoverlay.d.a.a c = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Logging").a(false).a().b();
    private final com.heypixel.heypixelmod.obsoverlay.d.a.a d = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Fast Fall").a(true).a().b();
    private final com.heypixel.heypixelmod.obsoverlay.d.a.a e = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Fast Fall On 1 Tick")
        .a(false)
        .a(this.d::m)
        .a()
        .b();
    private final com.heypixel.heypixelmod.obsoverlay.d.a.a f = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Fast Fall On 2 Tick")
        .a(false)
        .a(this.d::m)
        .a()
        .b();
    private final com.heypixel.heypixelmod.obsoverlay.d.a.a g = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Fast Fall On 3 Tick")
        .a(false)
        .a(this.d::m)
        .a()
        .b();
    private final com.heypixel.heypixelmod.obsoverlay.d.a.a h = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Fast Fall On 4 Tick")
        .a(false)
        .a(this.d::m)
        .a()
        .b();
    private final com.heypixel.heypixelmod.obsoverlay.d.a.a i = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Fast Fall On 5 Tick")
        .a(false)
        .a(this.d::m)
        .a()
        .b();
    private final com.heypixel.heypixelmod.obsoverlay.d.a.a j = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Rotation").a(true).a().b();
    private final c k = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Speed").a(0.1F).b(0.0F).c(10.0F).d(0.01F).a().c();
    private final c l = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Start Fast Fall").a(2.0F).b(0.0F).c(10.0F).d(1.0F).a(this.d::m).a().c();
    private final c m = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Packet Count").a(1.0F).b(1.0F).c(10.0F).d(1.0F).a(this.d::m).a().c();
    private final c n = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Skip Ticks").a(2.0F).b(1.0F).c(10.0F).d(1.0F).a(this.d::m).a().c();
    private final c o = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Ticks").a(3.0F).b(1.0F).c(10.0F).d(1.0F).a(this.d::m).a().c();
    private int p;

    private void b(String var1) {
        if (this.c.m()) {
            ChatUtil.a(var1);
        }
    }

    @Override
    public void d() {
        this.p = 0;
    }

    @Override
    public void e() {
        this.p = 0;
    }

    private double p() {
        float var1 = a.player.input.forwardImpulse;
        float var2 = a.player.input.leftImpulse;
        float var3 = a.player.getYRot();
        if (var1 < 0.0F) {
            var3 += 180.0F;
        }

        float var4 = 1.0F;
        if (var1 < 0.0F) {
            var4 = -0.5F;
        } else if (var1 > 0.0F) {
            var4 = 0.5F;
        }

        if (var2 > 0.0F) {
            var3 -= 90.0F * var4;
        }

        if (var2 < 0.0F) {
            var3 += 90.0F * var4;
        }

        return Math.toRadians(var3);
    }

    private void a(double var1) {
        if (u.a()) {
            double var3 = this.p();
            Vec3 var5 = a.player.getDeltaMovement();
            a.player.setDeltaMovement(var5.x + -Math.sin(var3) * var1, var5.y, var5.z + Math.cos(var3) * var1);
        }
    }

    @EventTarget
    public void onMotion(k var1) {
        if (a.player != null) {
            if (this.d.m()) {
                if (var1.b() == com.heypixel.heypixelmod.obsoverlay.events.api.b.a.a) {
                    if (a.player.onGround()) {
                        this.p = 0;
                    } else {
                        this.p++;
                    }
                } else if (var1.b() == com.heypixel.heypixelmod.obsoverlay.events.api.b.a.b && !a.player.onGround() && this.p > this.l.q()) {
                    this.b("Air ticks: " + this.p);
                    switch (this.p - (int)this.l.q()) {
                        case 1:
                            if (this.e.m()) {
                                this.q();
                            }
                            break;
                        case 2:
                            if (this.f.m()) {
                                this.q();
                            }
                            break;
                        case 3:
                            if (this.g.m()) {
                                this.q();
                            }
                            break;
                        case 4:
                            if (this.h.m()) {
                                this.q();
                            }
                            break;
                        case 5:
                            if (this.i.m()) {
                            }
                    }
                }
            }
        }
    }

    private void q() {
        for (int var1 = 0; var1 < (int)this.m.q(); var1++) {
            w.a(new ServerboundPlayerCommandPacket(a.player, Action.START_FALL_FLYING));
        }

        a.options.keyShift.setDown(false);
        a.player.jumpFromGround();
        this.a(this.k.q() / 10.0);

        for (int var2 = 0; var2 < (int)this.o.q(); var2++) {
            a.player.tick();
        }

        EixClient.d = (int)(EixClient.d + this.n.q());
        this.b("fall");
    }

    @EventTarget
    public void onPreTick(EventRunTicks var1) {
        if (var1.type() == com.heypixel.heypixelmod.obsoverlay.events.api.b.a.a && this.j.m()) {
            Scaffold var2 = EixClient.a().g().a(Scaffold.class);
            KillAura var3 = EixClient.a().g().a(KillAura.class);
            float var4 = s.a(-1.0F, 1.0F);
            if (!a.player.onGround() && !var2.m() && var3.c == null) {
                com.heypixel.heypixelmod.obsoverlay.utils.c.c.a(
                    new com.heypixel.heypixelmod.obsoverlay.utils.c.b(Mth.wrapDegrees(a.player.getYRot() - 45.0F) + var4, a.player.getXRot() + var4),
                    360.0
                );
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
