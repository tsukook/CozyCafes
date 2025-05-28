package io.tsukook.github.cozycafes.blocks;

import io.tsukook.github.cozycafes.registers.PerLevelTickerManagerRegistry;
import io.tsukook.github.cozycafes.systems.dandelion.DandelionSeed;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

public class DandelionBlock extends Block implements BonemealableBlock {
    public static final EnumProperty<DandelionStage> STAGE = EnumProperty.create("stage", DandelionStage.class);

    private static final VoxelShape SHAPE = Block.box(5, 0, 5, 11, 10, 11);

    public DandelionBlock(Properties p_49795_) {
        super(p_49795_);
        registerDefaultState(getStateDefinition().any().
                setValue(STAGE, DandelionStage.BULB)
        );
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(STAGE);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    private void grow(ServerLevel level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        int ordinal = state.getValue(STAGE).ordinal();
        if (ordinal < DandelionStage.CANCER4.ordinal()) {
            level.setBlockAndUpdate(pos, state.setValue(STAGE, DandelionStage.values()[ordinal + 1]));
        }
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        //level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("Tuck"), false);
        //level.destroyBlock(pos, false);

        if (state.getValue(STAGE) == DandelionStage.CANCER4) {
            for (int i = 0; i < 8; i++) {
                float horizontalRandom = 1;
                float verticalRandom = 30;
                PerLevelTickerManagerRegistry.LEVEL_TICK_SCHEDULER_MANAGER.getTicker(level).schedule(i * 4, serverLevel -> PerLevelTickerManagerRegistry.DANDELION_CANCER_MANAGER.getTicker(level).addSeed(new DandelionSeed(pos.getCenter().toVector3f(), new Vector3f(Mth.nextFloat(random, -horizontalRandom, horizontalRandom), Mth.nextFloat(random, 0, verticalRandom), Mth.nextFloat(random, -horizontalRandom, horizontalRandom)))));
                //level.playSound(null, pos, SoundEvents.GENERIC_EXPLODE.value(), SoundSource.BLOCKS, 10, 1);
            }
            level.setBlockAndUpdate(pos, state.setValue(STAGE, DandelionStage.BULB));
        } else {
            grow(level, pos);
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return state.getValue(STAGE) != DandelionStage.CANCER4;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        grow(level, pos);
    }
}
