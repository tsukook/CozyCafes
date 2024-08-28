package io.github.tsukook.cozycafes;

import com.tterrag.registrate.Registrate;

public class CCRegistry {
    public static final Registrate REGISTRATE = Registrate.create(CozyCafes.MODID);

    public static void registerAll() {
        CCBlocks.register();
    }
}
