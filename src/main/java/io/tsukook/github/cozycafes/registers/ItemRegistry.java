package io.tsukook.github.cozycafes.registers;

import io.tsukook.github.cozycafes.CozyCafes;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemRegistry {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CozyCafes.MODID);

    public static final DeferredItem<Item> TEST_ITEM = ITEMS.registerSimpleItem("test_item", new Item.Properties().overrideDescription("discreption"));
    public static final DeferredItem<BlockItem> TEST_BLOCK_ITEM = ITEMS.registerSimpleBlockItem(BlockRegistry.TEST_BLOCK);

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }

}
