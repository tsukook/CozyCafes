package io.tsukook.github.cozycafes.systems.dandelion;

import net.minecraft.server.level.ServerLevel;

import java.util.ArrayList;

// FIXME: Name is a placeholder
public class DandelionCancer {
    private final ServerLevel level;
    private boolean ticked = false;
    private final ArrayList<DandelionSeed> seeds = new ArrayList<>();

    public DandelionCancer(ServerLevel level) {
        this.level = level;
    }

    public void addSeed(DandelionSeed seed) {
        seeds.add(seed);
    }

    public void tick() {
        for (DandelionSeed seed : seeds) {
            seed.pos.add(seed.velocity);
        }
    }
}
