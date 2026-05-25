package fluke.stygian.world.feature;

import fluke.stygian.block.ModBlocks;
import fluke.stygian.util.MathUtils;
import net.minecraft.block.*;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldGenEnderCanopy extends WorldGenAbstractTree {
	private static final IBlockState LOG = ModBlocks.endLog.getDefaultState();
	private static final IBlockState LEAF = ModBlocks.endLeaves.getDefaultState();
	private static final IBlockState END_GRASS = ModBlocks.endGrass.getDefaultState();
	private static final int MIN_TRUNK_HEIGHT = 15;
	private static final int MAX_TRUNK_HEIGHT = 25;
	private static final int TRUNK_CORE = 5;

	public WorldGenEnderCanopy(boolean notify) {
		super(notify);
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {
		IBlockState inState = world.getBlockState(pos);
		if (inState != END_GRASS && inState != Blocks.END_STONE.getDefaultState()) {
			pos = pos.down();
		}

		int randomValue = rand.nextInt(MAX_TRUNK_HEIGHT - MIN_TRUNK_HEIGHT + 1);
		int trunkHeight = MIN_TRUNK_HEIGHT + randomValue;

		if (!isValidGenLocation(world, pos, rand)) {
			return false;
		}

		buildTrunk(world, rand, pos, trunkHeight);

		// MODIFICACIÓN: La lista se crea aquí localmente para evitar ConcurrentModificationException
		List<BranchInfo> branches = buildBranches(world, rand, pos, trunkHeight);
		buildCanopy(world, rand, pos, branches);

		return true;
	}

	private void placeLogAt(World worldIn, BlockPos pos) {
		worldIn.setBlockState(pos, LOG);
	}

	private void placeLeafAt(World worldIn, BlockPos pos) {
		IBlockState state = worldIn.getBlockState(pos);
		if (state.getBlock().isAir(state, worldIn, pos) || state.getBlock().isLeaves(state, worldIn, pos) || state == ModBlocks.endVine.getDefaultState()) {
			worldIn.setBlockState(pos, LEAF);
		}
	}

	public boolean isValidGenLocation(World world, BlockPos pos, Random rand) {
		int randomValue = rand.nextInt(MAX_TRUNK_HEIGHT - MIN_TRUNK_HEIGHT + 1);
		int trunkHeight = MathHelper.clamp(MIN_TRUNK_HEIGHT + randomValue, MIN_TRUNK_HEIGHT, MAX_TRUNK_HEIGHT);

		if (pos.getY() < 3 || pos.getY() + trunkHeight + 22 > 255) {
			return false;
		}

		int trunkRadius = 1;
		for (int y = 3; y < trunkHeight; y++) {
			for (int x = -trunkRadius; x <= trunkRadius; x++) {
				for (int z = -trunkRadius; z <= trunkRadius; z++) {
					if (!isReplaceable(world, pos.add(x, y, z))) return false;
				}
			}
		}
		return true;
	}

	public boolean isReplaceable(World world, BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		return block.isAir(state, world, pos) || block.isLeaves(state, world, pos) || block.isWood(world, pos) ||
				canGrowInto(block) || block == Blocks.END_STONE || block instanceof BlockBush ||
				block instanceof BlockVine || block.canBeReplacedByLeaves(state, world, pos);
	}

	private void buildCanopy(World world, Random rand, BlockPos pos, List<BranchInfo> branchEnds) {
		List<BlockPos> possibleVineSpots = new ArrayList<>();

		for (BranchInfo branch : branchEnds) {
			double xAngleTranslation = Math.cos(Math.toRadians(branch.rotationAngle));
			double zAngleTranslation = Math.sin(Math.toRadians(branch.rotationAngle));
			int xAnglizer = (int) Math.round(xAngleTranslation);
			int zAnglizer = (int) Math.round(zAngleTranslation);

			for (int y = 0; y <= 2; y++) {
				int canopyRadius = 8;
				if (y == 0) canopyRadius -= 3;
				else if (y == 2) canopyRadius -= 2;

				int maxDist = canopyRadius * canopyRadius;
				int lesserMaxDist = (canopyRadius - 1) * canopyRadius;

				for (int x = -canopyRadius; x <= canopyRadius; x++) {
					for (int z = -canopyRadius; z <= canopyRadius; z++) {
						double xDist = x * x;
						double zDist = z * z;

						double ratio = (Math.abs(x) > Math.abs(z)) ?
								(z * zAnglizer) / (x * xAnglizer + 0.001) :
								(x * xAnglizer) / (z * zAnglizer + 0.001);

						ratio = 1 - (ratio + 1.0) / 2.0;
						double squishFactor = MathHelper.clampedLerp(1.0, 1.55, ratio);
						xDist *= squishFactor;
						zDist *= squishFactor;

						int distMax = rand.nextBoolean() ? lesserMaxDist : maxDist;

						if (xDist + zDist < distMax) {
							placeLeafAt(world, branch.endPoint.add(x, y, z));
							if (y < 2 && xDist + zDist > lesserMaxDist && rand.nextInt(4) == 0) {
								possibleVineSpots.add(branch.endPoint.add(x, y, z));
							}
						}
					}
				}
			}
		}

		for (BlockPos vinePos : possibleVineSpots) {
			placeVine(world, rand, vinePos);
		}
	}

	private void placeVine(World world, Random rand, BlockPos pos) {
		int length = rand.nextInt(22);
		if (rand.nextInt(1) == 0) {
			if(world.isAirBlock(pos.west())) addHangingVine(world, pos.west(), BlockVine.EAST, length);
			if(world.isAirBlock(pos.east())) addHangingVine(world, pos.east(), BlockVine.WEST, length);
			if(world.isAirBlock(pos.north())) addHangingVine(world, pos.north(), BlockVine.SOUTH, length);
			if(world.isAirBlock(pos.south())) addHangingVine(world, pos.south(), BlockVine.NORTH, length);
		}
	}

	private void addHangingVine(World world, BlockPos pos, PropertyBool prop, int length) {
		IBlockState vineState = ModBlocks.endVine.getDefaultState().withProperty(prop, true);
		this.setBlockAndNotifyAdequately(world, pos, vineState);
		for (BlockPos next = pos.down(); world.isAirBlock(next) && length > 0; length--) {
			this.setBlockAndNotifyAdequately(world, next, vineState);
			next = next.down();
		}
	}

	private void buildTrunk(World world, Random rand, BlockPos center, int height) {
		for (int x = -TRUNK_CORE; x <= TRUNK_CORE; x++) {
			for (int z = -TRUNK_CORE; z <= TRUNK_CORE; z++) {
				int colHeight;
				if (Math.abs(x) <= 1 && Math.abs(z) <= 1) {
					colHeight = height;
				} else {
					colHeight = (Math.abs(x) <= Math.abs(z)) ?
							18 - Math.abs(x) - Math.abs(z) * 3 - rand.nextInt(2) :
							18 - Math.abs(x) * 3 - Math.abs(z) - rand.nextInt(2);
				}
				for (int y = 0; y < colHeight; y++) {
					placeLogAt(world, center.add(x, y, z));
				}
			}
		}
	}

	private List<BranchInfo> buildBranches(World world, Random rand, BlockPos center, int trunkHeight) {
		// MODIFICACIÓN: Lista local para evitar que varios hilos la corrompan
		List<BranchInfo> localBranches = new ArrayList<>();
		BlockPos branchBase = center.up(trunkHeight - 3);

		for (int n = 0; n < 7; n++) {
			if (rand.nextInt(15) == 0) continue;

			int branchLength, branchHeight, branchAngle;
			int dropWeight = 2 + rand.nextInt(3);

			if (n < 4) {
				branchLength = 15 + rand.nextInt(5);
				branchHeight = 7 + rand.nextInt(4);
				branchAngle = (45 + 90 * n) + (rand.nextInt(16) - 8);
			} else {
				branchLength = 8 + rand.nextInt(6);
				branchHeight = 12 + rand.nextInt(3);
				branchAngle = rand.nextInt(360);
			}

			double xDir = Math.cos(Math.toRadians(branchAngle));
			double zDir = Math.sin(Math.toRadians(branchAngle));

			BlockPos[] bezierPath = MathUtils.getQuadBezierArray(BlockPos.ORIGIN, new BlockPos(branchLength / 2, branchHeight + 2, 0), new BlockPos(branchLength, branchHeight - dropWeight, 0));
			BlockPos lastPos = null;

			for (int i = 0; i < bezierPath.length; i++) {
				BlockPos pathPos = bezierPath[i];
				BlockPos currentLogPos = branchBase.add((int)Math.round(pathPos.getX() * xDir), pathPos.getY(), (int)Math.round(pathPos.getX() * zDir));

				if (lastPos != null && currentLogPos.distanceSq(lastPos) > 1.0) fillGap(world, lastPos, currentLogPos);
				placeLogAt(world, currentLogPos);

				double progress = (double) i / bezierPath.length;
				if (progress < 0.25) {
					placeLogAt(world, currentLogPos.down());
					if (progress < 0.1) placeLogAt(world, currentLogPos.add(zDir > 0.5 ? 1 : 0, 0, xDir > 0.5 ? 1 : 0));
				}
				lastPos = currentLogPos;

				if (i == bezierPath.length - 1) {
					localBranches.add(new BranchInfo(currentLogPos, branchAngle));
				}
			}
		}
		return localBranches;
	}

	private void fillGap(World world, BlockPos from, BlockPos to) {
		placeLogAt(world, new BlockPos((from.getX() + to.getX()) / 2, (from.getY() + to.getY()) / 2, (from.getZ() + to.getZ()) / 2));
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