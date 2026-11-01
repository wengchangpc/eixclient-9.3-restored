package com.heypixel.heypixelmod.obsoverlay.modules.impl.render;

import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.r;
import com.heypixel.heypixelmod.obsoverlay.events.impl.t;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.a;
import com.heypixel.heypixelmod.obsoverlay.modules.b;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

@ModuleInfo(
    a = "ServerNameSpoof",
    b = "服务器名字伪装",
    c = "Spoof the server name",
    d = com.heypixel.heypixelmod.obsoverlay.modules.a.RENDER
)
public class ServerNameSpoof extends b {
    @EventTarget
    public void onRenderScoreboard(r var1) {
        String var2 = var1.a().getString();
        if (var2.contains("布吉岛")) {
            MutableComponent var3 = Component.literal("§e2b2t.gg");
            var3.setStyle(var1.a().getStyle());
            var1.a(var3);
        }
    }

    @EventTarget
    public void onRenderTab(t var1) {
        String var2 = var1.b().getString();
        if (var2.contains("布吉岛")) {
            if (var1.a() == com.heypixel.heypixelmod.obsoverlay.events.api.b.a.g) {
                var1.a(Component.literal("§e2b2t.gg"));
            } else if (var1.a() == com.heypixel.heypixelmod.obsoverlay.events.api.b.a.f) {
                var1.a(Component.literal(""));
            }
        }
    }
}
