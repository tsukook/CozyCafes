package io.github.tsukook.cozycafes.items;

import net.minecraft.world.item.Item;

public class CoffeeGroundItem extends Item {
    public int caffeinatedEffectDuration = 0;
    public int tint = 0;

    public CoffeeGroundItem(Properties pProperties, int caffeinatedEffectDuration, int tint) {
        super(pProperties);
        this.caffeinatedEffectDuration = caffeinatedEffectDuration;
        this.tint = tint;
    }
}
