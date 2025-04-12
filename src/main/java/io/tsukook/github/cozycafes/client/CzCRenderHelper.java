package io.tsukook.github.cozycafes.client;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class CzCRenderHelper {

    public static void renderVertexVTCL(VertexConsumer buffer, float x, float y, float z, float u, float v, int packedLight) {
        renderVertexVTCL(buffer, x, y, z, u, v, packedLight, 0xffffffff);
    }

    public static void renderVertexVTCL(VertexConsumer buffer, float x, float y, float z, float u, float v, int packedLight, int color) {
        buffer.addVertex(x, y, z)
                .setUv(u, v)
                .setColor(color)
                .setLight(packedLight);
    }

    public static void renderBillboardQuad(VertexConsumer buffer, Camera camera, Vector3f pos, Vector2f size, Vector2f uv0, Vector2f uv1, int packedLight) {
        renderBillboardQuad(buffer, camera, pos, uv0, uv1, size, packedLight, 0xffffffff);
    }

    public static void renderBillboardQuad(VertexConsumer buffer, Camera camera, Vector3f pos, Vector2f size, Vector2f uv0, Vector2f uv1, int packedLight, int color) {
        Vector3f dir = camera.getPosition().toVector3f().sub(pos);
        Vector3f right = new Vector3f(0, 1, 0).cross(dir).normalize();

        if (right.length() < 0.001) {
            right = dir.cross(new Vector3f(0, 0, 1));
            if (right.length() < 0.001) right = new Vector3f(1, 0, 0);
        }

        Vector3f up = dir.cross(right).normalize();
        Vector3f right_offset = right.mul(size.x/2);
        Vector3f up_offset = up.mul(size.y / 2);

        Vector3f top_right = pos.add(right_offset).add(up_offset);
        Vector3f top_left = pos.sub(right_offset).add(up_offset);
        Vector3f bottom_left = pos.sub(right_offset).sub(up_offset);
        Vector3f bottom_right = pos.add(right_offset).sub(up_offset);

        // FIXME: Potential issue with vertex winding
        renderVertexVTCL(buffer, top_right.x, top_right.y, top_right.z, uv1.x, uv1.y, packedLight, color);
        renderVertexVTCL(buffer, bottom_right.x, bottom_right.y, bottom_right.z, uv1.x, uv0.y, packedLight, color);
        renderVertexVTCL(buffer, bottom_left.x, bottom_left.y, bottom_left.z, uv0.x, uv0.y, packedLight, color);
        renderVertexVTCL(buffer, top_left.x, top_left.y, top_left.z, uv0.x, uv1.y, packedLight, color);
    }
}
