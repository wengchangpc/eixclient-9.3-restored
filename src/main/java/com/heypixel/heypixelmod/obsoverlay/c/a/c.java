package com.heypixel.heypixelmod.obsoverlay.c.a;

import com.heypixel.heypixelmod.obsoverlay.events.impl.s;
import com.heypixel.heypixelmod.obsoverlay.utils.ag;
import com.heypixel.heypixelmod.obsoverlay.utils.e.SkijaDraw;
import io.github.humbleui.skija.ClipMode;
import io.github.humbleui.skija.Path;
import io.github.humbleui.types.Rect;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class c {
    private final List<a> a = new CopyOnWriteArrayList<>();

    public void a(a var1) {
        if (!this.a.contains(var1)) {
            this.a.add(var1);
        }
    }

    public void a(s var1, com.heypixel.heypixelmod.obsoverlay.d.a.b var2) {
        float var3 = 5.0F;
        Path var4 = new Path();
        var4.addRect(Rect.makeXYWH(var2.n(), var2.o() + 5.0F, var2.p() + 10.0F, var2.q()));
        SkijaDraw.c();
        SkijaDraw.e().clipPath(var4, ClipMode.INTERSECT, true);

        for (a var6 : this.a) {
            float var7 = var6.a();
            var3 += var6.b();
            ag var8 = var6.g();
            ag var9 = var6.h();
            float var10 = (float)(System.currentTimeMillis() - var6.f());
            if (var10 > (float)var6.e()) {
                var8.a = 0.0F;
                var9.a = 0.0F;
                if (var8.b(true)) {
                    this.a.remove(var6);
                }
            } else {
                var8.a = var7;
                var9.a = var3;
            }

            var8.a(true);
            var9.a(true);
            var2.c(Math.max(var7, var2.p()));
            var2.d(var3);
            var6.a(var2.n() - var6.a() + var8.c + 2.0F, var2.o() - var6.b() + var9.c);
        }

        SkijaDraw.d();
    }
}
