package io.tsukook.github.cozycafes.systems.dandelion;

import org.joml.Vector3f;

public class DandelionPhysics {
    private static final float GRAVITY = -9.81f;
    private static final float DRAG = .5f;

    public static void tickSeed(DandelionSeed original) {
        tickSeed(original, 1f / 20);
    }
    public static void tickSeed(DandelionSeed original, float delta) {
        Vector3f acceleration = new Vector3f(-DRAG * original.velocity.x, GRAVITY - DRAG * original.velocity.y, -DRAG * original.velocity.z);
        original.velocity.add(acceleration.mul(delta));
        original.pos.add(original.velocity.mul(delta, new Vector3f()));
    }
}
