package fluke.stygian.block;

import fluke.stygian.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockEndGrass extends Block  implements ITickable {
	public static final String REG_NAME = "endgrass";
	private int ticks;
	public BlockEndGrass() {
		super(Material.GRASS, MapColor.PURPLE);
		this.setTickRandomly(true);
		this.setHardness(0.6F);
		this.setSoundType(SoundType.GROUND);
		this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		this.setTranslationKey(Reference.MOD_ID + ".endgrass");
		this.setRegistryName(REG_NAME);
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		if (!world.isRemote) {
			for (int i = 0; i < 4; ++i) {
				BlockPos targetPos = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);
				if (world.isBlockLoaded(targetPos)) {
					IBlockState targetState = world.getBlockState(targetPos);
					BlockPos abovePos = targetPos.up();

					if (targetState.getBlock() == Blocks.END_STONE && world.getLightFromNeighbors(abovePos) >= 4) {
						world.setBlockState(targetPos, this.getDefaultState());
					}
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public void initModel() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}
	@Override
	public void update() {
	}
}
