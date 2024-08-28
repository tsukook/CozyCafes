package io.github.tsukook.cozycafes.block;

import io.github.tsukook.cozycafes.CCBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class FluidContainerBlock extends Block implements EntityBlock {

    public FluidContainerBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return CCBlockEntities.TEST_CONTAINER.create(blockPos, blockState);
    }
}
