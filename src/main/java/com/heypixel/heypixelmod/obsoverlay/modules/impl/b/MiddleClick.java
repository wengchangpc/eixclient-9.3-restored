package com.heypixel.heypixelmod.obsoverlay.modules.impl.b;

import com.heypixel.heypixelmod.obsoverlay.EixClient;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventMouseClick;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventRunTicks;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.a;
import com.heypixel.heypixelmod.obsoverlay.modules.b;
import com.heypixel.heypixelmod.obsoverlay.utils.m;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.EntityHitResult;

@ModuleInfo(
    a = "Middle Click",
    b = "中键点击",
    c = "",
    d = com.heypixel.heypixelmod.obsoverlay.modules.a.MISC
)
public class MiddleClick extends b {
    private boolean c;
    private boolean d;
    private int e;

    @EventTarget
    public void onPreTick(EventRunTicks var1) {
        if (var1.type() == com.heypixel.heypixelmod.obsoverlay.events.api.b.a.a) {
            if (this.c) {
                this.c = false;
                if (!(a.hitResult instanceof EntityHitResult var2 && var2.getEntity() instanceof Player var3)) {
                    this.e = a.player.getInventory().selected;
                    int var8 = -1;

                    for (int var5 = 0; var5 < 9; var5++) {
                        ItemStack var6 = a.player.getInventory().getItem(var5);
                        if (var6.getItem() == Items.ENDER_PEARL) {
                            var8 = var5;
                            break;
                        }
                    }

                    if (var8 != -1) {
                        a.player.getInventory().selected = var8;
                        a.gameMode.useItem(a.player, InteractionHand.MAIN_HAND);
                        a.player.swing(InteractionHand.MAIN_HAND);
                        this.d = true;
                    } else {
                        com.heypixel.heypixelmod.obsoverlay.c.a.a var11 = new com.heypixel.heypixelmod.obsoverlay.c.a.a(
                            com.heypixel.heypixelmod.obsoverlay.c.a.b.d, "No pearls found.", 3000L
                        );
                        EixClient.a().j().a(var11);
                    }
                } else if (com.heypixel.heypixelmod.obsoverlay.utils.m.a((Entity)var3)) {
                    com.heypixel.heypixelmod.obsoverlay.c.a.a var9 = new com.heypixel.heypixelmod.obsoverlay.c.a.a(
                        com.heypixel.heypixelmod.obsoverlay.c.a.b.d, "Removed " + var3.getName().getString() + " from friends!", 3000L
                    );
                    EixClient.a().j().a(var9);
                    com.heypixel.heypixelmod.obsoverlay.utils.m.b(var3);
                } else {
                    com.heypixel.heypixelmod.obsoverlay.c.a.a var10 = new com.heypixel.heypixelmod.obsoverlay.c.a.a(
                        com.heypixel.heypixelmod.obsoverlay.c.a.b.a, "Added " + var3.getName().getString() + " as friends!", 3000L
                    );
                    EixClient.a().j().a(var10);
                    com.heypixel.heypixelmod.obsoverlay.utils.m.a(var3);
                }
            }

            if (this.d) {
                this.d = false;
                a.player.getInventory().selected = this.e;
            }
        }
    }

    @EventTarget
    public void onMouseKey(EventMouseClick var1) {
        if (var1.key() == 2 && !var1.state()) {
            this.c = true;
        }
    }
}
