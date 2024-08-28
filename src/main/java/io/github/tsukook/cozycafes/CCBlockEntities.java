package io.github.tsukook.cozycafes;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import io.github.tsukook.cozycafes.block.entity.FluidContainerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import static io.github.tsukook.cozycafes.CCRegistry.REGISTRATE;

public class CCBlockEntities {
    public static BlockEntityEntry<FluidContainerBlockEntity> TEST_CONTAINER = REGISTRATE.blockEntity("test_container",
                    (BlockEntityType<FluidContainerBlockEntity> blockEntityType, BlockPos blockPos, BlockState blockState) ->
                            new FluidContainerBlockEntity(blockEntityType, blockPos, blockState, 1000))
            .validBlocks(CCBlocks.TEST_CONTAINER)
            .register();

    // don't delete
    public static void register() {}
}
