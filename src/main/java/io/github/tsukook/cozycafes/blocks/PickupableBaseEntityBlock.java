package io.github.tsukook.cozycafes.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public abstract class PickupableBaseEntityBlock extends BaseEntityBlock {
    protected PickupableBaseEntityBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if (itemStack.isEmpty() && blockEntity != null) {

            ItemStack blockItem = new ItemStack(this.asItem(),1);
            blockItem.setTag(blockEntity.getUpdateTag());

            player.setItemInHand(interactionHand, blockItem);

            level.removeBlock(blockPos, false);
            return InteractionResult.sidedSuccess(level.isClientSide());
        }

        return InteractionResult.PASS;
    }
}
