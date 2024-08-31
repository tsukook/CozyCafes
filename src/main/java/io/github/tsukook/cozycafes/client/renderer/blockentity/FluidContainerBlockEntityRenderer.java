package io.github.tsukook.cozycafes.client.renderer.blockentity;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.tsukook.cozycafes.block.entity.FluidContainerBlockEntity;
import io.github.tsukook.cozycafes.client.renderer.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;

public class FluidContainerBlockEntityRenderer implements BlockEntityRenderer<FluidContainerBlockEntity> {
    private final float x0, y0, z0, x1, y1, z1;

    public FluidContainerBlockEntityRenderer(BlockEntityRendererProvider.Context context, float x0, float y0, float z0, float x1, float y1, float z1) {
        this.x0 = x0 / 16;
        this.y0 = y0 / 16;
        this.z0 = z0 / 16;
        this.x1 = x1 / 16;
        this.y1 = y1 / 16;
        this.z1 = z1 / 16;
    }

    @Override
    public void render(FluidContainerBlockEntity fluidContainerBlockEntity, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int i1) {
        FluidStack fluidStack = fluidContainerBlockEntity.getFluidTank().getFluid();
        if (fluidStack.isEmpty())
            return;

        Level level = fluidContainerBlockEntity.getLevel();
        if (level == null)
            return;

        BlockPos blockPos = fluidContainerBlockEntity.getBlockPos();

        IClientFluidTypeExtensions fluidTypeExtensions = IClientFluidTypeExtensions.of(fluidStack.getFluid());
        ResourceLocation stillTexture = fluidTypeExtensions.getStillTexture(fluidStack);
        if (stillTexture == null)
            return;

        FluidState fluidState = fluidStack.getFluid().defaultFluidState();

        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(stillTexture);
        int tintColor = fluidTypeExtensions.getTintColor(fluidStack.getFluid().defaultFluidState(), level, blockPos);

        VertexConsumer builder = multiBufferSource.getBuffer(ItemBlockRenderTypes.getRenderLayer(fluidState));

        float height = (float) fluidContainerBlockEntity.getFluidTank().getFluidAmount() / fluidContainerBlockEntity.getFluidTank().getCapacity();

        RenderHelper.drawBox(builder, poseStack, x0, y0, z0, x1, y1 * height, z1, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), light, tintColor);
    }
}
