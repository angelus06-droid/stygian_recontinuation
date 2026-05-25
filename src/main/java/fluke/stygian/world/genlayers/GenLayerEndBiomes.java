package fluke.stygian.world.genlayers;

import fluke.stygian.config.Configs;
import fluke.stygian.world.BiomeRegistrar;
import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerEndBiomes extends GenLayer {
    private final int SKY_ID;
    private final int END_FOREST_ID;
    private final int END_MEADOW_ID;
    private final int END_VOLCANO_ID;
    private final int END_DESERT_ID;
    private final int END_VALLEY_ID;
    private final int PLACEHOLDER;
    private final static int MAIN_ISLAND_SIZE;

    static {
        int size = (int) (60 / Math.pow(2, (Configs.worldgen.endBiomeSize - 1)));
        MAIN_ISLAND_SIZE = Math.max(1, size);
    }

    public GenLayerEndBiomes(long seed, GenLayer parent) {
        super(seed);
        this.parent = parent;
        SKY_ID = Biome.getIdForBiome(Biomes.SKY);
        END_FOREST_ID = Biome.getIdForBiome(BiomeRegistrar.END_JUNGLE);
        END_MEADOW_ID = Biome.getIdForBiome(BiomeRegistrar.END_MEADOW);
        END_VOLCANO_ID = Biome.getIdForBiome(BiomeRegistrar.END_VOLCANO);
        END_DESERT_ID = Biome.getIdForBiome(BiomeRegistrar.END_DESERT);
        END_VALLEY_ID = Biome.getIdForBiome(BiomeRegistrar.END_VALLEY);
        PLACEHOLDER = SKY_ID;
    }

    @Override
    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
        int[] out = IntCache.getIntCache(areaWidth * areaHeight);

        for (int y = 0; y < areaHeight; y++) {
            for (int x = 0; x < areaWidth; x++) {

                int worldX = areaX + x;
                int worldY = areaY + y;
                int index = x + y * areaWidth;

                if (worldX > -MAIN_ISLAND_SIZE && worldX < MAIN_ISLAND_SIZE
                        && worldY > -MAIN_ISLAND_SIZE && worldY < MAIN_ISLAND_SIZE) {

                    out[index] = SKY_ID;
                    continue;
                }

                int clumpX = worldX >> 3;
                int clumpY = worldY >> 3;

                this.initChunkSeed(clumpX, clumpY);

                int rand = this.nextInt(6);

                switch (rand) {
                    case 0:
                        out[index] = END_FOREST_ID;
                        break;
                    case 1:
                        out[index] = END_MEADOW_ID;
                        break;
                    case 2:
                        out[index] = END_VOLCANO_ID;
                        break;
                    case 3:
                        out[index] = END_DESERT_ID;
                        break;
                    case 4:
                        out[index] = END_VALLEY_ID;
                        break;
                    default:
                        out[index] = PLACEHOLDER;
                }
            }
        }

        return out;
    }
}