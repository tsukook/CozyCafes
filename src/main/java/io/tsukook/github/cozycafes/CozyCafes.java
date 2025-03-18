package io.tsukook.github.cozycafes;

import foundry.veil.api.client.render.rendertype.VeilRenderType;
import io.tsukook.github.cozycafes.registers.CzCBlockEntityRegistry;
import io.tsukook.github.cozycafes.registers.CzCBlockRegistry;
import io.tsukook.github.cozycafes.registers.CzCCreativeTabRegistry;
import io.tsukook.github.cozycafes.registers.CzCItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
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

        modContainer.registerConfig(ModConfig.Type.COMMON, CzCConfig.SPEC);
    }

    // Java
    public static ResourceLocation path(String path) {
        String fullPath = MODID + ":" + path;
        ResourceLocation location = ResourceLocation.tryParse(fullPath);
        if (location == null) {
            throw new IllegalArgumentException("Invalid ResourceLocation: " + fullPath);
        }
        return location;
    }
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());

            // Test the render type during setup
            event.enqueueWork(() -> {
                ResourceLocation sillyRenderTypeId = path("silly");
                LOGGER.info("Testing render type: {}", sillyRenderTypeId);

                // This will load the render type from resources
                ResourceLocation textureLocation = path("textures/block/coffee_pulper_texture.png");
                LOGGER.info("With texture: {}", textureLocation);
                VeilRenderType.get(sillyRenderTypeId, textureLocation);
            });
        }
    }
}