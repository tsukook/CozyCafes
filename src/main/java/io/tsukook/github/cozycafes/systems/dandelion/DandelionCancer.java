package io.tsukook.github.cozycafes.systems.dandelion;

import io.tsukook.github.cozycafes.networking.FlushDandelionSeedsPayload;
import io.tsukook.github.cozycafes.networking.SynchronizeDandelionSeedPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.neoforged.neoforge.network.PacketDistributor;
import org.joml.RoundingMode;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;

import java.util.ArrayList;

// FIXME: Name is a placeholder
public class DandelionCancer {
    private final ServerLevel level;
    private final ArrayList<DandelionSeed> seeds = new ArrayList<>(256);

    private boolean isFrozen = false;

    public DandelionCancer(ServerLevel level) {
        this.level = level;
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
        for (DandelionSeed seed : seeds) {
            Vector2i chunk = new Vector2i((int) seed.pos.x, (int) seed.pos.z);
            chunk.div(16);
            if (level.hasChunk(chunk.x, chunk.y)) {
                if (!isFrozen || bypassFrozen)
                    DandelionPhysics.tickSeed(seed);
                PacketDistributor.sendToPlayersTrackingChunk(level, new ChunkPos(chunk.x, chunk.y), new SynchronizeDandelionSeedPayload(seed.getId(), seed.pos, seed.velocity));
            }
        }
        PacketDistributor.sendToPlayersInDimension(level, new FlushDandelionSeedsPayload());
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
