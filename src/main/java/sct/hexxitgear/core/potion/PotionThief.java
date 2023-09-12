package sct.hexxitgear.core.potion;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sct.hexxitgear.HexxitGear;

public class PotionThief extends Potion {

    public static final ResourceLocation THIEF_ICON = new ResourceLocation(HexxitGear.MODID, "textures/items/thief_boots.png");

    public PotionThief() {
        super(false, 11669505);
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {
        entity.landMovementFactor = 0.15F;
        entity.jumpMovementFactor = entity.landMovementFactor * 0.5F;
    }

    @Override
    public final boolean isReady(int duration, int amplifier) {
        return duration > 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderInventoryEffect(PotionEffect effect, Gui gui, int x, int y, float z) {
        Minecraft.getMinecraft().renderEngine.bindTexture(THIEF_ICON);
        Gui.drawModalRectWithCustomSizedTexture(x + 6, y + 6, 0, 0, 18, 18, 18, 18);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderHUDEffect(PotionEffect effect, Gui gui, int x, int y, float z, float alpha) {
        Minecraft.getMinecraft().renderEngine.bindTexture(THIEF_ICON);
        Gui.drawModalRectWithCustomSizedTexture(x + 3, y + 3, 0, 0, 18, 18, 18, 18);
    }
}
