package fluke.stygian.entity.render;

import fluke.stygian.entity.EntitySupaika;
import fluke.stygian.entity.render.model.ModelSupaika;
import fluke.stygian.entity.render.model.layer.LayerSupaikaGlow;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderSupaika extends RenderLiving<EntitySupaika> {

    private static final ResourceLocation texture = new ResourceLocation("stygian:textures/entity/supaika.png");

    public RenderSupaika(RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelSupaika(), 0.5f);
        this.addLayer(new LayerSupaikaGlow(this));
    }

    @Override
    protected ResourceLocation getEntityTexture(EntitySupaika entity) {
        return texture;
    }

    @Override
    protected void preRenderCallback(EntitySupaika entity, float partialTickTime) {
        if (entity.isChild()) {
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
        }
    }
}