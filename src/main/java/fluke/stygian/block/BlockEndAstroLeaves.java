package fluke.stygian.block;

import fluke.stygian.util.Reference;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Random;

public class BlockEndAstroLeaves extends BlockLeaves
{
    public static final String REG_NAME = "endastroleaves";

    public BlockEndAstroLeaves() {
        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(CHECK_DECAY, Boolean.TRUE)
                .withProperty(DECAYABLE, Boolean.TRUE));

        setTranslationKey(Reference.MOD_ID + ".endastroleaves");
        setRegistryName(REG_NAME);
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return this.getDefaultState().withProperty(DECAYABLE, Boolean.FALSE).withProperty(CHECK_DECAY, Boolean.FALSE);
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (!worldIn.isRemote) {
            if (state.getValue(CHECK_DECAY) && state.getValue(DECAYABLE)) {
                byte range = 4;
                int x = pos.getX();
                int y = pos.getY();
                int z = pos.getZ();

                if (worldIn.isAreaLoaded(new BlockPos(x - 5, y - 5, z - 5), new BlockPos(x + 5, y + 5, z + 5))) {
                    BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

                    for (int dx = -range; dx <= range; ++dx) {
                        for (int dy = -range; dy <= range; ++dy) {
                            for (int dz = -range; dz <= range; ++dz) {
                                IBlockState iblockstate = worldIn.getBlockState(mutablePos.setPos(x + dx, y + dy, z + dz));

                                if (iblockstate.getBlock() == ModBlocks.endAstroLog || iblockstate.getBlock().canSustainLeaves(iblockstate, worldIn, mutablePos)) {
                                    worldIn.setBlockState(pos, state.withProperty(CHECK_DECAY, false), 4);
                                    return;
                                }
                            }
                        }
                    }
                    this.dropApple(worldIn, pos, state, 0);
                    worldIn.setBlockToAir(pos);
                }
            }
        }
    }
    @Override
    public void dropApple(World worldIn, BlockPos pos, IBlockState state, int chance) {
        if (worldIn.rand.nextInt(20) == 0) {
            spawnAsEntity(worldIn, pos, new ItemStack(this.getItemDropped(state, worldIn.rand, chance)));
        }
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, CHECK_DECAY, DECAYABLE);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(DECAYABLE, (meta & 4) == 0).withProperty(CHECK_DECAY, (meta & 8) > 0);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        if (!(Boolean) state.getValue(DECAYABLE)) i |= 4;
        if ((Boolean) state.getValue(CHECK_DECAY)) i |= 8;
        return i;
    }

    @Override
    public EnumType getWoodType(int meta) {
        return null;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(ModBlocks.endAstroSapling);
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        if (!worldIn.isRemote && stack.getItem() == Items.SHEARS) {
            player.addStat(Objects.requireNonNull(StatList.getBlockStats(this)));
            spawnAsEntity(worldIn, pos, new ItemStack(this, 1, 0));
        } else {
            super.harvestBlock(worldIn, player, pos, state, te, stack);
        }
    }

    @Override
    public NonNullList<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        return NonNullList.withSize(1, new ItemStack(this, 1, 0));
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return MapColor.LIGHT_BLUE;
    }
}