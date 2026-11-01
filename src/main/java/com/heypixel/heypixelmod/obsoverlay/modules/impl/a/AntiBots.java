package com.heypixel.heypixelmod.obsoverlay.modules.impl.a;

import com.heypixel.heypixelmod.obsoverlay.EixClient;
import com.heypixel.heypixelmod.obsoverlay.d.d;
import com.heypixel.heypixelmod.obsoverlay.d.a.c;
import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.k;
import com.heypixel.heypixelmod.obsoverlay.events.impl.m;
import com.heypixel.heypixelmod.obsoverlay.events.impl.u;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.a;
import com.heypixel.heypixelmod.obsoverlay.modules.b;
import com.mojang.authlib.GameProfile;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.network.protocol.game.ClientboundAddPlayerPacket;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket.Action;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket.Entry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.GameType;

@ModuleInfo(
    a = "AntiBots",
    b = "反人机",
    d = com.heypixel.heypixelmod.obsoverlay.modules.a.COMBAT,
    c = "Prevents bots from attacking you"
)
public class AntiBots extends b {
    private static final Map<UUID, String> c = new ConcurrentHashMap<>();
    private static final Map<Integer, String> d = new ConcurrentHashMap<>();
    private static final Map<UUID, Long> e = new ConcurrentHashMap<>();
    private static final Set<Integer> f = new HashSet<>();
    private static final Map<UUID, Long> g = new ConcurrentHashMap<>();
    private final c h = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Respawn Time").a(2500.0F).d(100.0F).b(0.0F).c(10000.0F).a().c();

    public static boolean a(Entity var0) {
        AntiBots var1 = EixClient.a().g().a(AntiBots.class);
        return var1.h.q() < 1.0F ? false : g.containsKey(var0.getUUID()) && (float)(System.currentTimeMillis() - g.get(var0.getUUID())) < var1.h.q();
    }

    public static boolean b(Entity var0) {
        return (f.contains(var0.getId()) || !a.getConnection().getOnlinePlayerIds().contains(var0.getUUID())) && EixClient.a().g().a(AntiBots.class).m();
    }

    @EventTarget
    public void bedWarsBot(m var1) {
        if (var1.b() == com.heypixel.heypixelmod.obsoverlay.events.api.b.a.d && a.level != null) {
            if (var1.c() instanceof ClientboundPlayerInfoUpdatePacket var2) {
                if (var2.actions().contains(Action.ADD_PLAYER)) {
                    for (Entry var5 : var2.entries()) {
                        GameProfile var6 = var5.profile();
                        UUID var7 = var6.getId();
                        g.put(var7, System.currentTimeMillis());
                    }
                }
            } else if (var1.c() instanceof ClientboundAnimatePacket var3) {
                Entity var10 = a.level.getEntity(var3.getId());
                if (var10 != null && var3.getAction() == 0) {
                    g.remove(var10.getUUID());
                }
            }
        }
    }

    @EventTarget
    public void onRespawn(u var1) {
        c.clear();
        d.clear();
        f.clear();
        e.clear();
    }

    @EventTarget
    public void onMotion(k var1) {
        if (var1.b() == com.heypixel.heypixelmod.obsoverlay.events.api.b.a.a) {
            for (java.util.Map.Entry var3 : e.entrySet()) {
                if (System.currentTimeMillis() - (Long)var3.getValue() > 500L) {
                    e.remove(var3.getKey());
                }
            }
        }
    }

    @EventTarget
    public void onPacket(m var1) {
        if (var1.b() == com.heypixel.heypixelmod.obsoverlay.events.api.b.a.d) {
            if (var1.c() instanceof ClientboundPlayerInfoUpdatePacket var2) {
                if (var2.actions().contains(Action.ADD_PLAYER)) {
                    for (Entry var6 : var2.entries()) {
                        if (var6.displayName() != null && var6.displayName().getSiblings().isEmpty() && var6.gameMode() == GameType.SURVIVAL) {
                            UUID var7 = var6.profile().getId();
                            e.put(var7, System.currentTimeMillis());
                            c.put(var7, var6.displayName().getString());
                        }
                    }
                }
            } else if (var1.c() instanceof ClientboundAddPlayerPacket var3) {
                if (e.containsKey(var3.getPlayerId())) {
                    String var10 = c.get(var3.getPlayerId());
                    d.put(var3.getEntityId(), var10);
                    e.remove(var3.getPlayerId());
                    f.add(var3.getEntityId());
                }
            } else if (var1.c() instanceof ClientboundRemoveEntitiesPacket var4) {
                IntListIterator var12 = var4.getEntityIds().iterator();

                while (var12.hasNext()) {
                    Integer var13 = var12.next();
                    if (f.contains(var13)) {
                        String var14 = d.get(var13);
                        f.remove(var13);
                    }
                }
            }
        }
    }
}
