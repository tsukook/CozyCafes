package io.github.tsukook.cozycafes.client.renderers.blockEntity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.fluid.FluidRenderer;
import io.github.tsukook.cozycafes.blocks.entities.MugBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraftforge.fluids.FluidStack;

public class MugBlockEntityRenderer implements BlockEntityRenderer<MugBlockEntity> {
    public MugBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}
    private static final float PIXEL = 1f/16f;

    @Override
    public void render(MugBlockEntity mugBlockEntity, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int overlay) {
        FluidStack fluidStack = mugBlockEntity.getFluid();
        if (!fluidStack.isEmpty()) {
            FluidRenderer.renderFluidBox(fluidStack, PIXEL*7, PIXEL, PIXEL*7, PIXEL*9, PIXEL*4.8f, PIXEL*9, multiBufferSource, poseStack, light, false);
        }
    }
}
