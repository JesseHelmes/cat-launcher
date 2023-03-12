package com.example.cat_launcher.init;

import com.example.cat_launcher.CatLauncherMod;
import com.example.cat_launcher.item.CatLauncher;
import com.example.cat_launcher.item.ModTiers;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
			CatLauncherMod.MODID);

	public static final RegistryObject<CatLauncher> CAT_LAUNCHER = ITEMS.register("cat_launcher",
	() -> new CatLauncher(ModTiers.NETHERITE,
			(new Item.Properties()).tab(CreativeModeTab.TAB_COMBAT).fireResistant().durability(-1).rarity(Rarity.EPIC)));
}
