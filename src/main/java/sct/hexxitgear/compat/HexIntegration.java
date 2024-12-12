package sct.hexxitgear.compat;

import net.minecraftforge.fml.common.Loader;

public class HexIntegration {
    public static void preInit() {
        if (Loader.isModLoaded("tconstruct")) {
            HexTinkersPlugin.registerToolMaterials();
        }
    }

    public static void init() {
    }

    public static void postInit() {
    }
}
