package io.tsukook.github.cozycafes.registers;

import io.tsukook.github.cozycafes.CozyCafes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CzCCreativeTabRegistry {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CozyCafes.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> COZY_CAFES_TAB = CREATIVE_MODE_TABS.register("cozy_cafes_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.cozycafes"))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> CzCItemRegistry.COFFEE_PLANT.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(CzCItemRegistry.COFFEE_PLANT.get());
                output.accept(CzCItemRegistry.COFFEE_PULPER.get());
                output.accept(CzCItemRegistry.COFFEE_BERRY.get());
                output.accept(CzCItemRegistry.PULPED_COFFEE_BEAN.get());
                output.accept(CzCItemRegistry.FERMENTED_COFFEE_BEAN.get());
                output.accept(CzCItemRegistry.DANDELION.get());
            }).build());

    public static void register(IEventBus modEventBus) {
        CREATIVE_MODE_TABS.register(modEventBus);
    }
}
