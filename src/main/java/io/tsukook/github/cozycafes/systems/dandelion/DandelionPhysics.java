package io.tsukook.github.cozycafes.systems.dandelion;

import org.joml.Vector3f;

public class DandelionPhysics {
    private static final float GRAVITY = -9.81f;
    private static final float DRAG = .95f;
    private static final float DELTA = 1/20f;

    public static DandelionSeed tickSeed(DandelionSeed original) {
        Vector3f acceleration = new Vector3f(-DRAG * original.velocity.x, GRAVITY - DRAG * original.velocity.y, -DRAG * original.velocity.z);
        original.velocity.add(acceleration.mul(DELTA));
        original.pos.add(original.velocity.mul(DELTA, new Vector3f()));
        return original;
    }
}
