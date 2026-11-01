package com.heypixel.heypixelmod.obsoverlay.b;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.heypixel.heypixelmod.obsoverlay.EixClient;
import com.heypixel.heypixelmod.obsoverlay.c.ClickGuiScreen;
import com.heypixel.heypixelmod.obsoverlay.d.f;
import com.heypixel.heypixelmod.obsoverlay.d.a.e;
import com.heypixel.heypixelmod.obsoverlay.modules.c;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.Scoreboard;
import com.heypixel.heypixelmod.obsoverlay.utils.m;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class b {
    public static final Logger logger = LogManager.getLogger(b.class);
    public static final File clientFolder = new File(System.getenv("APPDATA"), "EixClient");
    public static final File configFolder = new File(Minecraft.getInstance().gameDirectory, "config/Naven");
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final File configFile;
    private a config;

    public b() {
        if (!clientFolder.exists()) {
            clientFolder.mkdirs();
        }

        if (!configFolder.exists()) {
            configFolder.mkdirs();
        }

        this.configFile = new File(clientFolder, "config.json");
        this.config = new a();
    }

    public a a() {
        return this.config;
    }

    public void b() {
        if (this.configFile.exists()) {
            try (InputStreamReader var1 = new InputStreamReader(Files.newInputStream(this.configFile.toPath()), StandardCharsets.UTF_8)) {
                this.config = (a)this.gson.fromJson(var1, a.class);
                if (this.config == null) {
                    this.config = new a();
                }

                logger.info("Loaded config from JSON.");
            } catch (Exception var6) {
                logger.error("Failed to load JSON config!", var6);
                this.config = new a();
            }
        } else {
            logger.info("No config found. Loading defaults.");
        }

        this.e();
    }

    public void c() {
        this.f();

        try (OutputStreamWriter var1 = new OutputStreamWriter(Files.newOutputStream(this.configFile.toPath()), StandardCharsets.UTF_8)) {
            this.gson.toJson(this.config, var1);
            logger.info("Saved config to JSON.");
        } catch (Exception var6) {
            logger.error("Failed to save JSON config!", var6);
        }
    }

    public void a(String var1) {
        try {
            this.config = (a)this.gson.fromJson(var1, a.class);
            if (this.config == null) {
                this.config = new a();
            }

            this.e();
            logger.info("Loaded config from Cloud String.");
        } catch (Exception var3) {
            logger.error("Failed to load Cloud config!", var3);
        }
    }

    public String d() {
        this.f();
        return this.gson.toJson(this.config);
    }

    public void b(String var1) throws IOException {
        this.f();
        File var2 = new File(configFolder, var1 + ".json");

        try (OutputStreamWriter var3 = new OutputStreamWriter(Files.newOutputStream(var2.toPath()), StandardCharsets.UTF_8)) {
            this.gson.toJson(this.config, var3);
            logger.info("Saved config to: " + var2.getAbsolutePath());
        }
    }

    public void c(String var1) throws IOException {
        File var2 = new File(configFolder, var1 + ".json");
        if (!var2.exists()) {
            throw new FileNotFoundException("配置文件不存在: " + var1);
        } else {
            try (InputStreamReader var3 = new InputStreamReader(Files.newInputStream(var2.toPath()), StandardCharsets.UTF_8)) {
                this.config = (a)this.gson.fromJson(var3, a.class);
                if (this.config == null) {
                    this.config = new a();
                }

                this.e();
                logger.info("Loaded config from: " + var2.getAbsolutePath());
            }
        }
    }

    private void e() {
        c var1 = EixClient.a().g();
        if (this.config == null) {
            this.config = new a();
        }

        if (this.config.modules == null) {
            this.config.modules = new HashMap<>();
        }

        if (this.config.friends == null) {
            this.config.friends = new ArrayList<>();
        }

        if (this.config.gui == null) {
            this.config.gui = new a.a();
        }

        for (Entry var3 : this.config.modules.entrySet()) {
            try {
                com.heypixel.heypixelmod.obsoverlay.modules.b var4 = var1.d((String)var3.getKey());
                a.b var5 = (a.b)var3.getValue();
                if (var4 != null && var5 != null) {
                    var4.b(var5.key);
                    var4.a(var5.enabled);
                    if (var5.values != null) {
                        for (Entry var7 : var5.values.entrySet()) {
                            com.heypixel.heypixelmod.obsoverlay.d.c var8 = EixClient.a().d().a(var4, (String)var7.getKey());
                            if (var8 == null && "Scoreboard".equals(var4.b()) && "ArrayList Font".equals(var7.getKey())) {
                                var8 = EixClient.a().d().a(var4, "Font");
                            }

                            if ((var8 == null || !(var4 instanceof Scoreboard) || var8.a() != f.e) && var8 != null) {
                                this.a(var8, var7.getValue());
                            }
                        }
                    }
                }
            } catch (Exception var9) {
                logger.warn("Module or value not found: " + (String)var3.getKey());
            }
        }

        m.a().clear();

        for (String var11 : this.config.friends) {
            m.b(var11);
        }

        ClickGuiScreen.a = this.config.gui.x;
        ClickGuiScreen.b = this.config.gui.y;
        ClickGuiScreen.c = this.config.gui.width;
        ClickGuiScreen.d = this.config.gui.height;
    }

    private void a(com.heypixel.heypixelmod.obsoverlay.d.c var1, Object var2) {
        if (var2 != null) {
            switch (var1.a()) {
                case a:
                    if (var2 instanceof Boolean) {
                        var1.b().a((Boolean)var2);
                    }
                    break;
                case b:
                    if (var2 instanceof Number) {
                        var1.c().a(((Number)var2).floatValue());
                    }
                    break;
                case d:
                    if (var2 instanceof String) {
                        var1.d().a((String)var2);
                    }
                    break;
                case c:
                    if (var2 instanceof Number) {
                        int var7 = ((Number)var2).intValue();
                        e var8 = var1.e();
                        if (var7 >= 0 && var7 < var8.m().length) {
                            var8.a(var7);
                        }
                    }
                    break;
                case e:
                    if (var2 instanceof Map var6) {
                        com.heypixel.heypixelmod.obsoverlay.d.a.b var4 = var1.f();
                        if (var6.containsKey("x") && var6.get("x") instanceof Number) {
                            var4.a(((Number)var6.get("x")).floatValue());
                        }

                        if (var6.containsKey("y") && var6.get("y") instanceof Number) {
                            var4.b(((Number)var6.get("y")).floatValue());
                        }
                    }
                    break;
                case f:
                    if (var2 instanceof Number) {
                        var1.g().a(((Number)var2).intValue());
                    } else if (var2 instanceof String var3) {
                        try {
                            var1.g().a(Integer.parseInt(var3.trim()));
                        } catch (NumberFormatException var5) {
                        }
                    }
            }
        }
    }

    private void f() {
        c var1 = EixClient.a().g();
        this.config.modules.clear();

        for (com.heypixel.heypixelmod.obsoverlay.modules.b var3 : var1.a()) {
            a.b var4 = new a.b();
            var4.key = var3.o();
            var4.enabled = var3.m();

            for (com.heypixel.heypixelmod.obsoverlay.d.c var6 : EixClient.a().d().a()) {
                if (var6.i() == var3) {
                    switch (var6.a()) {
                        case a:
                            var4.values.put(var6.j(), var6.b().m());
                            break;
                        case b:
                            var4.values.put(var6.j(), var6.c().q());
                            break;
                        case d:
                            var4.values.put(var6.j(), var6.d().n());
                            break;
                        case c:
                            var4.values.put(var6.j(), var6.e().o());
                            break;
                        case e:
                            if (!(var3 instanceof Scoreboard)) {
                                HashMap var7 = new HashMap();
                                var7.put("x", var6.f().n());
                                var7.put("y", var6.f().o());
                                var4.values.put(var6.j(), var7);
                            }
                            break;
                        case f:
                            var4.values.put(var6.j(), var6.g().m());
                    }
                }
            }

            this.config.modules.put(var3.b(), var4);
        }

        this.config.friends = new ArrayList<>(m.a());
        this.config.gui.x = ClickGuiScreen.a;
        this.config.gui.y = ClickGuiScreen.b;
        this.config.gui.width = ClickGuiScreen.c;
        this.config.gui.height = ClickGuiScreen.d;
    }
}
