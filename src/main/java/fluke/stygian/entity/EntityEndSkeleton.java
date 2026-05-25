package fluke.stygian.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Calendar;

public class EntityEndSkeleton extends EntityMob implements IRangedAttackMob {
    public static final ResourceLocation LOOT = new ResourceLocation("stygian", "entities/end_skeleton");
    public int weaponType;
    private static final DataParameter<Boolean> SWINGING_ARMS;
    public final EntityAIAttackRangedBow<EntityEndSkeleton> aiArrowAttack;
    public final EntityAIAttackMelee aiAttackOnCollide;

    public EntityEndSkeleton(final World worldIn) {
        super(worldIn);
        this.aiArrowAttack = new EntityAIAttackRangedBow<>(this, 1.0, 25, 1.0f);
        this.aiAttackOnCollide = new EntityAIAttackMelee(this, 1.2, false) {
            public void resetTask() {
                super.resetTask();
                EntityEndSkeleton.this.setSwingingArms(false);
            }

            public void startExecuting() {
                super.startExecuting();
                EntityEndSkeleton.this.setSwingingArms(true);
            }
        };
        this.weaponType = this.rand.nextInt(3);
        this.setCombatTask();
    }

    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 1.0));
        this.tasks.addTask(6, new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(6, new EntityAILookIdle((EntityLiving)this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20);
    }

    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(EntityEndSkeleton.SWINGING_ARMS, false);
    }

    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEAD;
    }

    public void updateRidden() {
        super.updateRidden();
        if (this.getRidingEntity() instanceof EntityCreature) {
            final EntityCreature entitycreature = (EntityCreature)this.getRidingEntity();
            this.renderYawOffset = entitycreature.renderYawOffset;
        }
    }

    @Nullable
    public IEntityLivingData onInitialSpawn(final DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        this.setEquipmentBasedOnDifficulty(difficulty);
        this.setEnchantmentBasedOnDifficulty(difficulty);
        this.setCombatTask();
        this.SwitchWeapon();
        if (this.getItemStackFromSlot(EntityEquipmentSlot.HEAD).isEmpty()) {
            final Calendar calendar = this.world.getCurrentDate();
            if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.rand.nextFloat() < 0.25f) {
                this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack((this.rand.nextFloat() < 0.1f) ? Blocks.LIT_PUMPKIN : Blocks.PUMPKIN));
                this.inventoryArmorDropChances[EntityEquipmentSlot.HEAD.getIndex()] = 0.0f;
            }
        }
        return livingdata;
    }

    public void setCombatTask() {
        if (this.world != null && !this.world.isRemote) {
            this.tasks.removeTask(this.aiAttackOnCollide);
            this.tasks.removeTask(this.aiArrowAttack);
            final ItemStack itemstack = this.getHeldItemMainhand();
            if (itemstack.getItem() == Items.BOW) {
                int i = 20;
                if (this.world.getDifficulty() != EnumDifficulty.HARD) {
                    i = 40;
                }
                this.aiArrowAttack.setAttackCooldown(i);
                this.tasks.addTask(4, this.aiArrowAttack);
            }
            else {
                this.tasks.addTask(4, this.aiAttackOnCollide);
            }
        }
    }

    public boolean attackEntityAsMob(final Entity par1Entity) {
        par1Entity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), 5.0F);
        this.teleportRandomly();
        return true;
    }

    public static void registerFixesSkeleton(final DataFixer fixer) {
        EntityLiving.registerFixesMob(fixer, EntityEndSkeleton.class);
    }

    protected SoundEvent getAmbientSound() {
        this.playSound(SoundEvents.ENTITY_SKELETON_AMBIENT, 1.0F, 0.6F);
        return null;
    }

    protected SoundEvent getHurtSound(final DamageSource damageSourceIn) {
        this.playSound(SoundEvents.ENTITY_SKELETON_HURT, 1.0F, 0.6F);
        return null;
    }

    protected SoundEvent getDeathSound() {
        this.playSound(SoundEvents.ENTITY_SKELETON_DEATH, 1.0F, 0.6F);
        return null;
    }

    @Override
    protected void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound(SoundEvents.ENTITY_SKELETON_STEP, 0.15F, 0.6F);
    }

    protected boolean teleportRandomly() {
        final double d0 = this.posX + (this.rand.nextDouble() - 0.5) * 32.0;
        final double d2 = this.posY + (this.rand.nextInt(16) - 8);
        final double d3 = this.posZ + (this.rand.nextDouble() - 0.5) * 32.0;
        return this.teleportTo(d0, d2, d3);
    }

    protected boolean teleportToEntity(final Entity p_70816_1_) {
        Vec3d vec3d = new Vec3d(this.posX - p_70816_1_.posX, this.getEntityBoundingBox().minY + this.height / 2.0f - p_70816_1_.posY + p_70816_1_.getEyeHeight(), this.posZ - p_70816_1_.posZ);
        vec3d = vec3d.normalize();
        final double d0 = 16.0;
        final double d2 = this.posX + (this.rand.nextDouble() - 0.5) * 8.0 - vec3d.x * 16.0;
        final double d3 = this.posY + (this.rand.nextInt(16) - 8) - vec3d.y * 16.0;
        final double d4 = this.posZ + (this.rand.nextDouble() - 0.5) * 8.0 - vec3d.z * 16.0;
        return this.teleportTo(d2, d3, d4);
    }

    private boolean teleportTo(final double x, final double y, final double z) {
        final EnderTeleportEvent event = new EnderTeleportEvent((EntityLivingBase)this, x, y, z, 0.0f);
        if (MinecraftForge.EVENT_BUS.post((Event)event)) {
            return false;
        }
        final boolean flag = this.attemptTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ());
        if (flag) {
            this.world.playSound((EntityPlayer)null, this.prevPosX, this.prevPosY, this.prevPosZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, this.getSoundCategory(), 1.0f, 1.0f);
            this.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0f, 1.0f);
        }
        return flag;
    }

    public void SwitchWeapon() {
        switch (this.weaponType) {
            case 1: {
                this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack((Item)Items.BOW));
                this.setCombatTask();
                this.weaponType = this.rand.nextInt(3);
                break;
            }
            default: {
                this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.STONE_SWORD));
                this.setCombatTask();
                this.weaponType = this.rand.nextInt(3);
                break;
            }
        }
    }

    public void onDeath(final DamageSource cause) {
        super.onDeath(cause);
        if (cause.getTrueSource() instanceof EntityCreeper) {
            final EntityCreeper entitycreeper = (EntityCreeper)cause.getTrueSource();
            if (entitycreeper.getPowered() && entitycreeper.ableToCauseSkullDrop()) {
                entitycreeper.incrementDroppedSkulls();
                this.entityDropItem(new ItemStack(Items.SKULL, 1, 0), 0.0f);
            }
        }
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
        EntityTippedArrow arrow = new EntityTippedArrow(this.world, this);
        ItemStack bowStack = this.getHeldItemMainhand();

        double d0 = target.posX - this.posX;
        double d1 = target.getEntityBoundingBox().minY + (double)(target.height / 3.0F) - arrow.posY;
        double d2 = target.posZ - this.posZ;
        double d3 = (double)MathHelper.sqrt(d0 * d0 + d2 * d2);

        arrow.shoot(d0, d1 + d3 * 0.2D, d2, 1.6F, (float)(14 - this.world.getDifficulty().getId() * 6));

        this.playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        this.world.spawnEntity(arrow);
    }

    protected EntityTippedArrow getArrow(final float p_190726_1_) {
        final EntityTippedArrow entitytippedarrow = new EntityTippedArrow(this.world, (EntityLivingBase)this);
        return entitytippedarrow;
    }

    public void readEntityFromNBT(final NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setCombatTask();
    }

    public void setItemStackToSlot(final EntityEquipmentSlot slotIn, final ItemStack stack) {
        super.setItemStackToSlot(slotIn, stack);
        if (!this.world.isRemote && slotIn == EntityEquipmentSlot.MAINHAND) {
            this.setCombatTask();
        }
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return LOOT;
    }

    public float getEyeHeight() {
        return 1.74f;
    }

    public double getYOffset() {
        return -0.6;
    }

    @SideOnly(Side.CLIENT)
    public boolean isSwingingArms() {
        return (boolean)this.dataManager.get(EntityEndSkeleton.SWINGING_ARMS);
    }

    public void setSwingingArms(final boolean swingingArms) {
        this.dataManager.set(EntityEndSkeleton.SWINGING_ARMS, swingingArms);
    }

    static {
        SWINGING_ARMS = EntityDataManager.createKey(EntityEndSkeleton.class, DataSerializers.BOOLEAN);
    }
}