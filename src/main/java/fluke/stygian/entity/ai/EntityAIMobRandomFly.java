package fluke.stygian.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityMoveHelper;

import java.util.Random;

public class EntityAIMobRandomFly extends EntityAIBase {

	public EntityLiving parentEntity;
	public float distance;

	public EntityAIMobRandomFly(EntityLiving flying, float dist)
	{
		this.parentEntity = flying;
		this.distance = dist;
		this.setMutexBits(2);
	}

	@Override
	public boolean shouldExecute()
	{
		EntityMoveHelper entitymovehelper = this.parentEntity.getMoveHelper();

		if (!entitymovehelper.isUpdating())
		{
			return true;
		}
		else
		{
			double d0 = entitymovehelper.getX() - this.parentEntity.posX;
			double d1 = entitymovehelper.getY() - this.parentEntity.posY;
			double d2 = entitymovehelper.getZ() - this.parentEntity.posZ;
			double d3 = d0 * d0 + d1 * d1 + d2 * d2;
			return d3 < 1.0D || d3 > 1600.0D;
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
		double d0 = this.parentEntity.posX + (random.nextFloat() * 2.0F - 1.0F) * distance;
		double d1 = this.parentEntity.posY + (random.nextFloat() * 1.0F - 0.5F) * (distance / 2.0F);
		double d2 = this.parentEntity.posZ + (random.nextFloat() * 2.0F - 1.0F) * distance;
		this.parentEntity.getMoveHelper().setMoveTo(d0, d1, d2, 1.0D);
	}
}