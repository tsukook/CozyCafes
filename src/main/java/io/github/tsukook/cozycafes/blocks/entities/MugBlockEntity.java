package io.github.tsukook.cozycafes.blocks.entities;

import io.github.tsukook.cozycafes.blocks.Muggable;
import io.github.tsukook.cozycafes.blocks.Syruppable;
import io.github.tsukook.cozycafes.folder.Syrup;
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

public class MugBlockEntity extends BlockEntity implements Muggable, Syruppable {
    private FluidStack fluidStack = FluidStack.EMPTY;
    private Syrup syrup = new Syrup();

    public MugBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public FluidStack getFluid() {
        return fluidStack;
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        if (!fluidStack.isEmpty()) {
            fluidStack.getOrCreateTag().put("Syrup", syrup.writeToNBT(new CompoundTag()));
            nbt.put("Fluid", fluidStack.writeToNBT(new CompoundTag()));
        }
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        fluidStack = FluidStack.loadFluidStackFromNBT(nbt.getCompound("Fluid"));
        syrup = Syrup.readFromNBT(fluidStack.getTag().getCompound("Syrup"));
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

        FluidStack returnStack = fluidStack.copy();
        returnStack.setAmount(amount);

        fluidStack.shrink(amount);
        this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_CLIENTS);
        return returnStack;
    }

    @Override
    public int getAmount() {
        return fluidStack.getAmount();
    }

    @Override
    public boolean addSyrup(Syrup syrup) {
        this.syrup.getEffects().addAll(syrup.getEffects());
        this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_CLIENTS);
        return true;
    }

    @Override
    public boolean hasSyrup() {
        return this.syrup.hasEffects();
    }
}
