package fluke.stygian.entity;

import fluke.stygian.util.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class EntityEndbob extends EntityCreature {

    public static final ResourceLocation LOOT = new ResourceLocation("stygian", "entities/endbob");

    public EntityEndbob(World worldIn) {
        super(worldIn);
        this.setSize(0.8F, 0.8F);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));

        this.tasks.addTask(1, new EntityAIPanic(this, 1.5D));

        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 0.8D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }

    private void alertFellowEndbobs(DamageSource source) {
        double radius = 16.0D;
        List<EntityEndbob> list = this.world.getEntitiesWithinAABB(EntityEndbob.class,
                this.getEntityBoundingBox().grow(radius, 8.0D, radius));

        for (EntityEndbob nearbyBob : list) {
            if (nearbyBob.getRevengeTarget() == null && source.getTrueSource() instanceof net.minecraft.entity.EntityLivingBase) {
                nearbyBob.setRevengeTarget((net.minecraft.entity.EntityLivingBase) source.getTrueSource());
            }
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isEntityInvulnerable(source)) {
            return false;
        }

        boolean flag = super.attackEntityFrom(source, amount);

        if (flag && !this.world.isRemote) {
            this.alertFellowEndbobs(source);
        }

        return flag;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (this.world.isRemote) {
            for (int i = 0; i < 2; ++i) {
                this.world.spawnParticle(
                        EnumParticleTypes.PORTAL,
                        this.posX + (this.rand.nextDouble() - 0.5D) * this.width,
                        this.posY + this.rand.nextDouble() * this.height - 0.25D,
                        this.posZ + (this.rand.nextDouble() - 0.5D) * this.width,
                        (this.rand.nextDouble() - 0.5D) * 2.0D,
                        -this.rand.nextDouble(),
                        (this.rand.nextDouble() - 0.5D) * 2.0D
                );
            }
        }
    }

    @Override
    public float getEyeHeight() {
        return 0.5F;
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return LOOT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.endbob_idle;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.endbob_hurt;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.endbob_death;
    }

    @Override
    protected void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound(SoundEvents.ENTITY_CHICKEN_STEP, 0.2F, 1.5F);
    }
}