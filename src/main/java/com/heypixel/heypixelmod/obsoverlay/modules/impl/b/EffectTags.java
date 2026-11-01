package com.heypixel.heypixelmod.obsoverlay.modules.impl.b;

import com.heypixel.heypixelmod.obsoverlay.d.d;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventRender2D;
import com.heypixel.heypixelmod.obsoverlay.events.impl.p;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.b;
import com.heypixel.heypixelmod.obsoverlay.utils.aa;
import com.heypixel.heypixelmod.obsoverlay.utils.af;
import com.heypixel.heypixelmod.obsoverlay.utils.i;
import java.awt.Color;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.entity.Entity;
import org.antlr.v4.runtime.misc.OrderedHashSet;

@ModuleInfo(
    a = "EffectTags",
    b = "效果标签",
    c = "Show the player's effect tags.",
    d = com.heypixel.heypixelmod.obsoverlay.modules.a.MISC
)
public class EffectTags extends b {
    private final com.heypixel.heypixelmod.obsoverlay.d.a.a c = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Debug").a(false).a().b();
    private final com.heypixel.heypixelmod.obsoverlay.d.a.a d = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Shared").a(true).a().b();
    private final List<EffectTags.a> e = new CopyOnWriteArrayList<>();

    @EventTarget
    public void update(p var1) {
        try {
            this.a(var1.a());
        } catch (Exception var3) {
        }
    }

    private void a(float var1) {
        this.e.clear();

        for (Entity var3 : a.level.entitiesForRendering()) {
            if (var3 != a.player && var3 instanceof AbstractClientPlayer) {
                double var4 = a(var1, var3.xo, var3.getX());
                double var6 = a(var1, var3.yo, var3.getY()) + var3.getBbHeight();
                double var8 = a(var1, var3.zo, var3.getZ());
                com.heypixel.heypixelmod.obsoverlay.utils.f.b var10 = aa.a(var4, var6, var8, var1);
                this.e.add(new EffectTags.a((AbstractClientPlayer)var3, var10, com.heypixel.heypixelmod.obsoverlay.utils.i.a((AbstractClientPlayer)var3)));
            }
        }

        if (this.d.m()) {
            Map var12 = com.heypixel.heypixelmod.obsoverlay.utils.i.a();

            for (af var14 : var12.values()) {
                double var5 = var14.b();
                double var7 = var14.c() + a.player.getBbHeight();
                double var9 = var14.d();
                com.heypixel.heypixelmod.obsoverlay.utils.f.b var11 = aa.a(var5, var7, var9, var1);
                this.e.add(new EffectTags.a(null, var11, Set.of(var14.i())));
            }
        }
    }

    @EventTarget
    public void onRender(EventRender2D var1) {
        for (EffectTags.a var3 : this.e) {
            var1.stack().pushPose();
            double var4 = 0.0;

            for (String var7 : var3.c()) {
                com.heypixel.heypixelmod.obsoverlay.utils.renderer.d.a
                    .a(var1.stack(), I18n.get(var7, new Object[0]), var3.b().a + 10.0F, var3.b().b + var4, Color.RED, true, 0.3F);
                var4 += com.heypixel.heypixelmod.obsoverlay.utils.renderer.d.a.a(true, 0.3F);
            }

            if (this.c.m() && var3.a() != null) {
                AbstractClientPlayer var10 = var3.a();
                OrderedHashSet var11 = new OrderedHashSet();
                var11.add("X: " + var10.getX());
                var11.add("Y: " + var10.getY());
                var11.add("Z: " + var10.getZ());
                var11.add("Ticks: " + var10.tickCount);

                for (String var9 : var11) {
                    com.heypixel.heypixelmod.obsoverlay.utils.renderer.d.a.a(var1.stack(), var9, var3.b().a + 10.0F, var3.b().b + var4, Color.RED, true, 0.35F);
                    var4 += com.heypixel.heypixelmod.obsoverlay.utils.renderer.d.a.a(true, 0.35F);
                }
            }

            var1.stack().popPose();
        }
    }

    public static float a(float var0, float var1, float var2) {
        return var1 + var0 * (var2 - var1);
    }

    public static double a(double var0, double var2, float var4) {
        return var2 + var0 * (var4 - var2);
    }

    public static double a(float var0, double var1, double var3) {
        return var1 + var0 * (var3 - var1);
    }

    private static class a {
        AbstractClientPlayer a;
        com.heypixel.heypixelmod.obsoverlay.utils.f.b b;
        Set<String> c;

        public a(AbstractClientPlayer var1, com.heypixel.heypixelmod.obsoverlay.utils.f.b var2, Set<String> var3) {
            this.a = var1;
            this.b = var2;
            this.c = var3;
        }

        public AbstractClientPlayer a() {
            return this.a;
        }

        public void a(AbstractClientPlayer var1) {
            this.a = var1;
        }

        public com.heypixel.heypixelmod.obsoverlay.utils.f.b b() {
            return this.b;
        }

        public void a(com.heypixel.heypixelmod.obsoverlay.utils.f.b var1) {
            this.b = var1;
        }

        public Set<String> c() {
            return this.c;
        }

        public void a(Set<String> var1) {
            this.c = var1;
        }

        @Override
        public boolean equals(Object var1) {
            if (var1 == this) {
                return true;
            } else if (var1 instanceof EffectTags.a var2) {
                if (!var2.a(this)) {
                    return false;
                } else {
                    AbstractClientPlayer var3 = this.a();
                    AbstractClientPlayer var4 = var2.a();
                    if (Objects.equals(var3, var4)) {
                        com.heypixel.heypixelmod.obsoverlay.utils.f.b var5 = this.b();
                        com.heypixel.heypixelmod.obsoverlay.utils.f.b var6 = var2.b();
                        if (Objects.equals(var5, var6)) {
                            Set var7 = this.c();
                            Set var8 = var2.c();
                            return Objects.equals(var7, var8);
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            } else {
                return false;
            }
        }

        protected boolean a(Object var1) {
            return var1 instanceof EffectTags.a;
        }

        @Override
        public int hashCode() {
            byte var1 = 59;
            int var2 = 1;
            AbstractClientPlayer var3 = this.a();
            var2 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
            com.heypixel.heypixelmod.obsoverlay.utils.f.b var4 = this.b();
            var2 = var2 * 59 + (var4 == null ? 43 : var4.hashCode());
            Set var5 = this.c();
            return var2 * 59 + (var5 == null ? 43 : var5.hashCode());
        }

        @Override
        public String toString() {
            return "ItemTracker.TargetInfo(player=" + this.a() + ", position=" + this.b() + ", description=" + this.c() + ")";
        }
    }
}
