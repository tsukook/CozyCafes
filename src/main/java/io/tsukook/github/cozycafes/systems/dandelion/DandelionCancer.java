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
    private boolean ticked = false;
    private final ArrayList<DandelionSeed> seeds = new ArrayList<>(256);

    public DandelionCancer(ServerLevel level) {
        this.level = level;
    }

    public void addSeed(DandelionSeed seed) {
        seeds.add(seed);
    }
    public void clearSeeds() {
        seeds.clear();
    }

    public void tick() {
        // TODO: Send array of seeds per chunk instead of piss stream
        // TODO: Actually just send on creation and destruction and make physics deterministic
        for (DandelionSeed seed : seeds) {
            Vector2i chunk = new Vector2i((int) seed.pos.x, (int) seed.pos.z);
            chunk.div(16);
            if (level.hasChunk(chunk.x, chunk.y)) {
                seed.pos.add(seed.velocity);
                PacketDistributor.sendToPlayersTrackingChunk(level, new ChunkPos(chunk.x, chunk.y), new SynchronizeDandelionSeedPayload(seed.pos, seed.velocity));
            }
        }
        PacketDistributor.sendToPlayersInDimension(level, new FlushDandelionSeedsPayload());
    }
}
