package sct.hexxitgear.net;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.StringUtils;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import sct.hexxitgear.HexxitGear;
import sct.hexxitgear.core.ability.Ability;

public class ActionTextMessage implements IMessage {

	private int messageId;
	private int abilityId;
	private int data;

	public ActionTextMessage() {
	}

	public ActionTextMessage(int messageId, int abilityId, int data) {
		this.messageId = messageId;
		this.abilityId = abilityId;
		this.data = data;
	}

	public ActionTextMessage(int messageId, int abilityId) {
		this(messageId, abilityId, 0);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeByte(messageId).writeByte(abilityId).writeInt(data);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		messageId = buf.readByte();
		abilityId = buf.readByte();
		data = buf.readInt();
	}

	public static class ActionTextHandler implements IMessageHandler<ActionTextMessage, IMessage> {

		@Override
		public IMessage onMessage(ActionTextMessage message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> {
				Ability ability = Ability.ABILITIES.get(message.abilityId);
				if (ability.isInstant() && message.messageId == 2) return;
				switch (message.messageId) {
				case 0:
					HexxitGear.proxy.setActionText(new TextComponentTranslation("ability.hexxitgear.cooldown",
							new TextComponentTranslation(ability.getUnlocalizedName()),
							new TextComponentString(StringUtils.ticksToElapsedTime(message.data))));
					break;
				case 1:
					HexxitGear.proxy.setActionText(new TextComponentTranslation("ability.hexxitgear.activated",
							new TextComponentTranslation(ability.getUnlocalizedName())));
					break;
				case 2:
					HexxitGear.proxy.setActionText(new TextComponentTranslation("ability.hexxitgear.ended",
							new TextComponentTranslation(ability.getUnlocalizedName())));
					break;
				case 3:
					HexxitGear.proxy.setActionText(new TextComponentTranslation("ability.hexxitgear.refreshed",
							new TextComponentTranslation(ability.getUnlocalizedName())));
					break;
				case 4:
					HexxitGear.proxy.setActionText(new TextComponentTranslation("ability.hexxitgear.needxp",
							message.data,
							new TextComponentTranslation(ability.getUnlocalizedName())));
					break;
				case 5:
					HexxitGear.proxy.setActionText(new TextComponentTranslation("ability.hexxitgear.needfood",
							message.data,
							new TextComponentTranslation(ability.getUnlocalizedName())));
					break;
				default:
					break;
				}
			});
			return null;
		}

	}

}
