package fluke.stygian.world;

import fluke.stygian.config.Configs;
import fluke.stygian.world.biomes.*;
import git.jbredwards.nether_api.api.event.NetherAPIRegistryEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

@Mod.EventBusSubscriber(modid = "stygian")
public class BiomeRegistrar {

	public static final Biome END_JUNGLE = new BiomeEndJungle();
	public static final Biome END_VOLCANO = new BiomeEndVolcano();
	public static final Biome END_MEADOW = new BiomeEndLunarMeadow();
	public static final Biome END_DESERT = new BiomeEndDesert();
	public static final Biome END_VALLEY = new BiomeEndBacterialValley();

	public static void registerBiomes() {
		initBiome(END_JUNGLE, "stygian_growth", Type.END, Type.FOREST, Type.DENSE);
		initBiome(END_VOLCANO, "acidic_plains", Type.END, Type.WASTELAND, Type.DRY);
		initBiome(END_MEADOW, "lunar_meadow", Type.END, Type.MAGICAL, Type.PLAINS);
		initBiome(END_DESERT, "algea_desert", Type.END, Type.WASTELAND);
		initBiome(END_VALLEY, "bacterial_valley", Type.END, Type.WASTELAND, Type.DEAD);
	}

	@SubscribeEvent
	public static void onEndRegistry(NetherAPIRegistryEvent.End event) {
		event.registry.registerBiome(END_MEADOW, Configs.worldgen.lunarMeadowWeight);
		event.registry.registerBiome(END_DESERT, Configs.worldgen.algeaDesertWeight);
		event.registry.registerBiome(END_VALLEY, Configs.worldgen.bacterialValleyWeight);
	}

	private static void initBiome(Biome biome, String name, Type... types) {
		biome.setRegistryName("stygian", name);
		ForgeRegistries.BIOMES.register(biome);
		BiomeDictionary.addTypes(biome, types);
	}
}