package io.tsukook.github.cozycafes.client.models;// Made with Blockbench 4.10.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class CoffeePulperCylinderModel extends Model {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("cozycafes", "coffee_pulper_cylinder"), "main");
	private final ModelPart bb_main;

	public CoffeePulperCylinderModel(ModelPart root) {
		super(root, RenderType::entityCutoutNoCull);
		this.bb_main = root.getChild("bb_main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(36, 0).addBox(-2.0F, -7.0F, -7.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(36, 0).addBox(-2.0F, -2.0F, -7.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(34, 11).addBox(2.0F, -7.0F, -7.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(34, 11).addBox(-3.0F, -7.0F, -7.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition cube_r1 = bb_main.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(31, 0).addBox(-5.7981F, 1.7981F, -6.499F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(34, 11).addBox(-2.7981F, -1.2019F, -6.498F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(34, 15).addBox(-4.7981F, -0.2019F, -4.5F, 5.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.25F, -0.5F, 0.0F, 0.0F, -0.7854F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
}