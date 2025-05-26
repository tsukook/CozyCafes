package io.tsukook.github.cozycafes.systems;

import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.function.Function;

public class PerLevelTickerManager<T extends PerLevelTicker> {
    private final HashMap<Level, T> instances = new HashMap<>();
    private final Function<Level, T> constructor;

    public PerLevelTickerManager(Function<Level, T> constructor) {
        this.constructor = constructor;
    }

    public void registerLevel(Level level) {
        instances.put(level, constructor.apply(level));
    }

    public void tickLevel(Level level) {
        instances.get(level).tick();
    }

    public T getTicker(Level level) {
        return instances.get(level);
    }
}
