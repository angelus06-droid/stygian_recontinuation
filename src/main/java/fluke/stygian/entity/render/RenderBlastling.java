package fluke.stygian.entity.render;

import fluke.stygian.entity.EntityBlastling;
import fluke.stygian.entity.render.model.ModelBlastling;
import fluke.stygian.entity.render.model.layer.LayerBlastlingGlow;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBlastling extends RenderLiving<EntityBlastling> {

    private static final ResourceLocation texture = new ResourceLocation("stygian:textures/entity/blastling.png");

    public RenderBlastling(RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelBlastling(), 0.4f);
        this.addLayer(new LayerBlastlingGlow(this));
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityBlastling entity) {
        return texture;
    }
}