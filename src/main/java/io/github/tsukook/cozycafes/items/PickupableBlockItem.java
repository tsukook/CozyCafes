package io.github.tsukook.cozycafes.items;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;

public abstract class PickupableBlockItem extends BlockItem {
    public PickupableBlockItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    // Override this instead of useOn
    public InteractionResult useOnBlock(UseOnContext useOnContext) {
        return InteractionResult.PASS;
    }

    @Override
    public InteractionResult useOn(UseOnContext useOnContext) {
        Level level = useOnContext.getLevel();
        BlockPos blockPos = useOnContext.getClickedPos();
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        ItemStack itemStack = useOnContext.getItemInHand();
        Player player = useOnContext.getPlayer();

        boolean hasTag = true;
        CompoundTag compoundTag = itemStack.getOrCreateTag();
        if (compoundTag.isEmpty()){
            hasTag = false;
        }

        if (player.isCrouching()) {
            if (hasTag) {
                // Questionable
                ItemStack newItemStack = itemStack.copy();
                newItemStack.setTag(new CompoundTag());
                CompoundTag newTag = new CompoundTag();
                newTag.put("BlockEntityTag", compoundTag.copy());
                newItemStack.setTag(newTag);

                BlockHitResult newHitResult = new BlockHitResult(useOnContext.getClickLocation(), useOnContext.getClickedFace(), blockPos, useOnContext.isInside());

                UseOnContext newContext = new UseOnContext(level, player, useOnContext.getHand(), newItemStack, newHitResult);

                return super.useOn(newContext);
            }
            return super.useOn(useOnContext);
        }

        return this.useOnBlock(useOnContext);
    }
}
