package io.tsukook.github.cozycafes.events;

import io.tsukook.github.cozycafes.CozyCafes;
import io.tsukook.github.cozycafes.client.models.CoffeePulperCylinderModel;
import io.tsukook.github.cozycafes.client.renderers.CoffeePulperBlockEntityRenderer;
import io.tsukook.github.cozycafes.client.renderers.WaterFermentationVatRenderer;
import io.tsukook.github.cozycafes.registers.CzCBlockEntityRegistry;
import io.tsukook.github.cozycafes.registers.CzCBlockRegistry;
import net.minecraft.client.renderer.BiomeColors;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@EventBusSubscriber(modid = CozyCafes.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class CzCClientModEventBusSubscriber {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(CzCBlockEntityRegistry.COFFEE_PULPER_BLOCK_ENTITY.get(), CoffeePulperBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(CzCBlockEntityRegistry.WATER_FERMENTATION_VAT_BLOCK_ENTITY.get(), WaterFermentationVatRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(CoffeePulperCylinderModel.LAYER_LOCATION, CoffeePulperCylinderModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register(
                ((state, blockAndTintGetter, pos, tintIndex) -> blockAndTintGetter != null && pos != null ? BiomeColors.getAverageWaterColor(blockAndTintGetter, pos) : -1),
                CzCBlockRegistry.WATER_FERMENTATION_VAT.get()
        );
    }
}
