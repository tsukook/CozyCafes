package io.tsukook.github.cozycafes.events;

import io.tsukook.github.cozycafes.CozyCafes;
import io.tsukook.github.cozycafes.client.renderers.DandelionSeedRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

@EventBusSubscriber(modid = CozyCafes.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.GAME)
public class CzCClientGameEventBusSubscriber {
    @SubscribeEvent
    public static void onRenderLevelStage(RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_CUTOUT_BLOCKS) {
            DandelionSeedRenderer.render(Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(RenderType.translucentParticle(ResourceLocation.fromNamespaceAndPath(CozyCafes.MODID, "textures/atlas/particles.png"))), event.getCamera(), event.getPartialTick().getGameTimeDeltaTicks());
        }
    }
}
