package io.tsukook.github.cozycafes.systems.wind;

import io.tsukook.github.cozycafes.systems.PerLevelTicker;
import net.minecraft.world.level.Level;
import org.joml.Vector2d;

public class Wind implements PerLevelTicker {
    private final Vector2d windDirection = new Vector2d();
    private double x = 0;
    private double y = 0;
    public double changeSpeed = 0.001;
    public double windPower = 2;

    private static double fade(double t) {
        return 6 * Math.pow(t, 5) - 15 * Math.pow(t, 4) + 10 * Math.pow(t, 3);
    }

    private static final int PERM_SIZE = 256;
    private static final double[] gradients_x = new double[PERM_SIZE];
    private static final double[] gradients_y = new double[PERM_SIZE];

    private static double perlin_1d(double[] gradients, double x) {
        int i = (int)Math.floor(x);
        double t = x - i;
        double s = fade(t);

        int i_left = i & (PERM_SIZE - 1);
        int i_right = (i_left + 1) & (PERM_SIZE - 1);

        double g_left = gradients[i_left];
        double g_right = gradients[i_right];

        double a = g_left * t;
        double b = g_right * (t - 1);

        return a + s * (b - a);
    }

    public Wind(Level level) {
        for (int i = 0; i < PERM_SIZE; i++) {
            gradients_x[i] = level.random.nextDouble();
            gradients_y[i] = level.random.nextDouble();
        }
    }

    @Override
    public void tick() {
        x += changeSpeed;
        y += changeSpeed;
        windDirection.x = perlin_1d(gradients_x, x);
        windDirection.y = perlin_1d(gradients_y, y);
    }

    public Vector2d getWindForce() {
        return new Vector2d(windDirection).mul(windPower);
    }
}
