package com.heypixel.heypixelmod.obsoverlay.modules.impl.render;

import com.heypixel.heypixelmod.obsoverlay.EixClient;
import com.heypixel.heypixelmod.obsoverlay.d.a.e;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.m;
import com.heypixel.heypixelmod.obsoverlay.events.impl.s;
import com.heypixel.heypixelmod.obsoverlay.events.impl.t;
import com.heypixel.heypixelmod.obsoverlay.events.impl.u;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.b.InventoryManager;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Blink;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.c.Scaffold;
import com.heypixel.heypixelmod.obsoverlay.utils.ag;
import com.heypixel.heypixelmod.obsoverlay.utils.p;
import com.heypixel.heypixelmod.obsoverlay.utils.e.SkijaDraw;
import io.github.humbleui.skija.ClipMode;
import io.github.humbleui.skija.FilterTileMode;
import io.github.humbleui.skija.Font;
import io.github.humbleui.skija.FontMetrics;
import io.github.humbleui.skija.ImageFilter;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.PaintMode;
import io.github.humbleui.skija.PaintStrokeCap;
import io.github.humbleui.skija.Path;
import io.github.humbleui.types.RRect;
import io.github.humbleui.types.Rect;
import java.awt.Color;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoRemovePacket;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;

@ModuleInfo(
    a = "DynamicIsland",
    b = "灵动岛",
    c = "",
    d = com.heypixel.heypixelmod.obsoverlay.modules.a.RENDER
)
public class DynamicIsland extends com.heypixel.heypixelmod.obsoverlay.modules.b {
    private static final DateTimeFormatter c = DateTimeFormatter.ofPattern("HH:mm");
    private static volatile Component d;
    private static volatile Component e;
    private static volatile List<PlayerInfo> f = List.of();
    private final com.heypixel.heypixelmod.obsoverlay.d.a.a g = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Bloom").a(true).a().b();
    private final com.heypixel.heypixelmod.obsoverlay.d.a.a h = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Blur").a(true).a().b();
    private final e i = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Style").a("Island", "Capsule").a(0).a().e();
    private final com.heypixel.heypixelmod.obsoverlay.d.a.c j = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Radius")
        .a(6.0F)
        .b(0.0F)
        .c(15.0F)
        .d(1.0F)
        .a(() -> this.i.a("Island"))
        .a()
        .c();
    private final com.heypixel.heypixelmod.obsoverlay.d.a.c k = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "YOffset")
        .a(8.0F)
        .b(0.0F)
        .c(200.0F)
        .d(1.0F)
        .a()
        .c();
    private final com.heypixel.heypixelmod.obsoverlay.d.a.a l = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Music Island").a(true).a().b();
    private final e m = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Music Layout").a("Full", "Lyrics").a(0).a(() -> this.l.m()).a().e();
    private final com.heypixel.heypixelmod.obsoverlay.d.a.c n = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Music Max Width")
        .a(340.0F)
        .b(140.0F)
        .c(560.0F)
        .d(10.0F)
        .a(() -> this.l.m())
        .a()
        .c();
    private final com.heypixel.heypixelmod.obsoverlay.d.a.a o = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Lyric Translation")
        .a(true)
        .a(() -> this.l.m())
        .a()
        .b();
    private final com.heypixel.heypixelmod.obsoverlay.d.a.a p = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Music Progress Bar")
        .a(true)
        .a(() -> this.l.m())
        .a()
        .b();
    private final com.heypixel.heypixelmod.obsoverlay.d.a.a q = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Next Lyric Line")
        .a(false)
        .a(() -> this.l.m())
        .a()
        .b();
    private static DynamicIsland.ToggleInfo r;
    private static final Queue<DynamicIsland.ToggleInfo> s = new ConcurrentLinkedQueue<>();
    private static final Set<UUID> t = new CopyOnWriteArraySet<>();
    private long u = -1L;
    private long v = -1L;
    private float w = 90.0F;
    private DynamicIsland.a x = DynamicIsland.a.a;
    private float y;
    private float z = 1.0F;
    private float A;
    private float B;
    private float C;
    private float D;
    private float E;
    private float F = 65.0F;
    private float G = 65.0F;
    private float H = 19.0F;
    private final ag I = new ag(0.0F, 0.0F, 0.12F);
    private static final float[] J = new float[]{11.5F, 11.0F, 10.5F, 10.0F, 9.5F, 9.0F, 8.5F};
    private static final long K = 280L;
    private static final float L = 4.5F;
    private String M = "";
    private String N = "";
    private float O = 1.0F;
    private long P = 0L;
    private String Q = "";
    private float R = -1.0F;
    private boolean S = true;
    private float T = 65.0F;
    private List<PlayerInfo> U;
    private float V;
    private float W;

    public static void a(com.heypixel.heypixelmod.obsoverlay.modules.b var0, boolean var1) {
        if (!(var0 instanceof DynamicIsland)) {
            DynamicIsland.d var2 = DynamicIsland.d.a;
            if (var0 instanceof Scaffold) {
                var2 = DynamicIsland.d.b;
            } else if (var0 instanceof Blink) {
                var2 = DynamicIsland.d.c;
            }

            s.offer(new DynamicIsland.ToggleInfo(var0.h(), var1, var2));
        }
    }

    public static void a(Player var0) {
        if (var0 != null) {
            t.add(var0.getUUID());
        }
    }

    private static void b(String var0) {
        if (var0 != null && !var0.isEmpty()) {
            s.offer(new DynamicIsland.ToggleInfo("You killed " + var0 + " !", true, DynamicIsland.d.d));
        }
    }

    private static void p() {
        s.offer(new DynamicIsland.ToggleInfo("Victory!", true, DynamicIsland.d.e));
    }

    @EventTarget
    public void onRenderSkia(s var1) {
        this.q();
        this.C();
        if (this.A()) {
            this.P();
        }
    }

    @EventTarget
    public void onRenderTab(t var1) {
        if (var1.a() == com.heypixel.heypixelmod.obsoverlay.events.api.b.a.g) {
            d = var1.b();
        } else if (var1.a() == com.heypixel.heypixelmod.obsoverlay.events.api.b.a.f) {
            e = var1.b();
        }
    }

    @EventTarget
    public void onPacket(m var1) {
        if (var1.b() == com.heypixel.heypixelmod.obsoverlay.events.api.b.a.d && a.getConnection() != null) {
            if (var1.c() instanceof ClientboundPlayerInfoRemovePacket var2) {
                for (UUID var6 : var2.profileIds()) {
                    if (t.contains(var6)) {
                        PlayerInfo var7 = a.getConnection().getPlayerInfo(var6);
                        if (var7 != null) {
                            b(var7.getProfile().getName());
                        }

                        t.remove(var6);
                    }
                }
            } else if (var1.c() instanceof ClientboundSetTitleTextPacket var3) {
                if (d(var3.getText().getString())) {
                    p();
                }
            } else if (var1.c() instanceof ClientboundSetSubtitleTextPacket var4 && d(var4.getText().getString())) {
                p();
            }
        }
    }

    private static boolean d(String var0) {
        if (var0 != null && !var0.isEmpty()) {
            String var1 = var0.toLowerCase(Locale.ROOT);
            return var1.contains("胜利") || var1.contains("获胜") || var1.contains("victory") || var1.contains("you win");
        } else {
            return false;
        }
    }

    @EventTarget
    public void onRespawn(u var1) {
        t.clear();
        s.clear();
    }

    private void q() {
        this.v();
        this.x();

        try {
            this.r();
        } catch (Throwable var2) {
            this.M = "";
            this.Q = "";
        }

        this.y();
        this.u();
    }

    private void r() {
        if (!this.l.m()) {
            if (!this.M.isEmpty()) {
                this.M = "";
                this.N = "";
                this.O = 1.0F;
                this.Q = "";
            }
        } else {
            com.heypixel.heypixelmod.obsoverlay.music.e var1 = com.heypixel.heypixelmod.obsoverlay.music.e.a();
            var1.b();
            com.heypixel.heypixelmod.obsoverlay.music.lyric.b var2 = com.heypixel.heypixelmod.obsoverlay.music.lyric.b.a();
            if (var2.c() != this.o.m()) {
                var2.b(this.o.m());
            }

            String var3 = var2.d();
            String var4 = var1.l();
            float var5 = this.n.q();
            boolean var6 = this.m.a("Full");
            long var7 = System.currentTimeMillis();
            if (!var3.equals(this.M)) {
                this.N = this.M;
                this.P = var7;
                this.O = 0.0F;
                this.M = var3;
                this.Q = var4;
                this.R = var5;
                this.S = var6;
                this.T = this.a(var3, var4);
            } else if (!var4.equals(this.Q) || var5 != this.R || var6 != this.S) {
                this.Q = var4;
                this.R = var5;
                this.S = var6;
                this.T = this.a(var3, var4);
            }

            if (this.O < 1.0F) {
                float var9 = (float)(var7 - this.P) / 280.0F;
                this.O = var9 >= 1.0F ? 1.0F : a(var9);
            }
        }
    }

    private static float a(float var0) {
        float var1 = 1.0F - var0;
        return 1.0F - var1 * var1 * var1;
    }

    private boolean s() {
        return this.l.m() && com.heypixel.heypixelmod.obsoverlay.music.e.a().c();
    }

    private float t() {
        if (!this.s()) {
            return 19.0F;
        } else {
            return this.m.a("Full") ? 36.0F : 19.0F;
        }
    }

    private static Font a(String var0, float var1) {
        for (float var5 : J) {
            Font var6 = com.heypixel.heypixelmod.obsoverlay.utils.e.b.c.a(var5);
            if (SkijaDraw.a(var0, var6) <= var1) {
                return var6;
            }
        }

        return com.heypixel.heypixelmod.obsoverlay.utils.e.b.c.a(8.5F);
    }

    private float a(String var1, String var2) {
        float var3 = this.n.q();
        float var4 = var3 - 14.0F - 6.0F;
        String var5 = var1.isEmpty() ? var2 : var1;
        if (var5.isEmpty()) {
            return 65.0F;
        } else {
            Font var6 = a(var5, var4);
            float var7 = Math.min(var4, SkijaDraw.a(var5, var6));
            if (this.m.a("Full") && !var2.isEmpty() && !var2.equals(var5)) {
                Font var8 = com.heypixel.heypixelmod.obsoverlay.utils.e.b.c.a(8.5F);
                var7 = Math.max(var7, Math.min(var4, SkijaDraw.a(var2, var8)));
            }

            float var9 = var7 + 14.0F + 6.0F;
            return Math.max(65.0F, Math.min(var3, var9));
        }
    }

    private void b(float var1) {
        if (!(var1 <= 0.05F)) {
            com.heypixel.heypixelmod.obsoverlay.music.e var2 = com.heypixel.heypixelmod.obsoverlay.music.e.a();
            com.heypixel.heypixelmod.obsoverlay.music.lyric.b var3 = com.heypixel.heypixelmod.obsoverlay.music.lyric.b.a();
            String var4 = var3.d();
            String var5 = var2.l();
            if (var4.isEmpty() && var5.isEmpty()) {
                this.f(var1);
            } else {
                boolean var6 = this.m.a("Full");
                float var7 = Math.max(10.0F, this.C - 14.0F);
                float var8 = Math.min(var7, this.n.q() - 14.0F - 6.0F);
                int var9 = (int)(255.0F * var1);
                Color var10 = a(new Color(190, 190, 190), (int)(215.0F * var1));
                String var11 = var4.isEmpty() ? var5 : var4;
                Font var12 = a(var11, var8);
                String var14 = SkijaDraw.a(var11, var12, var7);
                String var15 = SkijaDraw.a(this.N, var12, var7);
                boolean var16 = this.O < 0.999F && !this.N.isEmpty();
                float var17 = var16 ? var9 * (1.0F - this.O) : 0.0F;
                float var18 = var16 ? var9 * this.O : var9;
                if (var6) {
                    if (!var5.isEmpty()) {
                        Font var19 = com.heypixel.heypixelmod.obsoverlay.utils.e.b.c.a(8.5F);
                        String var20 = SkijaDraw.a(var5, var19, var7);
                        float var21 = SkijaDraw.a(var20, var19);
                        SkijaDraw.a(var20, this.A + (this.C - var21) / 2.0F, this.B + 4.0F, var10, var19);
                    }

                    float var25 = this.B + 14.0F;
                    if (var16 && !var15.isEmpty()) {
                        this.a(var15, var12, var25 - 4.5F * this.O, (int)var17, false);
                    }

                    if (!var14.isEmpty()) {
                        float var27 = var25 + (var16 ? 4.5F * (1.0F - this.O) : 0.0F);
                        this.a(var14, var12, var27, (int)var18, true);
                    }

                    String var28 = var3.e();
                    if (var28.isEmpty() && this.q.m()) {
                        var28 = var3.f();
                    }

                    if (!var28.isEmpty()) {
                        Font var30 = com.heypixel.heypixelmod.obsoverlay.utils.e.b.c.a(8.5F);
                        String var22 = SkijaDraw.a(var28, var30, var7);
                        float var23 = SkijaDraw.a(var22, var30);
                        int var24 = (int)(215.0F * var1 * (var16 ? this.O : 1.0F));
                        SkijaDraw.a(var22, this.A + (this.C - var23) / 2.0F, this.B + 25.0F, a(new Color(190, 190, 190), var24), var30);
                    }

                    if (this.p.m()) {
                        this.c(var1);
                    }
                } else {
                    float var26 = this.B + this.D / 2.0F - (this.p.m() ? 1.5F : 0.0F);
                    float var29 = a(var26, var12);
                    if (var16 && !var15.isEmpty()) {
                        this.a(var15, var12, var29 - 4.5F * this.O, (int)var17, false);
                    }

                    if (!var14.isEmpty()) {
                        float var31 = var29 + (var16 ? 4.5F * (1.0F - this.O) : 0.0F);
                        this.a(var14, var12, var31, (int)var18, true);
                    }

                    if (this.p.m()) {
                        this.c(var1);
                    }
                }
            }
        }
    }

    private void a(String var1, Font var2, float var3, int var4, boolean var5) {
        if (var1 != null && !var1.isEmpty() && var4 > 2) {
            float var6 = SkijaDraw.a(var1, var2);
            float var7 = this.A + (this.C - var6) / 2.0F;
            if (!var5) {
                SkijaDraw.a(var1, var7, var3, a(Color.WHITE, var4), var2);
            } else {
                float var8 = com.heypixel.heypixelmod.obsoverlay.music.lyric.b.a().j();
                if (!(var8 < 0.0F) && !(var8 >= 0.999F)) {
                    SkijaDraw.a(var1, var7, var3, a(Color.WHITE, Math.max(24, (int)(var4 * 0.38F))), var2);
                    SkijaDraw.c();
                    SkijaDraw.d(var7 - 1.0F, this.B - 2.0F, Math.max(0.5F, var6 * var8 + 1.0F), this.D + 4.0F, 0.0F);
                    SkijaDraw.a(var1, var7, var3, a(Color.WHITE, var4), var2);
                    SkijaDraw.d();
                } else {
                    SkijaDraw.a(var1, var7, var3, a(Color.WHITE, var4), var2);
                }
            }
        }
    }

    private void c(float var1) {
        float var2 = (float)com.heypixel.heypixelmod.obsoverlay.music.e.a().h();
        if (!(var2 <= 0.0F)) {
            long var3 = com.heypixel.heypixelmod.obsoverlay.music.e.a().g();
            float var5 = m((float)var3 / var2);
            float var6 = this.A + 7.0F;
            float var7 = Math.max(4.0F, this.C - 14.0F);
            float var8 = 2.0F;
            float var9 = this.B + this.D - var8 - 3.0F;
            float var10 = var8 / 2.0F;
            SkijaDraw.a(var6, var9, var7, var8, var10, a(new Color(255, 255, 255), (int)(55.0F * var1)));
            if (var5 > 0.0F) {
                SkijaDraw.a(var6, var9, Math.max(var8, var7 * var5), var8, var10, a(new Color(255, 105, 180), (int)(225.0F * var1)));
            }
        }
    }

    private void d(float var1) {
        if (!(var1 <= 0.05F)) {
            if (this.N()) {
                this.h(var1);
            } else if (this.L()) {
                this.g(var1);
            } else if (this.s()) {
                try {
                    this.b(var1);
                } catch (Throwable var3) {
                    this.f(var1);
                }
            } else {
                this.f(var1);
            }
        }
    }

    private void u() {
        if (EixClient.a() != null && EixClient.a().g() != null) {
            com.heypixel.heypixelmod.obsoverlay.modules.b var1 = EixClient.a().g().a(Blink.class);
            if (var1 != null && var1.m()) {
                Blink var2 = (Blink)var1;
                float var3 = var2.f.q();
                float var4 = (float)var2.p();
                float var5 = m(var4 / var3);
                this.I.a = var5;
                this.I.a(true);
            } else {
                this.I.a = 0.0F;
                this.I.a(false);
            }
        } else {
            this.I.a = 0.0F;
            this.I.a(false);
        }
    }

    private void v() {
        boolean var1 = a.options.keyPlayerList.isDown();
        if (var1 && !this.A()) {
            this.v = System.currentTimeMillis();
            this.x = DynamicIsland.a.f;
            this.F = this.C;
            this.w();
        } else if (var1 && this.A()) {
            this.w();
        } else if (this.x == DynamicIsland.a.g || this.x == DynamicIsland.a.f) {
            this.v = System.currentTimeMillis();
            this.x = DynamicIsland.a.h;
        }
    }

    private void w() {
        if (a.getConnection() != null) {
            List var1 = f.isEmpty() ? List.copyOf(a.getConnection().getOnlinePlayers()) : f;
            this.U = var1.stream()
                .sorted(
                    Comparator.<PlayerInfo>comparingInt(var0 -> var0.getGameMode() == GameType.SPECTATOR ? 1 : 0)
                        .thenComparing(var0 -> var0.getProfile().getName())
                )
                .limit(80L)
                .collect(Collectors.toList());
            int var2 = this.U.size();
            int var3 = (int)Math.ceil(var2 / 1.0);
            this.V = 205.0F;
            float var4 = Math.max(0.0F, this.V - 16.0F);
            Font var5 = com.heypixel.heypixelmod.obsoverlay.utils.e.b.c.a(10.0F);
            float var6 = a(var5);
            int var7 = 1;
            if (d != null && !d.getString().isEmpty()) {
                var7 = a(d.getString(), var4, var5).size();
            }

            int var8 = 0;
            if (e != null && !e.getString().isEmpty()) {
                var8 = a(e.getString(), var4, var5).size();
            }

            float var9 = var7 * var6;
            float var10 = var8 * var6;
            float var11 = Math.max(30.0F, 12.0F + var9 + 8.0F);
            this.W = var11 + var3 * 14.0F + 8.0F + (var8 > 0 ? var10 + 8.0F : 0.0F);
            this.W = Math.max(this.W, 38.0F);
        }
    }

    private void x() {
        if (!this.A()) {
            if (!s.isEmpty()) {
                this.a(s.poll());
            } else if (r != null && this.R() >= 1900L) {
                r = null;
                this.u = -1L;
            }
        }
    }

    private void a(DynamicIsland.ToggleInfo var1) {
        float var2 = this.b(var1);
        this.G = this.C;
        this.H = this.D;
        r = var1;
        this.w = var2;
        if ((var1.type == DynamicIsland.d.b || var1.type == DynamicIsland.d.c) && !var1.enabled) {
            this.u = System.currentTimeMillis() - 1640L;
        } else {
            this.u = System.currentTimeMillis();
        }
    }

    private void y() {
        long var1 = this.R();
        long var3 = this.S();
        float var5 = this.C;
        float var6;
        if (!this.L() && !this.N()) {
            var6 = this.t();
        } else {
            var6 = 25.0F;
        }

        float var7 = this.D;
        if (this.x == DynamicIsland.a.f) {
            if (var3 < 380L) {
                float var8 = m((float)var3 / 380.0F);
                float var9 = k(var8);
                float var10 = k(var8);
                this.E = var9;
                this.a(DynamicIsland.a.f, var10, a(var9, this.F, this.V), a(var10, var6, this.W), 1.0F);
                var5 = this.C;
                var7 = this.D;
            } else {
                this.E = 1.0F;
                this.a(DynamicIsland.a.g, 1.0F, this.V, this.W, 1.0F);
                var5 = this.V;
                var7 = this.W;
            }
        } else if (this.x == DynamicIsland.a.h) {
            if (var3 < 380L) {
                float var13 = m((float)var3 / 380.0F);
                float var15 = k(1.0F - var13);
                float var20 = k(1.0F - var13);
                this.E = var15;
                this.a(DynamicIsland.a.h, var20, a(var15, this.F, this.V), a(var20, var6, this.W), 1.0F);
                var5 = this.C;
                var7 = this.D;
            } else {
                this.E = 0.0F;
                this.a(DynamicIsland.a.a, 0.0F, this.F, var6, 1.0F);
                this.v = -1L;
                var5 = this.F;
                var7 = var6;
            }
        } else if (this.x == DynamicIsland.a.g) {
            this.E = 1.0F;
            this.a(DynamicIsland.a.g, 1.0F, this.V, this.W, 1.0F);
            var5 = this.V;
            var7 = this.W;
        } else {
            float var14;
            if (this.N()) {
                var14 = this.O();
            } else if (this.L()) {
                var14 = this.M();
            } else if (this.s()) {
                var14 = this.T;
            } else {
                var14 = 65.0F;
            }

            if (r == null && this.u == -1L) {
                this.a(DynamicIsland.a.a, 0.0F, var14, var6, 1.0F);
                var5 = var14;
                var7 = var6;
            } else if (var1 < 220L) {
                float var16 = m((float)var1 / 220.0F);
                float var21 = j(var16);
                this.a(DynamicIsland.a.b, var21, a(var21, this.G, this.w), a(var21, this.H, 25.0F), 1.0F);
                var5 = a(var21, this.G, this.w);
                var7 = a(var21, this.H, 25.0F);
            } else if (var1 < 1420L) {
                float var17 = (float)(var1 - 220L) / 1200.0F;
                this.a(DynamicIsland.a.c, var17, this.C, 25.0F, 1.0F);
                var5 = this.w;
                var7 = 25.0F;
            } else if (var1 < 1640L) {
                float var18 = m((float)(var1 - 220L - 1200L) / 220.0F);
                float var22 = k(var18);
                this.a(DynamicIsland.a.d, var22, this.w, 25.0F, 1.0F);
                var5 = this.w;
                var7 = 25.0F;
            } else {
                float var19 = m((float)(var1 - 220L - 1200L - 220L) / 260.0F);
                float var23 = k(var19);
                this.a(DynamicIsland.a.e, var23, a(var23, this.w, var14), a(var23, 25.0F, var6), 1.0F);
                var5 = a(var23, this.w, var14);
                var7 = a(var23, 25.0F, var6);
            }
        }

        if (!this.A()) {
            this.C = a(0.38F, this.C, var5);
            this.D = a(0.28F, this.D, var7);
        }

        this.A = (a.getWindow().getGuiScaledWidth() - this.C) / 2.0F;
        this.B = this.k.q();
    }

    private float z() {
        return this.i.a("Capsule") ? Math.max(0.0F, Math.min(this.D, this.C) / 2.0F) : this.j.q();
    }

    private void a(DynamicIsland.a var1, float var2, float var3, float var4, float var5) {
        this.x = var1;
        this.y = var2;
        this.C = var3;
        this.D = var4;
        this.z = this.e(var5);
    }

    private boolean A() {
        return this.x == DynamicIsland.a.f || this.x == DynamicIsland.a.g || this.x == DynamicIsland.a.h;
    }

    private float B() {
        return this.x != DynamicIsland.a.f && this.x != DynamicIsland.a.g && this.x != DynamicIsland.a.h ? this.y : this.E;
    }

    private float e(float var1) {
        float var2 = var1 - this.z;
        float var3 = 0.22F;
        return this.z + var2 * var3;
    }

    private void C() {
        switch (this.x) {
            case a:
                this.D();
                break;
            case b:
                this.E();
                break;
            case c:
                this.F();
                break;
            case d:
                this.G();
                break;
            case e:
                this.H();
                break;
            case f:
                this.I();
                break;
            case g:
                this.J();
                break;
            case h:
                this.K();
        }
    }

    private void D() {
        this.a(DynamicIsland.b.i);
        this.i(1.0F);
        this.d(1.0F);
    }

    private void E() {
        this.a(DynamicIsland.b.i);
        this.i(1.0F);
        if (r != null && r.type == DynamicIsland.d.b && r.enabled) {
            this.g(this.y);
        } else if (r != null && r.type == DynamicIsland.d.c && r.enabled) {
            this.h(this.y);
        } else if (r != null) {
            this.a(r, l(this.y), 0.0F);
        } else {
            this.d(1.0F);
        }
    }

    private void F() {
        this.a(DynamicIsland.b.i);
        this.i(1.0F);
        if (r != null && r.type == DynamicIsland.d.b && r.enabled) {
            this.g(1.0F);
        } else if (r != null && r.type == DynamicIsland.d.c && r.enabled) {
            this.h(1.0F);
        } else if (r != null) {
            this.a(r, 255, this.y);
        } else {
            this.d(1.0F);
        }
    }

    private void G() {
        this.a(DynamicIsland.b.i);
        this.i(1.0F);
        if (r != null && r.type == DynamicIsland.d.b) {
            this.g(1.0F);
        } else if (r != null && r.type == DynamicIsland.d.c) {
            this.h(1.0F);
        } else if (r != null) {
            this.a(r, l(1.0F - this.y), 1.0F);
        }
    }

    private void H() {
        this.a(DynamicIsland.b.i);
        this.i(1.0F);
        if (r != null && r.type == DynamicIsland.d.c && !r.enabled) {
            this.h(1.0F - this.y);
        } else if (r != null && r.type == DynamicIsland.d.b && !r.enabled) {
            this.g(1.0F - this.y);
        } else {
            this.d(this.y);
        }
    }

    private void I() {
        this.a(DynamicIsland.b.i);
        float var1 = 1.0F - this.B();
        this.i(1.0F);
        if (r != null && r.type == DynamicIsland.d.b) {
            this.g(var1);
        } else if (r != null && r.type == DynamicIsland.d.c) {
            this.h(var1);
        } else if (r != null) {
            this.a(r, (int)(255.0F * var1), 0.0F);
        } else {
            this.d(var1);
        }
    }

    private void J() {
        this.a(DynamicIsland.b.i);
        this.i(1.0F);
    }

    private void K() {
        this.a(DynamicIsland.b.i);
        float var1 = 1.0F - this.B();
        this.i(1.0F);
        if (r != null && r.type == DynamicIsland.d.b) {
            this.g(var1);
        } else if (r != null && r.type == DynamicIsland.d.c) {
            this.h(var1);
        } else if (r != null) {
            this.a(r, (int)(255.0F * var1), 1.0F);
        } else {
            this.d(var1);
        }
    }

    private void a(Color var1) {
        if (this.g.m()) {
            SkijaDraw.c(this.A, this.B, this.C, this.D, this.z());
        }

        if (this.h.m() && this.z > 0.05F) {
            SkijaDraw.a(this.A, this.B, this.C, this.D, this.z());
        }

        SkijaDraw.a(this.A, this.B, this.C, this.D, this.z(), var1);
    }

    private void f(float var1) {
        if (!(var1 <= 0.05F)) {
            Font var2 = com.heypixel.heypixelmod.obsoverlay.utils.e.b.c.d(14.0F);
            String var3 = "EixClient";
            Color var4 = a(new Color(255, 105, 180), (int)(255.0F * var1));
            Rect var5 = var2.measureText(var3);
            FontMetrics var6 = var2.getMetrics();
            float var7 = this.A + this.C / 2.0F;
            float var8 = this.B + this.D / 2.0F;
            float var9 = var7 - var5.getLeft() - var5.getWidth() / 2.0F;
            float var10 = var8 + (var6.getAscent() - var6.getDescent()) / 2.0F - var6.getAscent();
            int var11 = io.github.humbleui.skija.Color.makeARGB(var4.getAlpha(), var4.getRed(), var4.getGreen(), var4.getBlue());
            Paint var12 = new Paint().setColor(var11);

            try {
                Paint var13 = var12.makeClone().setImageFilter(ImageFilter.makeBlur(2.5F, 2.5F, FilterTileMode.DECAL));

                try {
                    SkijaDraw.e().drawString(var3, var9, var10, var2, var13);
                    SkijaDraw.e().drawString(var3, var9 - 0.2F, var10, var2, var12);
                    SkijaDraw.e().drawString(var3, var9 + 0.2F, var10, var2, var12);
                    SkijaDraw.e().drawString(var3, var9, var10, var2, var12);
                } catch (Throwable var18) {
                    if (var13 != null) {
                        try {
                            var13.close();
                        } catch (Throwable var17) {
                            var18.addSuppressed(var17);
                        }
                    }

                    throw var18;
                }

                if (var13 != null) {
                    var13.close();
                }
            } catch (Throwable var19) {
                if (var12 != null) {
                    try {
                        var12.close();
                    } catch (Throwable var16) {
                        var19.addSuppressed(var16);
                    }
                }

                throw var19;
            }

            if (var12 != null) {
                var12.close();
            }
        }
    }

    private boolean L() {
        if (EixClient.a() != null && EixClient.a().g() != null) {
            com.heypixel.heypixelmod.obsoverlay.modules.b var1 = EixClient.a().g().a(Scaffold.class);
            return var1 != null && var1.m();
        } else {
            return false;
        }
    }

    private void g(float var1) {
        if (!(var1 <= 0.05F)) {
            int var2 = com.heypixel.heypixelmod.obsoverlay.utils.p.i();
            int var3 = Math.max(1, InventoryManager.p());
            float var4 = m((float)var2 / var3);
            float var5 = 6.0F;
            float var6 = 10.0F;
            float var7 = 6.0F;
            float var8 = 6.0F;
            float var9 = var8 / 2.0F;
            Font var10 = com.heypixel.heypixelmod.obsoverlay.utils.e.b.c.a(10.0F);
            String var11 = var2 + "/" + var3;
            float var12 = SkijaDraw.a(var11, var10);
            float var13 = this.B + this.D / 2.0F;
            float var14 = this.A + var5;
            float var15 = var13 - var6 / 2.0F;
            this.a(var14, var15, var6, (int)(255.0F * var1));
            float var16 = this.A + this.C - var5 - var12;
            float var17 = a(var13, var10) - 1.0F;
            SkijaDraw.a(var11, var16, var17, a(Color.WHITE, (int)(255.0F * var1)), var10);
            float var18 = var14 + var6 + var7;
            float var19 = var16 - var7 - var18;
            if (var19 > 2.0F) {
                float var20 = var13 - var8 / 2.0F;
                float var21 = var19 * var4;
                Color var22 = a(new Color(210, 210, 210), (int)(160.0F * var1));
                Color var23 = a(new Color(196, 128, 224), (int)(220.0F * var1));
                Color var24 = a(new Color(196, 128, 224), (int)(120.0F * var1));
                Path var25 = new Path();
                var25.addRRect(RRect.makeXYWH(var18, var20, var19, var8, var9));
                SkijaDraw.c();
                SkijaDraw.e().clipPath(var25, ClipMode.INTERSECT, true);
                SkijaDraw.a(var18, var20, var19, var8, var9, var22);
                if (var21 > 0.5F) {
                    SkijaDraw.b(var18, var20, var21, var8, var9, var24);
                    SkijaDraw.a(var18, var20, var21, var8, var9, var23);
                }

                SkijaDraw.d();
                var25.close();
            }
        }
    }

    private float M() {
        float var1 = 6.0F;
        float var2 = 10.0F;
        float var3 = 6.0F;
        float var4 = 65.0F;
        Font var5 = com.heypixel.heypixelmod.obsoverlay.utils.e.b.c.a(10.0F);
        int var6 = com.heypixel.heypixelmod.obsoverlay.utils.p.i();
        int var7 = Math.max(1, InventoryManager.p());
        String var8 = var6 + "/" + var7;
        float var9 = SkijaDraw.a(var8, var5);
        float var10 = var1 + var2 + var3 + var4 + var3 + var9 + var1;
        return Math.max(90.0F, var10);
    }

    private void a(float var1, float var2, float var3, int var4) {
        Color var5 = a(new Color(180, 180, 180), var4);
        float var7 = var3 * 0.25F;
        float var9 = var2 + var7;
        float var10 = var1 + var3 - var7;
        float var11 = var2 + var7;
        float var12 = var1 + var3 - var7;
        float var13 = var2 + var3;
        float var15 = var2 + var3;
        float var16 = var1 + var7;
        float var18 = var1 + var3;
        float var20 = var1 + var3;
        float var21 = var2 + var3 - var7;
        SkijaDraw.c(var1, var9, var10, var11, 1.2F, var5);
        SkijaDraw.c(var10, var11, var12, var13, 1.2F, var5);
        SkijaDraw.c(var12, var13, var1, var15, 1.2F, var5);
        SkijaDraw.c(var1, var15, var1, var9, 1.2F, var5);
        SkijaDraw.c(var16, var2, var18, var2, 1.2F, var5);
        SkijaDraw.c(var18, var2, var20, var21, 1.2F, var5);
        SkijaDraw.c(var20, var21, var12, var13, 1.2F, var5);
        SkijaDraw.c(var16, var2, var1, var9, 1.2F, var5);
    }

    private boolean N() {
        if (EixClient.a() != null && EixClient.a().g() != null) {
            com.heypixel.heypixelmod.obsoverlay.modules.b var1 = EixClient.a().g().a(Blink.class);
            return var1 != null && var1.m();
        } else {
            return false;
        }
    }

    private void h(float var1) {
        if (!(var1 <= 0.05F)) {
            if (EixClient.a() != null && EixClient.a().g() != null) {
                Blink var2 = EixClient.a().g().a(Blink.class);
                if (var2 != null) {
                    long var3 = var2.p();
                    float var5 = var2.f.q();
                    float var6 = this.I.c;
                    float var7 = 6.0F;
                    float var8 = 10.0F;
                    float var9 = 6.0F;
                    float var10 = 6.0F;
                    float var11 = var10 / 2.0F;
                    Font var12 = com.heypixel.heypixelmod.obsoverlay.utils.e.b.c.a(10.0F);
                    String var13 = var3 + "/" + (int)var5;
                    float var14 = SkijaDraw.a(var13, var12);
                    float var15 = this.B + this.D / 2.0F;
                    float var16 = this.A + var7;
                    float var17 = var15 - var8 / 2.0F;
                    this.b(var16, var17, var8, (int)(255.0F * var1));
                    float var18 = this.A + this.C - var7 - var14;
                    float var19 = a(var15, var12) - 1.0F;
                    SkijaDraw.a(var13, var18, var19, a(Color.WHITE, (int)(255.0F * var1)), var12);
                    float var20 = var16 + var8 + var9;
                    float var21 = var18 - var9 - var20;
                    if (var21 > 2.0F) {
                        float var22 = var15 - var10 / 2.0F;
                        float var23 = var21 * var6;
                        Color var24 = a(new Color(210, 210, 210), (int)(160.0F * var1));
                        Color var25 = a(new Color(196, 128, 224), (int)(220.0F * var1));
                        Color var26 = a(new Color(196, 128, 224), (int)(120.0F * var1));
                        Path var27 = new Path();
                        var27.addRRect(RRect.makeXYWH(var20, var22, var21, var10, var11));
                        SkijaDraw.c();
                        SkijaDraw.e().clipPath(var27, ClipMode.INTERSECT, true);
                        SkijaDraw.a(var20, var22, var21, var10, var11, var24);
                        if (var23 > 0.5F) {
                            SkijaDraw.b(var20, var22, var23, var10, var11, var26);
                            SkijaDraw.a(var20, var22, var23, var10, var11, var25);
                        }

                        SkijaDraw.d();
                        var27.close();
                    }
                }
            }
        }
    }

    private float O() {
        float var1 = 6.0F;
        float var2 = 10.0F;
        float var3 = 6.0F;
        float var4 = 65.0F;
        Font var5 = com.heypixel.heypixelmod.obsoverlay.utils.e.b.c.a(10.0F);
        if (EixClient.a() != null && EixClient.a().g() != null) {
            Blink var6 = EixClient.a().g().a(Blink.class);
            if (var6 == null) {
                return 90.0F;
            } else {
                long var7 = var6.p();
                float var9 = var6.f.q();
                String var10 = var7 + "/" + (int)var9;
                float var11 = SkijaDraw.a(var10, var5);
                float var12 = var1 + var2 + var3 + var4 + var3 + var11 + var1;
                return Math.max(90.0F, var12);
            }
        } else {
            return 90.0F;
        }
    }

    private void b(float var1, float var2, float var3, int var4) {
        Color var5 = a(new Color(180, 180, 180), var4);
        float var7 = var1 + var3 / 2.0F;
        float var8 = var2 + var3 - 1.0F;
        float var9 = 1.5F;
        Paint var10 = new Paint().setColor(io.github.humbleui.skija.Color.makeARGB(var5.getAlpha(), var5.getRed(), var5.getGreen(), var5.getBlue()));
        var10.setMode(PaintMode.STROKE);
        var10.setStrokeWidth(var9);
        var10.setStrokeCap(PaintStrokeCap.ROUND);
        Paint var11 = new Paint().setColor(io.github.humbleui.skija.Color.makeARGB(var5.getAlpha(), var5.getRed(), var5.getGreen(), var5.getBlue()));
        SkijaDraw.e().drawCircle(var7, var8, 1.2F, var11);
        SkijaDraw.e().drawArc(var7 - 3.5F, var8 - 3.5F, var7 + 3.5F, var8 + 3.5F, 225.0F, 90.0F, false, var10);
        SkijaDraw.e().drawArc(var7 - 6.5F, var8 - 6.5F, var7 + 6.5F, var8 + 6.5F, 225.0F, 90.0F, false, var10);
        var10.close();
        var11.close();
    }

    private void i(float var1) {
        if (!(var1 <= 0.05F)) {
            Font var2 = com.heypixel.heypixelmod.obsoverlay.utils.e.b.c.a(10.0F);
            Font var3 = com.heypixel.heypixelmod.obsoverlay.utils.e.b.c.c(10.0F);
            float var4 = 19.0F;
            float var5 = this.B + var4 / 2.0F;
            float var6 = a(var5, var2);
            Color var7 = a(Color.WHITE, (int)(255.0F * var1));
            float var8 = 6.0F;
            float var9 = 4.0F;
            String var10 = LocalTime.now().format(c);
            String var11 = "\ue855";
            Rect var12 = SkijaDraw.b(var11, var3);
            Rect var13 = SkijaDraw.b(var10, var2);
            float var14 = var12.getWidth();
            float var15 = var13.getWidth();
            float var16 = var14 + var9;
            float var17 = Math.min(var12.getLeft(), var16 + var13.getLeft());
            float var18 = Math.max(var12.getLeft() + var14, var16 + var13.getLeft() + var15);
            float var19 = var18 - var17;
            float var20 = Math.max(50.0F, var8 * 2.0F + var19);
            float var21 = this.A - 20.0F - var20;
            float var22 = var6 + (var13.getTop() + var13.getBottom()) / 2.0F;
            float var23 = var22 - (var12.getTop() + var12.getBottom()) / 2.0F;
            float var24 = var20 - var8 * 2.0F;
            float var25 = var21 + var20 / 2.0F - var19 / 2.0F - var17;
            float var26 = var21 + var8;
            float var27 = Math.max(0.0F, var24);
            SkijaDraw.c();
            SkijaDraw.d(var26, this.B, var27, var4, this.z());
            SkijaDraw.a(var11, var25, var23, var7, var3);
            SkijaDraw.a(var10, var25 + var16, var6, var7, var2);
            SkijaDraw.d();
            String var28 = "FPS:" + a.getFps();
            String var29 = "\ue31e";
            Rect var30 = SkijaDraw.b(var29, var3);
            Rect var31 = SkijaDraw.b(var28, var2);
            float var32 = var30.getWidth();
            float var33 = var31.getWidth();
            float var34 = var32 + var9;
            float var35 = Math.min(var30.getLeft(), var34 + var31.getLeft());
            float var36 = Math.max(var30.getLeft() + var32, var34 + var31.getLeft() + var33);
            float var37 = var36 - var35;
            float var38 = Math.max(50.0F, var8 * 2.0F + var37);
            float var39 = this.A + this.C + 20.0F;
            float var40 = var6 + (var31.getTop() + var31.getBottom()) / 2.0F;
            float var41 = var40 - (var30.getTop() + var30.getBottom()) / 2.0F;
            float var42 = var38 - var8 * 2.0F;
            float var43 = var39 + var38 / 2.0F - var37 / 2.0F - var35;
            float var44 = var39 + var8;
            float var45 = Math.max(0.0F, var42);
            SkijaDraw.c();
            SkijaDraw.d(var44, this.B, var45, var4, this.z());
            SkijaDraw.a(var29, var43, var41, var7, var3);
            SkijaDraw.a(var28, var43 + var34, var6, var7, var2);
            SkijaDraw.d();
        }
    }

    private void P() {
        if (this.U != null) {
            float var1 = this.A + 8.0F;
            float var2 = this.B + 8.0F;
            float var3 = this.A + this.C - 8.0F;
            float var4 = this.B + this.D - 8.0F;
            if (!(var3 <= var1) && !(var4 <= var2)) {
                float var5 = var3 - var1;
                Font var6 = com.heypixel.heypixelmod.obsoverlay.utils.e.b.c.a(10.0F);
                float var7 = a(var6);
                float var8 = m((this.B() - 0.15F) / 0.85F);
                if (!(var8 <= 0.0F)) {
                    int var9 = (int)(255.0F * var8);
                    Color var10 = a(Color.WHITE, var9);
                    Color var11 = new Color(160, 160, 160, var9);
                    SkijaDraw.c();
                    SkijaDraw.d(var1, var2, var5, var4 - var2, 0.0F);
                    float var12 = this.B + 12.0F;
                    Object var13 = d;
                    if (var13 == null || var13.getString().isEmpty()) {
                        var13 = Component.literal("Players: " + this.U.size());
                    }

                    for (String var16 : a(var13.getString(), var5, var6)) {
                        float var17 = SkijaDraw.a(var16, var6);
                        float var18 = this.A + (this.C - var17) / 2.0F;
                        float var19 = a(var12 + var7 / 2.0F, var6);
                        SkijaDraw.a(var16, var18, var19, var10, var6);
                        var12 += var7;
                    }

                    var12 += 8.0F;
                    float var36 = Math.max(this.B + 30.0F, var12);
                    float var37 = 14.0F;
                    float var38 = 10.0F;
                    float var39 = Math.max(0.0F, (var37 - var38) / 2.0F);
                    int var40 = 0;

                    for (PlayerInfo var21 : this.U) {
                        float var22 = var36 + var40 * var37;
                        if (var22 + var37 > var4) {
                            break;
                        }

                        float var24 = var22 + var39;
                        float var25 = var22 + var37 / 2.0F;
                        float var26 = a(var25, var6) - 1.5F;
                        Object var27 = null;
                        if ((a.level == null ? null : a.level.getPlayerByUUID(var21.getProfile().getId())) instanceof AbstractClientPlayer var29) {
                            var27 = var29.getSkinTextureLocation();
                        } else {
                            var27 = var21.getSkinLocation();
                        }

                        if (var27 != null) {
                            SkijaDraw.a((ResourceLocation)var27, var1, var24, var38, var38, 2.0F);
                        }

                        String var49 = var21.getLatency() + "ms";
                        float var30 = SkijaDraw.a(var49, var6);
                        float var31 = var3 - var30;
                        SkijaDraw.a(var49, var31, var26, var11, var6);
                        String var32 = this.e(var21.getProfile().getName());
                        float var33 = var1 + var38 + 4.0F;
                        float var34 = var31 - 6.0F;
                        if (var34 > var33) {
                            SkijaDraw.c();
                            SkijaDraw.d(var33, var22, var34 - var33, var37, 0.0F);
                            SkijaDraw.a(var32, var33, var26, var10, var6);
                            SkijaDraw.d();
                        }

                        var40++;
                    }

                    Component var41 = e;
                    if (var41 != null && !var41.getString().isEmpty()) {
                        List var42 = a(var41.getString(), var5, var6);
                        float var43 = var4 - var42.size() * var7;

                        for (String var44 : var42) {
                            float var45 = SkijaDraw.a(var44, var6);
                            float var46 = this.A + (this.C - var45) / 2.0F;
                            float var48 = a(var43 + var7 / 2.0F, var6);
                            SkijaDraw.a(var44, var46, var48, var10, var6);
                            var43 += var7;
                        }
                    }

                    SkijaDraw.d();
                }
            }
        }
    }

    private String e(String var1) {
        return "EixClient";
    }

    private void a(DynamicIsland.ToggleInfo var1, int var2, float var3) {
        if (var1 != null) {
            if (var1.type == DynamicIsland.d.d) {
                this.a(var1, var2);
            } else if (var1.type == DynamicIsland.d.e) {
                this.b(var1, var2);
            } else {
                float var4 = 6.0F;
                float var5 = 12.0F;
                float var6 = 5.0F;
                Font var7 = com.heypixel.heypixelmod.obsoverlay.utils.e.b.c.a(var5);
                float var8 = this.B + this.D / 2.0F;
                float var9 = var8 - var5 / 2.0F + 1.0F;
                this.a(this.A + var4, var9, var5, var1.enabled, var2);
                float var10 = this.A + var4 + var5 + var6;
                float var11 = this.A + this.C - var4 - var10;
                float var12 = SkijaDraw.a(var1.name, var7);
                float var13 = var10 + Math.max(0.0F, (var11 - var12) / 2.0F);
                float var14 = a(var8, var7) - 1.5F;
                SkijaDraw.a(var1.name, var13, var14, a(Color.WHITE, var2), var7);
            }
        }
    }

    private void a(DynamicIsland.ToggleInfo var1, int var2) {
        float var3 = 6.0F;
        float var4 = 9.0F;
        float var5 = 5.0F;
        Font var6 = com.heypixel.heypixelmod.obsoverlay.utils.e.b.c.a(var4);
        float var7 = this.B + this.D / 2.0F;
        float var8 = this.A + var3 + var4 / 2.0F;
        float var9 = var7;
        Color var10 = a(new Color(255, 105, 180), var2);
        int var11 = io.github.humbleui.skija.Color.makeARGB(var10.getAlpha(), var10.getRed(), var10.getGreen(), var10.getBlue());
        Paint var12 = new Paint().setColor(var11);

        try {
            Paint var13 = var12.makeClone().setImageFilter(ImageFilter.makeBlur(1.6F, 1.6F, FilterTileMode.DECAL));

            try {
                SkijaDraw.e().drawCircle(var8, var9, var4 / 2.0F, var13);
                SkijaDraw.e().drawCircle(var8, var9, var4 / 2.0F, var12);
            } catch (Throwable var19) {
                if (var13 != null) {
                    try {
                        var13.close();
                    } catch (Throwable var18) {
                        var19.addSuppressed(var18);
                    }
                }

                throw var19;
            }

            if (var13 != null) {
                var13.close();
            }
        } catch (Throwable var20) {
            if (var12 != null) {
                try {
                    var12.close();
                } catch (Throwable var17) {
                    var20.addSuppressed(var17);
                }
            }

            throw var20;
        }

        if (var12 != null) {
            var12.close();
        }

        float var21 = this.A + var3 + var4 + var5;
        float var22 = this.A + this.C - var3 - var21;
        float var14 = SkijaDraw.a(var1.name, var6);
        float var15 = var21 + Math.max(0.0F, (var22 - var14) / 2.0F);
        float var16 = a(var7, var6) - 1.0F;
        SkijaDraw.a(var1.name, var15, var16, a(Color.WHITE, var2), var6);
    }

    private void b(DynamicIsland.ToggleInfo var1, int var2) {
        float var3 = 6.0F;
        float var4 = 12.0F;
        float var5 = 5.0F;
        Font var6 = com.heypixel.heypixelmod.obsoverlay.utils.e.b.c.a(var4);
        float var7 = this.B + this.D / 2.0F;
        float var8 = this.A + var3 + var4 / 2.0F;
        float var9 = var7 + 1.0F;
        Color var10 = a(new Color(255, 215, 0), var2);
        int var11 = io.github.humbleui.skija.Color.makeARGB(var10.getAlpha(), var10.getRed(), var10.getGreen(), var10.getBlue());
        Paint var12 = new Paint().setColor(var11);

        try {
            Paint var13 = var12.makeClone().setImageFilter(ImageFilter.makeBlur(1.6F, 1.6F, FilterTileMode.DECAL));

            try {
                SkijaDraw.e().drawCircle(var8, var9, var4 / 2.0F, var13);
                SkijaDraw.e().drawCircle(var8, var9, var4 / 2.0F, var12);
            } catch (Throwable var19) {
                if (var13 != null) {
                    try {
                        var13.close();
                    } catch (Throwable var18) {
                        var19.addSuppressed(var18);
                    }
                }

                throw var19;
            }

            if (var13 != null) {
                var13.close();
            }
        } catch (Throwable var20) {
            if (var12 != null) {
                try {
                    var12.close();
                } catch (Throwable var17) {
                    var20.addSuppressed(var17);
                }
            }

            throw var20;
        }

        if (var12 != null) {
            var12.close();
        }

        float var21 = this.A + var3 + var4 + var5;
        float var22 = this.A + this.C - var3 - var21;
        float var14 = SkijaDraw.a(var1.name, var6);
        float var15 = var21 + Math.max(0.0F, (var22 - var14) / 2.0F);
        float var16 = a(var7, var6) - 1.5F;
        SkijaDraw.a(var1.name, var15, var16, a(Color.WHITE, var2), var6);
    }

    private void a(float var1, float var2, float var3, boolean var4, int var5) {
        Color var6 = var4 ? new Color(80, 220, 100, var5) : new Color(220, 80, 80, var5);
        float var7 = 2.0F;
        if (var4) {
            float var8 = var1 + var3 * 0.15F;
            float var9 = var2 + var3 * 0.5F;
            float var10 = var1 + var3 * 0.4F;
            float var11 = var2 + var3 * 0.8F;
            float var12 = var1 + var3 * 0.85F;
            float var13 = var2 + var3 * 0.25F;
            SkijaDraw.c(var8, var9, var10, var11, var7, var6);
            SkijaDraw.c(var10, var11, var12, var13, var7, var6);
        } else {
            float var14 = var3 * 0.2F;
            SkijaDraw.c(var1 + var14, var2 + var14, var1 + var3 - var14, var2 + var3 - var14, var7, var6);
            SkijaDraw.c(var1 + var3 - var14, var2 + var14, var1 + var14, var2 + var3 - var14, var7, var6);
        }
    }

    private float Q() {
        return this.b(r);
    }

    private float b(DynamicIsland.ToggleInfo var1) {
        if (var1 == null) {
            return 90.0F;
        } else if (var1.type == DynamicIsland.d.b) {
            return this.M();
        } else if (var1.type == DynamicIsland.d.c) {
            return this.O();
        } else {
            float var2 = 6.0F;
            float var3 = var1.type == DynamicIsland.d.d ? 9.0F : 12.0F;
            float var4 = 5.0F;
            Font var5 = com.heypixel.heypixelmod.obsoverlay.utils.e.b.c.a(var3);
            float var6 = SkijaDraw.a(var1.name, var5);
            float var7 = var2 + var3 + var4 + var6 + var2 + 6.0F;
            return Math.max(90.0F, var7);
        }
    }

    private long R() {
        return this.u == -1L ? 0L : System.currentTimeMillis() - this.u;
    }

    private long S() {
        return this.v == -1L ? 0L : System.currentTimeMillis() - this.v;
    }

    private static float j(float var0) {
        float var1 = 1.0F - var0;
        return 1.0F - var1 * var1 * var1;
    }

    private static float k(float var0) {
        if (var0 < 0.5F) {
            return 4.0F * var0 * var0 * var0;
        } else {
            float var1 = -2.0F * var0 + 2.0F;
            return 1.0F - var1 * var1 * var1 / 2.0F;
        }
    }

    private static int l(float var0) {
        return (int)(255.0F * var0);
    }

    private static Color a(Color var0, int var1) {
        return new Color(var0.getRed(), var0.getGreen(), var0.getBlue(), Math.max(0, Math.min(255, var1)));
    }

    private static float m(float var0) {
        if (var0 < 0.0F) {
            return 0.0F;
        } else {
            return var0 > 1.0F ? 1.0F : var0;
        }
    }

    private static float a(float var0, float var1, float var2) {
        return var1 + (var2 - var1) * var0;
    }

    private static float a(Font var0) {
        FontMetrics var1 = var0.getMetrics();
        return var1.getDescent() - var1.getAscent();
    }

    private static float a(float var0, Font var1) {
        FontMetrics var2 = var1.getMetrics();
        return var0 - (var2.getAscent() + var2.getDescent()) / 2.0F - 7.0F;
    }

    private static List<String> a(String var0, float var1, Font var2) {
        java.util.ArrayList var3 = new java.util.ArrayList();
        if (var0 != null && !var0.isEmpty()) {
            String[] var4 = var0.split(" ");
            StringBuilder var5 = new StringBuilder();

            for (String var9 : var4) {
                String var10 = var5.length() == 0 ? var9 : var5 + " " + var9;
                if (SkijaDraw.a(var10, var2) <= var1) {
                    var5.setLength(0);
                    var5.append(var10);
                } else {
                    if (var5.length() > 0) {
                        var3.add(var5.toString());
                        var5.setLength(0);
                    }

                    if (SkijaDraw.a(var9, var2) <= var1) {
                        var5.append(var9);
                    } else {
                        StringBuilder var11 = new StringBuilder();

                        for (int var12 = 0; var12 < var9.length(); var12++) {
                            var11.append(var9.charAt(var12));
                            if (SkijaDraw.a(var11.toString(), var2) > var1 && var11.length() > 1) {
                                var11.deleteCharAt(var11.length() - 1);
                                var3.add(var11.toString());
                                var11.setLength(0);
                                var11.append(var9.charAt(var12));
                            }
                        }

                        if (var11.length() > 0) {
                            var5.append((CharSequence)var11);
                        }
                    }
                }
            }

            if (var5.length() > 0) {
                var3.add(var5.toString());
            }

            return var3;
        } else {
            var3.add("");
            return var3;
        }
    }

    private record ToggleInfo(String name, boolean enabled, DynamicIsland.d type) {
    }

    private static enum a {
        a,
        b,
        c,
        d,
        e,
        f,
        g,
        h;
    }

    private static final class b {
        static final float a = 65.0F;
        static final float b = 19.0F;
        static final float c = 90.0F;
        static final float d = 25.0F;
        static final float e = 20.0F;
        static final float f = 50.0F;
        static final float g = 14.0F;
        static final float h = 10.0F;
        static final Color i = new Color(18, 18, 18, 70);
        static final float j = 14.0F;
        static final float k = 8.0F;
        static final float l = 12.0F;
        static final float m = 30.0F;
        static final int n = 1;
        static final float o = 36.0F;
        static final float p = 7.0F;
        static final float q = 8.5F;
        static final float r = 11.5F;
        static final float s = 8.5F;
        static final float t = 2.0F;
    }

    private static final class c {
        static final long a = 220L;
        static final long b = 1200L;
        static final long c = 220L;
        static final long d = 260L;
        static final long e = 1900L;
        static final long f = 380L;
    }

    private static enum d {
        a,
        b,
        c,
        d,
        e;
    }
}
