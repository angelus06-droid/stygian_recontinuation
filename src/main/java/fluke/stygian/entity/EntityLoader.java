package fluke.stygian.entity;

import fluke.stygian.Stygian;
import fluke.stygian.config.Configs;
import fluke.stygian.config.Configs.EntitySpawns.MobSettings;
import fluke.stygian.entity.projectiles.EntityBlastlingBall;
import fluke.stygian.entity.projectiles.EntitySnarelingBall;
import fluke.stygian.entity.projectiles.EntityThrowdableBone;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class EntityLoader {

    private static int entityId = 0;
    private static final String MOD_ID = "stygian";

    public static void init() {
        register(EntityWatchling.class, "watchling", 0x1c1720, 0x070608);
        register(EntityBlastling.class, "blastling", 0x0b0b29, 0xff93f7);
        register(EntitySnareling.class, "snareling", 0x2d3021, 0xc5d830);
        register(EntityEndSkeleton.class, "end_skeleton", 0x31312c, 0x1d1d1a);
        register(EntityCracky.class, "cracky", 0x31312c, 0x1d1d1a);
        register(EntitySupaika.class, "supaika", 0x31312c, 0xf185ff);
        register(EntityEndbob.class, "endbob", 0x602576, 0x33143d);
        register(EntityEndWyrm.class, "endwyrm", 0x6c3763, 0xad837d);
        register(EntityPix.class, "pix", 0x819cd8, 0xd8e1f3);
        register(EntityLunarZorp.class, "lunar_zorp", 0x21384e, 0x519bc2);
        register(EntityLunaling.class, "lunaling", 0x8ab3b1, 0xf3ffff);
        register(EntitySlush.class, "slush", 0xccd067, 0x996318);
        register(EntitySkipper.class, "skipper", 0x1d1f16, 0xf7faa0);

        EntityRegistry.registerModEntity(new ResourceLocation(MOD_ID, "blastling_ball"), EntityBlastlingBall.class, "blastling_ball", ++entityId, Stygian.instance, 64, 10, true);
        EntityRegistry.registerModEntity(new ResourceLocation(MOD_ID, "snareling_ball"), EntitySnarelingBall.class, "snareling_ball", ++entityId, Stygian.instance, 64, 10, true);
        EntityRegistry.registerModEntity(new ResourceLocation(MOD_ID, "throwdable_end_bone"), EntityThrowdableBone.class, "throwdable_end_bone", ++entityId, Stygian.instance, 64, 10, true);

        registerSpawn();
    }

    private static void register(Class<? extends EntityLiving> entityClass, String name, int primaryColor, int secondaryColor) {
        EntityRegistry.registerModEntity(
                new ResourceLocation(MOD_ID, name), entityClass, name, ++entityId, Stygian.instance, 64, 3, true, primaryColor, secondaryColor
        );
    }

    public static void registerSpawn() {
        if (Configs.spawnSettings == null) return;

        registerMob(EntityWatchling.class, Configs.spawnSettings.watchling, EnumCreatureType.MONSTER);
        registerMob(EntityBlastling.class, Configs.spawnSettings.blastling, EnumCreatureType.MONSTER);
        registerMob(EntitySnareling.class, Configs.spawnSettings.snareling, EnumCreatureType.MONSTER);
        registerMob(EntityEndSkeleton.class, Configs.spawnSettings.endSkeleton, EnumCreatureType.MONSTER);
        registerMob(EntityCracky.class, Configs.spawnSettings.cracky, EnumCreatureType.MONSTER);
        registerMob(EntitySupaika.class, Configs.spawnSettings.supaika, EnumCreatureType.MONSTER);
        registerMob(EntityEndWyrm.class, Configs.spawnSettings.endWyrm, EnumCreatureType.MONSTER);
        registerMob(EntityLunarZorp.class, Configs.spawnSettings.lunarZorp, EnumCreatureType.MONSTER);
        registerMob(EntitySkipper.class, Configs.spawnSettings.skipper, EnumCreatureType.MONSTER);

        registerMob(EntityEndbob.class, Configs.spawnSettings.endbob, EnumCreatureType.CREATURE);
        registerMob(EntityPix.class, Configs.spawnSettings.pix, EnumCreatureType.CREATURE);
        registerMob(EntityLunaling.class, Configs.spawnSettings.lunaling, EnumCreatureType.CREATURE);
        registerMob(EntitySlush.class, Configs.spawnSettings.slush, EnumCreatureType.CREATURE);
    }

    private static void registerMob(Class<? extends EntityLiving> entityClass, MobSettings settings, EnumCreatureType type) {
        if (settings.spawnProbability <= 0) return;

        List<Biome> biomes = new ArrayList<>();
        for (String biomeName : settings.biomes) {
            Biome biome = ForgeRegistries.BIOMES.getValue(new ResourceLocation(biomeName));
            if (biome != null) {
                biomes.add(biome);
            }
        }

        if (!biomes.isEmpty()) {
            EntityRegistry.addSpawn(entityClass, settings.spawnProbability, settings.minGroup, settings.maxGroup, type, biomes.toArray(new Biome[0]));
        }
    }
}