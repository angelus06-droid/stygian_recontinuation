package fluke.stygian.proxy;

import fluke.stygian.block.ModBlocks;
import fluke.stygian.entity.render.EntityRenderRegistry;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy
{
    public void preInit(final FMLPreInitializationEvent e) {
        EntityRenderRegistry.Load();
        super.preInit(e);
    }
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        ModBlocks.initModels();
    }

    @Override
    public void init(final FMLInitializationEvent e) {
        super.init(e);
    }
    public void registerRenders() {
    }

    @Override
    public void postInit(final FMLPostInitializationEvent e) {
        super.postInit(e);
    }
}
