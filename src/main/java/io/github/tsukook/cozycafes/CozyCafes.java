package io.github.tsukook.cozycafes;

import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import io.github.tsukook.cozycafes.blocks.CCBlockEntities;
import io.github.tsukook.cozycafes.blocks.CCBlocks;
import io.github.tsukook.cozycafes.effects.CCEffects;
import io.github.tsukook.cozycafes.fluids.CCFluids;
import io.github.tsukook.cozycafes.items.CCItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(CozyCafes.MODID)
public class CozyCafes {
    public static final String MODID = "cozycafes";
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MODID);

    public CozyCafes() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        REGISTRATE.registerEventListeners(modEventBus);
        REGISTRATE.defaultCreativeTab("cozycafes", b -> b.icon(CCItems.DARK_COFFEE_GROUNDS::asStack)).register();
        CCItems.register();
        CCBlocks.register();
        CCEffects.register(modEventBus);
        CCBlockEntities.register();
        CCFluids.register();
    }

    public static ResourceLocation getResource(String name) {
        return new ResourceLocation(MODID, name);
    }
}
