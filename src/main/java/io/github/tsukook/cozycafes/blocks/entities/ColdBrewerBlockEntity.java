package io.github.tsukook.cozycafes.blocks.entities;

import com.simibubi.create.foundation.utility.NBTHelper;
import io.github.tsukook.cozycafes.blocks.CCBlockEntities;
import io.github.tsukook.cozycafes.fluids.CCFluids;
import io.github.tsukook.cozycafes.items.CCItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.Nullable;

public class ColdBrewerBlockEntity extends BlockEntity {
    private final int TIME = 100;
    private int progress = TIME;
    private FluidTank fluidTank = new FluidTank(15);
    private ItemStack brewingItem = new ItemStack(Items.AIR, 1);

    public ColdBrewerBlockEntity(BlockEntityType<?> type, BlockPos pPos, BlockState pBlockState) {
        super(type, pPos, pBlockState);
    }

    public static boolean isBrewable(ItemStack itemStack) {
        return itemStack.is(CCItems.LIGHT_COFFEE_GROUNDS.get()) || itemStack.is(CCItems.MEDIUM_COFFEE_GROUNDS.get()) || itemStack.is(CCItems.DARK_COFFEE_GROUNDS.get());
    }

    public void brewTick(Level level, BlockPos blockPos, BlockState blockState, BlockEntity blockEntity) {
        if (hasWater()) {
            if (progress <= 0) {
                if (brewingItem.is(CCItems.LIGHT_COFFEE_GROUNDS.get())) {
                    setFluid(new FluidStack(CCFluids.BLOND_ROAST_COLD_BREW.get().getSource(), 1000));
                } else if (brewingItem.is(CCItems.MEDIUM_COFFEE_GROUNDS.get())) {
                    setFluid(new FluidStack(CCFluids.MEDIUM_ROAST_COLD_BREW.get().getSource(), 1000));
                } else if (brewingItem.is(CCItems.DARK_COFFEE_GROUNDS.get())) {
                    setFluid(new FluidStack(CCFluids.DARK_ROAST_COLD_BREW.get().getSource(), 1000));
                }
                setItem(ItemStack.EMPTY);

            } else if (isBrewable(brewingItem)) {
                progress--;
            }
        }
    }

    private boolean hasWater() {
        if  (fluidTank.isEmpty()) {
            return false;
        }
        return fluidTank.getFluid().getFluid().isSame(Fluids.WATER);
    }

    public void setFluid(FluidStack fluidStack) {
        fluidTank.setFluid(fluidStack);
    }

    public FluidTank getFluid() {
        return fluidTank;
    }

    public void setItem(ItemStack item) {
        brewingItem = item;
    }

    public boolean hasItem() {
        return !brewingItem.is(Items.AIR);
    }

    public ItemStack getItem() {
        return brewingItem;
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putInt("progress", progress);
        nbt.put("item", brewingItem.serializeNBT());
        nbt.put("fluid", fluidTank.writeToNBT(new CompoundTag()));
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        progress = nbt.getInt("progress");
        brewingItem.deserializeNBT(nbt);
        fluidTank.readFromNBT(nbt.getCompound("fluid"));
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbt = new CompoundTag();
        saveAdditional(nbt);
        return nbt;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}