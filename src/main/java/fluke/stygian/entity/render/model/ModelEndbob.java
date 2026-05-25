package fluke.stygian.entity.render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelEndbob extends ModelBase {
    ModelRenderer body;
    ModelRenderer body_r1;
    ModelRenderer body_r2;
    ModelRenderer body_r3;
    ModelRenderer rightEye;
    ModelRenderer RightEyeBlink;
    ModelRenderer leftEye;
    ModelRenderer leftEyeBlink;
    ModelRenderer rightLeg;
    ModelRenderer leftLeg;

    public ModelEndbob() {
        this.textureWidth = 64;
        this.textureHeight = 64;

        this.body = new ModelRenderer(this);
        this.body.setRotationPoint(0.0F, 18.0F, 0.0F);
        this.body.cubeList.add(new ModelBox(body, 0, 0, -4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F, false));
        this.body.cubeList.add(new ModelBox(body, 0, 25, -5.0F, -9.0F, -5.0F, 10, 10, 10, 0.0F, false));

        this.body_r1 = new ModelRenderer(this);
        this.body_r1.setRotationPoint(-2.0F, -8.0F, 3.0F);
        this.body.addChild(body_r1);
        this.setRotationAngle(body_r1, -0.3927F, 0.3491F, -0.1745F);
        this.body_r1.cubeList.add(new ModelBox(body_r1, 0, 58, -3.0F, -3.0F, 0.0F, 7, 3, 0, 0.0F, false));

        this.body_r2 = new ModelRenderer(this);
        this.body_r2.setRotationPoint(4.0F, -9.0F, -1.0F);
        this.body.addChild(body_r2);
        this.setRotationAngle(body_r2, 0.3491F, -0.5672F, -0.1309F);
        this.body_r2.cubeList.add(new ModelBox(body_r2, 0, 61, -6.0F, -3.0F, 0.0F, 7, 3, 0, 0.0F, false));

        this.body_r3 = new ModelRenderer(this);
        this.body_r3.setRotationPoint(0.0F, -9.0F, 1.0F);
        this.body.addChild(body_r3);
        this.setRotationAngle(body_r3, 0.2618F, 0.0F, -0.2182F);
        this.body_r3.cubeList.add(new ModelBox(body_r3, 0, 45, 0.0F, -5.0F, -3.0F, 0, 5, 4, 0.0F, false));

        this.rightEye = new ModelRenderer(this);
        this.rightEye.setRotationPoint(0.0F, -3.0F, -4.0F);
        this.body.addChild(rightEye);
        this.setRotationAngle(rightEye, 0.0F, 0.1745F, 0.0F);
        this.rightEye.cubeList.add(new ModelBox(rightEye, 34, 0, -3.0F, 0.0F, -4.0F, 3, 0, 4, 0.0F, false));
        this.rightEye.cubeList.add(new ModelBox(rightEye, 0, 0, -3.0F, -3.0F, -4.0F, 3, 3, 0, 0.0F, false));
        this.rightEye.cubeList.add(new ModelBox(rightEye, 25, 0, -3.0F, -3.0F, -3.99F, 3, 3, 0, 0.0F, false));

        this.RightEyeBlink = new ModelRenderer(this);
        this.RightEyeBlink.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.rightEye.addChild(RightEyeBlink);
        this.RightEyeBlink.cubeList.add(new ModelBox(RightEyeBlink, 0, 3, -3.0F, -3.0F, -4.01F, 3, 3, 0, 0.0F, false));

        this.leftEye = new ModelRenderer(this);
        this.leftEye.setRotationPoint(0.0F, -3.0F, -4.0F);
        this.body.addChild(leftEye);
        this.setRotationAngle(leftEye, 0.0F, -0.1745F, 0.0F);
        this.leftEye.cubeList.add(new ModelBox(leftEye, 34, 0, 0.0F, 0.0F, -4.0F, 3, 0, 4, 0.0F, false));
        this.leftEye.cubeList.add(new ModelBox(leftEye, 0, 0, 0.0F, -3.0F, -4.0F, 3, 3, 0, 0.0F, false));
        this.leftEye.cubeList.add(new ModelBox(leftEye, 25, 0, 0.0F, -3.0F, -3.99F, 3, 3, 0, 0.0F, false));

        this.leftEyeBlink = new ModelRenderer(this);
        this.leftEyeBlink.setRotationPoint(2.0F, 0.0F, -4.0F);
        this.leftEye.addChild(leftEyeBlink);
        this.leftEyeBlink.cubeList.add(new ModelBox(leftEyeBlink, 0, 3, -2.0F, -3.0F, -0.01F, 3, 3, 0, 0.0F, false));

        this.rightLeg = new ModelRenderer(this);
        this.rightLeg.setRotationPoint(-3.0F, 18.0F, 0.0F);
        this.rightLeg.cubeList.add(new ModelBox(rightLeg, 0, 16, -1.0F, 0.0F, -1.5F, 3, 6, 3, 0.0F, false));

        this.leftLeg = new ModelRenderer(this);
        this.leftLeg.setRotationPoint(3.0F, 18.0F, 0.0F);
        this.leftLeg.cubeList.add(new ModelBox(leftLeg, 0, 16, -2.0F, 0.0F, -1.5F, 3, 6, 3, 0.0F, true));
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        body.render(f5);
        rightLeg.render(f5);
        leftLeg.render(f5);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        this.rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.2F * limbSwingAmount;
        this.leftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.2F * limbSwingAmount;

        float speed = 0.05f;
        this.body.rotationPointY = 18.8F + MathHelper.sin(ageInTicks * speed) * 0.5F;
        float blinkCycle = ageInTicks % 100;

        boolean isBlinking = blinkCycle > 10 && blinkCycle < 13;

        if ((entityIn.getEntityId() % 3 == 0) && (blinkCycle > 16 && blinkCycle < 18)) {
            isBlinking = true;
        }

        this.RightEyeBlink.showModel = isBlinking;
        this.leftEyeBlink.showModel = isBlinking;

        this.body.rotateAngleY = netHeadYaw * 0.017453292F;
        this.body.rotateAngleX = headPitch * 0.017453292F;
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}