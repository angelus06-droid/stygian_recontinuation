package fluke.stygian.entity.render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelSlush extends ModelBase {
    ModelRenderer all;
    ModelRenderer body;
    ModelRenderer shell;
    ModelRenderer eyeVine;
    ModelRenderer eye;
    ModelRenderer tail;

    public ModelSlush() {
        textureWidth = 64;
        textureHeight = 32;

        all = new ModelRenderer(this);
        all.setRotationPoint(0.0F, 24.0F, 0.0F);

        body = new ModelRenderer(this);
        body.setRotationPoint(0.0F, 0.0F, 0.0F);
        all.addChild(body);
        body.cubeList.add(new ModelBox(body, 30, 0, -2.5F, -2.0F, -5.0F, 5, 2, 10, 0.0F, false));

        shell = new ModelRenderer(this);
        shell.setRotationPoint(0.0F, -2.0F, 0.0F);
        body.addChild(shell);
        shell.cubeList.add(new ModelBox(shell, 0, 0, -2.5F, -10.0F, -4.0F, 5, 10, 10, 0.0F, false));

        eyeVine = new ModelRenderer(this);
        eyeVine.setRotationPoint(0.0F, -2.0F, -4.0F);
        body.addChild(eyeVine);
        setRotationAngle(eyeVine, 0.3054F, 0.0F, 0.0F);
        eyeVine.cubeList.add(new ModelBox(eyeVine, 0, 0, -0.5F, -8.0F, -1.0F, 1, 8, 1, 0.0F, false));

        eye = new ModelRenderer(this);
        eye.setRotationPoint(0.0F, -7.0F, -0.5F);
        eyeVine.addChild(eye);
        setRotationAngle(eye, -0.2618F, 0.0F, 0.0F);
        eye.cubeList.add(new ModelBox(eye, 0, 20, -1.5F, -3.0F, -1.5F, 3, 3, 3, 0.0F, false));
        eye.cubeList.add(new ModelBox(eye, 4, 0, -0.5F, -2.0F, -1.6F, 1, 1, 1, 0.0F, false));

        tail = new ModelRenderer(this);
        tail.setRotationPoint(0.0F, -2.0F, 5.0F);
        body.addChild(tail);
        tail.cubeList.add(new ModelBox(tail, 20, 0, -1.5F, -1.0F, 0.0F, 3, 3, 3, 0.0F, false));
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        all.render(f5);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        float speed = 0.8F;
        float degree = 0.5F;
        this.body.offsetY = MathHelper.cos(limbSwing * speed) * 0.05F * limbSwingAmount;

        this.shell.rotateAngleX = MathHelper.cos(limbSwing * speed) * 0.1F * limbSwingAmount;

        this.tail.rotateAngleY = MathHelper.cos(limbSwing * speed * 0.5F) * 0.2F * limbSwingAmount;

        this.eyeVine.rotateAngleX = 0.3054F + (MathHelper.sin(ageInTicks * 0.05F) * 0.1F);
        this.eyeVine.rotateAngleZ = MathHelper.cos(ageInTicks * 0.05F) * 0.05F;
        this.eye.rotateAngleX = -0.2618F + (MathHelper.cos(ageInTicks * 0.05F) * 0.05F);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}