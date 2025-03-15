package io.tsukook.github.cozycafes.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.network.chat.Component;

public class CzCCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("czc")
                        .requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                        .then(
                                Commands.argument("test", StringArgumentType.greedyString())
                                        .executes(context -> {
                                            String str = StringArgumentType.getString(context, "test");
                                            context.getSource().getServer().getPlayerList().broadcastSystemMessage(Component.literal("CozyCafes test command: " + str), false);
                                            return str.length();
                                        })
                        )
        );
    }
}
