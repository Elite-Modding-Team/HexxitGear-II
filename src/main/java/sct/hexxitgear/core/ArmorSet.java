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

package sct.hexxitgear.core;

import com.elenai.elenaidodge.modpacks.potions.PotionInit;
import net.minecraft.block.material.Material;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringUtils;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import sct.hexxitgear.core.ability.*;
import sct.hexxitgear.item.ItemHexxitArmor;

import javax.annotation.Nullable;
import java.util.*;
import java.util.regex.Pattern;

public class ArmorSet {

    public static final Map<UUID, ArmorSet> CACHED_SETS = new HashMap<>();

    public static final List<ArmorSet> SETS = new ArrayList<>();

    public static final ArmorSet TRIBAL = new ArmorSet("tribal", ItemArmor.ArmorMaterial.DIAMOND,
            new IBuffEffect[]{
                    new IBuffEffect.Simple(() -> MobEffects.STRENGTH, 0),
                    new IBuffEffect.Simple(() -> MobEffects.HASTE, 1),
                    new IBuffEffect.Simple(() -> MobEffects.JUMP_BOOST, 2),
                    new IBuffEffect.Simple(() -> MobEffects.NIGHT_VISION, 0),
                    new IBuffEffect.StepAssist()
            },
            new AbilityRampage());
    public static final ArmorSet THIEF = new ArmorSet("thief", ItemArmor.ArmorMaterial.DIAMOND,
            new IBuffEffect[]{
                    new IBuffEffect.Simple(() -> PotionHandler.THIEF, 0),
                    new IBuffEffect.Simple(() -> PotionInit.CAN_WALLJUMP, 0),
                    new IBuffEffect.Simple(() -> MobEffects.LUCK, 1),
                    new IBuffEffect.Simple(() -> MobEffects.NIGHT_VISION, 0)
            },
            new AbilityStealth());
    public static final ArmorSet SCALE = new ArmorSet("scale", ItemArmor.ArmorMaterial.DIAMOND,
            new IBuffEffect[]{
                    new IBuffEffect.Absorption(1, 400),
                    new IBuffEffect.Simple(() -> MobEffects.STRENGTH, 1),
                    new IBuffEffect.Simple(() -> MobEffects.RESISTANCE, 0)
            },
            new AbilityShield());
    public static final ArmorSet SAGE = new ArmorSet("sage", ItemArmor.ArmorMaterial.DIAMOND,
            new IBuffEffect[]{
                    new IBuffEffect.Absorption(2, 280),
                    new IBuffEffect.Simple(() -> MobEffects.FIRE_RESISTANCE, 0),
                    new IBuffEffect.Simple(() -> MobEffects.WATER_BREATHING, 0),
                    new IBuffEffect.Simple(() -> MobEffects.NIGHT_VISION, 0),
                    new IBuffEffect.WaterDexterity()
            },
            new AbilityLift());

    private final String name;
    private final ItemArmor.ArmorMaterial material;
    private final IBuffEffect[] buffs;
    private final Ability ability;

    public ArmorSet(String name, ItemArmor.ArmorMaterial material, IBuffEffect[] buffs, Ability ability) {
        this.name = name;
        this.material = material;
        this.buffs = buffs;
        this.ability = ability;
        SETS.add(this);
    }

    public String getName() {
        return name;
    }

    public ItemArmor.ArmorMaterial getMaterial() {
        return material;
    }

    public Ability getAbility() {
        return ability;
    }

    public void applyBuffs(EntityPlayer player) {
        if (player.world.getTotalWorldTime() % IBuffEffect.REFRESH_QUERY_INTERVAL != 0) {
            return;
        }
        for (IBuffEffect buff : buffs) {
            buff.apply(player);
        }
    }

    public void removeBuffs(EntityPlayer player) {
        for (IBuffEffect buff : buffs) {
            buff.purge(player);
        }
    }

    @SideOnly(Side.CLIENT)
    public void addTooltip(List<String> tooltip) {
        tooltip.add(TextFormatting.GRAY + I18n.format("gui.hexxitgear.set." + name));
        tooltip.add("");
        if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            tooltip.add(TextFormatting.GRAY + TextFormatting.ITALIC.toString() + I18n.format("gui.hexxitgear.set.more_info"));
            return;
        }
        tooltip.add(TextFormatting.AQUA + I18n.format("gui.hexxitgear.set.set_bonus"));
        for (IBuffEffect buff : buffs) {
            tooltip.add(String.format(TextFormatting.GRAY + "+ " + TextFormatting.WHITE + "%s", buff.getDescription()));
        }
        tooltip.add("");
        StringBuilder abilityName = new StringBuilder()
                .append(TextFormatting.AQUA).append(I18n.format("gui.hexxitgear.set.set_ability")).append(": ")
                .append(TextFormatting.WHITE).append(I18n.format(ability.getUnlocalizedName()));
        if (ability.getHungerCost() > 0) {
            if (ability.getXpCost() > 0) {
                abilityName.append(TextFormatting.GRAY).append(" (")
                        .append(TextFormatting.YELLOW).append(ability.getHungerCost())
                        .append(TextFormatting.GRAY).append('+')
                        .append(TextFormatting.GREEN).append(ability.getXpCost())
                        .append(TextFormatting.GRAY).append(')');
            } else {
                abilityName.append(TextFormatting.GRAY).append(" (")
                        .append(TextFormatting.YELLOW).append(ability.getHungerCost())
                        .append(TextFormatting.GRAY).append(')');
            }
        } else if (ability.getXpCost() > 0) {
            abilityName.append(TextFormatting.GRAY).append(" (")
                    .append(TextFormatting.GREEN).append(ability.getXpCost())
                    .append(TextFormatting.GRAY).append(')');
        }
        tooltip.add(abilityName.toString());
        for (String line : I18n.format(ability.getUnlocalizedName() + ".desc").split(Pattern.quote("\\n"))) {
            tooltip.add("  " + TextFormatting.GRAY + line);
        }
        if (ability.getDuration() > 1) {
            tooltip.add(String.format(TextFormatting.GREEN + "%s: " + TextFormatting.WHITE + "%s",
                    I18n.format("gui.hexxitgear.set.set_ability_duration"), StringUtils.ticksToElapsedTime(ability.getDuration())));
        }
        tooltip.add(String.format(TextFormatting.GREEN + "%s: " + TextFormatting.WHITE + "%s",
                I18n.format("gui.hexxitgear.set.set_ability_cooldown"), StringUtils.ticksToElapsedTime(ability.getCooldown())));
    }

    @Nullable
    public static ArmorSet getCurrentArmorSet(EntityPlayer player) {
        ItemStack stack = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
        if (stack.isEmpty() || !(stack.getItem() instanceof ItemHexxitArmor)) {
            return null;
        }
        ArmorSet set = ((ItemHexxitArmor) stack.getItem()).getSet();
        if (isArmorSlotWrong(player, EntityEquipmentSlot.CHEST, set)
                || isArmorSlotWrong(player, EntityEquipmentSlot.LEGS, set)
                || isArmorSlotWrong(player, EntityEquipmentSlot.FEET, set)) {
            return null;
        }
        return set;
    }

    private static boolean isArmorSlotWrong(EntityPlayer player, EntityEquipmentSlot slot, ArmorSet set) {
        ItemStack stack = player.getItemStackFromSlot(slot);
        return stack.isEmpty() || !(stack.getItem() instanceof ItemHexxitArmor)
                || ((ItemHexxitArmor) stack.getItem()).getSet() != set;
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent e) {
        if (e.phase != TickEvent.Phase.END) {
            return;
        }
        AbilityHandler abilityHandler = AbilityHandler.getActiveAbility(e.player);
        ArmorSet s = CACHED_SETS.get(e.player.getUniqueID());
        if (s == null) {
            if (abilityHandler != null) {
                abilityHandler.setEnded(e.player);
            }
        } else if (getCurrentArmorSet(e.player) != s) {
            s.removeBuffs(e.player);
            CACHED_SETS.put(e.player.getUniqueID(), null);
            if (abilityHandler != null) {
                abilityHandler.setEnded(e.player);
            }
        } else if (abilityHandler != null) {
            abilityHandler.onTick(e.player);
        }
    }

    // Sage buff
    @SubscribeEvent
    public static void onPlayerBreakSpeed(PlayerEvent.BreakSpeed e) {
        if (ArmorSet.getCurrentArmorSet(e.getEntityPlayer()) == ArmorSet.SAGE && e.getEntityPlayer().isInsideOfMaterial(Material.WATER)) {
            e.setNewSpeed(e.getNewSpeed() * 5);
        }
    }

    public static void classloadForConfigs() {
    }
}
