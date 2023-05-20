/*
 * HexxitGear
 * Copyright (C) 2013  Ryan Cohen
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package sct.hexxitgear.core.ability;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sct.hexxitgear.core.AbilityHandler;

public class AbilityStealth extends Ability {

	public AbilityStealth() {
		super("Stealth", "ability.hexxitgear.stealth", 600, 800, 1, 4);
	}

	@Override
	public void start(EntityPlayer player) {
		player.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, getDuration(), 81, false, false));
		player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, getDuration(), 1, false, false));
		player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, getDuration(), 3, false, false));
	}

	@Override
	public void tick(EntityPlayer player, int duration) {
		if(player.ticksExisted - player.getLastAttackedEntityTime() <= 2) 
			AbilityHandler.ACTIVE_HANDLERS.get(player.getUniqueID()).setEnded(player);
	}

	@Override
	public void end(EntityPlayer player) {
		player.removePotionEffect(MobEffects.INVISIBILITY);
		player.removePotionEffect(MobEffects.SLOWNESS);
		player.removePotionEffect(MobEffects.STRENGTH);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderFirst(EntityPlayer player) {
		for (int i = 0; i < 360; i += 10) {
			player.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, player.posX, player.posY + 4, player.posZ, Math.sin(i) * 0.1F, -0.8F, Math.cos(i) * 0.1F);
		}
		player.world.playSound(player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ILLAGER_MIRROR_MOVE, SoundCategory.PLAYERS, 1, 1, false);
	}

	@Override
	public void renderAt(EntityPlayer player, int duration) {
	}
}
