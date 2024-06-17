package io.github.tsukook.cozycafes.advancements;

import io.github.tsukook.cozycafes.CozyCafes;
import io.github.tsukook.cozycafes.blocks.CCBlocks;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.CachedOutput;
import com.simibubi.create.foundation.data.CreateRegistrate;
import net.minecraft.data.DataProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class advancements implements DataProvider {
    public static final List<advancements> ENTRIES = new ArrayList();
    public static final Advancement START = null;
    public static final Advancement ROOT = CozyCafes("root", (b) -> {
        return  b.icon(CCBlocks.MUG_BLOCK).title("Welcome to Cozy").description("Here Be Coffee :3").awardedForFree();
    });

   @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        return null;
    }

    @Override
    public String getName() {
        return "";
    }
}





