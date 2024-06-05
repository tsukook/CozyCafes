package io.github.tsukook.cozycafes.fluids;

import com.tterrag.registrate.util.entry.FluidEntry;
import io.github.tsukook.cozycafes.CozyCafes;

import static io.github.tsukook.cozycafes.CozyCafes.REGISTRATE;

public class CCFluids {
    public static final FluidEntry<CoffeeFluid> COFFEE = REGISTRATE.virtualFluid("coffee", CozyCafes.getResource("fluid/coffee_still"), CozyCafes.getResource("fluid/coffee_flow"), CoffeeFluid.CoffeeFluidType::new, CoffeeFluid::new)
            .lang("Coffee")
            .register();

    public static final FluidEntry<SyrupFluid> SYRUP = REGISTRATE.virtualFluid("syrup", CozyCafes.getResource("fluid/syrup_still"), CozyCafes.getResource("fluid/syrup_flow"), SyrupFluid.SyrupFluidType::new, SyrupFluid::new)
            .lang("Syrup")
            .register();

    @SuppressWarnings("EmptyMethod")
    public static void register() {}
}
