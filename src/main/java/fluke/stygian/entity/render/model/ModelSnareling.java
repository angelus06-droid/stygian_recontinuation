package fluke.stygian.entity.render.model;

import fluke.stygian.entity.EntitySnareling;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

public class ModelSnareling extends ModelBase {
    private final ModelRenderer body;
    private final ModelRenderer head;
    private final ModelRenderer leftArm;
    private final ModelRenderer leftWindmill;
    private final ModelRenderer rightArm;
    private final ModelRenderer rightWindmill;
    private final ModelRenderer rightLeg;
    private final ModelRenderer leftLeg;

    public ModelSnareling() {
        this.textureWidth = 128;
        this.textureHeight = 64;

        this.body = new ModelRenderer(this);
        this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body.cubeList.add(new ModelBox(body, 31, 0, -5.5F, -13.0F, -3.5F, 11, 13, 6, 0.0F, false));
        this.body.cubeList.add(new ModelBox(body, 68, 0, -4.0F, -11.0F, -2.5F, 8, 8, 8, 0.75F, false));
        this.body.cubeList.add(new ModelBox(body, 68, 16, -4.0F, -11.0F, -2.25F, 8, 8, 8, 1.0F, false));

        this.head = new ModelRenderer(this);
        this.head.setRotationPoint(0.0F, -13.0F, 0.0F);
        this.body.addChild(head);
        this.head.cubeList.add(new ModelBox(head, 0, 0, -4.5F, -8.0F, -2.5F, 9, 8, 5, 0.0F, false));

        this.leftArm = new ModelRenderer(this);
        this.leftArm.setRotationPoint(6.0F, -11.0F, 0.0F);
        this.body.addChild(leftArm);
        this.leftArm.cubeList.add(new ModelBox(leftArm, 44, 20, -0.5F, -1.0F, -1.0F, 2, 36, 2, 0.0F, true));

        this.leftWindmill = new ModelRenderer(this);
        this.leftWindmill.setRotationPoint(0.5F, 34.0F, 0.0F);
        this.leftArm.addChild(leftWindmill);
        this.leftWindmill.cubeList.add(new ModelBox(leftWindmill, 52, 42, -1.0F, -1.0F, 1.0F, 2, 2, 14, 0.0F, true));

        this.rightArm = new ModelRenderer(this);
        this.rightArm.setRotationPoint(-6.0F, -11.0F, 0.0F);
        this.body.addChild(rightArm);
        this.rightArm.cubeList.add(new ModelBox(rightArm, 44, 20, -1.5F, -1.0F, -1.0F, 2, 36, 2, 0.0F, false));

        this.rightWindmill = new ModelRenderer(this);
        this.rightWindmill.setRotationPoint(-0.5F, 34.0F, 0.0F);
        this.rightArm.addChild(rightWindmill);
        this.rightWindmill.cubeList.add(new ModelBox(rightWindmill, 52, 42, -1.0F, -1.0F, 1.0F, 2, 2, 14, 0.0F, false));

        this.rightLeg = new ModelRenderer(this);
        this.rightLeg.setRotationPoint(-2.0F, 0.0F, 0.0F);
        this.rightLeg.cubeList.add(new ModelBox(rightLeg, 31, 19, -1.5F, 0.0F, -1.0F, 2, 24, 2, 0.0F, false));

        this.leftLeg = new ModelRenderer(this);
        this.leftLeg.setRotationPoint(2.0F, 0.0F, 0.0F);
        this.leftLeg.cubeList.add(new ModelBox(leftLeg, 31, 19, -0.5F, 0.0F, -1.0F, 2, 24, 2, 0.0F, true));
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        body.render(f5);
        rightLeg.render(f5);
        leftLeg.render(f5);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        // Rotación base de cabeza y piernas
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.head.rotateAngleX = headPitch * 0.017453292F;

        this.rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 0.7F * limbSwingAmount;
        this.leftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 0.7F * limbSwingAmount;

        // Inclinación ambiental leve
        this.body.rotateAngleX = (limbSwingAmount * -0.2F) + (MathHelper.cos(ageInTicks * 0.06F) * 0.02F);
    }

    @Override
    public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
        EntitySnareling snareling = (EntitySnareling) entitylivingbaseIn;

        float spin = (entitylivingbaseIn.ticksExisted + partialTickTime) * 0.5F;

        if (snareling.isSpinning()) {
            this.body.rotateAngleX = 0.6F;

            this.rightArm.rotateAngleX = spin;
            this.leftArm.rotateAngleX = spin + (float)Math.PI;

        } else {
            this.rightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 0.6F * limbSwingAmount * 0.5F;
            this.leftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 0.6F * limbSwingAmount * 0.5F;

            this.rightArm.rotateAngleZ = 0.05F;
            this.leftArm.rotateAngleZ = -0.05F;

            this.body.rotateAngleX = (limbSwingAmount * -0.2F) + (MathHelper.cos(spin * 0.1F) * 0.02F);
        }

        this.rightWindmill.rotateAngleX = 0;
        this.leftWindmill.rotateAngleX = 0;
    }
}