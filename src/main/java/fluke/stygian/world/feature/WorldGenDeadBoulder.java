package fluke.stygian.world.feature;

import fluke.stygian.util.FastNoise;
import fluke.stygian.util.FastNoise.NoiseType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldGenDeadBoulder extends WorldGenerator {
	private final IBlockState mainBlock;
	private final IBlockState secondaryBlock;
	private final FastNoise perlin;
	private final static IBlockState AIR = Blocks.AIR.getDefaultState();

	public WorldGenDeadBoulder(IBlockState main, IBlockState secondary) {
		this.mainBlock = main;
		this.secondaryBlock = secondary;
		perlin = new FastNoise();
		perlin.SetNoiseType(NoiseType.Perlin);
		perlin.SetFrequency(0.15F);
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {

		int radius = 2 + rand.nextInt(3);

		int maxHeight = radius + rand.nextInt(3);

		List<BlockPos> firstLayer = getFirstLayer(world, pos, radius);

		if(firstLayer == null)
			return false;

		for(BlockPos baseBlock : firstLayer) {
			world.setBlockState(baseBlock, mainBlock);
		}

		for(int y = 1; y < maxHeight; y++) {
			float ratio = (float)y / (float)maxHeight;
			double layerRadius = radius * Math.cos(ratio * Math.PI / 2);
			double maxDist = layerRadius * layerRadius;

			for(int x = (int)-layerRadius - 1; x <= (int)layerRadius + 1; x++)
			{
				for(int z = (int)-layerRadius - 1; z <= (int)layerRadius + 1; z++)
				{
					double xDist = x * x;
					double zDist = z * z;

					double noiseMod = (perlin.GetNoise(pos.getX() + x, y * 2, pos.getZ() + z) + 1) / 2.0 + 0.5;
					double noisyDist = (xDist * noiseMod) + (zDist * noiseMod);

					if(noisyDist > maxDist)
						continue;

					IBlockState stateToPlace = (rand.nextInt(10) == 0) ? secondaryBlock : mainBlock;

					BlockPos currentPos = pos.add(x, y, z);
					world.setBlockState(currentPos, stateToPlace);
				}
			}
		}
		return true;
	}

	public List<BlockPos> getFirstLayer(World world, BlockPos pos, int radius) {
		List<BlockPos> firstLayer = new ArrayList<>();
		double maxDist = radius * radius;

		for(int x = -radius; x <= radius; x++) {
			for(int z = -radius; z <= radius; z++) {
				double xDist = x * x;
				double zDist = z * z;
				double noiseMod = (perlin.GetNoise(pos.getX() + x, 0, pos.getZ() + z) + 1) / 2.0 + 0.5;
				double noisyDist = xDist * noiseMod + zDist * noiseMod;

				if(noisyDist > maxDist)
					continue;

				BlockPos checkPos = pos.add(x, 0, z).down();
				if(world.getBlockState(checkPos) != AIR) {
					firstLayer.add(pos.add(x, 0, z));
				} else if(world.getBlockState(checkPos.down()) != AIR) {
					firstLayer.add(pos.add(x, 0, z));
					firstLayer.add(pos.add(x, -1, z));
				} else if(world.getBlockState(checkPos.down().down()) != AIR) {
					firstLayer.add(pos.add(x, 0, z));
					firstLayer.add(pos.add(x, -1, z));
					firstLayer.add(pos.add(x, -2, z));
				} else {
					return null;
				}
			}
		}
		return firstLayer;
	}
}