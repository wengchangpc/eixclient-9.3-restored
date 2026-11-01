package com.heypixel.heypixelmod.mixin.O;

import com.heypixel.heypixelmod.obsoverlay.EixClient;
import com.heypixel.heypixelmod.obsoverlay.events.api.a.b;
import com.heypixel.heypixelmod.obsoverlay.events.impl.l;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.KeyboardInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({KeyboardInput.class})
public class MixinKeyboardInput extends Input {
    @Inject(
        at = {@At("TAIL")},
        method = {"tick"}
    )
    private void onTickTail(boolean var1, float var2, CallbackInfo var3) {
        this.forwardImpulse = this.up == this.down ? 0.0F : (this.up ? 1.0F : -1.0F);
        this.leftImpulse = this.left == this.right ? 0.0F : (this.left ? 1.0F : -1.0F);
        l var4 = new l(this.forwardImpulse, this.leftImpulse, this.jumping, this.shiftKeyDown, 0.3);
        EixClient.a().b().a((b)var4);
        double var5 = var4.e();
        this.forwardImpulse = var4.a();
        this.leftImpulse = var4.b();
        this.jumping = var4.c();
        this.shiftKeyDown = var4.d();
        if (var1) {
            this.leftImpulse = (float)(this.leftImpulse * var5);
            this.forwardImpulse = (float)(this.forwardImpulse * var5);
        }
    }
}
