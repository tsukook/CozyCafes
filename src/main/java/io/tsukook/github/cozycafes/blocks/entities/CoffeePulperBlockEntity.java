package io.tsukook.github.cozycafes.blocks.entities;

import io.tsukook.github.cozycafes.client.instances.CoffeePulperInstance;
import io.tsukook.github.cozycafes.registers.BlockEntityRegistry;
import io.tsukook.github.cozycafes.registers.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.world.level.Level;

public class CoffeePulperBlockEntity extends BlockEntity {
    @OnlyIn(Dist.CLIENT)
    public CoffeePulperInstance coffeePulperInstance = new CoffeePulperInstance();

    public ItemStack berries = ItemStack.EMPTY;

    public CoffeePulperBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntityRegistry.COFFEE_PULPER_BLOCK_ENTITY.get(), pos, blockState);
    }

    @OnlyIn(Dist.CLIENT)
    public static void clientTick(Level level, BlockPos pos, BlockState state, CoffeePulperBlockEntity blockEntity) {

    }

    public void consumeBerries(ItemStack stack) {
        if (!stack.is(ItemRegistry.COFFEE_BERRY))
            return;

        if (berries.isEmpty()) {
            berries = stack.copyAndClear();
        } else {
            berries.setCount(berries.getCount() + stack.getCount());
            stack.setCount(0);
        }
        setChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);

        if (!berries.isEmpty())
            tag.put("Item", berries.save(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        berries = ItemStack.parse(registries, tag.getCompound("Item")).orElse(ItemStack.EMPTY);
    }

    public void spin(Level level) {
        if (level.isClientSide()) {
            coffeePulperInstance.addSpin(0.1f);
        }
    }
}
