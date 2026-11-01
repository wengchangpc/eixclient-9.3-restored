package com.heypixel.heypixelmod.obsoverlay.music.lyric;

import java.util.Collections;
import java.util.List;

public final class c {
    public static final c EMPTY = new c(Collections.emptyList(), "", "");
    private final List<a> lines;
    private final String songName;
    private final String artistName;

    public c(List<a> var1, String var2, String var3) {
        this.lines = var1 == null ? Collections.emptyList() : var1;
        this.songName = var2 == null ? "" : var2;
        this.artistName = var3 == null ? "" : var3;
    }

    public List<a> a() {
        return this.lines;
    }

    public String b() {
        return this.songName;
    }

    public String c() {
        return this.artistName;
    }

    public boolean d() {
        return this.lines.isEmpty();
    }

    public int e() {
        return this.lines.size();
    }

    public int a(long var1) {
        if (this.lines.isEmpty()) {
            return -1;
        } else {
            int var3 = 0;
            int var4 = this.lines.size() - 1;
            int var5 = -1;

            while (var3 <= var4) {
                int var6 = var3 + var4 >>> 1;
                if (this.lines.get(var6).a() <= var1) {
                    var5 = var6;
                    var3 = var6 + 1;
                } else {
                    var4 = var6 - 1;
                }
            }

            return var5;
        }
    }

    public a a(int var1) {
        return var1 >= 0 && var1 < this.lines.size() ? this.lines.get(var1) : null;
    }

    public a b(int var1) {
        for (int var2 = Math.max(0, var1); var2 < this.lines.size(); var2++) {
            a var3 = this.lines.get(var2);
            if (!var3.e()) {
                return var3;
            }
        }

        return null;
    }

    public a c(int var1) {
        for (int var2 = Math.min(var1, this.lines.size() - 1); var2 >= 0; var2--) {
            a var3 = this.lines.get(var2);
            if (!var3.e()) {
                return var3;
            }
        }

        return null;
    }
}
