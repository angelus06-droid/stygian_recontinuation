package fluke.stygian.entity.render;

import fluke.stygian.entity.EntitySkipper;
import fluke.stygian.entity.render.model.ModelSkipper;
import fluke.stygian.entity.render.model.layer.LayerSkipperGlow;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderSkipper extends RenderLiving<EntitySkipper> {

    private static final ResourceLocation texture = new ResourceLocation("stygian:textures/entity/skipper.png");

    public RenderSkipper(RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelSkipper(), 0.5f);
        this.addLayer(new LayerSkipperGlow(this));
    }

    @Override
    protected ResourceLocation getEntityTexture(EntitySkipper entity) {
        return texture;
    }

    @Override
    protected void preRenderCallback(EntitySkipper entitylivingbaseIn, float partialTickTime) {
        float f = entitylivingbaseIn.getCreeperFlashIntensity(partialTickTime);

        float f1 = 1.0F + MathHelper.sin(f * 100.0F) * f * 0.01F;
        f = MathHelper.clamp(f, 0.0F, 1.0F);
        f = f * f;
        f = f * f;
        float f2 = (1.0F + f * 0.4F) * f1;
        float f3 = (1.0F + f * 0.4F) / f1;

        GlStateManager.scale(f2, f3, f2);
    }

    @Override
    protected int getColorMultiplier(EntitySkipper entitylivingbaseIn, float lightBrightness, float partialTickTime) {
        float f = entitylivingbaseIn.getCreeperFlashIntensity(partialTickTime);

        if ((int)(f * 10.0F) % 2 == 0) {
            return 0;
        } else {
            int i = (int)(f * 0.2F * 255.0F);
            i = MathHelper.clamp(i, 0, 255);
            return i << 24 | 0xdbe64e;
        }
    }
}