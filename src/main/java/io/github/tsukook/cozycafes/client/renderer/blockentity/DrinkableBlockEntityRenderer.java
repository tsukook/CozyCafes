package io.github.tsukook.cozycafes.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.tsukook.cozycafes.block.entity.DrinkableBlockEntity;
import io.github.tsukook.cozycafes.client.renderer.RenderHelper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class DrinkableBlockEntityRenderer implements BlockEntityRenderer<DrinkableBlockEntity> {
    private final float x0, y0, z0, x1, y1, z1, max_height;

    public DrinkableBlockEntityRenderer(BlockEntityRendererProvider.Context context, float x0, float y0, float z0, float x1, float y1, float z1, float max_height) {
        this.x0 = x0 / 16;
        this.y0 = y0 / 16;
        this.z0 = z0 / 16;
        this.x1 = x1 / 16;
        this.y1 = y1 / 16;
        this.z1 = z1 / 16;
        this.max_height = max_height / 16;
    }

    @Override
    public void render(DrinkableBlockEntity drinkableBlockEntity, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int overlay) {
        if (drinkableBlockEntity.isEmpty())
            return;

        float height = (float) drinkableBlockEntity.getAmount() / drinkableBlockEntity.getCapacity();
        RenderHelper.drawFluidQuad(drinkableBlockEntity.getFluid(), false, multiBufferSource, poseStack, x0, y0 + height * max_height, z0, x1, y1 + height * max_height, z1, false, light);
    }
}
