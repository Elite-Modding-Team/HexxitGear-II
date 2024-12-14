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

package sct.hexxitgear.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import sct.hexxitgear.HexxitGear;
import sct.hexxitgear.gui.HexTab;
import shadows.placebo.client.IHasModel;

public class BlockHexicalDiamond extends Block implements IHasModel {

    public BlockHexicalDiamond() {
        super(Material.IRON, MapColor.BLUE);
        setCreativeTab(HexTab.INSTANCE);
        setRegistryName("hexical_diamond_block");
        setTranslationKey(HexxitGear.MODID + ".hexical_diamond_block");
        setSoundType(SoundType.METAL);
        setHardness(10.0F);
        setResistance(20.0F);
        HexxitGear.INFO.getItemList().add(new ItemBlock(this).setRegistryName(getRegistryName()));
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
        items.add(new ItemStack(this));
    }
}
