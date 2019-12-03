package sct.hexxitgear.net;

import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import sct.hexxitgear.HexClient;
import sct.hexxitgear.core.ability.Ability;
import shadows.placebo.util.NetworkUtils;
import shadows.placebo.util.NetworkUtils.MessageProvider;

public class ActionTextMessage extends MessageProvider<ActionTextMessage> {

	private int messageId;
	private int abilityId;

	public ActionTextMessage() {
	}

	public ActionTextMessage(int id1, int id2) {
		messageId = id1;
		abilityId = id2;
	}

	@Override
	public void write(ActionTextMessage msg, PacketBuffer buf) {
		buf.writeInt(msg.messageId);
		buf.writeInt(msg.abilityId);
	}

	@Override
	public ActionTextMessage read(PacketBuffer buf) {
		ActionTextMessage msg = new ActionTextMessage();
		msg.messageId = buf.readInt();
		msg.abilityId = buf.readInt();
		return msg;
	}

	@Override
	public void handle(ActionTextMessage msg, Supplier<Context> ctx) {
		NetworkUtils.handlePacket(() -> () -> {
			Ability ability = Ability.ABILITIES.get(msg.abilityId);
			if (ability.isInstant() && msg.messageId == 2) return;
			switch (msg.messageId) {
			case 0:
				HexClient.setActionText(new TranslationTextComponent("ability.hexxitgear.cooldown", new TranslationTextComponent(ability.getUnlocalizedName())));
				break;
			case 1:
				HexClient.setActionText(new TranslationTextComponent("ability.hexxitgear.activated", new TranslationTextComponent(ability.getUnlocalizedName())));
				break;
			case 2:
				HexClient.setActionText(new TranslationTextComponent("ability.hexxitgear.ended", new TranslationTextComponent(ability.getUnlocalizedName())));
				break;
			case 3:
				HexClient.setActionText(new TranslationTextComponent("ability.hexxitgear.refreshed", new TranslationTextComponent(ability.getUnlocalizedName())));
				break;
			case 4:
				HexClient.setActionText(new TranslationTextComponent("ability.hexxitgear.needxp", new TranslationTextComponent(ability.getUnlocalizedName())));
				break;
			case 5:
				HexClient.setActionText(new TranslationTextComponent("ability.hexxitgear.needfood", new TranslationTextComponent(ability.getUnlocalizedName())));
				break;
			default:
				break;
			}
		}, ctx.get());
	}

}
