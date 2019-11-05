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

package sct.hexxitgear.core.buff;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class BuffMagicianSet implements IBuffHandler {

	@Override
	public void applyPlayerBuffs(PlayerEntity player) {
		if (!player.isPotionActive(Effects.ABSORPTION)) player.addPotionEffect(new EffectInstance(Effects.ABSORPTION, 500, 2, false, false));
		player.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 45, 0, false, false));
		player.addPotionEffect(new EffectInstance(Effects.WATER_BREATHING, 45, 0, false, false));
		player.addPotionEffect(new EffectInstance(Effects.NIGHT_VISION, 420, 0, false, false));
	}

	@Override
	public void removePlayerBuffs(PlayerEntity player) {
		player.removePotionEffect(Effects.ABSORPTION);
		player.removePotionEffect(Effects.NIGHT_VISION);
		player.removePotionEffect(Effects.FIRE_RESISTANCE);
		player.removePotionEffect(Effects.WATER_BREATHING);
	}
}
