package io.tsukook.github.cozycafes.networking;

import io.tsukook.github.cozycafes.blocks.entities.CoffeePulperBlockEntity;
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
}
