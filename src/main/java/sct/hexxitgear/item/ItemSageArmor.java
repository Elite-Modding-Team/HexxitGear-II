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
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sct.hexxitgear.core.ArmorSet;
import sct.hexxitgear.model.ModelSageCap;

public class ItemSageArmor extends ItemHexxitArmor {

    @SideOnly(Side.CLIENT)
    private static final ModelSageCap SAGE_CAP = new ModelSageCap();

    public ItemSageArmor(String regname, EntityEquipmentSlot slot, boolean ancient) {
        super(regname, ArmorSet.SAGE, 0, slot);
        this.hoodTexture = "hexxitgear:textures/maps/sage_hood.png";
        this.bodyTexture = "hexxitgear:textures/armor/sage2.png";
        this.overlayTexture = "hexxitgear:textures/armor/sage.png";
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected ModelBiped getHeadModel(ItemStack itemStack) {
        return SAGE_CAP;
    }
}
