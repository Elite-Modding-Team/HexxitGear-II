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

public class BuffScaleSet implements IBuffHandler {

	@Override
	public void applyPlayerBuffs(PlayerEntity player) {
		player.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 45, 0, false, false));
		player.addPotionEffect(new EffectInstance(Effects.RESISTANCE, 45, 1, false, false));
		if (!player.isPotionActive(Effects.HEALTH_BOOST)) player.addPotionEffect(new EffectInstance(Effects.HEALTH_BOOST, Integer.MAX_VALUE, 4, false, false));
		player.addPotionEffect(new EffectInstance(Effects.NIGHT_VISION, 420, 0, false, false));
	}

	@Override
	public void removePlayerBuffs(PlayerEntity player) {
		player.removePotionEffect(Effects.HEALTH_BOOST);
		player.removePotionEffect(Effects.NIGHT_VISION);
		player.removePotionEffect(Effects.SLOWNESS);
		player.removePotionEffect(Effects.RESISTANCE);
	}

}
