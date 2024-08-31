package io.github.tsukook.cozycafes;

import com.tterrag.registrate.util.entry.BlockEntry;
import io.github.tsukook.cozycafes.block.FluidContainerBlock;

import static io.github.tsukook.cozycafes.CCRegistry.REGISTRATE;

public class CCBlocks {
    public static final BlockEntry<FluidContainerBlock> TEST_CONTAINER = REGISTRATE.block("test_container", FluidContainerBlock::new)
            /*.blockEntity(((blockEntityType, blockPos, blockState) -> new FluidContainerBlockEntity(blockEntityType, blockPos, blockState, 1000)))
            .build()*/
            .properties(properties -> properties.noOcclusion())
            .defaultBlockstate()
            .lang("Test Container")
            .simpleItem()
            .register();

    public static void register() {}
}
