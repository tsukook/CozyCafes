package io.tsukook.github.cozycafes.networking;

import io.tsukook.github.cozycafes.blocks.entities.CoffeePulperBlockEntity;
import io.tsukook.github.cozycafes.client.renderers.DandelionSeedRenderer;
import io.tsukook.github.cozycafes.systems.dandelion.DandelionSeed;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class CzCClientPayloadHandler {
    public static void handlePulperSpinPayload(final PulperSpinPayload payload, final IPayloadContext context) {
        Level level = Minecraft.getInstance().level;
        BlockEntity blockEntity = level.getBlockEntity(payload.pos());
        if (blockEntity instanceof CoffeePulperBlockEntity coffeePulperBlockEntity) {
            coffeePulperBlockEntity.coffeePulperInstance.setSpin(payload.spinSpeed());
        }
    }

    public static void handleSynchronizeDandelionSeedsPayload(final SynchronizeDandelionSeedPayload payload, final IPayloadContext context) {
        context.enqueueWork(() -> DandelionSeedRenderer.addSeed(new DandelionSeed(payload.id(), payload.pos(), payload.velocity())));
    }

    public static void handleFlushDandelionSeedsPayload(final FlushDandelionSeedsPayload payload, final IPayloadContext context) {
        context.enqueueWork(DandelionSeedRenderer::flush);
    }
}
