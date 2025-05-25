package io.tsukook.github.cozycafes.networking;

import io.netty.buffer.ByteBuf;
import io.tsukook.github.cozycafes.CozyCafes;
import io.tsukook.github.cozycafes.systems.dandelion.DandelionSeed;
import io.tsukook.github.cozycafes.systems.dandelion.DandelionState;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;

public record DandelionSeedStatePayload(DandelionState state) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<DandelionSeedStatePayload> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(CozyCafes.MODID, "dandelion_seed_state"));

    public static final StreamCodec<ByteBuf, DandelionSeedStatePayload> STREAM_CODEC = StreamCodec.composite(
            DandelionState.STREAM_CODEC,
            DandelionSeedStatePayload::state,
            DandelionSeedStatePayload::new
    );

    public DandelionSeedStatePayload(long timestamp, HashMap<Integer, DandelionSeed> seeds) {
        this(new DandelionState(timestamp, seeds));
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
