package io.github.tsukook.cozycafes.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.tsukook.cozycafes.block.entity.FluidContainerBlockEntity;
import io.github.tsukook.cozycafes.client.renderer.RenderHelper;
import io.github.tsukook.cozycafes.client.renderer.Shape;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;

public class FluidContainerBlockEntityRenderer implements BlockEntityRenderer<FluidContainerBlockEntity> {
    private Shape fluidShape;

    public FluidContainerBlockEntityRenderer(BlockEntityRendererProvider.Context context, Shape fluidShape) {
        this.fluidShape = fluidShape;
    }

    @Override
    public void render(FluidContainerBlockEntity fluidContainerBlockEntity, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int i1) {
        FluidStack fluidStack = fluidContainerBlockEntity.getFluidTank().getFluid();
        if (fluidStack.isEmpty())
            return;

        Level level = fluidContainerBlockEntity.getLevel();
        if (level == null)
            return;

        BlockPos blockPos = fluidContainerBlockEntity.getBlockPos();

        IClientFluidTypeExtensions fluidTypeExtensions = IClientFluidTypeExtensions.of(fluidStack.getFluid());
        ResourceLocation stillTexture = fluidTypeExtensions.getStillTexture(fluidStack);
        if (stillTexture == null)
            return;

        FluidState fluidState = fluidStack.getFluid().defaultFluidState();

        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(stillTexture);
        int tintColor = fluidTypeExtensions.getTintColor(fluidStack.getFluid().defaultFluidState(), level, blockPos);

        VertexConsumer builder = multiBufferSource.getBuffer(ItemBlockRenderTypes.getRenderLayer(fluidState));

        float[] quads = fluidShape.getQuads();
        for (int i = 0; i < quads.length; i += 6) {
            float x0 = quads[i];
            float y0 = quads[i + 1];
            float z0 = quads[i + 2];

            float x1 = quads[i + 3];
            float y1 = quads[i + 4];
            float z1 = quads[i + 5];

            RenderHelper.drawQuad(builder, poseStack, x0, y0, z0, x1, y1, z1, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), light, tintColor);
        }
    }
}
