package sct.hexxitgear.core;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.StringUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.function.Supplier;

public interface IBuffEffect {

    int REFRESH_QUERY_INTERVAL = 10;

    void apply(EntityLivingBase target);

    void purge(EntityLivingBase target);

    @SideOnly(Side.CLIENT)
    String getDescription();

    class Simple implements IBuffEffect {

        private static final int MAX_DURATION = 300;
        private static final int REFRESH_INTERVAL = 50;

        private final Supplier<Potion> potionTypeProvider;
        private final int amplifier;

        public Simple(Supplier<Potion> potionTypeProvider, int amplifier) {
            this.potionTypeProvider = potionTypeProvider;
            this.amplifier = amplifier;
        }

        @Override
        public void apply(EntityLivingBase target) {
            Potion potionType = potionTypeProvider.get();
            PotionEffect activeEffect = target.getActivePotionEffect(potionType);
            if (activeEffect != null) {
                int activeAmplifier = activeEffect.getAmplifier();
                if (activeAmplifier > amplifier || (activeAmplifier == amplifier && MAX_DURATION - activeEffect.getDuration() < REFRESH_INTERVAL)) {
                    return;
                }
            }
            target.addPotionEffect(new PotionEffect(potionType, MAX_DURATION, amplifier, false, false));
        }

        @Override
        public void purge(EntityLivingBase target) {
            Potion potionType = potionTypeProvider.get();
            PotionEffect activeEffect = target.getActivePotionEffect(potionType);
            if (activeEffect != null && activeEffect.getAmplifier() == amplifier && activeEffect.getDuration() <= MAX_DURATION) {
                target.removePotionEffect(potionType);
            }
        }

        @Override
        public String getDescription() {
            String s1 = I18n.format(potionTypeProvider.get().getName());
            return amplifier > 0 ? s1 + " " + I18n.format("enchantment.level." + (amplifier + 1)) : s1;
        }
    }

    class Absorption implements IBuffEffect {

        private final int amplifier;
        private final int refreshInterval;

        public Absorption(int amplifier, int refreshInterval) {
            this.amplifier = amplifier;
            this.refreshInterval = refreshInterval;
        }

        @Override
        public void apply(EntityLivingBase target) {
            PotionEffect activeEffect = target.getActivePotionEffect(MobEffects.ABSORPTION);
            if (activeEffect != null) {
                int activeAmplifier = activeEffect.getAmplifier();
                if (activeAmplifier > amplifier) {
                    if (target.getAbsorptionAmount() > 4F * (amplifier + 1)) {
                        return;
                    }
                    target.removePotionEffect(MobEffects.ABSORPTION);
                } else if (activeAmplifier == amplifier) {
                    return;
                }
            }
            target.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, refreshInterval, amplifier, false, false));
        }

        @Override
        public void purge(EntityLivingBase target) {
            PotionEffect activeEffect = target.getActivePotionEffect(MobEffects.ABSORPTION);
            if (activeEffect != null && activeEffect.getAmplifier() == amplifier && activeEffect.getDuration() <= refreshInterval) {
                target.removePotionEffect(MobEffects.ABSORPTION);
            }
        }

        @Override
        public String getDescription() {
            return String.format("%s %d (%s)", I18n.format(MobEffects.ABSORPTION.getName()), amplifier + 1, StringUtils.ticksToElapsedTime(refreshInterval));
        }
    }

    class StepAssist implements IBuffEffect {

        @Override
        public void apply(EntityLivingBase target) {
            target.stepHeight = 1.003F;
        }

        @Override
        public void purge(EntityLivingBase target) {
            if (target.stepHeight == 1.003F) {
                target.stepHeight = 0.5001F;
            }
        }

        @Override
        public String getDescription() {
            return I18n.format("buff.hexxitgear.stepassist");
        }
    }

    class AquaDash implements IBuffEffect {

        @Override
        public void apply(EntityLivingBase target) {
            if (target.isInWater() && !target.isRiding()) {
                target.motionX *= 5;
                target.motionZ *= 5;
                target.motionY *= 5;
            }
        }

        @Override
        public void purge(EntityLivingBase target) {
        }

        @Override
        public String getDescription() {
            return I18n.format("buff.hexxitgear.aquadash");
        }
    }
}
