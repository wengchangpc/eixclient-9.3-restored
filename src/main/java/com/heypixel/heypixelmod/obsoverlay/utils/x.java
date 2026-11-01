package com.heypixel.heypixelmod.obsoverlay.utils;

import com.heypixel.heypixelmod.mixin.O.accessors.ClientLevelAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.prediction.BlockStatePredictionHandler;
import net.minecraft.client.multiplayer.prediction.PredictiveAction;

public class x {
    private static final Minecraft a = Minecraft.getInstance();

    public static void a(PredictiveAction var0) {
        if (a.getConnection() != null && a.level != null) {
            BlockStatePredictionHandler var1 = ((ClientLevelAccessor)a.level).getBlockStatePredictionHandler().startPredicting();

            try {
                int var2 = var1.currentSequence();
                a.getConnection().send(var0.predict(var2));
            } catch (Throwable var5) {
                if (var1 != null) {
                    try {
                        var1.close();
                    } catch (Throwable var4) {
                        var5.addSuppressed(var4);
                    }
                }

                throw var5;
            }

            if (var1 != null) {
                var1.close();
            }
        }
    }
}
