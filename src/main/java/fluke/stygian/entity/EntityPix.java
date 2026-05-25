package fluke.stygian.entity;

import fluke.stygian.util.ModSounds;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class EntityPix extends EntityAmbientCreature {

    public static final ResourceLocation LOOT = new ResourceLocation("stygian", "entities/pix");
    private BlockPos targetPos;

    public EntityPix(World worldIn) {
        super(worldIn);
        this.setSize(0.5F, 0.5F);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(6.0D);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (this.isEntityAlive() && !this.isInvisible()) {
            for (int i = 0; i < 1; ++i) {
                double x = this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width;
                double y = this.posY + this.rand.nextDouble() * (double)this.height;
                double z = this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width;

                double vx = (this.rand.nextDouble() - 0.5D) * 0.01D;
                double vy = -0.01D;
                double vz = (this.rand.nextDouble() - 0.5D) * 0.01D;

                this.world.spawnParticle(EnumParticleTypes.END_ROD, x, y, z, vx, vy, vz);
            }
        }
    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();

        EntityPlayer closestPlayer = this.world.getClosestPlayerToEntity(this, 6.0D);

        if (closestPlayer != null) {
            double diffX = this.posX - closestPlayer.posX;
            double diffZ = this.posZ - closestPlayer.posZ;

            if (this.targetPos == null || this.rand.nextInt(10) == 0) {
                this.targetPos = new BlockPos(
                        this.posX + diffX + (double)(this.rand.nextFloat() * 16.0F - 8.0F),
                        this.posY + (double)(this.rand.nextInt(5) - 2),
                        this.posZ + diffZ + (double)(this.rand.nextFloat() * 16.0F - 8.0F)
                );
            }
        }
        else if (this.targetPos == null || this.getDistanceSqToCenter(this.targetPos) < 2.0D || this.rand.nextInt(30) == 0) {
            this.targetPos = new BlockPos(
                    (int)this.posX + this.rand.nextInt(12) - 6,
                    (int)this.posY + this.rand.nextInt(6) - 3,
                    (int)this.posZ + this.rand.nextInt(12) - 6
            );
        }

        if (this.targetPos != null) {
            if (!this.world.isAirBlock(this.targetPos) && this.targetPos.getY() < 250) {
                this.targetPos = this.targetPos.up();
            }

            double dX = (double)this.targetPos.getX() + 0.5D - this.posX;
            double dY = (double)this.targetPos.getY() + 0.1D - this.posY;
            double dZ = (double)this.targetPos.getZ() + 0.5D - this.posZ;

            this.motionX += (Math.signum(dX) * 0.4D - this.motionX) * 0.1D;
            this.motionY += (Math.signum(dY) * 0.6D - this.motionY) * 0.1D;
            this.motionZ += (Math.signum(dZ) * 0.4D - this.motionZ) * 0.1D;

            float f = (float)(MathHelper.atan2(this.motionZ, this.motionX) * (180D / Math.PI)) - 90.0F;
            float f1 = MathHelper.wrapDegrees(f - this.rotationYaw);
            this.moveForward = 0.5F;
            this.rotationYaw += f1;
        }
    }

    @Override
    public void travel(float strafe, float vertical, float forward) {
        if (this.isServerWorld()) {
            this.moveRelative(strafe, vertical, forward, 0.1F);
            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);

            this.motionX *= 0.85D;
            this.motionY *= 0.85D;
            this.motionZ *= 0.85D;
        } else {
            super.travel(strafe, vertical, forward);
        }
    }

    public float getBrightness() {
        return 1.0f;
    }

    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender() {
        return 15728880;
    }

    @Override public void fall(float distance, float damageMultiplier) {

    }
    @Override protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {

    }
    @Override protected boolean canTriggerWalking() {
        return false;
    }
    @Override @Nullable protected ResourceLocation getLootTable() {
        return LOOT;
    }
    @Override protected SoundEvent getAmbientSound() {
        return ModSounds.pix_idle;
    }
    @Override public float getEyeHeight() {
        return this.height / 2.0F;
    }
}