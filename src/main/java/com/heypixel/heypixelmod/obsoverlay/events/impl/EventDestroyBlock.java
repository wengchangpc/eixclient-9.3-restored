package com.heypixel.heypixelmod.obsoverlay.events.impl;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public record EventDestroyBlock(BlockPos pos, Direction face) implements com.heypixel.heypixelmod.obsoverlay.events.api.a.b {
}
