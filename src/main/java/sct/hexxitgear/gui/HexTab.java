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

package sct.hexxitgear.gui;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import sct.hexxitgear.init.HexRegistry;

public class HexTab extends ItemGroup {

	public static final HexTab INSTANCE = new HexTab("Hexxit Gear");

	public HexTab(String label) {
		super(label);
	}

	@Override
	public ItemStack createIcon() {
		return new ItemStack(HexRegistry.TRIBAL_HELMET);
	}

	@Override
	public String getTranslationKey() {
		return "gui.hexxitgear.creativetab";
	}
}
