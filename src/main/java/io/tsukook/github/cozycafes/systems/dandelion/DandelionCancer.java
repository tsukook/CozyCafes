package io.tsukook.github.cozycafes.systems.dandelion;

import io.tsukook.github.cozycafes.networking.DandelionSeedStatePayload;
import io.tsukook.github.cozycafes.networking.DandelionSeedStatePayloadBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.neoforged.neoforge.network.PacketDistributor;
import org.joml.Vector2i;

import java.util.*;

// FIXME: Name is a placeholder
public class DandelionCancer {
    private final ServerLevel level;
    private final ArrayList<DandelionSeed> seeds = new ArrayList<>(256);
    private final HashMap<ChunkPos, ArrayList<DandelionSeed>> chunkPosDandelionSeedMap = new HashMap<>(32);

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
        chunkPosDandelionSeedMap.clear();
        PacketDistributor.sendToPlayersInDimension(level, new DandelionSeedStatePayloadBuilder().build());
        return count;
    }

    public void tick() {
        tick(false);
    }

    private void addToChunkPosDandelionSeedMap(DandelionSeed seed) {
        ChunkPos pos = getSeedChunkPos(seed);
        if (!chunkPosDandelionSeedMap.containsKey(pos)) {
            chunkPosDandelionSeedMap.put(pos, new ArrayList<>());
        }
        chunkPosDandelionSeedMap.get(pos).add(seed);
    }

    public void tick(boolean bypassFrozen) {
        seeds.forEach(seed -> {
            ChunkPos chunkPos = getSeedChunkPos(seed);
            if (level.hasChunk(chunkPos.x, chunkPos.z)) {
                if (!isFrozen || bypassFrozen) {
                    DandelionPhysics.tickSeed(seed);
                    addToChunkPosDandelionSeedMap(seed);
                }
            }
        });

        HashMap<ServerPlayer, DandelionSeedStatePayloadBuilder> payloads = new HashMap<>();

        chunkPosDandelionSeedMap.forEach((chunkPos, dandelionSeeds) -> {
            for (ServerPlayer player : level.getChunkSource().chunkMap.getPlayers(chunkPos, false)) {
                if (!payloads.containsKey(player)) {
                    payloads.put(player, new DandelionSeedStatePayloadBuilder().addSeeds(dandelionSeeds));
                } else {
                    payloads.get(player).addSeeds(dandelionSeeds);
                }
            }
        });

        payloads.forEach((player, dandelionSeedStatePayloadBuilder) -> {
            player.connection.send(dandelionSeedStatePayloadBuilder.build());
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
