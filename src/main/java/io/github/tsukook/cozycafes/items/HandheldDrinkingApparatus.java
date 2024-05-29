package io.github.tsukook.cozycafes.items;

import io.github.tsukook.cozycafes.blocks.Muggable;
import io.github.tsukook.cozycafes.effects.CCEffects;
import io.github.tsukook.cozycafes.fluids.CCFluids;
import io.github.tsukook.cozycafes.folder.Syrup;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;

public abstract class HandheldDrinkingApparatus extends PickupableBlockItem{
    public HandheldDrinkingApparatus(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    public abstract int getCapacity();

    @Override
    public InteractionResult useOnBlock(UseOnContext useOnContext) {
        Level level = useOnContext.getLevel();
        BlockPos blockPos = useOnContext.getClickedPos();
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        ItemStack itemStack = useOnContext.getItemInHand();

        CompoundTag compoundTag = itemStack.getOrCreateTag();

        if (FluidStack.loadFluidStackFromNBT(itemStack.getTag()).isEmpty() && blockEntity instanceof Muggable muggableBlockEntity) {
            if (muggableBlockEntity.getAmount() >= this.getCapacity()) {
                FluidStack fluidStack = muggableBlockEntity.takeFluid(this.getCapacity());
                Syrup syrup = new Syrup();
                fluidStack.getOrCreateTag().put("Syrup", syrup.writeToNBT(new CompoundTag()));
                compoundTag.put("Fluid", fluidStack.writeToNBT(new CompoundTag()));

                return InteractionResult.sidedSuccess(level.isClientSide());
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.DRINK;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        CompoundTag compoundTag = itemStack.getTag();

        if (!level.isClientSide()) {
            FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(compoundTag.getCompound("Fluid"));
            Fluid fluid = fluidStack.getFluid();
            int duration;

            // These are copied from the durations given by coffee grounds, these should probably be changed
            if (fluid.isSame(CCFluids.BLOND_ROAST_COLD_BREW.get())) {
                duration = 600*20;
            } else if (fluid.isSame(CCFluids.MEDIUM_ROAST_COLD_BREW.get())) {
                duration = 300*20;
            } else if (fluid.isSame(CCFluids.DARK_ROAST_COLD_BREW.get())) {
                duration = 150*20;
            } else {
                duration = 0;
            }

            if (duration != 0) {
                livingEntity.addEffect(new MobEffectInstance(CCEffects.CAFFEINATED.get(), duration, 0));
                CompoundTag fluidTag = fluidStack.getTag();
                if (fluidTag != null && fluidTag.contains("Syrup")) {
                    Syrup syrup = Syrup.readFromNBT(fluidTag.getCompound("Syrup"));
                    syrup.getEffects().forEach(mobEffectInstance -> {
                        livingEntity.addEffect(new MobEffectInstance(mobEffectInstance.getEffect(), duration, mobEffectInstance.getAmplifier()));
                    });
                }
            }
        }

        FluidStack fluidStack = FluidStack.EMPTY;
        compoundTag.put("Fluid", fluidStack.writeToNBT(new CompoundTag()));

        return itemStack;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        CompoundTag compoundTag = itemStack.getTag();
        if (compoundTag == null || !compoundTag.contains("Fluid")) {
            return InteractionResultHolder.pass(itemStack);
        }
        return ItemUtils.startUsingInstantly(level, player, interactionHand);
    }
}
