package com.example.cat_launcher.event;

import com.example.cat_launcher.CatLauncherMod;

import net.minecraft.world.entity.animal.Cat;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.Mod;

//@Mod.EventBusSubscriber(modid = CatLauncherMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonModEvents {
//	@SubscribeEvent
	public void onLivingDamage(LivingDamageEvent event) {
		// Check if the damage was caused by an explosion
		if(!event.getSource().isExplosion()) return;

		// Checks if it was the same entity to make things faster
		if(!event.getSource().getEntity().equals(event.getEntity())) return;

		// Get the entity that was damaged
		if(!(event.getEntity() instanceof Cat)) return;

		Cat cat = (Cat) event.getEntity();
		
		// ticks it need to be an adult
		int ticks = 24000;
		
		System.out.println(cat.getAge());
		
//		if(cat.getTicks())
//		
//		cat.getGrowingAge();

		// Cancel the original damage event
//		event.setCanceled(true);
	}
}
