package io.tsukook.github.cozycafes.events;

import io.tsukook.github.cozycafes.CozyCafes;
import io.tsukook.github.cozycafes.registers.CzCBlockRegistry;
import net.minecraft.core.BlockPos;
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
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = CozyCafes.MODID)
public class CzCServerEventBusSubscriber {
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
}
