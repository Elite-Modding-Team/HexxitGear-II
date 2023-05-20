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
import net.minecraft.client.particle.ParticleEndRod;
import net.minecraft.client.particle.ParticleSimpleAnimated;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AbilityRampage extends Ability {

	public static final int RED = Color.RED.getRGB();

	public AbilityRampage() {
		super("Rampage", "ability.hexxitgear.rampage", 300, 1600, 3, 9);
	}

	@Override
	public void start(EntityPlayer player) {
		player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, getDuration(), 2));
		player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, getDuration(), 2));
		player.addPotionEffect(new PotionEffect(MobEffects.SPEED, getDuration(), 2));
	}

	@Override
	public void tick(EntityPlayer player, int duration) {
		BlockPos pos = player.getPosition();
		for (EntityLivingBase e : player.world.getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB(pos.getX() - 3, pos.getY() - 3, pos.getZ() - 3, pos.getX() + 3, pos.getY() + 3, pos.getZ() + 3)))
			e.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 60, 3));
	}

	@Override
	public void end(EntityPlayer player) {
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderFirst(EntityPlayer player) {
		renderAt(player, 0);
		player.world.playSound(player.posX, player.posY, player.posZ, SoundEvents.ENTITY_LIGHTNING_THUNDER, SoundCategory.PLAYERS, 1, 1, false);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderAt(EntityPlayer player, int duration) {
		if (duration % 20 == 0) for (int i = 0; i < 360; i += 10) {
			ParticleSimpleAnimated p = new ParticleEndRod(Minecraft.getMinecraft().world, player.posX, player.posY, player.posZ, Math.sin(i) * 0.1, 0.1F, Math.cos(i) * 0.1);
			p.setColor(RED);
			p.setColorFade(RED);
			Minecraft.getMinecraft().effectRenderer.addEffect(p);
		}
	}
}
