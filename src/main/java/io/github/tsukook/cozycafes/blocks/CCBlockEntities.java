package io.github.tsukook.cozycafes.blocks;

import com.tterrag.registrate.builders.BlockEntityBuilder;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import io.github.tsukook.cozycafes.blocks.entities.ColdBrewerBlockEntity;
import io.github.tsukook.cozycafes.client.renderers.blockEntity.ColdBrewerBlockEntityRenderer;
import net.minecraft.world.level.block.entity.BlockEntityType;

import static io.github.tsukook.cozycafes.CozyCafes.REGISTRATE;

public class CCBlockEntities {
    public static final BlockEntityEntry<ColdBrewerBlockEntity> COLD_BREWER_BLOCK_ENTITY = REGISTRATE.blockEntity("cold_brewer", ColdBrewerBlockEntity::new)
            .validBlocks(CCBlocks.COLD_BREWER)
            .renderer(() -> ColdBrewerBlockEntityRenderer::new)
            .register();

    public static void register() {}
}
