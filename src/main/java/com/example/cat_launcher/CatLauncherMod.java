package com.example.cat_launcher;

import com.example.cat_launcher.init.EntityInit;
import com.example.cat_launcher.init.ItemInit;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CatLauncherMod.MODID)
public class CatLauncherMod {
	// Define mod id in a common place for everything to reference
	public static final String MODID = "cat_launcher";

	public CatLauncherMod() {
//		System.out.println();
//		/kill @e[type=! player]
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		EntityInit.ENTITIES.register(bus);
		ItemInit.ITEMS.register(bus);
	}
}
