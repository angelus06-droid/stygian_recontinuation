package fluke.stygian.entity.projectiles;

import fluke.stygian.entity.EntityBlastling;
import fluke.stygian.util.ModSounds;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityBlastlingBall extends EntityThrowable {

    private float projectileDamage = 4.0F;

    public EntityBlastlingBall(World worldIn) {
        super(worldIn);
    }

    public EntityBlastlingBall(World worldIn, EntityLivingBase throwerIn) {
        super(worldIn, throwerIn);
    }

    public EntityBlastlingBall(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    public EntityBlastlingBall(World worldIn, EntityBlastling thrower, double d0, double d1, double d2) {
        this(worldIn, (EntityLivingBase) thrower);
        this.shoot(d0, d1, d2, 1.0F, 1.0F);
    }

    @Override
    protected float getGravityVelocity() {
        return 0.0F;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.world.isRemote) {
            for (int j = 0; j < 2; ++j) {
                double px = this.posX + this.motionX * j / 4.0D;
                double py = this.posY + this.motionY * j / 4.0D;
                double pz = this.posZ + this.motionZ * j / 4.0D;

                // Partícula original (Portal)
                this.world.spawnParticle(EnumParticleTypes.PORTAL, px, py, pz, -this.motionX, -this.motionY + 0.2D, -this.motionZ);

                // Nueva partícula: SPELL_WITCH (Rastro)
                this.world.spawnParticle(EnumParticleTypes.SPELL_WITCH, px, py, pz, 0, 0, 0);
            }
        }
        if (this.ticksExisted > 200) {
            this.setDead();
        }
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (!this.world.isRemote) {
            if (result.typeOfHit == RayTraceResult.Type.BLOCK) {
                net.minecraft.block.state.IBlockState state = this.world.getBlockState(result.getBlockPos());

                if (state.getCollisionBoundingBox(this.world, result.getBlockPos()) == null) {
                    return;
                }
            }

            if (result.entityHit != null) {
                if (result.entityHit == this.getThrower()) {
                    return;
                }

                result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), this.projectileDamage);
            }

            this.world.playSound(null, this.posX, this.posY, this.posZ, ModSounds.blastling_bullet_land, this.getSoundCategory(), 1.0F, 1.0F + (this.rand.nextFloat() * 0.2F));
            this.world.setEntityState(this, (byte) 3);
            this.setDead();
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void handleStatusUpdate(byte id) {
        if (id == 3) {
            for (int i = 0; i < 15; ++i) {
                double d0 = this.rand.nextGaussian() * 0.02D;
                double d1 = this.rand.nextGaussian() * 0.02D;
                double d2 = this.rand.nextGaussian() * 0.02D;

                // Partículas al impactar
                this.world.spawnParticle(EnumParticleTypes.PORTAL, this.posX, this.posY, this.posZ, d0, d1, d2);
                this.world.spawnParticle(EnumParticleTypes.SPELL_WITCH, this.posX, this.posY, this.posZ, d0, d1, d2);
            }
        }
    }

    public float getBrightness() {
        return 1.0f;
    }

    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender() {
        return 15728880;
    }
}