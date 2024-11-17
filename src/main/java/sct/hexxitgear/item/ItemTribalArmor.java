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
import sct.hexxitgear.init.HexRegistry;
import sct.hexxitgear.proxy.ClientProxy;

public class ItemTribalArmor extends ItemHexxitArmor {

    public ItemTribalArmor(String regname, EntityEquipmentSlot slot, boolean ancient) {
        super(regname, ArmorSet.TRIBAL, 0, slot);
        this.hoodTexture = ancient ? "hexxitgear:textures/maps/ancient_tribal_skull.png" : "hexxitgear:textures/maps/tribal_skull.png";
        this.bodyTexture = ancient ? "hexxitgear:textures/armor/ancient_tribal2.png" : "hexxitgear:textures/armor/tribal2.png";
        this.overlayTexture = ancient ? "hexxitgear:textures/armor/ancient_tribal.png" : "hexxitgear:textures/armor/tribal.png";
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected ModelBiped getHeadModel(ItemStack itemStack) {
        return itemStack.getItem() == HexRegistry.ANCIENT_TRIBAL_HELMET ? ClientProxy.ANCIENT_TRIBAL_SKULL : ClientProxy.TRIBAL_SKULL;
    }
}
