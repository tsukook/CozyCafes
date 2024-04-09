package io.github.tsukook.cozycafes.blocks.custom;

import io.github.tsukook.cozycafes.cozycafes;
import io.github.tsukook.cozycafes.items.Moditems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class Modblocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, cozycafes.MODID);


    //Blocks from now on, i hate modprep ;3c

    public static final RegistryObject<Block> COFFEE_BUSH = registerBlocks("coffee_bush",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.SWEET_BERRY_BUSH).noOcclusion().noCollission()));




    private static <T extends  Block>RegistryObject<T> registerBlocks(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;

    }

private static <T extends Block>RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block){
return Moditems.ITEMS.register(name,() -> new BlockItem(block.get(), new Item.Properties()));
}

    public static void register(IEventBus eventbus) {
    BLOCKS.register(eventbus);
    }
}
