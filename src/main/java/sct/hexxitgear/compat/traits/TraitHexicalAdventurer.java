package sct.hexxitgear.compat.traits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import sct.hexxitgear.core.ArmorSet;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class TraitHexicalAdventurer extends AbstractTrait {
    public TraitHexicalAdventurer() {
        super("hexical_adventurer", 0x185ACC);
    }

    @Override
    public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
        ArmorSet set = ArmorSet.getCurrentArmorSet((EntityPlayer) player);

        // +50% damage while a full set of Hexxit Gear armor is equipped
        if (set != null && player instanceof EntityPlayer) {
            newDamage += damage / 2.0F;
        }

        return super.damage(tool, player, target, damage, newDamage, isCritical);
    }
}
