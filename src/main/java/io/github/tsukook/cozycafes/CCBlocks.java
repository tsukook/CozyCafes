package io.github.tsukook.cozycafes;

import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.world.level.block.Block;

import static io.github.tsukook.cozycafes.CCRegistry.REGISTRATE;

public class CCBlocks {
    public static final RegistryEntry<Block> TEST_BLOCK = REGISTRATE.block("test_block", Block::new)
            .simpleItem()
            .lang("Test block")
            .register();

    // don't delete
    public static void register() {}
}
