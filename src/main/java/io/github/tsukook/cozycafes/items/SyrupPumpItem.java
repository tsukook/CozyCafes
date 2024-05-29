package io.github.tsukook.cozycafes.items;

import io.github.tsukook.cozycafes.blocks.Syruppable;
import io.github.tsukook.cozycafes.folder.Syrup;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

public class SyrupPumpItem extends PickupableBlockItem {
    public SyrupPumpItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public InteractionResult useOnBlock(UseOnContext useOnContext) {
        Level level = useOnContext.getLevel();
        BlockPos blockPos = useOnContext.getClickedPos();
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        ItemStack itemStack = useOnContext.getItemInHand();
        Player player = useOnContext.getPlayer();

        CompoundTag compoundTag = itemStack.getTag();

        if (compoundTag != null && compoundTag.contains("Syrup") && blockEntity instanceof Syruppable syruppableBlockEntity && !syruppableBlockEntity.hasSyrup()) {
            if (syruppableBlockEntity.addSyrup(Syrup.readFromNBT(compoundTag.getCompound("Syrup")))) {
                return InteractionResult.sidedSuccess(level.isClientSide());
            }
        }

        return InteractionResult.PASS;
    }
}
