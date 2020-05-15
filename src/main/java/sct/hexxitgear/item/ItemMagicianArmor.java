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

import java.util.List;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sct.hexxitgear.gui.HexTab;
import sct.hexxitgear.init.HexRegistry;
import sct.hexxitgear.model.ModelSageHood;

public class ItemMagicianArmor extends ItemHexxitArmor {

	public ItemMagicianArmor(EquipmentSlotType slot) {
		super(ArmorMaterial.DIAMOND, slot, new Item.Properties().group(HexTab.INSTANCE));
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			if (player.isPotionActive(MobEffects.INVISIBILITY)) return "hexxitgear:textures/armor/invisible.png";
		}

		// If the helmet slot, return helmet texture map
		if (slot == EquipmentSlotType.HEAD) return "hexxitgear:textures/maps/sage_hood.png";

		if (stack.getItem() == HexRegistry.SAGE_LEGS) return "hexxitgear:textures/armor/sage2.png";

		return "hexxitgear:textures/armor/sage.png";
	}

	@SideOnly(Side.CLIENT)
	private static ModelSageHood hood;

	@SideOnly(Side.CLIENT)
	protected ModelSageHood getHoodModel() {
		if (hood == null) hood = new ModelSageHood();
		return hood;
	}

	@Override
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
		if (source.isMagicDamage()) return new ArmorProperties(1, damageReduceAmount / 15D, armor.getMaxDamage() + 5);
		return super.getProperties(player, armor, source, damage, slot);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, ModelBiped _default) {
		if (armorSlot == EquipmentSlotType.HEAD) {
			ModelBiped retVal = getHoodModel();
			retVal.isSneak = entityLiving.isSneaking();
			return retVal;
		}
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List<String> list, ITooltipFlag flag) {
		list.add(TextFormatting.DARK_PURPLE + I18n.format("gui.hexxitgear.set.sage"));
	}
}
