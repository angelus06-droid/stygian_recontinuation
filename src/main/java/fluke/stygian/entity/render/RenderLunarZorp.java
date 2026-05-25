package fluke.stygian.entity.render;

import fluke.stygian.entity.EntityLunarZorp;
import fluke.stygian.entity.render.model.ModelLunarZorp;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderLunarZorp extends RenderLiving<EntityLunarZorp> {

    private static final ResourceLocation texture = new ResourceLocation("stygian:textures/entity/lunar_zorp.png");

    public RenderLunarZorp(RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelLunarZorp(), 0.5f);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityLunarZorp entity) {
        return texture;
    }

    @Override
    protected void preRenderCallback(EntityLunarZorp entity, float partialTickTime) {
        if (entity.isChild()) {
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
        }
    }
}