package io.github.tsukook.cozycafes.fluids;

import com.simibubi.create.AllFluids;
import com.simibubi.create.content.fluids.VirtualFluid;
import io.github.tsukook.cozycafes.items.CoffeeGroundItem;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.FluidStack;

public class CoffeeFluid extends VirtualFluid {
    public CoffeeFluid(Properties properties) {
        super(properties);
    }

    public static FluidStack setCaffeineDuration(FluidStack fluidStack, int caffeineDuration) {
        CompoundTag compoundTag = fluidStack.getOrCreateTag();
        compoundTag.putInt("CaffeineDuration", caffeineDuration);
        return fluidStack;
    }

    public static int getCaffeineDuration(FluidStack fluidStack) {
        CompoundTag compoundTag = fluidStack.getTag();
        if (compoundTag != null && compoundTag.contains("CaffeineDuration")) {
            return compoundTag.getInt("CaffeineDuration");
        }
        return 0;
    }

    public static FluidStack setTint(FluidStack fluidStack, int tint) {
        CompoundTag compoundTag = fluidStack.getOrCreateTag();
        compoundTag.putInt("Tint", tint | 0xff000000);
        return fluidStack;
    }

    public static int getTint(FluidStack fluidStack) {
        CompoundTag compoundTag = fluidStack.getTag();
        if (compoundTag != null && compoundTag.contains("Tint")) {
            return compoundTag.getInt("Tint");
        }
        return 0;
    }

    public static FluidStack fromItem(FluidStack fluidStack, Item item) {
        if (item instanceof CoffeeGroundItem coffeeGroundItem) {
            setTint(fluidStack, coffeeGroundItem.tint);
            setCaffeineDuration(fluidStack, coffeeGroundItem.caffeinatedEffectDuration);
        }
        return fluidStack;
    }

    public static class CoffeeFluidType extends AllFluids.TintedFluidType {
        public CoffeeFluidType(Properties properties, ResourceLocation stillTexture, ResourceLocation flowingTexture) {
            super(properties, stillTexture, flowingTexture);
        }

        @Override
        protected int getTintColor(FluidStack fluidStack) {
            return CoffeeFluid.getTint(fluidStack);
        }

        @Override
        protected int getTintColor(FluidState fluidState, BlockAndTintGetter blockAndTintGetter, BlockPos blockPos) {
            return NO_TINT;
        }
    }
}
