package io.tsukook.github.cozycafes.events;

import io.tsukook.github.cozycafes.CozyCafes;
import io.tsukook.github.cozycafes.networking.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = CozyCafes.MODID, bus = EventBusSubscriber.Bus.MOD)
public class CzCModEventBusSubscriber {
    @SubscribeEvent
    public static void registerPayloadHandlers(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playToClient(
                PulperSpinPayload.TYPE,
                PulperSpinPayload.STREAM_CODEC,
                CzCClientPayloadHandler::handle
        );

        registrar.playToClient(
                DandelionSeedStatePayload.TYPE,
                DandelionSeedStatePayload.STREAM_CODEC,
                CzCClientPayloadHandler::handle
        );
    }
}
