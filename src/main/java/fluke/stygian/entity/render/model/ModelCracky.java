package fluke.stygian.entity.render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelCracky extends ModelBase {
    ModelRenderer all;
    ModelRenderer head;
    ModelRenderer head_r1;
    ModelRenderer head_r2;
    ModelRenderer mouth;
    ModelRenderer body;
    ModelRenderer leg1;
    ModelRenderer leg2;
    ModelRenderer leg3;
    ModelRenderer leg4;
    ModelRenderer tail;

    public ModelCracky() {
        this.textureWidth = 64;
        this.textureHeight = 64;

        this.all = new ModelRenderer(this);
        this.all.setRotationPoint(-1.0F, 13.5F, -7.0F);

        this.head = new ModelRenderer(this);
        this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.all.addChild(head);
        this.head.cubeList.add(new ModelBox(head, 0, 0, -2.0F, -3.0F, -2.0F, 6, 5, 4, 0.0F, false));
        this.head.cubeList.add(new ModelBox(head, 0, 28, -0.5F, -1.02F, -5.0F, 3, 2, 4, 0.1F, false));

        this.head_r1 = new ModelRenderer(this);
        this.head_r1.setRotationPoint(0.0F, -3.0F, 0.0F);
        this.head.addChild(head_r1);
        this.setRotationAngle(head_r1, -0.4363F, 0.0F, -0.5672F);
        this.head_r1.cubeList.add(new ModelBox(head_r1, 0, 9, -2.0F, -3.0F, -1.0F, 2, 4, 2, 0.0F, false));

        this.head_r2 = new ModelRenderer(this);
        this.head_r2.setRotationPoint(2.0F, -3.0F, 0.0F);
        this.head.addChild(head_r2);
        this.setRotationAngle(head_r2, -0.4363F, 0.0F, 0.5672F);
        this.head_r2.cubeList.add(new ModelBox(head_r2, 0, 9, 0.0F, -3.0F, -1.0F, 2, 4, 2, 0.0F, true));

        this.mouth = new ModelRenderer(this);
        this.mouth.setRotationPoint(1.0F, 1.0F, -1.0F);
        this.head.addChild(mouth);
        this.mouth.cubeList.add(new ModelBox(mouth, 0, 35, -1.5F, -1.0F, -3.8F, 3, 2, 4, 0.0F, false));

        this.body = new ModelRenderer(this);
        this.body.setRotationPoint(1.0F, -3.5F, 4.0F);
        this.all.addChild(body);
        this.setRotationAngle(body, 1.5708F, 0.0F, 0.0F);
        this.body.cubeList.add(new ModelBox(body, 18, 15, -3.0F, 3.0F, -7.0F, 6, 8, 6, 0.0F, false));
        this.body.cubeList.add(new ModelBox(body, 21, 0, -4.0F, -3.0F, -7.0F, 8, 6, 7, 0.0F, false));

        this.leg1 = new ModelRenderer(this);
        this.leg1.setRotationPoint(-1.5F, 2.5F, 13.0F);
        this.all.addChild(leg1);
        this.leg1.cubeList.add(new ModelBox(leg1, 0, 18, 0.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F, false));

        this.leg2 = new ModelRenderer(this);
        this.leg2.setRotationPoint(1.5F, 2.5F, 13.0F);
        this.all.addChild(leg2);
        this.leg2.cubeList.add(new ModelBox(leg2, 0, 18, 0.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F, true));

        this.leg3 = new ModelRenderer(this);
        this.leg3.setRotationPoint(-1.5F, 2.5F, 3.0F);
        this.all.addChild(leg3);
        this.leg3.cubeList.add(new ModelBox(leg3, 0, 18, 0.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F, false));

        this.leg4 = new ModelRenderer(this);
        this.leg4.setRotationPoint(1.5F, 2.5F, 3.0F);
        this.all.addChild(leg4);
        this.leg4.cubeList.add(new ModelBox(leg4, 0, 18, 0.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F, true));

        this.tail = new ModelRenderer(this);
        this.tail.setRotationPoint(1.0F, -2.5F, 15.0F);
        this.all.addChild(tail);
        this.setRotationAngle(tail, 1.1781F, 0.0F, 0.0F);
        this.tail.cubeList.add(new ModelBox(tail, 13, 19, 0.0F, 0.0F, -2.0F, 0, 8, 2, 0.0F, false));
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

        this.leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        this.leg3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        this.leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;

        float idle = MathHelper.cos(ageInTicks * 0.08F);
        this.tail.rotateAngleZ = idle * 0.1F;
    }
}