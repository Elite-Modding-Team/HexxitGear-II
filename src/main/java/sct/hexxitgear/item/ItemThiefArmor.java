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
import sct.hexxitgear.proxy.ClientProxy;

public class ItemThiefArmor extends ItemHexxitArmor {

    public ItemThiefArmor(String regname, EntityEquipmentSlot slot, boolean ancient) {
        super(regname, ArmorSet.THIEF, 0, slot);
        this.hoodTexture = ancient ? "hexxitgear:textures/maps/ancient_thief_hood.png" : "hexxitgear:textures/maps/thief_hood.png";
        this.bodyTexture = ancient ? "hexxitgear:textures/armor/ancient_thief2.png" : "hexxitgear:textures/armor/thief2.png";
        this.overlayTexture = ancient ? "hexxitgear:textures/armor/ancient_thief.png" : "hexxitgear:textures/armor/thief.png";
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected ModelBiped getHeadModel(ItemStack itemStack) {
        return ClientProxy.THIEF_HOOD;
    }
}
