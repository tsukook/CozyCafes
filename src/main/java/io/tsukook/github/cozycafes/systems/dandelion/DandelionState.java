package io.tsukook.github.cozycafes.systems.dandelion;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

import java.util.HashMap;

public record DandelionState(long timestampMillis, HashMap<Integer, DandelionSeed> seeds) {
    public static final StreamCodec<ByteBuf, DandelionState> STREAM_CODEC = new StreamCodec<ByteBuf, DandelionState>() {
        @Override
        public DandelionState decode(ByteBuf buffer) {
            long timestampMillis = buffer.readLong();
            int seedsSize = buffer.readInt();
            HashMap<Integer, DandelionSeed> seeds = new HashMap<>(seedsSize);
            for (int i = 0; i < seedsSize; i++) {
                // TODO: Remove velocity after rendering is done if unnecessary
                DandelionSeed seed = new DandelionSeed(buffer.readInt(), FriendlyByteBuf.readVector3f(buffer), FriendlyByteBuf.readVector3f(buffer));
                seeds.put(seed.getId(), seed);
            }
            return new DandelionState(timestampMillis, seeds);
        }

        @Override
        public void encode(ByteBuf buffer, DandelionState value) {
            buffer.writeLong(value.timestampMillis());
            buffer.writeInt(value.seeds.size());
            value.seeds.forEach((id, seed) -> {
                buffer.writeInt(seed.getId());
                FriendlyByteBuf.writeVector3f(buffer, seed.pos);
                // TODO: Remove this after rendering is done if unnecessary
                FriendlyByteBuf.writeVector3f(buffer, seed.velocity);
            });
        }
    };
}
