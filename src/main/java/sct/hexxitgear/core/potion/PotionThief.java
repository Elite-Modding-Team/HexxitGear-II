package sct.hexxitgear.core.potion;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
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
        if (!(entity instanceof EntityPlayer)) return;
        EntityPlayer player = (EntityPlayer) entity;
        player.landMovementFactor = 0.15F;
        player.jumpMovementFactor = player.landMovementFactor * 0.5F;
        if (player.onGround && !player.capabilities.isFlying && player.moveForward > 0.0F) {
            float speedBoost = 0.05F;
            if (player.isInWater()) {
                speedBoost /= 4.0F;
            }
            player.moveRelative(0.0F, 0.0F, speedBoost, 1.0F);
        }
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
