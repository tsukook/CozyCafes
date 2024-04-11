package io.github.tsukook.cozycafes.items;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFood {
    // replace MOVEMENT_SPEED with CAFFIENATED once the effect is added! :3c
    public static final FoodProperties GROUNDCOFFEE = new FoodProperties.Builder().alwaysEat().fast().effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, 1), 1f).build();
}
