package io.github.tsukook.cozycafes.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class DrinkableBlockEntity extends FluidContainerBlockEntity {
    public DrinkableBlockEntity(BlockEntityType<?> type, BlockPos pPos, BlockState pBlockState, int capacity) {
        super(type, pPos, pBlockState, capacity);
    }
}
