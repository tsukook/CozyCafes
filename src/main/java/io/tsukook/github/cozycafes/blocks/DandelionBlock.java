package io.tsukook.github.cozycafes.blocks;

import io.tsukook.github.cozycafes.registers.PerLevelTickerManagerRegistry;
import io.tsukook.github.cozycafes.systems.dandelion.DandelionCancer;
import io.tsukook.github.cozycafes.systems.dandelion.DandelionSeed;
import io.tsukook.github.cozycafes.systems.leveltickscheduler.LevelTickScheduler;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.text.NumberFormat;

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

    private boolean blowIfCancer4(BlockState state, BlockPos pos, RandomSource random, ServerLevel level) {
        if (state.getValue(STAGE) != DandelionStage.CANCER4)
            return false;
        DandelionCancer cancer = PerLevelTickerManagerRegistry.DANDELION_CANCER_MANAGER.getTicker(level);
        LevelTickScheduler scheduler = PerLevelTickerManagerRegistry.LEVEL_TICK_SCHEDULER_MANAGER.getTicker(level);
        // MutableBlockPos can change before it reaches the scheduled tick. This has caused me incomprehensible suffering
        Vector3f center = new Vector3f(pos.getX(), pos.getY(), pos.getZ()).add(0.5f, 0.5f, 0.5f);
        for (int i = 0; i < 8; i++) {
            float horizontalRandom = 1;
            float verticalRandom = 15;
            scheduler.schedule(i * 4, serverLevel -> {
                DandelionSeed seed = new DandelionSeed(center,
                        new Vector3f(
                                Mth.nextFloat(random, -horizontalRandom, horizontalRandom),
                                Mth.nextFloat(random, 0, verticalRandom),
                                Mth.nextFloat(random, -horizontalRandom, horizontalRandom)
                        )
                );
                seed.boostTicks = Mth.nextInt(random, 10, 60);
                cancer.addSeed(seed);
            });
            //level.playSound(null, pos, SoundEvents.GENERIC_EXPLODE.value(), SoundSource.BLOCKS, 10, 1);
        }
        level.setBlockAndUpdate(pos, state.setValue(STAGE, DandelionStage.BULB));
        return true;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        //level.destroyBlock(pos, false);

        DandelionCancer cancer = PerLevelTickerManagerRegistry.DANDELION_CANCER_MANAGER.getTicker(level);
        if (cancer.getWindForce().length() > 0.06) {
            if (!blowIfCancer4(state, pos, random, level))
                grow(level, pos);
        } else {
            grow(level, pos);
        }
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level instanceof ServerLevel serverLevel)
            return blowIfCancer4(state, pos, level.random, serverLevel) ? InteractionResult.SUCCESS : InteractionResult.FAIL;
        return state.getValue(STAGE) == DandelionStage.CANCER4 ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (level instanceof ServerLevel serverLevel)
            blowIfCancer4(state, pos, serverLevel.random, serverLevel);
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
