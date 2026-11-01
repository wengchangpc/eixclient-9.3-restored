package com.heypixel.heypixelmod.obsoverlay.modules.impl.render;

import com.heypixel.heypixelmod.obsoverlay.d.d;
import com.heypixel.heypixelmod.obsoverlay.d.a.c;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.a;
import com.heypixel.heypixelmod.obsoverlay.modules.b;

@ModuleInfo(
    a = "PostProcess",
    b = "后处理",
    c = "Post process effects",
    d = com.heypixel.heypixelmod.obsoverlay.modules.a.RENDER
)
public class PostProcess extends b {
    private static PostProcess c;
    private final com.heypixel.heypixelmod.obsoverlay.d.a.a d = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "FastBlur").a(false).a().b();
    private final c e = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Blur FPS").d(1.0F).a(90.0F).b(15.0F).c(120.0F).a(this.d::m).a().c();
    private final c f = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Blur Strength").a(2.0F).b(0.0F).c(19.0F).d(1.0F).a().c();
    private final com.heypixel.heypixelmod.obsoverlay.d.a.a g = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Glow").a(true).a().b();

    public PostProcess() {
        c = this;
    }

    @Override
    public void d() {
        this.a(false);
    }

    public int p() {
        return (int)this.f.q();
    }

    public int q() {
        return (int)this.e.q();
    }

    public boolean r() {
        return this.d.m();
    }

    public static boolean s() {
        return c != null && c.g.m();
    }
}
