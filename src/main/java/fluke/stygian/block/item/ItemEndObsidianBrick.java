package fluke.stygian.block.item;

import fluke.stygian.util.Reference;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemEndObsidianBrick extends Item {

    public static final String REG_NAME = "porphyry_brick";
    public ItemEndObsidianBrick() {
        setTranslationKey(Reference.MOD_ID + ".porphyry_brick");
        setRegistryName(REG_NAME);
        System.out.println("ItemEndObsidianBrick created!");
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(
                this, 0,
                new ModelResourceLocation(getRegistryName(), "porphyry_brick"));
    }
}
