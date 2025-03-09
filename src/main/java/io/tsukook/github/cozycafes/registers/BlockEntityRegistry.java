package io.tsukook.github.cozycafes.registers;

import io.tsukook.github.cozycafes.CozyCafes;
import io.tsukook.github.cozycafes.blocks.entities.CoffeePulperBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class BlockEntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, CozyCafes.MODID);

    public static final Supplier<BlockEntityType<CoffeePulperBlockEntity>> COFFEE_PULPER_BLOCK_ENTITY = BLOCK_ENTITIES.register("coffee_pulper",
            () -> new BlockEntityType<>(CoffeePulperBlockEntity::new, BlockRegistry.COFFEE_PULPER.get()));

    public static void register(IEventBus modEventBus) {
        BLOCK_ENTITIES.register(modEventBus);
    }
}
