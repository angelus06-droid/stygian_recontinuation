package fluke.stygian.world.feature;

import fluke.stygian.util.FastNoise;
import fluke.stygian.util.FastNoise.NoiseType;
import net.minecraft.block.BlockBone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenBoneSpike extends WorldGenerator {
	private final IBlockState boneVertical = Blocks.BONE_BLOCK.getDefaultState()
			.withProperty(BlockBone.AXIS, EnumFacing.Axis.Y);

	private final FastNoise noise;

	public WorldGenBoneSpike() {
		this.noise = new FastNoise();
		this.noise.SetNoiseType(NoiseType.Perlin);
		this.noise.SetFrequency(0.15F);
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {
		if (world.isAirBlock(pos.down())) {
			return false;
		}

		int height = 12 + rand.nextInt(13);

		float baseRadius = 1.8F + rand.nextFloat() * 1.0F;

		for (int y = -3; y < height; y++) {

			float progress = (float) (y + 3) / (float) (height + 3);

			float currentRadius = baseRadius * (1.0F - (float)Math.pow(progress, 0.7));

			if (currentRadius < 0.1F && y > 0) break;

			int intRadius = (int) Math.ceil(currentRadius) + 1;

			for (int x = -intRadius; x <= intRadius; x++) {
				for (int z = -intRadius; z <= intRadius; z++) {

					double n = (noise.GetNoise(pos.getX() + x, y * 2, pos.getZ() + z) + 1) * 0.5;
					double distSq = (x * x + z * z);

					if (distSq < (currentRadius * currentRadius * (n * 0.4 + 0.8))) {
						BlockPos placePos = pos.add(x, y, z);

						if (world.getBlockState(placePos).getBlock().isReplaceable(world, placePos) || y <= 0) {
							world.setBlockState(placePos, boneVertical, 2);
						}
					}
				}
			}
		}
		return true;
	}
}