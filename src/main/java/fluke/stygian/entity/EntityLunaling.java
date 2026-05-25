package fluke.stygian.entity;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
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

import com.google.common.base.Predicate;
import javax.annotation.Nullable;

public class EntityLunaling extends EntityCreature {

    public static final ResourceLocation LOOT = new ResourceLocation("stygian", "entities/lunaling");
    private int attackTimer;
    private int healTimer;

    public EntityLunaling(World worldIn) {
        super(worldIn);
        this.setSize(0.6f, 0.85f);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.1D, false));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 0.8D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(6, new EntityAILookIdle(this));

        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));

        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityLivingBase.class, 10, false, false, new Predicate<EntityLivingBase>() {
            @Override
            public boolean apply(@Nullable EntityLivingBase entity) {
                if (entity == null) return false;
                String className = entity.getClass().getSimpleName();
                return className.equals("EntityWatchling") ||
                        className.equals("EntityBlastling") ||
                        className.equals("EntitySnareling");
            }
        }));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (this.attackTimer > 0) --this.attackTimer;

        if (!this.world.isRemote) {
            if (this.isEntityAlive() && this.getHealth() < this.getMaxHealth()) {
                this.healTimer++;
                if (this.healTimer >= 100) {
                    this.heal(2.0F);
                    this.healTimer = 0;
                }
            } else {
                this.healTimer = 0;
            }
        }

        if (this.motionX * this.motionX + this.motionZ * this.motionZ > 2.5E-7D && this.rand.nextInt(5) == 0) {
            renderWalkParticles();
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

    private void renderWalkParticles() {
        BlockPos pos = new BlockPos(MathHelper.floor(this.posX), MathHelper.floor(this.posY - 0.2D), MathHelper.floor(this.posZ));
        IBlockState state = this.world.getBlockState(pos);
        if (state.getMaterial() != Material.AIR) {
            this.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK,
                    this.posX + (this.rand.nextFloat() - 0.5D) * this.width,
                    this.getEntityBoundingBox().minY + 0.1D,
                    this.posZ + (this.rand.nextFloat() - 0.5D) * this.width,
                    0, 0, 0, Block.getStateId(state));
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void handleStatusUpdate(byte id) {
        if (id == 4) this.attackTimer = 10;
        else super.handleStatusUpdate(id);
    }

    @SideOnly(Side.CLIENT)
    public int getAttackTimer() {
        return this.attackTimer;
    }

    @Override @Nullable
    protected ResourceLocation getLootTable() {
        return LOOT;
    }

    @Override protected SoundEvent getHurtSound(DamageSource ds) {
        return SoundEvents.BLOCK_STONE_HIT;
    }
    @Override protected SoundEvent getDeathSound() {
        return SoundEvents.BLOCK_STONE_BREAK;
    }
    @Override protected void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound(SoundEvents.ENTITY_IRONGOLEM_STEP, 0.25f, 5.0f);
    }
}