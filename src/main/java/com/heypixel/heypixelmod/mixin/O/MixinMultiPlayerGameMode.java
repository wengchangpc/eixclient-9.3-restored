package com.heypixel.heypixelmod.mixin.O;

import com.heypixel.heypixelmod.obsoverlay.EixClient;
import com.heypixel.heypixelmod.obsoverlay.events.api.a.b;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventDestroyBlock;
import com.heypixel.heypixelmod.obsoverlay.events.impl.n;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({MultiPlayerGameMode.class})
public class MixinMultiPlayerGameMode {
    @Redirect(
        method = {"useItem"},
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/multiplayer/ClientPacketListener;send(Lnet/minecraft/network/protocol/Packet;)V",
            ordinal = 0
        )
    )
    public void onSendPacket(ClientPacketListener var1, Packet<?> var2) {
        n var3 = new n(var2);
        EixClient.a().b().a((b)var3);
        if (!var3.a()) {
            var1.send(var3.b());
        }
    }

    @Inject(
        method = {"startDestroyBlock"},
        at = {@At("HEAD")}
    )
    public void onStartDestroyBlock(BlockPos var1, Direction var2, CallbackInfoReturnable<Boolean> var3) {
        EixClient.a().b().a((b)(new EventDestroyBlock(var1, var2)));
    }
}
