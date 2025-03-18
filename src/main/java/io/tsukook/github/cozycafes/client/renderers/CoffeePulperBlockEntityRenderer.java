package io.tsukook.github.cozycafes.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import foundry.veil.api.client.render.rendertype.VeilRenderType;
import io.tsukook.github.cozycafes.CozyCafes;
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
    private static final ResourceLocation COFFEE_PULPER_CYLINDER_TEXTURE_LOCATION =
            CozyCafes.path("textures/block/coffee_pulper_texture.png");
    private static final ResourceLocation RENDER_TYPE_ID =
            CozyCafes.path("silly");
    private final CoffeePulperCylinderModel model;

    public CoffeePulperBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.model = new CoffeePulperCylinderModel(context.bakeLayer(CoffeePulperCylinderModel.LAYER_LOCATION));
    }

    private static float directionToYRot(Direction direction) {
        return switch (direction) {
            case NORTH -> 90;
            case EAST -> 0;
            case SOUTH -> 270;
            case WEST -> 180;
            default -> throw new IllegalArgumentException("No y-Rot for vertical axis: " + direction);
        };
    }

    @Override
    public void render(CoffeePulperBlockEntity blockEntity, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        blockEntity.coffeePulperInstance.advance(partialTick);

        Direction direction = blockEntity.getBlockState().getValue(CoffeePulper.FACING);
        poseStack.pushPose();
        try {
            poseStack.translate(0.5, -1, 0.5);
            poseStack.mulPose(Axis.YP.rotationDegrees(directionToYRot(direction)));

            // Get the render type
            RenderType renderType = VeilRenderType.get(RENDER_TYPE_ID, COFFEE_PULPER_CYLINDER_TEXTURE_LOCATION);
            if (renderType == null) {
                // Fallback to a standard render type if custom one fails
                renderType = RenderType.entityCutoutNoCull(COFFEE_PULPER_CYLINDER_TEXTURE_LOCATION);
            }

            VertexConsumer vertexConsumer = bufferSource.getBuffer(renderType);
            poseStack.rotateAround(new Quaternionf().rotateZ(blockEntity.coffeePulperInstance.getCylinderRotation()),
                    0.0f, 1.25f, 0.0f);
            model.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay);
        } finally {
            poseStack.popPose();
        }
    }
}