package io.tsukook.github.cozycafes.blocks.entities;

import io.tsukook.github.cozycafes.registers.CzCBlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class WaterFermentationVatBlockEntity extends BlockEntity {
    public WaterFermentationVatBlockEntity(BlockPos pos, BlockState blockState) {
        super(CzCBlockEntityRegistry.WATER_FERMENTATION_VAT_BLOCK_ENTITY.get(), pos, blockState);
    }
}
