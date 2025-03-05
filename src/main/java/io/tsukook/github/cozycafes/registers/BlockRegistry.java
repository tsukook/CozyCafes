package io.tsukook.github.cozycafes.registers;

import io.tsukook.github.cozycafes.CozyCafes;
import io.tsukook.github.cozycafes.blocks.CoffeePlant;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockRegistry {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(CozyCafes.MODID);

    //public static final DeferredBlock<Block> TEST_BLOCK = BLOCKS.registerSimpleBlock("test_block", BlockBehaviour.Properties.of().friction(0));
    public static final DeferredBlock<CoffeePlant> COFFEE_PLANT = BLOCKS.registerBlock("coffee_plant", CoffeePlant::new, BlockBehaviour.Properties.of()
            .noOcclusion()
            .noCollission()
    );

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
    }
}
