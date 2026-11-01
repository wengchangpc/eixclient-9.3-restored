package com.heypixel.heypixelmod.obsoverlay.modules.impl.c;

import com.heypixel.heypixelmod.obsoverlay.d.d;
import com.heypixel.heypixelmod.obsoverlay.d.a.c;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.ab;
import com.heypixel.heypixelmod.obsoverlay.events.impl.ad;
import com.heypixel.heypixelmod.obsoverlay.events.impl.k;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.a;
import com.heypixel.heypixelmod.obsoverlay.modules.b;
import com.heypixel.heypixelmod.obsoverlay.utils.u;
import java.util.stream.StreamSupport;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket.Action;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.WebBlock;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

@ModuleInfo(
    a = "Speed",
    b = "嘻嘻哈哈",
    c = "Movement speed adjustments with Grim modes",
    d = com.heypixel.heypixelmod.obsoverlay.modules.a.MOVEMENT
)
public class Speed extends b {
    private final c c = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Bounding Box Size").a(0.4F).b(0.0F).c(1.0F).d(0.1F).a().c();
    private final c d = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "In Player Speed").a(0.08F).b(0.0F).c(0.08F).d(0.01F).a().c();
    private final c e = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Move Flying Increase").a(0.1F).b(-1.0F).c(1.0F).d(0.01F).a().c();
    private final com.heypixel.heypixelmod.obsoverlay.d.a.a f = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Fast Fall").a(true).a().b();
    private int g;
    private int h;

    @Override
    public void d() {
        this.g = 0;
        this.h = 0;
    }

    @Override
    public void e() {
        if (a.options != null) {
            a.options.keyShift.setDown(false);
            a.options.keyJump.setDown(false);
        }

        this.g = 0;
        this.h = 0;
    }

    @EventTarget
    public void onUpdate(ad var1) {
        if (a.player != null && a.level != null) {
            this.g = !a.player.onGround() ? this.g + 1 : 0;
            if (!this.r()
                && !a.player.onGround()
                && (!a.options.keyUp.isDown() || !a.options.keyRight.isDown() && !a.options.keyLeft.isDown())
                && !this.f.m()) {
                float var2 = (float)(a.player.getYRot() + 45.0F + Math.random());
                com.heypixel.heypixelmod.obsoverlay.utils.c.c.a(new com.heypixel.heypixelmod.obsoverlay.utils.c.b(var2, a.player.getXRot()), 10.0);
            }
        }
    }

    @EventTarget
    public void onMotion(k var1) {
        if (a.player != null) {
            if (this.f.m()) {
                if (var1.b() == com.heypixel.heypixelmod.obsoverlay.events.api.b.a.a) {
                    this.h = this.h == 0 ? (var1.h() ? 1 : 0) : 0;
                    var1.b(this.h != 0);
                    if (a.player.onGround()) {
                        a.player.jumpFromGround();
                    }
                } else if (var1.b() == com.heypixel.heypixelmod.obsoverlay.events.api.b.a.b && !a.player.onGround() && this.h >= 0) {
                    if (this.g < 1 || this.g > 5) {
                        return;
                    }

                    this.p();
                    a.options.keyShift.setDown(false);
                    if (this.g == 2) {
                        a.player.jumpFromGround();
                    }
                }
            }
        }
    }

    @EventTarget
    public void onStrafe(ab var1) {
        if (a.player != null && a.level != null) {
            AABB var2 = a.player.getBoundingBox().inflate(this.c.q());
            StreamSupport.<Entity>stream(a.level.entitiesForRendering().spliterator(), false)
                .filter(var0 -> var0 instanceof LivingEntity)
                .map(var0 -> (LivingEntity)var0)
                .filter(var1x -> var1x != a.player && var2.intersects(var1x.getBoundingBox()))
                .forEach(var1x -> this.a(this.d.q()));
            this.a(this.e.q() / 1000.0);
        }
    }

    private void p() {
        if (a.getConnection() != null && a.player != null) {
            a.getConnection().send(new ServerboundPlayerCommandPacket(a.player, Action.START_FALL_FLYING));
        }
    }

    private void a(double var1) {
        if (u.a()) {
            double var3 = this.q();
            Vec3 var5 = a.player.getDeltaMovement();
            a.player.setDeltaMovement(var5.x + -Math.sin(var3) * var1, var5.y, var5.z + Math.cos(var3) * var1);
        }
    }

    private double q() {
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

    private boolean r() {
        return a.level.getBlockState(a.player.blockPosition()).getBlock() instanceof WebBlock;
    }
}
