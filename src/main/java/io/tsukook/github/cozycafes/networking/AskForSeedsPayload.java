package io.tsukook.github.cozycafes.networking;

import io.netty.buffer.ByteBuf;
import io.tsukook.github.cozycafes.CozyCafes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.nio.charset.Charset;
import java.util.UUID;

public record AskForSeedsPayload(UUID uuid, ResourceLocation levelResource) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<AskForSeedsPayload> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(CozyCafes.MODID, "ask_for_seeds"));

    public static final StreamCodec<ByteBuf, AskForSeedsPayload> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public AskForSeedsPayload decode(ByteBuf buffer) {
            return new AskForSeedsPayload(FriendlyByteBuf.readUUID(buffer), ResourceLocation.parse(buffer.readCharSequence(buffer.readInt(), Charset.defaultCharset()).toString()));
        }

        @Override
        public void encode(ByteBuf buffer, AskForSeedsPayload value) {
            FriendlyByteBuf.writeUUID(buffer, value.uuid);
            String resourceString = value.levelResource.toString();
            buffer.writeInt(resourceString.length());
            buffer.writeCharSequence(resourceString, Charset.defaultCharset());
        }
    };

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
