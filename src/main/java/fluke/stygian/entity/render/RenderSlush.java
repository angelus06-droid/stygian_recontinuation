package fluke.stygian.entity.render;

import fluke.stygian.entity.EntitySlush;
import fluke.stygian.entity.render.model.ModelSlush;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderSlush extends RenderLiving<EntitySlush> {

    private static final ResourceLocation texture = new ResourceLocation("stygian:textures/entity/slush.png");

    public RenderSlush(RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelSlush(), 0.5f);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntitySlush entity) {
        return texture;
    }

    @Override
    public void doRender(EntitySlush entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

        super.doRender(entity, x, y, z, entityYaw, partialTicks);

        GlStateManager.disableBlend();
    }

    @Override
    protected void preRenderCallback(EntitySlush entity, float partialTickTime) {
        if (entity.isChild()) {
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
        }
    }
}