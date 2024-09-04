package io.github.tsukook.cozycafes.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

public abstract class PickupableBlock extends Block implements EntityBlock {
    public PickupableBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        ItemStack itemInHand = player.getItemInHand(interactionHand);
        if (!itemInHand.isEmpty())
            return InteractionResult.PASS;

        ItemStack newItem = new ItemStack(this.asItem());
        newItem.setTag(level.getBlockEntity(blockPos).getUpdateTag());

        player.setItemInHand(interactionHand, newItem);

        level.removeBlock(blockPos, false);
        level.gameEvent(player, GameEvent.BLOCK_DESTROY, blockPos);

        return InteractionResult.sidedSuccess(level.isClientSide());
    }
}
