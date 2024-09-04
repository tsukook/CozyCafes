package io.github.tsukook.cozycafes;

import com.tterrag.registrate.util.entry.BlockEntry;
import io.github.tsukook.cozycafes.block.DrinkableBlock;
import io.github.tsukook.cozycafes.block.FluidContainerBlock;
import io.github.tsukook.cozycafes.item.DrinkableBlockItem;
import net.minecraft.world.level.block.Block;

import static io.github.tsukook.cozycafes.CCRegistry.REGISTRATE;

public class CCBlocks {
    public static final BlockEntry<FluidContainerBlock> TEST_CONTAINER = REGISTRATE.block("test_container", FluidContainerBlock::new)
            .properties(properties -> properties.noOcclusion())
            .defaultBlockstate()
            .lang("Test Container")
            .simpleItem()
            .register();

    public static final BlockEntry<DrinkableBlock> TEST_DRINKABLE = REGISTRATE.block("test_drinkable", properties -> new DrinkableBlock(properties,
                    Block.box(5.5f, 0, 5.5f, 10.5f, 5, 10.5f)
            ))
            .properties(properties -> properties.noOcclusion())
            .defaultBlockstate()
            .lang("Test Drinkable")
            .item(DrinkableBlockItem::new)
            .build()
            .register();

    public static void register() {}
}
