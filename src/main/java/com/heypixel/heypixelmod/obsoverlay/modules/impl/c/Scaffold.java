package com.heypixel.heypixelmod.obsoverlay.modules.impl.c;

import com.heypixel.heypixelmod.obsoverlay.d.d;
import com.heypixel.heypixelmod.obsoverlay.d.a.c;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventRunTicks;
import com.heypixel.heypixelmod.obsoverlay.events.impl.k;
import com.heypixel.heypixelmod.obsoverlay.events.impl.l;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.a;
import com.heypixel.heypixelmod.obsoverlay.modules.b;
import com.heypixel.heypixelmod.obsoverlay.utils.ad;
import com.heypixel.heypixelmod.obsoverlay.utils.p;
import com.heypixel.heypixelmod.obsoverlay.utils.s;
import com.heypixel.heypixelmod.obsoverlay.utils.u;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.FungusBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

@ModuleInfo(
    a = "Scaffold",
    b = "自动搭路",
    c = "Automatically places blocks under you",
    d = com.heypixel.heypixelmod.obsoverlay.modules.a.MOVEMENT
)
public class Scaffold extends b {
    public static final List<Block> c = Arrays.asList(
        Blocks.AIR,
        Blocks.WATER,
        Blocks.LAVA,
        Blocks.ENCHANTING_TABLE,
        Blocks.GLASS_PANE,
        Blocks.GLASS_PANE,
        Blocks.IRON_BARS,
        Blocks.SNOW,
        Blocks.COAL_ORE,
        Blocks.DIAMOND_ORE,
        Blocks.EMERALD_ORE,
        Blocks.CHEST,
        Blocks.TRAPPED_CHEST,
        Blocks.TORCH,
        Blocks.ANVIL,
        Blocks.TRAPPED_CHEST,
        Blocks.NOTE_BLOCK,
        Blocks.JUKEBOX,
        Blocks.TNT,
        Blocks.GOLD_ORE,
        Blocks.IRON_ORE,
        Blocks.LAPIS_ORE,
        Blocks.STONE_PRESSURE_PLATE,
        Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE,
        Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE,
        Blocks.STONE_BUTTON,
        Blocks.LEVER,
        Blocks.TALL_GRASS,
        Blocks.TRIPWIRE,
        Blocks.TRIPWIRE_HOOK,
        Blocks.RAIL,
        Blocks.CORNFLOWER,
        Blocks.RED_MUSHROOM,
        Blocks.BROWN_MUSHROOM,
        Blocks.VINE,
        Blocks.SUNFLOWER,
        Blocks.LADDER,
        Blocks.FURNACE,
        Blocks.SAND,
        Blocks.CACTUS,
        Blocks.DISPENSER,
        Blocks.DROPPER,
        Blocks.CRAFTING_TABLE,
        Blocks.COBWEB,
        Blocks.PUMPKIN,
        Blocks.COBBLESTONE_WALL,
        Blocks.OAK_FENCE,
        Blocks.REDSTONE_TORCH,
        Blocks.FLOWER_POT
    );
    com.heypixel.heypixelmod.obsoverlay.d.a.a d = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Telly").a(true).a().b();
    com.heypixel.heypixelmod.obsoverlay.d.a.a e = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Snap").a(false).a(() -> !this.d.m()).a().b();
    c f = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Rotation Speed").a(180.0F).d(1.0F).b(1.0F).c(180.0F).a().c();
    c g = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Rotation Back Speed").a(180.0F).d(1.0F).b(1.0F).c(180.0F).a(this.d::m).a().c();
    c h = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Telly Ticks").a(1.0F).d(1.0F).b(0.0F).c(6.0F).a(this.d::m).a().c();
    com.heypixel.heypixelmod.obsoverlay.d.a.a i = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "SafeWalk").a(true).a(() -> !this.d.m()).a().b();
    com.heypixel.heypixelmod.obsoverlay.d.a.a j = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "ESP").a(true).a().b();
    private int k;
    private int l;
    private BlockPos m;
    private Direction n;
    private int o = -1;

    public static Vec3 a(BlockPos var0, Direction var1) {
        double var2 = var0.getX() + 0.5;
        double var4 = var0.getY() + 0.5;
        double var6 = var0.getZ() + 0.5;
        if (var1 != Direction.UP && var1 != Direction.DOWN) {
            var4 += 0.08;
        } else {
            var2 += s.a(0.3, -0.3);
            var6 += s.a(0.3, -0.3);
        }

        if (var1 == Direction.WEST || var1 == Direction.EAST) {
            var6 += s.a(0.3, -0.3);
        }

        if (var1 == Direction.SOUTH || var1 == Direction.NORTH) {
            var2 += s.a(0.3, -0.3);
        }

        return new Vec3(var2, var4, var6);
    }

    public static boolean a(ItemStack var0) {
        if (var0 == null || !(var0.getItem() instanceof BlockItem) || var0.getCount() <= 1) {
            return false;
        } else if (!p.k(var0)) {
            return false;
        } else {
            String var1 = var0.getDisplayName().getString();
            if (var1.contains("Click") || var1.contains("点击")) {
                return false;
            } else if (var0.getItem() instanceof ItemNameBlockItem) {
                return false;
            } else {
                Block var2 = ((BlockItem)var0.getItem()).getBlock();
                if (var2 instanceof FlowerBlock) {
                    return false;
                } else if (var2 instanceof BushBlock) {
                    return false;
                } else if (var2 instanceof FungusBlock) {
                    return false;
                } else {
                    return var2 instanceof CropBlock ? false : !(var2 instanceof SlabBlock) && !c.contains(var2);
                }
            }
        }
    }

    @EventTarget
    public void onPreTick(EventRunTicks var1) {
        if (a.player != null && a.level != null && var1.type() == com.heypixel.heypixelmod.obsoverlay.events.api.b.a.a) {
            int var2 = -1;

            for (int var3 = 0; var3 < 9; var3++) {
                ItemStack var4 = a.player.getInventory().getItem(var3);
                if (a(var4)) {
                    var2 = var3;
                    break;
                }
            }

            if (var2 != -1 && a.player.getInventory().selected != var2) {
                a.player.getInventory().selected = var2;
            }

            if (a.player.onGround()) {
                this.l = (int)Math.floor(a.player.getY()) - 1;
            }

            this.r();
            if (this.d.m()) {
                if (a.player.onGround()) {
                    this.k = 0;
                    this.m = null;
                    this.n = null;
                    com.heypixel.heypixelmod.obsoverlay.utils.c.b var5 = new com.heypixel.heypixelmod.obsoverlay.utils.c.b(
                        a.player.getYRot(), a.player.getXRot()
                    );
                    com.heypixel.heypixelmod.obsoverlay.utils.c.c.a(var5, this.g.q());
                } else {
                    if (this.k >= this.h.q()) {
                        com.heypixel.heypixelmod.obsoverlay.utils.c.b var6 = this.b(this.m, this.n);
                        com.heypixel.heypixelmod.obsoverlay.utils.c.c.a(var6, this.f.q());
                        this.p();
                    }

                    this.k++;
                }

                this.a("Telly");
            } else {
                if (this.m == null) {
                    com.heypixel.heypixelmod.obsoverlay.utils.c.c.a(
                        new com.heypixel.heypixelmod.obsoverlay.utils.c.b(Mth.wrapDegrees(a.player.getYRot() - 180.0F), 89.64F), this.f.q()
                    );
                }

                if (this.s() || !this.e.m()) {
                    com.heypixel.heypixelmod.obsoverlay.utils.c.b var7 = this.b(this.m, this.n);
                    com.heypixel.heypixelmod.obsoverlay.utils.c.c.a(var7, this.f.q());
                }

                this.p();
                this.a(this.e.m() ? "Snap" : "Normal");
            }
        }
    }

    @Override
    public void p() {
        if (this.s()) {
            boolean var1 = com.heypixel.heypixelmod.obsoverlay.utils.c.a.a(com.heypixel.heypixelmod.obsoverlay.utils.c.c.j(), this.m);
            if (var1) {
                InteractionResult var2 = a.gameMode
                    .useItemOn(a.player, InteractionHand.MAIN_HAND, new BlockHitResult(a(this.m, this.n), this.n, this.m, false));
                if (var2 == InteractionResult.SUCCESS) {
                    a.player.swing(InteractionHand.MAIN_HAND);
                }
            }
        }
    }

    @EventTarget
    public void onMoveInput(l var1) {
        if (a.player.onGround() && !a.options.keyJump.isDown() && u.a() && this.d.m()) {
            var1.a(true);
        }
    }

    public int q() {
        return !a.options.keyJump.isDown() && u.a() && a.player.fallDistance <= 0.25 && this.d.m() ? this.l : (int)Math.floor(a.player.getY()) - 1;
    }

    public void r() {
        Vec3 var1 = a.player.getEyePosition();
        BlockPos var2 = BlockPos.containing(var1.x, this.q(), var1.z);
        int var3 = var2.getX();
        int var4 = var2.getZ();
        if (!this.a(a.level.getBlockState(var2), a.level, var2)) {
            if (!this.a(var1, var2)) {
                for (int var5 = 1; var5 <= 6; var5++) {
                    if (this.a(var1, new BlockPos(var3, this.q() - var5, var4))) {
                        return;
                    }

                    for (int var6 = 0; var6 <= var5; var6++) {
                        for (int var7 = 0; var7 <= var5 - var6; var7++) {
                            int var8 = var5 - var6 - var7;

                            for (int var9 = 0; var9 <= 1; var9++) {
                                for (int var10 = 0; var10 <= 1; var10++) {
                                    if (this.a(var1, new BlockPos(var3 + (var9 == 0 ? var6 : -var6), this.q() - var8, var4 + (var10 == 0 ? var7 : -var7)))) {
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean a(BlockState var1, Level var2, BlockPos var3) {
        boolean var4 = !var1.getCollisionShape(var2, var3).isEmpty();
        boolean var5 = var1.getMenuProvider(var2, var3) == null;
        return var4 && var5;
    }

    private boolean a(Vec3 var1, BlockPos var2) {
        if (!(a.level.getBlockState(var2).getBlock() instanceof AirBlock) && !(a.level.getBlockState(var2).getBlock() instanceof WaterlilyBlock)) {
            return false;
        } else if (var2.getY() > this.q()) {
            return false;
        } else {
            Vec3 var3 = new Vec3(var2.getX() + 0.5, var2.getY(), var2.getZ() + 0.5);

            for (Direction var7 : Direction.values()) {
                Vec3 var8 = var3.add(new Vec3(var7.getNormal().getX(), var7.getNormal().getY(), var7.getNormal().getZ()).scale(0.5));
                BlockPos var9 = var2.offset(var7.getNormal());
                BlockPos var10 = new BlockPos(var9.getX(), var9.getY(), var9.getZ());
                if (this.a(a.level.getBlockState(var10), a.level, var10)) {
                    Vec3 var11 = var8.subtract(var1);
                    if (var11.lengthSqr() <= 20.25
                        && var11.dot(new Vec3(var7.getNormal().getX(), var7.getNormal().getY(), var7.getNormal().getZ())) >= 0.0
                        && (var7.getOpposite() != Direction.UP || !u.a() || a.options.keyJump.isDown())) {
                        this.m = new BlockPos(var9);
                        this.n = var7.getOpposite();
                        return true;
                    }
                }
            }

            return false;
        }
    }

    public BlockPos a(double var1, double var3, double var5) {
        return new BlockPos((int)Math.floor(var1), (int)Math.floor(var3), (int)Math.floor(var5));
    }

    @EventTarget
    public void onMotion(k var1) {
        if (var1.b() == com.heypixel.heypixelmod.obsoverlay.events.api.b.a.a && this.i.m() && !this.d.m()) {
            a.options.keyShift.setDown(a.player.onGround() && SafeWalk.a(0.3F));
        }
    }

    @EventTarget
    public void onRender(com.heypixel.heypixelmod.obsoverlay.events.impl.p var1) {
        if (this.m != null && this.j.m()) {
            PoseStack var2 = var1.b();
            var2.pushPose();
            Vec3 var3 = ad.b();
            var2.translate(-var3.x, -var3.y, -var3.z);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.setShader(GameRenderer::getPositionShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 0.4F);
            AABB var4 = new AABB(this.m);
            ad.a(var4, var2);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
            RenderSystem.disableBlend();
            var2.popPose();
        }
    }

    @Override
    public void d() {
        if (a.player != null) {
            this.o = a.player.getInventory().selected;
        }

        this.k = 0;
        this.m = null;
        this.n = null;
    }

    @Override
    public void e() {
        boolean var1 = InputConstants.isKeyDown(a.getWindow().getWindow(), a.options.keyShift.getKey().getValue());
        a.options.keyShift.setDown(var1);
        if (a.player != null && this.o != -1) {
            a.player.getInventory().selected = this.o;
        }
    }

    public com.heypixel.heypixelmod.obsoverlay.utils.c.b b(BlockPos var1, Direction var2) {
        com.heypixel.heypixelmod.obsoverlay.utils.c.b var3 = this.s()
            ? com.heypixel.heypixelmod.obsoverlay.utils.c.d.a(var1, var2)
            : com.heypixel.heypixelmod.obsoverlay.utils.c.d.a(var1.getCenter());
        com.heypixel.heypixelmod.obsoverlay.utils.c.b var4 = new com.heypixel.heypixelmod.obsoverlay.utils.c.b(
            Mth.wrapDegrees(a.player.getYRot() - 180.0F), var3.b()
        );
        boolean var5 = com.heypixel.heypixelmod.obsoverlay.utils.c.a.a(var4, var1);
        return var5 ? var4 : var3;
    }

    private boolean s() {
        Vec3 var1 = a.player.getEyePosition();
        BlockPos var2 = BlockPos.containing(var1.x, this.q(), var1.z);
        return a.level.getBlockState(var2).getBlock() instanceof AirBlock || a.level.getBlockState(var2).getBlock() instanceof WaterlilyBlock;
    }
}
