package com.example.cat_launcher.init;

import com.example.cat_launcher.CatLauncherMod;
import com.example.cat_launcher.entity.projectile.CatProjectile;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityInit {
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES,
			CatLauncherMod.MODID);

	public static final RegistryObject<EntityType<CatProjectile>> CAT_PROJECTILE = ENTITIES
			.register("cat_projectile",
					() -> EntityType.Builder.of((EntityType.EntityFactory<CatProjectile>) CatProjectile::new, MobCategory.MISC)
							.sized(0.6F, 0.7F) // hit box
							.clientTrackingRange(8)
							.updateInterval(10)
							.build(new ResourceLocation(CatLauncherMod.MODID, "cat_projectile").toString()));
}
