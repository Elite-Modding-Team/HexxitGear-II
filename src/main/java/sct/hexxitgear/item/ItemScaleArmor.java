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
import sct.hexxitgear.model.ModelScaleHelmet;

public class ItemScaleArmor extends ItemHexxitArmor {

    public ItemScaleArmor(String regname, EntityEquipmentSlot slot) {
        super(regname, ArmorSet.SCALE, 1, slot);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        if (slot == EntityEquipmentSlot.HEAD && !(stack.getItem() == HexRegistry.ANCIENT_SCALE_HELMET))
            return "hexxitgear:textures/maps/scale_helm.png";
        if (slot == EntityEquipmentSlot.HEAD && stack.getItem() == HexRegistry.ANCIENT_SCALE_HELMET)
            return "hexxitgear:textures/maps/ancient_scale_helm.png";

        if (stack.getItem() == HexRegistry.SCALE_LEGS) return "hexxitgear:textures/armor/scale2.png";
        if (stack.getItem() == HexRegistry.ANCIENT_SCALE_LEGS) return "hexxitgear:textures/armor/ancient_scale2.png";

        if (stack.getItem() == HexRegistry.ANCIENT_SCALE_CHEST || stack.getItem() == HexRegistry.ANCIENT_SCALE_BOOTS)
            return "hexxitgear:textures/armor/ancient_scale.png";
        return "hexxitgear:textures/armor/scale.png";
    }

    @SideOnly(Side.CLIENT)
    private static ModelScaleHelmet scaleHelmet;

    @SideOnly(Side.CLIENT)
    private ModelBiped getHelmet() {
        if (scaleHelmet == null) scaleHelmet = new ModelScaleHelmet();
        return scaleHelmet;
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
