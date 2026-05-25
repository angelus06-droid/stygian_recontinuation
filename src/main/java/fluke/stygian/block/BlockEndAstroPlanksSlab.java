package fluke.stygian.block;

import fluke.stygian.util.Reference;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public abstract class BlockEndAstroPlanksSlab extends BlockSlab {
	public static final PropertyEnum<Variant> VARIANT = PropertyEnum.create("variant", Variant.class);
	public static final String REG_NAME = "endastroplanksslab";

	public BlockEndAstroPlanksSlab(String name) {
		super(Material.WOOD);
		this.setHardness(2.0F);
		this.setSoundType(SoundType.WOOD);
		setTranslationKey(Reference.MOD_ID + "." + name);
		setRegistryName(name);

		IBlockState state = this.blockState.getBaseState();
		if (!this.isDouble()) {
			state = state.withProperty(HALF, EnumBlockHalf.BOTTOM);
		}
		setDefaultState(state.withProperty(VARIANT, Variant.DEFAULT));
		this.useNeighborBrightness = true;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(ModBlocks.endAstroPlanksSlab);
	}

	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random) {
		return this.isDouble() ? 2 : 1;
	}

	@Override
	public String getTranslationKey(int meta) {
		return super.getTranslationKey();
	}

	@Override
	public IProperty<?> getVariantProperty() {
		return VARIANT;
	}

	@Override
	public Comparable<?> getTypeForItem(ItemStack stack) {
		return Variant.DEFAULT;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		IBlockState state = this.getDefaultState().withProperty(VARIANT, Variant.DEFAULT);
		if (!this.isDouble()) {
			state = state.withProperty(HALF, (meta & 8) == 0 ? EnumBlockHalf.BOTTOM : EnumBlockHalf.TOP);
		}
		return state;
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int meta = 0;
		if (!this.isDouble() && state.getValue(HALF) == EnumBlockHalf.TOP) {
			meta |= 8;
		}
		return meta;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return this.isDouble() ? new BlockStateContainer(this, VARIANT) : new BlockStateContainer(this, HALF, VARIANT);
	}
	@SideOnly(Side.CLIENT)
	public void initModel() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}

	public static enum Variant implements IStringSerializable {
		DEFAULT;
		@Override
		public String getName() { return "default"; }
	}
}