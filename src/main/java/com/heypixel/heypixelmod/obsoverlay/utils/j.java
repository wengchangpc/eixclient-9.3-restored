package com.heypixel.heypixelmod.obsoverlay.utils;

import com.heypixel.heypixelmod.obsoverlay.EixClient;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.api.a.b;
import com.heypixel.heypixelmod.obsoverlay.events.api.b.a;
import com.heypixel.heypixelmod.obsoverlay.events.impl.e;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.RenderGuiEvent.Post;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class j {
    @SubscribeEvent
    public void a(Post var1) {
    }

    @SubscribeEvent
    public void a(ClientChatEvent var1) {
        e var2 = new e(var1.getMessage());
        EixClient.a().b().a((b)var2);
        if (var2.a()) {
            var1.setCanceled(true);
        }
    }

    @EventTarget
    public void onMotion(com.heypixel.heypixelmod.obsoverlay.events.impl.k var1) {
        if (var1.b() == a.a && Minecraft.getInstance().player.tickCount <= 1) {
            EixClient.a().b().a((b)(new com.heypixel.heypixelmod.obsoverlay.events.impl.u()));
        }
    }
}
