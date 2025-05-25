package io.tsukook.github.cozycafes.networking;

import io.tsukook.github.cozycafes.systems.dandelion.DandelionSeed;

import java.util.Collection;
import java.util.HashMap;

public class DandelionSeedStatePayloadBuilder {
    private HashMap<Integer, DandelionSeed> seeds = new HashMap<>();
    private long timestamp = 0;

    public DandelionSeedStatePayloadBuilder addSeeds(Collection<DandelionSeed> seeds) {
        seeds.forEach(seed -> {
            this.seeds.put(seed.getId(), seed);
        });
        return this;
    }

    public DandelionSeedStatePayload build() {
        if (timestamp == 0) {
            timestamp = System.currentTimeMillis();
        }

        return new DandelionSeedStatePayload(timestamp, seeds);
    }
}
