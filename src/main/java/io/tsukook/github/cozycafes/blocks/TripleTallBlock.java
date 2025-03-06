package io.tsukook.github.cozycafes.blocks;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum TripleTallBlock implements StringRepresentable {
    // Do NOT change the order of these!
    BOTTOM,
    MIDDLE,
    TOP;

    @Override
    public @NotNull String getSerializedName() {
        return name().toLowerCase();
    }
}
