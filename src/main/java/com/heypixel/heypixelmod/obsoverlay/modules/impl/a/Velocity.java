package com.heypixel.heypixelmod.obsoverlay.modules.impl.a;

import com.heypixel.heypixelmod.obsoverlay.d.d;
import com.heypixel.heypixelmod.obsoverlay.d.a.c;
import com.heypixel.heypixelmod.obsoverlay.d.a.e;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventRunTicks;
import com.heypixel.heypixelmod.obsoverlay.events.impl.l;
import com.heypixel.heypixelmod.obsoverlay.events.impl.m;
import com.heypixel.heypixelmod.obsoverlay.events.impl.p;
import com.heypixel.heypixelmod.obsoverlay.events.impl.s;
import com.heypixel.heypixelmod.obsoverlay.events.impl.u;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.b;
import com.heypixel.heypixelmod.obsoverlay.utils.ad;
import com.heypixel.heypixelmod.obsoverlay.utils.ag;
import com.heypixel.heypixelmod.obsoverlay.utils.e.SkijaDraw;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.humbleui.skija.ClipMode;
import io.github.humbleui.skija.Font;
import io.github.humbleui.skija.Path;
import io.github.humbleui.types.RRect;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundDisconnectPacket;
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
import net.minecraft.network.protocol.game.ClientboundPingPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerLookAtPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.network.protocol.game.ClientboundRespawnPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.protocol.game.ClientboundSetHealthPacket;
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

@ModuleInfo(
    a = "Velocity",
    b = "反击退",
    c = "Reduces knockback.",
    d = com.heypixel.heypixelmod.obsoverlay.modules.a.COMBAT
)
public class Velocity extends b {
    private final e d = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Mode").a("Legit", "Reduce", "NoXZ").a(0).a().e();
    private final Queue<Packet<? super ClientPacketListener>> e = new ConcurrentLinkedQueue<>();
    private final c f = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "AttackCounts").a(3.0F).d(1.0F).b(1.0F).c(5.0F).a(() -> this.d.a("NoXZ")).a().c();
    private final c g = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "MaxAlinkTime (ms)")
        .a(5000.0F)
        .d(50.0F)
        .b(50.0F)
        .c(10000.0F)
        .a(() -> this.d.a("NoXZ"))
        .a()
        .c();
    private final c h = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Horizontal %").a(35.0F).d(5.0F).b(0.0F).c(100.0F).a(() -> this.d.a("Reduce")).a().c();
    private final c i = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Vertical %").a(100.0F).d(5.0F).b(0.0F).c(100.0F).a(() -> this.d.a("Reduce")).a().c();
    private final c j = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Jump Window (ms)")
        .a(280.0F)
        .d(20.0F)
        .b(60.0F)
        .c(600.0F)
        .a(() -> this.d.a("Legit"))
        .a()
        .c();
    private final com.heypixel.heypixelmod.obsoverlay.d.a.b k = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "AlinkHUD Position").e(0.0F).f(0.0F).a().f();
    private final ag l = new ag(1.0F, 0.0F, 0.2F);
    private final ag m = new ag(0.0F, 0.0F, 0.12F);
    private final Map<Entity, com.heypixel.heypixelmod.obsoverlay.utils.f.d> n = new HashMap<>();
    public boolean c;
    private boolean o;
    private long p;
    private Vec3 q;
    private long r;
    private long s;
    private Player t;
    private Velocity.a u;
    private boolean v;
    private static final int w = 64;

    private void a(Velocity.a var1) {
        this.u = var1;
        this.s = System.currentTimeMillis();
    }

    private boolean a(long var1) {
        return System.currentTimeMillis() - this.s > var1;
    }

    @EventTarget
    public void onPacket(m var1) {
        String var2 = this.d.l();
        switch (var2) {
            case "Legit":
                if (var1.c() instanceof ClientboundSetEntityMotionPacket var16 && var16.getId() == a.player.getId()) {
                    this.o = true;
                    this.p = System.currentTimeMillis();
                }
                break;
            case "Reduce":
                if (var1.c() instanceof ClientboundSetEntityMotionPacket var15 && var15.getId() == a.player.getId()) {
                    double var21 = this.h.q() / 100.0;
                    double var24 = this.i.q() / 100.0;
                    if (var21 >= 0.999 && var24 >= 0.999) {
                        return;
                    }

                    Vec3 var9 = new Vec3(var15.getXa() / 8000.0 * var21, var15.getYa() / 8000.0 * var24, var15.getZa() / 8000.0 * var21);
                    var1.a(new ClientboundSetEntityMotionPacket(var15.getId(), var9));
                }
                break;
            case "NoXZ":
                if (var1.c() instanceof ClientboundPlayerPositionPacket && this.u == Velocity.a.a) {
                    this.c = true;
                    return;
                }

                if (var1.c() instanceof ClientboundSetEntityMotionPacket var4 && var4.getId() == a.player.getId()) {
                    if (this.u == Velocity.a.a) {
                        if (!this.c) {
                            this.a(Velocity.a.b);
                            this.r = System.currentTimeMillis();
                            var1.a(true);
                            this.q = new Vec3(var4.getXa() / 8000.0, var4.getYa() / 8000.0, var4.getZa() / 8000.0);
                        } else {
                            this.c = false;
                        }

                        return;
                    }

                    this.q = new Vec3(var4.getXa() / 8000.0, var4.getYa() / 8000.0, var4.getZa() / 8000.0);
                    this.a(Velocity.a.e);
                    var1.a(true);
                    return;
                }

                if (this.u != Velocity.a.a && var1.b() == com.heypixel.heypixelmod.obsoverlay.events.api.b.a.d) {
                    Packet var14 = var1.c();
                    if (var14 instanceof ClientboundPlayerPositionPacket) {
                        while (!this.e.isEmpty()) {
                            Packet var19 = this.e.poll();
                            if (var19 != null && a.getConnection() != null) {
                                var19.handle(a.getConnection());
                            }
                        }

                        this.b(false);
                        return;
                    }

                    if (var14 instanceof ClientboundPlayerLookAtPacket) {
                        this.a(Velocity.a.e);
                        return;
                    }

                    if (var14 instanceof ClientboundDisconnectPacket || var14 instanceof ClientboundRespawnPacket) {
                        this.b(false);
                        return;
                    }

                    if (!(var14 instanceof ClientboundPingPacket)
                        && !(var14 instanceof ClientboundMoveEntityPacket)
                        && !(var14 instanceof ClientboundTeleportEntityPacket)
                        && !(var14 instanceof ClientboundSetEntityMotionPacket)
                        && !(var14 instanceof ClientboundSetHealthPacket)) {
                        return;
                    }

                    if (var14 instanceof ClientboundMoveEntityPacket var17) {
                        Entity var6 = var17.getEntity(a.level);
                        if (var6 != null) {
                            com.heypixel.heypixelmod.obsoverlay.utils.f.d var7 = this.n
                                .getOrDefault(var6, new com.heypixel.heypixelmod.obsoverlay.utils.f.d(var6.getX(), var6.getY(), var6.getZ()));
                            if (var17.hasPosition()) {
                                double var8 = var17.getXa() / 4096.0;
                                double var10 = var17.getYa() / 4096.0;
                                double var12 = var17.getZa() / 4096.0;
                                this.n.put(var6, new com.heypixel.heypixelmod.obsoverlay.utils.f.d(var7.b() + var8, var7.c() + var10, var7.d() + var12));
                            }
                        }
                    }

                    if (var14 instanceof ClientboundTeleportEntityPacket var18) {
                        Entity var23 = a.level.getEntity(var18.getId());
                        if (var23 != null) {
                            this.n.put(var23, new com.heypixel.heypixelmod.obsoverlay.utils.f.d(var18.getX(), var18.getY(), var18.getZ()));
                        }
                    }

                    if (this.e.size() >= 64) {
                        this.b(false);
                        return;
                    }

                    this.e.add(var14);
                    var1.a(true);
                }
        }
    }

    @EventTarget
    public void onRespawn(u var1) {
        this.b(true);
        if (this.m()) {
            this.a(false);
        }
    }

    @EventTarget
    public void onPreTick(EventRunTicks var1) {
        if (a.player != null) {
            if (var1.type() == com.heypixel.heypixelmod.obsoverlay.events.api.b.a.b && this.u == Velocity.a.d) {
                this.b(true);
            }

            if (var1.type() == com.heypixel.heypixelmod.obsoverlay.events.api.b.a.b && this.u == Velocity.a.e) {
                a.player.setDeltaMovement(this.q.x, this.q.y, this.q.z);
                this.b(true);
            }

            if (var1.type() == com.heypixel.heypixelmod.obsoverlay.events.api.b.a.a) {
                String var2 = this.d.l();
                switch (var2) {
                    case "NoXZ":
                        if (this.u != Velocity.a.c) {
                            if (this.u == Velocity.a.b && (float)(System.currentTimeMillis() - this.r) >= this.g.q()) {
                                a.player.setDeltaMovement(this.q.x, this.q.y, this.q.z);
                                this.a(Velocity.a.d);
                            }
                        } else if (a.hitResult instanceof EntityHitResult var4 && var4.getEntity() instanceof Player var5 && !AntiBots.b(var5)) {
                            double var10 = 1.0;

                            for (int var8 = 0; var8 < (int)this.f.q(); var8++) {
                                if (a.player.isSprinting()) {
                                    a.player.setSprinting(false);
                                }

                                a.gameMode.attack(a.player, this.t);
                                a.player.swing(InteractionHand.MAIN_HAND);
                                var10 *= 0.6;
                            }

                            a.player.setDeltaMovement(this.q.x * var10, this.q.y, this.q.z * var10);
                            this.a(Velocity.a.d);
                        } else if (this.a(500L)) {
                            a.player.setDeltaMovement(this.q.x, this.q.y, this.q.z);
                            this.a(Velocity.a.d);
                        }

                        if (this.c && a.player.hurtTime == 0) {
                            this.c = false;
                        }
                        break;
                    case "Legit":
                        if (this.o && System.currentTimeMillis() - this.p > (long)this.j.q()) {
                            this.o = false;
                        }
                }

                this.a(this.d.l() + (this.u == Velocity.a.b ? " Alink " + (System.currentTimeMillis() - this.r) / 50L + "Ticks" : ""));
            }
        }
    }

    public void b(boolean var1) {
        this.c = false;
        this.u = Velocity.a.a;
        this.n.clear();
        this.t = null;
        if (!var1) {
            this.e.clear();
        } else {
            while (!this.e.isEmpty()) {
                Packet var2 = this.e.poll();
                if (var2 != null && a.getConnection() != null) {
                    var2.handle(a.getConnection());
                }
            }
        }
    }

    public boolean p() {
        return this.d.a("NoXZ") && this.u != Velocity.a.a;
    }

    @Override
    public void d() {
        this.o = false;
        this.p = 0L;
        this.c = false;
        this.n.clear();
        this.t = null;
        this.e.clear();
        this.q = null;
        this.a(Velocity.a.a);
    }

    @EventTarget
    public void onMoveInput(l var1) {
        String var2 = this.d.l();
        switch (var2) {
            case "NoXZ":
                if (this.u == Velocity.a.b
                    && this.q != null
                    && a.hitResult instanceof EntityHitResult var4
                    && var4.getEntity() instanceof Player var5
                    && !AntiBots.b(var5)) {
                    var1.a(1.0F);
                    var1.b(0.0F);
                    this.a(Velocity.a.c);
                    this.t = var5;
                }
                break;
            case "Legit":
                if (this.o) {
                    if (a.player.onGround() && com.heypixel.heypixelmod.obsoverlay.utils.u.a()) {
                        var1.a(true);
                    }

                    this.o = false;
                }
        }
    }

    @Override
    public void e() {
        this.o = false;
        this.c = false;
        this.n.clear();
        this.t = null;
        this.q = null;
        this.b(true);
        this.a(Velocity.a.a);
    }

    @EventTarget
    public void onRender(p var1) {
        if (this.u != Velocity.a.a) {
            PoseStack var2 = var1.b();

            for (Entity var4 : this.n.keySet()) {
                if (var4 instanceof Player) {
                    com.heypixel.heypixelmod.obsoverlay.utils.f.d var5 = this.n.get(var4);
                    if (var4.equals(this.t)) {
                        ad.a(var2, var5.b(), var5.c(), var5.d(), var4.getBbWidth(), var4.getBbHeight(), new Color(200, 0, 0, 60).getRGB());
                    } else {
                        ad.a(var2, var5.b(), var5.c(), var5.d(), var4.getBbWidth(), var4.getBbHeight(), new Color(0, 200, 0, 60).getRGB());
                    }
                }
            }
        }
    }

    @EventTarget
    public void onRenderSkia(s var1) {
        boolean var2 = a.screen instanceof com.heypixel.heypixelmod.obsoverlay.c.b;
        boolean var3 = this.d.a("NoXZ");
        boolean var4 = this.u != null && this.u != Velocity.a.a;
        boolean var5 = var2 || this.m() && var3;
        this.l.a = 1.0F;
        this.l.a(var5);
        if (!(this.l.c <= 0.01F)) {
            Font var6 = com.heypixel.heypixelmod.obsoverlay.utils.e.b.c.a(10.0F);
            long var7 = System.currentTimeMillis();
            long var9 = (long)this.g.q();
            long var11 = var4 && this.u == Velocity.a.b ? Math.max(0L, var7 - this.r) : 0L;
            float var13;
            if (var2) {
                var13 = 0.6F;
            } else if (var9 <= 0L) {
                var13 = 0.0F;
            } else if (var4 && this.u == Velocity.a.b) {
                var13 = Math.min(1.0F, (float)var11 / (float)var9);
            } else if (var4 && this.u == Velocity.a.c) {
                var13 = 1.0F;
            } else {
                var13 = 0.0F;
            }

            this.m.a = var13;
            this.m.a(true);
            String var14 = "Alink";
            float var15 = SkijaDraw.a(var14, var6);
            float var16 = 32.0F;
            float var17 = 120.0F;
            float var18 = Math.max(var17, var15 + 20.0F);
            float var19 = 10.0F;
            if (!this.v && this.k.n() == this.k.l() && this.k.o() == this.k.m()) {
                float var20 = a.getWindow().getGuiScaledWidth();
                float var21 = a.getWindow().getGuiScaledHeight();
                float var22 = var20 / 2.0F - var18 / 2.0F;
                float var23 = var21 - 55.0F;
                this.k.a(var22, var23);
                this.v = true;
            }

            float var36 = this.k.n();
            float var37 = this.k.o();
            int var38 = Math.min(255, Math.max(0, (int)(80.0F * this.l.c)));
            Color var39 = new Color(0, 0, 0, var38);
            SkijaDraw.c(var36, var37, var18, var16, var19);
            SkijaDraw.a(var36, var37, var18, var16, var19);
            SkijaDraw.a(var36, var37, var18, var16, var19, var39);
            float var24 = 5.0F;
            float var25 = 6.0F;
            float var26 = var36 + var24;
            float var27 = var37 + var16 - var24 - var25;
            float var28 = var18 - var24 * 2.0F;
            float var29 = var28 * this.m.c;
            float var30 = var25 / 2.0F;
            Path var31 = new Path();
            var31.addRRect(RRect.makeXYWH(var26, var27, var28, var25, var30));
            SkijaDraw.c();
            SkijaDraw.e().clipPath(var31, ClipMode.INTERSECT, true);
            SkijaDraw.a(var26, var27, var28, var25, var30, new Color(255, 255, 255, 50));
            if (var29 > 0.5F) {
                SkijaDraw.a(var26, var27, var29, var25, var30, new Color(196, 128, 224, 220));
            }

            SkijaDraw.d();
            var31.close();
            float var32 = var36 + var18 / 2.0F;
            float var33 = var37 + 7.0F;
            SkijaDraw.d(var14, var32, var33, new Color(255, 255, 255, (int)(220.0F * this.l.c)), var6);
            String var34 = Math.round(this.m.c * 100.0F) + "%";
            float var35 = var37 + 17.0F;
            SkijaDraw.d(var34, var36 + var18 / 2.0F, var35, new Color(255, 255, 255, (int)(230.0F * this.l.c)), var6);
            this.k.c(var18);
            this.k.d(var16);
        }
    }

    private static enum a {
        a,
        b,
        c,
        d,
        e;
    }
}
