package io.github.tsukook.cozycafes.items;

import com.tterrag.registrate.util.entry.ItemEntry;
import io.github.tsukook.cozycafes.effects.CCEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

import static io.github.tsukook.cozycafes.CozyCafes.REGISTRATE;

public class CCItems {
//    public static final DeferredRegister<Item> ITEMS =
//            DeferredRegister.create(ForgeRegistries.ITEMS, CozyCafes.MODID);
//
//    public static final RegistryObject<Item> GCOFFEE = ITEMS.register("coffee_grounds", () -> new Item(new Item.Properties().food(ModFood.GROUNDCOFFEE)));
//    public static final RegistryObject<Item> COFFEE = ITEMS.register("coffee_beans", () -> new Item(new Item.Properties()));

    public static final ItemEntry<Item> COFFEE_GROUNDS = REGISTRATE.item("coffee_grounds", Item::new)
            .properties(properties -> properties
                    .food(new FoodProperties.Builder()
                            .alwaysEat()
                            .fast()
                            .effect(() -> new MobEffectInstance(CCEffects.CAFFEINATED.get(), 600, 0), 1.0F)
                            .build()
                    )
            )
            .register();
    public static final ItemEntry<Item> COFFEE_BEANS = REGISTRATE.item("coffee_beans", Item::new)
            .register();

    public static void register() {}

}
