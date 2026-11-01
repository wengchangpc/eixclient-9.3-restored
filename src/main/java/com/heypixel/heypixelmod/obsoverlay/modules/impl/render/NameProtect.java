package com.heypixel.heypixelmod.obsoverlay.modules.impl.render;

import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.t;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.a;
import com.heypixel.heypixelmod.obsoverlay.modules.b;
import net.minecraft.network.chat.Component;
import org.apache.commons.lang3.StringUtils;

@ModuleInfo(
    a = "NameProtect",
    b = "名称保护",
    c = "Protect your name",
    d = com.heypixel.heypixelmod.obsoverlay.modules.a.RENDER
)
public class NameProtect extends b {
    public static NameProtect c;

    public NameProtect() {
        c = this;
    }

    public static String b(String var0) {
        if (c.m() && a.player != null) {
            return var0.contains(a.player.getName().getString()) ? StringUtils.replace(var0, a.player.getName().getString(), "§dAlpha§7") : var0;
        } else {
            return var0;
        }
    }

    @EventTarget
    public void onRenderTab(t var1) {
        var1.a(Component.literal(b(var1.b().getString())));
    }
}
