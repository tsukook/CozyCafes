package io.tsukook.github.cozycafes.blocks.entities;

import io.tsukook.github.cozycafes.registers.CzCBlockEntityRegistry;
import io.tsukook.github.cozycafes.registers.CzCItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class WaterFermentationVatBlockEntity extends BlockEntity {
    private ItemStack beans = ItemStack.EMPTY;

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


}
