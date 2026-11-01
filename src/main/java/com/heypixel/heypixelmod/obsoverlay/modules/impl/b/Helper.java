package com.heypixel.heypixelmod.obsoverlay.modules.impl.b;

import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.h;
import com.heypixel.heypixelmod.obsoverlay.events.impl.k;
import com.heypixel.heypixelmod.obsoverlay.events.impl.u;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.a;
import com.heypixel.heypixelmod.obsoverlay.modules.b;
import com.heypixel.heypixelmod.obsoverlay.utils.ChatUtil;
import com.heypixel.heypixelmod.obsoverlay.utils.al;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;

@ModuleInfo(
    a = "Helper",
    b = "助手",
    c = "Bed wars Info helper",
    d = com.heypixel.heypixelmod.obsoverlay.modules.a.MISC
)
public class Helper extends b {
    al c = new al();
    al d = new al();
    private int e;
    private int f = 1;
    private boolean g = false;

    @EventTarget
    public void onMotion(k var1) {
        if (var1.b() == com.heypixel.heypixelmod.obsoverlay.events.api.b.a.a) {
            if (this.c.a(30000.0) && this.g) {
                this.e++;
                ChatUtil.a("第" + this.e + "波钻石刷新");
                this.c.a();
            }

            if (this.d.a(60000.0) && this.g) {
                this.f++;
                ChatUtil.a("第" + this.f + "波绿宝石刷新");
                this.d.a();
            }
        }
    }

    @EventTarget
    public void onRespawn(u var1) {
        this.e = 0;
        this.f = 0;
    }

    @EventTarget(
        a = 0
    )
    public void onPacket(h var1) {
        try {
            if (a.player != null && !var1.a() && var1.b() instanceof ClientboundSystemChatPacket var2) {
                String var5 = var2.content().getString();
                if (var5.contains("游戏准备开始")) {
                    this.c.a();
                    this.d.a();
                    this.g = true;
                }

                if (var5.contains("游戏结束")) {
                    this.g = false;
                }
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }
    }
}
