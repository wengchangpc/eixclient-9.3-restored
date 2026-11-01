package com.heypixel.heypixelmod.obsoverlay.modules.impl.render;

import com.heypixel.heypixelmod.obsoverlay.d.d;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.s;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.a;
import com.heypixel.heypixelmod.obsoverlay.modules.b;
import com.heypixel.heypixelmod.obsoverlay.utils.e.SkijaDraw;
import java.awt.Color;

@ModuleInfo(
    a = "Test",
    b = "测试",
    c = "",
    d = com.heypixel.heypixelmod.obsoverlay.modules.a.RENDER
)
public class Test extends b {
    public com.heypixel.heypixelmod.obsoverlay.d.a.b c = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Position").e(10.0F).f(10.0F).a().f();

    @EventTarget
    public void onRenderSkia(s var1) {
        SkijaDraw.c(this.c.n(), this.c.o(), 50.0F, 50.0F, 5.0F);
        SkijaDraw.a(this.c.n(), this.c.o(), 50.0F, 50.0F, 5.0F);
        SkijaDraw.a(this.c.n(), this.c.o(), 50.0F, 50.0F, 5.0F, new Color(0, 0, 0, 90));
        this.c.c(50.0F);
        this.c.d(50.0F);
    }
}
