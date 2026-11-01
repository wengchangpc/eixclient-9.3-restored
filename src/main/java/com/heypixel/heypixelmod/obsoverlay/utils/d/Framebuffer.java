package com.heypixel.heypixelmod.obsoverlay.utils.d;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;

public class Framebuffer {
    public int a;
    public double b = 1.0;
    public int c;
    public int d;
    private int e;

    public Framebuffer(double var1) {
        this.b = var1;
        this.e();
    }

    public Framebuffer() {
        this.e();
    }

    private void e() {
        Window var1 = Minecraft.getInstance().getWindow();
        this.e = GlStateManager.glGenFramebuffers();
        this.a();
        this.a = GlStateManager._genTexture();
        com.heypixel.heypixelmod.obsoverlay.utils.d.d.e(this.a);
        com.heypixel.heypixelmod.obsoverlay.utils.d.d.a();
        com.heypixel.heypixelmod.obsoverlay.utils.d.d.b(3553, 10242, 33071);
        com.heypixel.heypixelmod.obsoverlay.utils.d.d.b(3553, 10243, 33071);
        com.heypixel.heypixelmod.obsoverlay.utils.d.d.b(3553, 10241, 9729);
        com.heypixel.heypixelmod.obsoverlay.utils.d.d.b(3553, 10240, 9729);
        this.c = (int)(var1.getWidth() * this.b);
        this.d = (int)(var1.getHeight() * this.b);
        com.heypixel.heypixelmod.obsoverlay.utils.d.d.a(3553, 0, 6407, this.c, this.d, 0, 6407, 5121, null);
        com.heypixel.heypixelmod.obsoverlay.utils.d.d.a(36160, 36064, 3553, this.a, 0);
        this.c();
    }

    public void a() {
        GlStateManager._glBindFramebuffer(36160, this.e);
    }

    public void b() {
        com.heypixel.heypixelmod.obsoverlay.utils.d.d.a(0, 0, this.c, this.d);
    }

    public void c() {
        Minecraft.getInstance().getMainRenderTarget().bindWrite(false);
    }

    public void d() {
        GlStateManager._glDeleteFramebuffers(this.e);
        GlStateManager._deleteTexture(this.a);
        this.e();
    }
}
