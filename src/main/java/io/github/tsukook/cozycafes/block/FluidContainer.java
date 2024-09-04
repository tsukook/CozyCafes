package io.github.tsukook.cozycafes.block;

import net.minecraftforge.fluids.FluidStack;

public interface FluidContainer {
    int getCapacity();
    int getAmount();
    boolean isEmpty();
    boolean canDrain();

    FluidStack getFluid();

    FluidStack drain(int amount);
}
