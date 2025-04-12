package io.tsukook.github.cozycafes.systems.dandelion;

import net.minecraft.server.level.ServerLevel;

import java.util.HashMap;

public class DandelionCancerManager {
    private static final HashMap<String, DandelionCancer> cancerHashMap = new HashMap<>();

    public static void createCancerForLevel(ServerLevel level) {
        cancerHashMap.put(level.dimension().location().toString(), new DandelionCancer(level));
    }

    public static void tickCancer(ServerLevel level) {
        cancerHashMap.get(level.dimension().location().toString()).tick();
    }
}
