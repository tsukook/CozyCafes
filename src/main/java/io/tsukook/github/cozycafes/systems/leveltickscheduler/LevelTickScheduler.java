package io.tsukook.github.cozycafes.systems.leveltickscheduler;

import io.tsukook.github.cozycafes.systems.PerLevelTicker;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.function.Consumer;

public class LevelTickScheduler implements PerLevelTicker {
    private final ServerLevel level;
    private final ArrayList<ScheduledEvent> scheduledEvents = new ArrayList<>();

    public LevelTickScheduler(Level level) {
        this.level = (ServerLevel) level;
    }

    @Override
    public void tick() {
        scheduledEvents.removeIf(scheduledEvent -> {
            if (scheduledEvent.ticks <= 0) {
                scheduledEvent.consumer.accept(level);
                return true;
            }
            return false;
        });
        scheduledEvents.forEach(scheduledEvent -> scheduledEvent.ticks--);
    }

    public void schedule(int ticks, Consumer<ServerLevel> consumer) {
        scheduledEvents.add(new ScheduledEvent(ticks, consumer));
    }
}
