package io.tsukook.github.cozycafes.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.tsukook.github.cozycafes.blocks.CoffeePulper;
import io.tsukook.github.cozycafes.blocks.entities.CoffeePulperBlockEntity;
import io.tsukook.github.cozycafes.client.models.CoffeePulperCylinderModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import org.joml.Quaternionf;

public class CoffeePulperBlockEntityRenderer implements BlockEntityRenderer<CoffeePulperBlockEntity> {
    private static final ResourceLocation COFFEE_PULPER_CYLINDER_TEXTURE_LOCATION = ResourceLocation.fromNamespaceAndPath("cozycafes", "textures/block/coffee_pulper_texture.png");
    private static final RenderType RENDER_TYPE = RenderType.entityCutoutNoCull(COFFEE_PULPER_CYLINDER_TEXTURE_LOCATION);
    private final CoffeePulperCylinderModel model;

    public CoffeePulperBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.model = new CoffeePulperCylinderModel(context.bakeLayer(CoffeePulperCylinderModel.LAYER_LOCATION));
    }

    private static float directionToYRot(Direction direction) {
        return switch (direction) {
            case NORTH -> 90;
            case EAST -> 0;
            case SOUTH -> 270; // -90
            case WEST -> 180;
            default -> throw new IllegalArgumentException("No y-Rot for vertical axis: " + direction);
        };
    }

    @Override
    public void render(CoffeePulperBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        Direction direction = blockEntity.getBlockState().getValue(CoffeePulper.FACING);
        poseStack.pushPose();
        poseStack.translate(0.5, -1, 0.5);
        poseStack.mulPose(Axis.YP.rotationDegrees(directionToYRot(direction)));
        //poseStack.mulPose(Axis.ZP.rotationDegrees(((float) blockEntity.n_tick / 20 + partialTick / 20) * 90));
        poseStack.rotateAround(new Quaternionf().rotateZ((float) -(((float) blockEntity.n_tick / 20 + partialTick / 20) * Math.PI)), 0, 1.25f, 0);
        model.renderToBuffer(poseStack, bufferSource.getBuffer(RENDER_TYPE), packedLight, packedOverlay);
        poseStack.popPose();
    }
}
