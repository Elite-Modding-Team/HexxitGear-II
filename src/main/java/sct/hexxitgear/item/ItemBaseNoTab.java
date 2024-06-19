package sct.hexxitgear.item;

import shadows.placebo.item.ItemBase;
import shadows.placebo.registry.RegistryInformation;

public class ItemBaseNoTab extends ItemBase {

    public ItemBaseNoTab(String name, RegistryInformation info) {
        super(name, info);
        setCreativeTab(null);
    }
}
