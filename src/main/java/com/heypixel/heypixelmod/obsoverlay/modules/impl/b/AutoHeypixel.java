package com.heypixel.heypixelmod.obsoverlay.modules.impl.b;

import com.heypixel.heypixelmod.obsoverlay.d.d;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.m;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.a;
import com.heypixel.heypixelmod.obsoverlay.modules.b;
import com.heypixel.heypixelmod.obsoverlay.utils.ChatUtil;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Screenshot;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;

@ModuleInfo(
    a = "AutoHeypixel",
    b = "自动岛吉吉",
    c = "Auto play Heypixel server.",
    d = com.heypixel.heypixelmod.obsoverlay.modules.a.MISC
)
public class AutoHeypixel extends b {
    public com.heypixel.heypixelmod.obsoverlay.d.a.a c = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Auto Screenshot").a(true).a().b();
    public com.heypixel.heypixelmod.obsoverlay.d.a.a d = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Auto Play").a(true).a().b();

    @EventTarget
    public void onPacker(m var1) {
        if (a.player != null && a.level != null) {
            Packet var2 = var1.c();
            if (var2 instanceof ClientboundSetTitleTextPacket) {
                boolean var3 = ((ClientboundSetTitleTextPacket)var2).getText().getString().contains("胜利");
                if (var3) {
                    if (this.c.m()) {
                        CompletableFuture.runAsync(() -> {
                                try {
                                    TimeUnit.SECONDS.sleep(1L);
                                } catch (InterruptedException var1x) {
                                    Thread.currentThread().interrupt();
                                }
                            })
                            .thenRun(
                                () -> a.execute(
                                    () -> {
                                        if (a.player != null && a.level != null) {
                                            Screenshot.grab(
                                                com.heypixel.heypixelmod.obsoverlay.b.b.clientFolder.getParentFile(),
                                                a.getMainRenderTarget(),
                                                var0 -> ChatUtil.a(var0.getString())
                                            );
                                        }
                                    }
                                )
                            );
                    }

                    if (this.d.m()) {
                        CompletableFuture.runAsync(() -> {
                            try {
                                TimeUnit.MILLISECONDS.sleep(500L);
                            } catch (InterruptedException var1x) {
                                Thread.currentThread().interrupt();
                            }
                        }).thenRun(() -> a.execute(() -> {
                            if (a.player != null && a.level != null) {
                                int var0 = a.player.getInventory().selected;
                                byte var1x = 4;
                                a.player.getInventory().selected = var1x;
                                KeyMapping.click(a.options.keyUse.getKey());
                                a.player.getInventory().selected = var0;
                            }
                        }));
                    }
                }
            }
        }
    }
}
