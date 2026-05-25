package fluke.stygian.entity;

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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class EntityCracky extends EntityMob
{
    public int attackTimer;
    public static final ResourceLocation LOOT_TABLE = new ResourceLocation("stygian", "entities/cracky");

    public EntityCracky(final World worldIn) {
        super(worldIn);
        this.setSize(0.8f, 1.3f);
    }

    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEAD;
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.2, false));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 0.8));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(6, new EntityAILookIdle(this));

        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true, false));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(15.0);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        this.attackTimer = 10;
        this.world.setEntityState(this, (byte)4);

        entityIn.hurtResistantTime = 0;

        float damage = (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), damage);

        if (flag) {
            this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0f, 1.5f);

            if (entityIn instanceof EntityLivingBase) {
                double deltaX = this.posX - entityIn.posX;
                double deltaZ = this.posZ - entityIn.posZ;

                while (deltaX * deltaX + deltaZ * deltaZ < 1.0E-4D) {
                    deltaX = (Math.random() - Math.random()) * 0.01D;
                    deltaZ = (Math.random() - Math.random()) * 0.01D;
                }

                ((EntityLivingBase)entityIn).knockBack(this, 0.1F, deltaX, deltaZ);

                this.applyEnchantments(this, entityIn);
            }
        }
        return flag;
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return LOOT_TABLE;
    }

    protected SoundEvent getAmbientSound() {
        this.playSound(SoundEvents.ENTITY_WITHER_SKELETON_AMBIENT, 1.0F, 1.5F);
        return null;
    }

    protected SoundEvent getHurtSound(final DamageSource damageSourceIn) {
        this.playSound(SoundEvents.ENTITY_WITHER_SKELETON_HURT, 1.0F, 1.5F);
        return null;
    }

    protected SoundEvent getDeathSound() {
        this.playSound(SoundEvents.ENTITY_WITHER_SKELETON_DEATH, 1.0F, 1.5F);
        return null;
    }

    @Override
    protected void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound(SoundEvents.ENTITY_WITHER_SKELETON_STEP, 0.15F, 1.3F);
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.attackTimer > 0) {
            --this.attackTimer;
        }
    }

    @SideOnly(Side.CLIENT)
    public int getAttackTimer() {
        return this.attackTimer;
    }
}