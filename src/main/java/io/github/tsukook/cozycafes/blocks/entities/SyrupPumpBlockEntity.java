package io.github.tsukook.cozycafes.blocks.entities;

import io.github.tsukook.cozycafes.fluids.CCFluids;
import io.github.tsukook.cozycafes.folder.Syrup;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;

import static io.github.tsukook.cozycafes.items.SyrupPumpItem.MYSTICAL_POTION_CONSTANT;

public class SyrupPumpBlockEntity extends BlockEntity {
    private final int CAPACITY = 250;
    private FluidStack fluidStack = new FluidStack(CCFluids.SYRUP.get(), 0);

    public SyrupPumpBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    private void update() {
        this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_CLIENTS);
    }

    public boolean addPotion(Potion potion) {
        if (!(fluidStack.getAmount() + MYSTICAL_POTION_CONSTANT > CAPACITY)) {
            if (fluidStack.isEmpty()) {
                fluidStack = new FluidStack(CCFluids.SYRUP.get(), 0);
            }
            fluidStack.grow(MYSTICAL_POTION_CONSTANT);

            CompoundTag compoundTag = fluidStack.getOrCreateTag();
            Syrup syrup;
            if (compoundTag.contains("Syrup")) {
                syrup = Syrup.readFromNBT(compoundTag.getCompound("Syrup"));
            } else {
                syrup = new Syrup();
            }
            syrup.addEffects(potion.getEffects());

            compoundTag.put("Syrup", syrup.writeToNBT(new CompoundTag()));
            return true;
        }
        return false;
    }

    public FluidStack getFluid() {
        return fluidStack;
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.put("Fluid", fluidStack.writeToNBT(new CompoundTag()));
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        fluidStack = FluidStack.loadFluidStackFromNBT(nbt.getCompound("Fluid"));
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
