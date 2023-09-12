package sct.hexxitgear.core;

import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import sct.hexxitgear.HexxitGear;
import sct.hexxitgear.core.potion.PotionThief;

@Mod.EventBusSubscriber
public class PotionHandler {

    public static final Potion THIEF = new PotionThief().setPotionName("effect." + HexxitGear.MODID + ".thief.name").setRegistryName(HexxitGear.MODID, "thief");

    @SubscribeEvent
    public static void onRegisterPotion(RegistryEvent.Register<Potion> event) {
        event.getRegistry().register(THIEF);
    }
}
