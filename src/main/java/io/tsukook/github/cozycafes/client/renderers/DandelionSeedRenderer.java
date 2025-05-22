package io.tsukook.github.cozycafes.client.renderers;

import com.mojang.blaze3d.vertex.VertexConsumer;
import io.tsukook.github.cozycafes.systems.dandelion.DandelionSeed;
import net.minecraft.client.Camera;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;

public class DandelionSeedRenderer {
    private static void renderVertex(VertexConsumer buffer, Quaternionf quaternionf, float x, float y, float z, float xOffset, float yOffset, float quadSize, float u, float v, int packedLight) {
        renderVertex(buffer, quaternionf, x, y, z, xOffset, yOffset, quadSize, u, v, packedLight, 0xffffffff);
    }

    private static void renderVertex(VertexConsumer buffer, Quaternionf quaternionf, float x, float y, float z, float xOffset, float yOffset, float quadSize, float u, float v, int packedLight, int color) {
        Vector3f vector3f = new Vector3f(xOffset, yOffset, 0.0f).rotate(quaternionf).mul(quadSize).add(x, y, z);
        buffer.addVertex(vector3f.x(), vector3f.y(), vector3f.z())
                .setUv(u, v)
                .setColor(color)
                .setLight(packedLight);
    }

    private static void renderSingle(VertexConsumer buffer, Vector3f pos, Camera camera, float partialTicks) {
        renderSingle(buffer, pos, camera, partialTicks, 0xffffffff);
    }

    private static void renderSingle(VertexConsumer buffer, Vector3f pos, Camera camera, float partialTicks, int color) {
        Quaternionf quaternionf = new Quaternionf();
        SingleQuadParticle.FacingCameraMode.LOOKAT_XYZ.setRotation(quaternionf, camera, partialTicks);

        Vec3 cameraPosition = camera.getPosition();

        float x, y, z;
        x = (float) (pos.x() - cameraPosition.x());
        y = (float) (pos.y() - cameraPosition.y());
        z = (float) (pos.z() - cameraPosition.z());

        int packedLight = 255; // TODO: Change

        // TODO: If you ever plan on having any other particles, you will need to change the UVs to some sprite system idk
        float u0, v0, u1, v1;
        u0 = 0;
        v0 = 0;
        u1 = 1;
        v1 = 1;

        renderVertex(buffer, quaternionf, x, y, z, 1.0f, -1.0f, 1.0f, u1, v1, packedLight, color);
        renderVertex(buffer, quaternionf, x, y, z, 1.0f, 1.0f, 1.0f, u1, v0, packedLight, color);
        renderVertex(buffer, quaternionf, x, y, z, -1.0f, 1.0f, 1.0f, u0, v0, packedLight, color);
        renderVertex(buffer, quaternionf, x, y, z, -1.0f, -1.0f, 1.0f, u0, v1, packedLight, color);
    }

    public static void render(VertexConsumer buffer, Camera camera, float partialTicks) {

    }

    public static void addNewState(long timestamp, ArrayList<DandelionSeed> seeds) {

    }
}
