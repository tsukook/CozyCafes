package io.tsukook.github.cozycafes.systems;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import io.tsukook.github.cozycafes.registers.PerLevelTickerManagerRegistry;
import io.tsukook.github.cozycafes.systems.dandelion.DandelionCancer;
import io.tsukook.github.cozycafes.systems.dandelion.DandelionSeed;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector2d;
import org.joml.Vector3f;

import java.text.NumberFormat;

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
                                                                                                                        .executes(context -> createRing(context, new Vector3f()))
                                                                                                                        .then(
                                                                                                                                Commands.argument("offsetVelocity", Vec3Argument.vec3(false))
                                                                                                                                        .executes(context -> createRing(context, Vec3Argument.getVec3(context, "offsetVelocity").toVector3f()))
                                                                                                                        )
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
                        .then(
                                Commands.literal("wind")
                                        .then(
                                                Commands.literal("get")
                                                        .executes(context -> {
                                                            Vector2d windForce = PerLevelTickerManagerRegistry.WIND_MANAGER.getTicker(context.getSource().getLevel()).getWindForce();
                                                            context.getSource().sendSystemMessage(Component.literal("Wind direction is " + windForce.toString(NumberFormat.getNumberInstance()) + " magnitude " + windForce.length()));
                                                            return 1;
                                                        })
                                        ).then(
                                                Commands.literal("power")
                                                        .then(
                                                                Commands.literal("get")
                                                                        .executes(context -> {
                                                                            double power = PerLevelTickerManagerRegistry.WIND_MANAGER.getTicker(context.getSource().getLevel()).windPower;
                                                                            context.getSource().sendSystemMessage(Component.literal("Wind power is " + power));
                                                                            return (int)power;
                                                                        })
                                                        ).then(
                                                                Commands.literal("set")
                                                                        .then(
                                                                                Commands.argument("power", DoubleArgumentType.doubleArg())
                                                                                        .executes(context -> {
                                                                                            double power = DoubleArgumentType.getDouble(context, "power");
                                                                                            PerLevelTickerManagerRegistry.WIND_MANAGER.getTicker(context.getSource().getLevel()).windPower = power;
                                                                                            context.getSource().sendSuccess(() -> Component.literal("Set wind power to " + power), true);
                                                                                            return 1;
                                                                                        })
                                                                        )
                                                        )
                                        ).then(
                                                Commands.literal("rateOfChange")
                                                        .then(
                                                                Commands.literal("get")
                                                                        .executes(context -> {
                                                                            context.getSource().sendSystemMessage(Component.literal("Wind rate of change is " + PerLevelTickerManagerRegistry.WIND_MANAGER.getTicker(context.getSource().getLevel()).changeSpeed));
                                                                            return 1;
                                                                        })
                                                        ).then(
                                                                Commands.literal("set")
                                                                        .then(
                                                                                Commands.argument("changePerTick", DoubleArgumentType.doubleArg(0))
                                                                                        .executes(context -> {
                                                                                            double rateOfChange = DoubleArgumentType.getDouble(context, "changePerTick");
                                                                                            PerLevelTickerManagerRegistry.WIND_MANAGER.getTicker(context.getSource().getLevel()).changeSpeed = rateOfChange;
                                                                                            context.getSource().sendSystemMessage(Component.literal("Set rate of change to " + rateOfChange));
                                                                                            return 1;
                                                                                        })
                                                                        )
                                                        )
                                        )
                        )
        );
    }

    private static int createRing(CommandContext<CommandSourceStack> context, Vector3f offset) {
        DandelionCancer cancer = getCancer(context);
        Vector3f position = Vec3Argument.getVec3(context, "position").toVector3f();
        float power = FloatArgumentType.getFloat(context, "power");

        int numberOfPoints = IntegerArgumentType.getInteger(context, "numberOfPoints");
        double interval = Math.PI * 2 / numberOfPoints;
        for (int i = 0; i < numberOfPoints; i++) {
            double angle = i * interval;
            cancer.addSeed(new DandelionSeed(new Vector3f(position), new Vector3f((float) Math.cos(angle), 0, (float) Math.sin(angle)).mul(power).add(offset)));
        }

        context.getSource().sendSystemMessage(Component.literal("Created ring of " + numberOfPoints + " seed" + (numberOfPoints != 1 ? "s" : "")));
        return 1;
    }

    private static DandelionCancer getCancer(CommandContext<CommandSourceStack> context) {
        return PerLevelTickerManagerRegistry.DANDELION_CANCER_MANAGER.getTicker(context.getSource().getLevel());
    }

    public static int createDandelionSeed(CommandContext<CommandSourceStack> context, Vec3 pos, Vec3 vel) {
        getCancer(context).addSeed(new DandelionSeed(pos.toVector3f(), vel.toVector3f()));
        return 1;
    }
}
