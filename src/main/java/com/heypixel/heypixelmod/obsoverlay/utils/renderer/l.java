package com.heypixel.heypixelmod.obsoverlay.utils.renderer;

import com.heypixel.heypixelmod.obsoverlay.EixClient;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventRender2D;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventShader;
import com.heypixel.heypixelmod.obsoverlay.utils.al;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;

public class l {
    private static final al a = new al();
    private static e b;
    private static e c;
    private static e d;
    private static i e;

    public static void a(EventRender2D var0, float var1) {
        Window var2 = Minecraft.getInstance().getWindow();
        if (e == null) {
            e = new i("shadow.vert", "shadow.frag");
            b = new e();
            c = new e();
            d = new e();
        }

        boolean var3 = false;
        if (a.a((double)(1000.0F / var1))) {
            var3 = true;
            a.a();
        }

        if (var3) {
            d.d();
            b.d();
            c.d();
            d.a();
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            EixClient.a()
                .b()
                .a((com.heypixel.heypixelmod.obsoverlay.events.api.a.b)(new EventShader(var0.stack(), com.heypixel.heypixelmod.obsoverlay.events.api.b.a.k)));
            d.c();
        }

        f.k();
        e.a();
        e.a("u_Size", var2.getWidth(), var2.getHeight());
        h.a(var0.stack());
        if (var3) {
            b.a();
            f.q(d.a);
            e.a("u_Direction", 1.0, 0.0);
            h.b(var0.stack());
            c.a();
            f.q(b.a);
            e.a("u_Direction", 0.0, 1.0);
            h.b(var0.stack());
            b.a();
            f.q(c.a);
            e.a("u_Direction", 1.0, 0.0);
            h.b(var0.stack());
            c.c();
        }

        f.q(b.a);
        e.a("u_Direction", 0.0, 1.0);
        h.b(var0.stack());
        f.l();
        h.b();
    }
}
