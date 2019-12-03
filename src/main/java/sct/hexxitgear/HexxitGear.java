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

package sct.hexxitgear;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import sct.hexxitgear.core.ArmorSet;
import sct.hexxitgear.init.HexConfig;
import sct.hexxitgear.init.HexRegistry;
import sct.hexxitgear.net.HexNetwork;
import shadows.placebo.recipe.RecipeHelper;

@Mod(HexxitGear.MODID)
public class HexxitGear {

	public static final String MODID = "hexxitgear";
	public static final String MODNAME = "Hexxit Gear";
	public static final String VERSION = "2.8.0";

	public static final Logger LOGGER = LogManager.getLogger(MODID);

	public static final RecipeHelper HELPER = new RecipeHelper(MODID);

	public HexxitGear() {
		HexConfig.loadCommonConfig();
		FMLJavaModLoadingContext.get().getModEventBus().register(new HexRegistry());
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
	}

	@SubscribeEvent
	public void setup(FMLCommonSetupEvent e) {
		HexNetwork.init();
		MinecraftForge.EVENT_BUS.register(ArmorSet.class);
	}

}
