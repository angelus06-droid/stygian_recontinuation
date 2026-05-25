package fluke.stygian.config;

import fluke.stygian.util.Reference;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = Reference.MOD_ID, name = Reference.MOD_ID, category = "")
@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class Configs {

	@Config.Name("World Generation")
	@Config.Comment("Configure how the Stygian End biomes and structures are generated.")
	public static ConfigWorldGen worldgen = new ConfigWorldGen();

	@Config.Name("Entity Spawns")
	@Config.Comment("Control mob spawn rates and locations.")
	public static EntitySpawns spawnSettings = new EntitySpawns();

	public static class ConfigWorldGen {
		@Config.RequiresWorldRestart
		@Config.RangeInt(min = 0, max = 100)
		@Config.Comment("Weight for the Lunar Meadow biome.")
		public int lunarMeadowWeight = 100;

		@Config.RequiresWorldRestart
		@Config.RangeInt(min = 0, max = 100)
		@Config.Comment("Weight for the Algea Desert biome.")
		public int algeaDesertWeight = 100;

		@Config.RequiresWorldRestart
		@Config.RangeInt(min = 0, max = 100)
		@Config.Comment("Weight for the Bacterial Valley biome.")
		public int bacterialValleyWeight = 100;

		@Config.RequiresWorldRestart
		@Config.RangeInt(min = 1, max = 16)
		@Config.Comment({"Controls size of end biomes. Larger number = larger biomes", "Default: 4"})
		public int endBiomeSize = 4;

		@Config.RequiresWorldRestart
		@Config.RangeInt(min = 1)
		@Config.Comment({"Controls how often end volcanoes generate. Larger number = fewer volcanoes", "Default: 8"})
		public int volcanoFrequency = 8;

		@Config.RequiresWorldRestart
		@Config.RangeInt(min = 1)
		@Config.Comment({"Controls how often large end trees generate. Larger number = fewer trees", "Default: 7"})
		public int treeFrequency = 7;

		@Config.RequiresWorldRestart
		@Config.RangeInt(min = 1)
		@Config.Comment({"Controls how often end boulders generate. Larger number = fewer boulders", "Default: 5"})
		public int bouldersFrequency = 5;

		@Config.RequiresWorldRestart
		@Config.RangeInt(min = 0, max = 99)
		@Config.Comment({"Reduce number of end biomes by percent. e.g. 40 would generate 40% fewer end biomes", "Default: 0"})
		public int biomeReducer = 0;
	}

	public static class EntitySpawns {

		@Config.RequiresMcRestart
		public final MobSettings watchling = new MobSettings(3, 1, 3,
				"minecraft:sky", "stygian:stygian_growth", "stygian:acidic_plains", "stygian:lunar_meadow", "stygian:algea_desert", "stygian:bacterial_valley", "ee:ash_wastelands");

		@Config.RequiresMcRestart
		public final MobSettings blastling = new MobSettings(2, 1, 2,
				"minecraft:sky", "stygian:stygian_growth", "stygian:acidic_plains", "stygian:lunar_meadow", "stygian:algea_desert", "stygian:bacterial_valley", "ee:ash_wastelands");

		@Config.RequiresMcRestart
		public final MobSettings snareling = new MobSettings(2, 1, 2,
				"minecraft:sky", "stygian:stygian_growth", "stygian:acidic_plains", "stygian:lunar_meadow", "stygian:algea_desert", "stygian:bacterial_valley", "ee:ash_wastelands");

		@Config.RequiresMcRestart
		public final MobSettings endSkeleton = new MobSettings(3, 1, 2, "stygian:acidic_plains");

		@Config.RequiresMcRestart
		public final MobSettings cracky = new MobSettings(4, 1, 3, "stygian:acidic_plains", "stygian:algea_desert", "ee:ash_wastelands");

		@Config.RequiresMcRestart
		public final MobSettings supaika = new MobSettings(1, 1, 1, "stygian:acidic_plains", "stygian:algea_desert", "ee:ash_wastelands");

		@Config.RequiresMcRestart
		public final MobSettings endbob = new MobSettings(5, 1, 4, "stygian:stygian_growth");

		@Config.RequiresMcRestart
		public final MobSettings endWyrm = new MobSettings(2, 1, 2, "stygian:stygian_growth", "stygian:algea_desert");

		@Config.RequiresMcRestart
		public final MobSettings pix = new MobSettings(8, 1, 2, "stygian:lunar_meadow");

		@Config.RequiresMcRestart
		public final MobSettings lunarZorp = new MobSettings(5, 1, 2, "stygian:lunar_meadow");

        @Config.RequiresMcRestart
        public final MobSettings lunaling = new MobSettings(2, 2, 5, "stygian:lunar_meadow");

		@Config.RequiresMcRestart
		public final MobSettings slush = new MobSettings(10, 1, 2, "stygian:bacterial_valley");

        @Config.RequiresMcRestart
        public final MobSettings skipper = new MobSettings(8, 1, 2, "stygian:bacterial_valley");

		public static class MobSettings {
			@Config.RangeInt(min = 0, max = 100)
			@Config.Comment("Spawn probability weight")
			public int spawnProbability;

			@Config.RangeInt(min = 1, max = 64)
			@Config.Comment("Minimum group size")
			public int minGroup;

			@Config.RangeInt(min = 1, max = 64)
			@Config.Comment("Maximum group size")
			public int maxGroup;

			@Config.Comment("List of biome IDs where this mob can spawn")
			public String[] biomes;

			public MobSettings(int prob, int min, int max, String... biomes) {
				this.spawnProbability = prob;
				this.minGroup = min;
				this.maxGroup = max;
				this.biomes = biomes;
			}
		}
	}

	@SubscribeEvent
	public static void onConfigReload(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (Reference.MOD_ID.equals(event.getModID())) {
			ConfigManager.sync(Reference.MOD_ID, Config.Type.INSTANCE);
		}
	}
}