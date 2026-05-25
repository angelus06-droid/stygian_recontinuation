package fluke.stygian.world.biomes;

import fluke.stygian.block.ModBlocks;
import fluke.stygian.world.feature.WorldGenViscousTree;
import git.jbredwards.nether_api.api.registry.INetherAPIRegistryListener;
import git.jbredwards.nether_api.api.world.INetherAPIChunkGenerator;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.feature.WorldGenBush;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Random;

public class BiomeEndBacterialValley extends Biome implements INetherAPIRegistryListener
{
	public static BiomeProperties properties = new BiomeProperties("Bacterial Valley");
	private static final IBlockState AIR = Blocks.AIR.getDefaultState();
	private static final IBlockState END_STONE = Blocks.END_STONE.getDefaultState();
	private static final IBlockState END_SNARE_GRASS = ModBlocks.endSnareGrass.getDefaultState();

	public WorldGenerator endSnareTallGrass = new WorldGenBush(ModBlocks.endSnareTallGrass);
	public WorldGenerator endSnareBells = new WorldGenBush(ModBlocks.endSnareBells);
	public WorldGenerator endCell = new WorldGenBush(ModBlocks.endCell);
	public WorldGenerator endViscousTree;

	static {
		properties.setTemperature(Biomes.SKY.getDefaultTemperature());
		properties.setRainfall(0.0F);
		properties.setRainDisabled();
		properties.setBaseBiome(String.valueOf(Biomes.SKY));
	}

	public BiomeEndBacterialValley() {
		super(properties);
		this.spawnableMonsterList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
		this.spawnableCaveCreatureList.clear();

		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEnderman.class, 40, 1, 4));

		this.topBlock = END_SNARE_GRASS;
		this.fillerBlock = END_STONE;

		this.endViscousTree = new WorldGenViscousTree(true);
	}

	@Override
	public BiomeDecorator createBiomeDecorator() {
		return new BiomeDecoratorEndBiomes();
	}

	@SideOnly(Side.CLIENT)
	public int getSkyColorByTemp(float currentTemperature) {
		return 0;
	}

	@Override
	public void decorate(World world, Random rand, BlockPos pos) {
		for(int x = 0; x < 16; x++) {
			for(int z = 0; z < 16; z++) {
				int plantRoll = rand.nextInt(150);

				if(plantRoll <= 5) {
					int terrainHeight = getEndSurfaceHeight(world, pos.add(x+8, 0, z+8), 30 - rand.nextInt(5), 70, END_SNARE_GRASS);

					if(terrainHeight > 0) {
						BlockPos plantPos = pos.add(x+8, terrainHeight + 1, z+8);

						if(plantRoll == 1) {
							endSnareBells.generate(world, rand, plantPos);
						}
						else if(plantRoll == 2 && rand.nextInt(4) == 0) {
							endCell.generate(world, rand, plantPos);
						}
						else {
							endSnareTallGrass.generate(world, rand, plantPos);
						}
					}
				}
			}
		}

		for (int i = 0; i < 3; i++) {
			if (rand.nextInt(3) <= 1) {
				int xOff = rand.nextInt(16) + 8;
				int zOff = rand.nextInt(16) + 8;

				BlockPos targetPos = world.getHeight(pos.add(xOff, 0, zOff));

				if (!world.isAirBlock(targetPos)) {
					targetPos = findAirAbove(world, targetPos);
				}

				if (world.getBlockState(targetPos.down()) == END_SNARE_GRASS) {
					endViscousTree.generate(world, rand, targetPos);
				}
			}
		}

		super.decorate(world, rand, pos);
	}

	public boolean generateIslands(@Nonnull INetherAPIChunkGenerator chunkGenerator, int chunkX, int chunkZ, float islandHeight) {
		return false;
	}

	public boolean generateChorusPlants(@Nonnull INetherAPIChunkGenerator chunkGenerator, int chunkX, int chunkZ, float islandHeight) {
		return false;
	}

	public boolean generateEndCity(@Nonnull INetherAPIChunkGenerator chunkGenerator, int chunkX, int chunkZ, int islandHeight) {
		return true;
	}

	private BlockPos findAirAbove(World world, BlockPos pos) {
		BlockPos current = pos;
		while (!world.isAirBlock(current) && current.getY() < 255) {
			current = current.up();
		}
		return current;
	}

	private int getEndSurfaceHeight(World world, BlockPos pos, int min, int max, IBlockState specificBlock) {
		int currentY = max;
		while(currentY >= min) {
			IBlockState state = world.getBlockState(pos.add(0, currentY, 0));
			if(state != AIR) {
				if(specificBlock == null || state == specificBlock)
					return currentY;
			}
			currentY--;
		}
		return 0;
	}
}