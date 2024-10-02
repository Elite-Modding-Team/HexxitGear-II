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

import net.minecraftforge.fml.common.FMLCommonHandler;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import sct.hexxitgear.core.ArmorSet;
import sct.hexxitgear.gui.HexTab;
import sct.hexxitgear.init.HexConfig;
import sct.hexxitgear.init.HexRegistry;
import sct.hexxitgear.net.HexNetwork;
import sct.hexxitgear.proxy.IProxy;
import sct.hexxitgear.world.HexGenerator;
import shadows.placebo.registry.RegistryInformation;
import shadows.placebo.util.RecipeHelper;

@Mod(modid = HexxitGear.MODID, name = HexxitGear.MODNAME, version = HexxitGear.VERSION, dependencies = "required-after:placebo@[1.2.0,);required-after:elenaidodge@[,1.13.2];after:cqrepoured;after:mod_lavacow;after:mowziesmobs")
public class HexxitGear {

	public static final String MODID = "hexxitgear";
	public static final String MODNAME = "Hexxit Gear II";
	public static final String VERSION = "2.9.0";

	@SidedProxy(clientSide = "sct.hexxitgear.proxy.ClientProxy", serverSide = "sct.hexxitgear.proxy.ServerProxy")
	public static IProxy proxy;

	public static Logger logger;

	public static final RegistryInformation INFO = new RegistryInformation(MODID, HexTab.INSTANCE);
	public static final RecipeHelper HELPER = new RecipeHelper(MODID, MODNAME, INFO.getRecipeList());

	@EventHandler
	public void preInit(FMLPreInitializationEvent evt) {
		logger = evt.getModLog();
		HexConfig.loadCommonConfig(evt);
		MinecraftForge.EVENT_BUS.register(new HexRegistry());
	}

	@EventHandler
	public void init(FMLInitializationEvent evt) {
		HexNetwork.init();
		proxy.registerKeybinds();
		MinecraftForge.TERRAIN_GEN_BUS.register(new HexGenerator());
		MinecraftForge.EVENT_BUS.register(ArmorSet.class);
	}

}
