package fluke.stygian.world.biomes;

import fluke.stygian.block.ModBlocks;
import fluke.stygian.world.feature.WorldGenBlueCactus;
import fluke.stygian.world.feature.WorldGenBoneSpike;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.feature.WorldGenBush;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BiomeEndDesert extends Biome {
	public static BiomeProperties properties = new BiomeProperties("Algea Desert");
	private static final IBlockState AIR = Blocks.AIR.getDefaultState();
	private static final IBlockState END_SAND = ModBlocks.endSand.getDefaultState();

	protected static final WorldGenBoneSpike BONE_SPIKE = new WorldGenBoneSpike();
	protected static final WorldGenLakes LAKE_WATER = new WorldGenLakes(Blocks.WATER);
	protected static final WorldGenBlueCactus BLUE_CACTUS = new WorldGenBlueCactus();
	protected static final WorldGenBush DEAD_GRASS = new WorldGenBush(ModBlocks.endDeadGrass);
	protected static final WorldGenBush ROTTEN_BUSH = new WorldGenBush(ModBlocks.endDeadBush);
	static {
		properties.setTemperature(Biomes.SKY.getDefaultTemperature());
		properties.setRainfall(Biomes.SKY.getRainfall());
		properties.setRainDisabled();
		properties.setBaseBiome(String.valueOf(Biomes.SKY));
	}
	public BiomeEndDesert() {
		super(properties);
		this.spawnableMonsterList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEnderman.class, 40, 1, 4));

		this.topBlock = END_SAND;
		this.fillerBlock = END_SAND;
	}

	@Override
	public BiomeDecorator createBiomeDecorator() {
		return new BiomeDecoratorEndBiomes();
	}

	@Override
	public void decorate(World world, Random rand, BlockPos pos) {
		for (int i = 0; i < 3; i++) {
			if (rand.nextInt(3) == 0) {
				BlockPos target = getSurfacePos(world, pos, rand, 30, 70);
				if (world.getBlockState(target).getBlock() == ModBlocks.endSand) {
					DEAD_GRASS.generate(world, rand, target.up());
				}
			}
		}

		for (int i = 0; i < 1; i++) {
			if (rand.nextInt(10) == 0) {
				BlockPos target = getSurfacePos(world, pos, rand, 30, 70);
				if (world.getBlockState(target).getBlock() == ModBlocks.endSand) {
					ROTTEN_BUSH.generate(world, rand, target.up());
				}
			}
		}

		for (int i = 0; i < 3; ++i) {
			if (rand.nextInt(8) == 0) {
				BlockPos target = getSurfacePos(world, pos, rand, 50, 70);
				if (world.getBlockState(target).getBlock() == ModBlocks.endSand) {
					BLUE_CACTUS.generate(world, rand, target.up());
				}
			}
		}

		if (rand.nextInt(12) == 0) {
			BlockPos target = getSurfacePos(world, pos, rand, 40, 70);
			LAKE_WATER.generate(world, rand, target);
		}

		if (rand.nextInt(5) == 0) {
			BlockPos target = getSurfacePos(world, pos, rand, 30, 70);
			if (world.getBlockState(target).getBlock() == ModBlocks.endSand) {
				BONE_SPIKE.generate(world, rand, target.up());
			}
		}

		super.decorate(world, rand, pos);
	}

	private BlockPos getSurfacePos(World world, BlockPos pos, Random rand, int min, int max) {
		int x = rand.nextInt(16) + 8;
		int z = rand.nextInt(16) + 8;

		for (int y = max; y >= min; y--)
		{
			BlockPos checkPos = pos.add(x, y, z);
			if (world.getBlockState(checkPos) != AIR)
			{
				return checkPos;
			}
		}
		return pos.add(x, 0, z);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getSkyColorByTemp(float currentTemperature) {
		return 0;
	}
}