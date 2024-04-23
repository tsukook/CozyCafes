package io.github.tsukook.cozycafes.client.renderers.blockEntity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.fluid.FluidRenderer;
import io.github.tsukook.cozycafes.blocks.entities.ColdBrewerBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraftforge.fluids.FluidStack;

public class ColdBrewerBlockEntityRenderer implements BlockEntityRenderer<ColdBrewerBlockEntity> {
    public ColdBrewerBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(ColdBrewerBlockEntity coldBrewerBlockEntity, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay) {
        if(coldBrewerBlockEntity.getFluid().isEmpty()) {
            return;
        }

        FluidStack fluidStack = coldBrewerBlockEntity.getFluid().getFluid();

        if (!fluidStack.isEmpty()) {
            FluidRenderer.renderFluidBox(fluidStack, 0, 0, 0, 1, 1, 1, multiBufferSource, poseStack, packedLight, true);
        }
    }
}
