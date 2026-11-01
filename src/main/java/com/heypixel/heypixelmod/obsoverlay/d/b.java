package com.heypixel.heypixelmod.obsoverlay.d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class b {
    private final List<NamedHolder> a = new ArrayList<>();
    private final Map<String, NamedHolder> b = new HashMap<>();

    public void a(NamedHolder var1) {
        this.a.add(var1);
        this.b.put(var1.h().toLowerCase(), var1);
    }

    public NamedHolder a(String var1) {
        return this.b.get(var1.toLowerCase());
    }

    public List<NamedHolder> a() {
        return this.a;
    }
}
