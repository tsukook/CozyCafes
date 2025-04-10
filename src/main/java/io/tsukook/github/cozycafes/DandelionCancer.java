package io.tsukook.github.cozycafes;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;

// FIXME: Name is a placeholder
public class DandelionCancer {
    private final ServerLevel level;
    private boolean ticked = false;

    public DandelionCancer(ServerLevel level) {
        this.level = level;
        this.level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("Created cancer at level " + level.dimension().location().toString()), false);
    }

    public void tick() {
        if (!ticked) {
            level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("Ticked cancer at level " + level.dimension().location().toString()), false);
            ticked = true;
        }
    }
}
