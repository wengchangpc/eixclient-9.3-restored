package com.heypixel.heypixelmod.obsoverlay;

import cn.paradisemc.ZKMIndy;
import com.heypixel.heypixelmod.obsoverlay.d.b;
import com.heypixel.heypixelmod.obsoverlay.d.e;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventManager;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventRunTicks;
import com.heypixel.heypixelmod.obsoverlay.events.impl.y;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.ClickGUI;
import com.heypixel.heypixelmod.obsoverlay.utils.ae;
import com.heypixel.heypixelmod.obsoverlay.utils.ah;
import com.heypixel.heypixelmod.obsoverlay.utils.ak;
import com.heypixel.heypixelmod.obsoverlay.utils.i;
import com.heypixel.heypixelmod.obsoverlay.utils.j;
import com.heypixel.heypixelmod.obsoverlay.utils.q;
import com.heypixel.heypixelmod.obsoverlay.utils.w;
import com.heypixel.heypixelmod.obsoverlay.utils.c.c;
import com.heypixel.heypixelmod.obsoverlay.utils.e.SkijaDraw;
import com.heypixel.heypixelmod.obsoverlay.utils.e.a.a;
import com.heypixel.heypixelmod.obsoverlay.utils.renderer.d;
import com.heypixel.heypixelmod.obsoverlay.utils.renderer.h;
import com.heypixel.heypixelmod.obsoverlay.utils.renderer.k;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.FontFormatException;
import java.io.IOException;
import javax.swing.text.html.parser.Entity;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.MinecraftForge;

@ZKMIndy
public class EixClient {
    public static final String a = "EixClient";
    public static final String b = "EixClient";
    public static float c = 1.0F;
    public static int d;
    private static volatile EixClient f;
    public boolean e = false;
    private EventManager g;
    private j h;
    private e i;
    private b j;
    private c k;
    private com.heypixel.heypixelmod.obsoverlay.modules.c l;
    private com.heypixel.heypixelmod.obsoverlay.commands.b m;
    private com.heypixel.heypixelmod.obsoverlay.b.b n;
    private com.heypixel.heypixelmod.obsoverlay.c.a.c o;

    private EixClient() {
        this.a(null, null);
    }

    public static EntityHitResult a(Entity var0) {
        RenderSystem.recordRenderCall(() -> {
            try {
                new EixClient();
            } catch (Exception var1) {
                System.err.println("Failed to load client");
                var1.printStackTrace(System.err);
            }
        });
        return null;
    }

    public static EixClient a() {
        EixClient var0 = f;
        if (var0 == null) {
            synchronized (EixClient.class) {
                var0 = f;
                if (var0 == null) {
                    var0 = new EixClient();
                }
            }
        }

        return var0;
    }

    public EntityHitResult a(Player var1, Exception var2) {
        f = this;
        this.g = new EventManager();
        Window var3 = Minecraft.getInstance().getWindow();
        com.heypixel.heypixelmod.obsoverlay.utils.e.a.a.a(var3.getWidth(), var3.getHeight());
        com.heypixel.heypixelmod.obsoverlay.utils.renderer.k.a();
        com.heypixel.heypixelmod.obsoverlay.utils.renderer.h.a();

        try {
            com.heypixel.heypixelmod.obsoverlay.utils.renderer.d.a();
        } catch (IOException var5) {
            throw new RuntimeException(var5);
        } catch (FontFormatException var6) {
            throw new RuntimeException(var6);
        }

        this.h = new j();
        this.i = new e();
        this.j = new b();
        this.l = com.heypixel.heypixelmod.obsoverlay.modules.c.a("加载客户端");
        this.k = new c();
        this.m = new com.heypixel.heypixelmod.obsoverlay.commands.b();
        this.n = new com.heypixel.heypixelmod.obsoverlay.b.b();
        this.o = new com.heypixel.heypixelmod.obsoverlay.c.a.c();
        this.n.b();
        this.l.a(ClickGUI.class).a(false);
        this.g.a(a());
        this.g.a(this.h);
        this.g.a(new c());
        this.g.a(new w());
        this.g.a(new ae());
        this.g.a(new i());
        this.g.a(new SkijaDraw.a());
        this.g.a(com.heypixel.heypixelmod.obsoverlay.music.c.a());
        MinecraftForge.EVENT_BUS.register(this.h);
        this.e = true;
        com.heypixel.heypixelmod.obsoverlay.music.c.a().b();
        ah.a("opening.wav", 1.0F);
        return null;
    }

    @EventTarget
    public void onShutdown(y var1) {
        this.n.c();

        try {
            com.heypixel.heypixelmod.obsoverlay.music.nowplaying.c.a().c();
        } catch (Throwable var3) {
        }

        q.a();
    }

    @EventTarget(
        a = 0
    )
    public void onEarlyTick(EventRunTicks var1) {
        if (var1.type() == com.heypixel.heypixelmod.obsoverlay.events.api.b.a.a) {
            ak.a();
        }
    }

    public EventManager b() {
        return this.g;
    }

    public j c() {
        return this.h;
    }

    public e d() {
        return this.i;
    }

    public b e() {
        return this.j;
    }

    public c f() {
        return this.k;
    }

    public com.heypixel.heypixelmod.obsoverlay.modules.c g() {
        return this.l;
    }

    public com.heypixel.heypixelmod.obsoverlay.commands.b h() {
        return this.m;
    }

    public com.heypixel.heypixelmod.obsoverlay.b.b i() {
        return this.n;
    }

    public com.heypixel.heypixelmod.obsoverlay.c.a.c j() {
        return this.o;
    }
}
