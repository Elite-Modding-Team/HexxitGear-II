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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sct.hexxitgear.core.ArmorSet;
import sct.hexxitgear.init.HexRegistry;
import sct.hexxitgear.model.ModelHoodHelmet;

public class ItemThiefArmor extends ItemHexxitArmor {

	public ItemThiefArmor(String regname, EntityEquipmentSlot slot) {
		super(regname, ArmorSet.THIEF, 0, slot);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			if (player.isPotionActive(MobEffects.INVISIBILITY)) return "hexxitgear:textures/armor/invisible.png";
		}

		// If the helmet slot, return helmet texture map
		if (slot == EntityEquipmentSlot.HEAD) return "hexxitgear:textures/maps/thief_hood.png";

		if (stack.getItem() == HexRegistry.THIEF_LEGS) return "hexxitgear:textures/armor/thief2.png";

		return "hexxitgear:textures/armor/thief.png";
	}

	@SideOnly(Side.CLIENT)
	private static ModelHoodHelmet hoodHelmet;

	@SideOnly(Side.CLIENT)
	private ModelBiped getHelmet() {
		if (hoodHelmet == null) hoodHelmet = new ModelHoodHelmet();

		return hoodHelmet;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
		if (armorSlot == EntityEquipmentSlot.HEAD) {
			ModelBiped helmet = getHelmet();
			helmet.isSneak = entityLiving.isSneaking();
			return helmet;
		}
		return null;
	}

}
