package fluke.stygian.entity;

import fluke.stygian.entity.projectiles.EntityBlastlingBall;
import fluke.stygian.entity.projectiles.EntityThrowdableBone;
import fluke.stygian.util.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class EntitySupaika extends EntityMob {

    public static final ResourceLocation LOOT = new ResourceLocation("stygian", "entities/supaika");

    private int attackTimer;
    private int blastshottimer;
    private int slamtimer;
    private int groundSlamTimer;

    public EntitySupaika(World worldIn) {
        super(worldIn);
        this.setSize(1.2F, 3.2F);
        this.experienceValue = 50;
    }

    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEAD;
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 0.8D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 12.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));

        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true, false));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(120.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.5D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (this.attackTimer > 0) --this.attackTimer;
        if (this.blastshottimer > 0) --this.blastshottimer;
        if (this.slamtimer > 0) --this.slamtimer;
        if (this.groundSlamTimer > 0) --this.groundSlamTimer;

        if (this.world.isRemote) {
            for (int i = 0; i < 2; ++i) {
                this.world.spawnParticle(EnumParticleTypes.DRAGON_BREATH, this.posX + (this.rand.nextDouble() - 0.5D) * this.width,
                        this.posY + this.rand.nextDouble() * this.height,
                        this.posZ + (this.rand.nextDouble() - 0.5D) * this.width,
                        (this.rand.nextDouble() - 0.5D) * 0.1D,
                        this.rand.nextDouble() * 0.15D,
                        (this.rand.nextDouble() - 0.5D) * 0.1D
                );
            }
        }

        if (!this.world.isRemote) {
            EntityLivingBase target = this.getAttackTarget();
            if (target != null) {
                double distSq = this.getDistanceSq(target);
                float healthPercent = this.getHealth() / this.getMaxHealth();

                if (healthPercent < 0.4F) {
                    if (this.slamtimer <= 0 && distSq < 25.0D && this.onGround) {
                        this.startSlamJump(target);
                        this.slamtimer = 150;
                        this.world.setEntityState(this, (byte) 83);
                    }

                    if (this.slamtimer == 130) {
                        this.dealSlamDamage();
                    }
                }

                if (distSq > 25.0D && distSq < 144.0D && this.slamtimer <= 0) {
                    if (this.blastshottimer <= 0) {

                        if (healthPercent < 0.5F) {
                            this.launchBoneShot(target);
                        } else {
                            this.launchBlastShot(target);
                        }

                        this.blastshottimer = 110;
                        this.world.setEntityState(this, (byte) 82);
                    }
                }
            }
        }
        if (this.world.isRemote && this.slamtimer > 130) {
            this.world.spawnParticle(EnumParticleTypes.CRIT,
                    this.posX + (this.rand.nextDouble() - 0.5D) * this.width,
                    this.posY + this.rand.nextDouble() * this.height,
                    this.posZ + (this.rand.nextDouble() - 0.5D) * this.width,
                    0.0D, -0.2D, 0.0D);
        }
    }

    private void launchBoneShot(EntityLivingBase target) {
        this.playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0F, 1.2F);

        for (int i = 0; i < 7; ++i) {
            EntityThrowdableBone bone = new EntityThrowdableBone(this.world, this);

            double posY = this.posY + (double)this.getEyeHeight() - 0.2D;
            bone.setPosition(this.posX, posY, this.posZ);

            double d0 = target.posX - this.posX;
            double d1 = target.getEntityBoundingBox().minY + (double)(target.height / 2.0F) - posY;
            double d2 = target.posZ - this.posZ;

            bone.shoot(d0, d1, d2, 0.8F, 20.0F);
            this.world.spawnEntity(bone);
        }
    }

    private void launchBlastShot(EntityLivingBase target) {
        this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0F, 0.6F);

        for (int i = 0; i < 5; ++i) {
            EntityBlastlingBall ball = new EntityBlastlingBall(this.world, this);

            double posY = this.posY + (double)this.getEyeHeight() - 0.2D;
            ball.setPosition(this.posX, posY, this.posZ);

            double d0 = target.posX - this.posX;
            double d1 = target.getEntityBoundingBox().minY + (double)(target.height / 2.0F) - posY;
            double d2 = target.posZ - this.posZ;

            ball.shoot(d0, d1, d2, 0.8F, 15.0F);
            this.world.spawnEntity(ball);
        }
    }

    private void startSlamJump(EntityLivingBase target) {
        this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.2F, 1.2F);

        double d0 = target.posX - this.posX;
        double d2 = target.posZ - this.posZ;
        float f = MathHelper.sqrt(d0 * d0 + d2 * d2);

        this.motionX = (d0 / f) * 1.2D;
        this.motionZ = (d2 / f) * 1.2D;
        this.motionY = 0.6D;
        this.isAirBorne = true;
    }

    private void dealSlamDamage() {
        this.playSound(net.minecraft.init.SoundEvents.ENTITY_GENERIC_EXPLODE, 0.7F, 1.2F);

        BlockPos posBelow = new BlockPos(this.posX, this.posY - 0.1D, this.posZ);
        net.minecraft.block.state.IBlockState state = this.world.getBlockState(posBelow);

        for (EntityLivingBase entity : this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(4.0D, 2.0D, 4.0D))) {
            if (entity != this) {
                entity.attackEntityFrom(DamageSource.causeMobDamage(this), 12.0F);

                if (entity instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) entity;
                    if (player.isActiveItemStackBlocking()) {
                        player.disableShield(true);
                    }
                }

                double d0 = entity.posX - this.posX;
                double d1 = entity.posZ - this.posZ;
                double distance = MathHelper.sqrt(d0 * d0 + d1 * d1);
                if (distance != 0) {
                    entity.addVelocity((d0 / distance) * 0.2D, 0.6D, (d1 / distance) * 1.5D);
                }
            }
        }

        this.world.setEntityState(this, (byte) 84);
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        if (this.slamtimer > 145) {
            return false;
        }

        this.attackTimer = 10;
        this.world.setEntityState(this, (byte) 4);
        boolean flag = super.attackEntityAsMob(entityIn);

        if (flag) {
            this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_WEAK, 1.0F, 0.8F);
        }
        return flag;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void handleStatusUpdate(byte id) {
        if (id == 4) {
            this.attackTimer = 10;
        } else if (id == 82) {
            this.blastshottimer = 20;
        } else if (id == 83) {
            this.slamtimer = 30;
        }else if (id == 84) {
            this.groundSlamTimer = 10;
            this.spawnGroundSlamParticles();
        } else {
            super.handleStatusUpdate(id);
        }
    }

    @SideOnly(Side.CLIENT)
    private void spawnGroundSlamParticles() {
        BlockPos posBelow = new BlockPos(this.posX, this.posY - 0.1D, this.posZ);
        net.minecraft.block.state.IBlockState state = this.world.getBlockState(posBelow);
        int blockId = Block.getStateId(state);

        int particleCount = 120;

        for (int i = 0; i < particleCount; i++) {
            double angle = this.rand.nextDouble() * 6.0 * Math.PI;

            double speed = 0.5D + this.rand.nextDouble() * 0.7D;

            double vx = Math.cos(angle) * speed;
            double vz = Math.sin(angle) * speed;

            this.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX, this.posY + 0.1D, this.posZ, vx, 0.25D, vz, blockId);

            if (i % 4 == 0) {
                this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL,
                        this.posX, this.posY + 0.1D, this.posZ,
                        vx * 0.5D, 0.1D, vz * 0.5D);
            }
        }
    }
    @Override
    public boolean getCanSpawnHere() {
        if (this.rand.nextInt(3) != 0) {
            return false;
        }

        return super.getCanSpawnHere() && this.world.canSeeSky(new BlockPos(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ));
    }

    @SideOnly(Side.CLIENT)
    public int getAttackTimer() { return this.attackTimer; }

    @SideOnly(Side.CLIENT)
    public int getBlastShotTimer() { return this.blastshottimer; }

    @SideOnly(Side.CLIENT)
    public int getSlamTimer() { return this.slamtimer; }

    @SideOnly(Side.CLIENT)
    public int getGroundSlamTimer() { return this.groundSlamTimer; }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source.isProjectile()) {
            amount *= 0.5F;
        }
        return super.attackEntityFrom(source, amount);
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable() { return LOOT; }

    @Override
    protected SoundEvent getAmbientSound() { return ModSounds.supaika_idle; }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) { return ModSounds.supaika_hurt; }

    @Override
    protected SoundEvent getDeathSound() { return ModSounds.supaika_death; }

    @Override
    protected void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound(SoundEvents.ENTITY_WITHER_SKELETON_STEP, 0.5F, 0.6F);
    }
}