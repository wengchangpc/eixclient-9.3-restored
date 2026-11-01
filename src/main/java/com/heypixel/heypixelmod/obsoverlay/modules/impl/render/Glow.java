package com.heypixel.heypixelmod.obsoverlay.modules.impl.render;

import com.heypixel.heypixelmod.obsoverlay.EixClient;
import com.heypixel.heypixelmod.obsoverlay.d.d;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.a;
import com.heypixel.heypixelmod.obsoverlay.modules.b;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;

@ModuleInfo(
    a = "Glow",
    b = "透视",
    c = "Glow effect for entities",
    d = com.heypixel.heypixelmod.obsoverlay.modules.a.RENDER
)
public class Glow extends b {
    com.heypixel.heypixelmod.obsoverlay.d.a.a c = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Player").a(true).a().b();
    com.heypixel.heypixelmod.obsoverlay.d.a.a d = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Items").a(false).a().b();
    com.heypixel.heypixelmod.obsoverlay.d.a.a e = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Mobs").a(false).a().b();
    com.heypixel.heypixelmod.obsoverlay.d.a.a f = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Animals").a(false).a().b();
    com.heypixel.heypixelmod.obsoverlay.d.a.a g = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Arrows").a(false).a().b();

    public static boolean a(Entity var0) {
        Glow var1 = EixClient.a().g().a(Glow.class);
        if (!var1.m()) {
            return false;
        } else if (var0 instanceof Player && var1.c.m()) {
            return true;
        } else if (var0 instanceof ItemEntity && var1.d.m()) {
            return true;
        } else {
            return var0 instanceof Mob && var1.e.m() ? true : var0 instanceof Animal && var1.f.m() || var0 instanceof Arrow && var1.g.m();
        }
    }
}
