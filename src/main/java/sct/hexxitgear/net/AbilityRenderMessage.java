package sct.hexxitgear.net;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import sct.hexxitgear.core.ability.Ability;
import shadows.placebo.util.NetworkUtils;
import shadows.placebo.util.NetworkUtils.MessageProvider;

public class AbilityRenderMessage extends MessageProvider<AbilityRenderMessage> {

	private int messageId;
	private int abilityId;
	private int player;
	private int duration;

	public AbilityRenderMessage(int messageId, int abilityId, PlayerEntity player, int duration) {
		this.messageId = messageId;
		this.abilityId = abilityId;
		this.player = player.getEntityId();
		this.duration = duration;
	}

	public AbilityRenderMessage() {
	}

	@Override
	public void write(AbilityRenderMessage msg, PacketBuffer buf) {
		buf.writeInt(msg.messageId);
		buf.writeInt(msg.abilityId);
		buf.writeInt(msg.player);
		buf.writeInt(msg.duration);
	}

	@Override
	public AbilityRenderMessage read(PacketBuffer buf) {
		AbilityRenderMessage msg = new AbilityRenderMessage();
		msg.messageId = buf.readInt();
		msg.abilityId = buf.readInt();
		msg.player = buf.readInt();
		msg.duration = buf.readInt();
		return msg;
	}

	@Override
	public void handle(AbilityRenderMessage msg, Supplier<Context> ctx) {
		NetworkUtils.handlePacket(() -> () -> {
			PlayerEntity player = (PlayerEntity) Minecraft.getInstance().world.getEntityByID(msg.player);
			if (msg.messageId == 0) Ability.ABILITIES.get(msg.abilityId).renderFirst(player);
			else Ability.ABILITIES.get(msg.abilityId).renderAt(player, msg.duration);
		}, ctx.get());
	}

}
