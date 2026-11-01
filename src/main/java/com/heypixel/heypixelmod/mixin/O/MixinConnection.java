package com.heypixel.heypixelmod.mixin.O;

import com.heypixel.heypixelmod.obsoverlay.EixClient;
import com.heypixel.heypixelmod.obsoverlay.events.api.a.b;
import com.heypixel.heypixelmod.obsoverlay.events.api.b.a;
import com.heypixel.heypixelmod.obsoverlay.events.impl.g;
import com.heypixel.heypixelmod.obsoverlay.utils.n;
import com.heypixel.heypixelmod.obsoverlay.utils.w;
import io.netty.channel.ChannelFuture;
import io.netty.channel.SimpleChannelInboundHandler;
import java.io.IOException;
import java.net.InetSocketAddress;
import net.minecraft.network.Connection;
import net.minecraft.network.PacketListener;
import net.minecraft.network.PacketSendListener;
import net.minecraft.network.protocol.Packet;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Connection.class})
public abstract class MixinConnection extends SimpleChannelInboundHandler<Packet<?>> {
    @Shadow
    @Final
    private static Logger LOGGER;

    @Shadow
    private static <T extends PacketListener> void genericsFtw(Packet<T> var0, PacketListener var1) {
    }

    @Inject(
        method = {"connectToServer"},
        at = {@At("HEAD")}
    )
    private static void injectHook(InetSocketAddress var0, boolean var1, CallbackInfoReturnable<Connection> var2) {
        try {
            n.a("http://127.0.0.1:23233/api/setHook?hook=1");
        } catch (IOException var4) {
        }
    }

    @Inject(
        method = {"connect"},
        at = {@At("HEAD")}
    )
    private static void injectHook2(InetSocketAddress var0, boolean var1, Connection var2, CallbackInfoReturnable<ChannelFuture> var3) {
        try {
            n.a("http://127.0.0.1:23233/api/setHook?hook=1");
        } catch (IOException var5) {
        }
    }

    @Shadow
    protected abstract void sendPacket(Packet<?> var1, @Nullable PacketSendListener var2);

    @Redirect(
        method = {"channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/protocol/Packet;)V"},
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/network/Connection;genericsFtw(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketListener;)V"
        )
    )
    private void onGenericsFtw(Packet<?> var1, PacketListener var2) {
        g var3 = new g(a.d, var1);
        EixClient.a().b().a((b)var3);
        if (!var3.a()) {
            genericsFtw(var3.c(), var2);
        }
    }

    @Redirect(
        method = {"send(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketSendListener;)V"},
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/network/Connection;sendPacket(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketSendListener;)V"
        )
    )
    private void onSend(Connection var1, Packet<?> var2, PacketSendListener var3) {
        if (w.b.contains(var2)) {
            w.b.remove(var2);
            this.sendPacket(var2, var3);
        } else {
            g var4 = new g(a.c, var2);
            EixClient.a().b().a((b)var4);
            if (!var4.a()) {
                this.sendPacket(var4.c(), var3);
            }
        }
    }
}
