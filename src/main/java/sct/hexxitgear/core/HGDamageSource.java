package sct.hexxitgear.core;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class HGDamageSource extends EntityDamageSource {
    private final String damageType;

    public HGDamageSource(String damageType, @Nullable Entity damageSourceEntity) {
        super(damageType, damageSourceEntity);
        this.damageType = damageType;
    }

    @Override
    public ITextComponent getDeathMessage(EntityLivingBase entityLivingBase) {
        return new TextComponentTranslation("message.hexxitgear." + damageType, entityLivingBase.getDisplayName());
    }
}
