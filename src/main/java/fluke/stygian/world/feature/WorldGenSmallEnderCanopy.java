package fluke.stygian.world.feature;

import fluke.stygian.block.ModBlocks;
import fluke.stygian.util.MathUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldGenSmallEnderCanopy extends WorldGenAbstractTree {
	private static final IBlockState LOG = ModBlocks.endLog.getDefaultState();
	private static final IBlockState LEAF = ModBlocks.endLeaves.getDefaultState();
	private static final IBlockState END_GRASS = ModBlocks.endGrass.getDefaultState();

	private static final int MIN_TRUNK_HEIGHT = 5;
	private static final int MAX_TRUNK_HEIGHT = 9;

	public WorldGenSmallEnderCanopy(boolean notify) {
		super(notify);
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {
		IBlockState stateBelow = world.getBlockState(pos.down());

		if (stateBelow.getBlock() != ModBlocks.endGrass && stateBelow.getBlock() != Blocks.END_STONE) {
			return false;
		}

		int trunkHeight = MIN_TRUNK_HEIGHT + rand.nextInt(MAX_TRUNK_HEIGHT - MIN_TRUNK_HEIGHT + 1);

		if (!isValidGenLocation(world, pos, trunkHeight)) {
			return false;
		}

		buildTrunk(world, rand, pos, trunkHeight);
		List<BranchInfo> branches = buildBranches(world, rand, pos, trunkHeight);
		buildCanopy(world, rand, pos, branches);

		return true;
	}

	private void placeLogAt(World worldIn, BlockPos pos) {
		worldIn.setBlockState(pos, LOG);
	}

	private void placeLeafAt(World worldIn, BlockPos pos) {
		IBlockState state = worldIn.getBlockState(pos);
		if (state.getBlock().isAir(state, worldIn, pos) || state.getBlock().isLeaves(state, worldIn, pos)) {
			worldIn.setBlockState(pos, LEAF);
		}
	}

	public boolean isValidGenLocation(World world, BlockPos pos, int height) {
		if (pos.getY() < 3 || pos.getY() + height + 5 > 255) return false;

		for (int y = 1; y <= height; y++) {
			if (!isReplaceable(world, pos.up(y))) return false;
		}
		return true;
	}

	public boolean isReplaceable(World world, BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		return block.isAir(state, world, pos) || block.isLeaves(state, world, pos) ||
				block.canBeReplacedByLeaves(state, world, pos) || block instanceof BlockBush;
	}

	private void buildTrunk(World world, Random rand, BlockPos startPos, int height) {
		for (int y = 0; y < height; y++) {
			placeLogAt(world, startPos.up(y));
		}
	}

	private List<BranchInfo> buildBranches(World world, Random rand, BlockPos center, int trunkHeight) {
		List<BranchInfo> localBranches = new ArrayList<>();
		BlockPos branchBase = center.up(trunkHeight - 1);

		int numBranches = 2 + rand.nextInt(3);
		for (int n = 0; n < numBranches; n++) {
			int branchLength = 3 + rand.nextInt(3);
			int branchHeight = 2 + rand.nextInt(2);
			int branchAngle = rand.nextInt(360);

			double xDir = Math.cos(Math.toRadians(branchAngle));
			double zDir = Math.sin(Math.toRadians(branchAngle));

			BlockPos[] bezierPath = MathUtils.getQuadBezierArray(
					BlockPos.ORIGIN,
					new BlockPos(branchLength / 2, branchHeight, 0),
					new BlockPos(branchLength, branchHeight - 1, 0)
			);

			for (int i = 0; i < bezierPath.length; i++) {
				BlockPos pathPos = bezierPath[i];
				BlockPos currentLogPos = branchBase.add((int)Math.round(pathPos.getX() * xDir), pathPos.getY(), (int)Math.round(pathPos.getX() * zDir));

				placeLogAt(world, currentLogPos);

				if (i == bezierPath.length - 1) {
					localBranches.add(new BranchInfo(currentLogPos, branchAngle));
				}
			}
		}
		return localBranches;
	}

	private void buildCanopy(World world, Random rand, BlockPos pos, List<BranchInfo> branchEnds) {
		for (BranchInfo branch : branchEnds) {
			int leafRadius = 3 + rand.nextInt(2);

			for (int x = -leafRadius; x <= leafRadius; x++) {
				for (int z = -leafRadius; z <= leafRadius; z++) {
					double distanceSq = (x * x) + (z * z);
					double maxDist = leafRadius * leafRadius;

					if (distanceSq <= maxDist) {
						if (distanceSq < maxDist - rand.nextInt(3)) {
							placeLeafAt(world, branch.endPoint.add(x, 0, z));

							if (distanceSq > maxDist * 0.6 && rand.nextInt(3) == 0) {
								placeLeafAt(world, branch.endPoint.add(x, -1, z));
							}
						}
					}

					if (distanceSq <= (leafRadius - 1) * (leafRadius - 1)) {
						if (rand.nextBoolean()) {
							placeLeafAt(world, branch.endPoint.add(x, 1, z));
						}
					}
				}
			}
		}
	}

	private static class BranchInfo {
		BlockPos endPoint;
		int rotationAngle;
		public BranchInfo(BlockPos endPoint, int rotationAngle) {
			this.endPoint = endPoint;
			this.rotationAngle = rotationAngle;
		}
	}
}