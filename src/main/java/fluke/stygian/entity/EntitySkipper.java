package fluke.stygian.entity;

import fluke.stygian.util.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityLivingBase;
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
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class EntitySkipper extends EntityMob {

    private int attackTimer;

    public static final ResourceLocation LOOT = new ResourceLocation("stygian", "entities/skipper");
    private static final DataParameter<Byte> CLIMBING = EntityDataManager.createKey(EntitySkipper.class, DataSerializers.BYTE);
    private static final DataParameter<Integer> STATE = EntityDataManager.createKey(EntitySkipper.class, DataSerializers.VARINT);

    private int lastActiveTime;
    private int timeSinceIgnited;
    private int fuseTime = 30;

    public EntitySkipper(World worldIn) {
        super(worldIn);
        this.setSize(0.8f, 0.9f);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(CLIMBING, (byte)0);
        this.dataManager.register(STATE, -1);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAISkipperSwell(this));
        this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4f));
        this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.1D, false));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 0.8D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(6, new EntityAILookIdle(this));

        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(15.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.5D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(31.0D);
    }

    @Override
    public void onUpdate() {
        if (this.isEntityAlive()) {
            this.lastActiveTime = this.timeSinceIgnited;
            int i = this.getSwellState();

            if (i > 0 && this.timeSinceIgnited == 0) {
                this.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0F, 0.5F);
            }

            this.timeSinceIgnited += i;

            if (this.timeSinceIgnited < 0) {
                this.timeSinceIgnited = 0;
            }

            if (this.timeSinceIgnited >= this.fuseTime) {
                this.timeSinceIgnited = this.fuseTime;
                this.explodeSlowness();
            }
        }

        super.onUpdate();

        if (!this.world.isRemote) {
            this.setBesideClimbableBlock(this.collidedHorizontally);
        }
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (this.attackTimer > 0) --this.attackTimer;
    }

    private void explodeSlowness() {
        if (!this.world.isRemote) {
            this.playSound(SoundEvents.ENTITY_SLIME_SQUISH, 1.5F, 0.4F);
            this.playSound(ModSounds.snareling_glob_land, 1.5F, 0.3F);

            if (this.world instanceof WorldServer) {
                WorldServer ws = (WorldServer) this.world;

                ws.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.posX, this.posY, this.posZ, 1, 0.0D, 0.0D, 0.0D, 0.0D);

                ws.spawnParticle(EnumParticleTypes.SLIME, this.posX, this.posY, this.posZ, 20, 0.5D, 0.5D, 0.5D, 0.05D);
            }

            float damage = (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
            AxisAlignedBB area = this.getEntityBoundingBox().grow(4.0D, 2.0D, 4.0D);
            List<EntityLivingBase> targets = this.world.getEntitiesWithinAABB(EntityLivingBase.class, area);

            for (EntityLivingBase entity : targets) {
                if (entity != this) {
                    entity.attackEntityFrom(DamageSource.causeMobDamage(this), damage);
                }
            }

            EntityAreaEffectCloud cloud = new EntityAreaEffectCloud(this.world, this.posX, this.posY, this.posZ);
            cloud.setRadius(3.5F);
            cloud.setDuration(120);
            cloud.addEffect(new PotionEffect(MobEffects.SLOWNESS, 140, 2));
            cloud.setColor(0xdbe64e);
            this.world.spawnEntity(cloud);

            this.setDead();
        }
    }

    public int getSwellState() {
        return this.dataManager.get(STATE);
    }

    public void setSwellState(int state) {
        this.dataManager.set(STATE, state);
    }

    @Override
    public boolean isOnLadder() {
        return this.isBesideClimbableBlock();
    }

    public boolean isBesideClimbableBlock() {
        return (this.dataManager.get(CLIMBING) & 1) != 0;
    }

    public void setBesideClimbableBlock(boolean climbing) {
        byte b0 = this.dataManager.get(CLIMBING);
        if (climbing) b0 = (byte)(b0 | 1);
        else b0 = (byte)(b0 & -2);
        this.dataManager.set(CLIMBING, b0);
    }

    static class EntityAISkipperSwell extends EntityAIBase {
        EntitySkipper skipper;
        EntityLivingBase target;

        public EntityAISkipperSwell(EntitySkipper skipperIn) {
            this.skipper = skipperIn;
            this.setMutexBits(1);
        }

        public boolean shouldExecute() {
            EntityLivingBase entitylivingbase = this.skipper.getAttackTarget();
            return this.skipper.getHealth() < this.skipper.getMaxHealth() * 0.4F
                    && entitylivingbase != null
                    && this.skipper.getDistanceSq(entitylivingbase) < 9.0D;
        }

        public void startExecuting() {
            this.skipper.getNavigator().clearPath();
            this.target = this.skipper.getAttackTarget();
        }

        public void resetTask() {
            this.target = null;
        }

        public void updateTask() {
            if (this.target == null) {
                this.skipper.setSwellState(-1);
            } else if (this.skipper.getDistanceSq(this.target) > 49.0D) {
                this.skipper.setSwellState(-1);
            } else if (!this.skipper.getEntitySenses().canSee(this.target)) {
                this.skipper.setSwellState(-1);
            } else {
                this.skipper.setSwellState(1);
            }
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        this.attackTimer = 10;
        this.world.setEntityState(this, (byte) 4);
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
        if (flag) this.applyEnchantments(this, entityIn);
        return flag;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void handleStatusUpdate(byte id) {
        if (id == 4) this.attackTimer = 10;
        else super.handleStatusUpdate(id);
    }

    @SideOnly(Side.CLIENT)
    public int getAttackTimer() { return this.attackTimer; }

    @SideOnly(Side.CLIENT)
    public float getCreeperFlashIntensity(float partialTicks) {
        return ((float)this.lastActiveTime + (float)(this.timeSinceIgnited - this.lastActiveTime) * partialTicks) / (float)(this.fuseTime - 2);
    }

    @Override
    protected void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound(SoundEvents.ENTITY_SPIDER_STEP, 0.5f, 2.0f);
    }

    @Override @Nullable
    protected ResourceLocation getLootTable() {
        return LOOT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource ds) { return ModSounds.skipper_hurt; }

    @Override
    protected SoundEvent getDeathSound() { return ModSounds.skipper_death; }

    @Override
    protected SoundEvent getAmbientSound() { return ModSounds.skipper_idle; }

    @Override
    protected boolean canDespawn() {
        return true;
    }
}