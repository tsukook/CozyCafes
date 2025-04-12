package io.tsukook.github.cozycafes.networking;

import io.netty.buffer.ByteBuf;
import io.tsukook.github.cozycafes.CozyCafes;
import io.tsukook.github.cozycafes.systems.dandelion.DandelionCancer;
import io.tsukook.github.cozycafes.systems.dandelion.DandelionSeed;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;

public record SynchronizeDandelionSeedsPayload(ArrayList<DandelionSeed> seeds) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SynchronizeDandelionSeedsPayload> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(CozyCafes.MODID, "synchronize_dandelion_seeds"));

    public static final StreamCodec<ByteBuf, SynchronizeDandelionSeedsPayload> STREAM_CODEC = StreamCodec.composite(
            DandelionCancer.DANDELION_CANCER_CODEC,
            SynchronizeDandelionSeedsPayload::seeds,
            SynchronizeDandelionSeedsPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
