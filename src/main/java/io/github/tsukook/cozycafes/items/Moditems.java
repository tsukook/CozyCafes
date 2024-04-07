package io.github.tsukook.cozycafes.items;

import io.github.tsukook.cozycafes.cozycafes;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Moditems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, cozycafes.MODID);

            public static final RegistryObject<Item> COFFEE = ITEMS.register("coffee_grounds",() ->new Item(new Item.Properties()));

            public static void  register(IEventBus eventBus){
        ITEMS.register(eventBus);
            }

}
