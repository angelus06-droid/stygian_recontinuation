package fluke.stygian.entity.render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelLunarZorp extends ModelBase {
    public ModelRenderer all;
    public ModelRenderer body;
    public ModelRenderer head;
    public ModelRenderer rightWing;
    public ModelRenderer rightWing_r1;
    public ModelRenderer rightWing_r2;
    public ModelRenderer leftWing;
    public ModelRenderer leftWing_r1;
    public ModelRenderer leftWing_r2;
    public ModelRenderer rightLeg;
    public ModelRenderer leftLeg;

    public ModelLunarZorp() {
        this.textureWidth = 64;
        this.textureHeight = 64;

        this.all = new ModelRenderer(this);
        this.all.setRotationPoint(0.0F, 24.0F, 0.0F);

        this.body = new ModelRenderer(this);
        this.body.setRotationPoint(0.0F, -9.0F, 0.0F);
        this.all.addChild(body);
        this.body.cubeList.add(new ModelBox(body, 0, 12, -5.0F, -9.0F, -4.0F, 10, 10, 8, 0.0F, false));
        this.body.cubeList.add(new ModelBox(body, 16, 42, -6.0F, -10.0F, -5.0F, 12, 12, 10, 0.0F, false));
        this.body.cubeList.add(new ModelBox(body, 24, 0, -1.0F, -15.0F, -1.0F, 2, 6, 2, 0.0F, false));

        this.head = new ModelRenderer(this);
        this.head.setRotationPoint(0.0F, -15.0F, 0.0F);
        this.body.addChild(head);
        this.head.cubeList.add(new ModelBox(head, 0, 0, -3.0F, -6.0F, -3.0F, 6, 6, 6, 0.0F, false));
        this.head.cubeList.add(new ModelBox(head, 32, 6, -3.0F, 0.0F, -3.0F, 6, 1, 6, 0.0F, false));

        this.rightWing = new ModelRenderer(this);
        this.rightWing.setRotationPoint(-3.0F, -3.0F, 1.0F);
        this.head.addChild(rightWing);
        this.setRotationAngle(rightWing, 0.1745F, -0.7418F, -0.0873F);
        this.rightWing.cubeList.add(new ModelBox(rightWing, 51, 31, 0.0F, -1.0F, 0.0F, 1, 2, 2, 0.0F, false));

        this.rightWing_r1 = new ModelRenderer(this);
        this.rightWing_r1.setRotationPoint(0.0F, 0.0F, 1.0F);
        this.rightWing.addChild(rightWing_r1);
        this.setRotationAngle(rightWing_r1, -0.3491F, -0.1745F, 0.0F);
        this.rightWing_r1.cubeList.add(new ModelBox(rightWing_r1, 48, 20, 0.0F, -1.5F, 0.0F, 0, 3, 7, 0.0F, false));

        this.rightWing_r2 = new ModelRenderer(this);
        this.rightWing_r2.setRotationPoint(0.0F, 0.0F, 1.0F);
        this.rightWing.addChild(rightWing_r2);
        this.setRotationAngle(rightWing_r2, 0.4363F, -0.3491F, 0.0F);
        this.rightWing_r2.cubeList.add(new ModelBox(rightWing_r2, 46, 12, 0.0F, -1.5F, 0.0F, 0, 3, 9, 0.0F, false));

        this.leftWing = new ModelRenderer(this);
        this.leftWing.setRotationPoint(3.0F, -3.0F, 1.0F);
        this.head.addChild(leftWing);
        this.setRotationAngle(leftWing, 0.1745F, 0.7418F, 0.0873F);
        this.leftWing.cubeList.add(new ModelBox(leftWing, 51, 31, -1.0F, -1.0F, 0.0F, 1, 2, 2, 0.0F, false));

        this.leftWing_r1 = new ModelRenderer(this);
        this.leftWing_r1.setRotationPoint(0.0F, 0.0F, 1.0F);
        this.leftWing.addChild(leftWing_r1);
        this.setRotationAngle(leftWing_r1, -0.3491F, 0.1745F, 0.0F);
        this.leftWing_r1.cubeList.add(new ModelBox(leftWing_r1, 48, 20, 0.0F, -1.5F, 0.0F, 0, 3, 7, 0.0F, false));

        this.leftWing_r2 = new ModelRenderer(this);
        this.leftWing_r2.setRotationPoint(0.0F, 0.0F, 1.0F);
        this.leftWing.addChild(leftWing_r2);
        this.setRotationAngle(leftWing_r2, 0.4363F, 0.3491F, 0.0F);
        this.leftWing_r2.cubeList.add(new ModelBox(leftWing_r2, 46, 12, 0.0F, -1.5F, 0.0F, 0, 3, 9, 0.0F, false));

        this.rightLeg = new ModelRenderer(this);
        this.rightLeg.setRotationPoint(-2.0F, -8.0F, 0.0F);
        this.all.addChild(rightLeg);
        this.rightLeg.cubeList.add(new ModelBox(rightLeg, 0, 30, -3.0F, 0.0F, -2.0F, 4, 8, 4, 0.0F, false));

        this.leftLeg = new ModelRenderer(this);
        this.leftLeg.setRotationPoint(3.0F, -8.0F, 0.0F);
        this.all.addChild(leftLeg);
        this.leftLeg.cubeList.add(new ModelBox(leftLeg, 0, 30, -2.0F, 0.0F, -2.0F, 4, 8, 4, 0.0F, true));
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
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.head.rotateAngleX = headPitch * 0.017453292F;

        this.rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;

        this.body.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * 0.05F * limbSwingAmount;

        float wingSpeed;
        float wingAmplitude;

        if (!entityIn.onGround) {
            wingSpeed = ageInTicks * 1.5F;
            wingAmplitude = 0.8F;
        } else {
            wingSpeed = ageInTicks * 0.15F;
            wingAmplitude = 0.1F;
        }

        this.rightWing.rotateAngleZ = -0.0873F - (MathHelper.sin(wingSpeed) * wingAmplitude);
        this.leftWing.rotateAngleZ = 0.0873F + (MathHelper.sin(wingSpeed) * wingAmplitude);

        float secondarySwing = MathHelper.cos(wingSpeed) * 0.1F;
        this.rightWing_r2.rotateAngleX = 0.4363F + secondarySwing;
        this.leftWing_r2.rotateAngleX = 0.4363F + secondarySwing;

        this.body.rotationPointY = -9.0F + (MathHelper.sin(ageInTicks * 0.1F) * 0.2F);
    }
}