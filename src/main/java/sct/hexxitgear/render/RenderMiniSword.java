package sct.hexxitgear.render;

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

import javax.annotation.Nonnull;

public class RenderMiniSword extends Render<EntityMiniSword> {
    private static final ItemStack PROJECTILE = new ItemStack(HexRegistry.HEXICAL_MASTER_SWORD_ACTIVATION);

    public RenderMiniSword(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(@Nonnull EntityMiniSword entity, double x, double y, double z, float yaw, float partialTicks) {
        RenderItem itemRender = Minecraft.getMinecraft().getRenderItem();
        GlStateManager.pushMatrix();
        bindEntityTexture(entity);
        GlStateManager.translate(x, y, z);
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(0.85f, 0.85f, 0.85f);
        GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks - 45.0f, 0.0f, 0.0f, 1.0f);
        bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        float f1 = entity.arrowShake - partialTicks;

        if (f1 > 0.0f) {
            float f2 = -MathHelper.sin(f1 * 3.0f) * f1;
            GlStateManager.rotate(f2, 0.0f, 0.0f, 1.0f);
        }

        GlStateManager.translate(-0.15f, -0.15f, 0.0f);

        if (renderOutlines) {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(8214271);
        }

        itemRender.renderItem(PROJECTILE, TransformType.NONE);

        if (renderOutlines) {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }

        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, yaw, partialTicks);
    }

    @Override
    protected ResourceLocation getEntityTexture(@Nonnull EntityMiniSword entity) {
        return TextureMap.LOCATION_BLOCKS_TEXTURE;
    }
}
