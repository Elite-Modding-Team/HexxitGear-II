package sct.hexxitgear.render;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import sct.hexxitgear.HexxitGear;
import sct.hexxitgear.core.ArmorSet;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = HexxitGear.MODID, value = Side.CLIENT)
public class RenderCape {

    private static final ResourceLocation SAGE_CAPE_TEXTURE = new ResourceLocation(HexxitGear.MODID, "textures/armor/capes/sage.png");
    private static final ResourceLocation SCALE_CAPE_TEXTURE = new ResourceLocation(HexxitGear.MODID, "textures/armor/capes/scale.png");
    private static final ResourceLocation THIEF_CAPE_TEXTURE = new ResourceLocation(HexxitGear.MODID, "textures/armor/capes/thief.png");
    private static final ResourceLocation TRIBAL_CAPE_TEXTURE = new ResourceLocation(HexxitGear.MODID, "textures/armor/capes/tribal.png");
    private static final ResourceLocation ANCIENT_THIEF_CAPE_TEXTURE = new ResourceLocation(HexxitGear.MODID, "textures/armor/capes/ancient_thief.png");
    private static final ResourceLocation ANCIENT_TRIBAL_CAPE_TEXTURE = new ResourceLocation(HexxitGear.MODID, "textures/armor/capes/ancient_tribal.png");

    @SuppressWarnings("ConstantValue")
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRenderPlayer(RenderPlayerEvent.Post event) {
        NetHandlerPlayClient connection = Minecraft.getMinecraft().getConnection();
        if (connection != null) {
            EntityPlayer player = event.getEntityPlayer();
            UUID playerId = player.getUniqueID();
            ArmorSet set = ArmorSet.getCurrentArmorSet(player);
            NetworkPlayerInfo playerInfo = connection.getPlayerInfo(playerId);
            if (playerInfo != null) {
                playerInfo.playerTextures.put(MinecraftProfileTexture.Type.CAPE, getCapeTexture(set, player));
            }
        }
    }

    private static ResourceLocation getCapeTexture(ArmorSet set, EntityPlayer player) {
        if (set == ArmorSet.SAGE) return SAGE_CAPE_TEXTURE;
        if (set == ArmorSet.SCALE) return SCALE_CAPE_TEXTURE;
        if (set == ArmorSet.THIEF) return ArmorSet.isAncientSet(set, player) ? ANCIENT_THIEF_CAPE_TEXTURE : THIEF_CAPE_TEXTURE;
        if (set == ArmorSet.TRIBAL) return ArmorSet.isAncientSet(set, player) ? ANCIENT_TRIBAL_CAPE_TEXTURE : TRIBAL_CAPE_TEXTURE;
        return null;
    }
}
