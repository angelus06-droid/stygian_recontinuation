package fluke.stygian.world.biomes;

import fluke.stygian.block.ModBlocks;
import fluke.stygian.config.Configs;
import fluke.stygian.world.feature.WorldGenEndPlant;
import fluke.stygian.world.feature.WorldGenEnderCanopy;
import fluke.stygian.world.feature.WorldGenSmallEnderCanopy;
import fluke.stygian.world.feature.WorldGenSurfacePatch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeEndDecorator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BiomeEndJungle extends Biome {
	public static BiomeProperties properties = new BiomeProperties("Stygian Growth");
	public WorldGenerator endTallGrass;
	public WorldGenerator endGlowGrass;
	public WorldGenerator endDragonOrchid;
	public WorldGenerator endWyrmRoots;
	public WorldGenerator endGrassRemoval;
	public WorldGenerator endCanopyTree;
	public WorldGenerator endCanopySmall;
	private static final IBlockState AIR = Blocks.AIR.getDefaultState();
	private static final IBlockState END_STONE = Blocks.END_STONE.getDefaultState();
	private static final IBlockState END_GRASS = ModBlocks.endGrass.getDefaultState();
	private Random randy;

	static {
		properties.setTemperature(Biomes.SKY.getDefaultTemperature());
		properties.setRainfall(Biomes.SKY.getRainfall());
		properties.setRainDisabled();
		properties.setBaseBiome(String.valueOf(Biomes.SKY));
	}

    public BiomeEndJungle() {
		super(properties);

		this.spawnableMonsterList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
		this.spawnableCaveCreatureList.clear();
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEnderman.class, 40, 1, 4));
		this.topBlock = END_GRASS;
		this.fillerBlock = END_STONE;
		this.decorator = new BiomeEndDecorator();
        this.endGrassRemoval = new WorldGenSurfacePatch(END_STONE, END_GRASS, 1);
        this.endCanopyTree = new WorldGenEnderCanopy(true);
		this.endCanopySmall = new WorldGenSmallEnderCanopy(true);
        endTallGrass = new WorldGenEndPlant(ModBlocks.endTallGrass.getDefaultState());
        endGlowGrass = new WorldGenEndPlant(ModBlocks.endGlowPlant.getDefaultState());
		endDragonOrchid = new WorldGenEndPlant(ModBlocks.endDragonOrchid.getDefaultState());
		endWyrmRoots = new WorldGenEndPlant(ModBlocks.endWyrmRoots.getDefaultState());
        randy = new Random();
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
		for(int x = 0; x < 16; x++) {
			for(int z = 0; z < 16; z++) {
				int plantRoll = randy.nextInt(100);

				if(plantRoll <= 17) {
					int terrainHeight = getEndSurfaceHeight(world, pos.add(x+8, 0, z+8), 30-randy.nextInt(5), 70);

					if(terrainHeight > 0) {
						BlockPos plantPos = pos.add(x+8, terrainHeight, z+8);

						if(plantRoll == 0) {
							endDragonOrchid.generate(world, randy, plantPos);
						} else if(plantRoll <= 2) {
							endWyrmRoots.generate(world, randy, plantPos);
						} else if(plantRoll == 17) {
							endGlowGrass.generate(world, randy, plantPos);
						} else {
							endTallGrass.generate(world, randy, plantPos);
						}
					}
				}
			}
		}


		if (randy.nextInt(7) != 0) {
			int xOff = randy.nextInt(16);
			int zOff = randy.nextInt(16);

			BlockPos targetPos = pos.add(xOff, 0, zOff);

			endGrassRemoval.generate(world, randy, targetPos);
		}

		if (randy.nextInt(Configs.worldgen.treeFrequency) == 0) {
			int xOff = randy.nextInt(16) + 8;
			int zOff = randy.nextInt(16) + 8;

			BlockPos searchPos = pos.add(xOff, 0, zOff);
			int yHeight = getEndSurfaceHeight(world, searchPos, 50, 70);

			if (yHeight > 0) {
				BlockPos treePos = new BlockPos(searchPos.getX(), yHeight + 1, searchPos.getZ());
				IBlockState soil = world.getBlockState(treePos.down());

				if (soil.getBlock() == Blocks.END_STONE || soil.getBlock() == ModBlocks.endGrass) {
					endCanopyTree.generate(world, randy, treePos);
				}
			}
		}

		if (randy.nextInt(2) == 0) {
			int xOff = randy.nextInt(16) + 8;
			int zOff = randy.nextInt(16) + 8;

			BlockPos searchPos = pos.add(xOff, 0, zOff);
			int yHeight = getEndSurfaceHeight(world, searchPos, 50, 70);

			if (yHeight > 0) {
				BlockPos treePos = new BlockPos(searchPos.getX(), yHeight + 1, searchPos.getZ());
				IBlockState soil = world.getBlockState(treePos.down());

				if (soil.getBlock() == Blocks.END_STONE || soil.getBlock() == ModBlocks.endGrass) {
					endCanopySmall.generate(world, randy, treePos);
				}
			}
		}

		super.decorate(world, rand, pos);
    }

    private int getEndSurfaceHeight(World world, BlockPos pos, int min, int max) {
    	int maxY = max;
    	int minY = min;
    	int currentY = maxY;

    	while(currentY >= minY) {
    		if(!world.isAirBlock(pos.add(0, currentY, 0)))
    				return currentY;
    		currentY--;
    	}
    	return 0;
    }
	@SideOnly(Side.CLIENT)
	@Override
	public int getGrassColorAtPos(BlockPos pos) {
		return 0x592ba2;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getFoliageColorAtPos(BlockPos pos) {
		return 0x7f21d8;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getWaterColorMultiplier() {
		return 0x220a30;
	}
}
