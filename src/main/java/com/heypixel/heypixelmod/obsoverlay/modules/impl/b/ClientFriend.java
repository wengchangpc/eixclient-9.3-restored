package com.heypixel.heypixelmod.obsoverlay.modules.impl.b;

import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.a;
import com.heypixel.heypixelmod.obsoverlay.modules.b;
import net.minecraft.world.entity.Entity;

@ModuleInfo(
    a = "ClientFriend",
    b = "客户端朋友",
    c = "Treat other users as friend!",
    d = com.heypixel.heypixelmod.obsoverlay.modules.a.MISC
)
public class ClientFriend extends b {
    public static boolean a(Entity var0) {
        return false;
    }
}
