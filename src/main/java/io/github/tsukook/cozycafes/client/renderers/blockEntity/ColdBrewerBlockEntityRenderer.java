package io.github.tsukook.cozycafes.client.renderers.blockEntity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.fluid.FluidRenderer;
import io.github.tsukook.cozycafes.blocks.entities.ColdBrewerBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraftforge.fluids.FluidStack;

public class ColdBrewerBlockEntityRenderer implements BlockEntityRenderer<ColdBrewerBlockEntity> {
    public ColdBrewerBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}
    private static final float PIXEL = 1f/16f;

    @Override
    public void render(ColdBrewerBlockEntity coldBrewerBlockEntity, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay) {
        if(coldBrewerBlockEntity.getFluid().isEmpty()) {
            return;
        }

        FluidStack fluidStack = coldBrewerBlockEntity.getFluid().getFluid();

        if (!fluidStack.isEmpty()) {
            FluidRenderer.renderFluidBox(fluidStack, PIXEL*3+0.001f, 0, PIXEL*3+0.001f, 1-PIXEL*3-0.001f, 1-PIXEL*6, 1-PIXEL*3-0.001f, multiBufferSource, poseStack, packedLight, true);
        }
    }
}
