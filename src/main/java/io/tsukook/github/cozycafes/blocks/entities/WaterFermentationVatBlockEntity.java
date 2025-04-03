package io.tsukook.github.cozycafes.blocks.entities;

import io.tsukook.github.cozycafes.registers.CzCBlockEntityRegistry;
import io.tsukook.github.cozycafes.registers.CzCItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class WaterFermentationVatBlockEntity extends BlockEntity {
    private ItemStack beans = ItemStack.EMPTY;
    private static final int MAX_FERMENTATION_TIME_TICKS = 10 * 20;
    private int fermentation = 0;

    public WaterFermentationVatBlockEntity(BlockPos pos, BlockState blockState) {
        super(CzCBlockEntityRegistry.WATER_FERMENTATION_VAT_BLOCK_ENTITY.get(), pos, blockState);
    }

    public int addBeans(int count) {
        if (beans.isEmpty()) {
            beans = new ItemStack(CzCItemRegistry.PULPED_COFFEE_BEAN.get());
            int real_count = Math.min(64, count);
            int remaining = count - real_count;
            beans.setCount(real_count);
            setChanged();
            return remaining;
        }

        int real_count = Math.min(64, count + beans.getCount());
        int remaining = count - real_count;
        beans.grow(count);
        setChanged();
        return remaining;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, WaterFermentationVatBlockEntity blockEntity) {
        if (blockEntity.beans.isEmpty())
            return;
        blockEntity.fermentation += 1;

        if (blockEntity.fermentation >= MAX_FERMENTATION_TIME_TICKS) {
            blockEntity.fermentation = 0;
            blockEntity.beans = new ItemStack(CzCItemRegistry.FERMENTED_COFFEE_BEAN.get(), blockEntity.beans.getCount());
        }
        blockEntity.setChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);

        if (!beans.isEmpty())
            tag.put("Item", beans.save(registries));

        tag.putInt("Fermentation", fermentation);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        beans = ItemStack.parse(registries, tag.getCompound("Item")).orElse(ItemStack.EMPTY);
        fermentation = tag.getInt("Fermentation");
    }
}
