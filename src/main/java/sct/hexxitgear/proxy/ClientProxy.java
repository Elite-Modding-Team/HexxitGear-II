/*
 * HexxitGear
 * Copyright (C) 2013  Ryan Cohen
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package sct.hexxitgear.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import sct.hexxitgear.HexxitGear;
import sct.hexxitgear.control.HexKeybinds;
import sct.hexxitgear.init.HexRegistry;
import shadows.placebo.client.IHasModel;

import java.awt.*;

@SuppressWarnings("deprecation")
@EventBusSubscriber(Side.CLIENT)
public class ClientProxy implements IProxy {

    @SubscribeEvent
    public static void modelRegistry(ModelRegistryEvent e) {
        for (Item i : HexxitGear.INFO.getItemList())
            if (i instanceof IHasModel) ((IHasModel) i).initModels(e);
        HexRegistry.HEXBISCUS.initModels(e);

        OBJLoader.INSTANCE.addDomain(HexxitGear.MODID);
        for (final Item item : ForgeRegistries.ITEMS.getValues()) {
            if (item.equals(HexRegistry.HEXICAL_MASTER_SWORD_INACTIVE) || item.equals(HexRegistry.HEXICAL_MASTER_SWORD)) {
                ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "normal"));
            }
        }
    }

    public static void spawnParticle(EnumParticleTypes type, double x, double y, double z, Color color, double velX, double velY, double velZ) {
        Particle particle = Minecraft.getMinecraft().effectRenderer.spawnEffectParticle(type.getParticleID(), x, y, z, velX, velY, velZ);
        float randBrightness = 0.5F + (float) Math.random();
        particle.setRBGColorF((color.getRed() / 255.0F) * randBrightness, (color.getGreen() / 255.0F) * randBrightness, (color.getBlue() / 255.0F) * randBrightness);
    }

    @Override
    public void registerKeybinds() {
        MinecraftForge.EVENT_BUS.register(new HexKeybinds());
    }

    @Override
    public void setActionText(ITextComponent message) {
        Minecraft.getMinecraft().ingameGUI.setOverlayMessage(message, false);
    }
}
