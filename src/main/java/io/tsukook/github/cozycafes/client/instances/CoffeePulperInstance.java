package io.tsukook.github.cozycafes.client.instances;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CoffeePulperInstance {
    private float cylinderRotation = 0;
    private float angularVelocity = 0;
    private final float SPEED = 0.1f;

    public void setSpin(float spin) {
        angularVelocity = spin;
    }

    public void advance(float partialTick) {
        cylinderRotation += angularVelocity * (1 + partialTick) * SPEED;
    }

    public float getCylinderRotation() {
        return cylinderRotation;
    }
}
