package fluke.stygian.entity.render;

import fluke.stygian.entity.EntityCracky;
import fluke.stygian.entity.render.model.ModelCracky;
import fluke.stygian.entity.render.model.layer.LayerCrackyGlow;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderCracky extends RenderLiving<EntityCracky> {

    private static final ResourceLocation texture = new ResourceLocation("stygian:textures/entity/cracky.png");

    public RenderCracky(RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelCracky(), 0.4f);
        this.addLayer(new LayerCrackyGlow(this));
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityCracky entity) {
        return texture;
    }
}