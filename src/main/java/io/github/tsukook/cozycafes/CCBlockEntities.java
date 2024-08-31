package io.github.tsukook.cozycafes;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import io.github.tsukook.cozycafes.block.entity.FluidContainerBlockEntity;
import io.github.tsukook.cozycafes.client.renderer.blockentity.FluidContainerBlockEntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import static io.github.tsukook.cozycafes.CCRegistry.REGISTRATE;

public class CCBlockEntities {
    public static BlockEntityEntry<FluidContainerBlockEntity> TEST_CONTAINER = REGISTRATE.blockEntity("test_container",
                    (BlockEntityType<FluidContainerBlockEntity> blockEntityType, BlockPos blockPos, BlockState blockState) ->
                            new FluidContainerBlockEntity(blockEntityType, blockPos, blockState, 1000))
            .renderer(() -> context -> new FluidContainerBlockEntityRenderer(context, 1, 1, 1, 14, 14, 14))
            .validBlocks(CCBlocks.TEST_CONTAINER)
            .register();

    public static void register() {}
}
