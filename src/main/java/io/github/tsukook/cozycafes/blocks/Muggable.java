package io.github.tsukook.cozycafes.blocks;

import net.minecraftforge.fluids.FluidStack;

public interface Muggable {
    FluidStack takeFluid(int amount);
    int getAmount();
}
