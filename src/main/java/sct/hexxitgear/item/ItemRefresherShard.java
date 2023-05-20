package sct.hexxitgear.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import sct.hexxitgear.HexxitGear;
import sct.hexxitgear.core.AbilityHandler;
import shadows.placebo.item.ItemBase;

import javax.annotation.Nullable;
import java.util.List;

public class ItemRefresherShard extends ItemBase {

    public ItemRefresherShard() {
        super("refresher_shard", HexxitGear.INFO);
    }

    @Override
    public IRarity getForgeRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        world.playSound(player, player.posX, player.posY, player.posZ, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.PLAYERS, 1F, 0.5F);
        world.playSound(player, player.posX, player.posY, player.posZ, SoundEvents.ITEM_TOTEM_USE, SoundCategory.PLAYERS, 0.5F, 1.75F);
        player.renderBrokenItemStack(stack);
        if (!player.capabilities.isCreativeMode) stack.shrink(1);
        if (world.isRemote) {
            for (int i = 0; i < 60; i++) {
                float a = i * (float)Math.PI / 12;
                float r = 0.01F + 0.005F * i;
                world.spawnParticle(EnumParticleTypes.END_ROD, player.posX, player.posY + 0.1D, player.posZ,
                        r * MathHelper.sin(a), 0.02F, r * MathHelper.cos(a));
            }
        } else {
            AbilityHandler activeHandler = AbilityHandler.getActiveAbility(player);
            if (activeHandler != null) {
                activeHandler.clearCooldown();
            }
        }
        player.getCooldownTracker().setCooldown(this, 16);
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flags) {
        tooltip.add(TextFormatting.GRAY + I18n.format("gui.hexxitgear.refresher_shard"));
    }

}
