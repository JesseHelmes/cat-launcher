package com.example.cat_launcher.entity.projectile;

import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.CatVariantTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.CatVariant;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public class CatProjectile extends AbstractArrow {
	private static final EntityDataAccessor<CatVariant> DATA_VARIANT_ID = SynchedEntityData.defineId(CatProjectile.class, EntityDataSerializers.CAT_VARIANT);

	private Cat cat = EntityType.CAT.create(this.level);

	public CatProjectile(EntityType<? extends CatProjectile> entity, Level level) {
		super(entity, level);
	}

	public CatProjectile(EntityType<CatProjectile> entityType, double x, double y, double z, Level world) {
		super(entityType, x, y, z, world);
	}

	public CatProjectile(EntityType<CatProjectile> entityType, LivingEntity shooter, Level world) {
		super(entityType, shooter, world);
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_VARIANT_ID, CatVariant.BLACK);
	}

	public void addAdditionalSaveData(CompoundTag p_28156_) {
		super.addAdditionalSaveData(p_28156_);
		p_28156_.putString("variant", Registry.CAT_VARIANT.getKey(this.getCatVariant()).toString());
	}

	public void readAdditionalSaveData(CompoundTag p_28142_) {
		super.readAdditionalSaveData(p_28142_);
		CatVariant catvariant = Registry.CAT_VARIANT.get(ResourceLocation.tryParse(p_28142_.getString("variant")));
		if (catvariant != null) {
			this.setCatVariant(catvariant);
		} else {
			this.setCatSkin(this.level);
		}
	}

	public CatVariant getCatVariant() {
		return this.entityData.get(DATA_VARIANT_ID);
	}

	public void setCatVariant(CatVariant p_218133_) {
		this.entityData.set(DATA_VARIANT_ID, p_218133_);
	}

	public ResourceLocation getResourceLocation() {
		return this.getCatVariant().texture();
	}

	@Override
	protected ItemStack getPickupItem() {
		return ItemStack.EMPTY;
	}

	public Cat getCat() {
		return this.cat;
	}

	public double getBaseDamage() {
		return 0.0D;
	}

	@Override
	protected void onHit(HitResult result) {
		super.onHit(result);

		Cat cat = this.getCat();

		float power = 3.0F;
		// passing the cat prevents the cat from dying by its own boom
		this.level.explode(cat, this.getX(), this.getY(), this.getZ(), power, Explosion.BlockInteraction.BREAK);

		if(!this.level.isClientSide()) {
			cat.setCatVariant(this.getCatVariant());

			// TODO this sometimes spawns the cat inside of a block instead of just outside of it, so i need to solve that time time
			cat.moveTo(this.getX(), this.getY(), this.getZ(), -this.getYRot(), 0.0F);
			cat.yBodyRot = -this.getYRot();
			cat.yHeadRot = -this.getYRot();


			this.level.addFreshEntity(cat);

			this.level.broadcastEntityEvent(this, (byte)3);
			this.discard();
		}
	}

	public void shoot(double p_36775_, double p_36776_, double p_36777_, float p_36778_, float p_36779_) {
		super.shoot(p_36775_, p_36776_, p_36777_, p_36778_, p_36779_);
		this.setCatSkin(level);
	}

	public void setCatSkin(Level level) {
		boolean flag = level.getMoonBrightness() > 0.9F;
		TagKey<CatVariant> tagkey = flag ? CatVariantTags.FULL_MOON_SPAWNS : CatVariantTags.DEFAULT_SPAWNS;
		Registry.CAT_VARIANT.getTag(tagkey).flatMap((p_218136_) -> {
		return p_218136_.getRandomElement(this.level.getRandom());
		}).ifPresent((p_218138_) -> {
			this.setCatVariant(p_218138_.value());
		});
	}

	protected SoundEvent getDefaultHitGroundSoundEvent() {
		return SoundEvents.CAT_HURT;
	}

	// TODO have it follow a straight line longer
	@Override
	public void tick() {
		super.tick();
		// prevent cat from spawning too high, or spawning to the first open block when
		// it hits the side
		this.setXRot(0.0F); // this is needed to get it always be facing horizontal

	}
}
