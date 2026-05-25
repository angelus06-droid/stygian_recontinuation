package fluke.stygian.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;

public class EntityAIFlyingMoveHelper extends EntityMoveHelper {
	private final EntityLiving parentEntity;
	private final boolean checkCollision;

	public EntityAIFlyingMoveHelper(EntityLiving parent, boolean collisionChecks) {
		super(parent);
		this.parentEntity = parent;
		this.checkCollision = collisionChecks;
	}

	@Override
	public void onUpdateMoveHelper() {
		if (this.action == EntityMoveHelper.Action.MOVE_TO) {
			double d0 = this.posX - this.parentEntity.posX;
			double d1 = this.posY - this.parentEntity.posY;
			double d2 = this.posZ - this.parentEntity.posZ;
			double d3 = d0 * d0 + d1 * d1 + d2 * d2;

			if (d3 < 1.0E-7D) {
				this.parentEntity.setMoveForward(0.0F);
				return;
			}

			float f = (float)(MathHelper.atan2(d2, d0) * (180D / Math.PI)) - 90.0F;
			this.parentEntity.rotationYaw = this.limitAngle(this.parentEntity.rotationYaw, f, 10.0F);
			this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw;

			double speedAttr = this.parentEntity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();
			double speed = speedAttr * this.speed;

			d3 = (double)MathHelper.sqrt(d3);

			if (!checkCollision || this.isNotColliding(this.posX, this.posY, this.posZ, d3)) {
				this.parentEntity.motionX += d0 / d3 * speed * 0.1D;
				this.parentEntity.motionY += d1 / d3 * speed * 0.1D;
				this.parentEntity.motionZ += d2 / d3 * speed * 0.1D;

				float f1 = (float)(-(MathHelper.atan2(d1, d3) * (180D / Math.PI)));
				this.parentEntity.rotationPitch = this.limitAngle(this.parentEntity.rotationPitch, f1, 10.0F);
			} else {
				this.action = EntityMoveHelper.Action.WAIT;
			}

			if (Math.abs(this.parentEntity.motionX) < 0.01 && Math.abs(this.parentEntity.motionZ) < 0.01 && !this.parentEntity.isNotColliding()) {
				this.parentEntity.motionY += 0.05D;
			}
			EntityLivingBase target = this.parentEntity.getAttackTarget();
			if (target != null) {
				double diffX = target.posX - this.parentEntity.posX;
				double diffZ = target.posZ - this.parentEntity.posZ;
				float targetYaw = (float)(MathHelper.atan2(diffZ, diffX) * (180D / Math.PI)) - 90.0F;

				this.parentEntity.rotationYaw = this.limitAngle(this.parentEntity.rotationYaw, targetYaw, 30.0F);
				this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw;
			} else {
				float targetYaw = (float)(MathHelper.atan2(d2, d0) * (180D / Math.PI)) - 90.0F;
				this.parentEntity.rotationYaw = this.limitAngle(this.parentEntity.rotationYaw, targetYaw, 10.0F);
				this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw;
			}
		}
	}

	public float limitAngle(float sourceAngle, float targetAngle, float maxChange) {
		float f = MathHelper.wrapDegrees(targetAngle - sourceAngle);
		if (f > maxChange) f = maxChange;
		if (f < -maxChange) f = -maxChange;
		return sourceAngle + f;
	}

	private boolean isNotColliding(double wayX, double wayY, double wayZ, double dist) {
		double d0 = (wayX - this.parentEntity.posX) / dist;
		double d1 = (wayY - this.parentEntity.posY) / dist;
		double d2 = (wayZ - this.parentEntity.posZ) / dist;
		AxisAlignedBB axisalignedbb = this.parentEntity.getEntityBoundingBox();

		for (int i = 1; (double)i < dist; ++i) {
			axisalignedbb = axisalignedbb.offset(d0, d1, d2);
			if (!this.parentEntity.world.getCollisionBoxes(this.parentEntity, axisalignedbb).isEmpty()) {
				return false;
			}
		}
		return true;
	}
}