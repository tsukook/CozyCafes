package io.github.tsukook.cozycafes.block.entity;

import io.github.tsukook.cozycafes.block.FluidContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class FluidContainerBlockEntity extends BlockEntity implements FluidContainer {
    private final FluidTank fluidTank = new FluidTank(0);

    public FluidContainerBlockEntity(BlockEntityType<?> type, BlockPos pPos, BlockState pBlockState, int capacity) {
        super(type, pPos, pBlockState);
        fluidTank.setCapacity(capacity);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        CompoundTag fluidTag = new CompoundTag();
        fluidTank.writeToNBT(fluidTag);
        tag.put("Fluid", fluidTag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        CompoundTag fluidTag = tag.getCompound("Fluid");
        fluidTank.readFromNBT(fluidTag);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        this.saveAdditional(tag);
        return tag;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public int getCapacity() {
        return fluidTank.getCapacity();
    }

    @Override
    public int getAmount() {
        return fluidTank.getFluidAmount();
    }

    @Override
    public boolean isEmpty() {
        return fluidTank.isEmpty();
    }

    @Override
    public boolean canDrain() {
        return true;
    }

    @Override
    public FluidStack getFluid() {
        return fluidTank.getFluid();
    }

    @Override
    public FluidStack drain(int amount) {
        return canDrain() ? fluidTank.drain(amount, IFluidHandler.FluidAction.EXECUTE) : FluidStack.EMPTY;
    }
}
