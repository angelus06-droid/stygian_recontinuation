package fluke.stygian.entity.render;

import fluke.stygian.entity.EntityPix;
import fluke.stygian.entity.render.model.ModelPix;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderPix extends RenderLiving<EntityPix> {

    private static final ResourceLocation texture = new ResourceLocation("stygian:textures/entity/pix.png");

    public RenderPix(RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelPix(), 0.2f);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityPix entity) {
        return texture;
    }

    @Override
    protected void preRenderCallback(EntityPix entity, float partialTickTime) {
    }
}