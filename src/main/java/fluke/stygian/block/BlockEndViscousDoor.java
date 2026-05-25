package fluke.stygian.block;

import fluke.stygian.util.Reference;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockEndViscousDoor extends BlockDoor {
	public static final String REG_NAME = "endviscousdoor";

	public BlockEndViscousDoor() {
		super(Material.WOOD);
		this.setHardness(2.0F);
		this.setSoundType(SoundType.WOOD);
		setTranslationKey(Reference.MOD_ID + ".endviscousdoor");
		setRegistryName(REG_NAME);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		if (state.getValue(HALF) == EnumDoorHalf.UPPER) {
			return null;
		}
		return ModBlocks.endViscousDoorItem;
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(ModBlocks.endViscousDoorItem);
	}

	@SideOnly(Side.CLIENT)
	public void initModel() {
		ModelLoader.setCustomModelResourceLocation(ModBlocks.endViscousDoorItem, 0,
				new ModelResourceLocation(getRegistryName(), "inventory"));
	}
}