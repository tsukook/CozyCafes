package io.tsukook.github.cozycafes.systems;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import io.tsukook.github.cozycafes.systems.dandelion.DandelionCancerManager;
import io.tsukook.github.cozycafes.systems.dandelion.DandelionSeed;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.world.phys.Vec3;

public class CzCCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("czc")
                        .requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                        .then(
                                Commands.literal("dandelions")
                                        .then(
                                                Commands.literal("create")
                                                        .then(
                                                                Commands.argument("position", Vec3Argument.vec3())
                                                                        .executes(context -> {
                                                                            return createDandelionSeed(context, Vec3Argument.getVec3(context, "position"), Vec3.ZERO);
                                                                        }).then(Commands.argument("velocity", Vec3Argument.vec3(false))
                                                                                .executes(context -> {
                                                                                    return createDandelionSeed(context, Vec3Argument.getVec3(context, "position"), Vec3Argument.getVec3(context, "velocity"));
                                                                                })
                                                                        )
                                                        )
                                        ).then(
                                                Commands.literal("clear")
                                                        .executes(context -> {
                                                            DandelionCancerManager.getCancer(context.getSource().getLevel()).clearSeeds();
                                                            return 1;
                                                        })
                                        )
                                )
        );
    }

    public static int createDandelionSeed(CommandContext<CommandSourceStack> context, Vec3 pos, Vec3 vel) {
        DandelionCancerManager.getCancer(context.getSource().getLevel()).addSeed(new DandelionSeed(pos.toVector3f(), vel.toVector3f()));
        return 1;
    }
}
