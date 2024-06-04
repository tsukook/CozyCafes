package io.github.tsukook.cozycafes.items;

import com.tterrag.registrate.util.entry.ItemEntry;
import io.github.tsukook.cozycafes.effects.CCEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

import static io.github.tsukook.cozycafes.CozyCafes.REGISTRATE;

public class CCItems {
    public static final ItemEntry<CoffeeGroundItem> DARK_COFFEE_GROUNDS = coffeeGrounds("dark", 150);
    public static final ItemEntry<CoffeeGroundItem> MEDIUM_COFFEE_GROUNDS = coffeeGrounds("medium", 300);
    public static final ItemEntry<CoffeeGroundItem> LIGHT_COFFEE_GROUNDS = coffeeGrounds("blond", 600);

    public static final ItemEntry<Item> DARK_COFFEE_BEANS = REGISTRATE.item("dark_coffee_beans", Item::new)
            .register();
    public static final ItemEntry<Item> MEDIUM_COFFEE_BEANS = REGISTRATE.item("medium_coffee_beans", Item::new)
            .register();
    public static final ItemEntry<Item> BLOND_COFFEE_BEANS = REGISTRATE.item("blond_coffee_beans", Item::new)
            .register();
    public static final ItemEntry<Item> GREEN_COFFEE_BEANS = REGISTRATE.item("green_coffee_beans", Item::new)
            .register();

    private static ItemEntry<CoffeeGroundItem> coffeeGrounds(String name, int effectDuration) {
        ItemEntry<CoffeeGroundItem> item = REGISTRATE.item(name + "_coffee_grounds", properties -> new CoffeeGroundItem(properties, effectDuration))
                .properties(properties -> properties
                        .food(new FoodProperties.Builder()
                                .alwaysEat()
                                .fast()
                                .effect(() -> new MobEffectInstance(CCEffects.CAFFEINATED.get(), effectDuration, 0), 1.0F)
                                .build()
                        )
                )
                .register();
        return item;
    }

    @SuppressWarnings("EmptyMethod")
    public static void register() {}

}
