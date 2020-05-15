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

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import sct.hexxitgear.core.AbilityHandler;

public class AbilityStealth extends Ability {

	public AbilityStealth() {
		super("Stealth", "ability.hexxitgear.stealth", 600, 800, 160, 4);
	}

	@Override
	public void start(PlayerEntity player) {
		player.addPotionEffect(new EffectInstance(Effects.INVISIBILITY, getDuration(), 81, false, false));
		player.addPotionEffect(new EffectInstance(Effects.SLOWNESS, getDuration(), 1, false, false));
		player.addPotionEffect(new EffectInstance(Effects.STRENGTH, getDuration(), 3, false, false));
	}

	@Override
	public void tick(PlayerEntity player, int duration) {
		if (player.ticksExisted - player.getLastAttackedEntityTime() <= 2) AbilityHandler.ACTIVE_HANDLERS.get(player.getUniqueID()).setEnded(player);
	}

	@Override
	public void end(PlayerEntity player) {
		player.removePotionEffect(Effects.INVISIBILITY);
		player.removePotionEffect(Effects.SLOWNESS);
		player.removePotionEffect(Effects.STRENGTH);
	}

	@Override
	public void renderFirst(PlayerEntity player) {
		for (int i = 0; i < 360; i += 10) {
			player.world.addParticle(ParticleTypes.SMOKE, player.getX(), player.getY() + 4, player.getZ(), Math.sin(i) * 0.1F, -0.8F, Math.cos(i) * 0.1F);
		}
		player.world.playSound(player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_ILLUSIONER_MIRROR_MOVE, SoundCategory.PLAYERS, 1, 1, false);
	}

	@Override
	public void renderAt(PlayerEntity player, int duration) {
	}
}
