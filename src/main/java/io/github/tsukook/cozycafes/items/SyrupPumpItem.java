package io.github.tsukook.cozycafes.items;

import com.simibubi.create.foundation.item.render.SimpleCustomRenderer;
import io.github.tsukook.cozycafes.blocks.Syruppable;
import io.github.tsukook.cozycafes.client.renderers.item.SyrupPumpItemRenderer;
import io.github.tsukook.cozycafes.fluids.CCFluids;
import io.github.tsukook.cozycafes.folder.Syrup;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.fluids.FluidStack;

import java.util.function.Consumer;

public class SyrupPumpItem extends PickupableBlockItem {
    public static final int MYSTICAL_POTION_CONSTANT = 50;

    public SyrupPumpItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public InteractionResult useOnBlock(UseOnContext useOnContext) {
        Level level = useOnContext.getLevel();
        BlockPos blockPos = useOnContext.getClickedPos();
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        ItemStack itemStack = useOnContext.getItemInHand();
        Player player = useOnContext.getPlayer();

        CompoundTag compoundTag = itemStack.getTag();

        if (compoundTag != null && compoundTag.contains("Fluid") && blockEntity instanceof Syruppable syruppableBlockEntity && !syruppableBlockEntity.hasSyrup()) {
            FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(compoundTag.getCompound("Fluid"));
            if (fluidStack.getFluid().isSame(CCFluids.SYRUP.get()) && fluidStack.getAmount() >= 10 && syruppableBlockEntity.addSyrup(Syrup.readFromNBT(fluidStack.getTag().getCompound("Syrup")))) {
                fluidStack.shrink(10);
                return InteractionResult.sidedSuccess(level.isClientSide());
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(SimpleCustomRenderer.create(this, new SyrupPumpItemRenderer()));
    }
}
