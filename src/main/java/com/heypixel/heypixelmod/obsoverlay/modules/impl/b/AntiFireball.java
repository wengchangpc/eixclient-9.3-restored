package com.heypixel.heypixelmod.obsoverlay.modules.impl.b;

import com.heypixel.heypixelmod.obsoverlay.EixClient;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.k;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.a;
import com.heypixel.heypixelmod.obsoverlay.modules.b;
import com.heypixel.heypixelmod.obsoverlay.modules.impl.c.LongJump;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.projectile.Fireball;

@ModuleInfo(
    a = "AntiFireball",
    b = "反火球",
    c = "Prevents fireballs from damaging you",
    d = com.heypixel.heypixelmod.obsoverlay.modules.a.MISC
)
public class AntiFireball extends b {
    @EventTarget
    public void onMotion(k var1) {
        if (!EixClient.a().g().a(LongJump.class).m() && var1.b() == com.heypixel.heypixelmod.obsoverlay.events.api.b.a.a) {
            Stream var2 = StreamSupport.stream(a.level.entitiesForRendering().spliterator(), true);
            Optional var3 = var2.filter(var0 -> var0 instanceof Fireball && a.player.distanceTo(var0) < 6.0F).map(var0 -> (Fireball)var0).findFirst();
            if (!var3.isPresent()) {
                return;
            }

            Fireball var4 = (Fireball)var3.get();
            a.gameMode.attack(a.player, var4);
            a.player.swing(InteractionHand.MAIN_HAND);
        }
    }
}
