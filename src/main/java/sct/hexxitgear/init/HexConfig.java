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

package sct.hexxitgear.init;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.fml.loading.FMLPaths;
import sct.hexxitgear.HexxitGear;
import sct.hexxitgear.core.ArmorSet;
import shadows.placebo.config.Configuration;

public class HexConfig {

	public static File configFolder;

	public static Configuration config;

	private static final List<Integer> DIM_BLACKLIST = new ArrayList<Integer>();
	private static int hexbiscusChance = 0;

	public static void loadCommonConfig() {
		config = new Configuration(new File(FMLPaths.CONFIGDIR.get().toFile(), HexxitGear.MODID + ".cfg"));
		config.load();

		registerDimBlacklist(config.getStringList("Dimensional Blacklist", "worldgen", new String[0], "Dimensions where hexbiscuses will not generate. New line per id."));
		ArmorSet.classloadForConfigs();
		hexbiscusChance = config.getInt("Hexbiscus Chance", "worldgen", 50, 1, 600, "The 1/n chance for a hexbiscus to generate. Lower numbers means more.");

		if (config.hasChanged()) config.save();
	}

	private static void registerDimBlacklist(String[] parsed) {
		for (String dim : parsed) {
			Integer dimID = Integer.parseInt(dim);
			if (!DIM_BLACKLIST.contains(dimID)) DIM_BLACKLIST.add(dimID);
		}
	}

	public static List<Integer> getDimBlacklist() {
		return DIM_BLACKLIST;
	}

	public static int getHexbiscusChance() {
		return hexbiscusChance;
	}
}
