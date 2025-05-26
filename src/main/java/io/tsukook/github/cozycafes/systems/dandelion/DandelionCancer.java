package io.tsukook.github.cozycafes.systems.dandelion;

import io.tsukook.github.cozycafes.networking.DandelionSeedStatePayloadBuilder;
import io.tsukook.github.cozycafes.registers.CzCBlockRegistry;
import io.tsukook.github.cozycafes.systems.PerLevelTicker;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.neoforged.neoforge.network.PacketDistributor;
import org.joml.Vector2i;

import java.util.*;

// FIXME: Name is a placeholder
public class DandelionCancer implements PerLevelTicker {
    private final ServerLevel level;
    private final HashMap<Integer, DandelionSeed> seeds = new HashMap<>(256);
    private final HashMap<ChunkPos, HashSet<Integer>> chunkPosDandelionSeedMap = new HashMap<>(32);

    private boolean isFrozen = false;

    public DandelionCancer(Level level) {
        this.level = (ServerLevel)level;
    }

    public static ChunkPos getSeedChunkPos(DandelionSeed seed) {
        Vector2i chunk = new Vector2i((int) seed.pos.x, (int) seed.pos.z);
        chunk.div(16);
        return new ChunkPos(chunk.x, chunk.y);
    }

    public static BlockPos getSeedBlockPos(DandelionSeed seed) {
        return new BlockPos((int)seed.pos.x, (int)seed.pos.y, (int)seed.pos.z);
    }

    public void addSeed(DandelionSeed seed) {
        seeds.put(seed.getId(), seed);
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
            chunkPosDandelionSeedMap.put(pos, new HashSet<>());
        }
        chunkPosDandelionSeedMap.get(pos).add(seed.getId());
    }

    public void tick(boolean bypassFrozen) {
        ArrayList<Integer> removeQueue = new ArrayList<>();
        seeds.forEach((id, seed) -> {
            ChunkPos chunkPos = getSeedChunkPos(seed);
            if (level.hasChunk(chunkPos.x, chunkPos.z)) {
                if (!isFrozen || bypassFrozen) {
                    if (chunkPosDandelionSeedMap.containsKey(chunkPos)) {
                        chunkPosDandelionSeedMap.get(chunkPos).remove(id);
                    }

                    Vec3 prevSeedPos = new Vec3(seed.pos);
                    DandelionPhysics.tickSeed(seed);
                    BlockHitResult result = level.clip(new ClipContext(prevSeedPos, new Vec3(seed.pos), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, CollisionContext.empty()));
                    if (result.getType() != HitResult.Type.MISS) {
                        removeQueue.add(id);
                        BlockPos blockPos = result.getBlockPos().above();
                        if (level.getBlockState(blockPos).isAir())
                            level.setBlockAndUpdate(blockPos, CzCBlockRegistry.DANDELION.get().defaultBlockState());
                    } else {
                        addToChunkPosDandelionSeedMap(seed);
                    }
                }
            }
        });

        removeQueue.forEach(seeds::remove);
        removeQueue.clear();

        HashMap<ServerPlayer, DandelionSeedStatePayloadBuilder> payloads = new HashMap<>();

        chunkPosDandelionSeedMap.forEach((chunkPos, dandelionSeeds) -> {
            for (ServerPlayer player : level.getChunkSource().chunkMap.getPlayers(chunkPos, false)) {
                if (!payloads.containsKey(player)) {
                    DandelionSeedStatePayloadBuilder builder = new DandelionSeedStatePayloadBuilder();
                    dandelionSeeds.forEach(id -> builder.addSeed(seeds.get(id)));
                    payloads.put(player, builder);
                } else {
                    dandelionSeeds.forEach(id -> payloads.get(player).addSeed(seeds.get(id)));
                }
            }
        });

        chunkPosDandelionSeedMap.entrySet().removeIf(chunkPosHashSetEntry -> chunkPosHashSetEntry.getValue().isEmpty());

        payloads.forEach((player, dandelionSeedStatePayloadBuilder) -> player.connection.send(dandelionSeedStatePayloadBuilder.build()));
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
