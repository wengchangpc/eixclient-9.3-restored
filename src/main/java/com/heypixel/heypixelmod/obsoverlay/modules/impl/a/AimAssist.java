package com.heypixel.heypixelmod.obsoverlay.modules.impl.a;

import com.heypixel.heypixelmod.obsoverlay.EixClient;
import com.heypixel.heypixelmod.obsoverlay.d.d;
import com.heypixel.heypixelmod.obsoverlay.d.a.c;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.k;
import com.heypixel.heypixelmod.obsoverlay.events.impl.q;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.a;
import com.heypixel.heypixelmod.obsoverlay.modules.b;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.ClientFriend;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.Target;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.Teams;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.phys.HitResult.Type;

@ModuleInfo(
    a = "AimAssist",
    b = "瞄准辅助",
    c = "Automatically aims at targets",
    d = com.heypixel.heypixelmod.obsoverlay.modules.a.COMBAT
)
public class AimAssist extends b {
    private final c c = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Speed").a(2.0F).b(1.0F).c(10.0F).d(0.1F).a().c();
    private final com.heypixel.heypixelmod.obsoverlay.d.a.a d = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Aim Pitch").a(false).a().b();
    private final com.heypixel.heypixelmod.obsoverlay.d.a.a e = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Require Swinging").a(true).a().b();
    private final com.heypixel.heypixelmod.obsoverlay.d.a.a f = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Sticky").a(false).a().b();
    private final com.heypixel.heypixelmod.obsoverlay.d.a.a g = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Require Mouse Movement").a(false).a().b();
    private final com.heypixel.heypixelmod.obsoverlay.d.a.a h = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Limit Items").a(false).a().b();
    private final com.heypixel.heypixelmod.obsoverlay.d.a.a i = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Aim Whilst on Target").a(false).a().b();
    private final c j = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "On Target Speed").a(1.0F).b(1.0F).c(10.0F).d(0.1F).a(this.i::m).a().c();
    private final c k = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "FOV").a(90.0F).b(0.0F).c(180.0F).d(1.0F).a().c();
    private float l;
    private float m;
    private LivingEntity n;
    private double o;
    private double p;

    @EventTarget
    public void onMotion(k var1) {
        if (var1.b() == com.heypixel.heypixelmod.obsoverlay.events.api.b.a.a) {
            double var2 = 4.0;
            this.l = 0.0F;
            this.m = 0.0F;
            this.n = null;
            List var4 = this.a(var2);
            if (!var4.isEmpty()) {
                this.n = (LivingEntity)var4.get(0);
                com.heypixel.heypixelmod.obsoverlay.utils.c.b var5 = com.heypixel.heypixelmod.obsoverlay.utils.c.d.a(this.n);
                float var6 = Math.abs(Mth.wrapDegrees(var5.a() - a.player.getYRot()));
                if (var6 > this.k.q()) {
                    this.n = null;
                } else if (!this.h.m() || !a.player.getMainHandItem().isEmpty() && a.player.getMainHandItem().getItem() instanceof SwordItem) {
                    float var7 = this.c.q();
                    if (a.hitResult != null && a.hitResult.getType() == Type.ENTITY) {
                        var7 = this.j.q();
                    }

                    if (this.f.m()) {
                        var7 *= 10.0F;
                    }

                    float var8 = var7 / Minecraft.getInstance().getFps() * 100.0F;
                    com.heypixel.heypixelmod.obsoverlay.utils.c.b var9 = new com.heypixel.heypixelmod.obsoverlay.utils.c.b(
                        a.player.getYRot(), a.player.getXRot()
                    );
                    com.heypixel.heypixelmod.obsoverlay.utils.c.b var10 = com.heypixel.heypixelmod.obsoverlay.utils.c.d.a(var9, var5, var8);
                    this.l = var10.a();
                    this.m = var10.b();
                } else {
                    this.n = null;
                }
            }
        }
    }

    @EventTarget
    public void onRender3D(q var1) {
        if (this.n != null && (this.l != 0.0F || this.m != 0.0F)) {
            double var2 = a.mouseHandler.xpos();
            double var4 = a.mouseHandler.ypos();
            boolean var6 = var2 != this.o || var4 != this.p;
            this.o = var2;
            this.p = var4;
            if (!this.g.m() || var6) {
                if (a.hitResult == null || a.hitResult.getType() != Type.ENTITY || this.i.m()) {
                    if (!this.e.m() || a.options.keyAttack.isDown()) {
                        double var7 = (Double)a.options.sensitivity().get() * 0.6 + 0.2;
                        double var9 = var7 * var7 * var7 * 8.0;
                        double var11 = var9 * 0.15;
                        float var13 = (float)(Math.round(this.l / var11) * var11);
                        float var14 = (float)(Math.round(this.m / var11) * var11);
                        boolean var15 = Math.abs(var13) >= var11;
                        boolean var16 = this.d.m() && Math.abs(var14) >= var11;
                        if (var15 || var16) {
                            a.player.turn(var15 ? var13 : 0.0, var16 ? var14 : 0.0);
                        }
                    }
                }
            }
        }
    }

    private List<LivingEntity> a(double var1) {
        ArrayList var3 = new ArrayList();
        if (a.level == null) {
            return var3;
        } else {
            for (Entity var5 : a.level.entitiesForRendering()) {
                if (var5 instanceof LivingEntity var6 && this.a(var6) && a.player.distanceTo(var6) <= var1) {
                    com.heypixel.heypixelmod.obsoverlay.utils.c.b var7 = com.heypixel.heypixelmod.obsoverlay.utils.c.d.a(var6);
                    float var8 = Math.abs(Mth.wrapDegrees(var7.a() - a.player.getYRot()));
                    if (var8 <= this.k.q()) {
                        var3.add(var6);
                    }
                }
            }

            var3.sort(Comparator.comparingDouble(var0 -> {
                com.heypixel.heypixelmod.obsoverlay.utils.c.b var1x = com.heypixel.heypixelmod.obsoverlay.utils.c.d.a(var0);
                float var2 = Math.abs(Mth.wrapDegrees(var1x.a() - a.player.getYRot()));
                float var3x = Math.abs(var1x.b() - a.player.getXRot());
                return Math.sqrt(var2 * var2 + var3x * var3x);
            }));
            return var3;
        }
    }

    private boolean a(LivingEntity var1) {
        if (var1 == a.player) {
            return false;
        } else if (!var1.isAlive()) {
            return false;
        } else if (!a.player.hasLineOfSight(var1)) {
            return false;
        } else if (AntiBots.b(var1)) {
            return false;
        } else if (Teams.a(var1)) {
            return false;
        } else if (ClientFriend.a(var1)) {
            return false;
        } else {
            Target var2 = EixClient.a().g().a(Target.class);
            return var2 != null && var2.m() ? var2.a(var1) : var1 instanceof Player;
        }
    }
}
