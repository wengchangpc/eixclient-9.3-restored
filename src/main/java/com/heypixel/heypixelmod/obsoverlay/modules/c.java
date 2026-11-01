package com.heypixel.heypixelmod.obsoverlay.modules;

import com.heypixel.heypixelmod.obsoverlay.EixClient;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventMouseClick;
import com.heypixel.heypixelmod.obsoverlay.events.impl.j;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AimAssist;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AntiBots;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AutoClicker;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.a.AutoThrow;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.a.CrystalAura;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.a.KeepSprint;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.a.KillAura;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.a.TickBase;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.a.Velocity;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.AntiFireball;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.AutoHeypixel;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.AutoSoup;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.AutoTools;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.ChestStealer;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.ClientFriend;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.EffectTags;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.FastPlace;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.GhostBedMine;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.GhostChestOpen;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.GhostHand;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.Helper;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.MiddleClick;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.MusicPlayer;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.Target;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.Teams;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.c.AutoStuck;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Blink;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Eagle;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.c.FastWeb;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Fly;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.c.GrimFly;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.c.GrimLowHop;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.c.LongJump;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.c.NoJumpDelay;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.c.NoSlow;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.c.SafeWalk;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Sprint;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Stuck;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.Animations;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.AntiBlindness;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.AntiNausea;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.BedESP;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.ChestESP;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.ClickGUI;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.Compass;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.DynamicIsland;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.EffectHUD;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.Fov;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.FullBright;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.Glow;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.HUD;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.HUDEditor;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.ItemTags;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.KeyBinds;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.MotionBlur;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.NameProtect;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.NameTags;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.NoHurtCam;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.NoRender;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.Notification;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.PostProcess;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.Projectiles;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.Scoreboard;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.ServerNameSpoof;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.TargetESP;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.TargetHUD;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.TimeChanger;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.ViewClip;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.Watermark;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.Weather;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class c {
    private static final Logger a = LogManager.getLogger(c.class);
    private final List<b> b = new ArrayList<>();
    private final Map<Class<? extends b>, b> c = new HashMap<>();
    private final Map<String, b> d = new HashMap<>();

    public c() {
        try {
            EixClient var1 = EixClient.a();
            if (var1 != null && var1.b() != null) {
                var1.b().a(this);
            }

            this.b();
            this.b.sort((var0, var1x) -> var0.b().compareToIgnoreCase(var1x.b()));
        } catch (Exception var2) {
            a.error("Failed to initialize modules", var2);
            throw new RuntimeException(var2);
        }
    }

    public static c a(String var0) {
        return new c();
    }

    public static String b(String var0) {
        return var0;
    }

    private void b() {
        this.a(
            new KillAura(),
            new AutoThrow(),
            new HUD(),
            new DynamicIsland(),
            new Velocity(),
            new TickBase(),
            new NameTags(),
            new TargetESP(),
            new ChestStealer(),
            new InventoryManager(),
            new Scaffold(),
            new AntiBots(),
            new Sprint(),
            new ChestESP(),
            new BedESP(),
            new ClickGUI(),
            new Teams(),
            new Glow(),
            new EffectTags(),
            new ClientFriend(),
            new GhostBedMine(),
            new GhostChestOpen(),
            new NoJumpDelay(),
            new FastPlace(),
            new AntiFireball(),
            new Stuck(),
            new AutoStuck(),
            new ServerNameSpoof(),
            new AutoSoup(),
            new AutoTools(),
            new ViewClip(),
            new Projectiles(),
            new TimeChanger(),
            new Weather(),
            new FullBright(),
            new NameProtect(),
            new NoHurtCam(),
            new AutoClicker(),
            new AntiBlindness(),
            new AntiNausea(),
            new Scoreboard(),
            new Compass(),
            new Blink(),
            new GrimFly(),
            new Fly(),
            new FastWeb(),
            new PostProcess(),
            new CrystalAura(),
            new EffectHUD(),
            new NoRender(),
            new ItemTags(),
            new SafeWalk(),
            new MotionBlur(),
            new Helper(),
            new NoSlow(),
            new LongJump(),
            new Target(),
            new KeepSprint(),
            new Animations(),
            new Fov(),
            new GhostHand(),
            new Eagle(),
            new HUDEditor(),
            new Watermark(),
            new TargetHUD(),
            new Notification(),
            new AutoHeypixel(),
            new KeyBinds(),
            new com.heypixel.heypixelmod.obsoverlay.modules.impl.render.ArrayList(),
            new GrimLowHop(),
            new AimAssist(),
            new MiddleClick(),
            new MusicPlayer()
        );
    }

    private void a(b... var1) {
        for (b var5 : var1) {
            this.a(var5);
        }
    }

    private void a(b var1) {
        var1.c();
        this.b.add(var1);
        this.c.put((Class<? extends b>)var1.getClass(), var1);
        this.d.put(var1.h().toLowerCase(), var1);
    }

    public List<b> a(a var1) {
        ArrayList var2 = new ArrayList();

        for (b var4 : this.b) {
            if (var4.l() == var1) {
                var2.add(var4);
            }
        }

        return var2;
    }

    public <T extends b> T a(Class<T> var1) {
        b var2 = this.c.get(var1);
        return (T)(var2 == null ? null : var1.cast(var2));
    }

    public b c(String var1) {
        b var2 = this.d.get(var1.toLowerCase());
        if (var2 == null) {
            throw new com.heypixel.heypixelmod.obsoverlay.a.b();
        } else {
            return var2;
        }
    }

    public b d(String var1) {
        return var1 == null ? null : this.d.get(var1.toLowerCase());
    }

    @EventTarget
    public void onKey(j var1) {
        if (var1.c() && Minecraft.getInstance().screen == null) {
            for (b var3 : this.b) {
                if (var3.o() == var1.b()) {
                    var3.f();
                }
            }
        }
    }

    @EventTarget
    public void onKey(EventMouseClick var1) {
        if (!var1.state() && (var1.key() == 3 || var1.key() == 4)) {
            for (b var3 : this.b) {
                if (var3.o() == -var1.key()) {
                    var3.f();
                }
            }
        }
    }

    public List<b> a() {
        return this.b;
    }
}
