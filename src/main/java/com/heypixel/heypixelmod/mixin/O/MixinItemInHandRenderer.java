package com.heypixel.heypixelmod.mixin.O;

import com.heypixel.heypixelmod.obsoverlay.EixClient;
import com.heypixel.heypixelmod.obsoverlay.events.api.a.b;
import com.heypixel.heypixelmod.obsoverlay.events.impl.af;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({ItemInHandRenderer.class})
public class MixinItemInHandRenderer {
    @Redirect(
        method = {"tick"},
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/player/LocalPlayer;getMainHandItem()Lnet/minecraft/world/item/ItemStack;"
        )
    )
    public ItemStack hookMainHand(LocalPlayer var1) {
        af var2 = new af(InteractionHand.MAIN_HAND, var1.getMainHandItem());
        if (var1 == Minecraft.getInstance().player) {
            EixClient.a().b().a((b)var2);
        }

        return var2.b();
    }

    @Redirect(
        method = {"tick"},
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/player/LocalPlayer;getOffhandItem()Lnet/minecraft/world/item/ItemStack;"
        )
    )
    public ItemStack hookOffHand(LocalPlayer var1) {
        af var2 = new af(InteractionHand.OFF_HAND, var1.getOffhandItem());
        if (var1 == Minecraft.getInstance().player) {
            EixClient.a().b().a((b)var2);
        }

        return var2.b();
    }
}
