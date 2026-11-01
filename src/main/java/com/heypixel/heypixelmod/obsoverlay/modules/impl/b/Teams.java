package com.heypixel.heypixelmod.obsoverlay.modules.impl.b;

import com.heypixel.heypixelmod.obsoverlay.EixClient;
import com.heypixel.heypixelmod.obsoverlay.d.d;
import com.heypixel.heypixelmod.obsoverlay.d.a.e;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.modules.a;
import com.heypixel.heypixelmod.obsoverlay.modules.b;
import java.util.Objects;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

@ModuleInfo(
    a = "Teams",
    b = "队伍",
    c = "Prevent attack teammates",
    d = com.heypixel.heypixelmod.obsoverlay.modules.a.MISC
)
public class Teams extends b {
    public static Teams c;
    public e d = com.heypixel.heypixelmod.obsoverlay.d.d.a(this, "Mode").a(0).a("Scoreboard", "Color").a().e();

    public Teams() {
        c = this;
    }

    public static boolean a(Entity var0) {
        if (!EixClient.a().g().a(Teams.class).m()) {
            return false;
        } else if (var0 instanceof Player) {
            if (c.d.a("Color")) {
                Integer var3 = var0.getTeamColor();
                Integer var4 = a.player.getTeamColor();
                return var3.equals(var4);
            } else {
                String var1 = b(var0);
                String var2 = b(a.player);
                return Objects.equals(var1, var2);
            }
        } else {
            return false;
        }
    }

    public static String b(Entity var0) {
        PlayerInfo var1 = a.getConnection().getPlayerInfo(var0.getUUID());
        if (var1 == null) {
            return null;
        } else {
            return var1.getTeam() != null ? var1.getTeam().getName() : null;
        }
    }
}
