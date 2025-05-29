package io.tsukook.github.cozycafes.systems.dandelion;

import org.joml.Vector3f;

public class DandelionSeed {
    private static int nextId = 0;
    private int id;
    public Vector3f pos;
    public Vector3f velocity;

    // Stinky!
    public Vector3f jerk = new Vector3f();
    public Vector3f boost = new Vector3f(0, 0.375f, 0);
    public int boostTicks;

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
        this.pos = new Vector3f(pos);
        this.velocity = new Vector3f(velocity);
    }

    public int getId() {
        return id;
    }
}
