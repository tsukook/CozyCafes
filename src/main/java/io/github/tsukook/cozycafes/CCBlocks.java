package io.github.tsukook.cozycafes;

import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import io.github.tsukook.cozycafes.block.FluidContainerBlock;
import net.minecraft.world.level.block.Block;

import static io.github.tsukook.cozycafes.CCRegistry.REGISTRATE;

public class CCBlocks {
    public static final RegistryEntry<Block> TEST_BLOCK = REGISTRATE.block("test_block", Block::new)
            .simpleItem()
            .lang("Test Block")
            .register();

    public static final BlockEntry<FluidContainerBlock> TEST_CONTAINER = REGISTRATE.block("test_container", FluidContainerBlock::new)
            /*.blockEntity(((blockEntityType, blockPos, blockState) -> new FluidContainerBlockEntity(blockEntityType, blockPos, blockState, 1000)))
            .build()*/
            .lang("Test Container")
            .simpleItem()
            .register();

    // don't delete
    public static void register() {}
}
