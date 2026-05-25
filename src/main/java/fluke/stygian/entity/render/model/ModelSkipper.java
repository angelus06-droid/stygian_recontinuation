package fluke.stygian.entity.render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelSkipper extends ModelBase {
    ModelRenderer all;
    ModelRenderer head;
    ModelRenderer body;
    ModelRenderer bodyBack;
    ModelRenderer rightLegFront;
    ModelRenderer RightLegBack;
    ModelRenderer leftLegFront;
    ModelRenderer leftLegBack;

    public ModelSkipper() {
        this.textureWidth = 64;
        this.textureHeight = 64;

        this.all = new ModelRenderer(this);
        this.all.setRotationPoint(0.0F, 18.0F, -3.0F);


        this.head = new ModelRenderer(this);
        this.head.setRotationPoint(0.0F, -1.0F, 0.0F);
        this.all.addChild(head);
        this.head.cubeList.add(new ModelBox(head, 0, 0, -3.0F, -3.0F, -6.0F, 6, 6, 6, 0.0F, false));
        this.head.cubeList.add(new ModelBox(head, 0, 12, -5.0F, 1.0F, -10.0F, 4, 1, 8, 0.0F, false));
        this.head.cubeList.add(new ModelBox(head, 0, 12, 1.0F, 1.0F, -10.0F, 4, 1, 8, 0.0F, true));

        this.body = new ModelRenderer(this);
        this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.all.addChild(body);
        this.body.cubeList.add(new ModelBox(body, 42, 35, -2.0F, -3.0F, 0.0F, 4, 4, 7, 0.0F, false));

        this.bodyBack = new ModelRenderer(this);
        this.bodyBack.setRotationPoint(0.0F, -1.0F, 6.0F);
        this.body.addChild(bodyBack);
        this.bodyBack.cubeList.add(new ModelBox(bodyBack, 46, 0, -4.0F, -4.0F, 0.0F, 8, 8, 1, 0.0F, false));
        this.bodyBack.cubeList.add(new ModelBox(bodyBack, 46, 26, -4.0F, -4.0F, 8.0F, 8, 8, 1, 0.0F, false));
        this.bodyBack.cubeList.add(new ModelBox(bodyBack, 30, 9, -5.0F, -5.0F, 1.0F, 10, 10, 7, 0.0F, false));

        this.rightLegFront = new ModelRenderer(this);
        this.rightLegFront.setRotationPoint(-2.0F, 0.0F, 1.0F);
        this.all.addChild(rightLegFront);
        this.setRotationAngle(rightLegFront, -0.3054F, -0.3054F, 0.3927F);
        this.rightLegFront.cubeList.add(new ModelBox(rightLegFront, 0, 38, -5.0F, -2.0F, -1.0F, 6, 2, 2, 0.0F, false));
        this.rightLegFront.cubeList.add(new ModelBox(rightLegFront, 0, 42, -6.0F, -1.0F, 0.0F, 3, 9, 0, 0.0F, false));

        this.RightLegBack = new ModelRenderer(this);
        this.RightLegBack.setRotationPoint(-2.0F, 0.0F, 5.0F);
        this.all.addChild(RightLegBack);
        this.setRotationAngle(RightLegBack, 0.3054F, 0.3054F, 0.3927F);
        this.RightLegBack.cubeList.add(new ModelBox(RightLegBack, 0, 38, -5.0F, -2.0F, -1.0F, 6, 2, 2, 0.0F, false));
        this.RightLegBack.cubeList.add(new ModelBox(RightLegBack, 0, 42, -6.0F, -1.0F, 0.0F, 3, 9, 0, 0.0F, false));

        this.leftLegFront = new ModelRenderer(this);
        this.leftLegFront.setRotationPoint(2.0F, 0.0F, 1.0F);
        this.all.addChild(leftLegFront);
        this.setRotationAngle(leftLegFront, -0.3054F, 0.3054F, -0.3927F);
        this.leftLegFront.cubeList.add(new ModelBox(leftLegFront, 0, 38, -1.0F, -2.0F, -1.0F, 6, 2, 2, 0.0F, true));
        this.leftLegFront.cubeList.add(new ModelBox(leftLegFront, 0, 42, 3.0F, -1.0F, 0.0F, 3, 9, 0, 0.0F, true));

        this.leftLegBack = new ModelRenderer(this);
        this.leftLegBack.setRotationPoint(2.0F, 0.0F, 5.0F);
        this.all.addChild(leftLegBack);
        this.setRotationAngle(leftLegBack, 0.3054F, -0.3054F, -0.3927F);
        this.leftLegBack.cubeList.add(new ModelBox(leftLegBack, 0, 38, -1.0F, -2.0F, -1.0F, 6, 2, 2, 0.0F, true));
        this.leftLegBack.cubeList.add(new ModelBox(leftLegBack, 0, 42, 3.0F, -1.0F, 0.0F, 3, 9, 0, 0.0F, true));
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
        float speed = 0.8F;
        float intensity = 0.4F;


        this.rightLegFront.rotateAngleY = (float)Math.cos(limbSwing * speed) * intensity * limbSwingAmount;
        this.rightLegFront.rotateAngleZ = 0.4F + (float)Math.sin(limbSwing * speed) * intensity * limbSwingAmount;

        this.leftLegFront.rotateAngleY = (float)Math.cos(limbSwing * speed + (float)Math.PI) * intensity * limbSwingAmount;
        this.leftLegFront.rotateAngleZ = -0.4F - (float)Math.sin(limbSwing * speed + (float)Math.PI) * intensity * limbSwingAmount;

        this.RightLegBack.rotateAngleY = (float)Math.cos(limbSwing * speed + (float)Math.PI/2) * intensity * limbSwingAmount;
        this.RightLegBack.rotateAngleZ = 0.4F + (float)Math.sin(limbSwing * speed + (float)Math.PI/2) * intensity * limbSwingAmount;

        this.leftLegBack.rotateAngleY = (float)Math.cos(limbSwing * speed + 1.5F * (float)Math.PI) * intensity * limbSwingAmount;
        this.leftLegBack.rotateAngleZ = -0.4F - (float)Math.sin(limbSwing * speed + 1.5F * (float)Math.PI) * intensity * limbSwingAmount;

        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.head.rotateAngleX = headPitch * 0.017453292F;

        float breathe = (float)Math.sin(ageInTicks * 0.15F) * 0.03F;
        this.bodyBack.rotateAngleX = breathe;

        this.all.rotationPointY = 18.0F + (float)Math.cos(limbSwing * speed * 2.0F) * 0.5F * limbSwingAmount;
    }
}