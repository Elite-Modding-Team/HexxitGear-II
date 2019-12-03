package sct.hexxitgear.net;

import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import sct.hexxitgear.core.AbilityHandler;
import shadows.placebo.util.NetworkUtils;
import shadows.placebo.util.NetworkUtils.MessageProvider;

public class ActivateMessage extends MessageProvider<ActivateMessage> {

	public ActivateMessage() {
	}

	@Override
	public void write(ActivateMessage msg, PacketBuffer buf) {

	}

	@Override
	public ActivateMessage read(PacketBuffer buf) {
		return new ActivateMessage();
	}

	@Override
	public void handle(ActivateMessage msg, Supplier<Context> ctx) {
		NetworkUtils.handlePacket(() -> () -> {
			AbilityHandler.activateAbility(ctx.get().getSender());
		}, ctx.get());
	}

}
