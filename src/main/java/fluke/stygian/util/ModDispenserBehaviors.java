package fluke.stygian.util;

import fluke.stygian.block.ModBlocks;
import fluke.stygian.entity.projectiles.EntityBlastlingBall;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ModDispenserBehaviors {

	public static void init() {
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModBlocks.endBlastlingBall, new BehaviorProjectileDispense() {

			@Override
			protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
				return new EntityBlastlingBall(worldIn, position.getX(), position.getY(), position.getZ());
			}

			@Override
			protected void playDispenseSound(IBlockSource source) {
				World world = source.getWorld();
				BlockPos pos = source.getBlockPos();
				world.playSound(null, pos, ModSounds.blastling_shoot, SoundCategory.BLOCKS, 1.0F, 1.3F);
			}

			@Override
			protected float getProjectileInaccuracy() {
				return 1.1F;
			}

			@Override
			protected float getProjectileVelocity() {
				return 1.1F;
			}

			@Override
			public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
				EnumFacing enumfacing = (EnumFacing)source.getBlockState().getValue(BlockDispenser.FACING);
				IPosition iposition = BlockDispenser.getDispensePosition(source);
				ItemStack itemstack = stack.splitStack(1);

				IProjectile iprojectile = this.getProjectileEntity(source.getWorld(), iposition, itemstack);

				iprojectile.shoot((double)enumfacing.getXOffset(), (double)enumfacing.getYOffset(), (double)enumfacing.getZOffset(), this.getProjectileVelocity(), this.getProjectileInaccuracy());

				source.getWorld().spawnEntity((net.minecraft.entity.Entity)iprojectile);
				return stack;
			}
		});
	}
}