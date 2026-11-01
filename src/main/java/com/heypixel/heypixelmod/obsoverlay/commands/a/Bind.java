package com.heypixel.heypixelmod.obsoverlay.commands.a;

import com.heypixel.heypixelmod.obsoverlay.EixClient;
import com.heypixel.heypixelmod.obsoverlay.commands.Command;
import com.heypixel.heypixelmod.obsoverlay.commands.CommandInfo;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.j;
import com.heypixel.heypixelmod.obsoverlay.modules.b;
import com.heypixel.heypixelmod.obsoverlay.utils.ChatUtil;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.InputConstants.Key;

@CommandInfo(
    a = "bind",
    b = "Bind a command to a key",
    c = {"b"}
)
public class Bind extends Command {
    @Override
    public void a(String[] var1) {
        if (var1.length == 1) {
            final String var2 = var1[0];

            try {
                final b var3 = EixClient.a().g().c(var2);
                if (var3 != null) {
                    ChatUtil.a("Press a key to bind " + var2 + " to.");
                    EixClient.a().b().a(new Object() {
                        @EventTarget
                        public void onKey(j var1) {
                            if (var1.c()) {
                                var3.b(var1.b());
                                Key var2x = InputConstants.getKey(var1.b(), 0);
                                String var3x = var2x.getDisplayName().getString().toUpperCase();
                                ChatUtil.a("Bound " + var2 + " to " + var3x + ".");
                                EixClient.a().b().b(this);
                                EixClient.a().i().c();
                            }
                        }
                    });
                } else {
                    ChatUtil.a("Invalid module.");
                }
            } catch (com.heypixel.heypixelmod.obsoverlay.a.b var7) {
                ChatUtil.a("Invalid module.");
            }
        } else if (var1.length == 2) {
            String var8 = var1[0];
            String var9 = var1[1];

            try {
                b var4 = EixClient.a().g().c(var8);
                if (var4 != null) {
                    if (var9.equalsIgnoreCase("none")) {
                        var4.b(InputConstants.UNKNOWN.getValue());
                        ChatUtil.a("Unbound " + var8 + ".");
                        EixClient.a().i().c();
                    } else {
                        Key var5 = InputConstants.getKey("key.keyboard." + var9.toLowerCase());
                        if (var5 != InputConstants.UNKNOWN) {
                            var4.b(var5.getValue());
                            ChatUtil.a("Bound " + var8 + " to " + var9.toUpperCase() + ".");
                            EixClient.a().i().c();
                        } else {
                            ChatUtil.a("Invalid key.");
                        }
                    }
                } else {
                    ChatUtil.a("Invalid module.");
                }
            } catch (com.heypixel.heypixelmod.obsoverlay.a.b var6) {
                ChatUtil.a("Invalid module.");
            }
        } else {
            ChatUtil.a("Usage: .bind <module> [key]");
        }
    }

    @Override
    public String[] b(String[] var1) {
        return EixClient.a()
            .g()
            .a()
            .stream()
            .map(b::h)
            .filter(var1x -> var1x.toLowerCase().startsWith(var1.length == 0 ? "" : var1[0].toLowerCase()))
            .toArray(String[]::new);
    }
}
