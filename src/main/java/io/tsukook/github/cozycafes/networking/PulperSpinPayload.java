package io.tsukook.github.cozycafes.networking;

import io.netty.buffer.ByteBuf;
import io.tsukook.github.cozycafes.CozyCafes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record PulperSpinPayload(BlockPos pos, float spinSpeed) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<PulperSpinPayload> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(CozyCafes.MODID, "float_payload"));

    public static final StreamCodec<ByteBuf, PulperSpinPayload> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            PulperSpinPayload::pos,
            ByteBufCodecs.FLOAT,
            PulperSpinPayload::spinSpeed,
            PulperSpinPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
