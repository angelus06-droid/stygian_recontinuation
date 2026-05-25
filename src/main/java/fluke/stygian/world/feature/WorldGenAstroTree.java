package fluke.stygian.world.feature;

import fluke.stygian.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockVine;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class WorldGenAstroTree extends WorldGenAbstractTree {
	private static final IBlockState LOG = ModBlocks.endAstroLog.getDefaultState();
	private static final IBlockState LEAF = ModBlocks.endAstroLeaves.getDefaultState();

	private static final int MIN_HEIGHT = 6;
	private static final int MAX_HEIGHT = 10;

	public WorldGenAstroTree(boolean notify) {
		super(notify);
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {
		int height = rand.nextInt(MAX_HEIGHT - MIN_HEIGHT + 1) + MIN_HEIGHT;

		if (!canGrow(world, pos, height)) return false;

		for (int y = 0; y < height; y++) {
			this.setBlockAndNotifyAdequately(world, pos.up(y), LOG);
		}

		BlockPos top = pos.up(height - 1);

		generateLeafSphere(world, top.up(1), 3.5, rand);

		generateLeafSphere(world, top.add(2, -1, 0), 2.5, rand);
		generateLeafSphere(world, top.add(-2, -1, 0), 2.5, rand);
		generateLeafSphere(world, top.add(0, -1, 2), 2.5, rand);
		generateLeafSphere(world, top.add(0, -1, -2), 2.5, rand);

		for (int i = 0; i < 3; i++) {
			BlockPos randomPos = top.add(rand.nextInt(5) - 2, rand.nextInt(3) - 2, rand.nextInt(5) - 2);
			generateLeafSphere(world, randomPos, 1.5 + rand.nextDouble(), rand);
		}

		return true;
	}

	private void generateLeafSphere(World world, BlockPos center, double radius, Random rand) {
		int ceilRadius = (int) Math.ceil(radius);

		for (int x = -ceilRadius; x <= ceilRadius; x++) {
			for (int y = -ceilRadius; y <= ceilRadius; y++) {
				for (int z = -ceilRadius; z <= ceilRadius; z++) {
					double distance = Math.sqrt(x * x + y * y + z * z);

					if (distance <= radius - (rand.nextDouble() * 0.8)) {
						BlockPos leafPos = center.add(x, y, z);
						IBlockState state = world.getBlockState(leafPos);

						if (state.getBlock().isAir(state, world, leafPos) || state.getBlock().isReplaceable(world, leafPos)) {
							this.setBlockAndNotifyAdequately(world, leafPos, LEAF);
						}
					}
				}
			}
		}
	}

	private boolean canGrow(World world, BlockPos pos, int height) {
		for (int y = pos.getY(); y <= pos.getY() + height + 2; y++) {
			int checkRadius = 3;
			for (int x = pos.getX() - checkRadius; x <= pos.getX() + checkRadius; x++) {
				for (int z = pos.getZ() - checkRadius; z <= pos.getZ() + checkRadius; z++) {
					if (y >= 0 && y < 256) {
						if (!this.isReplaceable(world, new BlockPos(x, y, z))) {
							return false;
						}
					} else {
						return false;
					}
				}
			}
		}
		return true;
	}

	@Override
	protected boolean canGrowInto(Block blockType) {
		return super.canGrowInto(blockType) || blockType == Blocks.END_STONE || blockType == ModBlocks.endLunarGrass;
	}

	public boolean isReplaceable(World world, BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		return block.isAir(state, world, pos) || block.isLeaves(state, world, pos) ||
				block instanceof BlockBush || block instanceof BlockVine;
	}
}