package io.github.tsukook.cozycafes.client.renderers.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.fluid.FluidRenderer;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModel;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModelRenderer;
import com.simibubi.create.foundation.item.render.PartialItemModelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class MugItemRenderer extends CustomRenderedItemModelRenderer {
    private static final float PIXEL = 1f / 16f;

    @Override
    protected void render(ItemStack itemStack, CustomRenderedItemModel customRenderedItemModel, PartialItemModelRenderer partialItemModelRenderer, ItemDisplayContext itemDisplayContext, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int overlay) {
        partialItemModelRenderer.render(customRenderedItemModel.getOriginalModel(), light);

        CompoundTag compoundTag = itemStack.getTag();
        if (compoundTag != null) {
            FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(compoundTag);

            if (!fluidStack.isEmpty()) {
                FluidRenderer.renderFluidBox(fluidStack, -PIXEL, -PIXEL*7f, -PIXEL, PIXEL, -PIXEL*3.2f, PIXEL, multiBufferSource, poseStack, light, true);
            }
        }
    }
}
