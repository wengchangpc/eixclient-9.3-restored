package com.heypixel.heypixelmod.obsoverlay.commands;

import com.heypixel.heypixelmod.obsoverlay.EixClient;
import com.heypixel.heypixelmod.obsoverlay.commands.a.Bind;
import com.heypixel.heypixelmod.obsoverlay.commands.a.Config;
import com.heypixel.heypixelmod.obsoverlay.commands.a.Language;
import com.heypixel.heypixelmod.obsoverlay.commands.a.Music;
import com.heypixel.heypixelmod.obsoverlay.commands.a.Proxy;
import com.heypixel.heypixelmod.obsoverlay.commands.a.Toggle;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.e;
import com.heypixel.heypixelmod.obsoverlay.utils.ChatUtil;
import java.util.HashMap;
import java.util.Map;

public class b {
    public static final String a = ".";
    public final Map<String, Command> b = new HashMap<>();

    public b() {
        try {
            this.a();
        } catch (Exception var2) {
            throw new RuntimeException(var2);
        }

        EixClient.a().b().a(this);
    }

    private void a() {
        this.a(new Bind());
        this.a(new Toggle());
        this.a(new Config());
        this.a(new Language());
        this.a(new Proxy());
        this.a(new Music());
    }

    private void a(Command var1) {
        var1.a();
        this.b.put(var1.b().toLowerCase(), var1);

        for (String var5 : var1.d()) {
            this.b.put(var5.toLowerCase(), var1);
        }
    }

    @EventTarget
    public void onChat(e var1) {
        if (var1.b().startsWith(".")) {
            var1.a(true);
            String var2 = var1.b().substring(".".length());
            String[] var3 = var2.split(" ");
            if (var3.length < 1) {
                ChatUtil.a("Invalid command.");
                return;
            }

            String var4 = var3[0].toLowerCase();
            Command var5 = this.b.get(var4);
            if (var5 == null) {
                ChatUtil.a("Invalid command.");
                return;
            }

            String[] var6 = new String[var3.length - 1];
            System.arraycopy(var3, 1, var6, 0, var6.length);
            var5.a(var6);
        }
    }
}
