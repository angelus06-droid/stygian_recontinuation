package fluke.stygian.entity;

import fluke.stygian.entity.ai.EntityAIFlyingMoveHelper;
import fluke.stygian.entity.ai.EntityAIMobRandomFly;
import fluke.stygian.entity.ai.EntityAIMobTargetFly;
import fluke.stygian.util.ModSounds;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityEndWyrm extends EntityMob {
    public static final ResourceLocation LOOT = new ResourceLocation("stygian", "entities/end_wyrm");
    private int healTimer;
    private int attackTimer;

    public EntityEndWyrm(World worldIn) {
        super(worldIn);
        this.setSize(1.1f, 1.1f);
        this.isImmuneToFire = true;
        this.moveHelper = new EntityAIFlyingMoveHelper(this, true);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.2, false));
        this.tasks.addTask(3, new EntityAIMobTargetFly(this, 1.0F));
        this.tasks.addTask(5, new EntityAIMobRandomFly(this, 12.0F));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(8, new EntityAILookIdle(this));

        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
    }
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        this.setNoGravity(true);

        if (this.attackTimer > 0) --this.attackTimer;

        if (!this.world.isRemote) {
            if (this.isEntityAlive() && this.getHealth() < this.getMaxHealth()) {
                this.healTimer++;
                if (this.healTimer >= 80) {
                    this.heal(1.0F);
                    this.healTimer = 0;
                }
            } else {
                this.healTimer = 0;
            }
            this.motionY += Math.sin(this.ticksExisted * 0.1) * 0.005;
        }
        if (this.getAttackTarget() != null) {
            EntityLivingBase target = this.getAttackTarget();
            this.getLookHelper().setLookPositionWithEntity(target, 30.0F, 30.0F);
            this.rotationYawHead = this.rotationYaw;
        }
    }

    @Override
    public void travel(float strafe, float vertical, float forward) {
        if (this.isInWater() || this.isInLava()) {
            this.moveRelative(strafe, vertical, forward, 0.02F);
            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.8D;
            this.motionY *= 0.8D;
            this.motionZ *= 0.8D;
        } else {
            float friction = 0.91F;
            if (this.onGround) {
                friction = this.world.getBlockState(new BlockPos(this.posX, this.getEntityBoundingBox().minY - 1, this.posZ)).getBlock().slipperiness * 0.91F;
            }

            this.moveRelative(strafe, vertical, forward, 0.02F);
            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);

            float airFriction = 0.91F;
            this.motionX *= airFriction;
            this.motionY *= airFriction;
            this.motionZ *= airFriction;
        }

        this.prevLimbSwingAmount = this.limbSwingAmount;
        double dx = this.posX - this.prevPosX;
        double dz = this.posZ - this.prevPosZ;
        float distanceMoved = MathHelper.sqrt(dx * dx + dz * dz) * 4.0F;
        if (distanceMoved > 1.0F) distanceMoved = 1.0F;
        this.limbSwingAmount += (distanceMoved - this.limbSwingAmount) * 0.4F;
        this.limbSwing += this.limbSwingAmount;
    }

    @Override public void fall(float distance, float damageMultiplier) {
    }
    @Override protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {
    }
    @Override public boolean isOnLadder() {
        return false;
    }
    @Override protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    protected net.minecraft.pathfinding.PathNavigate createNavigator(World w) {
        return new PathNavigateFlying(this, w);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.endwyrm_idle;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.endwyrm_hurt;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.endwyrm_death;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(5.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(7.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(30.0D);
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return LOOT;
    }
}