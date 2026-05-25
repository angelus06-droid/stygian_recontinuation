package fluke.stygian.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;

import java.util.Random;

public class EntityAIMobTargetFly extends EntityAIBase {

	public EntityLiving parentEntity;
	public float distance;
	private int pathWithTarget = 0;

	public EntityAIMobTargetFly(EntityLiving flying, float dist)
	{
		this.parentEntity = flying;
		this.distance = dist;
		this.setMutexBits(2);
	}

	@Override
	public boolean shouldExecute() {
		EntityLivingBase target = this.parentEntity.getAttackTarget();
		if (target == null) return false;

		if (target == null || !target.isEntityAlive()) {
			this.parentEntity.setAttackTarget(null);
			this.parentEntity.getMoveHelper().setMoveTo(this.parentEntity.posX, this.parentEntity.posY, this.parentEntity.posZ, 0.0D);
			return false;
		}

		double distSq = this.parentEntity.getDistanceSq(target);

		double followRange = this.parentEntity.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getAttributeValue();

		if (distSq > followRange * followRange) {
			return false;
		}

		EntityMoveHelper moveHelper = this.parentEntity.getMoveHelper();
		if (!moveHelper.isUpdating()) {
			return true;
		} else {
			return this.pathWithTarget++ > 10;
		}
	}

	@Override
	public boolean shouldContinueExecuting()
	{
		return false;
	}

	@Override
	public void startExecuting()
	{
		Random random = this.parentEntity.getRNG();
		EntityLivingBase target = this.parentEntity.getAttackTarget();
		if (target != null)
		{
			double d0 = target.posX + (random.nextFloat() * 2.0F - 1.0F) * distance;
			double d1 = target.posY + this.parentEntity.getEyeHeight() + (random.nextFloat() * 2.0F - 1.0F) * distance;
			double d2 = target.posZ + (random.nextFloat() * 2.0F - 1.0F) * distance;

			if (this.parentEntity.getEntitySenses().canSee(target))
			{
				this.parentEntity.getMoveHelper().setMoveTo(d0, d1, d2, 1.0D);
			}
			else {
				Path path = this.parentEntity.getNavigator().getPathToEntityLiving(target);
				if (path != null && !path.isFinished()) {
					PathPoint point = path.getPathPointFromIndex(path.getCurrentPathIndex());
					this.parentEntity.getMoveHelper().setMoveTo(point.x, point.y, point.z, 1.0D);
				} else {
					this.parentEntity.getMoveHelper().setMoveTo(d0, d1, d2, 1.0D);
				}
			}
		}

		this.pathWithTarget = 0;
	}
}