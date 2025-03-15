package io.tsukook.github.cozycafes.blocks.entities;

import io.tsukook.github.cozycafes.blocks.CoffeePulper;
import io.tsukook.github.cozycafes.client.instances.CoffeePulperInstance;
import io.tsukook.github.cozycafes.networking.PulperSpinPayload;
import io.tsukook.github.cozycafes.registers.CzCBlockEntityRegistry;
import io.tsukook.github.cozycafes.registers.CzCItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.HashMap;
import java.util.Map;

public class CoffeePulperBlockEntity extends BlockEntity {
    private HashMap<String, Float> effectors = new HashMap<>();
    private float spinSpeed;
    private float pulpProgress;

    @OnlyIn(Dist.CLIENT)
    public CoffeePulperInstance coffeePulperInstance = new CoffeePulperInstance();

    public ItemStack berries = ItemStack.EMPTY;

    public CoffeePulperBlockEntity(BlockPos pos, BlockState blockState) {
        super(CzCBlockEntityRegistry.COFFEE_PULPER_BLOCK_ENTITY.get(), pos, blockState);
    }

    private static double directionToYRot(Direction direction) {
        return switch (direction) {
            case NORTH -> Math.PI/2;
            case EAST -> 0;
            case SOUTH -> Math.PI*3/4; // -90
            case WEST -> Math.PI;
            default -> throw new IllegalArgumentException("No y-Rot for vertical axis: " + direction);
        };
    }

    private int debugTickTracker = 0;
    private int debugLastPulp = 0;
    // TODO: Possibly do something better for decay
    public static void serverTick(Level level, BlockPos pos, BlockState state, CoffeePulperBlockEntity blockEntity) {
        if (level instanceof ServerLevel serverLevel) {
            blockEntity.debugTickTracker += 1;

            float previousSpeed = blockEntity.spinSpeed;
            blockEntity.spinSpeed = 0;
            for (Map.Entry<String, Float> entry : blockEntity.effectors.entrySet()) {
                float spinSpeed = entry.getValue();
                blockEntity.spinSpeed += spinSpeed;

                float newSpinSpeed = spinSpeed * 0.85f;
                if (newSpinSpeed != 0 && newSpinSpeed > 0.001)
                    blockEntity.effectors.put(entry.getKey(), newSpinSpeed);
                else
                    blockEntity.effectors.remove(entry.getKey());
            }

            if (blockEntity.spinSpeed != previousSpeed) {
                PacketDistributor.sendToPlayersTrackingChunk(serverLevel, new ChunkPos(pos), new PulperSpinPayload(pos, blockEntity.spinSpeed));
                blockEntity.setChanged();
            }

            if (!blockEntity.berries.isEmpty()) {
                blockEntity.pulpProgress += blockEntity.spinSpeed / 20f;

                for (int i = 0; i < Math.floor(blockEntity.pulpProgress); i++) {
                    float ticksSinceLastPulp = blockEntity.debugTickTracker - blockEntity.debugLastPulp;
                    serverLevel.getServer().getPlayerList().broadcastSystemMessage(Component.literal("Seconds since last pulp: " + ticksSinceLastPulp / 20 + " (" + 20 / ticksSinceLastPulp + "/s)"), false);
                    blockEntity.debugLastPulp = blockEntity.debugTickTracker;
                    blockEntity.pulpProgress -= 1;
                    blockEntity.berries.shrink(1);
                    double direction = state.getValue(CoffeePulper.FACING).toYRot() / 180 * Math.PI + Math.PI / 2;
                    double speed = blockEntity.spinSpeed * 0.25f;
                    level.addFreshEntity(new ItemEntity(level, pos.getX() + 0.5f + Math.cos(direction) / 2, pos.getY() + 0.25f, pos.getZ() + 0.5f + Math.sin(direction) / 2, new ItemStack(CzCItemRegistry.PULPED_COFFEE_BEAN.get()), Math.cos(direction) * speed, 0, Math.sin(direction) * speed));
                }
                blockEntity.setChanged();
            }
        }
    }

    public boolean consumeBerries(ItemStack stack) {
        if (!stack.is(CzCItemRegistry.COFFEE_BERRY))
            return false;

        int oldCount = berries.getCount();
        if (berries.isEmpty()) {
            berries = stack.copyAndClear();
        } else {
            int count = Math.min(berries.getCount() + stack.getCount(), 64);
            stack.shrink(count - berries.getCount());
            berries.setCount(count);
        }
        setChanged();
        if (oldCount != berries.getCount())
            return true;
        else
            return false;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);

        if (!berries.isEmpty())
            tag.put("Item", berries.save(registries));

        CompoundTag effectorsTag = new CompoundTag();
        for (Map.Entry<String, Float> entry : effectors.entrySet()) {
            effectorsTag.putFloat(entry.getKey(), entry.getValue());
        }
        tag.put("effectors", effectorsTag);
        tag.putFloat("pulp_progress", pulpProgress);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        berries = ItemStack.parse(registries, tag.getCompound("Item")).orElse(ItemStack.EMPTY);

        CompoundTag effectorsTag = tag.getCompound("effectors");
        if (!effectorsTag.isEmpty()) {
            for (String key : effectorsTag.getAllKeys()) {
                effectors.put(key, effectorsTag.getFloat(key));
            }
        }
        pulpProgress = tag.getFloat("pulp_progress");
    }

    public void spin(Player player) {
        effectors.put(player.getStringUUID(), 1f);
    }
}
