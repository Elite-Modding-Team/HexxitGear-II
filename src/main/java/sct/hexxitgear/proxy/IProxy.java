package sct.hexxitgear.proxy;

import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.text.ITextComponent;

import java.awt.*;

public interface IProxy {

	void registerKeybinds();

	void setActionText(ITextComponent message);

	void spawnParticle(EnumParticleTypes type, double x, double y, double z, Color color, double velX, double velY, double velZ);
}
