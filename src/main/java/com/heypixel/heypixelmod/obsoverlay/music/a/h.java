package com.heypixel.heypixelmod.obsoverlay.music.a;

import java.util.List;

public final class h {
    private final h.a kind;
    private final long id;
    private final String name;
    private final String cover;
    private final String creator;
    private final String description;
    private final int songCount;
    private final List<i> songs;

    public h(h.a var1, long var2, String var4, String var5, String var6, String var7, int var8, List<i> var9) {
        this.kind = var1;
        this.id = var2;
        this.name = var4 == null ? "" : var4;
        this.cover = var5 == null ? "" : var5;
        this.creator = var6 == null ? "" : var6;
        this.description = var7 == null ? "" : var7;
        this.songCount = var8;
        this.songs = var9 == null ? List.of() : List.copyOf(var9);
    }

    public h.a a() {
        return this.kind;
    }

    public long b() {
        return this.id;
    }

    public String c() {
        return this.name;
    }

    public String d() {
        return this.cover;
    }

    public String e() {
        return this.creator;
    }

    public String f() {
        return this.description;
    }

    public int g() {
        return this.songCount;
    }

    public List<i> h() {
        return this.songs;
    }

    public static enum a {
        PLAYLIST("歌单"),
        ALBUM("专辑"),
        TOPLIST("榜单");

        private final String cnName;

        private a(String var3) {
            this.cnName = var3;
        }

        public String a() {
            return this.cnName;
        }
    }
}
