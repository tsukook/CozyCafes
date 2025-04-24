package io.tsukook.github.cozycafes.events;

import com.mojang.brigadier.CommandDispatcher;
import io.tsukook.github.cozycafes.CozyCafes;
import io.tsukook.github.cozycafes.systems.CzCCommand;
import io.tsukook.github.cozycafes.systems.dandelion.DandelionCancerManager;
import io.tsukook.github.cozycafes.registers.CzCBlockRegistry;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

@EventBusSubscriber(modid = CozyCafes.MODID, bus = EventBusSubscriber.Bus.GAME)
public class CzCGameEventBusSubscriber {
    @SubscribeEvent
    public static void onBlockRightClick(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        ItemStack stack = event.getItemStack();
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);
        Player player = event.getEntity();
        if (stack.is(Items.WATER_BUCKET) && state.is(Blocks.COMPOSTER)) {
            level.setBlockAndUpdate(pos, CzCBlockRegistry.WATER_FERMENTATION_VAT.get().defaultBlockState());
            player.setItemInHand(event.getHand(), ItemUtils.createFilledResult(stack, player, new ItemStack(Items.BUCKET)));
            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.SUCCESS);
        }
    }

    @SubscribeEvent
    public static void onLevelLoad(LevelEvent.Load event) {
        if (event.getLevel() instanceof ServerLevel serverLevel)
            DandelionCancerManager.createCancerForLevel(serverLevel);
    }

    @SubscribeEvent
    public static void onLevelTickPost(LevelTickEvent.Post event) {
        if (event.getLevel() instanceof ServerLevel level) {
            if (!level.tickRateManager().runsNormally())
                return;
            DandelionCancerManager.tickCancer(level);
        }
    }

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        CzCCommand.register(dispatcher);
    }
}
