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

package sct.hexxitgear.control;

import org.lwjgl.glfw.GLFW;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import sct.hexxitgear.core.ArmorSet;
import sct.hexxitgear.net.ActivateMessage;
import sct.hexxitgear.net.HexNetwork;

public class HexKeybinds {

	public static KeyBinding activateHexxitArmor = new KeyBinding("Activate Hexxit Gear Armor", GLFW.GLFW_KEY_X, "Hexxit Gear");

	public HexKeybinds() {
		ClientRegistry.registerKeyBinding(activateHexxitArmor);
	}

	@SubscribeEvent
	public void keyEvent(InputEvent.KeyInputEvent event) {
		if (!ChatScreen.class.equals(Minecraft.getInstance().currentScreen.getClass()) && activateHexxitArmor.isPressed() && ArmorSet.getCurrentArmorSet(Minecraft.getInstance().player) != null) HexNetwork.INSTANCE.sendToServer(new ActivateMessage());
	}

}
