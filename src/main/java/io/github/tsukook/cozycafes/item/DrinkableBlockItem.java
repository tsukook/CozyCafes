package io.github.tsukook.cozycafes.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.LavaFluid;
import net.minecraftforge.fluids.FluidStack;

public class DrinkableBlockItem extends UsableBlockItem {
    private final int DRINK_DURATION;

    public DrinkableBlockItem(Block pBlock, Properties pProperties, int drink_duration) {
        super(pBlock, pProperties);
        this.DRINK_DURATION = drink_duration;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        if (!itemStack.hasTag() || !itemStack.getTag().contains("Fluid"))
            return itemStack;

        FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(itemStack.getTag().getCompound("Fluid"));

        if (fluidStack.getFluid() instanceof LavaFluid) {
            livingEntity.setSecondsOnFire(15);
            livingEntity.hurt(level.damageSources().lava(), 15f);
        }

        fluidStack.shrink(100);

        fluidStack.writeToNBT(itemStack.getTag().getCompound("Fluid"));

        return itemStack;
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return DRINK_DURATION;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.DRINK;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemStack = player.getItemInHand(usedHand);

        if (player.isCrouching() || !itemStack.hasTag() || !itemStack.getTag().contains("Fluid"))
            return InteractionResultHolder.pass(itemStack);

        return ItemUtils.startUsingInstantly(level, player, usedHand);
    }
}
