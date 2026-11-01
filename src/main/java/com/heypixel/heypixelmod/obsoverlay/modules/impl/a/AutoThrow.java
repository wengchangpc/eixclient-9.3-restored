package com.heypixel.heypixelmod.obsoverlay.modules.impl.a;

import com.heypixel.heypixelmod.obsoverlay.EixClient;
import com.heypixel.heypixelmod.obsoverlay.d.d;
import com.heypixel.heypixelmod.obsoverlay.d.a.c;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.k;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.b;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.ClientFriend;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.Target;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.Teams;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Blink;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Stuck;
import com.heypixel.heypixelmod.obsoverlay.utils.al;
import com.heypixel.heypixelmod.obsoverlay.utils.m;
import com.heypixel.heypixelmod.obsoverlay.utils.x;
import java.util.Comparator;
import java.util.Optional;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.network.protocol.game.ServerboundSwingPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.EnderpearlItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.LingeringPotionItem;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.SplashPotionItem;
import net.minecraft.world.phys.Vec3;

@ModuleInfo(
    a = "AutoThrow",
    b = "自动投掷",
    c = "Automatically throw snowballs and eggs.",
    d = com.heypixel.heypixelmod.obsoverlay.modules.a.COMBAT
)
public class AutoThrow extends b {
    private static final double c = 0.6;
    private static final double d = 0.006;
    private final c e = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Min Distance").a(5.0F).d(1.0F).b(3.0F).c(30.0F).a().c();
    private final c f = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Max Distance").a(10.0F).d(1.0F).b(3.0F).c(30.0F).a().c();
    private final c g = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Delay").a(500.0F).d(50.0F).b(50.0F).c(2000.0F).a().c();
    private final al h = new al();
    private com.heypixel.heypixelmod.obsoverlay.utils.c.b i;
    private int j;
    private int k = -1;
    private AutoThrow.a l;

    @EventTarget
    public void onMotion(k var1) {
        if (var1.b() != com.heypixel.heypixelmod.obsoverlay.events.api.b.a.a) {
            if (this.k != -1) {
                a.getConnection().send(new ServerboundSetCarriedItemPacket(this.k));
                this.k = -1;
            }
        } else if (a.player != null && a.level != null) {
            if (!EixClient.a().g().a(Scaffold.class).m() && !EixClient.a().g().a(Stuck.class).m() && !EixClient.a().g().a(Blink.class).m()) {
                this.i = null;
                AutoThrow.a var2 = this.p();
                if (var2 != null) {
                    if (this.j > 0) {
                        this.j--;
                        if (this.j == 0 && this.l != null) {
                            this.a(this.l);
                            this.l = null;
                        }
                    } else {
                        Optional var3 = this.q();
                        if (var3.isPresent() && this.h.a((double)this.g.q()) && this.a(var2.a)) {
                            this.i = this.a((LivingEntity)var3.get());
                            com.heypixel.heypixelmod.obsoverlay.utils.c.c.a(this.i, 180.0);
                            this.j = 2;
                            this.l = var2;
                            this.h.a();
                        }
                    }
                }
            } else {
                this.j = 0;
                this.l = null;
            }
        }
    }

    private void a(AutoThrow.a var1) {
        if (var1.a == InteractionHand.MAIN_HAND) {
            int var2 = a.player.getInventory().selected;
            boolean var3 = var2 != var1.b;
            if (var3) {
                a.getConnection().send(new ServerboundSetCarriedItemPacket(var1.b));
                this.k = var2;
            }
        }

        x.a(var1x -> new ServerboundUseItemPacket(var1.a, var1x));
        a.getConnection().send(new ServerboundSwingPacket(var1.a));
    }

    private AutoThrow.a p() {
        ItemStack var1 = a.player.getOffhandItem();
        if (this.a(var1)) {
            return new AutoThrow.a(InteractionHand.OFF_HAND, -1);
        } else {
            int var2 = a.player.getInventory().selected;
            ItemStack var3 = (ItemStack)a.player.getInventory().items.get(var2);
            if (this.a(var3)) {
                return new AutoThrow.a(InteractionHand.MAIN_HAND, var2);
            } else {
                for (int var4 = 0; var4 < 9; var4++) {
                    ItemStack var5 = (ItemStack)a.player.getInventory().items.get(var4);
                    if (this.a(var5)) {
                        return new AutoThrow.a(InteractionHand.MAIN_HAND, var4);
                    }
                }

                return null;
            }
        }
    }

    private boolean a(InteractionHand var1) {
        if (a.player.isUsingItem()) {
            return false;
        } else {
            ItemStack var2 = var1 == InteractionHand.MAIN_HAND ? a.player.getMainHandItem() : a.player.getOffhandItem();
            if (var2.isEmpty()) {
                return true;
            } else {
                Item var3 = var2.getItem();
                if (var3 instanceof EnderpearlItem) {
                    return false;
                } else if (var3 instanceof BowItem) {
                    return false;
                } else {
                    return !(var3 instanceof PotionItem) && !(var3 instanceof SplashPotionItem) && !(var3 instanceof LingeringPotionItem)
                        ? !var3.isEdible()
                        : false;
                }
            }
        }
    }

    private com.heypixel.heypixelmod.obsoverlay.utils.c.b a(LivingEntity var1) {
        Vec3 var2 = var1.getDeltaMovement();
        double var3 = var1.getX();
        double var5 = var1.getY() + var1.getBbHeight() * 0.6;
        double var7 = var1.getZ();
        double var9 = 0.0;

        for (int var11 = 0; var11 < 3; var11++) {
            double var12 = var3 + var2.x * var9;
            double var14 = var7 + var2.z * var9;
            double var16 = var12 - a.player.getX();
            double var18 = var14 - a.player.getZ();
            double var20 = Math.sqrt(var16 * var16 + var18 * var18);
            var9 = var20 / 0.6;
        }

        double var27 = var3 + var2.x * var9;
        double var13 = var5 + var2.y * var9;
        double var15 = var7 + var2.z * var9;
        double var17 = var27 - a.player.getX();
        double var19 = var15 - a.player.getZ();
        double var21 = var13 - (a.player.getY() + a.player.getEyeHeight());
        double var23 = Math.sqrt(var17 * var17 + var19 * var19);
        float var25 = (float)(Math.toDegrees(Math.atan2(var19, var17)) - 90.0);
        float var26 = -this.a((float)var23, (float)var21, 0.6F, 0.006F);
        return new com.heypixel.heypixelmod.obsoverlay.utils.c.b(var25, Mth.clamp(var26, -90.0F, 90.0F));
    }

    private float a(float var1, float var2, float var3, float var4) {
        float var5 = var3 * var3;
        float var6 = var5 * var5 - var4 * (var4 * var1 * var1 + 2.0F * var2 * var5);
        return var6 <= 0.0F ? (float)Math.toDegrees(Math.atan2(var2, var1)) : (float)Math.toDegrees(Math.atan((var5 - Math.sqrt(var6)) / (var4 * var1)));
    }

    private Optional<AbstractClientPlayer> q() {
        Target var1 = EixClient.a().g().a(Target.class);
        return a.level
            .players()
            .stream()
            .filter(var0 -> var0 != a.player)
            .filter(LivingEntity::isAlive)
            .filter(var0 -> !var0.isSpectator())
            .filter(var0 -> !AntiBots.b(var0))
            .filter(var1x -> var1 == null || var1.a(var1x))
            .filter(var0 -> !Teams.a(var0))
            .filter(var0 -> !com.heypixel.heypixelmod.obsoverlay.utils.m.a(var0))
            .filter(var0 -> !ClientFriend.a(var0))
            .filter(a.player::hasLineOfSight)
            .filter(var0 -> !var0.isInvisibleTo(a.player))
            .filter(var1x -> {
                double var2 = this.b((LivingEntity)var1x);
                return var2 <= this.f.q() && var2 >= this.e.q();
            })
            .min(Comparator.comparingDouble(var0 -> a.player.distanceTo(var0)));
    }

    private double b(LivingEntity var1) {
        double var2 = var1.getX() - a.player.getX();
        double var4 = var1.getZ() - a.player.getZ();
        return Math.sqrt(var2 * var2 + var4 * var4);
    }

    private boolean a(ItemStack var1) {
        return !var1.isEmpty() && (var1.getItem() == Items.EGG || var1.getItem() == Items.SNOWBALL);
    }

    private static class a {
        private final InteractionHand a;
        private final int b;

        private a(InteractionHand var1, int var2) {
            this.a = var1;
            this.b = var2;
        }
    }
}
