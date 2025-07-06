package io.tsukook.github.cozycafes.items;

import io.tsukook.github.cozycafes.registers.PerLevelTickerManagerRegistry;
import io.tsukook.github.cozycafes.systems.dandelion.DandelionSeed;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.joml.Vector3f;

import java.util.List;

public class DandelionBlockItem extends BlockItem {
    public DandelionBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 20;
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        if (level instanceof ServerLevel serverLevel) {
            float ySpreadMax = 45.0f/4;
            float ySpreadMin = ySpreadMax;
            float xSpreadMax = 0;
            float xSpreadMin = ySpreadMax/2;
            float xRot = livingEntity.getXRot() + Mth.nextFloat(level.random, -xSpreadMin, xSpreadMax);
            float yRot = livingEntity.getYRot() + Mth.nextFloat(level.random, -ySpreadMin, ySpreadMax);
            DandelionSeed seed = new DandelionSeed(
                    new Vector3f((float) livingEntity.getX(), (float) livingEntity.getEyeY(), (float) livingEntity.getZ()),
                    livingEntity.calculateViewVector(xRot, yRot).toVector3f().mul(7.5f)
            );
            seed.boost = new Vector3f(0, 0.2f, 0);
            seed.boostTicks = Mth.nextInt(level.random, 15, 30);
            PerLevelTickerManagerRegistry.DANDELION_CANCER_MANAGER.getTicker(serverLevel).addSeed(seed);
        }
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.TOOT_HORN;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> componentList, TooltipFlag tooltipFlag) {
        componentList.add(Component.translatable("stupidity.blow_me").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.DARK_PURPLE));
    }
}
