package io.github.tsukook.cozycafes.items;

import com.simibubi.create.foundation.item.render.SimpleCustomRenderer;
import io.github.tsukook.cozycafes.client.renderers.item.MugItemRenderer;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class MugItem extends HandheldDrinkingApparatus {
    private static final int DRINK_DURATION = 32;
    public static final int CAPACITY = 250;

    public MugItem(Block block, Properties pProperties) {
        super(block, pProperties);
    }

    @Override
    public int getCapacity() {
        return CAPACITY;
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return DRINK_DURATION;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(SimpleCustomRenderer.create(this, new MugItemRenderer()));
    }
}
