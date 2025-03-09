package io.tsukook.github.cozycafes.client;

import io.tsukook.github.cozycafes.CozyCafes;
import io.tsukook.github.cozycafes.client.models.CoffeePulperCylinderModel;
import io.tsukook.github.cozycafes.client.renderers.CoffeePulperBlockEntityRenderer;
import io.tsukook.github.cozycafes.registers.BlockEntityRegistry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = CozyCafes.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventSubscriber {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(BlockEntityRegistry.COFFEE_PULPER_BLOCK_ENTITY.get(), CoffeePulperBlockEntityRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(CoffeePulperCylinderModel.LAYER_LOCATION, CoffeePulperCylinderModel::createBodyLayer);
    }
}
