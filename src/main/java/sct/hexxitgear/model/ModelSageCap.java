package sct.hexxitgear.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelSageCap extends ModelWtfMojang {
    //fields
    ModelRenderer head;
    ModelRenderer Shape1;
    ModelRenderer Shape2;
    ModelRenderer Shape3;

    public ModelSageCap() {
        textureWidth = 64;
        textureHeight = 64;

        head = new ModelRenderer(this, 0, 0);
        head.addBox(-4F, -7.5F, -4F, 8, 8, 8);
        head.setRotationPoint(0F, 0F, 0F);
        head.setTextureSize(64, 64);
        head.mirror = true;

        Shape1 = new ModelRenderer(this, 0, 33);
        Shape1.addBox(-5F, -9F, -5F, 10, 9, 10);
        Shape1.setRotationPoint(0F, 0F, 0F);
        Shape1.setTextureSize(64, 64);
        Shape1.mirror = true;
        setRotation(Shape1, 0F, 0F, 0F);

        Shape2 = new ModelRenderer(this, 49, 0);
        Shape2.addBox(-3F, -9F, 5F, 6, 5, 1);
        Shape2.setRotationPoint(0F, 0F, 0F);
        Shape2.setTextureSize(64, 64);
        Shape2.mirror = true;
        setRotation(Shape2, 0F, 0F, 0F);

        Shape3 = new ModelRenderer(this, 49, 6);
        Shape3.addBox(-2F, -9F, 6F, 4, 3, 1);
        Shape3.setRotationPoint(0F, 0F, 0F);
        Shape3.setTextureSize(64, 64);
        Shape3.mirror = true;
        setRotation(Shape3, 0F, 0F, 0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        float scaledUp = f5 + 0.009F;
        float suLarge = f5 + 0.002F;
        setRotationAngles(f, f1, f2, f3, f4, scaledUp, entity);
        GlStateManager.pushMatrix();
        if (entity.isSneaking()) GlStateManager.translate(0, .2, 0);
        head.render(scaledUp);
        Shape1.render(suLarge);
        Shape2.render(suLarge);
        Shape3.render(suLarge);
        GlStateManager.popMatrix();
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        float newX = this.bipedHeadwear.rotateAngleX;
        float newY = this.bipedHeadwear.rotateAngleY;
        setRotation(head, newX, newY, 0);
        setRotation(Shape1, newX, newY, 0);
        setRotation(Shape2, newX, newY, 0);
        setRotation(Shape3, newX, newY, 0);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
        model.rotationPointY = (this.isSneak) ? 1.0f : 0;
    }
}
