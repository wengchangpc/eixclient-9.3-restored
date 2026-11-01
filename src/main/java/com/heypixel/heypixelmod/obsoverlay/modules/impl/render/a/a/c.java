package com.heypixel.heypixelmod.obsoverlay.modules.impl.render.a.a;

import java.awt.Color;
import java.util.Collections;
import java.util.HashSet;
import net.minecraft.world.entity.projectile.ThrownPotion;

public class c extends a {
    public c() {
        super(new HashSet<>(Collections.singleton(ThrownPotion.class)), new Color(255, 66, 249));
    }

    @Override
    public float c() {
        return 0.05F;
    }
}
