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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import sct.hexxitgear.core.ability.Ability;
import sct.hexxitgear.core.ability.AbilityLift;
import sct.hexxitgear.core.ability.AbilityRampage;
import sct.hexxitgear.core.ability.AbilityShield;
import sct.hexxitgear.core.ability.AbilityStealth;
import sct.hexxitgear.core.buff.BuffMagicianSet;
import sct.hexxitgear.core.buff.BuffScaleSet;
import sct.hexxitgear.core.buff.BuffThiefSet;
import sct.hexxitgear.core.buff.BuffTribalSet;
import sct.hexxitgear.core.buff.IBuffHandler;
import sct.hexxitgear.init.HexRegistry;

public class ArmorSet {

	public static final Map<UUID, ArmorSet> CACHED_SETS = new HashMap<>();

	public static final List<ArmorSet> SETS = new ArrayList<>();

	public static final ArmorSet TRIBAL = new ArmorSet("Tribal", new Item[] { HexRegistry.TRIBAL_HELMET, HexRegistry.TRIBAL_CHEST, HexRegistry.TRIBAL_LEGS, HexRegistry.TRIBAL_BOOTS }, new BuffTribalSet(), new AbilityRampage());
	public static final ArmorSet THIEF = new ArmorSet("Thief", new Item[] { HexRegistry.THIEF_HELMET, HexRegistry.THIEF_CHEST, HexRegistry.THIEF_LEGS, HexRegistry.THIEF_BOOTS }, new BuffThiefSet(), new AbilityStealth());
	public static final ArmorSet SCALE = new ArmorSet("Scale", new Item[] { HexRegistry.SCALE_HELMET, HexRegistry.SCALE_CHEST, HexRegistry.SCALE_LEGS, HexRegistry.SCALE_BOOTS }, new BuffScaleSet(), new AbilityShield());
	public static final ArmorSet SAGE = new ArmorSet("Sage", new Item[] { HexRegistry.SAGE_HELMET, HexRegistry.SAGE_CHEST, HexRegistry.SAGE_LEGS, HexRegistry.SAGE_BOOTS }, new BuffMagicianSet(), new AbilityLift());

	private final Item[] armors;
	private final String name;
	private final IBuffHandler buffHandler;
	private final Ability ability;

	public ArmorSet(String name, Item[] armor, IBuffHandler buffHandler, Ability ability) {
		this.name = name;
		this.armors = armor;
		this.buffHandler = buffHandler;
		this.ability = ability;
		SETS.add(this);
	}

	public String getName() {
		return name;
	}

	public Item[] getArmors() {
		return armors;
	}

	public Ability getAbility() {
		return ability;
	}

	public void applyBuffs(PlayerEntity player) {
		buffHandler.applyPlayerBuffs(player);
	}

	public void removeBuffs(PlayerEntity player) {
		buffHandler.removePlayerBuffs(player);
	}

	@Nullable
	public static ArmorSet getCurrentArmorSet(PlayerEntity player) {
		Iterable<ItemStack> curArmor = player.getArmorInventoryList();
		for (ArmorSet set : SETS) {
			int i = 0;
			int matched = 0;
			for (ItemStack s : curArmor)
				if (!s.isEmpty() && s.getItem() == set.armors[3 - i++]) ++matched;

			if (matched == 4) return set;
		}
		return null;
	}

	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent e) {
		ArmorSet s = CACHED_SETS.get(e.player.getUniqueID());
		if (!e.player.world.isRemote && s != null && getCurrentArmorSet(e.player) != s) {
			s.removeBuffs(e.player);
			CACHED_SETS.put(e.player.getUniqueID(), null);
		}
	}

	public static void classloadForConfigs() {
	}
}
