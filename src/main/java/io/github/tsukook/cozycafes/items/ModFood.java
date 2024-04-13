package io.github.tsukook.cozycafes.items;

import io.github.tsukook.cozycafes.effects.Effects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFood {
    public static final FoodProperties GROUNDCOFFEE = new FoodProperties.Builder().alwaysEat().fast().effect(new MobEffectInstance(Effects.CAFFEINATED.get(), 600, 0), 1f).build();
}
