package io.github.tsukook.cozycafes.items;

import io.github.tsukook.cozycafes.blocks.Muggable;
import io.github.tsukook.cozycafes.effects.CCEffects;
import io.github.tsukook.cozycafes.fluids.CCFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class MugItem extends Item {
    private static final int DRINK_DURATION = 32;
    private static final int CAPACITY = 250;

    public MugItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        BlockPos blockPos = pContext.getClickedPos();
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        ItemStack itemStack = pContext.getItemInHand();

        CompoundTag compoundTag = itemStack.getOrCreateTag();
        if (compoundTag.contains("FluidName")) {
            if (compoundTag.getInt("Amount") != 0) {
                return InteractionResult.PASS;
            }
        }

        if (blockEntity instanceof Muggable muggableBlockEntity) {
            if (muggableBlockEntity.getAmount() >= CAPACITY) {
                FluidStack fluidStack = muggableBlockEntity.takeFluid(CAPACITY);
                fluidStack.writeToNBT(compoundTag);

                return InteractionResult.sidedSuccess(level.isClientSide());
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        CompoundTag compoundTag = itemStack.getTag();

        if(!level.isClientSide()) {
            FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(compoundTag);
            Fluid fluid = fluidStack.getFluid();
            int duration = 0;

            // These are copied from the durations given by coffee grounds, these should probably be changed
            if (fluid.isSame(CCFluids.BLOND_ROAST_COLD_BREW.get())) {
                duration = 600;
            } else if (fluid.isSame(CCFluids.MEDIUM_ROAST_COLD_BREW.get())) {
                duration = 300;
            } else if (fluid.isSame(CCFluids.DARK_ROAST_COLD_BREW.get())) {
                duration = 150;
            }

            if (duration != 0) {
                livingEntity.addEffect(new MobEffectInstance(CCEffects.CAFFEINATED.get(), duration, 0));
            }
        }

        FluidStack fluidStack = FluidStack.EMPTY;
        fluidStack.writeToNBT(compoundTag);

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
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        CompoundTag compoundTag = pPlayer.getItemInHand(pUsedHand).getTag();
        if (compoundTag == null || !compoundTag.contains("FluidName") || compoundTag.getInt("Amount") == 0) {
            return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
        }
        return ItemUtils.startUsingInstantly(pLevel, pPlayer, pUsedHand);
    }
}
