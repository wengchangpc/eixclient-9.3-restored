package com.heypixel.heypixelmod.obsoverlay.modules.impl.render;

import com.heypixel.heypixelmod.obsoverlay.d.d;
import com.heypixel.heypixelmod.obsoverlay.d.a.c;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.a;
import com.heypixel.heypixelmod.obsoverlay.modules.b;

@ModuleInfo(
    a = "FullBright",
    b = "光明",
    c = "Make your world brighter.",
    d = com.heypixel.heypixelmod.obsoverlay.modules.a.RENDER
)
public class FullBright extends b {
    public c c = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Brightness").a(1.0F).d(0.1F).b(0.0F).c(1.0F).a().c();
}
