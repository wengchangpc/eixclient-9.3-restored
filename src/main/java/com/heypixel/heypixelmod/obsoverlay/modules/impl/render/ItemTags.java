package com.heypixel.heypixelmod.obsoverlay.modules.impl.render;

import com.heypixel.heypixelmod.obsoverlay.d.d;
import com.heypixel.heypixelmod.obsoverlay.d.a.c;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventRender2D;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventShader;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.a;
import com.heypixel.heypixelmod.obsoverlay.modules.b;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.ChestStealer;
import com.heypixel.heypixelmod.obsoverlay.utils.aa;
import com.heypixel.heypixelmod.obsoverlay.utils.ad;
import com.heypixel.heypixelmod.obsoverlay.utils.h;
import com.heypixel.heypixelmod.obsoverlay.utils.p;
import com.mojang.blaze3d.vertex.PoseStack;
import java.awt.Color;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.EggItem;
import net.minecraft.world.item.EnchantedGoldenAppleItem;
import net.minecraft.world.item.EndCrystalItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SnowballItem;
import org.joml.Vector4f;

@ModuleInfo(
    a = "ItemTags",
    b = "物品标签",
    c = "Show item tags.",
    d = com.heypixel.heypixelmod.obsoverlay.modules.a.RENDER
)
public class ItemTags extends b {
    private final int l = com.heypixel.heypixelmod.obsoverlay.utils.h.a(0, 0, 0, 40);
    private final ConcurrentHashMap<ItemEntity, com.heypixel.heypixelmod.obsoverlay.utils.f.b> m = new ConcurrentHashMap<>();
    private final List<Vector4f> n = new java.util.ArrayList<>();
    public c c = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Scale").a(0.25F).d(0.01F).b(0.1F).c(0.5F).a().c();
    com.heypixel.heypixelmod.obsoverlay.d.a.a d = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "All Items").a(false).a().b();
    com.heypixel.heypixelmod.obsoverlay.d.a.a e = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "God Items").a(true).a(() -> !this.d.m()).a().b();
    com.heypixel.heypixelmod.obsoverlay.d.a.a f = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Diamond").a(true).a(() -> !this.d.m()).a().b();
    com.heypixel.heypixelmod.obsoverlay.d.a.a g = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Gold").a(true).a(() -> !this.d.m()).a().b();
    com.heypixel.heypixelmod.obsoverlay.d.a.a h = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Iron").a(true).a(() -> !this.d.m()).a().b();
    com.heypixel.heypixelmod.obsoverlay.d.a.a i = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Ender Pearl").a(true).a(() -> !this.d.m()).a().b();
    com.heypixel.heypixelmod.obsoverlay.d.a.a j = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Golden Apple").a(true).a(() -> !this.d.m()).a().b();
    com.heypixel.heypixelmod.obsoverlay.d.a.a k = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Useful Item").a(true).a(() -> !this.d.m()).a().b();

    private static String a(ItemEntity var0) {
        ItemStack var1 = var0.getItem();
        return var1.getDisplayName().getString() + " * " + var1.getCount();
    }

    private boolean a(ItemStack var1) {
        if (var1 == null) {
            return false;
        } else if (var1.isEmpty()) {
            return false;
        } else if (this.d.m()) {
            return true;
        } else {
            if (this.e.m()) {
                if (p.f(var1)) {
                    return true;
                }

                if (var1.getItem() instanceof EnchantedGoldenAppleItem) {
                    return true;
                }

                if (p.c(var1)) {
                    return true;
                }
            }

            if (this.f.m() && var1.getItem() == Items.DIAMOND) {
                return true;
            } else if (this.g.m() && var1.getItem() == Items.GOLD_INGOT) {
                return true;
            } else if (this.h.m() && var1.getItem() == Items.IRON_INGOT) {
                return true;
            } else if (this.i.m() && var1.getItem() == Items.ENDER_PEARL) {
                return true;
            } else if (this.j.m() && var1.getItem() == Items.GOLDEN_APPLE) {
                return true;
            } else if (this.k.m()) {
                if (var1.getItem() instanceof BlockItem && var1.getCount() < 8) {
                    return false;
                } else {
                    return (var1.getItem() instanceof SnowballItem || var1.getItem() instanceof EggItem) && var1.getCount() < 3
                        ? false
                        : ChestStealer.a(var1);
                }
            } else {
                return false;
            }
        }
    }

    private boolean b(ItemStack var1) {
        if (p.f(var1)) {
            return true;
        } else {
            return var1.getItem() instanceof EnchantedGoldenAppleItem ? true : var1.getItem() instanceof EndCrystalItem || p.c(var1);
        }
    }

    private void a(float var1) {
        this.m.clear();

        for (Entity var3 : a.level.entitiesForRendering()) {
            if (var3 instanceof ItemEntity var4 && this.a(var4.getItem())) {
                double var5 = a(var1, var3.xo, var3.getX());
                double var7 = a(var1, var3.yo, var3.getY()) + var3.getBbHeight() + 0.5;
                double var9 = a(var1, var3.zo, var3.getZ());
                com.heypixel.heypixelmod.obsoverlay.utils.f.b var11 = aa.a(var5, var7, var9, var1);
                var11.b(var11.b() - 2.0F);
                this.m.put(var4, var11);
            }
        }
    }

    @EventTarget
    public void update(com.heypixel.heypixelmod.obsoverlay.events.impl.p var1) {
        try {
            this.a(var1.a());
        } catch (Exception var3) {
        }
    }

    @EventTarget
    public void onShader(EventShader var1) {
        for (Vector4f var3 : this.n) {
            ad.a(var1.stack(), var3.x(), var3.y(), var3.z(), var3.w(), this.l);
        }
    }

    @EventTarget
    public void on2DRender(EventRender2D var1) {
        try {
            PoseStack var2 = var1.stack();
            this.n.clear();

            for (ItemEntity var4 : this.m.keySet()) {
                if (var4 != null) {
                    com.heypixel.heypixelmod.obsoverlay.utils.f.b var5 = this.m.get(var4);
                    var2.pushPose();
                    com.heypixel.heypixelmod.obsoverlay.utils.renderer.text.c var6 = com.heypixel.heypixelmod.obsoverlay.utils.renderer.d.a;
                    String var7 = a(var4);
                    float var8 = var6.a(var7, this.c.q()) + 8.0F;
                    this.n.add(new Vector4f(var5.a - var8 / 2.0F, var5.b - 14.0F, var5.a + var8 / 2.0F, var5.b));
                    if (this.b(var4.getItem())) {
                        var6.a(var2, var7, var5.a - var8 / 2.0F + 4.0F, var5.b - 12.0F, Color.RED, true, this.c.q());
                    } else {
                        var6.a(var2, var7, var5.a - var8 / 2.0F + 4.0F, var5.b - 12.0F, Color.WHITE, true, this.c.q());
                    }

                    var2.popPose();
                }
            }
        } catch (Exception var9) {
        }
    }

    public static float a(float var0, float var1, float var2) {
        return var1 + var0 * (var2 - var1);
    }

    public static double a(double var0, double var2, float var4) {
        return var2 + var0 * (var4 - var2);
    }

    public static double a(float var0, double var1, double var3) {
        return var1 + var0 * (var3 - var1);
    }
}
