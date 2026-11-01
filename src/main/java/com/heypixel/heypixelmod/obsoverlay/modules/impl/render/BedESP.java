package com.heypixel.heypixelmod.obsoverlay.modules.impl.render;

import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.k;
import com.heypixel.heypixelmod.obsoverlay.events.impl.p;
import com.heypixel.heypixelmod.obsoverlay.events.impl.u;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.a;
import com.heypixel.heypixelmod.obsoverlay.modules.b;
import com.heypixel.heypixelmod.obsoverlay.utils.BlockShapeUtil;
import com.heypixel.heypixelmod.obsoverlay.utils.ad;
import com.heypixel.heypixelmod.obsoverlay.utils.g;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.level.block.entity.BedBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;

@ModuleInfo(
    a = "BedESP",
    b = "床透视",
    c = "Highlights beds",
    d = com.heypixel.heypixelmod.obsoverlay.modules.a.RENDER
)
public class BedESP extends b {
    private static final float[] c = new float[]{1.0F, 0.0F, 0.0F};
    private final List<AABB> d = new CopyOnWriteArrayList<>();

    @EventTarget
    public void onRespawn(u var1) {
        this.d.clear();
    }

    @EventTarget
    public void onTick(k var1) {
        if (var1.b() == com.heypixel.heypixelmod.obsoverlay.events.api.b.a.a) {
            java.util.ArrayList var2 = com.heypixel.heypixelmod.obsoverlay.utils.g.a().collect(Collectors.toCollection(java.util.ArrayList::new));
            this.d.clear();

            for (BlockEntity var4 : var2) {
                if (var4 instanceof BedBlockEntity var5) {
                    AABB var6 = BlockShapeUtil.a(var5.getBlockPos());
                    this.d.add(var6);
                }
            }
        }
    }

    @EventTarget
    public void onRender(p var1) {
        PoseStack var2 = var1.b();
        var2.pushPose();
        RenderSystem.disableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionShader);
        Tesselator var3 = RenderSystem.renderThreadTesselator();
        BufferBuilder var4 = var3.getBuilder();
        RenderSystem.setShaderColor(c[0], c[1], c[2], 0.25F);

        for (AABB var6 : this.d) {
            ad.a(var4, var2.last().pose(), var6);
        }

        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        var2.popPose();
    }
}
