package io.github.tsukook.cozycafes.effects;

import io.github.tsukook.cozycafes.CozyCafes;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CCEffects {
    public static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, CozyCafes.MODID);

    public static final RegistryObject<MobEffect> CAFFEINATED = EFFECTS.register("caffeinated", Caffeinated::new);

    public static void register(IEventBus eventbus) {
        EFFECTS.register(eventbus);
    }

}
