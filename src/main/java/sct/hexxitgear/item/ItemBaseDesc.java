package sct.hexxitgear.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sct.hexxitgear.HexxitGear;
import shadows.placebo.item.ItemBase;
import shadows.placebo.registry.RegistryInformation;

public class ItemBaseDesc extends ItemBase {

    protected String tooltip = null;

    public ItemBaseDesc(String name, RegistryInformation info) {
        super(name, info);
        this.tooltip = "tooltip." + HexxitGear.MODID + "." + name;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flag) {
        list.add(TextFormatting.WHITE + I18n.format(tooltip));
    }
}
