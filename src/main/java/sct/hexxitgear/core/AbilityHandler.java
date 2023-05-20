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

package sct.hexxitgear.core;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import sct.hexxitgear.core.ability.Ability;
import sct.hexxitgear.net.AbilityRenderMessage;
import sct.hexxitgear.net.ActionTextMessage;
import sct.hexxitgear.net.HexNetwork;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AbilityHandler {

	private int activeTime;
	private int cooldownTime;
	private Ability ability;
	private boolean ended = false;
	private boolean started = false;

	public static final Map<UUID, AbilityHandler> ACTIVE_HANDLERS = new HashMap<>();

	private AbilityHandler(EntityPlayer player) {
		if (player.world.isRemote) throw new IllegalArgumentException("Ability handler has been constructed on a client world, please report this!");
		this.ability = ArmorSet.getCurrentArmorSet(player).getAbility();
		this.activeTime = ability.getDuration();
		this.cooldownTime = ability.getCooldown();
	}

	public static AbilityHandler getActiveAbility(EntityPlayer player) {
		if (player.world.isRemote) return null;
		return ACTIVE_HANDLERS.get(player.getUniqueID());
	}

	public static void activateAbility(EntityPlayer player) {
		AbilityHandler handler = ACTIVE_HANDLERS.get(player.getUniqueID());
		if (handler != null) {
			HexNetwork.INSTANCE.sendTo(new ActionTextMessage(0, handler.ability.getId(), handler.cooldownTime), (EntityPlayerMP) player);
			return;
		}
		handler = new AbilityHandler(player);
		if (!player.capabilities.isCreativeMode && player.experienceLevel < handler.ability.getXpCost()) {
			HexNetwork.INSTANCE.sendTo(
					new ActionTextMessage(4, handler.ability.getId(), handler.ability.getXpCost() - player.experienceLevel), (EntityPlayerMP) player);
			return;
		}
		int food = player.getFoodStats().getFoodLevel();
		if (!player.capabilities.isCreativeMode && food < handler.ability.getHungerCost()) {
			HexNetwork.INSTANCE.sendTo(
					new ActionTextMessage(5, handler.ability.getId(), handler.ability.getHungerCost() - food), (EntityPlayerMP) player);
			return;
		}
		if (!player.capabilities.isCreativeMode) {
			player.addExperienceLevel(-handler.ability.getXpCost());
			player.getFoodStats().setFoodLevel(food - handler.ability.getHungerCost());
		}
		ACTIVE_HANDLERS.put(player.getUniqueID(), handler);
	}

	public void onTick(EntityPlayer player) {
		if (activeTime > 0) {
			if (ability != null) {
				if (ability.getDuration() == activeTime || ability.isInstant()) {
					HexNetwork.INSTANCE.sendTo(new ActionTextMessage(1, ability.getId()), (EntityPlayerMP) player);
				}
				if (!started) {
					ability.start(player);
					HexNetwork.INSTANCE.sendToAllAround(new AbilityRenderMessage(0, ability.getId(), player, 0), new TargetPoint(player.world.provider.getDimension(), player.posX, player.posY, player.posZ, 50));
					started = true;
				} else {
					ability.tick(player, activeTime);
					HexNetwork.INSTANCE.sendToAllAround(new AbilityRenderMessage(1, ability.getId(), player, activeTime), new TargetPoint(player.world.provider.getDimension(), player.posX, player.posY, player.posZ, 50));
				}
				if (ability.isInstant()) activeTime = 0;
			}
			activeTime--;
		} else if (cooldownTime > 0) {
			if (ability != null && !ended) {
				setEnded(player);
			}
			cooldownTime--;
		} else {
			HexNetwork.INSTANCE.sendTo(new ActionTextMessage(3, ability.getId()), (EntityPlayerMP) player);
			ability = null;
			ACTIVE_HANDLERS.remove(player.getUniqueID());
		}
	}
	
	public void setEnded(EntityPlayer player) {
		ability.end(player);
		ended = true;
		activeTime = 0;
		if (ability.getDuration() >= 100) HexNetwork.INSTANCE.sendTo(new ActionTextMessage(2, ability.getId()), (EntityPlayerMP) player);
	}

	public void clearCooldown() {
		cooldownTime = 0;
	}
}
