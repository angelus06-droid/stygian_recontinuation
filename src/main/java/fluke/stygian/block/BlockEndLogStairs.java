package fluke.stygian.block;

import fluke.stygian.util.Reference;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockEndLogStairs extends BlockStairs
{
    public static final String REG_NAME = "endlogstairs";

    public BlockEndLogStairs(IBlockState modelState)
    {
        super(modelState);
        this.setCreativeTab(ModBlocks.STYGIAN_TAB);
        this.setHardness(2.0F);
        this.setSoundType(SoundType.WOOD);
        this.setTranslationKey(Reference.MOD_ID + "." + REG_NAME);
        this.setRegistryName(REG_NAME);
        this.useNeighborBrightness = true;
    }

    @SideOnly(Side.CLIENT)
    public void initModel()
    {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0,
                new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}