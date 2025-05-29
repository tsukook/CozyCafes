package io.tsukook.github.cozycafes.systems.dandelion;

import org.joml.Vector2d;
import org.joml.Vector3f;

// TODO: move all of this into the DandelionSeed class
public class DandelionPhysics {
    private static final float GRAVITY = -9.81f;
    private static final float DRAG = 0.7f;
    private static final float DELTA = 1/20f;

    public static DandelionSeed tickSeed(DandelionCancer cancer, DandelionSeed original) {
        Vector2d windForce = cancer.getWindForce();
        original.velocity.x += (float) windForce.x;
        original.velocity.z += (float) windForce.y;

        if (original.boostTicks > 0) {
            original.jerk.add(original.boost);
            original.boostTicks--;
        } else {
            original.jerk.sub(original.boost).max(new Vector3f(0, 0, 0));
        }

        Vector3f acceleration = new Vector3f(-DRAG * original.velocity.x, GRAVITY - 20 * DRAG * original.velocity.y, -DRAG * original.velocity.z);
        acceleration.add(original.jerk);
        original.velocity.add(acceleration.mul(DELTA));
        original.pos.add(original.velocity.mul(DELTA, new Vector3f()));
        return original;
    }
}
