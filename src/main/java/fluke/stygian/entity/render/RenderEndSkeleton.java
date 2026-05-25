package fluke.stygian.entity.render;

import fluke.stygian.entity.EntityEndSkeleton;
import fluke.stygian.entity.render.model.ModelEndSkeleton;
import fluke.stygian.entity.render.model.layer.LayerEndSkeletonGlow;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderEndSkeleton extends RenderBiped<EntityEndSkeleton>
{
    private static final ResourceLocation texture = new ResourceLocation("stygian:textures/entity/end_skeleton.png");

    public RenderEndSkeleton(final RenderManager renderManagerIn) {
        super(renderManagerIn, (ModelBiped)new ModelEndSkeleton(), 0.5f);
        this.addLayer(new LayerEndSkeletonGlow(this));
        this.addLayer(new LayerHeldItem((RenderLivingBase)this));
        this.addLayer(new LayerBipedArmor(this) {
            protected void initArmor() {
                this.modelLeggings = new ModelEndSkeleton(0.5f, true);
                this.modelArmor = new ModelEndSkeleton(1.0f, true);
            }
        });
    }

    protected ResourceLocation getEntityTexture(final EntityEndSkeleton entity) {
        return RenderEndSkeleton.texture;
    }

    public void transformHeldFull3DItemLayer() {
        GlStateManager.translate(0.09375f, 0.1875f, 0.0f);
    }

}