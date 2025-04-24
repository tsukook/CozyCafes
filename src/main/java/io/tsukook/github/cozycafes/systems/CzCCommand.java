package io.tsukook.github.cozycafes.systems;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import io.tsukook.github.cozycafes.systems.dandelion.DandelionCancer;
import io.tsukook.github.cozycafes.systems.dandelion.DandelionCancerManager;
import io.tsukook.github.cozycafes.systems.dandelion.DandelionSeed;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

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
                                                                Commands.literal("single")
                                                                        .then(
                                                                                Commands.argument("position", Vec3Argument.vec3())
                                                                                    .executes(context -> {
                                                                                    return createDandelionSeed(context, Vec3Argument.getVec3(context, "position"), Vec3.ZERO);
                                                                                }).then(
                                                                                        Commands.argument("velocity", Vec3Argument.vec3(false))
                                                                                            .executes(context -> {
                                                                                                context.getSource().sendSystemMessage(Component.literal("Created seed"));
                                                                                                return createDandelionSeed(context, Vec3Argument.getVec3(context, "position"), Vec3Argument.getVec3(context, "velocity"));
                                                                                            })
                                                                                        )
                                                                        )
                                                        ).then(
                                                                Commands.literal("ring")
                                                                        .then(
                                                                                Commands.argument("position", Vec3Argument.vec3())
                                                                                        .then(
                                                                                                Commands.argument("power", FloatArgumentType.floatArg())
                                                                                                        .then(
                                                                                                                Commands.argument("numberOfPoints", IntegerArgumentType.integer(0))
                                                                                                                        .executes(context -> {
                                                                                                                            DandelionCancer cancer = getCancer(context);
                                                                                                                            Vector3f position = Vec3Argument.getVec3(context, "position").toVector3f();
                                                                                                                            float power = FloatArgumentType.getFloat(context, "power");

                                                                                                                            int numberOfPoints = IntegerArgumentType.getInteger(context, "numberOfPoints");
                                                                                                                            double interval = Math.PI * 2 / numberOfPoints;
                                                                                                                            for (int i = 0; i < numberOfPoints; i++) {
                                                                                                                                double angle = i * interval;
                                                                                                                                cancer.addSeed(new DandelionSeed(new Vector3f(position), new Vector3f((float) Math.cos(angle), 0, (float) Math.sin(angle)).mul(power)));
                                                                                                                            }

                                                                                                                            context.getSource().sendSystemMessage(Component.literal("Created ring of " + numberOfPoints + " seed" + (numberOfPoints != 1 ? "s" : "")));
                                                                                                                            return 1;
                                                                                                                        })
                                                                                                        )
                                                                                        )
                                                                        )
                                                        )
                                        ).then(
                                                Commands.literal("clear")
                                                        .executes(context -> {
                                                            int amount = getCancer(context).clearSeeds();
                                                            context.getSource().sendSystemMessage(Component.literal("Cleared " + amount + " dandelion seed" + (amount != 1 ? "s" : "")));
                                                            return 1;
                                                        })
                                        ).then(
                                                Commands.literal("freeze")
                                                        .executes(context -> {
                                                            DandelionCancer cancer = getCancer(context);
                                                            if (cancer.getIsFrozen()) {
                                                                context.getSource().sendSystemMessage(Component.literal("Already frozen"));
                                                                return -1;
                                                            }
                                                            context.getSource().sendSystemMessage(Component.literal("Dandelions frozen"));
                                                            cancer.setFrozen(true);
                                                            return 1;
                                                        })
                                        ).then(
                                                Commands.literal("unfreeze")
                                                        .executes(context -> {
                                                            DandelionCancer cancer = getCancer(context);
                                                            if (!cancer.getIsFrozen()) {
                                                                context.getSource().sendSystemMessage(Component.literal("Already unfrozen"));
                                                                return -1;
                                                            }
                                                            context.getSource().sendSystemMessage(Component.literal("Dandelions unfrozen"));
                                                            cancer.setFrozen(false);
                                                            return 1;
                                                        })
                                        ).then(
                                                Commands.literal("tick")
                                                        .executes(context -> {
                                                            getCancer(context).tick(true);
                                                            context.getSource().sendSystemMessage(Component.literal("Ticked dandelions once"));
                                                            return 1;
                                                        }).then(
                                                                Commands.argument("tickAmount", IntegerArgumentType.integer(0))
                                                                        .executes(context -> {
                                                                            int amount = IntegerArgumentType.getInteger(context, "tickAmount");
                                                                            for (int i = 0; i < amount; i++)
                                                                                getCancer(context).tick(true);
                                                                            context.getSource().sendSystemMessage(Component.literal("Ticked dandelions " + (amount != 1 ? amount + " times" : "once")));
                                                                            return 1;
                                                                        })
                                                        )
                                        ).then(
                                                Commands.literal("count")
                                                        .executes(context -> {
                                                            int count = getCancer(context).countSeeds();
                                                            context.getSource().sendSystemMessage(Component.literal(count + " dandelion seed" + (count != 1 ? "s" : "") + " in dimension " + context.getSource().getLevel().dimension().location()));
                                                            return count;
                                                        })
                                        )
                                )
        );
    }

    private static DandelionCancer getCancer(CommandContext<CommandSourceStack> context) {
        return DandelionCancerManager.getCancer(context.getSource().getLevel());
    }

    public static int createDandelionSeed(CommandContext<CommandSourceStack> context, Vec3 pos, Vec3 vel) {
        DandelionCancerManager.getCancer(context.getSource().getLevel()).addSeed(new DandelionSeed(pos.toVector3f(), vel.toVector3f()));
        return 1;
    }
}
