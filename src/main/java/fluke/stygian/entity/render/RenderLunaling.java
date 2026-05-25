package fluke.stygian.entity.render;

import fluke.stygian.entity.EntityLunaling;
import fluke.stygian.entity.render.model.ModelLunaling;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderLunaling extends RenderLiving<EntityLunaling> {

    private static final ResourceLocation texture = new ResourceLocation("stygian:textures/entity/lunaling.png");

    public RenderLunaling(RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelLunaling(), 0.5f);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityLunaling entity) {
        return texture;
    }

    @Override
    protected void preRenderCallback(EntityLunaling entity, float partialTickTime) {
        if (entity.isChild()) {
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
        }
    }

    @Override
    protected void applyRotations(EntityLunaling entityLiving, float p_77043_2_, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);

        if ((double)entityLiving.limbSwingAmount >= 0.01D) {
            float f = 13.0F;
            float f2 = entityLiving.limbSwing - entityLiving.limbSwingAmount * (1.0F - partialTicks) + 6.0F;
            float f3 = (Math.abs(f2 % 13.0F - 6.5F) - 3.25F) / 3.25F;
            GlStateManager.rotate(6.5F * f3, 0.0F, 0.0F, 1.0F);
        }
    }
}