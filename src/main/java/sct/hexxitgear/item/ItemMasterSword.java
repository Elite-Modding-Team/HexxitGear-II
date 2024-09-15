package sct.hexxitgear.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import sct.hexxitgear.HexxitGear;
import sct.hexxitgear.entity.EntityMiniSword;
import sct.hexxitgear.gui.HexTab;
import sct.hexxitgear.init.HexRegistry;

import javax.annotation.Nullable;
import java.util.List;

public class ItemMasterSword extends ItemSword {

    private final boolean inactive;
    private boolean highUp;

    public ItemMasterSword(String regname, ToolMaterial material) {
        super(material);
        setCreativeTab(HexTab.INSTANCE);
        setRegistryName(HexxitGear.MODID, regname);
        setTranslationKey(HexxitGear.MODID + "." + regname);
        inactive = regname.contains("inactive");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        if (!inactive) {
            if (!world.isRemote) {
                ItemStack stack = player.getHeldItem(hand);
                EntityMiniSword sword = new EntityMiniSword(world, player);
                sword.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 0.8F, 3.0F);
                sword.setKnockbackStrength(EnchantmentHelper.getEnchantmentLevel(Enchantments.KNOCKBACK, stack));
                world.spawnEntity(sword);
                player.getCooldownTracker().setCooldown(this, 10);
                world.playSound(null, player.posX, player.posY, player.posZ, HexRegistry.HEXICAL_MASTER_SWORD_SHOOT_SOUND, SoundCategory.PLAYERS, 1.0F, 0.6F + world.rand.nextFloat());
            } else {
                player.swingArm(hand);
            }
            return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
        }
        return new ActionResult<>(EnumActionResult.PASS, player.getHeldItem(hand));
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        if (attacker instanceof EntityPlayer) {
            NBTTagList enchants = stack.getEnchantmentTagList();
            int bonus = 0;
            for (int enchant = 0; enchant < enchants.tagCount(); enchant++) {
                bonus += enchants.getCompoundTagAt(enchant).getShort("lvl");
            }
            target.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) attacker).setDamageBypassesArmor(), bonus + 4.0F);
        }
        return true;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
        return true;
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return inactive && highUp;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, world, entity, itemSlot, isSelected);

        if (!(entity instanceof EntityPlayer)) return;
        EntityPlayer player = (EntityPlayer) entity;

        // Activation particles
        if (inactive && player.posY > 128 && isSelected && world.isThundering()) {
            for (int k = 0; k < 40; ++k) {
                double d2 = world.rand.nextGaussian() * 0.02D;
                double d0 = world.rand.nextGaussian() * 0.02D;
                double d1 = world.rand.nextGaussian() * 0.02D;
                world.spawnParticle(EnumParticleTypes.END_ROD, player.posX + (world.rand.nextFloat() * player.width * 2.0F) - player.width, player.posY + (world.rand.nextFloat() * player.height), player.posZ + (world.rand.nextFloat() * player.width * 2.0F) - player.width, d2, d0, d1);
            }
        }

        // Activation event
        if (!world.isRemote && inactive) {
            if (player.posY > 128) {
                highUp = true;
                if (isSelected && world.isThundering()) {
                    world.addWeatherEffect(new EntityLightningBolt(world, player.posX, player.posY, player.posZ, true));
                    player.inventory.deleteStack(stack);
                    player.inventory.add(itemSlot, new ItemStack(HexRegistry.HEXICAL_MASTER_SWORD));
                    InventoryHelper.spawnItemStack(world, player.posX, player.posY + 1.0D, player.posZ, new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("cqrepoured:sword_walker"))));
                    InventoryHelper.spawnItemStack(world, player.posX, player.posY + 1.0D, player.posZ, new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("mowziesmobs:wrought_axe"))));
                    InventoryHelper.spawnItemStack(world, player.posX, player.posY + 1.0D, player.posZ, new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("mod_lavacow:skeletonking_mace"))));
                    world.playSound(null, player.posX, player.posY + 1.0D, player.posZ, HexRegistry.HEXICAL_MASTER_SWORD_ACTIVATION_SOUND, SoundCategory.PLAYERS, 0.5F, 1.0F);
                    if (FMLLaunchHandler.side().isClient()) {
                        displayItemActivation();
                    }
                }
            } else {
                highUp = false;
            }
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.EPIC;
    }

    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
        Multimap<String, AttributeModifier> multimap = HashMultimap.create();

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Damage modifier", this.getAttackDamage(), 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Speed modifier", 100.0D - 4.0D, 0));
        }

        return multimap;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);

        tooltip.add(TextFormatting.GRAY + I18n.format("gui.hexxitgear.set.impenetrable"));
        if (inactive) {
            tooltip.add(TextFormatting.DARK_PURPLE + I18n.format("tooltip.hexxitgear.hexical_master_sword_inactive.1"));
            tooltip.add(TextFormatting.DARK_PURPLE + I18n.format("tooltip.hexxitgear.hexical_master_sword_inactive.2"));
            tooltip.add(TextFormatting.DARK_PURPLE + I18n.format("tooltip.hexxitgear.hexical_master_sword_inactive.3"));
            tooltip.add(TextFormatting.DARK_PURPLE + I18n.format("tooltip.hexxitgear.hexical_master_sword_inactive.4"));
        } else {
            tooltip.add(TextFormatting.DARK_PURPLE + I18n.format("tooltip.hexxitgear.hexical_master_sword_active.1"));
            tooltip.add("");
            if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                tooltip.add(TextFormatting.GRAY + TextFormatting.ITALIC.toString() + I18n.format("gui.hexxitgear.set.more_info"));
                return;
            }
            tooltip.add(TextFormatting.AQUA + I18n.format("gui.hexxitgear.set.abilities"));
            tooltip.add(String.format(TextFormatting.GRAY + "+ " + TextFormatting.WHITE + "%s", I18n.format("tooltip.hexxitgear.hexical_master_sword_active.2")));
            tooltip.add(String.format(TextFormatting.GRAY + "+ " + TextFormatting.WHITE + "%s", I18n.format("tooltip.hexxitgear.hexical_master_sword_active.3")));
            tooltip.add(String.format(TextFormatting.GRAY + "+ " + TextFormatting.WHITE + "%s", I18n.format("tooltip.hexxitgear.hexical_master_sword_active.4")));
        }
    }

    @SideOnly(Side.CLIENT)
    public void displayItemActivation() {
        Minecraft.getMinecraft().entityRenderer.displayItemActivation(new ItemStack(HexRegistry.HEXICAL_MASTER_SWORD_ACTIVATION));
    }
}
