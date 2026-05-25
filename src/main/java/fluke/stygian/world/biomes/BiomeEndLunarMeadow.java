package fluke.stygian.world.biomes;

import fluke.stygian.block.ModBlocks;
import fluke.stygian.config.Configs;
import fluke.stygian.world.feature.WorldGenAstroTree;
import fluke.stygian.world.feature.WorldGenEndPlant;
import fluke.stygian.world.feature.WorldGenLunarBoulder;
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

public class BiomeEndLunarMeadow extends Biome {
	public static BiomeProperties properties = new BiomeProperties("Lunar Meadow");
	public final WorldGenerator endTallGrass;
	public final WorldGenerator endGlowGrass;
    public final WorldGenerator endMoonshine;
	public final WorldGenerator endGrassRemoval;
	public final WorldGenerator endAstroTree;
	public static final WorldGenLunarBoulder END_BOULDER = new WorldGenLunarBoulder(ModBlocks.endMoonRock.getDefaultState(), ModBlocks.endPolishedMoonRock.getDefaultState());
	private Random randy;

	private static final IBlockState END_STONE = Blocks.END_STONE.getDefaultState();
	private static final IBlockState END_GRASS = ModBlocks.endLunarGrass.getDefaultState();

	static {
		properties.setTemperature(Biomes.SKY.getDefaultTemperature());
		properties.setRainfall(Biomes.SKY.getRainfall());
		properties.setRainDisabled();
		properties.setBaseBiome(String.valueOf(Biomes.SKY));
	}

	public BiomeEndLunarMeadow() {
		super(properties);
		this.spawnableMonsterList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
		this.spawnableCaveCreatureList.clear();
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEnderman.class, 40, 1, 4));

		this.topBlock = END_GRASS;
		this.fillerBlock = END_STONE;

		this.endTallGrass = new WorldGenEndPlant(ModBlocks.endLunarTallGrass.getDefaultState());
		this.endGlowGrass = new WorldGenEndPlant(ModBlocks.endLightFlower.getDefaultState());
        this.endMoonshine = new WorldGenEndPlant(ModBlocks.endMoonshine.getDefaultState());
		this.endGrassRemoval = new WorldGenSurfacePatch(END_STONE, END_GRASS, 1);
		this.endAstroTree = new WorldGenAstroTree(true);
		randy = new Random();
	}

	@Override
	public BiomeDecorator createBiomeDecorator() {
		return new BiomeEndDecorator();
	}

	public void decorate(World world, Random rand, BlockPos pos) {
        for(int x = 0; x < 16; x++) {
            for(int z = 0; z < 16; z++) {
                int plantRoll = randy.nextInt(100);

                if(plantRoll <= 15) {
                    int terrainHeight = getEndSurfaceHeight(world, pos.add(x+8, 0, z+8), 30-randy.nextInt(5), 70);

                    if(terrainHeight > 0) {
                        BlockPos plantPos = pos.add(x+8, terrainHeight, z+8);

                        if(plantRoll == 1) {
                            endMoonshine.generate(world, randy, plantPos);
                        } else if(plantRoll == 15) {
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

		if (randy.nextInt(Configs.worldgen.bouldersFrequency) == 0) {
			int xOff = randy.nextInt(16);
			int zOff = randy.nextInt(16);
			BlockPos targetPos = pos.add(xOff, 0, zOff);

			int yHeight = getEndSurfaceHeight(world, targetPos, 50, 70);

			if (yHeight > 0)
			{
				END_BOULDER.generate(world, randy, targetPos.up(yHeight + 1));
			}
		}

		if (randy.nextInt(3) == 0) {
			int xOff = randy.nextInt(16) + 8;
			int zOff = randy.nextInt(16) + 8;

			BlockPos targetPos = world.getHeight(pos.add(xOff, 0, zOff));

			IBlockState soil = world.getBlockState(targetPos.down());
			if (soil.getBlock() == ModBlocks.endLunarGrass) {
				endAstroTree.generate(world, randy, targetPos);
			}
		}

		super.decorate(world, rand, pos);
	}

	private int getEndSurfaceHeight(World world, BlockPos pos, int min, int max)
	{
		int maxY = max;
		int minY = min;
		int currentY = maxY;

		while(currentY >= minY)
		{
			if(!world.isAirBlock(pos.add(0, currentY, 0)))
				return currentY;
			currentY--;
		}
		return 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getSkyColorByTemp(float currentTemperature)
	{
		return 0;
	}
}