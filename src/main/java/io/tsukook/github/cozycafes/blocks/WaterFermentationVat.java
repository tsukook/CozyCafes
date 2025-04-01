package io.tsukook.github.cozycafes.blocks;

import com.mojang.serialization.MapCodec;
import io.tsukook.github.cozycafes.registers.CzCItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class WaterFermentationVat extends BaseEntityBlock {
    public static final BooleanProperty BEANS = BooleanProperty.create("beans");

    private static final VoxelShape INSIDE = box(2.0, 2.0, 2.0, 14.0, 16.0, 14.0);
    private static VoxelShape SHAPE = Shapes.join(
            Shapes.block(),
            INSIDE,
            BooleanOp.ONLY_FIRST
    );

    public WaterFermentationVat(Properties p_49224_) {
        super(p_49224_);
        registerDefaultState(getStateDefinition().any().setValue(BEANS, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BEANS);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState();
    }

    public static final MapCodec<CoffeePulper> CODEC = simpleCodec(CoffeePulper::new);
    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return null;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    // InteractionResult.CONSUME = do not pass and do not animate
    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (stack.is(CzCItemRegistry.PULPED_COFFEE_BEAN)) {
            level.setBlockAndUpdate(pos, state.setValue(BEANS, true));
            stack.consume(64, player);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
