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

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class AbilityRampage extends Ability {

	public static final int RED = Color.RED.getRGB();

	public AbilityRampage() {
		super("Rampage", "ability.hexxitgear.rampage", 300, 1600, 400, 9);
	}

	@Override
	public void start(PlayerEntity player) {
		player.addPotionEffect(new EffectInstance(Effects.STRENGTH, getDuration(), 2));
		player.addPotionEffect(new EffectInstance(Effects.REGENERATION, getDuration(), 2));
		player.addPotionEffect(new EffectInstance(Effects.SPEED, getDuration(), 2));
	}

	@Override
	public void tick(PlayerEntity player, int duration) {
		BlockPos pos = player.getPosition();
		for (LivingEntity e : player.world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(pos.getX() - 3, pos.getY() - 3, pos.getZ() - 3, pos.getX() + 3, pos.getY() + 3, pos.getZ() + 3)))
			e.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 60, 3));
	}

	@Override
	public void end(PlayerEntity player) {
	}

	@Override
	public void renderFirst(PlayerEntity player) {
		renderAt(player, 0);
		player.world.playSound(player.posX, player.posY, player.posZ, SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER, SoundCategory.PLAYERS, 1, 1, false);
	}

	@Override
	public void renderAt(PlayerEntity player, int duration) {
		if (duration % 20 == 0) for (int i = 0; i < 360; i += 10) {
			SimpleAnimatedParticle p = (SimpleAnimatedParticle) Minecraft.getInstance().particles.addParticle(ParticleTypes.END_ROD, player.posX, player.posY, player.posZ, Math.sin(i) * 0.1, 0.1F, Math.cos(i) * 0.1);
			p.setColor(RED);
			p.setColorFade(RED);
		}
	}
}
