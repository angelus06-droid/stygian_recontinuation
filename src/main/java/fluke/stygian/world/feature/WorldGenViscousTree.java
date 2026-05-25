package fluke.stygian.world.feature;

import fluke.stygian.block.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class WorldGenViscousTree extends WorldGenAbstractTree {
	private static final IBlockState LOG = ModBlocks.endViscousLog.getDefaultState();
	private static final IBlockState LEAF = ModBlocks.endViscousLeaves.getDefaultState();

	private static final IBlockState TARGET_GRASS = ModBlocks.endSnareGrass.getDefaultState();
	private static final int MIN_HEIGHT = 8;
	private static final int MAX_HEIGHT = 14;

	public WorldGenViscousTree(boolean notify) {
		super(notify);
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {
		int height = rand.nextInt(MAX_HEIGHT - MIN_HEIGHT + 1) + MIN_HEIGHT;

		if (world.getBlockState(pos.down()).getBlock() != Blocks.END_STONE && world.getBlockState(pos.down()) != TARGET_GRASS) {
			return false;
		}

		BlockPos currentPos = pos;
		double offsetX = 0;
		double offsetZ = 0;

		for (int i = 0; i < height; i++) {
			BlockPos logPos = pos.add(offsetX, i, offsetZ);
			if (isReplaceable(world, logPos)) {
				this.setBlockAndNotifyAdequately(world, logPos, LOG);
			}

			if (i % 3 == 0) {
				offsetX += (rand.nextDouble() - 0.5) * 1.5;
				offsetZ += (rand.nextDouble() - 0.5) * 1.5;
			}

			if (i > height / 2 && rand.nextInt(3) == 0) {
				generateBranch(world, logPos, rand);
			}
		}

		generateCellularCanopy(world, pos.add(offsetX, height, offsetZ), rand, 4);

		return true;
	}

	private void generateBranch(World world, BlockPos startPos, Random rand) {
		int branchLen = 3 + rand.nextInt(3);
		int dirX = rand.nextInt(3) - 1;
		int dirZ = rand.nextInt(3) - 1;

		BlockPos branchPos = startPos;
		for (int i = 0; i < branchLen; i++) {
			branchPos = branchPos.add(dirX, 1, dirZ);
			if (isReplaceable(world, branchPos)) {
				this.setBlockAndNotifyAdequately(world, branchPos, LOG);
			} else {
				break;
			}
		}
		generateCellularCanopy(world, branchPos, rand, 3);
	}

	private void generateCellularCanopy(World world, BlockPos center, Random rand, int radius) {
		for (int x = -radius; x <= radius; x++) {
			for (int y = -radius; y <= radius; y++) {
				for (int z = -radius; z <= radius; z++) {
					double distance = Math.sqrt((x * x) + (y * y * 1.1) + (z * z));

					if (distance <= radius) {
						BlockPos leafPos = center.add(x, y, z);
						if (world.isAirBlock(leafPos) || world.getBlockState(leafPos).getBlock().isLeaves(world.getBlockState(leafPos), world, leafPos)) {
							this.setBlockAndNotifyAdequately(world, leafPos, LEAF);
						}
					}
				}
			}
		}
	}

	@Override
	public boolean isReplaceable(World world, BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		return state.getBlock().isAir(state, world, pos) || state.getBlock().isLeaves(state, world, pos) || state.getBlock() instanceof BlockBush;
	}
}