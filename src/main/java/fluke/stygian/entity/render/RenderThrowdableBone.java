package fluke.stygian.entity.render;

import fluke.stygian.block.ModBlocks;
import fluke.stygian.entity.projectiles.EntityThrowdableBone;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderThrowdableBone extends Render<EntityThrowdableBone> {
    private final float scale;

    public RenderThrowdableBone(final RenderManager renderManagerIn, final float scaleIn) {
        super(renderManagerIn);
        this.scale = scaleIn;
    }

    public void doRender(final EntityThrowdableBone entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks) {
        GlStateManager.pushMatrix();
        this.bindEntityTexture(entity);
        GlStateManager.translate((float)x, (float)y, (float)z);
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(this.scale, this.scale, this.scale);

        GlStateManager.enableLighting();
        // --------------------------------------------------------------------------

        final TextureAtlasSprite textureatlassprite = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getParticleIcon(ModBlocks.endBone);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();

        final float f = textureatlassprite.getMinU();
        final float f2 = textureatlassprite.getMaxU();
        final float f3 = textureatlassprite.getMinV();
        final float f4 = textureatlassprite.getMaxV();

        GlStateManager.rotate(180.0f - this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(((this.renderManager.options.thirdPersonView == 2) ? -1 : 1) * -this.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);

        if (this.renderOutlines) {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entity));
        }

        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
        bufferbuilder.pos(-0.5, -0.25, 0.0).tex((double)f, (double)f4).normal(0.0f, 1.0f, 0.0f).endVertex();
        bufferbuilder.pos(0.5, -0.25, 0.0).tex((double)f2, (double)f4).normal(0.0f, 1.0f, 0.0f).endVertex();
        bufferbuilder.pos(0.5, 0.75, 0.0).tex((double)f2, (double)f3).normal(0.0f, 1.0f, 0.0f).endVertex();
        bufferbuilder.pos(-0.5, 0.75, 0.0).tex((double)f, (double)f3).normal(0.0f, 1.0f, 0.0f).endVertex();
        tessellator.draw();

        if (this.renderOutlines) {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }

        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    protected ResourceLocation getEntityTexture(final EntityThrowdableBone entity) {
        return TextureMap.LOCATION_BLOCKS_TEXTURE;
    }
}