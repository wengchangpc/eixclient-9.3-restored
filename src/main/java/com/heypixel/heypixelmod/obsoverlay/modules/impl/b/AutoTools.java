package com.heypixel.heypixelmod.obsoverlay.modules.impl.b;

import com.heypixel.heypixelmod.obsoverlay.d.d;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.af;
import com.heypixel.heypixelmod.obsoverlay.events.impl.k;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.a;
import com.heypixel.heypixelmod.obsoverlay.modules.b;
import com.heypixel.heypixelmod.obsoverlay.utils.p;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.RedStoneOreBlock;
import net.minecraft.world.level.block.WebBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult.Type;

@ModuleInfo(
    a = "AutoTools",
    b = "自动工具",
    c = "Automatically switches to the best tool for the job",
    d = com.heypixel.heypixelmod.obsoverlay.modules.a.MISC
)
public class AutoTools extends b {
    private final com.heypixel.heypixelmod.obsoverlay.d.a.a c = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Check Sword").a(false).a().b();
    private final com.heypixel.heypixelmod.obsoverlay.d.a.a d = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Switch Back").a(true).a().b();
    private final com.heypixel.heypixelmod.obsoverlay.d.a.a e = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Silent").a(this.d::m).a(true).a().b();
    private int f = -1;

    @EventTarget
    public void onUpdateHeldItem(af var1) {
        if (this.d.m() && this.e.m() && var1.a() == InteractionHand.MAIN_HAND && this.f != -1) {
            var1.a(a.player.getInventory().getItem(this.f));
        }
    }

    @EventTarget
    public void onMotion(k var1) {
        if (var1.b() == com.heypixel.heypixelmod.obsoverlay.events.api.b.a.a) {
            if (a.gameMode.isDestroying()) {
                if (this.c.m()) {
                    ItemStack var2 = a.player.getMainHandItem();
                    if (var2.getItem() instanceof SwordItem) {
                        return;
                    }
                }

                if (a.hitResult.getType() == Type.BLOCK) {
                    BlockHitResult var4 = (BlockHitResult)a.hitResult;
                    int var3 = this.a(var4.getBlockPos());
                    if (var3 != -1 && var3 != a.player.getInventory().selected) {
                        this.f = a.player.getInventory().selected;
                        a.player.getInventory().selected = var3;
                    }
                }
            }
        } else if (!a.gameMode.isDestroying() && this.d.m() && this.f != -1) {
            a.player.getInventory().selected = this.f;
            this.f = -1;
        }
    }

    private int a(BlockPos var1) {
        BlockState var2 = a.level.getBlockState(var1);
        Block var3 = var2.getBlock();
        int var4 = 0;
        float var5 = 1.0F;

        for (int var6 = 0; var6 < 9; var6++) {
            ItemStack var7 = a.player.getInventory().getItem(var6);
            if (!p.u(var7) && !var7.isEmpty() && !var2.isAir() && (!(var7.getItem() instanceof SwordItem) || var3 instanceof WebBlock)) {
                float var8 = var7.getItem().getDestroySpeed(var7, var2);
                if (var8 > 1.0F && !(var3 instanceof DropExperienceBlock) && !(var3 instanceof RedStoneOreBlock)) {
                    int var9 = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_EFFICIENCY, var7);
                    if (var9 > 0) {
                        var8 += var9 * var9 + 1;
                    }
                }

                if (var8 > var5) {
                    var4 = var6;
                    var5 = var8;
                }
            }
        }

        return var5 > 1.0F ? var4 : -1;
    }
}
