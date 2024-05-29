package io.github.tsukook.cozycafes.client.renderers.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.fluid.FluidRenderer;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModel;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModelRenderer;
import com.simibubi.create.foundation.item.render.PartialItemModelRenderer;
import io.github.tsukook.cozycafes.blocks.entities.SyrupPumpBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class SyrupPumpItemRenderer extends CustomRenderedItemModelRenderer {
    private static final float PIXEL = 1f/16f;

    @Override
    protected void render(ItemStack itemStack, CustomRenderedItemModel customRenderedItemModel, PartialItemModelRenderer partialItemModelRenderer, ItemDisplayContext itemDisplayContext, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int i1) {
        partialItemModelRenderer.render(customRenderedItemModel.getOriginalModel(), light);

        CompoundTag compoundTag = itemStack.getTag();
        if (compoundTag != null && compoundTag.contains("Fluid")) {
            FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(compoundTag.getCompound("Fluid"));

            if (!fluidStack.isEmpty()) {
                float normalizedAmount = (float) fluidStack.getAmount() / SyrupPumpBlockEntity.CAPACITY;
                FluidRenderer.renderFluidBox(fluidStack, -PIXEL*2f + 0.001f, -1f + PIXEL*8f + 0.001f, -PIXEL*2f + 0.001f, PIXEL*2f - 0.001f, (0.5f + PIXEL*4f) * normalizedAmount - 0.5f - 0.001f, PIXEL*2f - 0.001f, multiBufferSource, poseStack, light, true);
            }
        }
    }
}
