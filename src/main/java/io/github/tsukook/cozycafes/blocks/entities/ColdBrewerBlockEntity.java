package io.github.tsukook.cozycafes.blocks.entities;

import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;
import io.github.tsukook.cozycafes.blocks.Muggable;
import io.github.tsukook.cozycafes.fluids.CCFluids;
import io.github.tsukook.cozycafes.fluids.CoffeeFluid;
import io.github.tsukook.cozycafes.items.CoffeeGroundItem;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import java.util.List;

public class ColdBrewerBlockEntity extends BlockEntity implements Muggable, IHaveGoggleInformation {
    private final int TIME = 100;
    private int progress = TIME;
    private final FluidTank fluidTank = new FluidTank(1000);
    private ItemStack brewingItem = new ItemStack(Items.AIR, 1);

    public ColdBrewerBlockEntity(BlockEntityType<?> type, BlockPos pPos, BlockState pBlockState) {
        super(type, pPos, pBlockState);
    }

    public static boolean isBrewable(Item item) {
        return item instanceof CoffeeGroundItem;
    }

    private void update() {
        this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_CLIENTS);
    }

    public void brewTick(Level level, BlockPos blockPos, BlockState blockState, BlockEntity blockEntity) {
        if (hasWater()) {
            if (progress <= 0) {
                progress = TIME;
                if (isBrewable(brewingItem.getItem())) {
                    FluidStack coffeeFluidStack = new FluidStack(CCFluids.COFFEE.get().getSource(), getAmount());
                    CoffeeFluid.fromItem(coffeeFluidStack, brewingItem.getItem());
                    setFluid(coffeeFluidStack);
                }
                setItem(ItemStack.EMPTY);
            } else if (isBrewable(brewingItem.getItem())) {
                progress--;
                update();
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
        update();
    }

    public FluidTank getFluid() {
        return fluidTank;
    }

    public void setItem(ItemStack item) {
        brewingItem = item;
        update();
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
        brewingItem.deserializeNBT(nbt.getCompound("item"));
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

    @Override
    public FluidStack takeFluid(int amount) {
        FluidStack returnStack = fluidTank.drain(amount, IFluidHandler.FluidAction.EXECUTE);
        update();
        return returnStack;
    }

    @Override
    public int getAmount() {
        return fluidTank.getFluidAmount();
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        tooltip.add((Component.literal(spacing + "AAAAA FUCK IT COLDS HELP ME")));
        tooltip.add((Component.literal(spacing + "AAAAAAA GOD IS FUCKING DRAD")));
        return true;
    }
}
