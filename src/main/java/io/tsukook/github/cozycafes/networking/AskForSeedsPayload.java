package io.tsukook.github.cozycafes.networking;

import io.netty.buffer.ByteBuf;
import io.tsukook.github.cozycafes.CozyCafes;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record AskForSeedsPayload() implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<AskForSeedsPayload> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(CozyCafes.MODID, "ask_for_seeds"));

    public static final StreamCodec<ByteBuf, AskForSeedsPayload> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public AskForSeedsPayload decode(ByteBuf buffer) {
            return new AskForSeedsPayload();
        }

        @Override
        public void encode(ByteBuf buffer, AskForSeedsPayload value) {
            // do nothing
        }
    };

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
