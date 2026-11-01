package com.heypixel.heypixelmod.obsoverlay.modules.impl.b;

import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.k;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.a;
import com.heypixel.heypixelmod.obsoverlay.modules.b;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket.Action;
import net.minecraft.world.level.block.entity.BedBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;

@ModuleInfo(
    a = "GhostBedMine",
    b = "隔墙挖床",
    c = "Mines beds through walls",
    d = com.heypixel.heypixelmod.obsoverlay.modules.a.MISC
)
public class GhostBedMine extends b {
    private BlockPos c = null;
    private Direction d = Direction.UP;
    private int e = 0;
    private boolean f = false;
    private int g = 0;

    @EventTarget
    public void onMotion(k var1) {
        if (var1.b() == com.heypixel.heypixelmod.obsoverlay.events.api.b.a.a) {
            if (a.player != null && a.level != null && a.getConnection() != null) {
                if (!a.options.keyAttack.isDown()) {
                    this.p();
                } else {
                    BlockPos var2 = this.q();
                    if (var2 == null) {
                        this.p();
                    } else {
                        if (!var2.equals(this.c)) {
                            this.p();
                            this.c = var2;
                            this.d = this.a(this.c);
                            this.e = 0;
                            this.f = false;
                        }

                        if (this.c != null) {
                            if (!this.f) {
                                this.a(Action.START_DESTROY_BLOCK);
                                this.f = true;
                                this.e = 0;
                            } else {
                                this.e++;
                                if (this.e >= 6) {
                                    this.a(Action.STOP_DESTROY_BLOCK);
                                    this.p();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void a(Action var1) {
        if (a.getConnection() != null && this.c != null) {
            a.getConnection().send(new ServerboundPlayerActionPacket(var1, this.c, this.d, this.g++));
        }
    }

    private void p() {
        this.c = null;
        this.d = Direction.UP;
        this.e = 0;
        this.f = false;
    }

    private BlockPos q() {
        double var1 = 4.5;
        Vec3 var3 = a.player.getEyePosition(1.0F);
        Vec3 var4 = a.player.getViewVector(1.0F);

        for (double var5 = 0.1; var5 <= var1; var5 += 0.1) {
            Vec3 var7 = var3.add(var4.scale(var5));
            BlockPos var8 = BlockPos.containing(var7);
            BlockEntity var9 = a.level.getBlockEntity(var8);
            if (var9 instanceof BedBlockEntity && a.player.distanceToSqr(Vec3.atCenterOf(var8)) <= var1 * var1) {
                return var8;
            }
        }

        return null;
    }

    private Direction a(BlockPos var1) {
        Vec3 var2 = a.player.getEyePosition(1.0F);
        Vec3 var3 = Vec3.atCenterOf(var1);
        Vec3 var4 = var3.subtract(var2).normalize();
        return Direction.getNearest(var4.x, var4.y, var4.z);
    }
}
