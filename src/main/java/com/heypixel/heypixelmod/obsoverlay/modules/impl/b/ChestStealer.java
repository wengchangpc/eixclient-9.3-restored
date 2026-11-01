package com.heypixel.heypixelmod.obsoverlay.modules.impl.b;

import com.heypixel.heypixelmod.obsoverlay.d.d;
import com.heypixel.heypixelmod.obsoverlay.d.a.c;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.k;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.a;
import com.heypixel.heypixelmod.obsoverlay.modules.b;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold;
import com.heypixel.heypixelmod.obsoverlay.utils.ak;
import com.heypixel.heypixelmod.obsoverlay.utils.p;
import com.heypixel.heypixelmod.obsoverlay.utils.s;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;

@ModuleInfo(
    a = "ChestStealer",
    b = "箱子小偷",
    c = "Automatically steals items from chests",
    d = com.heypixel.heypixelmod.obsoverlay.modules.a.MISC
)
public class ChestStealer extends b {
    private static final ak c = new ak();
    private final c d = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Min Delay (Ticks)").a(2.0F).d(1.0F).b(0.0F).c(10.0F).a().c();
    private final c e = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Max Delay (Ticks)").a(6.0F).d(1.0F).b(0.0F).c(10.0F).a().c();
    private final com.heypixel.heypixelmod.obsoverlay.d.a.a f = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Ender Chest").a(false).a().b();
    private Screen g;

    public static boolean p() {
        return !c.a(3);
    }

    public static boolean a(ItemStack var0) {
        if (var0.isEmpty()) {
            return false;
        } else if (!p.u(var0) && !p.b(var0)) {
            if (var0.getItem() instanceof ArmorItem var1) {
                float var11 = p.s(var0);
                float var18 = p.a(var1.getEquipmentSlot());
                return !(var11 <= var18);
            } else if (var0.getItem() instanceof SwordItem) {
                float var10 = p.r(var0);
                float var17 = p.e();
                return !(var10 <= var17);
            } else if (var0.getItem() instanceof PickaxeItem) {
                float var9 = p.p(var0);
                float var16 = p.n();
                return !(var9 <= var16);
            } else if (var0.getItem() instanceof AxeItem) {
                float var8 = p.p(var0);
                float var15 = p.p();
                return !(var8 <= var15);
            } else if (var0.getItem() instanceof ShovelItem) {
                float var7 = p.p(var0);
                float var14 = p.s();
                return !(var7 <= var14);
            } else if (var0.getItem() instanceof CrossbowItem) {
                float var6 = p.t(var0);
                float var13 = p.u();
                return !(var6 <= var13);
            } else if (var0.getItem() instanceof BowItem && p.l(var0)) {
                float var5 = p.n(var0);
                float var12 = p.w();
                return !(var5 <= var12);
            } else if (var0.getItem() instanceof BowItem && p.m(var0)) {
                float var4 = p.o(var0);
                float var3 = p.y();
                return !(var4 <= var3);
            } else if (var0.getItem() == Items.COMPASS) {
                return !p.c(var0.getItem());
            } else if (var0.getItem() == Items.WATER_BUCKET && p.d(Items.WATER_BUCKET) >= InventoryManager.t()) {
                return false;
            } else if (var0.getItem() == Items.LAVA_BUCKET && p.d(Items.LAVA_BUCKET) >= InventoryManager.u()) {
                return false;
            } else if (var0.getItem() instanceof BlockItem && Scaffold.a(var0) && p.i() + var0.getCount() >= InventoryManager.p()) {
                return false;
            } else if (var0.getItem() == Items.ARROW && p.d(Items.ARROW) + var0.getCount() >= InventoryManager.s()) {
                return false;
            } else if (var0.getItem() instanceof FishingRodItem && p.d(Items.FISHING_ROD) >= 1) {
                return false;
            } else {
                return var0.getItem() != Items.SNOWBALL && var0.getItem() != Items.EGG
                        || p.d(Items.SNOWBALL) + p.d(Items.EGG) + var0.getCount() < InventoryManager.r() && InventoryManager.q()
                    ? !(var0.getItem() instanceof ItemNameBlockItem) && p.v(var0)
                    : false;
            }
        } else {
            return true;
        }
    }

    @EventTarget(
        a = 1
    )
    public void onMotion(k var1) {
        if (var1.b() == com.heypixel.heypixelmod.obsoverlay.events.api.b.a.a) {
            Screen var2 = a.screen;
            if (var2 instanceof ContainerScreen var3) {
                ChestMenu var4 = (ChestMenu)var3.getMenu();
                if (var2 != this.g) {
                    c.b();
                } else {
                    String var5 = var3.getTitle().getString();
                    String var6 = Component.translatable("container.chest").getString();
                    String var7 = Component.translatable("container.chestDouble").getString();
                    String var8 = Component.translatable("container.enderchest").getString();
                    if (var5.equals(var6) || var5.equals(var7) || var5.equals("Chest") || this.f.m() && var5.equals(var8)) {
                        if (this.a(var4) && c.a(s.b((int)this.d.q(), (int)this.e.q()))) {
                            a.player.closeContainer();
                        } else {
                            List var9 = IntStream.range(0, var4.getRowCount() * 9).boxed().collect(Collectors.toList());
                            Collections.shuffle(var9);

                            for (Integer var11 : var9) {
                                ItemStack var12 = var4.getSlot(var11).getItem();
                                if (a(var12) && this.a(var4, var12) && c.a(s.b((int)this.d.q(), (int)this.e.q()))) {
                                    a.gameMode.handleInventoryMouseClick(var4.containerId, var11, 0, ClickType.QUICK_MOVE, a.player);
                                    c.b();
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            this.g = var2;
        }
    }

    private boolean a(ChestMenu var1, ItemStack var2) {
        if (!p.u(var2) && !p.b(var2)) {
            for (int var3 = 0; var3 < var1.getRowCount() * 9; var3++) {
                ItemStack var4 = var1.getSlot(var3).getItem();
                if (var2.getItem() instanceof ArmorItem var5 && var4.getItem() instanceof ArmorItem var6) {
                    if (var5.getEquipmentSlot() == var6.getEquipmentSlot() && p.s(var4) > p.s(var2)) {
                        return false;
                    }
                } else if (var2.getItem() instanceof SwordItem && var4.getItem() instanceof SwordItem) {
                    if (p.r(var4) > p.r(var2)) {
                        return false;
                    }
                } else if (var2.getItem() instanceof PickaxeItem && var4.getItem() instanceof PickaxeItem) {
                    if (p.p(var4) > p.p(var2)) {
                        return false;
                    }
                } else if (var2.getItem() instanceof AxeItem && var4.getItem() instanceof AxeItem) {
                    if (p.p(var4) > p.p(var2)) {
                        return false;
                    }
                } else if (var2.getItem() instanceof ShovelItem && var4.getItem() instanceof ShovelItem && p.p(var4) > p.p(var2)) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    private boolean a(ChestMenu var1) {
        for (int var2 = 0; var2 < var1.getRowCount() * 9; var2++) {
            ItemStack var3 = var1.getSlot(var2).getItem();
            if (!var3.isEmpty() && a(var3) && this.a(var1, var3)) {
                return false;
            }
        }

        return true;
    }
}
