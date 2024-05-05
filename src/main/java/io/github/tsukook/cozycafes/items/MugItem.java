package io.github.tsukook.cozycafes.items;

import com.simibubi.create.foundation.item.render.SimpleCustomRenderer;
import io.github.tsukook.cozycafes.blocks.Muggable;
import io.github.tsukook.cozycafes.client.renderers.item.MugItemRenderer;
import io.github.tsukook.cozycafes.effects.CCEffects;
import io.github.tsukook.cozycafes.fluids.CCFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.fluids.FluidStack;

import java.util.function.Consumer;

public class MugItem extends PickupableBlockItem {
    private static final int DRINK_DURATION = 32;
    public static final int CAPACITY = 250;

    public MugItem(Block block, Properties pProperties) {
        super(block, pProperties);
    }

    @Override
    public InteractionResult useOnBlock(UseOnContext useOnContext) {
        Level level = useOnContext.getLevel();
        BlockPos blockPos = useOnContext.getClickedPos();
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        ItemStack itemStack = useOnContext.getItemInHand();

        CompoundTag compoundTag = itemStack.getOrCreateTag();

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
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        CompoundTag compoundTag = itemStack.getTag();
        if (compoundTag == null || !compoundTag.contains("FluidName") || compoundTag.getInt("Amount") == 0) {
            return InteractionResultHolder.pass(itemStack);
        }
        return ItemUtils.startUsingInstantly(pLevel, pPlayer, pUsedHand);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(SimpleCustomRenderer.create(this, new MugItemRenderer()));
    }
}
