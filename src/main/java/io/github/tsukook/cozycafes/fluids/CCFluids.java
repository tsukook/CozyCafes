package io.github.tsukook.cozycafes.fluids;

import com.simibubi.create.content.fluids.VirtualFluid;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.FluidEntry;
import io.github.tsukook.cozycafes.CozyCafes;

import static io.github.tsukook.cozycafes.CozyCafes.REGISTRATE;

public class CCFluids {
    public static final FluidEntry<VirtualFluid> BLOND_ROAST_COLD_BREW = REGISTRATE.virtualFluid("blond_roast_cold_brew", CozyCafes.getResource("fluid/blond_roast_cold_brew_still"), CozyCafes.getResource("fluid/blond_roast_cold_brew_flow"), CreateRegistrate::defaultFluidType, VirtualFluid::new)
            .register();
    public static final FluidEntry<VirtualFluid> MEDIUM_ROAST_COLD_BREW = REGISTRATE.virtualFluid("medium_roast_cold_brew", CozyCafes.getResource("fluid/medium_roast_cold_brew_still"), CozyCafes.getResource("fluid/medium_roast_cold_brew_flow"), CreateRegistrate::defaultFluidType, VirtualFluid::new)
            .register();
    public static final FluidEntry<VirtualFluid> DARK_ROAST_COLD_BREW = REGISTRATE.virtualFluid("dark_roast_cold_brew", CozyCafes.getResource("fluid/dark_roast_cold_brew_still"), CozyCafes.getResource("fluid/dark_roast_cold_brew_flow"), CreateRegistrate::defaultFluidType, VirtualFluid::new)
            .register();

    public static final FluidEntry<SyrupFluid> SYRUP = REGISTRATE.virtualFluid("syrup", CozyCafes.getResource("fluid/syrup_still"), CozyCafes.getResource("fluid/syrup_flow"), SyrupFluid.SyrupFluidType::new, SyrupFluid::new)
            .register();

    public static void register() {}
}
