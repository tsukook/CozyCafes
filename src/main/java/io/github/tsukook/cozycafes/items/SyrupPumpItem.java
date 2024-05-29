package io.github.tsukook.cozycafes.items;

import io.github.tsukook.cozycafes.blocks.Syruppable;
import io.github.tsukook.cozycafes.fluids.CCFluids;
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
import net.minecraftforge.fluids.FluidStack;

public class SyrupPumpItem extends PickupableBlockItem {
    public static final int MYSTICAL_POTION_CONSTANT = 50;

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

        if (compoundTag != null && compoundTag.contains("Fluid") && blockEntity instanceof Syruppable syruppableBlockEntity && !syruppableBlockEntity.hasSyrup()) {
            FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(compoundTag.getCompound("Fluid"));
            if (fluidStack.getFluid().isSame(CCFluids.SYRUP.get()) && fluidStack.getAmount() >= 10 && syruppableBlockEntity.addSyrup(Syrup.readFromNBT(fluidStack.getTag().getCompound("Syrup")))) {
                fluidStack.shrink(10);
                return InteractionResult.sidedSuccess(level.isClientSide());
            }
        }

        return InteractionResult.PASS;
    }
}
