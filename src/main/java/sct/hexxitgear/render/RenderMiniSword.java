package sct.hexxitgear.render;

import javax.annotation.Nonnull;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import sct.hexxitgear.entity.EntityMiniSword;
import sct.hexxitgear.init.HexRegistry;

public class RenderMiniSword extends Render<EntityMiniSword> {
    public RenderMiniSword(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(@Nonnull EntityMiniSword entity, double d, double d1, double d2, float f, float f1) {
        RenderItem itemRender = Minecraft.getMinecraft().getRenderItem();
        GlStateManager.pushMatrix();
        bindEntityTexture(entity);
        GlStateManager.translate(d, d1, d2);
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(0.85f, 0.85f, 0.85f);
        GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * f1 - 90.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * f1 - 45.0f, 0.0f, 0.0f, 1.0f);
        this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        float f15 = entity.arrowShake - f1;
        
        if (f15 > 0.0f) {
            float f16 = -MathHelper.sin(f15 * 3.0f) * f15;
            GlStateManager.rotate(f16, 0.0f, 0.0f, 1.0f);
        }
        
        GlStateManager.translate(-0.15f, -0.15f, 0.0f);
        
        if (renderOutlines) {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(getTeamColor(entity));
        }
        
        itemRender.renderItem(getStackToRender(entity), TransformType.NONE);
        
        if (renderOutlines) {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }
        
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        super.doRender(entity, d, d1, d2, f, f1);
    }

    public ItemStack getStackToRender(EntityMiniSword entity) {
        return new ItemStack(HexRegistry.HEXICAL_MASTER_SWORD_ACTIVATION);
    }

    @Override
    protected ResourceLocation getEntityTexture(@Nonnull EntityMiniSword entity) {
        return TextureMap.LOCATION_BLOCKS_TEXTURE;
    }
}
