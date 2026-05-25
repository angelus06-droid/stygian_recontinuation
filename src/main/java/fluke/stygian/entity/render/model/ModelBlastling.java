package fluke.stygian.entity.render.model;

import fluke.stygian.entity.EntityBlastling;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

public class ModelBlastling extends ModelBase {
    ModelRenderer leftLeg;
    ModelRenderer rightLeg;
    ModelRenderer body;
    ModelRenderer head;
    ModelRenderer rightArm;
    ModelRenderer leftArm;

    public ModelBlastling() {
        this.textureWidth = 128;
        this.textureHeight = 64;

        this.leftLeg = new ModelRenderer(this);
        this.leftLeg.setRotationPoint(2.5F, 3.0F, 0.0F);
        this.leftLeg.cubeList.add(new ModelBox(leftLeg, 100, 0, -1.0F, -1.0F, -1.0F, 2, 22, 2, 0.0F, true));

        this.rightLeg = new ModelRenderer(this);
        this.rightLeg.setRotationPoint(-2.5F, 3.0F, 0.0F);
        this.rightLeg.cubeList.add(new ModelBox(rightLeg, 100, 0, -1.0F, -1.0F, -1.0F, 2, 22, 2, 0.0F, false));

        this.body = new ModelRenderer(this);
        this.body.setRotationPoint(0.0F, 3.0F, 0.0F);
        this.body.cubeList.add(new ModelBox(body, 60, 0, -6.0F, -16.0F, -4.0F, 12, 16, 8, 0.0F, false));

        this.head = new ModelRenderer(this);
        this.head.setRotationPoint(0.0F, -12.0F, -1.0F);
        this.body.addChild(head);
        this.head.cubeList.add(new ModelBox(head, 0, 0, -4.0F, -8.0F, -6.0F, 8, 8, 8, 0.0F, false));
        this.head.cubeList.add(new ModelBox(head, 0, 16, -4.0F, -11.0F, -6.0F, 8, 11, 8, 0.5F, false));

        this.rightArm = new ModelRenderer(this);
        this.rightArm.setRotationPoint(-6.0F, -11.0F, 0.0F);
        this.body.addChild(rightArm);
        this.rightArm.cubeList.add(new ModelBox(rightArm, 32, 0, -7.0F, -4.0F, -3.5F, 7, 35, 7, 0.0F, false));

        this.leftArm = new ModelRenderer(this);
        this.leftArm.setRotationPoint(6.0F, -11.0F, 0.0F);
        this.body.addChild(leftArm);
        this.leftArm.cubeList.add(new ModelBox(leftArm, 32, 0, 0.0F, -4.0F, -3.5F, 7, 35, 7, 0.0F, true));
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        leftLeg.render(f5);
        rightLeg.render(f5);
        body.render(f5);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.head.rotateAngleX = headPitch * 0.017453292F;

        this.rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 0.7F * limbSwingAmount;
        this.leftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 0.7F * limbSwingAmount;

        this.body.rotateAngleX = (limbSwingAmount * 0.1F) + (MathHelper.cos(ageInTicks * 0.06F) * 0.02F);

        this.rightArm.rotateAngleZ = 0.05F + (MathHelper.cos(ageInTicks * 0.06F) * 0.05F);
        this.leftArm.rotateAngleZ = -0.05F - (MathHelper.cos(ageInTicks * 0.06F) * 0.05F);
    }

    @Override
    public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
        EntityBlastling watchling = (EntityBlastling) entitylivingbaseIn;
        int i = watchling.getAttackTimer();

        if (watchling.isArmsRaised()) {
            float baseRotation = -1.5F;

            float shake = MathHelper.sin((watchling.ticksExisted + partialTickTime) * 1.0F) * 0.1F;

            this.rightArm.rotateAngleX = baseRotation + shake;
            this.leftArm.rotateAngleX = baseRotation - shake;

            float shakeZ = MathHelper.cos((watchling.ticksExisted + partialTickTime) * 1.5F) * 0.05F;
            this.rightArm.rotateAngleZ = 0.1F + shakeZ;
            this.leftArm.rotateAngleZ = -0.1F - shakeZ;

        }
        else if (i > 0) {
            float attackMove = this.triangleWave((float)i - partialTickTime, 10.0F);
            this.rightArm.rotateAngleX = -1.5F + 1.5F * attackMove;
            this.leftArm.rotateAngleX = -1.5F + 1.5F * attackMove;

            this.body.rotateAngleX -= attackMove * 0.1F;
        }
        else {
            this.rightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount * 0.5F;
            this.leftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;

            this.rightArm.rotateAngleZ = 0.05F + (MathHelper.cos((watchling.ticksExisted + partialTickTime) * 0.06F) * 0.05F);
            this.leftArm.rotateAngleZ = -0.05F - (MathHelper.cos((watchling.ticksExisted + partialTickTime) * 0.06F) * 0.05F);
        }
    }

    private float triangleWave(float p_78172_1_, float p_78172_2_) {
        return (Math.abs(p_78172_1_ % p_78172_2_ - p_78172_2_ * 0.5f) - p_78172_2_ * 0.25f) / (p_78172_2_ * 0.25f);
    }
}