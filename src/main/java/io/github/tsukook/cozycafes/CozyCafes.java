package io.github.tsukook.cozycafes;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.common.Mod;

@Mod(CozyCafes.MODID)
public class CozyCafes
{
    public static final String MODID = "cozycafes";

    public CozyCafes()
    {
        CCRegistry.registerAll();
    }
}
