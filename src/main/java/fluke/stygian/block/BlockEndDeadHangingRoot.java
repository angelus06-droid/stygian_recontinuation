package fluke.stygian.block;

import fluke.stygian.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockEndDeadHangingRoot extends BlockBush {

	public static final String REG_NAME = "enddeadhangingroot";
	protected static final AxisAlignedBB ROOT_AABB = new AxisAlignedBB(0.25D, 0.2D, 0.25D, 0.75D, 1.0D, 0.75D);

	public BlockEndDeadHangingRoot() {
		super(Material.VINE);
		this.setSoundType(SoundType.PLANT);
		this.setHardness(0.0F);

		setTranslationKey(Reference.MOD_ID + "." + REG_NAME);
		setRegistryName(REG_NAME);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return ROOT_AABB;
	}

	@Override
	public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
		BlockPos abovePos = pos.up();
		IBlockState aboveState = worldIn.getBlockState(abovePos);
		Block aboveBlock = aboveState.getBlock();

		return aboveBlock == ModBlocks.endDeadStone || aboveBlock == ModBlocks.endDeadDirt;
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return this.canBlockStay(worldIn, pos, worldIn.getBlockState(pos));
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (!this.canBlockStay(worldIn, pos, state)) {
			this.dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockToAir(pos);
		}
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@SideOnly(Side.CLIENT)
	public void initModel() {
		net.minecraftforge.client.model.ModelLoader.setCustomModelResourceLocation(
				net.minecraft.item.Item.getItemFromBlock(this), 0,
				new net.minecraft.client.renderer.block.model.ModelResourceLocation(getRegistryName(), "inventory")
		);
	}
}