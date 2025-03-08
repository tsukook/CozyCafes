package io.tsukook.github.cozycafes.blocks;

import io.tsukook.github.cozycafes.registers.BlockRegistry;
import io.tsukook.github.cozycafes.registers.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.common.util.TriState;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;

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

    protected boolean canGrowAtBlock(LevelReader level, BlockPos pos) {
        return level.getBlockState(pos).isAir() || level.getBlockState(pos).is(this);
    }

    protected void updatePlant(BlockState state, ServerLevel level, BlockPos pos, int age) {
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

        int age = state.getValue(AGE);
        if (age == 1) {
            if (canGrowAtBlock(level, pos.above()))
                age++;
        }
        else if (age == 3) {
            if (canGrowAtBlock(level, pos.above(2)))
                age++;
        } else
            age++;
        updatePlant(state, level, pos, age);
    }

    protected void forAllBlocksInPlant(LevelAccessor level, BlockPos pos, Consumer<BlockPos> action) {forAllBlocksInPlant(level, pos, action,false);}

    protected void forAllBlocksInPlant(LevelAccessor level, BlockPos pos, Consumer<BlockPos> action, boolean skipOriginal) {
        BlockPos bottomBlockPos = pos;
        while (level.getBlockState(bottomBlockPos.below()).is(this)) {
            bottomBlockPos = bottomBlockPos.below();
        }

        BlockPos nextBlockPos = bottomBlockPos;
        while (level.getBlockState(nextBlockPos).is(this) || nextBlockPos.equals(pos)) {
            if (nextBlockPos != pos || !skipOriginal) {
                action.accept(nextBlockPos);
            }
            nextBlockPos = nextBlockPos.above();
        }
    }

    @Override
    public void destroy(LevelAccessor level, BlockPos pos, BlockState state) {
        forAllBlocksInPlant(level, pos, pos1 -> level.destroyBlock(pos1, true), true);
    }



    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        TripleTallBlock segment = state.getValue(SEGMENT);
        if (segment == TripleTallBlock.BOTTOM) {
            BlockPos belowBlockPos = pos.below();
            BlockState belowBlockState = level.getBlockState(belowBlockPos);
            TriState soilDecision = belowBlockState.canSustainPlant(level, belowBlockPos, Direction.UP, state);
            if (!soilDecision.isDefault()) return soilDecision.isTrue();
            return belowBlockState.is(BlockTags.DIRT);
        }
        return false;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        int age = state.getValue(AGE);
        if (age != 6)
            return InteractionResult.PASS;

        forAllBlocksInPlant(level, pos, pos1 -> {
            BlockState blockState = level.getBlockState(pos1);
            popResource(level, pos1, new ItemStack(ItemRegistry.COFFEE_BERRY.get()));
            BlockState newBlockState = blockState.setValue(AGE, 4);
            level.setBlock(pos1, newBlockState, 3);
        });

        level.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0f, 0.8f + level.random.nextFloat() * 0.4f);

        return InteractionResult.SUCCESS;
    }
}
