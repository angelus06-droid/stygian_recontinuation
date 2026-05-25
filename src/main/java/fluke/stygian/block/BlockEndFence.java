package fluke.stygian.block;

import fluke.stygian.util.Reference;
import net.minecraft.block.BlockFence;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockEndFence extends BlockFence {
	public static final String REG_NAME = "endfence";

	public BlockEndFence() {
		super(Material.WOOD, MapColor.SAND);
		this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		this.setHardness(2.0F);
		this.setResistance(5.0F);
		this.setSoundType(SoundType.WOOD);
		this.setTranslationKey(Reference.MOD_ID + ".endfence");
		this.setRegistryName(REG_NAME);
	}

	@SideOnly(Side.CLIENT)
	public void initModel() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0,
				new ModelResourceLocation(getRegistryName(), "inventory"));
	}
}