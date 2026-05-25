package fluke.stygian.world.biomes;

import com.google.common.base.Predicate;
import fluke.stygian.block.ModBlocks;
import fluke.stygian.config.Configs;
import fluke.stygian.world.feature.*;
import git.jbredwards.nether_api.api.biome.IEndBiome;
import git.jbredwards.nether_api.api.registry.INetherAPIRegistryListener;
import git.jbredwards.nether_api.api.world.INetherAPIChunkGenerator;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeEndDecorator;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenBush;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Random;

public class BiomeEndVolcano extends Biome implements IEndBiome, INetherAPIRegistryListener {
	public static BiomeProperties properties = new BiomeProperties("Acidic Plains");
	private static final IBlockState AIR = Blocks.AIR.getDefaultState();
	private static final IBlockState END_OBSIDIAN = ModBlocks.endObsidian.getDefaultState();
	private static final IBlockState END_MAGMA = ModBlocks.endMagma.getDefaultState();

	private static final IBlockState END_DEAD_DIRT = ModBlocks.endDeadDirt.getDefaultState();
	private static final IBlockState END_DEAD_STONE = ModBlocks.endDeadStone.getDefaultState();

	public static final WorldGenDeadBoulder END_BOULDER = new WorldGenDeadBoulder(ModBlocks.endDeadCobblestone.getDefaultState(), END_OBSIDIAN);

	public WorldGenerator endDeadBush = new WorldGenBush(ModBlocks.endDeadBush);
	public WorldGenerator endAcidDeadBush = new WorldGenBush(ModBlocks.endAcidDeadBush);
	public WorldGenerator endHangingRoots = new WorldGenBush(ModBlocks.endDeadHangingRoot);
	public WorldGenerator endVolcano = new WorldGenEndVolcano(END_OBSIDIAN, END_MAGMA, ModBlocks.endAcid.getDefaultState());
	public WorldGenerator endSmallLake = new WorldGenLakes(ModBlocks.endAcid);
	public WorldGenerator endLargeLake = new WorldGenEndLake(ModBlocks.endAcid.getDefaultState(), END_OBSIDIAN);
	public WorldGenerator endCactus = new WorldGenEndCactus();
	public WorldGenerator magmaPatch = new WorldGenSurfacePatch(END_MAGMA, END_DEAD_DIRT, 1);
	public WorldGenerator coalGen = new WorldGenMinable(ModBlocks.endDeadCoalOre.getDefaultState(), 17, new Predicate<IBlockState>() {
		public boolean apply(IBlockState state) {
			return state != null && state.getBlock() == ModBlocks.endDeadStone;
		}
	});
	public WorldGenerator obsidianGen = new WorldGenMinable(END_OBSIDIAN, 55, new Predicate<IBlockState>() {
		public boolean apply(IBlockState state) {
			return state != null && (state.getBlock() == ModBlocks.endDeadStone || state.getBlock() == ModBlocks.endDeadDirt);
		}
	});
	private Random randy;

	static {
		properties.setTemperature(Biomes.SKY.getDefaultTemperature());
		properties.setRainfall(Biomes.SKY.getRainfall());
		properties.setRainDisabled();
	}

	public BiomeEndVolcano() {
		super(properties);
		this.spawnableMonsterList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
		this.spawnableCaveCreatureList.clear();
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEnderman.class, 40, 1, 4));

		this.topBlock = END_DEAD_DIRT;
		this.fillerBlock = END_DEAD_DIRT;

		this.decorator = new BiomeEndDecorator();
		randy = new Random();
	}

	@Override
	public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
		this.generateBiomeTerrain(worldIn, rand, chunkPrimerIn, x, z, noiseVal);

		for (int y = 255; y >= 0; --y) {
			IBlockState iblockstate = chunkPrimerIn.getBlockState(x & 15, y, z & 15);

			if (iblockstate.getBlock() == Blocks.STONE || iblockstate.getBlock() == Blocks.END_STONE) {
				chunkPrimerIn.setBlockState(x & 15, y, z & 15, END_DEAD_STONE);
			}
		}
	}

	public boolean generateIslands(@Nonnull INetherAPIChunkGenerator chunkGenerator, int chunkX, int chunkZ, float islandHeight) {
		return false;
	}

	public boolean generateChorusPlants(@Nonnull INetherAPIChunkGenerator chunkGenerator, int chunkX, int chunkZ, float islandHeight) {
		return false;
	}

	public boolean generateEndCity(@Nonnull INetherAPIChunkGenerator chunkGenerator, int chunkX, int chunkZ, int islandHeight) {
		return false;
	}

	@Override
	public BiomeDecorator createBiomeDecorator() {
		return new BiomeDecoratorEndBiomes();
	}

	@SideOnly(Side.CLIENT)
	public int getSkyColorByTemp(float currentTemperature) {
		return 0;
	}

	public void decorate(World world, Random rand, BlockPos pos) {
		if(randy.nextInt(Configs.worldgen.volcanoFrequency ) == 0) {
			int yHeight = getEndSurfaceHeight(world, pos.add(16, 0, 16), 50, 70, null);
			if(yHeight > 0)
				endVolcano.generate(world, rand, pos.add(16, yHeight+1, 16));
		}

		if(randy.nextInt(10) == 0) {
			int randX = rand.nextInt(8)+12;
			int randZ = rand.nextInt(8)+12;
			int yHeight = getEndSurfaceHeight(world, pos.add(randX, 0, randZ), 60, 70, null);
			if(yHeight > 0)
				endLargeLake.generate(world, rand, pos.add(randX, yHeight, randZ));
		}
		for (int i = 0; i < 20; ++i) {
			int x = rand.nextInt(16) + 8;
			int z = rand.nextInt(16) + 8;
			int y = rand.nextInt(100) + 10;
			coalGen.generate(world, rand, pos.add(x, y, z));
		}

		for(int n=0; n<2; n++) {
			if(randy.nextInt(3) == 0) {
				int randX = randy.nextInt(16) + 8;
				int randZ = randy.nextInt(16) + 8;
				int yHeight = getEndSurfaceHeight(world, pos.add(randX, 0, randZ), 30, 70, null);
				if (yHeight > 0)
					endDeadBush.generate(world, rand, pos.add(randX, yHeight, randZ));
			}
		}

		if(randy.nextInt(10) == 0) {
			int randX = randy.nextInt(16) + 8;
			int randZ = randy.nextInt(16) + 8;

			int yHeight = getEndSurfaceHeight(world, pos.add(randX, 0, randZ), 30, 70, null);

			if (yHeight > 0) {
				endAcidDeadBush.generate(world, rand, pos.add(randX, yHeight, randZ));
			}
		}

		for(int n=0; n<2; n++) {
			int randX = randy.nextInt(16)+8;
			int randZ = randy.nextInt(16)+8;
			int yHeight = getEndSurfaceHeight(world, pos.add(randX, 0, randZ), 50, 70, null);
			if(yHeight > 0)
				endCactus.generate(world, rand, pos.add(randX, yHeight, randZ));
		}

		if(randy.nextInt(8) == 0) {
			int randX = rand.nextInt(16)+8;
			int randZ = rand.nextInt(16)+8;
			int yHeight = getEndSurfaceHeight(world, pos.add(randX, 0, randZ), 60, 70, null);
			if(yHeight > 0)
				endSmallLake.generate(world, rand, pos.add(randX, yHeight, randZ));
		}

		if(randy.nextInt(8) == 0) {
			magmaPatch.generate(world, rand, pos.add(8, 0, 8));
		}

		if (randy.nextInt(2) == 0) {
			int xOff = randy.nextInt(16);
			int zOff = randy.nextInt(16);
			BlockPos targetPos = pos.add(xOff, 0, zOff);

			int yHeight = getEndSurfaceHeight(world, pos.add(xOff, 0, zOff), 60, 70, null);

			if (yHeight > 0)
			{
				if (randy.nextInt(10) == 0) {
					world.setBlockState(targetPos.up(yHeight + 1), END_OBSIDIAN);
				} else {
					END_BOULDER.generate(world, randy, targetPos.up(yHeight + 1));
				}
			}
		}

		for (int i = 0; i < 4; i++) {
			int rx = rand.nextInt(16) + 8;
			int rz = rand.nextInt(16) + 8;

			for (int ry = 40; ry < 120; ry++) {
				BlockPos checkPos = pos.add(rx, ry, rz);

				if (world.isAirBlock(checkPos)) {
					IBlockState stateAbove = world.getBlockState(checkPos.up());
					if (stateAbove == END_DEAD_DIRT || stateAbove == END_DEAD_STONE) {

						if (rand.nextFloat() < 0.25f) {
							endHangingRoots.generate(world, rand, checkPos);
							break;
						}
					}
				}
			}
		}

		for (int i = 0; i < 5; ++i) {
			int x = rand.nextInt(16) + 8;
			int z = rand.nextInt(16) + 8;
			int y = rand.nextInt(70) + 10;
			obsidianGen.generate(world, rand, pos.add(x, y, z));
		}
		super.decorate(world, rand, pos);
	}

	@Override
	public void buildSurface(@Nonnull INetherAPIChunkGenerator chunkGenerator, int chunkX, int chunkZ, @Nonnull ChunkPrimer primer, int x, int z, double terrainNoise) {
		int currDepth = -1;
		for(int y = chunkGenerator.getWorld().getActualHeight() - 1; y >= 0; --y) {
			final IBlockState here = primer.getBlockState(x, y, z);
			if(here.getMaterial() == Material.AIR) currDepth = -1;
			else if(here.getBlock() == Blocks.END_STONE) {
				if(currDepth == -1) {
					currDepth = 40 + chunkGenerator.getRand().nextInt(2);
					primer.setBlockState(x, y, z, topBlock);
				}
				else if(currDepth > 0) {
					--currDepth;
					fillerBlock = ModBlocks.endDeadStone.getDefaultState();
					primer.setBlockState(x, y, z, fillerBlock);
				}
			}
		}
	}


	private int getEndSurfaceHeight(World world, BlockPos pos, int min, int max, IBlockState specificBlock)
	{
		int maxY = max;
		int minY = min;
		int currentY = maxY;
		IBlockState state;

		while(currentY >= minY)
		{
			state = world.getBlockState(pos.add(0, currentY, 0));
			if(state != AIR)
				if(specificBlock == null || state == specificBlock)
					return currentY;

			currentY--;
		}
		return 0;
	}
}