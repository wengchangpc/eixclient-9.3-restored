package com.heypixel.heypixelmod.obsoverlay.commands.a;

import com.heypixel.heypixelmod.obsoverlay.EixClient;
import com.heypixel.heypixelmod.obsoverlay.commands.Command;
import com.heypixel.heypixelmod.obsoverlay.commands.CommandInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.b;
import com.heypixel.heypixelmod.obsoverlay.utils.ChatUtil;

@CommandInfo(
    a = "toggle",
    b = "Toggle a module",
    c = {"t"}
)
public class Toggle extends Command {
    @Override
    public void a(String[] var1) {
        if (var1.length == 1) {
            String var2 = var1[0];

            try {
                b var3 = EixClient.a().g().c(var2);
                if (var3 != null) {
                    var3.f();
                } else {
                    ChatUtil.a("Invalid module.");
                }
            } catch (com.heypixel.heypixelmod.obsoverlay.a.b var4) {
                ChatUtil.a("Invalid module.");
            }
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
