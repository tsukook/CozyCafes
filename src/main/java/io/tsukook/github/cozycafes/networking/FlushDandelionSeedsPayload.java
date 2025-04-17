package io.tsukook.github.cozycafes.networking;

import io.netty.buffer.ByteBuf;
import io.tsukook.github.cozycafes.CozyCafes;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record FlushDandelionSeedsPayload() implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<FlushDandelionSeedsPayload> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(CozyCafes.MODID, "clear_dandelion_seeds"));

    public static final StreamCodec<ByteBuf, FlushDandelionSeedsPayload> STREAM_CODEC = new StreamCodec<ByteBuf, FlushDandelionSeedsPayload>() {
        @Override
        public FlushDandelionSeedsPayload decode(ByteBuf buffer) {
            return new FlushDandelionSeedsPayload();
        }

        @Override
        public void encode(ByteBuf buffer, FlushDandelionSeedsPayload value) {
            // do nothing
        }
    };

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
