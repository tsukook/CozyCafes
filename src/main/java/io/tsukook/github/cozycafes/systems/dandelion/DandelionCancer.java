package io.tsukook.github.cozycafes.systems.dandelion;

import io.tsukook.github.cozycafes.networking.FlushDandelionSeedsPayload;
import io.tsukook.github.cozycafes.networking.SynchronizeDandelionSeedPayload;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.neoforged.neoforge.network.PacketDistributor;
import org.joml.RoundingMode;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;

import java.util.*;

// FIXME: Name is a placeholder
public class DandelionCancer {
    private final ServerLevel level;
    private final ArrayList<DandelionSeed> seeds = new ArrayList<>(256);
    private final Set<UUID> playerSyncQueue = new HashSet<>(32);

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

        seeds.forEach(seed -> {
            //PacketDistributor.sendToPlayersTrackingChunk(level, getSeedChunkPos(seed), new SynchronizeDandelionSeedPayload(seed.getId(), seed.pos, seed.velocity));
            level.getChunkSource().chunkMap.getPlayers(getSeedChunkPos(seed), false).forEach(player -> {
                if (playerSyncQueue.contains(player.getUUID())) {
                    player.connection.send(new ClientboundCustomPayloadPacket(new SynchronizeDandelionSeedPayload(seed.getId(), seed.pos, seed.velocity)));
                    playerSyncQueue.remove(player.getUUID());
                }
            });
        });

        PacketDistributor.sendToPlayersInDimension(level, new FlushDandelionSeedsPayload());
    }

    public void queuePlayerForSync(Player player) {
        queuePlayerForSync(player.getUUID());
    }

    public void queuePlayerForSync(UUID uuid) {
        playerSyncQueue.add(uuid);
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
