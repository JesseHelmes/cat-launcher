package com.example.cat_launcher.item;

import com.example.cat_launcher.entity.projectile.CatProjectile;
import com.example.cat_launcher.init.EntityInit;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.level.Level;

public class CatLauncher extends TieredItem implements Vanishable {
	public CatLauncher(Tier tier, Properties properties) {
		super(tier, properties);
	}

	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);

		Cat entity = EntityType.CAT.create(level);
		level.playSound((Player)null, player.getX(), player.getY(),
				player.getZ(), SoundEvents.CAT_AMBIENT,
				SoundSource.PLAYERS, 1.0F, entity.getVoicePitch());

		if (!level.isClientSide) {
			CatProjectile cat = new CatProjectile(EntityInit.CAT_PROJECTILE.get(), player, level);
			// speed is the 2 second parameter
			// TODO slow down projectile
			cat.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 0.75F, 1.0F);

			// spawns entity left or right from the screen based on with hand the item is being hold
			double distance = 0.8D;
			if(hand == InteractionHand.OFF_HAND) distance = -distance;
			double angle = Math.toRadians(player.getYRot() + 90.0);
			// moves it to the right, but also further away from the player
//			double x = player.getX() + (distanceFromPlayer * Math.cos(angle)) + (distanceToSideways * Math.cos(angle + Math.PI / 2));
//			double z = player.getZ() + (distanceFromPlayer * Math.sin(angle)) + (distanceToSideways * Math.sin(angle + Math.PI / 2));
			double x = player.getX() + Math.cos(angle) + (distance * Math.cos(angle + Math.PI / 2));
			double z = player.getZ() + Math.sin(angle) + (distance * Math.sin(angle + Math.PI / 2));
			cat.moveTo(x, player.getY() + 3.2D, z);

			level.addFreshEntity(cat);
		}

		player.awardStat(Stats.ITEM_USED.get(this));

		return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
	}
}
