package io.github.tsukook.cozycafes.blocks;

import com.simibubi.create.foundation.data.BlockStateGen;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.block.Blocks;

import static io.github.tsukook.cozycafes.CozyCafes.REGISTRATE;

public class CCBlocks {
    public static final BlockEntry<CoffeeBushBlock> COFFEE_BUSH = REGISTRATE.block("coffee_bush", CoffeeBushBlock::new)
            .initialProperties(() -> Blocks.SWEET_BERRY_BUSH)
            .lang("Coffee Bush")
            .simpleItem()
            .register();
    public static final BlockEntry<ColdBrewerBlock> COLD_BREWER = REGISTRATE.block("cold_brewer", ColdBrewerBlock::new)
            .initialProperties(() -> Blocks.GLASS)
            .lang("Cold Brewer")
            .blockstate(BlockStateGen.horizontalBlockProvider(false))
            .item().build()
            .register();

    public static void register() {}
}
