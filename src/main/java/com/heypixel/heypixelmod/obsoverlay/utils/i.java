package com.heypixel.heypixelmod.obsoverlay.utils;

import com.heypixel.heypixelmod.mixin.O.accessors.LivingEntityAccessor;
import com.heypixel.heypixelmod.obsoverlay.EixClient;
import com.heypixel.heypixelmod.obsoverlay.c.a.b;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.api.b.a;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import org.antlr.v4.runtime.misc.OrderedHashSet;

public class i {
    private static final Minecraft a = Minecraft.getInstance();
    private static final Map<Entity, Set<String>> b = new ConcurrentHashMap<>();
    private static final Map<String, af> c = new ConcurrentHashMap<>();

    public static Set<String> a(AbstractClientPlayer var0) {
        List var1 = z.a((Integer)var0.getEntityData().get(LivingEntityAccessor.getEffectColorId()));
        var1.remove(MobEffects.ABSORPTION);
        OrderedHashSet var2 = new OrderedHashSet();
        if (b.containsKey(var0)) {
            var2.addAll(b.get(var0));
        }

        Set var3 = var1.stream().map(var0x -> "effect.minecraft." + BuiltInRegistries.MOB_EFFECT.getKey(var0x).getPath()).collect(Collectors.toSet());
        var2.addAll(var3);
        return var2;
    }

    public static Map<String, af> a() {
        return c;
    }

    @EventTarget
    public void onRespawn(com.heypixel.heypixelmod.obsoverlay.events.impl.u var1) {
        b.clear();
        c.clear();
    }

    @EventTarget
    public void onMotion(com.heypixel.heypixelmod.obsoverlay.events.impl.k var1) {
        if (var1.b() == com.heypixel.heypixelmod.obsoverlay.events.api.b.a.a && a.level != null) {
            a().forEach((var0, var1x) -> {
                if (System.currentTimeMillis() - var1x.j() > 500L) {
                    a().remove(var0);
                }
            });

            for (AbstractClientPlayer var3 : new ArrayList(a.level.players())) {
                if (var3 != a.player) {
                    if (!b.containsKey(var3)) {
                        b.put(var3, new HashSet<>());
                    }

                    Set var4 = b.get(var3);
                    if ((p.c(var3.getMainHandItem()) || p.c(var3.getOffhandItem())) && !var4.contains("God Axe")) {
                        com.heypixel.heypixelmod.obsoverlay.c.a.a var5 = new com.heypixel.heypixelmod.obsoverlay.c.a.a(
                            com.heypixel.heypixelmod.obsoverlay.c.a.b.c, var3.getName().getString() + " is holding god axe!", 3000L
                        );
                        EixClient.a().j().a(var5);
                        var4.add("God Axe");
                    }

                    if ((p.d(var3.getMainHandItem()) || p.d(var3.getOffhandItem())) && !var4.contains("Enchanted Golden Apple")) {
                        com.heypixel.heypixelmod.obsoverlay.c.a.a var6 = new com.heypixel.heypixelmod.obsoverlay.c.a.a(
                            com.heypixel.heypixelmod.obsoverlay.c.a.b.c, var3.getName().getString() + " is holding enchanted golden apple!", 3000L
                        );
                        EixClient.a().j().a(var6);
                        var4.add("Enchanted Golden Apple");
                    }

                    if ((p.e(var3.getMainHandItem()) || p.e(var3.getOffhandItem())) && !var4.contains("End Crystal")) {
                        com.heypixel.heypixelmod.obsoverlay.c.a.a var7 = new com.heypixel.heypixelmod.obsoverlay.c.a.a(
                            com.heypixel.heypixelmod.obsoverlay.c.a.b.c, var3.getName().getString() + " is holding end crystal!", 3000L
                        );
                        EixClient.a().j().a(var7);
                        var4.add("End Crystal");
                    }

                    if ((p.f(var3.getMainHandItem()) || p.f(var3.getOffhandItem())) && !var4.contains("KB Ball")) {
                        com.heypixel.heypixelmod.obsoverlay.c.a.a var8 = new com.heypixel.heypixelmod.obsoverlay.c.a.a(
                            com.heypixel.heypixelmod.obsoverlay.c.a.b.c, var3.getName().getString() + " is holding KB Ball!", 3000L
                        );
                        EixClient.a().j().a(var8);
                        var4.add("KB Ball");
                    }

                    if ((p.g(var3.getMainHandItem()) || p.g(var3.getOffhandItem())) && !var4.contains("KB Stick")) {
                        com.heypixel.heypixelmod.obsoverlay.c.a.a var9 = new com.heypixel.heypixelmod.obsoverlay.c.a.a(
                            com.heypixel.heypixelmod.obsoverlay.c.a.b.c, var3.getName().getString() + " is holding KB Stick!", 3000L
                        );
                        EixClient.a().j().a(var9);
                        var4.add("KB Stick");
                    }

                    if ((p.h(var3.getMainHandItem()) > 2 || p.h(var3.getOffhandItem()) > 2) && !var4.contains("Punch Bow")) {
                        com.heypixel.heypixelmod.obsoverlay.c.a.a var10 = new com.heypixel.heypixelmod.obsoverlay.c.a.a(
                            com.heypixel.heypixelmod.obsoverlay.c.a.b.c, var3.getName().getString() + " is holding Punch Bow!", 3000L
                        );
                        EixClient.a().j().a(var10);
                        var4.add("Punch Bow");
                    }

                    if ((p.i(var3.getMainHandItem()) > 3 || p.i(var3.getOffhandItem()) > 3) && !var4.contains("Power Bow")) {
                        com.heypixel.heypixelmod.obsoverlay.c.a.a var11 = new com.heypixel.heypixelmod.obsoverlay.c.a.a(
                            com.heypixel.heypixelmod.obsoverlay.c.a.b.c, var3.getName().getString() + " is holding Power Bow!", 3000L
                        );
                        EixClient.a().j().a(var11);
                        var4.add("Power Bow");
                    }
                }
            }
        }
    }
}
