package fluke.stygian.entity;

import fluke.stygian.util.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
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
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class EntityLunarZorp extends EntityMob {

    public static final ResourceLocation LOOT = new ResourceLocation("stygian", "entities/lunar_zorp");
    private int attackTimer;

    public EntityLunarZorp(World worldIn) {
        super(worldIn);
        this.setSize(0.6F, 1.9F);
        this.experienceValue = 6;
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.2D, false));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 0.8D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(25.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(31.0D);
        this.stepHeight = 1.0F;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (this.world.isRemote && this.rand.nextFloat() < 0.1F) {
            for (int i = 0; i < 1; ++i) {
                this.world.spawnParticle(
                        EnumParticleTypes.CLOUD,
                        this.posX + (this.rand.nextDouble() - 0.5D) * this.width,
                        this.posY + this.rand.nextDouble() * this.height,
                        this.posZ + (this.rand.nextDouble() - 0.5D) * this.width,
                        0.0D, 0.02D, 0.0D
                );
            }
        }

        if (!this.onGround && this.motionY < 0.0D) {
            this.motionY *= 0.55D;
        }

        if (this.attackTimer > 0) {
            --this.attackTimer;
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        this.attackTimer = 10;
        this.world.setEntityState(this, (byte) 4);
        boolean flag = super.attackEntityAsMob(entityIn);

        if (flag) {
            this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_WEAK, 1.2F, 1.3F);
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

    @Override public void fall(float distance, float damageMultiplier) {

    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.lunar_zorp_idle;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.lunar_zorp_hurt;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.lunar_zorp_death;
    }

    @Override
    protected void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound(SoundEvents.ENTITY_PIG_STEP, 0.8F, 1.2F);
    }

    @SideOnly(Side.CLIENT)
    public int getAttackTimer() {
        return this.attackTimer;
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return LOOT;
    }
}