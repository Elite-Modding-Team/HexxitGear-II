package sct.hexxitgear.compat.traits;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.common.FMLCommonHandler;
import sct.hexxitgear.core.ArmorSet;
import sct.hexxitgear.init.HexRegistry;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.shared.client.ParticleEffect;
import slimeknights.tconstruct.tools.TinkerTools;

public class TraitHexicalSpark extends AbstractTrait {
    public TraitHexicalSpark() {
        super("hexical_spark", 0x185ACC);
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
        ArmorSet set = ArmorSet.getCurrentArmorSet((EntityPlayer) player);

        // 15% chance (or 30% chance with full Hexxit Gear armor set) for a lightning strike to occur
        final double chance = set != null ? 0.3D : 0.15D;

        if (target.world.rand.nextDouble() <= chance && player instanceof EntityPlayer) {
            target.world.addWeatherEffect(new EntityLightningBolt(target.world, target.posX, target.posY, target.posZ, true));
            target.world.playSound(null, target.posX, target.posY, target.posZ, HexRegistry.HEXICAL_MASTER_SWORD_ZAP_SOUND, SoundCategory.PLAYERS, 1.0F, 0.6F + target.world.rand.nextFloat());

            // Lightning strike deals AoE damage
            for (Entity nearbyLivingEntity : target.world.getEntitiesWithinAABBExcludingEntity(player, target.getEntityBoundingBox().grow(4.0D, 4.0D, 4.0D))) {
                if (nearbyLivingEntity instanceof EntityLivingBase && !nearbyLivingEntity.isOnSameTeam(player) || (nearbyLivingEntity instanceof EntityPlayer && FMLCommonHandler.instance().getMinecraftServerInstance().isPVPEnabled())) {
                    TinkerTools.proxy.spawnEffectParticle(ParticleEffect.Type.HEART_ELECTRO, nearbyLivingEntity, 5);
                    nearbyLivingEntity.attackEntityFrom(DamageSource.LIGHTNING_BOLT, damageDealt);
                }
            }
        }
    }
}
