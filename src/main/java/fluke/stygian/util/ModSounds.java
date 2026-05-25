package fluke.stygian.util;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@Mod.EventBusSubscriber(modid = "stygian")
public class ModSounds {
	@ObjectHolder("stygian:entity.watchling.idle")
	public static final SoundEvent watchling_idle = null;

	@ObjectHolder("stygian:entity.watchling.hurt")
	public static final SoundEvent watchling_hurt = null;

	@ObjectHolder("stygian:entity.watchling.death")
	public static final SoundEvent watchling_death = null;
	
	@ObjectHolder("stygian:entity.watchling.step")
	public static final SoundEvent watchling_step = null;

	@ObjectHolder("stygian:entity.watchling.attack")
	public static final SoundEvent watchling_attack = null;

	@ObjectHolder("stygian:entity.blastling.idle")
	public static final SoundEvent blastling_idle = null;

	@ObjectHolder("stygian:entity.blastling.hurt")
	public static final SoundEvent blastling_hurt = null;

	@ObjectHolder("stygian:entity.blastling.death")
	public static final SoundEvent blastling_death = null;

	@ObjectHolder("stygian:entity.blastling.step")
	public static final SoundEvent blastling_step = null;

	@ObjectHolder("stygian:entity.blastling.shoot")
	public static final SoundEvent blastling_shoot = null;

	@ObjectHolder("stygian:entity.blastling.bullet_land")
	public static final SoundEvent blastling_bullet_land = null;

	@ObjectHolder("stygian:entity.snareling.idle")
	public static final SoundEvent snareling_idle = null;

	@ObjectHolder("stygian:entity.snareling.hurt")
	public static final SoundEvent snareling_hurt = null;

	@ObjectHolder("stygian:entity.snareling.death")
	public static final SoundEvent snareling_death = null;

	@ObjectHolder("stygian:entity.snareling.step")
	public static final SoundEvent snareling_step = null;

	@ObjectHolder("stygian:entity.snareling.attack")
	public static final SoundEvent snareling_attack = null;

	@ObjectHolder("stygian:entity.snareling.prepare_shoot")
	public static final SoundEvent snareling_prepare_shoot = null;

	@ObjectHolder("stygian:entity.snareling.shoot")
	public static final SoundEvent snareling_shoot = null;

	@ObjectHolder("stygian:entity.snareling.glob_land")
	public static final SoundEvent snareling_glob_land = null;

	@ObjectHolder("stygian:entity.pix.idle")
	public static final SoundEvent pix_idle = null;

	@ObjectHolder("stygian:entity.lunar_zorp.idle")
	public static final SoundEvent lunar_zorp_idle = null;

	@ObjectHolder("stygian:entity.lunar_zorp.hurt")
	public static final SoundEvent lunar_zorp_hurt = null;

	@ObjectHolder("stygian:entity.lunar_zorp.death")
	public static final SoundEvent lunar_zorp_death = null;

	@ObjectHolder("stygian:entity.endwyrm.idle")
	public static final SoundEvent endwyrm_idle = null;

	@ObjectHolder("stygian:entity.endwyrm.hurt")
	public static final SoundEvent endwyrm_hurt = null;

	@ObjectHolder("stygian:entity.endwyrm.death")
	public static final SoundEvent endwyrm_death = null;

	@ObjectHolder("stygian:entity.endbob.idle")
	public static final SoundEvent endbob_idle = null;

	@ObjectHolder("stygian:entity.endbob.hurt")
	public static final SoundEvent endbob_hurt = null;

	@ObjectHolder("stygian:entity.endbob.death")
	public static final SoundEvent endbob_death = null;

	@ObjectHolder("stygian:entity.supaika.idle")
	public static final SoundEvent supaika_idle = null;

	@ObjectHolder("stygian:entity.supaika.hurt")
	public static final SoundEvent supaika_hurt = null;

	@ObjectHolder("stygian:entity.supaika.death")
	public static final SoundEvent supaika_death = null;

	@ObjectHolder("stygian:entity.skipper.idle")
	public static final SoundEvent skipper_idle = null;

	@ObjectHolder("stygian:entity.skipper.hurt")
	public static final SoundEvent skipper_hurt = null;

	@ObjectHolder("stygian:entity.skipper.death")
	public static final SoundEvent skipper_death = null;

	@SubscribeEvent
	public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
		event.getRegistry().registerAll(
				createSound("entity.watchling.idle"),
				createSound("entity.watchling.hurt"),
				createSound("entity.watchling.death"),
				createSound("entity.watchling.step"),
				createSound("entity.watchling.attack"),
				createSound("entity.blastling.idle"),
				createSound("entity.blastling.hurt"),
				createSound("entity.blastling.death"),
				createSound("entity.blastling.step"),
				createSound("entity.blastling.shoot"),
				createSound("entity.blastling.bullet_land"),
				createSound("entity.snareling.idle"),
				createSound("entity.snareling.hurt"),
				createSound("entity.snareling.death"),
				createSound("entity.snareling.step"),
				createSound("entity.snareling.attack"),
				createSound("entity.snareling.prepare_shoot"),
				createSound("entity.snareling.shoot"),
				createSound("entity.snareling.glob_land"),
				createSound("entity.pix.idle"),
				createSound("entity.lunar_zorp.idle"),
				createSound("entity.lunar_zorp.hurt"),
				createSound("entity.lunar_zorp.death"),
				createSound("entity.endwyrm.idle"),
				createSound("entity.endwyrm.hurt"),
				createSound("entity.endwyrm.death"),
				createSound("entity.endbob.idle"),
				createSound("entity.endbob.hurt"),
				createSound("entity.endbob.death"),
				createSound("entity.supaika.idle"),
				createSound("entity.supaika.hurt"),
				createSound("entity.supaika.death"),
				createSound("entity.skipper.idle"),
				createSound("entity.skipper.hurt"),
				createSound("entity.skipper.death")
		);
	}

	private static SoundEvent createSound(String name) {
		ResourceLocation id = new ResourceLocation("stygian", name);
		return new SoundEvent(id).setRegistryName(id);
	}
}