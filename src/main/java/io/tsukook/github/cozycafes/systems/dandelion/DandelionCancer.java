package io.tsukook.github.cozycafes.systems.dandelion;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.neoforge.network.PacketDistributor;
import org.joml.RoundingMode;
import org.joml.Vector2f;
import org.joml.Vector2i;

import java.util.ArrayList;

// FIXME: Name is a placeholder
public class DandelionCancer {
    private final ServerLevel level;
    private boolean ticked = false;
    private final ArrayList<DandelionSeed> seeds = new ArrayList<>(256);

    public static final StreamCodec<ByteBuf, ArrayList<DandelionSeed>> DANDELION_CANCER_CODEC = new StreamCodec<ByteBuf, ArrayList<DandelionSeed>>() {
        @Override
        public void encode(ByteBuf buffer, ArrayList<DandelionSeed> value) {
            buffer.writeInt(value.size());
            for (DandelionSeed seed : value) {
                FriendlyByteBuf.writeVector3f(buffer, seed.pos);
                FriendlyByteBuf.writeVector3f(buffer, seed.velocity);
            }
        }

        @Override
        public ArrayList<DandelionSeed> decode(ByteBuf buffer) {
            int size = buffer.readInt();
            ArrayList<DandelionSeed> seeds = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                DandelionSeed seed = new DandelionSeed();
                seed.pos = FriendlyByteBuf.readVector3f(buffer);
                seed.velocity = FriendlyByteBuf.readVector3f(buffer);
                seeds.add(seed);
            }
            return seeds;
        }
    };

    public DandelionCancer(ServerLevel level) {
        this.level = level;
    }

    public void addSeed(DandelionSeed seed) {
        seeds.add(seed);
    }

    public void tick() {
        for (DandelionSeed seed : seeds) {
            Vector2i chunk = seed.pos.div(16).xy(new Vector2f()).get(RoundingMode.FLOOR, new Vector2i());
            if (level.hasChunk(chunk.x, chunk.y)) {
                seed.pos.add(seed.velocity);
            }
        }
    }
}
