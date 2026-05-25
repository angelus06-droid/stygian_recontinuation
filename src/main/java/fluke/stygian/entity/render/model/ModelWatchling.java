package fluke.stygian.entity.render.model;

import fluke.stygian.entity.EntityWatchling;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

public class ModelWatchling extends ModelBase {
    ModelRenderer rightArm;
    ModelRenderer leftArm;
    ModelRenderer head;
    ModelRenderer rightLeg;
    ModelRenderer leftLeg;
    ModelRenderer body;

    public ModelWatchling() {
        this.textureWidth = 64;
        this.textureHeight = 64;

        this.rightLeg = new ModelRenderer(this);
        this.rightLeg.setRotationPoint(-2.5F, 4.0F, 0.0F);
        this.rightLeg.cubeList.add(new ModelBox(rightLeg, 39, 2, -1.0F, -1.0F, -1.0F, 2, 21, 2, 0.0F, false));

        this.leftLeg = new ModelRenderer(this);
        this.leftLeg.setRotationPoint(2.5F, 4.0F, 0.0F);
        this.leftLeg.cubeList.add(new ModelBox(leftLeg, 39, 2, -1.0F, -1.0F, -1.0F, 2, 21, 2, 0.0F, true));

        this.body = new ModelRenderer(this);
        this.body.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.body.cubeList.add(new ModelBox(body, 0, 19, -5.5F, -13.0F, -3.0F, 11, 15, 6, 0.0F, false));

        this.rightArm = new ModelRenderer(this);
        this.rightArm.setRotationPoint(-6.0F, -11.0F, 0.0F);
        this.body.addChild(rightArm);
        this.rightArm.cubeList.add(new ModelBox(rightArm, 48, 0, -3.5F, -2.0F, -2.0F, 4, 29, 4, 0.0F, false));

        this.leftArm = new ModelRenderer(this);
        this.leftArm.setRotationPoint(6.0F, -11.0F, 0.0F);
        this.body.addChild(leftArm);
        this.leftArm.cubeList.add(new ModelBox(leftArm, 48, 0, -0.5F, -2.0F, -2.0F, 4, 29, 4, 0.0F, true));

        this.head = new ModelRenderer(this);
        this.head.setRotationPoint(0.0F, -13.0F, 0.0F);
        this.body.addChild(head);
        this.head.cubeList.add(new ModelBox(head, 0, 0, -4.5F, -8.0F, -3.5F, 9, 8, 7, 0.0F, false));
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        rightLeg.render(f5);
        leftLeg.render(f5);
        body.render(f5);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.head.rotateAngleX = headPitch * 0.017453292F;

        this.rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 0.7F * limbSwingAmount;
        this.leftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 0.7F * limbSwingAmount;

        this.body.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * 0.05F * limbSwingAmount;

        this.body.rotateAngleX = (limbSwingAmount * 0.1F) + (MathHelper.cos(ageInTicks * 0.06F) * 0.02F);

        this.rightArm.rotateAngleZ = 0.05F + (MathHelper.cos(ageInTicks * 0.06F) * 0.05F);
        this.leftArm.rotateAngleZ = -0.05F - (MathHelper.cos(ageInTicks * 0.06F) * 0.05F);
    }

    @Override
    public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
        EntityWatchling watchling = (EntityWatchling) entitylivingbaseIn;
        int i = watchling.getAttackTimer();

        if (i > 0) {
            float attackMove = this.triangleWave((float)i - partialTickTime, 10.0F);
            this.rightArm.rotateAngleX = -1.5F + 1.5F * attackMove;
            this.leftArm.rotateAngleX = -1.5F + 1.5F * attackMove;

            this.body.rotateAngleX -= attackMove * 0.1F;
        } else {
            this.rightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount * 0.5F;
            this.leftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
        }
    }

    private float triangleWave(float p_78172_1_, float p_78172_2_) {
        return (Math.abs(p_78172_1_ % p_78172_2_ - p_78172_2_ * 0.5f) - p_78172_2_ * 0.25f) / (p_78172_2_ * 0.25f);
    }
}