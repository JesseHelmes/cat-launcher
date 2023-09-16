package com.example.cat_launcher.client.renderer;

import com.example.cat_launcher.entity.projectile.CatProjectile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.minecraft.client.model.CatModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Cat;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CatProjectileRenderer <T extends CatProjectile> extends EntityRenderer<T> {
	private final CatModel<Cat> model;

	public CatProjectileRenderer(Context context) {
		super(context);
		this.model = new CatModel<Cat>(context.bakeLayer(ModelLayers.CAT));
		this.model.young = false; // TODO i could get this from CatProjectile if i wanted
	}

	@Override
	public void render(T entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
		super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
		matrixStack.pushPose();
		matrixStack.mulPose(Axis.YP.rotationDegrees(entity.getYRot()));
		// to make it correctly position, f3 + b shows hitbox
		matrixStack.mulPose(Axis.XP.rotationDegrees(180.0F));

		// position model correctly
		matrixStack.translate(0, -1.5, 0);

		// this is to test if it goes horizontal
//		matrixStack.mulPose(Axis.XP.rotationDegrees(entity.getXRot()));
		VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(this.getTextureLocation(entity)));
		this.model.setupAnim(entity.getCat(), entityYaw, partialTicks, 0.0F, 0.0F, 0.0F);
		this.model.renderToBuffer(matrixStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		matrixStack.popPose();
	}

	@Override
	public ResourceLocation getTextureLocation(T entity) {
		return entity.getResourceLocation();
	}
}
