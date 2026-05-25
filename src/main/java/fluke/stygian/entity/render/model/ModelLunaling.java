package fluke.stygian.entity.render.model;

import fluke.stygian.entity.EntityLunaling;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class ModelLunaling extends ModelBase {
    public ModelRenderer body;
    public ModelRenderer head;
    public ModelRenderer rightleg;
    public ModelRenderer leftleg;
    public ModelRenderer rightarm;
    public ModelRenderer leftarm;

    public ModelLunaling() {
        textureWidth = 64;
        textureHeight = 64;

        body = new ModelRenderer(this);
        body.setRotationPoint(0.0F, 26.0F, 0.0F);
        body.cubeList.add(new ModelBox(body, 0, 11, -3.0F, -12.0F, -2.0F, 6, 6, 4, 0.0F, false));

        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, 14.0F, 0.0F);
        head.cubeList.add(new ModelBox(head, 0, 0, -3.0F, -5.0F, -3.0F, 6, 5, 6, 0.0F, false));

        rightleg = new ModelRenderer(this);
        rightleg.setRotationPoint(-2.0F, 20.0F, 0.0F);
        rightleg.cubeList.add(new ModelBox(rightleg, 20, 0, -1.0F, 0.0F, -1.0F, 2, 4, 2, 0.0F, false));

        leftleg = new ModelRenderer(this);
        leftleg.setRotationPoint(2.0F, 20.0F, 0.0F);
        leftleg.cubeList.add(new ModelBox(leftleg, 20, 0, -1.0F, 0.0F, -1.0F, 2, 4, 2, 0.0F, true));

        rightarm = new ModelRenderer(this);
        rightarm.setRotationPoint(-3.0F, 16.0F, 0.0F);
        rightarm.cubeList.add(new ModelBox(rightarm, 28, 0, -3.0F, -2.0F, -2.0F, 3, 7, 4, 0.0F, false));

        leftarm = new ModelRenderer(this);
        leftarm.setRotationPoint(3.0F, 16.0F, 0.0F);
        leftarm.cubeList.add(new ModelBox(leftarm, 28, 0, 0.0F, -2.0F, -2.0F, 3, 7, 4, 0.0F, true));
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        body.render(f5);
        head.render(f5);
        rightleg.render(f5);
        leftleg.render(f5);
        rightarm.render(f5);
        leftarm.render(f5);
    }

    public void setRotationAngles(final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scaleFactor, final Entity entityIn) {
        final float f = 1.0f;
        this.leftleg.rotateAngleX = 1.5f * this.triangleWave(limbSwing, 13.0f) * limbSwingAmount;
        this.rightleg.rotateAngleX = -1.5f * this.triangleWave(limbSwing, 13.0f) * limbSwingAmount;
        this.leftleg.rotateAngleY = 0.0f;
        this.rightleg.rotateAngleY = 0.0f;
        this.leftleg.rotateAngleZ = 0.0f;
        this.rightleg.rotateAngleZ = 0.0f;
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.head.rotateAngleX = headPitch * 0.017453292F;
    }

    public void setLivingAnimations(final EntityLivingBase entitylivingbaseIn, final float limbSwing, final float limbSwingAmount, final float partialTickTime) {
        final EntityLunaling entityirongolem = (EntityLunaling)entitylivingbaseIn;
        final int i = entityirongolem.getAttackTimer();
        if (i > 0) {
            this.rightarm.rotateAngleX = -2.0f + 1.5f * this.triangleWave(i - partialTickTime, 10.0f);
            this.leftarm.rotateAngleX = -2.0f + 1.5f * this.triangleWave(i - partialTickTime, 10.0f);
        }
        else {
            this.rightarm.rotateAngleX = (-0.2f + 1.5f * this.triangleWave(limbSwing, 13.0f)) * limbSwingAmount;
            this.leftarm.rotateAngleX = (-0.2f - 1.5f * this.triangleWave(limbSwing, 13.0f)) * limbSwingAmount;
        }
    }

    private float triangleWave(final float p_78172_1_, final float p_78172_2_) {
        return (Math.abs(p_78172_1_ % p_78172_2_ - p_78172_2_ * 0.5f) - p_78172_2_ * 0.25f) / (p_78172_2_ * 0.25f);
    }
}