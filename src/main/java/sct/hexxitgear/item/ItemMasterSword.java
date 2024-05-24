package sct.hexxitgear.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;
import sct.hexxitgear.HexxitGear;
import sct.hexxitgear.gui.HexTab;
import sct.hexxitgear.init.HexRegistry;

public class ItemMasterSword extends ItemSword {

    private final boolean inactive;
    private boolean highUp;

    public ItemMasterSword(String regname, ToolMaterial material) {
        super(material);
        setCreativeTab(HexTab.INSTANCE);
        setRegistryName(HexxitGear.MODID, regname);
        setTranslationKey(HexxitGear.MODID + "." + regname);
        inactive = regname.contains("inactive");
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return inactive && highUp;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, world, entity, itemSlot, isSelected);

        if (!world.isRemote && inactive && isSelected && entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            if (player.posY > 128) {
                highUp = true;
                if (world.isThundering()) {
                    world.addWeatherEffect(new EntityLightningBolt(world, player.posX, player.posY, player.posZ, true));
                    player.inventory.deleteStack(stack);
                    player.inventory.add(itemSlot, new ItemStack(HexRegistry.HEXICAL_MASTER_SWORD));
                }
            }
        }
    }
}
