package io.tsukook.github.cozycafes.blocks.entities;

import io.tsukook.github.cozycafes.registers.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.world.level.Level;

public class CoffeePulperBlockEntity extends BlockEntity {
    @OnlyIn(Dist.CLIENT)
    public int n_tick = 0;

    public CoffeePulperBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntityRegistry.COFFEE_PULPER_BLOCK_ENTITY.get(), pos, blockState);
    }

    @OnlyIn(Dist.CLIENT)
    public static void clientTick(Level level, BlockPos pos, BlockState state, CoffeePulperBlockEntity blockEntity) {
        blockEntity.n_tick += 1;
    }
}
