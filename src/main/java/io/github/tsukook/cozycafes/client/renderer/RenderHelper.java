package io.github.tsukook.cozycafes.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;

public class RenderHelper {
    public static void drawVertex(VertexConsumer vertexConsumer, PoseStack poseStack, float x, float y, float z, float u, float v, int packedLight, int color) {
        vertexConsumer.vertex(poseStack.last().pose(), x, y, z)
                .color(color)
                .uv(u, v)
                .uv2(packedLight)
                .normal(1, 0, 0)
                .endVertex();
    }

    public static void drawQuad(VertexConsumer vertexConsumer, PoseStack poseStack, float x0, float y0, float z0, float x1, float y1, float z1, boolean magic, float u0, float v0, float u1, float v1, int packedLight, int color) {
        if (magic) {
            drawVertex(vertexConsumer, poseStack, x0, y0, z0, u0, v0, packedLight, color);
            drawVertex(vertexConsumer, poseStack, x1, y1, z0, u0, v1, packedLight, color);
            drawVertex(vertexConsumer, poseStack, x1, y1, z1, u1, v1, packedLight, color);
            drawVertex(vertexConsumer, poseStack, x0, y0, z1, u1, v0, packedLight, color);
        } else {
            drawVertex(vertexConsumer, poseStack, x0, y0, z0, u0, v0, packedLight, color);
            drawVertex(vertexConsumer, poseStack, x0, y1, z1, u0, v1, packedLight, color);
            drawVertex(vertexConsumer, poseStack, x1, y1, z1, u1, v1, packedLight, color);
            drawVertex(vertexConsumer, poseStack, x1, y0, z0, u1, v0, packedLight, color);
        }
    }

    public static void drawBox(VertexConsumer vertexConsumer, PoseStack poseStack, float x0, float y0, float z0, float x1, float y1, float z1, float u0, float v0, float u1, float v1, int packedLight, int color) {
        drawQuad(vertexConsumer, poseStack, x0, y0, z0, x1, y1, z0, false, u0, v0, u1, v1, packedLight, color); // red positive z
        drawQuad(vertexConsumer, poseStack, x1, y0, z1, x0, y1, z1, false, u0, v0, u1, v1, packedLight, color); // green negative z
        drawQuad(vertexConsumer, poseStack, x0, y0, z1, x0, y1, z0, true, u0, v0, u1, v1, packedLight, color); // blue positive x
        drawQuad(vertexConsumer, poseStack, x1, y0, z0, x1, y1, z1, true, u0, v0, u1, v1, packedLight, color); // purple negative x
        drawQuad(vertexConsumer, poseStack, x0, y0, z1, x1, y0, z0, false, u0, v0, u1, v1, packedLight, color); // yellow y positive y
        drawQuad(vertexConsumer, poseStack, x0, y1, z0, x1, y1, z1, false, u0, v0, u1, v1, packedLight, color); // cyan y negative y
    }

    public static void drawFluidQuad(FluidStack fluidStack, boolean flowing, MultiBufferSource multiBufferSource, PoseStack poseStack, float x0, float y0, float z0, float x1, float y1, float z1, boolean magic, int packedLight) {
        IClientFluidTypeExtensions fluidTypeExtensions = IClientFluidTypeExtensions.of(fluidStack.getFluid());

        ResourceLocation fluidTexture;
        if (flowing) {
            fluidTexture = fluidTypeExtensions.getFlowingTexture(fluidStack);
        } else {
            fluidTexture = fluidTypeExtensions.getStillTexture(fluidStack);
        }

        if (fluidTexture == null)
            return;

        FluidState fluidState = fluidStack.getFluid().defaultFluidState();

        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(fluidTexture);
        int tintColor = fluidTypeExtensions.getTintColor(fluidStack);
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(ItemBlockRenderTypes.getRenderLayer(fluidState));

        drawQuad(vertexConsumer, poseStack, x0, y0, z0, x1, y1, z1, magic, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), packedLight, tintColor);
    }
}
