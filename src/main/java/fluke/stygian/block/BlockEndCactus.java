package fluke.stygian.block;

import fluke.stygian.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockEndCactus extends Block implements IGrowable
{
    public static final String REG_NAME = "endcactus";
    public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 15);
    protected static final AxisAlignedBB CACTUS_COLLISION_AABB = new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 0.8125D, 1.0D, 0.8125D);
    protected static final AxisAlignedBB CACTUS_AABB = new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 0.8125D, 1.0D, 0.8125D);

    public BlockEndCactus()
    {
        super(Material.CACTUS);
        this.setCreativeTab(CreativeTabs.DECORATIONS);
        this.setHardness(0.4F);
        this.setSoundType(SoundType.CLOTH);
        this.setTranslationKey(Reference.MOD_ID + ".endcactus");
        this.setRegistryName(REG_NAME);
        this.setTickRandomly(true);
        this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, 0));
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (!worldIn.isAreaLoaded(pos, 1)) return;

        BlockPos blockpos = pos.up();

        if (worldIn.isAirBlock(blockpos)) {
            int cactusHeight;
            for (cactusHeight = 1; worldIn.getBlockState(pos.down(cactusHeight)).getBlock() == this; ++cactusHeight);

            if (cactusHeight < 7) {
                int age = state.getValue(AGE);

                if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, blockpos, state, true)) {
                    if (age == 15) {
                        worldIn.setBlockState(blockpos, this.getDefaultState());
                        IBlockState iblockstate = state.withProperty(AGE, 0);
                        worldIn.setBlockState(pos, iblockstate, 4);
                        iblockstate.neighborChanged(worldIn, blockpos, this, pos);
                    }else{
                        worldIn.setBlockState(pos, state.withProperty(AGE, age + 1), 4);
                    }
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
                }
            }
        }
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        return worldIn.getLight(pos) == 0;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        worldIn.setBlockState(pos, state.withProperty(AGE, Math.min(15, state.getValue(AGE) + 1)), 4);
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
        return false;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return CACTUS_COLLISION_AABB;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
        return CACTUS_AABB.offset(pos);
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return super.canPlaceBlockAt(worldIn, pos) ? this.canBlockStay(worldIn, pos) : false;
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (!this.canBlockStay(worldIn, pos)) {
            worldIn.destroyBlock(pos, true);
        }
    }

    public boolean canBlockStay(World worldIn, BlockPos pos) {
        IBlockState stateDown = worldIn.getBlockState(pos.down());
        return stateDown.getBlock() == this || stateDown.getBlock() == ModBlocks.endMagma;
    }

    @Override
    public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        entityIn.attackEntityFrom(DamageSource.CACTUS, 1.0F);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(AGE, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(AGE);
    }

    @Override
    protected BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, new IProperty[] {AGE});
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        IStateMapper mappy = (new StateMap.Builder()).ignore(AGE).build();
        ModelLoader.setCustomStateMapper(this, mappy);
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}
