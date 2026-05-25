package fluke.stygian.entity.projectiles;

import fluke.stygian.entity.EntityBlastling;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityThrowdableBone extends EntityThrowable {

    private float projectileDamage = 8.0F;
    private float extraDamage = 0.0F;
    private int fireLevel = 0;
    private int knockbackLevel = 0;

    private int bounces = 0;
    private static final int MAX_BOUNCES = 4;

    public EntityThrowdableBone(World worldIn) {
        super(worldIn);
        this.setSize(0.45F, 0.45F);
    }

    public EntityThrowdableBone(World worldIn, EntityLivingBase throwerIn) {
        super(worldIn, throwerIn);
        this.setSize(0.45F, 0.45F);
    }

    public EntityThrowdableBone(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
        this.setSize(0.45F, 0.45F);
    }

    public EntityThrowdableBone(World worldIn, EntityBlastling thrower, double d0, double d1, double d2) {
        this(worldIn, (EntityLivingBase) thrower);
        this.shoot(d0, d1, d2, 1.0F, 1.0F);
    }

    public void setEnchantmentDamage(float extra) {
        this.extraDamage = extra;
    }

    public void setFireLevel(int level) {
        this.fireLevel = level;
    }

    public void setKnockbackLevel(int level) {
        this.knockbackLevel = level;
    }

    @Override
    protected float getGravityVelocity() {
        return 0.03F;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (this.world.isRemote) {
            for (int j = 0; j < 2; ++j) {
                this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL,
                        this.posX + this.motionX * j / 4.0D,
                        this.posY + this.motionY * j / 4.0D,
                        this.posZ + this.motionZ * j / 4.0D,
                        -this.motionX, -this.motionY + 0.2D, -this.motionZ);
            }
        }

        if (this.ticksExisted > 200) {
            this.setDead();
        }
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (!this.world.isRemote) {

            if (result.entityHit != null) {
                if (result.entityHit != this.getThrower()) {
                    float totalDamage = this.projectileDamage + this.extraDamage;

                    boolean flag = result.entityHit.attackEntityFrom(
                            DamageSource.causeThrownDamage(this, this.getThrower()),
                            totalDamage
                    );

                    if (flag) {
                        if (this.fireLevel > 0) {
                            result.entityHit.setFire(this.fireLevel * 4);
                        }

                        if (this.knockbackLevel > 0 && result.entityHit instanceof EntityLivingBase) {
                            EntityLivingBase target = (EntityLivingBase) result.entityHit;

                            double dx = this.motionX;
                            double dz = this.motionZ;

                            double magnitude = MathHelper.sqrt(dx * dx + dz * dz);

                            if (magnitude > 0.0001D) {
                                double strength = this.knockbackLevel * 0.6D;

                                target.motionX += dx / magnitude * strength;
                                target.motionZ += dz / magnitude * strength;

                                target.motionY += 0.1D;

                                target.velocityChanged = true;
                            }
                        }
                    }

                    this.playBreakSound();
                    this.setDead();
                }
                return;
            }

            if (result.typeOfHit == RayTraceResult.Type.BLOCK) {

                if (this.world.getBlockState(result.getBlockPos())
                        .getCollisionBoundingBox(this.world, result.getBlockPos()) == null) {
                    return;
                }

                if (this.bounces >= MAX_BOUNCES) {
                    this.createWitherCloud();
                    this.playBreakSound();
                    this.setDead();
                    return;
                }

                EnumFacing sideHit = result.sideHit;

                double nx = sideHit.getXOffset();
                double ny = sideHit.getYOffset();
                double nz = sideHit.getZOffset();

                double dot = this.motionX * nx + this.motionY * ny + this.motionZ * nz;

                this.motionX = this.motionX - 2 * dot * nx;
                this.motionY = this.motionY - 2 * dot * ny;
                this.motionZ = this.motionZ - 2 * dot * nz;

                double bounceFactor = 0.6D;
                this.motionX *= bounceFactor;
                this.motionY *= bounceFactor;
                this.motionZ *= bounceFactor;

                if (sideHit.getAxis() == EnumFacing.Axis.Y) {
                    this.motionX *= 0.8D;
                    this.motionZ *= 0.8D;
                }

                this.posX += nx * 0.2D;
                this.posY += ny * 0.2D;
                this.posZ += nz * 0.2D;
                this.setPosition(this.posX, this.posY, this.posZ);

                this.rotationYaw += this.rand.nextFloat() * 60F - 30F;
                this.rotationPitch += this.rand.nextFloat() * 60F - 30F;

                this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_SKELETON_HURT, SoundCategory.NEUTRAL, 0.5F, 0.7F + this.rand.nextFloat() * 0.3F);

                this.world.setEntityState(this, (byte) 4);

                this.bounces++;
            }
        }
    }

    private void createWitherCloud() {
        EntityAreaEffectCloud cloud = new EntityAreaEffectCloud(this.world, this.posX, this.posY, this.posZ);
        cloud.setRadius(1.2F);
        cloud.setRadiusOnUse(-0.5F);
        cloud.setWaitTime(2);
        cloud.setDuration(40);
        cloud.setRadiusPerTick(-cloud.getRadius() / (float) cloud.getDuration());
        cloud.addEffect(new PotionEffect(MobEffects.WITHER, 100, 1));
        cloud.setColor(0x333333);
        this.world.spawnEntity(cloud);
    }

    private void playBreakSound() {
        this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_SKELETON_DEATH, SoundCategory.NEUTRAL, 0.8F, 0.8F);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void handleStatusUpdate(byte id) {
        if (id == 4) {
            for (int i = 0; i < 10; ++i) {
                this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE,
                        this.posX,
                        this.posY,
                        this.posZ,
                        this.rand.nextGaussian() * 0.02D,
                        0.05D,
                        this.rand.nextGaussian() * 0.02D);
            }
        }
    }
}