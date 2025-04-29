package io.tsukook.github.cozycafes.networking;

import io.tsukook.github.cozycafes.systems.dandelion.DandelionCancerManager;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.Objects;

public class CzCServerPayloadHandler {
    public static void handle(final AskForSeedsPayload payload, final IPayloadContext context) {
        Objects.requireNonNull(DandelionCancerManager.getCancer(context.player().level())).queuePlayerForSync(context.player());
    }
}
