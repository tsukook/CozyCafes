package io.github.tsukook.cozycafes.client.renderers.blockEntity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.fluid.FluidRenderer;
import io.github.tsukook.cozycafes.blocks.entities.SyrupPumpBlockEntity;
import io.github.tsukook.cozycafes.items.MugItem;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraftforge.fluids.FluidStack;

public class SyrupPumpBlockEntityRenderer implements BlockEntityRenderer<SyrupPumpBlockEntity> {
    public SyrupPumpBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}
    private static final float PIXEL = 1f/16f;

    @Override
    public void render(SyrupPumpBlockEntity syrupPumpBlockEntity, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int i1) {
        FluidStack fluidStack = syrupPumpBlockEntity.getFluid();
        if (!fluidStack.isEmpty()) {
            FluidRenderer.renderFluidBox(fluidStack, PIXEL*6+0.001f, 0.001f, PIXEL*6+0.001f, PIXEL*10-0.001f, PIXEL*(12f * fluidStack.getAmount() / MugItem.CAPACITY)-0.001f, PIXEL*10-0.001f, multiBufferSource, poseStack, light, false);
        }
    }
}
