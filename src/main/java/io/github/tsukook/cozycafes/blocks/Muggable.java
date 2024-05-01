package io.github.tsukook.cozycafes.blocks;

import net.minecraftforge.fluids.capability.templates.FluidTank;

public interface Muggable {
    FluidTank takeFluid(int amount);
}
