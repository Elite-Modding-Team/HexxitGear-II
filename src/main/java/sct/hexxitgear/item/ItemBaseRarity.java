package sct.hexxitgear.item;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IRarity;
import sct.hexxitgear.init.HexRegistry;
import shadows.placebo.item.ItemBase;
import shadows.placebo.registry.RegistryInformation;

public class ItemBaseRarity extends ItemBase {

    public ItemBaseRarity(String name, RegistryInformation info) {
        super(name, info);
    }

    @Override
    public IRarity getForgeRarity(ItemStack stack) {
    	if (this == HexRegistry.HEXICAL_MASTER_SWORD_BLADE || this == HexRegistry.HEXICAL_MASTER_SWORD_HILT) {
            return HexRegistry.RARITY_INACTIVE;
        }

        return EnumRarity.COMMON;
    }
}
