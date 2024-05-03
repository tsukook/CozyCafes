package io.github.tsukook.cozycafes.blocks;

import io.github.tsukook.cozycafes.blocks.entities.ColdBrewerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fluids.FluidStack;

public class ColdBrewerBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final VoxelShape SHAPE = Block.box(3, 0, 3, 13, 14, 13);

    public ColdBrewerBlock(Properties pProperties) {
        super(pProperties);
        registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ColdBrewerBlockEntity(CCBlockEntities.COLD_BREWER_BLOCK_ENTITY.get(), blockPos, blockState);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        //return level.isClientSide() ? null : (level1, blockPos, blockState1, blockEntity) -> ((ColdBrewerBlockEntity) blockEntity).brewTick(level1, blockPos, blockState1, blockEntity);
        return level.isClientSide() ? null : (level1, blockPos, blockState1, blockEntity) -> ((ColdBrewerBlockEntity) blockEntity).brewTick(level1, blockPos, blockState1, blockEntity);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        boolean success = false;
        if (level.getBlockEntity(blockPos) instanceof ColdBrewerBlockEntity coldBrewerBlockEntity) {
            if (coldBrewerBlockEntity.getFluid().isEmpty() && itemStack.is(Items.WATER_BUCKET)) {
                success = true;
                if (!level.isClientSide()) {
                    coldBrewerBlockEntity.setFluid(new FluidStack(Fluids.WATER, 1000));
                    if (!player.isCreative())
                        player.setItemInHand(interactionHand, new ItemStack(Items.BUCKET));
                }
            }
            if (!coldBrewerBlockEntity.hasItem() && ColdBrewerBlockEntity.isBrewable(itemStack)){
                success = true;
                if (!level.isClientSide()) {
                    coldBrewerBlockEntity.setItem(itemStack.copyWithCount(1));
                    if (!player.isCreative())
                        itemStack.setCount(itemStack.getCount() - 1);
                }
            }
        }
        return success ? InteractionResult.sidedSuccess(level.isClientSide()) : InteractionResult.PASS;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }
}
