package com.heypixel.heypixelmod.obsoverlay.modules.impl.a;

import com.heypixel.heypixelmod.obsoverlay.EixClient;
import com.heypixel.heypixelmod.obsoverlay.d.d;
import com.heypixel.heypixelmod.obsoverlay.d.a.c;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventRunTicks;
import com.heypixel.heypixelmod.obsoverlay.events.impl.p;
import com.heypixel.heypixelmod.obsoverlay.events.impl.u;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.a;
import com.heypixel.heypixelmod.obsoverlay.modules.b;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.ClientFriend;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.Target;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.Teams;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Blink;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland;
import com.heypixel.heypixelmod.obsoverlay.utils.ad;
import com.heypixel.heypixelmod.obsoverlay.utils.m;
import com.mojang.blaze3d.vertex.PoseStack;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;

@ModuleInfo(
    a = "KillAura",
    b = "杀戮光环",
    c = "Automatically attacks entities",
    d = com.heypixel.heypixelmod.obsoverlay.modules.a.COMBAT
)
public class KillAura extends b {
    public Entity c;
    c d = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Aim Range").a(5.0F).d(0.1F).b(1.0F).c(6.0F).a().c();
    com.heypixel.heypixelmod.obsoverlay.d.a.a e = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "1.9 Mode").a(false).a().b();
    c f = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "CPS").a(10.0F).d(1.0F).b(1.0F).c(30.0F).a(() -> !this.e.m()).a().c();
    c g = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Rotation Speed").a(45.0F).d(1.0F).b(1.0F).c(180.0F).a().c();
    c h = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "FOV").a(360.0F).d(1.0F).b(0.0F).c(360.0F).a().c();
    c i = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Drift").a(0.1F).d(0.1F).b(0.0F).c(5.0F).a().c();
    c j = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Jitter").a(0.02F).d(0.01F).b(0.0F).c(1.0F).a().c();
    c k = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Reach").a(3.0F).d(0.1F).b(1.0F).c(4.5F).a().c();
    com.heypixel.heypixelmod.obsoverlay.d.a.a l = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Sprint Reset").a(false).a().b();
    c m = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Sprint Reset Chance").a(0.35F).d(0.05F).b(0.0F).c(1.0F).a(this.l::m).a().c();
    com.heypixel.heypixelmod.obsoverlay.d.a.a n = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Raycast Check").a(true).a().b();
    private static final long o = 55L;
    private static final long p = 600L;
    private static final int q = 4;
    private List<Entity> r = new ArrayList<>();
    private long s = 0L;
    private long t = 0L;
    private long u = 0L;
    private int v = -1;
    private int w = 0;
    private int x = -10;
    private Random y;
    private double z;
    private double A;
    private double B;
    private double C;
    private double D;
    private double E;
    private double F;
    private double G;
    private double H;

    public KillAura() {
        this.q();
    }

    @Override
    public void d() {
        this.q();
        this.r.clear();
        this.c = null;
        this.x = -10;
        this.s = 0L;
        this.t = 0L;
        this.u = 0L;
        this.v = -1;
        this.w = 0;
        com.heypixel.heypixelmod.obsoverlay.utils.a.d.e();
    }

    @Override
    public void e() {
        this.c = null;
        this.x = -10;
        this.r.clear();
        com.heypixel.heypixelmod.obsoverlay.utils.a.d.d();
        com.heypixel.heypixelmod.obsoverlay.utils.c.c.b(this);
    }

    private void q() {
        this.y = new Random(System.nanoTime());
        this.z = 0.0;
        this.A = this.y.nextDouble() * 0.3 + 0.1;
        this.B = this.y.nextDouble() * 0.5 + 0.5;
        this.C = this.y.nextDouble() * 0.3 + 0.1;
        this.D = this.y.nextDouble() * 0.5 + 0.5;
        this.E = this.y.nextDouble() * Math.PI * 2.0;
        this.F = this.y.nextDouble() * Math.PI * 2.0;
        this.G = this.y.nextDouble() * Math.PI * 2.0;
        this.H = this.y.nextDouble() * Math.PI * 2.0;
    }

    private com.heypixel.heypixelmod.obsoverlay.utils.c.b a(
        com.heypixel.heypixelmod.obsoverlay.utils.c.b var1, com.heypixel.heypixelmod.obsoverlay.utils.c.b var2, float var3
    ) {
        if (var1 != null && var2 != null) {
            float var4 = Mth.wrapDegrees(var2.a() - var1.a());
            float var5 = var2.b() - var1.b();
            double var6 = this.g.q();
            double var8 = this.i.q();
            double var10 = this.j.q();
            if (var6 <= 0.0) {
                return var2;
            } else {
                float var12 = var4 * var3;
                float var13 = var5 * var3;
                double var14 = Math.sqrt(var12 * var12 + var13 * var13);
                if (var14 < var8) {
                    return new com.heypixel.heypixelmod.obsoverlay.utils.c.b(var1.a() + var12, var1.b() + var13);
                } else {
                    if (var14 > 0.0) {
                        double var16 = Math.abs(var12) / var14;
                        double var18 = Math.abs(var13) / var14;
                        double var20 = var6 * var16 * var3;
                        double var22 = var6 * var18 * var3;
                        var12 = Mth.clamp(var12, (float)(-var20), (float)var20);
                        var13 = Mth.clamp(var13, (float)(-var22), (float)var22);
                    }

                    this.z += var3;
                    double var32 = Math.sin(this.z * this.A + this.E) + (this.y.nextDouble() * 0.1 + 0.45) * Math.sin(this.z * this.B + this.F);
                    double var33 = Math.sin(this.z * this.C + this.G) + (this.y.nextDouble() * 0.1 + 0.45) * Math.sin(this.z * this.D + this.H);
                    double var34 = var32 * var8 * var3;
                    double var35 = var33 * var8 * var3;
                    double var24 = (this.y.nextDouble() * 2.0 - 1.0) * var10 * var3;
                    double var26 = (this.y.nextDouble() * 2.0 - 1.0) * var10 * var3;
                    float var28 = var12 + (float)var34 + (float)var24;
                    float var29 = var13 + (float)var35 + (float)var26;
                    float var30 = var1.a() + var28;
                    float var31 = Mth.clamp(var1.b() + var29, -90.0F, 90.0F);
                    return a(new com.heypixel.heypixelmod.obsoverlay.utils.c.b(var30, var31), var1);
                }
            }
        } else {
            return var2;
        }
    }

    private static com.heypixel.heypixelmod.obsoverlay.utils.c.b a(
        com.heypixel.heypixelmod.obsoverlay.utils.c.b var0, com.heypixel.heypixelmod.obsoverlay.utils.c.b var1
    ) {
        if (var0 != null && var1 != null) {
            double var2 = ((Double)a.options.sensitivity().get()).floatValue() * 0.6 + 0.2;
            double var4 = var2 * var2 * var2 * 8.0;
            double var6 = var4 * 0.15;
            float var8 = var0.a() - var1.a();
            float var9 = var0.b() - var1.b();
            float var10 = var1.a() + (float)(Math.round(var8 / var6) * var6);
            float var11 = Mth.clamp(var1.b() + (float)(Math.round(var9 / var6) * var6), -90.0F, 90.0F);
            return new com.heypixel.heypixelmod.obsoverlay.utils.c.b(var10, var11);
        } else {
            return var0;
        }
    }

    @EventTarget
    public void onRespawn(u var1) {
        this.c = null;
        this.x = -10;
        this.r.clear();
        if (this.m()) {
            this.a(false);
        }
    }

    @EventTarget
    public void onPreTick(EventRunTicks var1) {
        if (a.player != null && a.level != null && var1.type() == com.heypixel.heypixelmod.obsoverlay.events.api.b.a.a) {
            this.w++;
            com.heypixel.heypixelmod.obsoverlay.utils.a.d.b();
            boolean var2 = EixClient.a().g().a(Scaffold.class).m();
            boolean var3 = EixClient.a().g().a(Blink.class).m();
            CrystalAura var4 = EixClient.a().g().a(CrystalAura.class);
            if (!var2 && (var4 == null || var4.c == null) && !var3) {
                this.s();
                if (this.c == null) {
                    this.r();
                } else {
                    double var5 = this.d.q();
                    final double var7 = this.k.q();
                    double var9 = com.heypixel.heypixelmod.obsoverlay.utils.a.b.a(this.c);
                    boolean var11 = this.c.isAlive() && !this.c.isRemoved();
                    boolean var12 = var11 && var9 <= var5;
                    boolean var13 = var11 && var9 <= var7 + 0.06;
                    if (!var11) {
                        this.r();
                    } else {
                        if (!var12) {
                            if (this.w - this.x > 4) {
                                this.r();
                                return;
                            }
                        } else {
                            this.x = this.w;
                        }

                        com.heypixel.heypixelmod.obsoverlay.utils.c.b var14 = com.heypixel.heypixelmod.obsoverlay.utils.c.d.a(this.c);
                        if (var14 == null) {
                            this.r();
                        } else {
                            com.heypixel.heypixelmod.obsoverlay.utils.c.b var15 = com.heypixel.heypixelmod.obsoverlay.utils.c.c.j();
                            if (var15 == null) {
                                var15 = new com.heypixel.heypixelmod.obsoverlay.utils.c.b(a.player.getYRot(), a.player.getXRot());
                            }

                            Function var18 = new Function<com.heypixel.heypixelmod.obsoverlay.utils.c.b, Boolean>() {
                                private float c = Float.NaN;
                                private float d = Float.NaN;
                                private boolean e = false;
                                private boolean f = false;

                                public Boolean a(com.heypixel.heypixelmod.obsoverlay.utils.c.b var1) {
                                    if (var1 == null) {
                                        return false;
                                    } else if (this.f && var1.a() == this.c && var1.b() == this.d) {
                                        return this.e;
                                    } else {
                                        boolean var3x = com.heypixel.heypixelmod.obsoverlay.utils.c.a.a(var1, var7) instanceof EntityHitResult var4x
                                            && var4x.getEntity().equals(KillAura.this.c);
                                        this.c = var1.a();
                                        this.d = var1.b();
                                        this.e = var3x;
                                        this.f = true;
                                        return var3x;
                                    }
                                }
                            };
                            boolean var19 = Boolean.TRUE.equals(var18.apply(var14));
                            com.heypixel.heypixelmod.obsoverlay.utils.c.b var20;
                            if (var19) {
                                var20 = var14;
                            } else {
                                com.heypixel.heypixelmod.obsoverlay.utils.c.b var21 = this.a(var15, var14, 1.0F);
                                var20 = var21 != null
                                        && !Float.isNaN(var21.a())
                                        && !Float.isNaN(var21.b())
                                        && !Float.isInfinite(var21.a())
                                        && !Float.isInfinite(var21.b())
                                    ? var21
                                    : var14;
                            }

                            com.heypixel.heypixelmod.obsoverlay.utils.c.c.a(this, var20, this.g.q(), var18);
                            if (var13) {
                                this.a(var20, Boolean.TRUE.equals(var18.apply(var20)));
                            }
                        }
                    }
                }
            } else {
                this.r();
            }
        }
    }

    private void r() {
        boolean var1 = this.c != null;
        this.c = null;
        if (var1) {
            com.heypixel.heypixelmod.obsoverlay.utils.c.c.b(this);
        }
    }

    private void a(com.heypixel.heypixelmod.obsoverlay.utils.c.b var1, boolean var2) {
        if (this.c != null && a.player != null && a.gameMode != null) {
            if (this.v != this.w) {
                long var3 = System.currentTimeMillis();
                if (this.e.m()) {
                    if (a.player.getAttackStrengthScale(0.0F) < 1.0F) {
                        return;
                    }
                } else {
                    long var5 = Math.max(this.t, 55L);
                    if (var3 - this.s < var5) {
                        return;
                    }
                }

                if (!var2) {
                    if (this.n.m() && !com.heypixel.heypixelmod.obsoverlay.utils.a.b.a(this.c, var1, this.k.q())) {
                        return;
                    }

                    if (!this.n.m() && com.heypixel.heypixelmod.obsoverlay.utils.a.b.a(this.c) > this.k.q() - 0.06) {
                        return;
                    }
                }

                if (this.l.m() && var3 - this.u >= 600L && com.heypixel.heypixelmod.obsoverlay.utils.a.a.b(this.m.q())) {
                    com.heypixel.heypixelmod.obsoverlay.utils.a.d.a();
                    this.u = var3;
                }

                a.gameMode.attack(a.player, this.c);
                a.player.swing(InteractionHand.MAIN_HAND);
                this.v = this.w;
                if (!this.e.m()) {
                    this.s = var3;
                    this.t = com.heypixel.heypixelmod.obsoverlay.utils.a.a.a((double)this.f.q());
                }

                if (this.c instanceof Player var7) {
                    DynamicIsland.a(var7);
                }
            }
        }
    }

    private void s() {
        if (a.player != null && a.level != null) {
            float var1 = this.d.q();
            double var2 = var1 * var1;
            float var4 = this.h.q();
            this.c = null;
            double var5 = Double.MAX_VALUE;
            Target var7 = EixClient.a().g().a(Target.class);
            double var8 = a.player.getX();
            double var10 = a.player.getZ();
            float var12 = a.player.getYRot();
            AABB var14 = a.player.getBoundingBox().inflate(var1);
            List var15 = a.level
                .getEntities(
                    a.player,
                    var14,
                    var9 -> var9 instanceof LivingEntity
                        && var9 != a.player
                        && var9.isAlive()
                        && !var9.isSpectator()
                        && !AntiBots.b(var9)
                        && (var7 == null || var7.a(var9))
                        && !Teams.a(var9)
                        && !com.heypixel.heypixelmod.obsoverlay.utils.m.a(var9)
                        && !ClientFriend.a(var9)
                        && a.player.distanceToSqr(var9) <= var2
                        && a(var9, var8, var10, var12, var4)
                );
            this.r = (List<Entity>)(var15 != null ? var15 : new ArrayList<>());

            for (Entity var17 : this.r) {
                double var18 = a.player.distanceToSqr(var17);
                if (var18 < var5 && var18 <= var2) {
                    var5 = var18;
                    this.c = var17;
                }
            }

            this.a(this.r.size() + " Targets");
        } else {
            this.c = null;
            this.r = new ArrayList<>();
        }
    }

    private static boolean a(Entity var0, double var1, double var3, float var5, float var6) {
        if (var6 >= 360.0F) {
            return true;
        } else {
            double var7 = var0.getX() - var1;
            double var9 = var0.getZ() - var3;
            if (var7 * var7 + var9 * var9 < 1.0E-6) {
                return true;
            } else {
                float var11 = (float)(-Mth.atan2(var7, var9) * (180.0 / Math.PI));
                return Math.abs(Mth.wrapDegrees(var11 - var5)) <= var6;
            }
        }
    }

    public List<Entity> p() {
        return this.r;
    }

    @EventTarget
    public void onRender(p var1) {
        if (this.r != null && !this.r.isEmpty()) {
            PoseStack var2 = var1.b();

            for (Entity var4 : this.r) {
                if (var4 != null) {
                    if (var4.equals(this.c)) {
                        ad.a(var2, var4.getX(), var4.getY(), var4.getZ(), var4.getBbWidth(), var4.getBbHeight(), new Color(200, 0, 0, 60).getRGB());
                    } else {
                        ad.a(var2, var4.getX(), var4.getY(), var4.getZ(), var4.getBbWidth(), var4.getBbHeight(), new Color(0, 200, 0, 60).getRGB());
                    }
                }
            }
        }
    }
}
