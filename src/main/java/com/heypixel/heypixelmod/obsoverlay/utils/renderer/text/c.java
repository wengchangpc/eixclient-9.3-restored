package com.heypixel.heypixelmod.obsoverlay.utils.renderer.text;

import com.heypixel.heypixelmod.obsoverlay.utils.renderer.f;
import com.heypixel.heypixelmod.obsoverlay.utils.renderer.g;
import com.heypixel.heypixelmod.obsoverlay.utils.renderer.j;
import com.heypixel.heypixelmod.obsoverlay.utils.renderer.k;
import com.mojang.blaze3d.vertex.PoseStack;
import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.BufferUtils;

public class c {
    private static final Logger a = LogManager.getLogger(c.class);
    private static final Color b = new Color(60, 60, 60, 180);
    private final g c = new j(k.a, com.heypixel.heypixelmod.obsoverlay.utils.renderer.c.b, g.a.b, g.a.b, g.a.d);
    private final d d;

    public c(String var1, int var2, int var3, int var4, int var5) {
        InputStream var6 = this.getClass().getResourceAsStream("/assets/heypixel/VcX6svVqmeT8/fonts/" + var1 + ".ttf");
        if (var6 == null) {
            throw new RuntimeException("Font not found: " + var1);
        } else {
            byte[] var7;
            try {
                ByteArrayOutputStream var8 = new ByteArrayOutputStream();
                byte[] var9 = new byte[1024];

                int var10;
                while ((var10 = var6.read(var9)) != -1) {
                    var8.write(var9, 0, var10);
                }

                var7 = var8.toByteArray();
            } catch (IOException var11) {
                throw new RuntimeException("Failed to read font: " + var1, var11);
            }

            ByteBuffer var12 = BufferUtils.createByteBuffer(var7.length).put(var7);
            ((Buffer)var12).flip();
            long var13 = System.currentTimeMillis();
            this.d = new d(var12, var2, var3, var4, var5);
            a.info("Loaded font {} in {}ms", var1, System.currentTimeMillis() - var13);
        }
    }

    public void a(float var1) {
        this.c.b = var1;
    }

    public float a(String var1, double var2) {
        return (float)this.a(var1, false, var2);
    }

    public double a(String var1, boolean var2, double var3) {
        return (this.d.a(var1) + (var2 ? 0.5F : 0.0F)) * var3;
    }

    public double a(boolean var1, double var2) {
        return (this.d.a() + (var1 ? 0.5F : 0.0F)) * var2;
    }

    public double a(PoseStack var1, String var2, double var3, double var5, Color var7, boolean var8, double var9) {
        this.c.a();
        double var11;
        if (var8) {
            var11 = this.d.a(this.c, var2, var3 + 0.5, var5 + 0.5, b, var9, true);
            this.d.a(this.c, var2, var3, var5, var7, var9, false);
        } else {
            var11 = this.d.a(this.c, var2, var3, var5, var7, var9, false);
        }

        this.c.d();
        f.q(this.d.a.getId());
        this.c.b(var1);
        return var11;
    }
}
