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

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sct.hexxitgear.HexxitGear;
import sct.hexxitgear.core.ArmorSet;
import sct.hexxitgear.gui.HexTab;
import sct.hexxitgear.init.HexRegistry;
import shadows.placebo.client.IHasModel;

import javax.annotation.Nullable;
import java.util.List;

public class ItemHexxitArmor extends ItemArmor implements ISpecialArmor, IHasModel {

	private final ArmorSet set;

	public ItemHexxitArmor(String regname, ArmorSet set, int renderindex, EntityEquipmentSlot slot) {
		super(set.getMaterial(), renderindex, slot);
		this.set = set;
		setCreativeTab(HexTab.INSTANCE);
		setRegistryName(HexxitGear.MODID, regname);
		setTranslationKey(HexxitGear.MODID + "." + regname);
		HexxitGear.INFO.getItemList().add(this);
	}

	public ArmorSet getSet() {
		return set;
	}

	@Override
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
		return new ArmorProperties(0, 0, Integer.MAX_VALUE);
	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		return 0;
	}

	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
		if (entity instanceof EntityPlayer && !(((EntityPlayer) entity).capabilities.isCreativeMode)) {
			if (stack.getItemDamage() < stack.getMaxDamage()) {
				stack.setItemDamage(stack.getItemDamage() + 1);
			}
		}
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		if (world.isRemote) return;
		if (this.armorType != EntityEquipmentSlot.HEAD) return;

		ArmorSet set = ArmorSet.getCurrentArmorSet(player);
		if (set != null) {
			ArmorSet.CACHED_SETS.put(player.getUniqueID(), set);
			set.applyBuffs(player);
		}
	}

	@Override
	public IRarity getForgeRarity(ItemStack stack) {
		return EnumRarity.UNCOMMON;
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		return toRepair.isItemDamaged() && repair.getItem() == HexRegistry.HEXICAL_ESSENCE;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flags) {
		set.addTooltip(tooltip);
	}

}
