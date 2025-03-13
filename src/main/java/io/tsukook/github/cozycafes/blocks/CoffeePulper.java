package io.tsukook.github.cozycafes.blocks;

import com.mojang.serialization.MapCodec;
import io.tsukook.github.cozycafes.blocks.entities.CoffeePulperBlockEntity;
import io.tsukook.github.cozycafes.registers.CzCBlockEntityRegistry;
import io.tsukook.github.cozycafes.registers.CzCItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class CoffeePulper extends BaseEntityBlock {
    public static EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
    private static final VoxelShape SHAPE = Block.box(2, 0, 2, 14, 14, 14);

    public CoffeePulper(Properties p_49795_) {
        super(p_49795_);
        registerDefaultState(getStateDefinition().any()
                .setValue(FACING, Direction.NORTH)
        );
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    public static final MapCodec<CoffeePulper> CODEC = simpleCodec(CoffeePulper::new);
    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CoffeePulperBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return !level.isClientSide() ? createTickerHelper(blockEntityType, CzCBlockEntityRegistry.COFFEE_PULPER_BLOCK_ENTITY.get(), CoffeePulperBlockEntity::serverTick) : null;
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.getBlockEntity(pos) instanceof CoffeePulperBlockEntity coffeePulperBlockEntity) {
            if (stack.is(CzCItemRegistry.COFFEE_BERRY)) {
                coffeePulperBlockEntity.consumeBerries(stack);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.TRY_WITH_EMPTY_HAND;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.getBlockEntity(pos) instanceof CoffeePulperBlockEntity coffeePulperBlockEntity) {
            coffeePulperBlockEntity.spin(player);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }
}
