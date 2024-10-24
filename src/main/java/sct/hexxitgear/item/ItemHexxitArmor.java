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

package sct.hexxitgear.item;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sct.hexxitgear.HexxitGear;
import sct.hexxitgear.core.ArmorSet;
import sct.hexxitgear.gui.HexTab;
import sct.hexxitgear.init.HexRegistry;
import sct.hexxitgear.model.ModelDualLayerArmor;
import shadows.placebo.client.IHasModel;

import javax.annotation.Nullable;
import java.util.List;

public abstract class ItemHexxitArmor extends ItemArmor implements ISpecialArmor, IHasModel {

    @SideOnly(Side.CLIENT)
    private static final ModelDualLayerArmor HEXXIT_CHEST = new ModelDualLayerArmor(1.0f);
    @SideOnly(Side.CLIENT)
    private static final ModelDualLayerArmor HEXXIT_LEGGINGS = new ModelDualLayerArmor(0.6f);
    @SideOnly(Side.CLIENT)
    private static final ModelDualLayerArmor HEXXIT_FEET = new ModelDualLayerArmor(0.6f);

    private final ArmorSet set;

    protected String hoodTexture;
    protected String bodyTexture;
    protected String overlayTexture;

    protected ItemHexxitArmor(String regname, ArmorSet set, int renderindex, EntityEquipmentSlot slot) {
        super(set.getMaterial(), renderindex, slot);
        this.set = set;
        setCreativeTab(HexTab.INSTANCE);
        setRegistryName(HexxitGear.MODID, regname);
        setTranslationKey(HexxitGear.MODID + "." + regname);
        HexxitGear.INFO.getItemList().add(this);
    }

    public ArmorSet getSet() {
        return set;
    }

    @Override
    public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
        return new ArmorProperties(0, 0, Integer.MAX_VALUE);
    }

    @Override
    public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
        return 0;
    }

    @Override
    public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
        if (entity instanceof EntityPlayer && !(((EntityPlayer) entity).capabilities.isCreativeMode)) {
            if (stack.getItemDamage() < stack.getMaxDamage()) {
                stack.setItemDamage(stack.getItemDamage() + 1);
            }
        }
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        if (armorType != EntityEquipmentSlot.HEAD) return;

        ArmorSet set = ArmorSet.getCurrentArmorSet(player);
        if (set != null) {
            ArmorSet.CACHED_SETS.put(player.getUniqueID(), set);
            set.applyBuffs(player);

            // Sage buff
            if (set == ArmorSet.SAGE && player.isInWater() && !player.capabilities.isFlying && player.moveForward > 0.0F) {
                player.moveRelative(0.0F, 0.0F, 0.025F, 1.0F);
                player.jumpMovementFactor = 0.05F;
            }
        }
    }

    @Override
    public IRarity getForgeRarity(ItemStack stack) {
        return EnumRarity.UNCOMMON;
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return toRepair.isItemDamaged() && repair.getItem() == HexRegistry.HEXICAL_ESSENCE;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flags) {
        set.addTooltip(tooltip);
    }

    @Override
    public boolean hasOverlay(ItemStack stack) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    protected ModelDualLayerArmor getBodyModel(EntityEquipmentSlot slot) {
        if (slot == EntityEquipmentSlot.CHEST) return HEXXIT_CHEST;
        if (slot == EntityEquipmentSlot.LEGS) return HEXXIT_LEGGINGS;
        if (slot == EntityEquipmentSlot.FEET) return HEXXIT_FEET;
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            if (player.isPotionActive(MobEffects.INVISIBILITY)) return "hexxitgear:textures/armor/invisible.png";
        }

        // If the helmet slot, return helmet texture map
        if (armorType == EntityEquipmentSlot.HEAD) return hoodTexture;

        //noinspection ConstantValue
        if (type != null && type.equals("overlay")) {
            ModelDualLayerArmor armor = getBodyModel(slot);
            if (armor != null) armor.prepareForOverlay();
            return overlayTexture;
        }

        ModelDualLayerArmor otherArmor = getBodyModel(slot);
        if (otherArmor != null) otherArmor.prepareForNormal();
        return bodyTexture;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
        ModelBiped biped;

        if (armorSlot == EntityEquipmentSlot.HEAD) {
            biped = getHeadModel();
        } else {
            biped = getBodyModel(armorSlot);
        }

        if (biped == null) return null;

        biped.isSneak = entityLiving.isSneaking();
        biped.isRiding = entityLiving.isRiding();
        biped.isChild = entityLiving.isChild();

        biped.bipedHead.showModel = (armorSlot == EntityEquipmentSlot.HEAD);
        biped.bipedHeadwear.showModel = (armorSlot == EntityEquipmentSlot.HEAD);
        biped.bipedBody.showModel = (armorSlot == EntityEquipmentSlot.CHEST);
        biped.bipedRightArm.showModel = (armorSlot == EntityEquipmentSlot.CHEST);
        biped.bipedLeftArm.showModel = (armorSlot == EntityEquipmentSlot.CHEST);
        biped.bipedRightLeg.showModel = (armorSlot == EntityEquipmentSlot.LEGS || armorSlot == EntityEquipmentSlot.FEET);
        biped.bipedLeftLeg.showModel = (armorSlot == EntityEquipmentSlot.LEGS || armorSlot == EntityEquipmentSlot.FEET);

        ItemStack heldItem = entityLiving.getHeldItemMainhand();
        if (!heldItem.isEmpty()) {
            biped.rightArmPose = ModelBiped.ArmPose.ITEM;
        }

        if (!heldItem.isEmpty() && entityLiving.getHeldItemMainhand().getCount() > 0) {
            EnumAction action = heldItem.getItemUseAction();
            if (action == EnumAction.BLOCK) {
                biped.rightArmPose = ModelBiped.ArmPose.BLOCK;
            } else if (action == EnumAction.BOW) {
                biped.rightArmPose = ModelBiped.ArmPose.BOW_AND_ARROW;
            }
        }

        return biped;
    }

    @SideOnly(Side.CLIENT)
    protected abstract ModelBiped getHeadModel();
}
