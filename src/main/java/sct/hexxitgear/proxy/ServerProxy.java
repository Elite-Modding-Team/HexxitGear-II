package sct.hexxitgear.proxy;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import sct.hexxitgear.init.HexRegistry;

public class ServerProxy implements IProxy {

    @Override
    public void registerKeybinds() {
    }

    @Override
    public void setActionText(ITextComponent message) {
    }

    @SubscribeEvent
    public void onAnvilUpdate(AnvilUpdateEvent event) {
        ItemStack left = event.getLeft();
        ItemStack right = event.getRight();
        ItemStack outputStack = left.copy();

        // Hexical Master Sword
        if (left.getItem() == HexRegistry.HEXICAL_MASTER_SWORD_HILT && right.getItem() == HexRegistry.HEXICAL_MASTER_SWORD_BLADE) {
            event.setCost(20);
            event.setOutput(new ItemStack(HexRegistry.HEXICAL_MASTER_SWORD_INACTIVE).copy());
            event.getOutput().setTagCompound(outputStack.getTagCompound());
            event.setMaterialCost(1);
        }
    }
}
