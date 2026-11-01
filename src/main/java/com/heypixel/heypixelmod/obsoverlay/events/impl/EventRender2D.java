package com.heypixel.heypixelmod.obsoverlay.events.impl;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;

public record EventRender2D(PoseStack stack, GuiGraphics guiGraphics) implements com.heypixel.heypixelmod.obsoverlay.events.api.a.b {
}
