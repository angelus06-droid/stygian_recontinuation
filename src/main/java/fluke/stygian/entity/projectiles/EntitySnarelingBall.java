package fluke.stygian.entity.projectiles;

import fluke.stygian.util.ModSounds;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntitySnarelingBall extends EntityThrowable {

    private float projectileDamage = 5.5F;

    public EntitySnarelingBall(World worldIn) {
        super(worldIn);
    }

    public EntitySnarelingBall(World worldIn, EntityLivingBase throwerIn) {
        super(worldIn, throwerIn);
        this.motionX *= 0.8D;
        this.motionY *= 0.8D;
        this.motionZ *= 0.8D;
    }

    public EntitySnarelingBall(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    @Override
    protected float getGravityVelocity() {
        return 0.01F;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.world.isRemote) {
            for (int j = 0; j < 2; ++j) {
                this.world.spawnParticle(EnumParticleTypes.SLIME,
                        this.posX + (this.rand.nextDouble() - 0.5D) * 0.2D,
                        this.posY + (this.rand.nextDouble() - 0.5D) * 0.2D,
                        this.posZ + (this.rand.nextDouble() - 0.5D) * 0.2D,
                        0.0D, 0.0D, 0.0D);
            }
        }

        if (this.ticksExisted > 200) {
            this.setDead();
        }
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (!this.world.isRemote) {
            if (result.entityHit != null && result.entityHit != this.getThrower()) {
                result.entityHit.attackEntityFrom(
                        DamageSource.causeThrownDamage(this, this.getThrower()),
                        this.projectileDamage
                );

                if (result.entityHit instanceof EntityLivingBase) {
                    ((EntityLivingBase)result.entityHit).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 200, 1));
                }
            }
            if (result.typeOfHit == RayTraceResult.Type.BLOCK) {
                net.minecraft.block.state.IBlockState state = this.world.getBlockState(result.getBlockPos());

                if (state.getCollisionBoundingBox(this.world, result.getBlockPos()) == null) {
                    return;
                }
            }

            this.createLingeringEffect();

            this.world.playSound(null, this.posX, this.posY, this.posZ, ModSounds.snareling_glob_land, this.getSoundCategory(), 1.0F, 1.0F  + (this.rand.nextFloat() * 0.2F));

            this.world.setEntityState(this, (byte) 3);
            this.setDead();
        }
    }

    private void createLingeringEffect() {
        EntityAreaEffectCloud cloud = new EntityAreaEffectCloud(this.world, this.posX, this.posY, this.posZ);
        if (this.getThrower() instanceof EntityLivingBase) {
            cloud.setOwner((EntityLivingBase) this.getThrower());
        }

        cloud.setRadius(2.5F);
        cloud.setRadiusOnUse(-0.5F);
        cloud.setWaitTime(10);
        cloud.setDuration(160);
        cloud.setRadiusPerTick(-cloud.getRadius() / (float)cloud.getDuration());

        cloud.setColor(0xdbe64e);
        cloud.setParticle(EnumParticleTypes.SPELL_MOB);

        cloud.addEffect(new PotionEffect(MobEffects.SLOWNESS, 200, 1));

        this.world.spawnEntity(cloud);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void handleStatusUpdate(byte id) {
        if (id == 3) {
            for (int i = 0; i < 12; ++i) {
                this.world.spawnParticle(EnumParticleTypes.SLIME,
                        this.posX, this.posY, this.posZ,
                        this.rand.nextGaussian() * 0.1D,
                        this.rand.nextGaussian() * 0.1D,
                        this.rand.nextGaussian() * 0.1D);
            }

            float r = 0.858F;
            float g = 0.901F;
            float b = 0.305F;

            for (int k = 0; k < 20; ++k) {
                this.world.spawnParticle(EnumParticleTypes.SPELL_MOB,
                        this.posX + (this.rand.nextDouble() - 0.5D) * 0.5D,
                        this.posY + (this.rand.nextDouble() - 0.5D) * 0.5D,
                        this.posZ + (this.rand.nextDouble() - 0.5D) * 0.5D,
                        r, g, b);
            }
        }
    }
}