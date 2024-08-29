package io.github.tsukook.cozycafes.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

public class RenderHelper {
    public static void drawVertex(VertexConsumer vertexConsumer, PoseStack poseStack, float x, float y, float z, float u, float v, int packedLight, int color) {
        vertexConsumer.vertex(poseStack.last().pose(), x, y, z)
                .color(color)
                .uv(u, v)
                .uv2(packedLight)
                .normal(1, 0, 0)
                .endVertex();
    }

    public static void drawQuad(VertexConsumer vertexConsumer, PoseStack poseStack, float x0, float y0, float z0, float x1, float y1, float z1, float u0, float v0, float u1, float v1, int packedLight, int color) {
        drawVertex(vertexConsumer, poseStack, x0, y0, z0, u0, v0, packedLight, color);
        drawVertex(vertexConsumer, poseStack, x0, y1, z1, u0, v1, packedLight, color);
        drawVertex(vertexConsumer, poseStack, x1, y1, z1, u1, v1, packedLight, color);
        drawVertex(vertexConsumer, poseStack, x1, y0, z0, u1, v0, packedLight, color);
    }
}
