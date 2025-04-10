package io.tsukook.github.cozycafes.blocks;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum DandelionStage implements StringRepresentable {
    BULB,
    BLOOM,
    CANCER4;

    @Override
    public @NotNull String getSerializedName() {
        return name().toLowerCase();
    }
}
