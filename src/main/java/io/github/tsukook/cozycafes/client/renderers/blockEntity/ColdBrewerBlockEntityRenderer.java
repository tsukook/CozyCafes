package io.github.tsukook.cozycafes.client.renderers.blockEntity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.foundation.fluid.FluidRenderer;
import io.github.tsukook.cozycafes.blocks.entities.ColdBrewerBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class ColdBrewerBlockEntityRenderer implements BlockEntityRenderer<ColdBrewerBlockEntity> {
    public ColdBrewerBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}
    private static final float PIXEL = 1f/16f;

    @Override
    public void render(ColdBrewerBlockEntity coldBrewerBlockEntity, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay) {
        if(!coldBrewerBlockEntity.getFluid().isEmpty()) {
            FluidStack fluidStack = coldBrewerBlockEntity.getFluid().getFluid();

            if (!fluidStack.isEmpty()) {
                FluidRenderer.renderFluidBox(fluidStack, PIXEL*3+0.001f, 0.0001f, PIXEL*3+0.001f, 1-PIXEL*3-0.001f, (1-PIXEL*6) * ((float) fluidStack.getAmount() / 1000f), 1-PIXEL*3-0.001f, multiBufferSource, poseStack, packedLight, true);
            }
        }

        if (coldBrewerBlockEntity.hasItem()) {
            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            ItemStack itemStack = coldBrewerBlockEntity.getItem();

            poseStack.pushPose();
            float scale = 1.4f;
            poseStack.scale(scale, scale, scale);
            poseStack.translate(PIXEL*5.5, 0.001f+PIXEL/2, PIXEL*4);
            poseStack.mulPose(Axis.XP.rotationDegrees(90));
            itemRenderer.renderStatic(itemStack, ItemDisplayContext.GROUND, packedLight, packedOverlay, poseStack, multiBufferSource, coldBrewerBlockEntity.getLevel(), 0);
            poseStack.popPose();
        }
    }
}
