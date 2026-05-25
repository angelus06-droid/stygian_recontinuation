package fluke.stygian.entity;

import fluke.stygian.util.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class EntityWatchling extends EntityMob {

    public static final ResourceLocation LOOT = new ResourceLocation("stygian", "entities/watchling");
    private static final DataParameter<Boolean> IS_CHILD = EntityDataManager.createKey(EntityWatchling.class, DataSerializers.BOOLEAN);
    private int attackTimer;

    public EntityWatchling(World worldIn) {
        super(worldIn);
        this.setSize(0.8F, 2.7F);
        this.experienceValue = 6;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(IS_CHILD, Boolean.valueOf(false));
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.15D, false));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 0.8D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true, false));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.31D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.5D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(30.0D);
    }

    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
        livingdata = super.onInitialSpawn(difficulty, livingdata);

        if (this.rand.nextFloat() < 0.10F) {
            this.setChild(true);
        }
        return livingdata;
    }

    @Override
    public boolean isChild() {
        return this.getDataManager().get(IS_CHILD);
    }

    public void setChild(boolean childEntity) {
        this.getDataManager().set(IS_CHILD, Boolean.valueOf(childEntity));
        if (this.world != null && !this.world.isRemote) {
            this.setScaleForAge(childEntity);
        }
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> key) {
        if (IS_CHILD.equals(key)) {
            this.setScaleForAge(this.isChild());
        }
        super.notifyDataManagerChange(key);
    }

    public void setScaleForAge(boolean child) {
        if (child) {
            this.setSize(0.5F, 1.3F);
            this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
        } else {
            this.setSize(0.8F, 2.7F);
            this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.31D);
        }
    }

    @Override
    public float getEyeHeight() {
        return this.isChild() ? 0.95F : 2.1F;
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

        if (!this.world.isRemote && this.isWet()) {
            this.attackEntityFrom(DamageSource.DROWN, 1.0F);
            this.teleportRandomly();
        }

        if (this.attackTimer > 0) {
            --this.attackTimer;
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isEntityInvulnerable(source)) {
            return false;
        }

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
                    break;
                }
            }
        }

        boolean flag = super.attackEntityFrom(source, amount);

        if (!this.world.isRemote && this.rand.nextInt(10) == 0) {
            this.teleportRandomly();
        }

        return flag;
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        this.attackTimer = 10;
        this.world.setEntityState(this, (byte) 4);

        boolean flag = super.attackEntityAsMob(entityIn);

        this.playSound(ModSounds.watchling_attack, 1.0F, 1.0F + (this.rand.nextFloat() * 0.2F));

        if (flag && entityIn instanceof EntityLivingBase) {
            if (this.isChild()) {
                this.teleportRandomly();
            }
        }
        return flag;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void handleStatusUpdate(byte id) {
        if (id == 4) {
            this.attackTimer = 10;
        } else {
            super.handleStatusUpdate(id);
        }
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return LOOT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.watchling_idle;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.watchling_hurt;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.watchling_death;
    }

    @Override
    protected void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound(ModSounds.watchling_step, 0.15F, 1.0F);
    }

    @SideOnly(Side.CLIENT)
    public int getAttackTimer() {
        return this.attackTimer;
    }
}