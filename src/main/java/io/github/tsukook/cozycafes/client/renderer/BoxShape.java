package io.github.tsukook.cozycafes.client.renderer;

public class BoxShape implements Shape {
    private float x;
    private float y;
    private float z;
    private float width;
    private float height;
    private float length;

    public BoxShape(float x, float y, float z, float width, float height, float length) {
        this.x = x / 16;
        this.y = y / 16;
        this.z = z / 16;
        this.width = width / 16;
        this.height = height / 16;
        this.length = length / 16;
    }

    @Override
    public float[] getQuads() {
        float x0 = x;
        float y0 = y;
        float z0 = z;
        float x1 = x + width;
        float y1 = y + height;
        float z1 = z + length;

        float[] quads = {
            x0, y0, z0,
            x1, y1, z0,

            x0, y0, z1,
            x1, y1, z1,

            x0, y0, z0,
            x0, y1, z1,

            x0, y1, z0,
            x1, y1, z1,

            x0, y0, z0,
            x1, y0, z1,

            x1, y0, z0,
            x1, y1, z1
        };
        return quads;
    }
}
