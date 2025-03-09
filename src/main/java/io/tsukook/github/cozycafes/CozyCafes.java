package io.tsukook.github.cozycafes;

import io.tsukook.github.cozycafes.registers.BlockEntityRegistry;
import io.tsukook.github.cozycafes.registers.BlockRegistry;
import io.tsukook.github.cozycafes.registers.CreativeTabRegistry;
import io.tsukook.github.cozycafes.registers.ItemRegistry;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;

@Mod(CozyCafes.MODID)
public class CozyCafes
{
    public static final String MODID = "cozycafes";
    private static final Logger LOGGER = LogUtils.getLogger();

    public CozyCafes(IEventBus modEventBus, ModContainer modContainer)
    {

        BlockRegistry.register(modEventBus);
        ItemRegistry.register(modEventBus);
        CreativeTabRegistry.register(modEventBus);
        BlockEntityRegistry.register(modEventBus);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    /*@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }*/
}
