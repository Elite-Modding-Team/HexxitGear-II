package sct.hexxitgear.compat;

import net.minecraft.item.ItemStack;
import sct.hexxitgear.compat.traits.TraitHexicalAdventurer;
import sct.hexxitgear.compat.traits.TraitHexicalSpark;
import sct.hexxitgear.init.HexRegistry;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.BowMaterialStats;
import slimeknights.tconstruct.library.materials.ExtraMaterialStats;
import slimeknights.tconstruct.library.materials.HandleMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.materials.MaterialTypes;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.HarvestLevels;
import slimeknights.tconstruct.tools.TinkerMaterials;

public class HexTinkersPlugin {
    public static final Material HEXICAL_DIAMOND = new Material("hexical_diamond", 0x185ACC);

    public static final AbstractTrait HEXICAL_ADVENTURER = new TraitHexicalAdventurer();
    public static final AbstractTrait HEXICAL_SPARK = new TraitHexicalSpark();

    public static void registerToolMaterials() {
        TinkerMaterials.materials.add(HEXICAL_DIAMOND);
        TinkerRegistry.integrate(HEXICAL_DIAMOND).preInit();
        HEXICAL_DIAMOND.addCommonItems("Hexical");
        HEXICAL_DIAMOND.addItem(new ItemStack(HexRegistry.HEXICAL_DIAMOND), 1, Material.VALUE_Ingot);
        HEXICAL_DIAMOND.setRepresentativeItem(HexRegistry.HEXICAL_DIAMOND);
        HEXICAL_DIAMOND.setCraftable(true).setCastable(false);
        TinkerRegistry.addMaterialStats(HEXICAL_DIAMOND,
                new HeadMaterialStats(1270, 8.00F, 5.00F, HarvestLevels.DIAMOND),
                new HandleMaterialStats(1.10F, 100),
                new ExtraMaterialStats(269),
                new BowMaterialStats(0.5F, 1.5F, 1.75F));
        HEXICAL_DIAMOND.addTrait(HEXICAL_SPARK, MaterialTypes.HEAD);
        HEXICAL_DIAMOND.addTrait(HEXICAL_ADVENTURER);
    }
}
