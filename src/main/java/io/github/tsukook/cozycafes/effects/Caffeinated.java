package io.github.tsukook.cozycafes.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.entity.player.PlayerSpawnPhantomsEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static io.github.tsukook.cozycafes.CozyCafes.MODID;

public class Caffeinated extends MobEffect {
    public Caffeinated() {
        super(MobEffectCategory.NEUTRAL, 0x964B00);
        addAttributeModifier(Attributes.MOVEMENT_SPEED, "32db2e21-c0c6-44e7-b014-2913ea1b12b2", 0.15f, AttributeModifier.Operation.MULTIPLY_TOTAL);
        addAttributeModifier(Attributes.ATTACK_SPEED, "a2c7e20d-3ae0-4757-a3bb-92f65e8cd8b6", 0.05f, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Mod.EventBusSubscriber(modid = MODID)
    public static class Events {
        @SubscribeEvent
        public static Event.Result onPhantomSpawn(PlayerSpawnPhantomsEvent event) {
            var player = event.getEntity();
            if (player.hasEffect(CCEffects.CAFFEINATED.get())) {
                return Event.Result.DENY;
            }
            return Event.Result.DEFAULT;
        }
    }
}
