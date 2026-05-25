package fluke.stygian.entity.render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelEndWyrm extends ModelBase {
    ModelRenderer all;
    ModelRenderer body;
    ModelRenderer leftWing;
    ModelRenderer leftWingEnd;
    ModelRenderer rightWing;
    ModelRenderer rightWingEnd;
    ModelRenderer tail;
    ModelRenderer tail2;
    ModelRenderer neck_main;
    ModelRenderer neck;
    ModelRenderer head_main;
    ModelRenderer head;
    ModelRenderer head_r1;
    ModelRenderer head_r2;
    ModelRenderer mouth;

    public ModelEndWyrm() {
        textureWidth = 64;
        textureHeight = 64;

        this.all = new ModelRenderer(this);
        this.all.setRotationPoint(-1.0F, 13.0F, -3.0F);


        this.body = new ModelRenderer(this);
        this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.all.addChild(body);
        this.setRotationAngle(body, -0.7854F, 0.0F, 0.0F);
        this.body.cubeList.add(new ModelBox(body, 0, 9, -1.5F, -1.0F, 0.0F, 5, 3, 9, 0.0F, false));
        this.body.cubeList.add(new ModelBox(body, 0, 29, 1.0F, -3.0F, 0.0F, 0, 2, 9, 0.0F, false));

        this.leftWing = new ModelRenderer(this);
        this.leftWing.setRotationPoint(3.5F, 0.5F, 0.0F);
        this.body.addChild(leftWing);
        this.leftWing.cubeList.add(new ModelBox(leftWing, 27, 12, 0.0F, -0.5F, 0.0F, 6, 1, 9, 0.0F, false));

        this.leftWingEnd = new ModelRenderer(this);
        this.leftWingEnd.setRotationPoint(6.0F, 0.0F, 0.0F);
        this.leftWing.addChild(leftWingEnd);
        this.leftWingEnd.cubeList.add(new ModelBox(leftWingEnd, 20, 24, 0.0F, -0.5F, 0.0F, 13, 1, 9, 0.0F, false));

        this.rightWing = new ModelRenderer(this);
        this.rightWing.setRotationPoint(-1.5F, 1.0F, 0.0F);
        this.body.addChild(rightWing);
        this.rightWing.cubeList.add(new ModelBox(rightWing, 27, 12, -6.0F, -1.0F, 0.0F, 6, 1, 9, 0.0F, true));

        this.rightWingEnd = new ModelRenderer(this);
        this.rightWingEnd.setRotationPoint(-6.0F, -0.5F, 0.0F);
        this.rightWing.addChild(rightWingEnd);
        this.rightWingEnd.cubeList.add(new ModelBox(rightWingEnd, 20, 24, -13.0F, -0.5F, 0.0F, 13, 1, 9, 0.0F, true));

        this.tail = new ModelRenderer(this);
        this.tail.setRotationPoint(1.0F, 0.0F, 9.0F);
        this.body.addChild(tail);
        this.setRotationAngle(tail, 0.3491F, 0.0F, 0.0F);
        this.tail.cubeList.add(new ModelBox(tail, 0, 21, -1.5F, -1.0F, -1.0F, 3, 2, 7, 0.0F, false));

        this.tail2 = new ModelRenderer(this);
        this.tail2.setRotationPoint(0.0F, 0.5F, 6.0F);
        this.tail.addChild(tail2);
        this.setRotationAngle(tail2, 0.3491F, 0.0F, 0.0F);
        this.tail2.cubeList.add(new ModelBox(tail2, 3, 30, -0.5F, -1.0F, 0.0F, 1, 1, 6, 0.0F, false));

        this.neck_main = new ModelRenderer(this);
        this.neck_main.setRotationPoint(1.0F, 0.5F, 0.0F);
        this.body.addChild(neck_main);


        this.neck = new ModelRenderer(this);
        this.neck.setRotationPoint(0.0F, -1.5F, 0.0F);
        this.neck_main.addChild(neck);
        this.setRotationAngle(neck, 0.3927F, 0.0F, 0.0F);
        this.neck.cubeList.add(new ModelBox(neck, 48, 0, -1.5F, 0.0F, -4.0F, 3, 3, 4, 0.0F, false));

        this.head_main = new ModelRenderer(this);
        this.head_main.setRotationPoint(0.0F, 1.25F, -3.0F);
        this.neck.addChild(head_main);


        this.head = new ModelRenderer(this);
        this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.head_main.addChild(head);
        this.setRotationAngle(head, 0.5672F, 0.0F, 0.0F);
        this.head.cubeList.add(new ModelBox(head, 0, 0, -3.0F, -1.75F, -5.0F, 6, 4, 5, 0.0F, false));
        this.head.cubeList.add(new ModelBox(head, 22, 0, -2.0F, -1.75F, -9.0F, 4, 3, 4, 0.0F, false));
        this.head.cubeList.add(new ModelBox(head, 31, 8, -2.0F, 1.25F, -9.0F, 4, 1, 0, 0.0F, false));
        this.head.cubeList.add(new ModelBox(head, 22, 3, -2.01F, 1.25F, -9.0F, 0, 1, 4, 0.0F, false));
        this.head.cubeList.add(new ModelBox(head, 30, 3, 2.01F, 1.25F, -9.0F, 0, 1, 4, 0.0F, false));

        this.head_r1 = new ModelRenderer(this);
        this.head_r1.setRotationPoint(3.0F, -1.0F, -1.0F);
        this.head.addChild(head_r1);
        this.setRotationAngle(head_r1, 0.1309F, 0.2182F, 0.0F);
        this.head_r1.cubeList.add(new ModelBox(head_r1, 20, 8, -1.0F, -1.0F, -1.0F, 2, 2, 5, 0.0F, false));

        this.head_r2 = new ModelRenderer(this);
        this.head_r2.setRotationPoint(-3.0F, -1.0F, -1.0F);
        this.head.addChild(head_r2);
        this.setRotationAngle(head_r2, 0.1309F, -0.2182F, 0.0F);
        this.head_r2.cubeList.add(new ModelBox(head_r2, 20, 8, -1.0F, -1.0F, -1.0F, 2, 2, 5, 0.0F, false));

        this.mouth = new ModelRenderer(this);
        this.mouth.setRotationPoint(-1.0F, 1.0F, -5.0F);
        this.head.addChild(mouth);
        this.mouth.cubeList.add(new ModelBox(mouth, 34, 0, -1.0F, 0.25F, -3.0F, 4, 1, 3, 0.0F, false));
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
        float wingRotation = MathHelper.cos(ageInTicks * 0.15F) * 0.4F;
        this.leftWing.rotateAngleZ = wingRotation;
        this.leftWingEnd.rotateAngleZ = wingRotation * 0.5F;

        this.rightWing.rotateAngleZ = -wingRotation;
        this.rightWingEnd.rotateAngleZ = -wingRotation * 0.5F;

        float tailSwing = MathHelper.cos(limbSwing * 0.5F) * 0.2F * limbSwingAmount;
        this.tail.rotateAngleY = tailSwing;
        this.tail2.rotateAngleY = MathHelper.cos(limbSwing * 0.5F - 1.0F) * 0.3F * limbSwingAmount;

        this.head_main.rotateAngleY = netHeadYaw * 0.017453292F;
        this.head_main.rotateAngleX = headPitch * 0.017453292F;

        this.body.rotateAngleX = -0.7854F + MathHelper.cos(ageInTicks * 0.05F) * 0.05F;
    }
}