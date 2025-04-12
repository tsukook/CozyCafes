package io.tsukook.github.cozycafes.client.renderers;

import com.mojang.blaze3d.vertex.VertexConsumer;
import io.tsukook.github.cozycafes.client.CzCRenderHelper;
import io.tsukook.github.cozycafes.systems.dandelion.DandelionSeed;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import org.joml.Vector2f;

import java.util.ArrayList;

public class DandelionSeedRenderer {
    public static ArrayList<DandelionSeed> seeds = new ArrayList<DandelionSeed>(256);

    public static void render(VertexConsumer buffer, Camera camera) {
        for (DandelionSeed seed : seeds) {
            CzCRenderHelper.renderBillboardQuad(buffer, camera, seed.pos, new Vector2f(1, 1), new Vector2f(0, 0), new Vector2f(1, 1), 255);
        }
    }
}
