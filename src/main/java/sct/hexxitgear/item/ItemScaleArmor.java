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
import sct.hexxitgear.model.ModelScaleFullHelm;

public class ItemScaleArmor extends ItemHexxitArmor {

    @SideOnly(Side.CLIENT)
    private static final ModelScaleFullHelm SCALE_FULL_HELM = new ModelScaleFullHelm();

    public ItemScaleArmor(String regname, EntityEquipmentSlot slot, boolean ancient) {
        super(regname, ArmorSet.SCALE, 1, slot);
        this.hoodTexture = ancient ? "hexxitgear:textures/maps/ancient_scale_helm.png" : "hexxitgear:textures/maps/scale_helm.png";
        this.bodyTexture = ancient ? "hexxitgear:textures/armor/ancient_scale2.png" : "hexxitgear:textures/armor/scale2.png";
        this.overlayTexture = ancient ? "hexxitgear:textures/armor/ancient_scale.png" : "hexxitgear:textures/armor/scale.png";
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected ModelBiped getHeadModel(ItemStack itemStack) {
        return SCALE_FULL_HELM;
    }
}
