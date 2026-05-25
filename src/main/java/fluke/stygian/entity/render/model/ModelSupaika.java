package fluke.stygian.entity.render.model;

import fluke.stygian.entity.EntitySupaika;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

public class ModelSupaika extends ModelBase {
    ModelRenderer all;
    ModelRenderer body;
    ModelRenderer bodyUp;
    ModelRenderer rightArm;
    ModelRenderer rightArmDown;
    ModelRenderer leftArm;
    ModelRenderer leftArmDown;
    ModelRenderer head;
    ModelRenderer rightEye;
    ModelRenderer leftEye;
    ModelRenderer rightLeg;
    ModelRenderer rightLegDown;
    ModelRenderer leftLeg;
    ModelRenderer leftLegDown;

    public ModelSupaika() {
        textureWidth = 64;
        textureHeight = 64;

        all = new ModelRenderer(this);
        all.setRotationPoint(0.0F, -10.0F, 0.0F);

        body = new ModelRenderer(this);
        body.setRotationPoint(0.0F, 11.0F, 0.0F);
        all.addChild(body);
        body.cubeList.add(new ModelBox(body, 28, 28, -6.0F, -1.0F, -1.0F, 12, 2, 2, 0.0F, false));
        body.cubeList.add(new ModelBox(body, 12, 22, -1.5F, -9.0F, -0.5F, 3, 9, 3, 0.0F, false));
        body.cubeList.add(new ModelBox(body, 24, 22, -5.5F, -4.0F, 0.0F, 11, 2, 2, 0.0F, false));
        body.cubeList.add(new ModelBox(body, 24, 22, -5.5F, -8.0F, 0.0F, 11, 2, 2, 0.0F, false));

        bodyUp = new ModelRenderer(this);
        bodyUp.setRotationPoint(0.0F, -9.0F, 1.0F);
        body.addChild(bodyUp);
        bodyUp.cubeList.add(new ModelBox(bodyUp, 24, 22, -5.5F, -7.0F, -1.0F, 11, 2, 2, 0.0F, false));
        bodyUp.cubeList.add(new ModelBox(bodyUp, 24, 22, -5.5F, -3.0F, -1.0F, 11, 2, 2, 0.0F, false));
        bodyUp.cubeList.add(new ModelBox(bodyUp, 0, 22, -1.5F, -10.0F, -1.5F, 3, 10, 3, 0.2F, false));
        bodyUp.cubeList.add(new ModelBox(bodyUp, 0, 16, -8.0F, -11.0F, -2.5F, 16, 3, 3, 0.0F, false));

        rightArm = new ModelRenderer(this);
        rightArm.setRotationPoint(8.0F, -9.5F, -1.0F);
        bodyUp.addChild(rightArm);
        rightArm.cubeList.add(new ModelBox(rightArm, 0, 0, 0.0F, -1.5F, -1.5F, 3, 12, 3, 0.0F, true));

        rightArmDown = new ModelRenderer(this);
        rightArmDown.setRotationPoint(1.5F, 9.5F, 0.0F);
        rightArm.addChild(rightArmDown);
        rightArmDown.cubeList.add(new ModelBox(rightArmDown, 24, 0, -2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F, true));

        leftArm = new ModelRenderer(this);
        leftArm.setRotationPoint(-8.0F, -9.5F, -1.0F);
        bodyUp.addChild(leftArm);
        leftArm.cubeList.add(new ModelBox(leftArm, 0, 0, -3.0F, -1.5F, -1.5F, 3, 12, 3, 0.0F, false));

        leftArmDown = new ModelRenderer(this);
        leftArmDown.setRotationPoint(-1.5F, 9.5F, 0.0F);
        leftArm.addChild(leftArmDown);
        leftArmDown.cubeList.add(new ModelBox(leftArmDown, 24, 0, -2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F, false));

        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, -11.0F, -1.0F);
        bodyUp.addChild(head);
        head.cubeList.add(new ModelBox(head, 0, 35, -4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F, false));

        rightEye = new ModelRenderer(this);
        rightEye.setRotationPoint(-2.0F, -4.0F, -5.0F);
        head.addChild(rightEye);
        setRotationAngle(rightEye, 0.1309F, 0.1745F, 0.3054F);
        rightEye.cubeList.add(new ModelBox(rightEye, 4, 51, -3.0F, -1.0F, 0.0F, 4, 3, 0, 0.0F, false));

        leftEye = new ModelRenderer(this);
        leftEye.setRotationPoint(2.0F, -4.0F, -5.0F);
        head.addChild(leftEye);
        setRotationAngle(leftEye, 0.1309F, -0.1745F, -0.3054F);
        leftEye.cubeList.add(new ModelBox(leftEye, 12, 51, -1.0F, -1.0F, 0.0F, 4, 3, 0, 0.0F, false));

        rightLeg = new ModelRenderer(this);
        rightLeg.setRotationPoint(-5.0F, 11.0F, 0.0F);
        all.addChild(rightLeg);
        rightLeg.cubeList.add(new ModelBox(rightLeg, 12, 0, -2.0F, 0.0F, -1.5F, 3, 12, 3, 0.0F, false));

        rightLegDown = new ModelRenderer(this);
        rightLegDown.setRotationPoint(-0.5F, 11.0F, 0.0F);
        rightLeg.addChild(rightLegDown);
        rightLegDown.cubeList.add(new ModelBox(rightLegDown, 40, 0, -2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F, false));

        leftLeg = new ModelRenderer(this);
        leftLeg.setRotationPoint(5.0F, 11.0F, 0.0F);
        all.addChild(leftLeg);
        leftLeg.cubeList.add(new ModelBox(leftLeg, 12, 0, -1.0F, 0.0F, -1.5F, 3, 12, 3, 0.0F, true));

        leftLegDown = new ModelRenderer(this);
        leftLegDown.setRotationPoint(0.5F, 11.0F, 0.0F);
        leftLeg.addChild(leftLegDown);
        leftLegDown.cubeList.add(new ModelBox(leftLegDown, 40, 0, -2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F, true));
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        all.render(f5);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        EntitySupaika boss = (EntitySupaika) entityIn;

        this.all.rotationPointY = -10.0F;
        this.body.rotateAngleX = 0;
        this.body.rotateAngleY = 0;
        this.body.rotateAngleZ = 0;
        this.bodyUp.rotateAngleX = 0;
        this.bodyUp.rotateAngleY = 0;
        this.bodyUp.rotateAngleZ = 0;
        this.leftArm.rotateAngleY = 0;
        this.rightArm.rotateAngleY = 0;
        this.leftArmDown.rotateAngleY = 0;
        this.rightArmDown.rotateAngleY = 0;

        float idleTimer = ageInTicks * 0.05F;
        float breatheOffset = MathHelper.sin(idleTimer) * 0.05F;

        this.body.rotateAngleX += breatheOffset;
        this.head.rotateAngleX = headPitch * 0.017453292F + (breatheOffset * 0.5F);
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;

        this.rightArm.rotateAngleZ = -0.05F - (MathHelper.cos(idleTimer) * 0.02F);
        this.leftArm.rotateAngleZ = 0.05F + (MathHelper.cos(idleTimer) * 0.02F);

        this.rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 0.8F * limbSwingAmount;
        this.leftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 0.8F * limbSwingAmount;
        this.rightLegDown.rotateAngleX = Math.max(0, MathHelper.sin(limbSwing * 0.6662F + (float)Math.PI) * 0.6F * limbSwingAmount);
        this.leftLegDown.rotateAngleX = Math.max(0, MathHelper.sin(limbSwing * 0.6662F) * 0.6F * limbSwingAmount);

        this.rightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 0.6F * limbSwingAmount;
        this.leftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 0.6F * limbSwingAmount;
        this.rightArmDown.rotateAngleX = -Math.abs(MathHelper.sin(limbSwing * 0.6662F) * 0.4F * limbSwingAmount);
        this.leftArmDown.rotateAngleX = -Math.abs(MathHelper.sin(limbSwing * 0.6662F + (float)Math.PI) * 0.4F * limbSwingAmount);

        float shotT = (float)boss.getBlastShotTimer();
        float slamT = (float)boss.getSlamTimer();
        float groundT = (float)boss.getGroundSlamTimer();
        int attackTimer = boss.getAttackTimer();

        if (groundT > 0) {
            float progress = 1.0F - (groundT / 5.0F);
            this.body.rotateAngleX = this.toRad(40.0F * (1.0F - progress));
            this.rightArm.rotateAngleX = this.toRad(-40.0F * (1.0F - progress));
            this.leftArm.rotateAngleX = this.toRad(-40.0F * (1.0F - progress));
            this.rightArmDown.rotateAngleX = this.toRad(-20.0F * (1.0F - progress));
            this.leftArmDown.rotateAngleX = this.toRad(-20.0F * (1.0F - progress));
            this.all.rotationPointY = -10.0F + (2.0F * (1.0F - progress));
        }
        else if (slamT > 0) {
            this.body.rotateAngleX = this.toRad(-20.0F);
            this.rightArm.rotateAngleX = this.toRad(-120.0F);
            this.leftArm.rotateAngleX = this.toRad(-120.0F);
            this.rightArmDown.rotateAngleX = this.toRad(-40.0F);
            this.leftArmDown.rotateAngleX = this.toRad(-40.0F);
            float jumpCurve = MathHelper.sin((1.0F - (slamT / 30.0F)) * (float)Math.PI);
            this.all.rotationPointY = -10.0F - (jumpCurve * 5.0F);
        }
        else if (shotT > 0) {
            float progress = 1.0F - (shotT / 20.0F);

            if (progress < 0.5F) {
                float p = progress * 2.0F;

                this.rightArm.rotateAngleX = this.toRad(-20.0F + (10.0F * p));
                this.rightArm.rotateAngleY = this.toRad(-10.0F + (5.0F * p));
                this.rightArm.rotateAngleZ = this.toRad(-30.0F + (10.0F * p));

                this.leftArm.rotateAngleX = this.toRad(-80.0F + (40.0F * p));
                this.leftArm.rotateAngleY = this.toRad(-80.0F + (160.0F * p));

                this.bodyUp.rotateAngleX = this.toRad(40.0F - (20.0F * p));
                this.bodyUp.rotateAngleY = this.toRad(-20.0F + (60.0F * p));
                this.bodyUp.rotateAngleZ = this.toRad(10.0F - (20.0F * p));

                this.leftArmDown.rotateAngleX = this.toRad(-40.0F + (20.0F * p));
                this.leftArmDown.rotateAngleY = this.toRad(-20.0F + (10.0F * p));

                this.rightArmDown.rotateAngleX = this.toRad(-40.0F + (20.0F * p));

            } else {
                float p2 = (progress - 0.5F) * 2.0F;

                this.rightArm.rotateAngleX = this.toRad(-10.0F + (10.0F * p2));
                this.rightArm.rotateAngleY = this.toRad(-5.0F + (5.0F * p2));
                this.rightArm.rotateAngleZ = this.toRad(-20.0F + (20.0F * p2));

                this.leftArm.rotateAngleX = this.toRad(-40.0F + (40.0F * p2));
                this.leftArm.rotateAngleY = this.toRad(80.0F - (80.0F * p2));

                this.bodyUp.rotateAngleX = this.toRad(20.0F - (20.0F * p2));
                this.bodyUp.rotateAngleY = this.toRad(40.0F - (40.0F * p2));
                this.bodyUp.rotateAngleZ = this.toRad(-10.0F + (10.0F * p2));

                this.leftArmDown.rotateAngleX = this.toRad(-20.0F + (20.0F * p2));
                this.leftArmDown.rotateAngleY = this.toRad(-10.0F + (10.0F * p2));

                this.rightArmDown.rotateAngleX = this.toRad(-20.0F + (20.0F * p2));
            }
        }
        else if (attackTimer > 0) {
            float f = (float)attackTimer;
            float swing = this.triangleWave(f, 10.0f);
            this.rightArm.rotateAngleX = -1.5f + (1.2f * swing);
            this.leftArm.rotateAngleX = -1.5f + (1.2f * swing);
            this.rightArmDown.rotateAngleX = -0.5f;
            this.leftArmDown.rotateAngleX = -0.5f;
        }
    }

    private float toRad(float degrees) {
        return degrees * 0.017453292F;
    }

    @Override
    public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
    }

    private float triangleWave(final float p_78172_1_, final float p_78172_2_) {
        return (Math.abs(p_78172_1_ % p_78172_2_ - p_78172_2_ * 0.5f) - p_78172_2_ * 0.25f) / (p_78172_2_ * 0.25f);
    }
}