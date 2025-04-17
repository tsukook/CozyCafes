package io.tsukook.github.cozycafes.networking;

import io.netty.buffer.ByteBuf;
import io.tsukook.github.cozycafes.CozyCafes;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector3f;

public record SynchronizeDandelionSeedPayload(Vector3f pos, Vector3f velocity) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SynchronizeDandelionSeedPayload> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(CozyCafes.MODID, "synchronize_dandelion_seeds"));

    public static final StreamCodec<ByteBuf, SynchronizeDandelionSeedPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VECTOR3F,
            SynchronizeDandelionSeedPayload::pos,
            ByteBufCodecs.VECTOR3F,
            SynchronizeDandelionSeedPayload::velocity,
            SynchronizeDandelionSeedPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
