package sct.hexxitgear.model;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelDualLayerArmor extends ModelWtfMojang {
    private final ModelWtfMojang skinModel;
    private final ModelWtfMojang armorModel;
    private boolean drawOverlay = false;

    public ModelDualLayerArmor() {
        this(0.3f);
    }

    public ModelDualLayerArmor(float skinSize) {
        skinModel = new ModelWtfMojang(0.3f, 0.0f, 64, 32);
        armorModel = new ModelWtfMojang(skinSize, 0.0f, 64, 32);
    }

    public void prepareForOverlay() {
        drawOverlay = true;
    }

    public void prepareForNormal() {
        drawOverlay = false;
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        //Pick out the correct model to draw
        ModelWtfMojang biped;
        if (drawOverlay) {
            biped = armorModel;
            drawOverlay = false;
        } else biped = skinModel;

        //Set up anything we can't pipe through
        setupRenderData(biped);
        biped.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    }

    private void setupRenderData(ModelWtfMojang biped) {
        biped.isSneak = this.isSneak;
        biped.leftArmPose = this.leftArmPose;
        biped.rightArmPose = this.rightArmPose;
        biped.bipedHead.showModel = this.bipedHead.showModel;
        biped.bipedHeadwear.showModel = this.bipedHeadwear.showModel;
        biped.bipedBody.showModel = this.bipedBody.showModel;
        biped.bipedRightArm.showModel = this.bipedRightArm.showModel;
        biped.bipedLeftArm.showModel = this.bipedLeftArm.showModel;
        biped.bipedLeftLeg.showModel = this.bipedLeftLeg.showModel;
        biped.bipedRightLeg.showModel = this.bipedRightLeg.showModel;
        biped.swingProgress = this.swingProgress;
        biped.isRiding = this.isRiding;
        biped.isChild = this.isChild;
    }
}
