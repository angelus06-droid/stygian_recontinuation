package fluke.stygian.entity;

import fluke.stygian.entity.projectiles.EntitySnarelingBall;
import fluke.stygian.util.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class EntitySnareling extends EntityMob implements IRangedAttackMob {

    public static final ResourceLocation LOOT = new ResourceLocation("stygian", "entities/snareling");
    private static final DataParameter<Boolean> IS_SPINNING = EntityDataManager.createKey(EntitySnareling.class, DataSerializers.BOOLEAN);
    private int attackTimer;

    public EntitySnareling(World worldIn) {
        super(worldIn);
        this.setSize(0.8F, 2.85F);
        this.experienceValue = 7;
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.4D, false) {
            @Override
            public boolean shouldExecute() {
                EntityLivingBase target = EntitySnareling.this.getAttackTarget();
                return super.shouldExecute() && target != null && target.isPotionActive(MobEffects.SLOWNESS);
            }
        });
        this.tasks.addTask(2, new EntityAIAvoidEntity<>(this, EntityLivingBase.class,
                t -> t == EntitySnareling.this.getAttackTarget() && !t.isPotionActive(MobEffects.SLOWNESS),
                4.0F, 1.0D, 1.1D));
        this.tasks.addTask(4, new EntityAIAttackRanged(this, 1.0D, 100, 16.0F));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 0.8D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true, false));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(18.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.31D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(30.0D);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(IS_SPINNING, Boolean.valueOf(false));
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
        this.playSound(ModSounds.snareling_prepare_shoot, 1.0F, 0.5F);

        EntitySnarelingBall entityball = new EntitySnarelingBall(this.world, this);
        double d0 = target.posX - this.posX;
        double d1 = target.getEntityBoundingBox().minY + (double)(target.height / 2.0F) - entityball.posY;
        double d2 = target.posZ - this.posZ;
        float f = MathHelper.sqrt(d0 * d0 + d2 * d2);
        float vFactor = f * 0.18F;

        entityball.shoot(d0, d1 + (double)vFactor, d2, 0.8F, 1.0F);

        this.playSound(ModSounds.snareling_shoot, 1.0F, 1.0F);
        this.world.spawnEntity(entityball);
    }

    @Override
    public void setSwingingArms(boolean swingingArms) { }

    public boolean isSpinning() {
        return this.getDataManager().get(IS_SPINNING);
    }

    @Override
    public boolean isPotionApplicable(PotionEffect potioneffectIn) {
        if (potioneffectIn.getPotion() == MobEffects.SLOWNESS) {
            return false;
        }
        return super.isPotionApplicable(potioneffectIn);
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (this.world.isRemote) {
            for (int i = 0; i < 2; ++i) {
                this.world.spawnParticle(EnumParticleTypes.PORTAL,
                        this.posX + (this.rand.nextDouble() - 0.5D) * this.width,
                        this.posY + this.rand.nextDouble() * this.height - 0.25D,
                        this.posZ + (this.rand.nextDouble() - 0.5D) * this.width,
                        (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
            }
        }

        if (!this.world.isRemote) {
            EntityLivingBase target = this.getAttackTarget();
            boolean shouldSpin = target != null && target.isPotionActive(MobEffects.SLOWNESS);

            if (shouldSpin != this.getDataManager().get(IS_SPINNING)) {
                this.getDataManager().set(IS_SPINNING, shouldSpin);
            }
        }

        if (!this.world.isRemote && this.isWet()) {
            this.attackEntityFrom(DamageSource.DROWN, 1.0F);
            this.teleportRandomly();
        }
        if (this.attackTimer > 0) --this.attackTimer;
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        this.world.setEntityState(this, (byte) 4);
        boolean flag = super.attackEntityAsMob(entityIn);
        this.playSound(ModSounds.snareling_attack, 1.0F, 1.0F + (this.rand.nextFloat() * 0.2F));
        return flag;
    }

    protected boolean teleportRandomly() {
        double x = this.posX + (this.rand.nextDouble() - 0.5D) * 32.0D;
        double y = this.posY + (double)(this.rand.nextInt(32) - 16);
        double z = this.posZ + (this.rand.nextDouble() - 0.5D) * 32.0D;
        return this.teleportTo(x, y, z);
    }

    protected boolean teleportTo(double x, double y, double z) {
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos((int) Math.floor(x), (int) Math.floor(y), (int) Math.floor(z));

        while (pos.getY() > 0 && !this.world.getBlockState(pos.down()).isFullBlock()) {
            pos.move(EnumFacing.DOWN);
        }

        if (this.world.getBlockState(pos).getMaterial().blocksMovement()) {
            return false;
        }

        boolean success = this.attemptTeleport(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);

        if (success) {
            this.world.playSound(null, this.prevPosX, this.prevPosY, this.prevPosZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, this.getSoundCategory(), 1.0F, 1.0F
            );
            this.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F);
        }

        return success;
    }

    protected boolean teleportTowards(Entity target) {
        Vec3d vec = new Vec3d(this.posX - target.posX, this.getEntityBoundingBox().minY + (double)(this.height / 2.0F) - target.posY + target.getEyeHeight(), this.posZ - target.posZ
        );
        vec = vec.normalize();

        double x = this.posX + (this.rand.nextDouble() - 0.5D) * 8.0D - vec.x * 16.0D;
        double y = this.posY + (double)(this.rand.nextInt(16) - 8) - vec.y * 16.0D;
        double z = this.posZ + (this.rand.nextDouble() - 0.5D) * 8.0D - vec.z * 16.0D;

        return this.teleportTo(x, y, z);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isEntityInvulnerable(source)) return false;
        if (source instanceof net.minecraft.util.EntityDamageSourceIndirect) {
            for (int i = 0; i < 64; ++i) {
                if (this.teleportRandomly()) {
                    return false;
                }
            }
            return false;
        }

        if (source.getTrueSource() instanceof EntityLivingBase) {
            for (int i = 0; i < 16; ++i) {
                if (this.teleportTowards((Entity) source.getTrueSource())) {
                    return true;
                }
            }
        }

        if (this.rand.nextInt(10) == 0) this.teleportRandomly();
        return super.attackEntityFrom(source, amount);
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 1;
    }

    @Override
    public boolean getCanSpawnHere() {
        if (this.world.provider.getDimension() == 1) {

            boolean validdistance = (this.posX > 500.0D || this.posX < -500.0D || this.posZ > 500.0D || this.posZ < -500.0D);

            return super.getCanSpawnHere() && validdistance;
        }
        return super.getCanSpawnHere();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void handleStatusUpdate(byte id) {
        if (id == 4) {
        } else {
            super.handleStatusUpdate(id);
        }
    }

    @Override @Nullable protected ResourceLocation getLootTable() { return LOOT; }
    @Override protected SoundEvent getAmbientSound() { return ModSounds.snareling_idle; }
    @Override protected SoundEvent getHurtSound(DamageSource source) { return ModSounds.snareling_hurt; }
    @Override protected SoundEvent getDeathSound() { return ModSounds.snareling_death; }
    @Override protected void playStepSound(BlockPos pos, Block blockIn) { this.playSound(ModSounds.snareling_step, 0.15F, 1.0F); }
    @SideOnly(Side.CLIENT) public int getAttackTimer() { return this.attackTimer; }
}