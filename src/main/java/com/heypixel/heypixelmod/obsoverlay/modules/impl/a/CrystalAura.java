package com.heypixel.heypixelmod.obsoverlay.modules.impl.a;

import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventRunTicks;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.a;
import com.heypixel.heypixelmod.obsoverlay.modules.b;
import com.heypixel.heypixelmod.obsoverlay.utils.c.c;
import com.heypixel.heypixelmod.obsoverlay.utils.c.d;
import net.minecraft.network.protocol.game.ServerboundInteractPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;

@ModuleInfo(
    a = "CrystalAura",
    b = "水晶光环",
    d = com.heypixel.heypixelmod.obsoverlay.modules.a.COMBAT,
    c = "Automatically attacks end crystals"
)
public class CrystalAura extends b {
    public Entity c;

    @EventTarget
    public void onEarlyTick(EventRunTicks var1) {
        if (var1.type() == com.heypixel.heypixelmod.obsoverlay.events.api.b.a.a && a.player != null && a.level != null) {
            double var2 = 4.0;
            double var4 = var2 * var2;
            Entity var6 = this.c;
            this.c = null;
            EndCrystal var7 = null;
            double var8 = Double.MAX_VALUE;
            AABB var10 = a.player.getBoundingBox().inflate(var2);

            for (EndCrystal var13 : a.level.getEntitiesOfClass(EndCrystal.class, var10)) {
                if (var13.isAlive()) {
                    double var14 = a.player.distanceToSqr(var13);
                    if (var14 < var8 && var14 <= var4) {
                        var8 = var14;
                        var7 = var13;
                    }
                }
            }

            if (var7 != null) {
                com.heypixel.heypixelmod.obsoverlay.utils.c.b var16 = com.heypixel.heypixelmod.obsoverlay.utils.c.d.a(var7);
                if (com.heypixel.heypixelmod.obsoverlay.utils.c.a.a(var16, 3.0) instanceof EntityHitResult var18 && var18.getEntity().equals(var7)) {
                    com.heypixel.heypixelmod.obsoverlay.utils.c.c.a(var16, 180.0);
                    this.c = var7;
                }
            }

            if (var6 != null && this.c == null && com.heypixel.heypixelmod.obsoverlay.utils.c.c.k()) {
                com.heypixel.heypixelmod.obsoverlay.utils.c.c.b(false);
            }
        }
    }

    @EventTarget
    public void onClick(com.heypixel.heypixelmod.obsoverlay.events.impl.d var1) {
        if (this.c != null && a.hitResult instanceof EntityHitResult var3 && var3.getEntity().equals(this.c)) {
            a.getConnection().send(ServerboundInteractPacket.createAttackPacket(this.c, false));
            a.player.swing(InteractionHand.MAIN_HAND);
            this.c = null;
        }
    }
}
