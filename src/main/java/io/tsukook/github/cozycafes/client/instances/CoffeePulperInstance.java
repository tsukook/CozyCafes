package io.tsukook.github.cozycafes.client.instances;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CoffeePulperInstance {
    private float cylinderRotation = 0;
    private float angularVelocity = 0;
    private final float DECAY = 0.98f;

    public void addSpin(float angularVelocity) {
        this.angularVelocity += angularVelocity;
    }

    public void advance(float partialTick) {
        angularVelocity *= DECAY;
        cylinderRotation += angularVelocity * (1 + partialTick);
        //cylinderRotation += 0.1f;
    }

    public float getCylinderRotation() {
        return cylinderRotation;
    }
}
