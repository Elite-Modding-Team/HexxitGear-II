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

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sct.hexxitgear.core.ArmorSet;
import sct.hexxitgear.init.HexRegistry;
import sct.hexxitgear.model.ModelSkullHelmet;

public class ItemTribalArmor extends ItemHexxitArmor {

	public ItemTribalArmor(String regname, EntityEquipmentSlot slot) {
		super(regname, ArmorSet.TRIBAL, 0, slot);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		if (slot == EntityEquipmentSlot.HEAD) return "hexxitgear:textures/maps/tribal_skull.png";

		if (stack.getItem() == HexRegistry.TRIBAL_LEGS) return "hexxitgear:textures/armor/tribal2.png";

		return "hexxitgear:textures/armor/tribal.png";
	}

	@SideOnly(Side.CLIENT)
	private static ModelSkullHelmet skullHelmet;

	@SideOnly(Side.CLIENT)
	private ModelSkullHelmet getHelmet() {
		if (skullHelmet == null) skullHelmet = new ModelSkullHelmet();
		return skullHelmet;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
		if (armorSlot == EntityEquipmentSlot.HEAD) {
			ModelBiped skull = getHelmet();
			skull.isSneak = entityLiving.isSneaking();
			return skull;
		}
		return null;
	}

}
