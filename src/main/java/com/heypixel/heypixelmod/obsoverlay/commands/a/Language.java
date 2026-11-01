package com.heypixel.heypixelmod.obsoverlay.commands.a;

import com.heypixel.heypixelmod.obsoverlay.EixClient;
import com.heypixel.heypixelmod.obsoverlay.commands.Command;
import com.heypixel.heypixelmod.obsoverlay.commands.CommandInfo;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.api.b.a;
import com.heypixel.heypixelmod.obsoverlay.events.impl.k;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.LanguageSelectScreen;

@CommandInfo(
    a = "language",
    b = "Open language gui.",
    c = {"lang"}
)
public class Language extends Command {
    @Override
    public void a(String[] var1) {
        EixClient.a().b().a(new Object() {
            @EventTarget
            public void onMotion(k var1) {
                if (var1.b() == com.heypixel.heypixelmod.obsoverlay.events.api.b.a.a) {
                    Minecraft.getInstance().setScreen(new LanguageSelectScreen(null, Minecraft.getInstance().options, Minecraft.getInstance().getLanguageManager()));
                    EixClient.a().b().b(this);
                }
            }
        });
    }

    @Override
    public String[] b(String[] var1) {
        return new String[0];
    }
}
