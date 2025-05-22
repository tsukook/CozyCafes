package io.tsukook.github.cozycafes.networking;

import io.netty.buffer.ByteBuf;
import io.tsukook.github.cozycafes.CozyCafes;
import io.tsukook.github.cozycafes.systems.dandelion.DandelionSeed;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;

public record DandelionSeedStatePayload(long timestamp, ArrayList<DandelionSeed> dandelionSeeds) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<DandelionSeedStatePayload> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(CozyCafes.MODID, "dandelion_seed_state"));

    public static final StreamCodec<ByteBuf, DandelionSeedStatePayload> STREAM_CODEC = new StreamCodec<ByteBuf, DandelionSeedStatePayload>() {
        @Override
        public DandelionSeedStatePayload decode(ByteBuf buffer) {
            long timestamp = buffer.readLong();
            int seedsSize = buffer.readInt();
            ArrayList<DandelionSeed> seeds = new ArrayList<>(seedsSize);
            for (int i = 0; i < seedsSize; i++) {
                // TODO: Remove velocity after rendering is done if unnecessary
                seeds.add(new DandelionSeed(buffer.readInt(), FriendlyByteBuf.readVector3f(buffer), FriendlyByteBuf.readVector3f(buffer)));
            }
            return new DandelionSeedStatePayload(timestamp, seeds);
        }

        @Override
        public void encode(ByteBuf buffer, DandelionSeedStatePayload value) {
            buffer.writeLong(value.timestamp());
            buffer.writeInt(value.dandelionSeeds.size());
            value.dandelionSeeds.forEach(seed -> {
                buffer.writeInt(seed.getId());
                FriendlyByteBuf.writeVector3f(buffer, seed.pos);
                // TODO: Remove this after rendering is done if unnecessary
                FriendlyByteBuf.writeVector3f(buffer, seed.velocity);
            });
        }
    };

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
