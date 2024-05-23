package sct.hexxitgear.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;
import sct.hexxitgear.HexxitGear;

public class ItemMasterSword  extends ItemSword {

    public ItemMasterSword() {
        super(Item.ToolMaterial.DIAMOND);
        setRegistryName("hexical_master_sword");
        setTranslationKey(HexxitGear.MODID + ".hexical_master_sword");
    }
}
