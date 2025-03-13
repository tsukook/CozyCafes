package io.tsukook.github.cozycafes.blocks.entities;

import io.tsukook.github.cozycafes.registers.BlockEntityRegistry;
import io.tsukook.github.cozycafes.registers.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;

public class CoffeePulperBlockEntity extends BlockEntity {
    private HashMap<String, Float> effectors = new HashMap<>();
    private float spinSpeed;

    public ItemStack berries = ItemStack.EMPTY;

    public CoffeePulperBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntityRegistry.COFFEE_PULPER_BLOCK_ENTITY.get(), pos, blockState);
    }

    // TODO: Possibly do something better for decay
    public static void serverTick(Level level, BlockPos pos, BlockState state, CoffeePulperBlockEntity blockEntity) {
        blockEntity.spinSpeed = 0;
        for (Map.Entry<String, Float> entry : blockEntity.effectors.entrySet()) {
            float spinSpeed = entry.getValue();
            blockEntity.spinSpeed += spinSpeed;

            float newSpinSpeed = spinSpeed * 0.95f;
            if (newSpinSpeed != 0)
                blockEntity.effectors.put(entry.getKey(), newSpinSpeed);
            else
                blockEntity.effectors.remove(entry.getKey());
        }
    }

    public void consumeBerries(ItemStack stack) {
        if (!stack.is(ItemRegistry.COFFEE_BERRY))
            return;

        if (berries.isEmpty()) {
            berries = stack.copyAndClear();
        } else {
            berries.setCount(berries.getCount() + stack.getCount());
            stack.setCount(0);
        }
        setChanged();
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
    }

    public void spin(Player player) {
        effectors.put(player.getStringUUID(), 1f);
    }
}
