package io.tsukook.github.cozycafes;

import io.tsukook.github.cozycafes.registers.CzCBlockEntityRegistry;
import io.tsukook.github.cozycafes.registers.CzCBlockRegistry;
import io.tsukook.github.cozycafes.registers.CzCCreativeTabRegistry;
import io.tsukook.github.cozycafes.registers.CzCItemRegistry;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import org.slf4j.Logger;



import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

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

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, CzCConfig.SPEC);
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
