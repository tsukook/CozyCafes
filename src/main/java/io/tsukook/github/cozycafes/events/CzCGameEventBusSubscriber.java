package io.tsukook.github.cozycafes.events;

import com.mojang.brigadier.CommandDispatcher;
import io.tsukook.github.cozycafes.CozyCafes;
import io.tsukook.github.cozycafes.commands.CzCCommand;
import net.minecraft.commands.CommandSourceStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = CozyCafes.MODID, bus = EventBusSubscriber.Bus.GAME)
public class CzCGameEventBusSubscriber {
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        CzCCommand.register(dispatcher);
    }
}
