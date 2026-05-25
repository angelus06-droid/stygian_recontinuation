package fluke.stygian.entity.render;

import fluke.stygian.entity.EntityEndWyrm;
import fluke.stygian.entity.render.model.ModelEndWyrm;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderEndWyrm extends RenderLiving<EntityEndWyrm> {

    private static final ResourceLocation texture = new ResourceLocation("stygian:textures/entity/endwyrm.png");

    public RenderEndWyrm(RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelEndWyrm(), 0.3f);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityEndWyrm entity) {
        return texture;
    }

    @Override
    protected void preRenderCallback(EntityEndWyrm entity, float partialTickTime) {
    }
}