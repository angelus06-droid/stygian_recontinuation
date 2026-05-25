package fluke.stygian.entity.render;

import fluke.stygian.entity.EntityWatchling;
import fluke.stygian.entity.render.model.ModelWatchling;
import fluke.stygian.entity.render.model.layer.LayerWatchlingGlow;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderWatchling extends RenderLiving<EntityWatchling> {

    private static final ResourceLocation texture = new ResourceLocation("stygian:textures/entity/watchling.png");

    public RenderWatchling(RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelWatchling(), 0.5f);
        this.addLayer(new LayerWatchlingGlow(this));
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityWatchling entity) {
        return texture;
    }

    @Override
    protected void preRenderCallback(EntityWatchling entity, float partialTickTime) {
        if (entity.isChild()) {
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
        }
    }
}