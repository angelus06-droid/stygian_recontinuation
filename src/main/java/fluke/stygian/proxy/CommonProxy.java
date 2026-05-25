package fluke.stygian.proxy;

import fluke.stygian.util.ModDispenserBehaviors;
import fluke.stygian.world.WorldProviderEndBiomes;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

	public void init(final FMLInitializationEvent e) {
		overrideEnd();
		ModDispenserBehaviors.init();
	}


	public void postInit(final FMLPostInitializationEvent e) {
	}

	public void registerRenders() {
	}

	public void overrideEnd() {
		DimensionManager.unregisterDimension(1);
		DimensionType endBiomes = DimensionType.register("End", "_end", 1, WorldProviderEndBiomes.class, false);
		DimensionManager.registerDimension(1, endBiomes);
	}

	public void preInit(FMLPreInitializationEvent e) {
	}
}