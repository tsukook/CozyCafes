package io.tsukook.github.cozycafes.registers;

import io.tsukook.github.cozycafes.CozyCafes;
import io.tsukook.github.cozycafes.blocks.CoffeePlant;
import io.tsukook.github.cozycafes.blocks.CoffeePulper;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;


public class CzCBlockRegistry {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(CozyCafes.MODID);

    public static final DeferredBlock<CoffeePlant> COFFEE_PLANT = BLOCKS.registerBlock("coffee_plant", CoffeePlant::new, BlockBehaviour.Properties.of()
            .noOcclusion()
            .noCollission()
    );
    public static final DeferredBlock<CoffeePulper> COFFEE_PULPER = BLOCKS.registerBlock("coffee_pulper", CoffeePulper::new, BlockBehaviour.Properties.of().noOcclusion());

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
    }
}
