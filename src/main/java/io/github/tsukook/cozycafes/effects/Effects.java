package io.github.tsukook.cozycafes.effects;

import io.github.tsukook.cozycafes.cozycafes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Effects {
    public static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, cozycafes.MODID);

    public static final RegistryObject<MobEffect> CAFFEINATED = EFFECTS.register("caffeinated", Caffeinated::new);

    public static void register(IEventBus eventbus) {
        EFFECTS.register(eventbus);
    }

}
