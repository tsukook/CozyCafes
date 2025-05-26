package io.tsukook.github.cozycafes.registers;

import io.tsukook.github.cozycafes.systems.PerLevelTicker;
import io.tsukook.github.cozycafes.systems.PerLevelTickerManager;
import io.tsukook.github.cozycafes.systems.dandelion.DandelionCancer;
import io.tsukook.github.cozycafes.systems.wind.Wind;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.function.Function;

public class PerLevelTickerManagerRegistry {
    public static final ArrayList<PerLevelTickerManager<?>> MANAGERS = new ArrayList<>();

    // Order is important
    public static final PerLevelTickerManager<Wind> WIND_MANAGER = register(Wind::new);
    public static final PerLevelTickerManager<DandelionCancer> DANDELION_CANCER_MANAGER = register(DandelionCancer::new);

    private static <T extends PerLevelTicker> PerLevelTickerManager<T> register(Function<Level, T> constructor) {
        PerLevelTickerManager<T> manager = new PerLevelTickerManager<>(constructor);
        MANAGERS.add(manager);
        return manager;
    }
}
