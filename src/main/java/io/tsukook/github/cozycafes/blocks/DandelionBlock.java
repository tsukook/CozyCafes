package io.tsukook.github.cozycafes.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DandelionBlock extends Block {
    private static final VoxelShape SHAPE = Block.box(5, 0, 5, 11, 10, 11);

    public DandelionBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}
