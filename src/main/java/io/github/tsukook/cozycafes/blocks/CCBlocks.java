package io.github.tsukook.cozycafes.blocks;

import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.block.Blocks;

import static io.github.tsukook.cozycafes.CozyCafes.REGISTRATE;

public class CCBlocks {
    public static final BlockEntry<CoffeeBushBlock> COFFEE_BUSH = REGISTRATE.block("coffee_bush", CoffeeBushBlock::new)
            .initialProperties(() -> Blocks.SWEET_BERRY_BUSH)
            .simpleItem()
            .register();
    public static final BlockEntry<ColdBrewerBlock> COLD_BREWER = REGISTRATE.block("cold_brewer", ColdBrewerBlock::new)
            .initialProperties(() -> Blocks.GLASS)
            .simpleItem()
            .register();

    public static void register() {}
}
