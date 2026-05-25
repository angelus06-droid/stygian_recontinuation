package fluke.stygian.entity.render;

import fluke.stygian.entity.EntitySnareling;
import fluke.stygian.entity.render.model.ModelSnareling;
import fluke.stygian.entity.render.model.layer.LayerSnarelingGlow;
import net.minecraft.client.renderer.GlStateManager; // Importante
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderSnareling extends RenderLiving<EntitySnareling> {

    private static final ResourceLocation texture = new ResourceLocation("stygian:textures/entity/snareling.png");

    public RenderSnareling(RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelSnareling(), 0.4f);
        this.addLayer(new LayerSnarelingGlow(this));
    }

    @Override
    public void doRender(EntitySnareling entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

        super.doRender(entity, x, y, z, entityYaw, partialTicks);

        GlStateManager.disableBlend();
    }

    @Override
    protected ResourceLocation getEntityTexture(EntitySnareling entity) {
        return texture;
    }
}