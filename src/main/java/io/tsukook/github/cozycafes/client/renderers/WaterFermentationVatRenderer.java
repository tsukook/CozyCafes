package io.tsukook.github.cozycafes.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import io.tsukook.github.cozycafes.blocks.entities.WaterFermentationVatBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class WaterFermentationVatRenderer implements BlockEntityRenderer<WaterFermentationVatBlockEntity> {
    public WaterFermentationVatRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(WaterFermentationVatBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {

    }
}
