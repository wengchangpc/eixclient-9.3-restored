package com.heypixel.heypixelmod.obsoverlay.modules.impl.render;

import com.heypixel.heypixelmod.obsoverlay.c.ClickGuiScreen;
import com.heypixel.heypixelmod.obsoverlay.d.d;
import com.heypixel.heypixelmod.obsoverlay.d.a.e;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.a;
import com.heypixel.heypixelmod.obsoverlay.modules.b;

@ModuleInfo(
    a = "ClickGUI",
    b = "点击用户图形界面",
    d = com.heypixel.heypixelmod.obsoverlay.modules.a.RENDER,
    c = "The ClickGUI"
)
public class ClickGUI extends b {
    public e c = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Language")
        .a("English", "中文")
        .a(var0 -> com.heypixel.heypixelmod.obsoverlay.modules.b.b = true)
        .a()
        .e();
    public e d = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Style").a("Naven").a(var0 -> com.heypixel.heypixelmod.obsoverlay.modules.b.b = true).a().e();
    ClickGuiScreen e = null;

    @Override
    protected void c() {
        super.c();
        this.b(344);
    }

    @Override
    public void d() {
        if (this.e == null) {
            this.e = new ClickGuiScreen();
        }

        a.setScreen(this.e);
        this.f();
    }

    public String p() {
        return this.c.l();
    }
}
