package io.tsukook.github.cozycafes.systems.dandelion;

import org.joml.Vector3f;

public class DandelionSeed {
    private static int nextId = 0;
    private int id;
    public Vector3f pos;
    public Vector3f velocity;

    public DandelionSeed(DandelionSeed original) {
        this(original.getId(), new Vector3f(original.pos), new Vector3f(original.velocity));
    }

    public DandelionSeed() {
        this(new Vector3f(), new Vector3f());
    }

    public DandelionSeed(Vector3f pos) {
        this(pos, new Vector3f());
    }

    public DandelionSeed(Vector3f pos, Vector3f velocity) {
        this(nextId++, pos, velocity);
    }

    public DandelionSeed(int id, Vector3f pos, Vector3f velocity) {
        this.id = id;
        this.pos = pos;
        this.velocity = velocity;
    }

    public int getId() {
        return id;
    }
}
