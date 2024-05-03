package io.github.tsukook.cozycafes.blocks.entities;

import io.github.tsukook.cozycafes.blocks.Muggable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;

public class MugBlockEntity extends BlockEntity implements Muggable {
    private FluidStack fluidStack = FluidStack.EMPTY;

    public MugBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public FluidStack getFluid() {
        return fluidStack;
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        fluidStack.writeToNBT(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        fluidStack = FluidStack.loadFluidStackFromNBT(nbt);
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

    @Override
    public FluidStack takeFluid(int amount) {
        int maxAmount = this.getAmount();
        if (amount > maxAmount) {
            amount = maxAmount;
        }
        fluidStack.shrink(amount);
        FluidStack returnStack = new FluidStack(fluidStack.getFluid(), amount);
        this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_CLIENTS);
        return returnStack;
    }

    @Override
    public int getAmount() {
        return fluidStack.getAmount();
    }
}
