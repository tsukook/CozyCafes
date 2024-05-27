package io.github.tsukook.cozycafes.fluids;

import com.simibubi.create.AllFluids;
import com.simibubi.create.content.fluids.VirtualFluid;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SyrupFluid extends VirtualFluid {
    public SyrupFluid(Properties properties) {
        super(properties);
    }

    public static FluidStack addPotion(FluidStack fluidStack, MobEffectInstance effect) {
        List<MobEffectInstance> effects = new ArrayList<>();
        effects.add(effect);
        return addPotion(fluidStack, effects);
    }

    public static FluidStack addPotion(FluidStack fluidStack, Collection<MobEffectInstance> effects) {
        CompoundTag compoundTag = fluidStack.getOrCreateTag();
        ListTag listTag = new ListTag();
        if (compoundTag.contains("PotionEffects")) {
            listTag = compoundTag.getList("PotionEffects", ListTag.TAG_COMPOUND);
        }

        for (MobEffectInstance effectInstance : effects) {
            listTag.add(effectInstance.save(new CompoundTag()));
        }

        compoundTag.put("PotionEffects", listTag);
        return fluidStack;
    }

    public static class SyrupFluidType extends AllFluids.TintedFluidType {

        public SyrupFluidType(Properties properties, ResourceLocation stillTexture, ResourceLocation flowingTexture) {
            super(properties, stillTexture, flowingTexture);
        }

        @Override
        protected int getTintColor(FluidStack fluidStack) {
            CompoundTag compoundTag = fluidStack.getOrCreateTag();
            ListTag listTag = compoundTag.getList("PotionEffects", ListTag.TAG_COMPOUND);
            int[] color = {0, 0, 0};
            int[] numberOfColors = {0};
            listTag.forEach(tag -> {
                MobEffectInstance mobEffectInstance = MobEffectInstance.load((CompoundTag) tag);
                int fullColor = mobEffectInstance.getEffect().getColor();
                color[0] = (fullColor & 0xff0000) >> 16;
                color[1] = (fullColor & 0x00ff00) >> 8;
                color[2] = (fullColor & 0x0000ff);
                numberOfColors[0] += 1;
            });

            if (numberOfColors[0] == 0)
                numberOfColors[0] = 1;
            color[0] /= numberOfColors[0];
            color[1] /= numberOfColors[0];
            color[2] /= numberOfColors[0];
            int colorInteger = (color[0] << 16) | (color[1] << 8) | color[2];
            return colorInteger | 0xff000000;
        }

        @Override
        protected int getTintColor(FluidState fluidState, BlockAndTintGetter blockAndTintGetter, BlockPos blockPos) {
            return NO_TINT;
        }
    }
}
