package io.tsukook.github.cozycafes.systems.dandelion;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import org.joml.Vector2i;

import java.util.*;

// FIXME: Name is a placeholder
public class DandelionCancer {
    private final ServerLevel level;
    private final ArrayList<DandelionSeed> seeds = new ArrayList<>(256);

    private boolean isFrozen = false;

    public DandelionCancer(ServerLevel level) {
        this.level = level;
    }

    public static ChunkPos getSeedChunkPos(DandelionSeed seed) {
        Vector2i chunk = new Vector2i((int) seed.pos.x, (int) seed.pos.z);
        chunk.div(16);
        return new ChunkPos(chunk.x, chunk.y);
    }

    public void addSeed(DandelionSeed seed) {
        seeds.add(seed);
    }
    public int clearSeeds() {
        int count = seeds.size();
        seeds.clear();
        return count;
    }

    public void tick() {
        tick(false);
    }

    public void tick(boolean bypassFrozen) {
        seeds.forEach(seed -> {
            ChunkPos chunkPos = getSeedChunkPos(seed);
            if (level.hasChunk(chunkPos.x, chunkPos.z)) {
                if (!isFrozen || bypassFrozen)
                    DandelionPhysics.tickSeed(seed);
            }
        });
    }

    public void setFrozen(boolean isFrozen) {
        this.isFrozen = isFrozen;
    }

    public int countSeeds() {
        return seeds.size();
    }

    public boolean getIsFrozen() {
        return isFrozen;
    }
}
