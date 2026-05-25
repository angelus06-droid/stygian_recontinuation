package fluke.stygian.block;

import fluke.stygian.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockEndDeadDirt extends Block {

	public static final String REG_NAME = "enddeaddirt";

	public BlockEndDeadDirt() {
		super(Material.GROUND, MapColor.PURPLE_STAINED_HARDENED_CLAY);

		this.setSoundType(SoundType.GROUND);
		this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		this.setHardness(0.5F);
		this.setHarvestLevel("shovel", 0);

		setTranslationKey(Reference.MOD_ID + ".enddeaddirt");
		setRegistryName(REG_NAME);
	}

	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return MapColor.BLACK_STAINED_HARDENED_CLAY;
	}

	@SideOnly(Side.CLIENT)
	public void initModel() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0,
				new ModelResourceLocation(getRegistryName(), "inventory"));
	}
}