package fluke.stygian.entity.render;

import fluke.stygian.entity.EntityEndbob;
import fluke.stygian.entity.render.model.ModelEndbob;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderEndbob extends RenderLiving<EntityEndbob> {

    private static final ResourceLocation texture = new ResourceLocation("stygian:textures/entity/endbob.png");

    public RenderEndbob(RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelEndbob(), 0.2f);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityEndbob entity) {
        return texture;
    }

    @Override
    protected void preRenderCallback(EntityEndbob entity, float partialTickTime) {
        if (entity.isChild()) {
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
        }
    }
}