package fluke.stygian.entity.render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelPix extends ModelBase {
    public ModelRenderer body;
    public ModelRenderer rightWing;
    public ModelRenderer leftWing;

    public ModelPix() {
        textureWidth = 64;
        textureHeight = 32;

        this.body = new ModelRenderer(this);
        this.body.setRotationPoint(0.0F, 22.0F, 0.0F);
        this.body.cubeList.add(new ModelBox(body, 0, 0, -2.0F, -2.0F, -2.0F, 4, 4, 4, 0.0F, false));
        this.body.cubeList.add(new ModelBox(body, 0, 8, -2.0F, -2.0F, -2.0F, 4, 4, 4, 0.2F, false));

        this.rightWing = new ModelRenderer(this);
        this.rightWing.setRotationPoint(-2.0F, 0.0F, 1.0F);
        this.body.addChild(rightWing);
        this.rightWing.cubeList.add(new ModelBox(rightWing, 0, 28, -7.0F, -3.0F, 0.0F, 7, 4, 0, 0.0F, false));

        this.leftWing = new ModelRenderer(this);
        this.leftWing.setRotationPoint(2.0F, 0.0F, 1.0F);
        this.body.addChild(leftWing);
        this.leftWing.cubeList.add(new ModelBox(leftWing, 18, 28, 0.0F, -3.0F, 0.0F, 7, 4, 0, 0.0F, false));
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        body.render(f5);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        this.body.rotationPointY = 22.0F + MathHelper.cos(ageInTicks * 0.15F) * 0.5F;

        this.rightWing.rotateAngleY = MathHelper.cos(ageInTicks * 1.5F) * (float)Math.PI * 0.25F;
        this.leftWing.rotateAngleY = -this.rightWing.rotateAngleY;

        this.rightWing.rotateAngleZ = 0.2F;
        this.leftWing.rotateAngleZ = -0.2F;
    }
}