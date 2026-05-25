package fluke.stygian.world.feature;

import fluke.stygian.block.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenBlueCactus extends WorldGenerator {
	private final static IBlockState END_CACTUS = ModBlocks.endBlueCactus.getDefaultState();
	private final static IBlockState END_SHIREN = ModBlocks.endShiren.getDefaultState();

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {
		if (!world.isAirBlock(pos)) {
			return false;
		}

		if (world.getBlockState(pos.down()).getBlock() != ModBlocks.endSand) {
			return false;
		}

		int height = 1 + rand.nextInt(5);
		BlockPos lastPos = pos;

		for (int y = 0; y < height; ++y) {
			BlockPos currentPos = pos.up(y);

			if (world.isAirBlock(currentPos)) {
				world.setBlockState(currentPos, END_CACTUS, 2);
				lastPos = currentPos;
			} else {
				break;
			}
		}

		BlockPos flowerPos = lastPos.up();
		if (world.isAirBlock(flowerPos) && rand.nextFloat() < 0.50f) {
			world.setBlockState(flowerPos, END_SHIREN, 2);
		}

		return true;
	}
}