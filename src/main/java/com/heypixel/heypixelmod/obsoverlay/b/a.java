package com.heypixel.heypixelmod.obsoverlay.b;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class a {
    public Map<String, com.heypixel.heypixelmod.obsoverlay.b.a.b> modules = new HashMap<>();
    public List<String> friends = new ArrayList<>();
    public List<String> killSays = new ArrayList<>();
    public List<String> spammerMessages = new ArrayList<>();
    public com.heypixel.heypixelmod.obsoverlay.b.a.c proxy = new com.heypixel.heypixelmod.obsoverlay.b.a.c();
    public com.heypixel.heypixelmod.obsoverlay.b.a.a gui = new com.heypixel.heypixelmod.obsoverlay.b.a.a();

    public static class a {
        public float x = 0.0F;
        public float y = 0.0F;
        public float width = 600.0F;
        public float height = 400.0F;
    }

    public static class b {
        public int key;
        public boolean enabled;
        public Map<String, Object> values = new HashMap<>();
    }

    public static class c {
        public String host;
        public int port;
    }
}
