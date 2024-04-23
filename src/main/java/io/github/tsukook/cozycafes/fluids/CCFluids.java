package io.github.tsukook.cozycafes.fluids;

import com.simibubi.create.content.fluids.VirtualFluid;
import com.tterrag.registrate.util.entry.FluidEntry;
import io.github.tsukook.cozycafes.CozyCafes;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.ForgeFlowingFluid;

import static io.github.tsukook.cozycafes.CozyCafes.REGISTRATE;

public class CCFluids {
    public static final FluidEntry<VirtualFluid> BLOND_ROAST_COLD_BREW = REGISTRATE.virtualFluid("blond_roast_cold_brew", new ResourceLocation(CozyCafes.MODID, "textures/fluid/blond_roast_cold_brew_still"), new ResourceLocation(CozyCafes.MODID, "textures/fluid/blond_roast_cold_brew_flow"))
            .register();
    public static final FluidEntry<VirtualFluid> MEDIUM_ROAST_COLD_BREW = REGISTRATE.virtualFluid("medium_roast_cold_brew", new ResourceLocation(CozyCafes.MODID, "textures/fluid/medium_roast_cold_brew_still"), new ResourceLocation(CozyCafes.MODID, "textures/fluid/medium_roast_cold_brew_flow"))
            .register();
    public static final FluidEntry<VirtualFluid> DARK_ROAST_COLD_BREW = REGISTRATE.virtualFluid("dark_roast_cold_brew", new ResourceLocation(CozyCafes.MODID, "textures/fluid/dark_roast_cold_brew_still"), new ResourceLocation(CozyCafes.MODID, "textures/fluid/dark_roast_cold_brew_flow"))
            .register();

    public static void register() {}
}
