package io.tsukook.github.cozycafes.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.Nullable;

public class CoffeePlant extends Block implements BonemealableBlock {
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 6);
    public static final int MAX_AGE = 6;
    public static final EnumProperty<TripleTallBlock> SEGMENT = EnumProperty.create("segment", TripleTallBlock.class);

    public CoffeePlant(Properties properties) {
        super(properties);

        registerDefaultState(getStateDefinition().any()
                .setValue(AGE, 0)
                .setValue(SEGMENT, TripleTallBlock.BOTTOM)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
        builder.add(SEGMENT);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState();
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return level.getBlockState(pos.above()).isAir();
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("Bonemealsd"), false);
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return state.getValue(AGE) != MAX_AGE;
    }

    protected void grow(BlockState state, ServerLevel level, BlockPos pos) {
        int age = state.getValue(AGE) + 1;

        level.setBlock(pos, state.setValue(AGE, age), 1 | 2);

        if (age > 1) {
            level.setBlock(pos.above(), defaultBlockState().setValue(AGE, age).setValue(SEGMENT, TripleTallBlock.MIDDLE), 1 | 2);
            if (age > 3) {
                level.setBlock(pos.above(2), defaultBlockState().setValue(AGE, age).setValue(SEGMENT, TripleTallBlock.TOP), 1 | 2);
            }
        }
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(SEGMENT) != TripleTallBlock.BOTTOM)
            return;
        grow(state, level, pos);
    }
}
