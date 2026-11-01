package com.heypixel.heypixelmod.obsoverlay.utils;

import com.heypixel.heypixelmod.obsoverlay.EixClient;
import com.heypixel.heypixelmod.obsoverlay.c.a.a;
import com.heypixel.heypixelmod.obsoverlay.c.a.b;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
import net.minecraft.network.protocol.game.ClientboundPingPacket;
import net.minecraft.network.protocol.game.ClientboundSetPlayerTeamPacket;
import net.minecraft.network.protocol.game.ClientboundSetTimePacket;
import net.minecraft.network.protocol.game.ServerboundCustomPayloadPacket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class w {
    public static final Logger a = LogManager.getLogger("PacketUtil");
    private static final al c = new al();
    private static final a d = new a(com.heypixel.heypixelmod.obsoverlay.c.a.b.c, "Server lagging!", 2000L);
    public static Set<Packet<?>> b = new HashSet<>();
    private static long e = 0L;

    public static boolean a() {
        return c.a(500.0);
    }

    public static void a(Packet<?> var0) {
        a.info("Sending: " + var0.getClass().getName());
        if (var0 instanceof ServerboundCustomPayloadPacket var1) {
            a.info("RE custompayload, {}", var1.getIdentifier().toString());
            if (var1.getIdentifier().toString().equals("heypixelmod:s2cevent")) {
                FriendlyByteBuf var2 = var1.getData();
                var2.markReaderIndex();
                int var3 = var2.readVarInt();
                a.info("after packet ({}", var3);
                if (var3 == 2) {
                    a.info("after packet");
                    a.info(Arrays.toString(t.a(var2, var2.readableBytes())));
                }

                var2.resetReaderIndex();
            }
        }

        b.add(var0);
        Minecraft.getInstance().getConnection().send(var0);
    }

    @EventTarget
    public void onMotion(com.heypixel.heypixelmod.obsoverlay.events.impl.k var1) {
        if (var1.b() == com.heypixel.heypixelmod.obsoverlay.events.api.b.a.a) {
            if (a()) {
                EixClient.a().j().a(d);
                d.b(System.currentTimeMillis());
                d.a(com.heypixel.heypixelmod.obsoverlay.c.a.b.c);
                e = Math.round(c.d());
                d.a("Server lagging. Aura disabled! (" + e + "ms)");
            } else {
                d.a(com.heypixel.heypixelmod.obsoverlay.c.a.b.a);
                d.a("Server currently online! (" + e + "ms)");
            }
        }
    }

    @EventTarget(
        a = 4
    )
    public void onGlobalPacket(com.heypixel.heypixelmod.obsoverlay.events.impl.g var1) {
        if (var1.c() instanceof ClientboundPingPacket
            || var1.c() instanceof ClientboundMoveEntityPacket
            || var1.c() instanceof ClientboundSetTimePacket
            || var1.c() instanceof ClientboundSetPlayerTeamPacket) {
            c.a();
        }

        if (!var1.a()) {
            Packet var2 = var1.c();
            com.heypixel.heypixelmod.obsoverlay.events.impl.m var3 = new com.heypixel.heypixelmod.obsoverlay.events.impl.m(var1.b(), var2);
            EixClient.a().b().a((com.heypixel.heypixelmod.obsoverlay.events.api.a.b)var3);
            if (var3.a()) {
                var1.a(true);
            }

            var1.a(var3.c());
        }
    }
}
