package io.tsukook.github.cozycafes.systems.dandelion;

import org.joml.Vector3f;

public class DandelionSeed {
    public Vector3f pos;
    public Vector3f velocity;

    public DandelionSeed(Vector3f pos, Vector3f velocity) {
        this.pos = pos;
        this.velocity = velocity;
    }
}
