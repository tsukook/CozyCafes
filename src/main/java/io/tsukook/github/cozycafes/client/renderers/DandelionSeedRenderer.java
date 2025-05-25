package io.tsukook.github.cozycafes.client.renderers;

import com.mojang.blaze3d.vertex.VertexConsumer;
import io.tsukook.github.cozycafes.systems.dandelion.DandelionState;
import net.minecraft.client.Camera;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Objects;

public class DandelionSeedRenderer {
    // Render latest in like red or smth
    private static final ArrayList<DandelionState> stateBuffer = new ArrayList<>(5);
    private static final long DELAY_MILLIS = 100;

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
        long targetTime = System.currentTimeMillis() - DELAY_MILLIS;
        float alpha = -1;
        DandelionState prev = null;
        DandelionState next = null;
        for (int i = 0; i < stateBuffer.size() - 1; i++) {
            prev = stateBuffer.get(i);
            next = stateBuffer.get(i + 1);
            if (prev.timestampMillis() <= targetTime && targetTime < next.timestampMillis()) {
                alpha = (float) (targetTime - prev.timestampMillis()) / (next.timestampMillis() - prev.timestampMillis());
                break;
            }
        }

        if (prev == null || next == null)
            return;

        // Yuck!
        DandelionState finalNext = next;
        float finalAlpha = alpha;
        prev.seeds().forEach((id, seed) -> {
            if (finalNext.seeds().containsKey(id)) {
                Vector3f interpolated = new Vector3f();
                seed.pos.lerp(finalNext.seeds().get(id).pos, finalAlpha, interpolated);

                renderSingle(buffer, interpolated, camera, partialTicks);
            }
        });

        /*next.seeds().forEach((id, seed) -> {
            renderSingle(buffer, seed.pos, camera, partialTicks, 0xffff0000);
        });*/
    }

    public static void addNewState(DandelionState state) {
        stateBuffer.add(state);
        if (stateBuffer.size() >= 5) {
            stateBuffer.removeFirst();
        }
    }
}
