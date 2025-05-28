package io.tsukook.github.cozycafes.systems.leveltickscheduler;

import net.minecraft.server.level.ServerLevel;

import java.util.function.Consumer;

public class ScheduledEvent {
    public int ticks;
    public Consumer<ServerLevel> consumer;

    public ScheduledEvent(int ticks, Consumer<ServerLevel> consumer) {
        this.ticks = ticks;
        this.consumer = consumer;
    }
}
