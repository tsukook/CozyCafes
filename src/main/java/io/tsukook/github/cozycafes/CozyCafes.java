package io.tsukook.github.cozycafes;

import io.tsukook.github.cozycafes.registers.CzCBlockEntityRegistry;
import io.tsukook.github.cozycafes.registers.CzCBlockRegistry;
import io.tsukook.github.cozycafes.registers.CzCCreativeTabRegistry;
import io.tsukook.github.cozycafes.registers.CzCItemRegistry;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(CozyCafes.MODID)
public class CozyCafes
{
    public static final String MODID = "cozycafes";
    private static final Logger LOGGER = LogUtils.getLogger();

    public CozyCafes(IEventBus modEventBus, ModContainer modContainer)
    {
        CzCBlockRegistry.register(modEventBus);
        CzCItemRegistry.register(modEventBus);
        CzCCreativeTabRegistry.register(modEventBus);
        CzCBlockEntityRegistry.register(modEventBus);
    }
}
