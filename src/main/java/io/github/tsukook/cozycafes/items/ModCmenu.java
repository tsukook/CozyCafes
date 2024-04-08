package io.github.tsukook.cozycafes.items;

import io.github.tsukook.cozycafes.cozycafes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCmenu {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, cozycafes.MODID);

    public static final RegistryObject<CreativeModeTab> MODCMENU_TAB = CREATIVE_MODE_TABS.register("creativetab", () -> CreativeModeTab.builder().icon(() -> Moditems.COFFEE.get().getDefaultInstance())
            .title(Component.translatable("creativetab.cozycafes"))
            .displayItems((pParameters, pOutput) -> {
                pOutput.accept(Moditems.COFFEE.get());
                pOutput.accept(Moditems.GCOFFEE.get());
                pOutput.accept(Moditems.COFFEE_BUSH.get());

            }).build());


    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
}
}

