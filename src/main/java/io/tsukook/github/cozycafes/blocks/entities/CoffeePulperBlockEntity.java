package io.tsukook.github.cozycafes.blocks.entities;

import io.tsukook.github.cozycafes.registers.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CoffeePulperBlockEntity extends BlockEntity {
    public CoffeePulperBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntityRegistry.COFFEE_PULPER_BLOCK_ENTITY.get(), pos, blockState);
    }
}
