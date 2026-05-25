package fluke.stygian.entity;

import fluke.stygian.entity.projectiles.EntityBlastlingBall;
import fluke.stygian.util.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.UUID;

public class EntityBlastling extends EntityMob {

    public static final ResourceLocation LOOT = new ResourceLocation("stygian", "entities/blastling");
    private static final DataParameter<Boolean> ARMS_RAISED = EntityDataManager.<Boolean>createKey(EntityBlastling.class, DataSerializers.BOOLEAN);

    private static final UUID SLOWDOWN_UUID = UUID.fromString("67222473-1959-4592-8823-9D22DBE03E01");
    private static final AttributeModifier SLOWDOWN_MODIFIER = (new AttributeModifier(SLOWDOWN_UUID, "Fireball slowdown", -0.6D, 2)).setSaved(false);

    private int attackTimer;
    private int fireballCooldown = 0;
    private int fireballsLeft = 0;
    private int timeBetweenShots = 0;

    public EntityBlastling(World worldIn) {
        super(worldIn);
        this.setSize(0.8F, 2.6F);
        this.experienceValue = 8;
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 0.8D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true, false));
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(ARMS_RAISED, Boolean.valueOf(false));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(15.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.31D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.5D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(30.0D);
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (!this.world.isRemote) {
            EntityLivingBase target = this.getAttackTarget();
            IAttributeInstance movementSpeed = this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);

            if (this.fireballCooldown > 0) {
                this.fireballCooldown--;
            }

            if (target != null && this.fireballCooldown <= 0 && this.fireballsLeft <= 0) {
                if (this.getDistanceSq(target) < 400.0D) {
                    this.fireballsLeft = 10 + this.rand.nextInt(4);
                    this.fireballCooldown = 300;
                }
            }

            if (this.fireballsLeft > 0 && target != null) {
                if (!movementSpeed.hasModifier(SLOWDOWN_MODIFIER)) {
                    movementSpeed.applyModifier(SLOWDOWN_MODIFIER);
                }

                if (this.timeBetweenShots <= 0) {
                    this.shootFireball(target);
                    this.fireballsLeft--;
                    this.timeBetweenShots = 6;
                } else {
                    this.timeBetweenShots--;
                }
                this.setSwingingArms(true);
            } else {
                if (movementSpeed.hasModifier(SLOWDOWN_MODIFIER)) {
                    movementSpeed.removeModifier(SLOWDOWN_MODIFIER);
                }
                this.setSwingingArms(false);
            }
        }

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

    private void shootFireball(EntityLivingBase target) {
        boolean isRightSide = (this.fireballsLeft % 2 == 0);
        float sideOffset = isRightSide ? -0.7F : 0.7F;

        float yaw = this.renderYawOffset;
        double radYaw = Math.toRadians(yaw);
        double radPitch = Math.toRadians(this.rotationPitch);
        double offsetX = -Math.cos(radYaw) * sideOffset;
        double offsetZ = -Math.sin(radYaw) * sideOffset;

        double offsetY = this.height * 0.7D;

        double d0 = target.posX - (this.posX + offsetX);
        double d1 = target.getEntityBoundingBox().minY + (double)(target.height / 2.0F) - (this.posY + offsetY);
        double d2 = target.posZ - (this.posZ + offsetZ);

        EntityBlastlingBall entityfireball = new EntityBlastlingBall(this.world, this, d0, d1, d2);

        entityfireball.setPosition(this.posX + offsetX, this.posY + offsetY, this.posZ + offsetZ);

        this.world.spawnEntity(entityfireball);

        this.playSound(ModSounds.blastling_shoot, 1.0F, 1.0F + (this.rand.nextFloat() * 0.1F));
    }
    public void setSwingingArms(boolean swingingArms) {
        this.dataManager.set(ARMS_RAISED, Boolean.valueOf(swingingArms));
    }

    @SideOnly(Side.CLIENT)
    public boolean isArmsRaised() {
        return ((Boolean)this.dataManager.get(ARMS_RAISED)).booleanValue();
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isEntityInvulnerable(source)) return false;

        if (source.getTrueSource() instanceof EntityLivingBase) {
            for (int i = 0; i < 16; ++i) {
                if (this.teleportTowards((Entity) source.getTrueSource())) {
                    return true;
                }
            }
        }

        if (source instanceof net.minecraft.util.EntityDamageSourceIndirect) {
            for (int i = 0; i < 64; ++i) {
                if (this.teleportRandomly()) {
                    return false;
                }
            }
            return false;
        }

        boolean flag = super.attackEntityFrom(source, amount);
        if (this.rand.nextInt(10) == 0) this.teleportRandomly();
        return flag;
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        this.attackTimer = 10;
        this.world.setEntityState(this, (byte) 4);
        this.playSound(SoundEvents.ENTITY_SKELETON_HURT, 1.0f, 0.8f);
        return super.attackEntityAsMob(entityIn);
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
        if (id == 4) this.attackTimer = 10;
        else super.handleStatusUpdate(id);
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return LOOT;
    }

    @Override protected SoundEvent getAmbientSound() { return ModSounds.blastling_idle; }
    @Override protected SoundEvent getHurtSound(DamageSource source) { return ModSounds.blastling_hurt; }
    @Override protected SoundEvent getDeathSound() { return ModSounds.blastling_death; }
    @Override protected void playStepSound(BlockPos pos, Block blockIn) { this.playSound(ModSounds.blastling_step, 0.15F, 1.0F); }
    @SideOnly(Side.CLIENT) public int getAttackTimer() { return this.attackTimer; }
    @Override public float getEyeHeight() { return 2.1F; }
}