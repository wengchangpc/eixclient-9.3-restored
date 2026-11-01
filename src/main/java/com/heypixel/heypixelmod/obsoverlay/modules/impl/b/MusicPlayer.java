package com.heypixel.heypixelmod.obsoverlay.modules.impl.b;

import com.heypixel.heypixelmod.obsoverlay.EixClient;
import com.heypixel.heypixelmod.obsoverlay.d.d;
import com.heypixel.heypixelmod.obsoverlay.d.a.c;
import com.heypixel.heypixelmod.obsoverlay.d.a.e;
import com.heypixel.heypixelmod.obsoverlay.d.a.f;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.s;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.a;
import com.heypixel.heypixelmod.obsoverlay.modules.b;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.render.HUD;
import com.heypixel.heypixelmod.obsoverlay.utils.v;
import com.heypixel.heypixelmod.obsoverlay.utils.e.SkijaDraw;
import io.github.humbleui.skija.Font;
import java.awt.Color;
import java.io.File;
import net.minecraft.client.Minecraft;

@ModuleInfo(
    a = "MusicPlayer",
    b = "音乐播放器",
    c = "播放本地音乐文件 (MP3 / OGG / WAV / AIFF)",
    d = com.heypixel.heypixelmod.obsoverlay.modules.a.MISC
)
public class MusicPlayer extends b {
    public com.heypixel.heypixelmod.obsoverlay.d.a.a c = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Show HUD").a(true).a().b();
    public e d = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "HUD Style").a("Full", "Compact").a(0).a(() -> this.c.m()).a().e();
    public com.heypixel.heypixelmod.obsoverlay.d.a.a e = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Background").a(true).a(() -> this.c.m()).a().b();
    public com.heypixel.heypixelmod.obsoverlay.d.a.a f = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Progress Bar").a(true).a(() -> this.c.m()).a().b();
    public com.heypixel.heypixelmod.obsoverlay.d.a.a g = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Show Time").a(true).a(() -> this.c.m()).a().b();
    public com.heypixel.heypixelmod.obsoverlay.d.a.a h = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Equalizer Icon").a(true).a(() -> this.c.m()).a().b();
    public c i = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Volume")
        .a(70.0F)
        .d(1.0F)
        .b(0.0F)
        .c(100.0F)
        .a(var0 -> com.heypixel.heypixelmod.obsoverlay.music.c.a().a(var0.c().q() / 100.0F))
        .a()
        .c();
    public e j = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Loop Mode")
        .a("Off", "All", "One")
        .a(1)
        .a(var0 -> com.heypixel.heypixelmod.obsoverlay.music.c.a().a(com.heypixel.heypixelmod.obsoverlay.music.c.a.values()[var0.e().o()]))
        .a()
        .e();
    public com.heypixel.heypixelmod.obsoverlay.d.a.a k = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Shuffle")
        .a(false)
        .a(var0 -> com.heypixel.heypixelmod.obsoverlay.music.c.a().a(var0.b().m()))
        .a()
        .b();
    public f l = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Music Folder")
        .a(r())
        .a(var0 -> com.heypixel.heypixelmod.obsoverlay.music.c.a().a(var0.d().n()))
        .a()
        .d();
    public com.heypixel.heypixelmod.obsoverlay.d.a.b m = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Position").e(10.0F).f(220.0F).a().f();
    public com.heypixel.heypixelmod.obsoverlay.d.a.a n = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Netease Sync").a(true).a(var1 -> this.q()).a().b();
    public com.heypixel.heypixelmod.obsoverlay.d.a.a o = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Other Players").a(true).a(var1 -> this.q()).a().b();
    public com.heypixel.heypixelmod.obsoverlay.d.a.a p = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "SMTC Sync").a(false).a(var1 -> this.q()).a().b();
    public com.heypixel.heypixelmod.obsoverlay.d.a.a q = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Fetch Lyrics")
        .a(true)
        .a(var0 -> com.heypixel.heypixelmod.obsoverlay.music.lyric.b.a().a(var0.b().m()))
        .a()
        .b();
    public c r = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Lyric Offset (ms)")
        .a(0.0F)
        .d(50.0F)
        .b(-5000.0F)
        .c(5000.0F)
        .a(() -> this.q.m())
        .a(var0 -> com.heypixel.heypixelmod.obsoverlay.music.lyric.b.a().a((long)var0.c().q()))
        .a()
        .c();
    public com.heypixel.heypixelmod.obsoverlay.d.a.d s = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Play / Pause Key").b(91).a().g();
    public com.heypixel.heypixelmod.obsoverlay.d.a.d t = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Next Key").b(93).a().g();
    public com.heypixel.heypixelmod.obsoverlay.d.a.d u = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Previous Key").b(92).a().g();
    public com.heypixel.heypixelmod.obsoverlay.d.a.d v = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Volume Up Key").b(61).a().g();
    public com.heypixel.heypixelmod.obsoverlay.d.a.d w = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Volume Down Key").b(45).a().g();
    public com.heypixel.heypixelmod.obsoverlay.d.a.d x = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Playlist Key").b(260).a().g();

    private static String r() {
        String var0 = System.getenv("APPDATA");
        File var1 = var0 != null && !var0.isBlank() ? new File(var0, "EixClient") : new File(System.getProperty("user.home"), "EixClient");
        return new File(var1, "music").getAbsolutePath();
    }

    public static void p() {
        Minecraft.getInstance().setScreen(new com.heypixel.heypixelmod.obsoverlay.c.d());
    }

    @Override
    protected void c() {
        super.c();
        this.b(0);
    }

    @Override
    public void d() {
        try {
            com.heypixel.heypixelmod.obsoverlay.music.c var1 = com.heypixel.heypixelmod.obsoverlay.music.c.a();
            var1.b();
            com.heypixel.heypixelmod.obsoverlay.utils.v.a(var1::l);
            this.q();
        } catch (Throwable var2) {
        }
    }

    @Override
    public void e() {
        com.heypixel.heypixelmod.obsoverlay.music.nowplaying.c.a().c();
    }

    public void q() {
        try {
            com.heypixel.heypixelmod.obsoverlay.music.nowplaying.c var1 = com.heypixel.heypixelmod.obsoverlay.music.nowplaying.c.a();
            var1.b(this.n.m());
            var1.c(this.o.m());
            var1.a(this.p.m());
            com.heypixel.heypixelmod.obsoverlay.music.lyric.b.a().a(this.q.m());
            com.heypixel.heypixelmod.obsoverlay.music.lyric.b.a().a((long)this.r.q());
            boolean var2 = this.n.m() || this.o.m() || this.p.m();
            if (this.m() && var2) {
                var1.e();
            } else {
                var1.c();
            }
        } catch (Throwable var3) {
        }
    }

    @EventTarget
    public void onRenderSkia(s var1) {
        if (this.c.m()) {
            try {
                this.s();
            } catch (Throwable var3) {
            }
        }
    }

    private void s() {
        com.heypixel.heypixelmod.obsoverlay.music.c var1 = com.heypixel.heypixelmod.obsoverlay.music.c.a();
        com.heypixel.heypixelmod.obsoverlay.music.b var2 = var1.c();
        com.heypixel.heypixelmod.obsoverlay.music.d var3 = var1.h();
        float var4 = this.m.n();
        float var5 = this.m.o();
        boolean var6 = this.d.a("Compact");
        float var7 = var6 ? this.b(var4, var5, var1, var2, var3) : this.a(var4, var5, var1, var2, var3);
        this.m.c(var7);
        this.m.d(var6 ? 20.0F : 54.0F);
    }

    private float a(
        float var1,
        float var2,
        com.heypixel.heypixelmod.obsoverlay.music.c var3,
        com.heypixel.heypixelmod.obsoverlay.music.b var4,
        com.heypixel.heypixelmod.obsoverlay.music.d var5
    ) {
        float var6 = 190.0F;
        float var7 = 54.0F;
        float var8 = 10.0F;
        Color var9 = t();
        boolean var10 = var4.c() == com.heypixel.heypixelmod.obsoverlay.music.b.a.PLAYING;
        if (this.e.m()) {
            SkijaDraw.c(var1, var2, var6, var7, 6.0F);
            SkijaDraw.a(var1, var2, var6, var7, 6.0F);
            SkijaDraw.a(var1, var2, var6, var7, 6.0F, new Color(0, 0, 0, 120));
        }

        float var11 = var1 + var8;
        if (this.h.m()) {
            this.a(var1 + var8, var2 + 8.0F, 14.0F, var9, var10);
            var11 = var1 + var8 + 20.0F;
        }

        float var12 = this.g.m() ? 62.0F : 6.0F;
        float var13 = var6 - (var11 - var1) - var8 - var12;
        Font var14 = com.heypixel.heypixelmod.obsoverlay.utils.e.b.c.a(12.0F);
        Font var15 = com.heypixel.heypixelmod.obsoverlay.utils.e.b.c.a(9.0F);
        if (var5 == null) {
            String var20 = var3.k() ? "扫描音乐中..." : "没有音乐文件";
            SkijaDraw.a(SkijaDraw.a(var20, var14, var13), var11, var2 + 9.0F, new Color(255, 255, 255, 190), var14);
            SkijaDraw.a("放入 " + a(var3.d()), var11, var2 + 26.0F, new Color(170, 170, 170), var15);
            return var6;
        } else {
            String var16 = SkijaDraw.a(var5.b(), var14, var13);
            SkijaDraw.a(var16, var11, var2 + 9.0F, Color.WHITE, var14);
            String var17 = var5.c().isEmpty() ? "未知艺术家" : var5.c();
            SkijaDraw.a(SkijaDraw.a(var17, var15, var13), var11, var2 + 27.0F, new Color(175, 175, 175), var15);
            if (this.g.m()) {
                String var18 = com.heypixel.heypixelmod.obsoverlay.music.d.b(var4.k()) + " / " + com.heypixel.heypixelmod.obsoverlay.music.d.b(var4.j());
                float var19 = SkijaDraw.a(var18, var15);
                SkijaDraw.a(var18, var1 + var6 - var8 - var19, var2 + 27.0F, new Color(200, 200, 200), var15);
            }

            if (this.f.m()) {
                this.a(var1 + var8, var2 + 42.0F, var6 - var8 * 2.0F, 3.0F, var4, var9);
            }

            return var6;
        }
    }

    private float b(
        float var1,
        float var2,
        com.heypixel.heypixelmod.obsoverlay.music.c var3,
        com.heypixel.heypixelmod.obsoverlay.music.b var4,
        com.heypixel.heypixelmod.obsoverlay.music.d var5
    ) {
        float var6 = 20.0F;
        float var7 = 6.0F;
        Color var8 = t();
        boolean var9 = var4.c() == com.heypixel.heypixelmod.obsoverlay.music.b.a.PLAYING;
        Font var10 = com.heypixel.heypixelmod.obsoverlay.utils.e.b.c.a(11.0F);
        String var11;
        if (var5 == null) {
            var11 = var3.k() ? "扫描音乐中..." : "没有音乐文件";
        } else {
            String var12 = var5.c().isEmpty() ? "" : var5.c() + " - ";
            var11 = var12 + var5.b();
        }

        if (var4.c() == com.heypixel.heypixelmod.obsoverlay.music.b.a.PAUSED) {
            var11 = "[暂停] " + var11;
        }

        String var18 = "";
        if (this.g.m() && var5 != null) {
            var18 = com.heypixel.heypixelmod.obsoverlay.music.d.b(var4.k()) + "/" + com.heypixel.heypixelmod.obsoverlay.music.d.b(var4.j());
        }

        float var13 = this.h.m() ? 16.0F : 0.0F;
        float var14 = var18.isEmpty() ? 0.0F : SkijaDraw.a(var18, var10) + 6.0F;
        float var15 = SkijaDraw.a(var11, var10);
        float var16 = var7 + var13 + var15 + var14 + var7;
        var16 = Math.max(var16, 90.0F);
        if (this.e.m()) {
            SkijaDraw.a(var1, var2, var16, var6, 4.0F);
            SkijaDraw.a(var1, var2, var16, var6, 4.0F, new Color(0, 0, 0, 120));
        }

        float var17 = var1 + var7;
        if (this.h.m()) {
            this.a(var17, var2 + 4.0F, 12.0F, var8, var9);
            var17 += var13;
        }

        SkijaDraw.a(var11, var17, var2 + 5.0F, Color.WHITE, var10);
        if (!var18.isEmpty()) {
            SkijaDraw.a(var18, var1 + var16 - var7 - SkijaDraw.a(var18, var10), var2 + 5.0F, new Color(190, 190, 190), var10);
        }

        if (this.f.m() && var5 != null) {
            this.a(var1 + var7, var2 + var6 - 2.5F, var16 - var7 * 2.0F, 1.5F, var4, var8);
        }

        return var16;
    }

    private void a(float var1, float var2, float var3, float var4, com.heypixel.heypixelmod.obsoverlay.music.b var5, Color var6) {
        long var7 = var5.j();
        long var9 = var5.k();
        float var11 = var7 > 0L ? Math.max(0.0F, Math.min(1.0F, (float)var9 / (float)var7)) : 0.0F;
        float var12 = var4 / 2.0F;
        SkijaDraw.a(var1, var2, var3, var4, var12, new Color(255, 255, 255, 45));
        if (var11 > 0.0F) {
            SkijaDraw.a(var1, var2, Math.max(var4, var3 * var11), var4, var12, var6);
            float var13 = var1 + var3 * var11;
            SkijaDraw.a(var13, var2 + var12, var12 + 1.0F, Color.WHITE);
        }
    }

    private void a(float var1, float var2, float var3, Color var4, boolean var5) {
        byte var6 = 4;
        float var7 = var3 * 0.16F;
        float var8 = (var3 - var7 * (var6 - 1)) / var6;
        double var9 = System.currentTimeMillis() / 220.0;

        for (int var11 = 0; var11 < var6; var11++) {
            float var12;
            if (var5) {
                var12 = (float)(0.35 + 0.65 * Math.abs(Math.sin(var9 + var11 * 0.9)));
            } else {
                var12 = 0.25F + var11 * 0.05F;
            }

            float var13 = Math.max(2.0F, var3 * var12);
            float var14 = var1 + var11 * (var8 + var7);
            float var15 = var2 + (var3 - var13) / 2.0F;
            SkijaDraw.a(var14, var15, var8, var13, var8 / 2.0F, var4);
        }
    }

    private static Color t() {
        try {
            HUD var0 = EixClient.a().g().a(HUD.class);
            if (var0 != null) {
                return HUD.p();
            }
        } catch (Throwable var1) {
        }

        return new Color(102, 255, 209);
    }

    private static String a(File var0) {
        if (var0 == null) {
            return "";
        } else {
            String var1 = var0.getAbsolutePath();
            return var1.length() > 42 ? "..." + var1.substring(var1.length() - 39) : var1;
        }
    }
}
