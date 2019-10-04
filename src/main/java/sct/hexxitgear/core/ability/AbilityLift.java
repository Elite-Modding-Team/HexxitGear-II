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

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;

public class AbilityLift extends Ability {

	public static final double FRAC = Math.PI / 180;

	public AbilityLift() {
		super("Arcane Rising", "ability.hexxitgear.lift", 600, 500, 6);
	}

	@Override
	public void start(PlayerEntity player) {
		for (LivingEntity e : getHitEntities(player)) {
			if (e != player) e.addVelocity(0, 2, 0);
		}
	}

	protected List<LivingEntity> getHitEntities(PlayerEntity player) {
		List<LivingEntity> hits = new ArrayList<>();
		for (LivingEntity e : player.world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(player.getPosition(), player.getPosition().up(2)).grow(3.5, 1, 3.5))) {
			if (e != player && isEntityInFront(e, player)) hits.add(e);
		}
		return hits;
	}

	protected boolean isEntityInFront(LivingEntity entity, PlayerEntity player) {
		float precalc = player.rotationYaw % 360;
		float a = 360 + (90 + (precalc < 0 ? precalc + 360 : precalc)) % 360 * -1;

		double x = entity.posX - player.posX;
		double z = entity.posZ - player.posZ;

		x *= getXMult(a, x, z);
		a = 0;

		double angX = Math.acos(x / 3.5) / FRAC;
		double angZ = Math.asin(z / 3.5) / FRAC;

		boolean b1 = angX + 360 > a + 270;
		boolean b2 = angX < a + 90;
		boolean b3 = angZ + 360 > a + 270;
		boolean b4 = angZ < a + 90;

		return b1 && b2 && b3 && b4;
	}

	static double getXMult(double a, double x, double z) {
		if (a <= 90 || a >= 270) return 1;
		if (z < 0 && x > 0 && a >= 90 && a <= 135) return 1;
		return -1;
	}

	@Override
	public void end(PlayerEntity player) {
	}

	@Override
	public void tick(PlayerEntity player, int duration) {
	}

	@Override
	public void renderFirst(PlayerEntity player) {
		player.world.playSound(player.posX, player.posY, player.posZ, SoundEvents.ENTITY_EVOKER_CAST_SPELL, SoundCategory.PLAYERS, 1, 1, false);
		for (LivingEntity e : getHitEntities(player))
			for (int i = 0; i < 10; i++)
				player.world.addParticle(ParticleTypes.DRAGON_BREATH, e.posX, e.posY, e.posZ, 0, (i + 1) * 0.1, 0);
	}

}
