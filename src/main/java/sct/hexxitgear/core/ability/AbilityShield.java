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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AbilityShield extends Ability {

	public static final int BLUE = Color.BLUE.getRGB();

	public AbilityShield() {
		super("Titanic Shielding", "ability.hexxitgear.shield", 120, 2400, 3, 12);
	}

	@Override
	public void start(EntityPlayer player) {
		player.setEntityInvulnerable(true);
	}

	@Override
	public void tick(EntityPlayer player, int duration) {
	}

	@Override
	public void end(EntityPlayer player) {
		player.setEntityInvulnerable(false);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderFirst(EntityPlayer player) {
		renderAt(player, 0);
		player.world.playSound(player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ILLAGER_PREPARE_MIRROR, SoundCategory.PLAYERS, 1, 1, false);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderAt(EntityPlayer player, int duration) {
		if (duration % 10 == 0) for (int i = 0; i < 360; i += 10) {
			ParticleSimpleAnimated p = new ParticleEndRod(Minecraft.getMinecraft().world, player.posX + Math.sin(i), player.posY, player.posZ + Math.cos(i), 0, 0.2F, 0);
			p.setColor(BLUE);
			p.setColorFade(BLUE);
			Minecraft.getMinecraft().effectRenderer.addEffect(p);
		}
	}
}
