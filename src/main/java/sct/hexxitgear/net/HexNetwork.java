package sct.hexxitgear.net;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import sct.hexxitgear.HexxitGear;
import shadows.placebo.util.NetworkUtils;

public class HexNetwork {

	//Formatter::off
    public static final SimpleChannel INSTANCE	 = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(HexxitGear.MODID, "channel"))
            .clientAcceptedVersions(s->true)
            .serverAcceptedVersions(s->true)
            .networkProtocolVersion(() -> "1.0.0")
            .simpleChannel();
    //Formatter::on

	public static void init() {
		NetworkUtils.registerMessage(INSTANCE, 0, new AbilityRenderMessage());
		NetworkUtils.registerMessage(INSTANCE, 1, new ActivateMessage());
		NetworkUtils.registerMessage(INSTANCE, 2, new ActionTextMessage());

	}
}