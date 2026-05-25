package fluke.stygian;

import fluke.stygian.block.ModBlocks;
import fluke.stygian.entity.EntityLoader;
import fluke.stygian.proxy.CommonProxy;
import fluke.stygian.util.Reference;
import fluke.stygian.world.BiomeRegistrar;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Reference.MOD_ID, name = "Stygian End Re-Continuation", version = "2.0.0", acceptableRemoteVersions = "*")
public class Stygian {
	public static final Logger LOGGER = LogManager.getLogger(Reference.MOD_ID);

	@Instance
	public static Stygian instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;

	static {
		FluidRegistry.enableUniversalBucket();
	}

	@Mod.EventHandler
	public void preInit(final FMLPreInitializationEvent e) {
		Stygian.proxy.preInit(e);
		Stygian.proxy.registerRenders();
		EntityLoader.init();
	}

	@Mod.EventHandler
	public void init(final FMLInitializationEvent e) {
		Stygian.proxy.init(e);
		BiomeRegistrar.registerBiomes();
		GameRegistry.addSmelting(ModBlocks.endBlueCactus, new ItemStack(Items.DYE, 2, 4), 0.5F);
		GameRegistry.addSmelting(ModBlocks.endObsidian, new ItemStack(ModBlocks.endObsidianBrick, 3), 0.2F);
		GameRegistry.addSmelting(ModBlocks.endDeadCobblestone, new ItemStack(ModBlocks.endDeadStone, 1), 0.1F);
	}

	@Mod.EventHandler
	public void postInit(final FMLPostInitializationEvent e) {
		Stygian.proxy.postInit(e);
		EntityLoader.registerSpawn();
	}

	@EventHandler
	public void startServer(FMLServerStartingEvent event) {
	}
}