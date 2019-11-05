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

package sct.hexxitgear.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.world.World;
import sct.hexxitgear.core.AbilityHandler;
import sct.hexxitgear.core.ArmorSet;
import sct.hexxitgear.init.HexRegistry;

public class ItemHexxitArmor extends ArmorItem {

	public ItemHexxitArmor(IArmorMaterial material, EquipmentSlotType slot, Item.Properties props) {
		super(material, slot, props);
	}

	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		if (world.isRemote) return;
		if (this.slot != EquipmentSlotType.HEAD) return;

		ArmorSet set = ArmorSet.getCurrentArmorSet(player);
		if (set != null) set.applyBuffs(player);

		AbilityHandler handler = AbilityHandler.getActiveAbility(player);

		if (handler != null) {
			handler.onTick(player);
		}

		if (set != null) ArmorSet.CACHED_SETS.put(player.getUniqueID(), set);
	}

	@Override
	public Rarity getRarity(ItemStack stack) {
		return Rarity.UNCOMMON;
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		return toRepair.isDamaged() && repair.getItem() == HexRegistry.HEXICAL_ESSENCE;
	}
}
