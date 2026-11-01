package com.heypixel.heypixelmod.obsoverlay.modules.impl.b;

import com.heypixel.heypixelmod.obsoverlay.EixClient;
import com.heypixel.heypixelmod.obsoverlay.c.ClickGuiScreen;
import com.heypixel.heypixelmod.obsoverlay.d.d;
import com.heypixel.heypixelmod.obsoverlay.d.a.c;
import com.heypixel.heypixelmod.obsoverlay.d.a.e;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.k;
import com.heypixel.heypixelmod.obsoverlay.events.impl.m;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.a;
import com.heypixel.heypixelmod.obsoverlay.modules.b;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold;
import com.heypixel.heypixelmod.obsoverlay.utils.ak;
import com.heypixel.heypixelmod.obsoverlay.utils.p;
import com.heypixel.heypixelmod.obsoverlay.utils.s;
import com.heypixel.heypixelmod.obsoverlay.utils.u;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.protocol.game.ServerboundContainerClosePacket;
import net.minecraft.network.protocol.game.ServerboundInteractPacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemOnPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemPacket;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
import org.apache.commons.lang3.tuple.Pair;

@ModuleInfo(
    a = "InventoryManager",
    b = "背包整理",
    c = "Automatically manage your inventory",
    d = com.heypixel.heypixelmod.obsoverlay.modules.a.MISC
)
public class InventoryManager extends b {
    private static final ak K = new ak();
    private final c L = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Min Delay (Ticks)").a(2.0F).d(1.0F).b(0.0F).c(10.0F).a().c();
    private final c M = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Max Delay (Ticks)").a(6.0F).d(1.0F).b(0.0F).c(10.0F).a().c();
    e c = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Offhand Items").a("None", "Golden Apple", "Projectile", "Fishing Rod", "Block").a().e();
    com.heypixel.heypixelmod.obsoverlay.d.a.a d = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Auto Armor").a(true).a().b();
    com.heypixel.heypixelmod.obsoverlay.d.a.a e = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Inventory Only").a(true).a().b();
    com.heypixel.heypixelmod.obsoverlay.d.a.a f = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Switch Sword").a(true).a().b();
    c g = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Sword Slot").a(1.0F).d(1.0F).b(1.0F).c(9.0F).a(() -> this.f.m()).a().c();
    com.heypixel.heypixelmod.obsoverlay.d.a.a h = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Switch Block").a(() -> !this.c.a("Block")).a(true).a().b();
    c i = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Block Slot").a(2.0F).d(1.0F).b(1.0F).c(9.0F).a(() -> this.h.m() && !this.c.a("Block")).a().c();
    c j = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Max Block Size").a(256.0F).d(64.0F).b(64.0F).c(512.0F).a(() -> this.h.m()).a().c();
    com.heypixel.heypixelmod.obsoverlay.d.a.a k = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Switch Pickaxe").a(true).a().b();
    c l = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Pickaxe Slot").a(3.0F).d(1.0F).b(1.0F).c(9.0F).a(() -> this.k.m()).a().c();
    com.heypixel.heypixelmod.obsoverlay.d.a.a m = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Switch Axe").a(true).a().b();
    c n = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Axe Slot").a(4.0F).d(1.0F).b(1.0F).c(9.0F).a(() -> this.m.m()).a().c();
    com.heypixel.heypixelmod.obsoverlay.d.a.a o = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Switch Bow or Crossbow").a(true).a().b();
    c p = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Bow Slot").a(5.0F).d(1.0F).b(1.0F).c(9.0F).a(() -> this.o.m()).a().c();
    e q = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Bow Priority").a("Crossbow", "Power Bow", "Punch Bow").a(() -> this.o.m()).a().e();
    c r = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Max Arrow Size").a(256.0F).d(64.0F).b(64.0F).c(512.0F).a(() -> this.o.m()).a().c();
    com.heypixel.heypixelmod.obsoverlay.d.a.a s = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Switch Water Bucket").a(true).a().b();
    c t = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Water Bucket Slot").a(6.0F).d(1.0F).b(1.0F).c(9.0F).a(() -> this.s.m()).a().c();
    com.heypixel.heypixelmod.obsoverlay.d.a.a u = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Switch Ender Pearl").a(true).a().b();
    c v = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Ender Pearl Slot").a(7.0F).d(1.0F).b(1.0F).c(9.0F).a(() -> this.u.m()).a().c();
    com.heypixel.heypixelmod.obsoverlay.d.a.a w = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Switch Fireball").a(true).a().b();
    c x = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Fireball Slot").a(8.0F).d(1.0F).b(1.0F).c(9.0F).a(() -> this.w.m()).a().c();
    com.heypixel.heypixelmod.obsoverlay.d.a.a y = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Switch Golden Apple")
        .a(() -> !this.c.a("Golden Apple"))
        .a(true)
        .a()
        .b();
    c z = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Golden Apple Slot")
        .a(9.0F)
        .d(1.0F)
        .b(1.0F)
        .c(9.0F)
        .a(() -> this.y.m() && !this.c.a("Golden Apple"))
        .a()
        .c();
    com.heypixel.heypixelmod.obsoverlay.d.a.a A = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Throw Items").a(true).a().b();
    c B = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Keep Water Buckets").a(1.0F).d(1.0F).b(0.0F).c(5.0F).a(() -> this.A.m()).a().c();
    c C = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Keep Lava Buckets").a(1.0F).d(1.0F).b(0.0F).c(5.0F).a(() -> this.A.m()).a().c();
    com.heypixel.heypixelmod.obsoverlay.d.a.a D = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Keep Eggs & Snowballs").a(true).a().b();
    com.heypixel.heypixelmod.obsoverlay.d.a.a E = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Switch Eggs & Snowballs")
        .a(false)
        .a(() -> this.D.m() && !this.c.a("Projectile"))
        .a()
        .b();
    c F = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Eggs & Snowballs Slot")
        .a(9.0F)
        .d(1.0F)
        .b(1.0F)
        .c(9.0F)
        .a(() -> this.E.m() && this.D.m() && !this.c.a("Projectile"))
        .a()
        .c();
    c G = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Max Eggs & Snowballs Size").a(64.0F).d(16.0F).b(16.0F).c(256.0F).a(() -> this.D.m()).a().c();
    com.heypixel.heypixelmod.obsoverlay.d.a.a H = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Switch Rod")
        .a(() -> !this.c.a("Fishing Rod"))
        .a(false)
        .a()
        .b();
    c I = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Rod Slot").a(9.0F).d(1.0F).b(1.0F).c(9.0F).a(() -> this.H.m() && !this.c.a("Fishing Rod")).a().c();
    int J = 0;
    private boolean N = false;
    private boolean O = false;

    public static int p() {
        return (int)EixClient.a().g().a(InventoryManager.class).j.q();
    }

    public static boolean q() {
        return EixClient.a().g().a(InventoryManager.class).D.m();
    }

    public static int r() {
        return (int)EixClient.a().g().a(InventoryManager.class).G.q();
    }

    public static int s() {
        return (int)EixClient.a().g().a(InventoryManager.class).r.q();
    }

    public static int t() {
        return (int)EixClient.a().g().a(InventoryManager.class).B.q();
    }

    public static int u() {
        return (int)EixClient.a().g().a(InventoryManager.class).C.q();
    }

    public boolean a(ItemStack var1) {
        if (var1.isEmpty()) {
            return false;
        } else if (com.heypixel.heypixelmod.obsoverlay.utils.p.u(var1)) {
            return true;
        } else if (var1.getDisplayName().getString().contains("点击使用")) {
            return true;
        } else if (var1.getItem() instanceof ArmorItem var2) {
            float var5 = com.heypixel.heypixelmod.obsoverlay.utils.p.s(var1);
            if (com.heypixel.heypixelmod.obsoverlay.utils.p.b(var2.getEquipmentSlot()) >= var5) {
                return false;
            } else {
                float var4 = com.heypixel.heypixelmod.obsoverlay.utils.p.a(var2.getEquipmentSlot());
                return !(var5 < var4);
            }
        } else if (var1.getItem() instanceof SwordItem) {
            return com.heypixel.heypixelmod.obsoverlay.utils.p.f() == var1;
        } else if (var1.getItem() instanceof PickaxeItem) {
            return com.heypixel.heypixelmod.obsoverlay.utils.p.o() == var1;
        } else if (var1.getItem() instanceof AxeItem && !com.heypixel.heypixelmod.obsoverlay.utils.p.b(var1)) {
            return com.heypixel.heypixelmod.obsoverlay.utils.p.q() == var1;
        } else if (var1.getItem() instanceof ShovelItem) {
            return com.heypixel.heypixelmod.obsoverlay.utils.p.t() == var1;
        } else if (var1.getItem() instanceof CrossbowItem) {
            return com.heypixel.heypixelmod.obsoverlay.utils.p.v() == var1;
        } else if (var1.getItem() instanceof BowItem && com.heypixel.heypixelmod.obsoverlay.utils.p.l(var1)) {
            return com.heypixel.heypixelmod.obsoverlay.utils.p.x() == var1;
        } else if (var1.getItem() instanceof BowItem && com.heypixel.heypixelmod.obsoverlay.utils.p.m(var1)) {
            return com.heypixel.heypixelmod.obsoverlay.utils.p.z() == var1;
        } else if (var1.getItem() instanceof BowItem && com.heypixel.heypixelmod.obsoverlay.utils.p.d(Items.BOW) > 1) {
            return false;
        } else if (var1.getItem() == Items.WATER_BUCKET && com.heypixel.heypixelmod.obsoverlay.utils.p.d(Items.WATER_BUCKET) > t()) {
            return false;
        } else if (var1.getItem() == Items.LAVA_BUCKET && com.heypixel.heypixelmod.obsoverlay.utils.p.d(Items.LAVA_BUCKET) > u()) {
            return false;
        } else if (var1.getItem() instanceof FishingRodItem && com.heypixel.heypixelmod.obsoverlay.utils.p.d(Items.FISHING_ROD) > 1) {
            return false;
        } else {
            return (var1.getItem() == Items.SNOWBALL || var1.getItem() == Items.EGG) && !q()
                ? false
                : !(var1.getItem() instanceof ItemNameBlockItem) && com.heypixel.heypixelmod.obsoverlay.utils.p.v(var1);
        }
    }

    @EventTarget
    public void onPacket(m var1) {
        if (var1.b() == com.heypixel.heypixelmod.obsoverlay.events.api.b.a.c) {
            if (var1.c() instanceof ServerboundContainerClosePacket) {
                this.O = false;
            }

            if (this.O && !this.e.m()) {
                if (var1.c() instanceof ServerboundMovePlayerPacket) {
                    if (com.heypixel.heypixelmod.obsoverlay.utils.u.a()) {
                        a.getConnection().send(new ServerboundContainerClosePacket(a.player.inventoryMenu.containerId));
                    }
                } else if (var1.c() instanceof ServerboundUseItemOnPacket
                    || var1.c() instanceof ServerboundUseItemPacket
                    || var1.c() instanceof ServerboundInteractPacket
                    || var1.c() instanceof ServerboundPlayerActionPacket) {
                    a.getConnection().send(new ServerboundContainerClosePacket(a.player.inventoryMenu.containerId));
                }
            }
        }
    }

    private boolean v() {
        ArrayList var1 = new ArrayList();
        if (!this.D.m()) {
            this.E.a(false);
        }

        var1.add(Pair.of(this.f, this.g));
        var1.add(Pair.of(this.k, this.l));
        var1.add(Pair.of(this.m, this.n));
        var1.add(Pair.of(this.o, this.p));
        var1.add(Pair.of(this.s, this.t));
        var1.add(Pair.of(this.u, this.v));
        var1.add(Pair.of(this.w, this.x));
        if (!this.c.a("Golden Apple")) {
            var1.add(Pair.of(this.y, this.z));
        }

        if (!this.c.a("Projectile")) {
            var1.add(Pair.of(this.E, this.F));
        }

        if (!this.c.a("Fishing Rod")) {
            var1.add(Pair.of(this.H, this.I));
        }

        if (!this.c.a("Block")) {
            var1.add(Pair.of(this.h, this.i));
        }

        HashSet var2 = new HashSet();

        for (Pair var4 : var1) {
            if (((com.heypixel.heypixelmod.obsoverlay.d.a.a)var4.getKey()).m()) {
                int var5 = (int)(((c)var4.getValue()).q() - 1.0F);
                if (var2.contains(var5)) {
                    return false;
                }

                var2.add(var5);
            }
        }

        return true;
    }

    @EventTarget
    public void onMotion(k var1) {
        if (var1.b() == com.heypixel.heypixelmod.obsoverlay.events.api.b.a.a) {
            if (!(a.screen instanceof ClickGuiScreen) && !this.v()) {
                com.heypixel.heypixelmod.obsoverlay.c.a.a var23 = new com.heypixel.heypixelmod.obsoverlay.c.a.a(
                    com.heypixel.heypixelmod.obsoverlay.c.a.b.d, "Duplicate slot config in Inventory Manager! Please check your config!", 8000L
                );
                EixClient.a().j().a(var23);
                this.f();
                return;
            }

            if (com.heypixel.heypixelmod.obsoverlay.utils.p.a()) {
                return;
            }

            if (com.heypixel.heypixelmod.obsoverlay.utils.u.a()) {
                this.J = 0;
            } else {
                this.J++;
            }

            if (ChestStealer.p() || EixClient.a().g().a(Scaffold.class).m() || (this.e.m() ? !(a.screen instanceof InventoryScreen) : this.J <= 1)) {
                this.N = false;
                return;
            }

            if (a.screen instanceof AbstractContainerScreen var2 && var2.getMenu().containerId != a.player.inventoryMenu.containerId) {
                return;
            }

            if (this.d.m()) {
                for (int var8 = 0; var8 < a.player.getInventory().armor.size(); var8++) {
                    ItemStack var24 = (ItemStack)a.player.getInventory().armor.get(var8);
                    if (var24.getItem() instanceof ArmorItem var4
                        && !var24.isEmpty()
                        && K.a(com.heypixel.heypixelmod.obsoverlay.utils.s.b((int)this.L.q(), (int)this.M.q()))
                        && com.heypixel.heypixelmod.obsoverlay.utils.p.a(var4.getEquipmentSlot()) > com.heypixel.heypixelmod.obsoverlay.utils.p.s(var24)) {
                        a.gameMode.handleInventoryMouseClick(a.player.inventoryMenu.containerId, 4 + (4 - var8), 1, ClickType.THROW, a.player);
                        this.O = true;
                        K.b();
                    }
                }

                for (int var9 = 0; var9 < a.player.getInventory().items.size(); var9++) {
                    ItemStack var25 = (ItemStack)a.player.getInventory().items.get(var9);
                    if (!var25.isEmpty() && var25.getItem() instanceof ArmorItem var37) {
                        float var49 = com.heypixel.heypixelmod.obsoverlay.utils.p.s(var25);
                        boolean var6 = com.heypixel.heypixelmod.obsoverlay.utils.p.a(var37.getEquipmentSlot()) == var49;
                        boolean var7 = com.heypixel.heypixelmod.obsoverlay.utils.p.b(var37.getEquipmentSlot()) < var49;
                        if (var6 && var7 && K.a(com.heypixel.heypixelmod.obsoverlay.utils.s.b((int)this.L.q(), (int)this.M.q()))) {
                            if (var9 < 9) {
                                a.gameMode.handleInventoryMouseClick(a.player.inventoryMenu.containerId, var9 + 36, 0, ClickType.QUICK_MOVE, a.player);
                            } else {
                                a.gameMode.handleInventoryMouseClick(a.player.inventoryMenu.containerId, var9, 0, ClickType.QUICK_MOVE, a.player);
                            }

                            this.O = true;
                            K.b();
                        }
                    }
                }
            }

            if (this.N && K.a(com.heypixel.heypixelmod.obsoverlay.utils.s.b((int)this.L.q(), (int)this.M.q()))) {
                a.gameMode.handleInventoryMouseClick(a.player.inventoryMenu.containerId, 45, 0, ClickType.PICKUP, a.player);
                this.O = true;
                this.N = false;
                K.b();
            }

            if (this.c.a("Golden Apple")) {
                ItemStack var13 = (ItemStack)a.player.getInventory().offhand.get(0);
                int var29 = com.heypixel.heypixelmod.obsoverlay.utils.p.b(Items.GOLDEN_APPLE);
                if (var29 != -1 && K.a(com.heypixel.heypixelmod.obsoverlay.utils.s.b((int)this.L.q(), (int)this.M.q()))) {
                    if (var13.getItem() == Items.GOLDEN_APPLE) {
                        ItemStack var40 = (ItemStack)a.player.getInventory().items.get(var29);
                        if (var13.getCount() + var40.getCount() <= 64) {
                            if (var29 < 9) {
                                a.gameMode.handleInventoryMouseClick(a.player.inventoryMenu.containerId, var29 + 36, 0, ClickType.PICKUP, a.player);
                            } else {
                                a.gameMode.handleInventoryMouseClick(a.player.inventoryMenu.containerId, var29, 0, ClickType.PICKUP, a.player);
                            }

                            this.O = true;
                            this.N = true;
                            K.b();
                        }
                    } else {
                        this.c(var29);
                    }
                }
            } else if (this.c.a("Projectile")) {
                ItemStack var12 = (ItemStack)a.player.getInventory().offhand.get(0);
                ItemStack var28 = com.heypixel.heypixelmod.obsoverlay.utils.p.g();
                if (var28 != null) {
                    int var39 = com.heypixel.heypixelmod.obsoverlay.utils.p.j(var28);
                    boolean var51 = false;
                    if (var12.getItem() != Items.EGG && var12.getItem() != Items.SNOWBALL) {
                        var51 = true;
                    } else if (var12.getCount() < var28.getCount()) {
                        var51 = true;
                    }

                    if (var51 && var39 != -1 && K.a(com.heypixel.heypixelmod.obsoverlay.utils.s.b((int)this.L.q(), (int)this.M.q()))) {
                        this.c(var39);
                    }
                }
            } else if (this.c.a("Fishing Rod")) {
                ItemStack var10 = (ItemStack)a.player.getInventory().offhand.get(0);
                int var26 = com.heypixel.heypixelmod.obsoverlay.utils.p.b(Items.FISHING_ROD);
                if (var26 != -1 && K.a(com.heypixel.heypixelmod.obsoverlay.utils.s.b((int)this.L.q(), (int)this.M.q())) && var10.getItem() != Items.FISHING_ROD) {
                    this.c(var26);
                }
            } else if (this.c.a("Block")) {
                ItemStack var11 = (ItemStack)a.player.getInventory().offhand.get(0);
                ItemStack var27 = com.heypixel.heypixelmod.obsoverlay.utils.p.m();
                if (var27 != null) {
                    int var38 = com.heypixel.heypixelmod.obsoverlay.utils.p.j(var27);
                    boolean var50 = false;
                    if (Scaffold.a(var11)) {
                        if (var11.getCount() < var27.getCount()) {
                            var50 = true;
                        }
                    } else {
                        var50 = true;
                    }

                    if (var50 && var38 != -1 && K.a(com.heypixel.heypixelmod.obsoverlay.utils.s.b((int)this.L.q(), (int)this.M.q()))) {
                        this.c(var38);
                    }
                }
            }

            if (this.y.m() && !this.c.a("Golden Apple")) {
                this.a((int)(this.z.q() - 1.0F), Items.GOLDEN_APPLE);
            }

            if (this.h.m()) {
                int var14 = (int)(this.i.q() - 1.0F);
                ItemStack var30 = (ItemStack)a.player.getInventory().items.get(var14);
                ItemStack var41 = com.heypixel.heypixelmod.obsoverlay.utils.p.m();
                if (var41 != null && (var41.getCount() > var30.getCount() || !Scaffold.a(var30)) && !this.c.a("Block")) {
                    this.a(var14, var41);
                }

                if (com.heypixel.heypixelmod.obsoverlay.utils.p.i() > this.j.q()) {
                    ItemStack var52 = com.heypixel.heypixelmod.obsoverlay.utils.p.l();
                    this.b(var52);
                }
            }

            if (this.f.m()) {
                int var15 = (int)(this.g.q() - 1.0F);
                ItemStack var31 = (ItemStack)a.player.getInventory().items.get(var15);
                ItemStack var42 = com.heypixel.heypixelmod.obsoverlay.utils.p.f();
                ItemStack var53 = com.heypixel.heypixelmod.obsoverlay.utils.p.r();
                if (com.heypixel.heypixelmod.obsoverlay.utils.p.q(var53) > com.heypixel.heypixelmod.obsoverlay.utils.p.r(var42)) {
                    var42 = var53;
                }

                if (var42 != null) {
                    float var56 = var31.getItem() instanceof SwordItem
                        ? com.heypixel.heypixelmod.obsoverlay.utils.p.r(var31)
                        : com.heypixel.heypixelmod.obsoverlay.utils.p.q(var31);
                    float var58 = var42.getItem() instanceof SwordItem
                        ? com.heypixel.heypixelmod.obsoverlay.utils.p.r(var42)
                        : com.heypixel.heypixelmod.obsoverlay.utils.p.q(var42);
                    if (var58 > var56) {
                        this.a(var15, var42);
                    }
                }
            }

            if (this.k.m()) {
                int var16 = (int)(this.l.q() - 1.0F);
                ItemStack var32 = com.heypixel.heypixelmod.obsoverlay.utils.p.o();
                ItemStack var43 = (ItemStack)a.player.getInventory().items.get(var16);
                if (var32 != null
                    && var32.getItem() instanceof PickaxeItem
                    && (
                        com.heypixel.heypixelmod.obsoverlay.utils.p.p(var32) > com.heypixel.heypixelmod.obsoverlay.utils.p.p(var43)
                            || !(var43.getItem() instanceof PickaxeItem)
                    )) {
                    this.a(var16, var32);
                }
            }

            if (this.m.m()) {
                int var17 = (int)(this.n.q() - 1.0F);
                ItemStack var33 = com.heypixel.heypixelmod.obsoverlay.utils.p.q();
                ItemStack var44 = (ItemStack)a.player.getInventory().items.get(var17);
                if (var33 != null
                    && var33.getItem() instanceof AxeItem
                    && (
                        com.heypixel.heypixelmod.obsoverlay.utils.p.p(var33) > com.heypixel.heypixelmod.obsoverlay.utils.p.p(var44)
                            || !(var44.getItem() instanceof AxeItem)
                    )) {
                    this.a(var17, var33);
                }
            }

            if (this.H.m() && !this.c.a("Fishing Rod")) {
                int var18 = (int)(this.I.q() - 1.0F);
                ItemStack var34 = com.heypixel.heypixelmod.obsoverlay.utils.p.h();
                ItemStack var45 = (ItemStack)a.player.getInventory().items.get(var18);
                if (!(var45.getItem() instanceof FishingRodItem)) {
                    this.a(var18, var34);
                }
            }

            if (this.o.m()) {
                int var19 = (int)(this.p.q() - 1.0F);
                ItemStack var35 = (ItemStack)a.player.getInventory().items.get(var19);
                ItemStack var46;
                float var54;
                float var57;
                if (this.q.a("Crossbow")) {
                    var46 = com.heypixel.heypixelmod.obsoverlay.utils.p.v();
                    var54 = com.heypixel.heypixelmod.obsoverlay.utils.p.t(var46);
                    var57 = com.heypixel.heypixelmod.obsoverlay.utils.p.t(var35);
                } else if (this.q.a("Power Bow")) {
                    var46 = com.heypixel.heypixelmod.obsoverlay.utils.p.z();
                    var54 = com.heypixel.heypixelmod.obsoverlay.utils.p.o(var46);
                    var57 = com.heypixel.heypixelmod.obsoverlay.utils.p.o(var35);
                } else {
                    var46 = com.heypixel.heypixelmod.obsoverlay.utils.p.x();
                    var54 = com.heypixel.heypixelmod.obsoverlay.utils.p.n(var46);
                    var57 = com.heypixel.heypixelmod.obsoverlay.utils.p.n(var35);
                }

                if (var46 == null) {
                    var46 = com.heypixel.heypixelmod.obsoverlay.utils.p.v();
                    var54 = com.heypixel.heypixelmod.obsoverlay.utils.p.t(var46);
                    var57 = com.heypixel.heypixelmod.obsoverlay.utils.p.t(var35);
                }

                if (var46 == null) {
                    var46 = com.heypixel.heypixelmod.obsoverlay.utils.p.z();
                    var54 = com.heypixel.heypixelmod.obsoverlay.utils.p.o(var46);
                    var57 = com.heypixel.heypixelmod.obsoverlay.utils.p.o(var35);
                }

                if (var46 == null) {
                    var46 = com.heypixel.heypixelmod.obsoverlay.utils.p.x();
                    var54 = com.heypixel.heypixelmod.obsoverlay.utils.p.n(var46);
                    var57 = com.heypixel.heypixelmod.obsoverlay.utils.p.n(var35);
                }

                if (var46 != null && var54 > var57) {
                    this.a(var19, var46);
                }

                if (com.heypixel.heypixelmod.obsoverlay.utils.p.d(Items.ARROW) > this.r.q()) {
                    ItemStack var59 = com.heypixel.heypixelmod.obsoverlay.utils.p.k();
                    this.b(var59);
                }
            }

            if (this.u.m()) {
                this.a((int)(this.v.q() - 1.0F), Items.ENDER_PEARL);
            }

            if (this.s.m()) {
                this.a((int)(this.t.q() - 1.0F), Items.WATER_BUCKET);
            }

            if (this.w.m()) {
                this.a((int)(this.x.q() - 1.0F), Items.FIRE_CHARGE);
            }

            if (this.D.m()) {
                if (com.heypixel.heypixelmod.obsoverlay.utils.p.d(Items.EGG) + com.heypixel.heypixelmod.obsoverlay.utils.p.d(Items.SNOWBALL) > this.G.q()) {
                    ItemStack var20 = com.heypixel.heypixelmod.obsoverlay.utils.p.j();
                    this.b(var20);
                }

                if (this.E.m() && !this.c.a("Projectile")) {
                    int var21 = (int)(this.F.q() - 1.0F);
                    if (com.heypixel.heypixelmod.obsoverlay.utils.p.d(Items.EGG) > 0) {
                        this.a(var21, Items.EGG);
                    } else if (com.heypixel.heypixelmod.obsoverlay.utils.p.d(Items.SNOWBALL) > 0) {
                        this.a(var21, Items.SNOWBALL);
                    }
                }
            }

            if (this.A.m()) {
                List var22 = IntStream.range(0, a.player.getInventory().items.size()).boxed().collect(Collectors.toList());
                Collections.shuffle(var22);

                for (Integer var47 : var22) {
                    ItemStack var55 = (ItemStack)a.player.getInventory().items.get(var47);
                    if (!var55.isEmpty() && !this.a(var55)) {
                        this.b(var55);
                    }
                }
            }
        }
    }

    private void c(int var1) {
        if (var1 < 9) {
            a.gameMode.handleInventoryMouseClick(a.player.inventoryMenu.containerId, var1 + 36, 40, ClickType.SWAP, a.player);
        } else {
            a.gameMode.handleInventoryMouseClick(a.player.inventoryMenu.containerId, var1, 40, ClickType.SWAP, a.player);
        }

        this.O = true;
        K.b();
    }

    private void b(ItemStack var1) {
        if (com.heypixel.heypixelmod.obsoverlay.utils.p.k(var1) && K.a(com.heypixel.heypixelmod.obsoverlay.utils.s.b((int)this.L.q(), (int)this.M.q()))) {
            int var2 = com.heypixel.heypixelmod.obsoverlay.utils.p.j(var1);
            if (var2 != -1) {
                if (var2 < 9) {
                    a.gameMode.handleInventoryMouseClick(a.player.inventoryMenu.containerId, var2 + 36, 1, ClickType.THROW, a.player);
                } else {
                    a.gameMode.handleInventoryMouseClick(a.player.inventoryMenu.containerId, var2, 1, ClickType.THROW, a.player);
                }

                this.O = true;
                K.b();
            }
        }
    }

    private void a(int var1, ItemStack var2) {
        ItemStack var3 = (ItemStack)a.player.getInventory().items.get(var1);
        if (com.heypixel.heypixelmod.obsoverlay.utils.p.k(var3)
            && var2 != var3
            && K.a(com.heypixel.heypixelmod.obsoverlay.utils.s.b((int)this.L.q(), (int)this.M.q()))) {
            int var4 = com.heypixel.heypixelmod.obsoverlay.utils.p.j(var2);
            if (var4 != -1) {
                if (var4 < 9) {
                    a.gameMode.handleInventoryMouseClick(a.player.inventoryMenu.containerId, var4 + 36, var1, ClickType.SWAP, a.player);
                } else {
                    a.gameMode.handleInventoryMouseClick(a.player.inventoryMenu.containerId, var4, var1, ClickType.SWAP, a.player);
                }

                this.O = true;
                K.b();
            }
        }
    }

    private void a(int var1, Item var2) {
        ItemStack var3 = (ItemStack)a.player.getInventory().items.get(var1);
        if (com.heypixel.heypixelmod.obsoverlay.utils.p.k(var3) && K.a(com.heypixel.heypixelmod.obsoverlay.utils.s.b((int)this.L.q(), (int)this.M.q()))) {
            int var4 = com.heypixel.heypixelmod.obsoverlay.utils.p.b(var2);
            if (var4 != -1) {
                ItemStack var5 = (ItemStack)a.player.getInventory().items.get(var4);
                if (var3.getItem() != var2 || var3.getItem() == var2 && var3.getCount() < var5.getCount()) {
                    if (var4 < 9) {
                        a.gameMode.handleInventoryMouseClick(a.player.inventoryMenu.containerId, var4 + 36, var1, ClickType.SWAP, a.player);
                    } else {
                        a.gameMode.handleInventoryMouseClick(a.player.inventoryMenu.containerId, var4, var1, ClickType.SWAP, a.player);
                    }

                    this.O = true;
                    K.b();
                }
            }
        }
    }
}
