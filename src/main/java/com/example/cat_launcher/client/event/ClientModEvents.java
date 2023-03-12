package com.example.cat_launcher.client.event;

import com.example.cat_launcher.CatLauncherMod;
import com.example.cat_launcher.client.renderer.CatProjectileRenderer;
import com.example.cat_launcher.init.EntityInit;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.api.distmarker.Dist;

@Mod.EventBusSubscriber(modid = CatLauncherMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

	@SubscribeEvent
	public static void doSetup(FMLClientSetupEvent event) {
		EntityRenderers.register(EntityInit.CAT_PROJECTILE.get(), CatProjectileRenderer::new);
	}
}