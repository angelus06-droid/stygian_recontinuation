package fluke.stygian.block.item;

import fluke.stygian.block.ModBlocks;
import fluke.stygian.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemEndBoneMeal extends Item {

    public static final String REG_NAME = "endbonemeal";

    public ItemEndBoneMeal() {
        setTranslationKey(Reference.MOD_ID + ".endbonemeal");
        setRegistryName(REG_NAME);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack itemStack = playerIn.getHeldItem(handIn);

        if (!playerIn.canPlayerEdit(playerIn.getPosition(), playerIn.getHorizontalFacing(), itemStack)) {
            return new ActionResult<>(EnumActionResult.FAIL, itemStack);
        }

        boolean success = applyBoneMeal(itemStack, worldIn, playerIn, handIn);

        if (success && !worldIn.isRemote) {
            worldIn.playEvent(2005, playerIn.getPosition(), 0);
        }

        if (success) {
            return new ActionResult<>(EnumActionResult.SUCCESS, itemStack);
        } else {
            return new ActionResult<>(EnumActionResult.PASS, itemStack);
        }
    }

    public boolean applyBoneMeal(ItemStack itemStack, World world, EntityPlayer player, EnumHand hand) {
        RayTraceResult result = player.rayTrace(5, 1);
        if (result != null && result.typeOfHit == RayTraceResult.Type.BLOCK) {
            BlockPos targetPos = result.getBlockPos();
            IBlockState targetState = world.getBlockState(targetPos);
            Block targetBlock = targetState.getBlock();

            if (targetBlock == ModBlocks.endGrass ||
                    targetBlock == ModBlocks.endLunarGrass ||
                    targetBlock == ModBlocks.endSnareGrass) {

                if (!world.isRemote) {
                    growVanillaStyleVegetation(world, targetPos, targetBlock);
                    world.playEvent(2005, targetPos, 0);
                }

                if (!player.capabilities.isCreativeMode) {
                    itemStack.shrink(1);
                }
                return true;
            }
        }
        return false;
    }

    private void growVanillaStyleVegetation(World world, BlockPos pos, Block baseBlock) {
        IBlockState tallGrassState;
        IBlockState flowerState;
        IBlockState specialPlantState;

        if (baseBlock == ModBlocks.endLunarGrass) {
            tallGrassState = ModBlocks.endLunarTallGrass.getDefaultState();
            flowerState = ModBlocks.endLightFlower.getDefaultState();
            specialPlantState = ModBlocks.endMoonshine.getDefaultState();
        } else if (baseBlock == ModBlocks.endSnareGrass) {
            tallGrassState = ModBlocks.endSnareTallGrass.getDefaultState();
            flowerState = ModBlocks.endSnareBells.getDefaultState();
            specialPlantState = ModBlocks.endCell.getDefaultState();
        } else {
            tallGrassState = ModBlocks.endTallGrass.getDefaultState();
            flowerState = ModBlocks.endGlowPlant.getDefaultState();
            specialPlantState = ModBlocks.endWyrmRoots.getDefaultState();
        }

        for (int i = 0; i < 128; ++i) {
            BlockPos currentPos = pos;
            int j = 0;

            while (true) {
                if (j >= i / 16) {
                    BlockPos upPos = currentPos.up();
                    if (world.isAirBlock(upPos) && baseBlock.canPlaceBlockAt(world, upPos)) {

                        int rand = world.rand.nextInt(10);

                        if (rand == 0) {
                            world.setBlockState(upPos, flowerState, 3);
                        } else if (rand == 1) {
                            world.setBlockState(upPos, specialPlantState, 2);
                        } else {
                            world.setBlockState(upPos, tallGrassState, 3);
                        }
                    }
                    break;
                }

                currentPos = currentPos.add(world.rand.nextInt(3) - 1, (world.rand.nextInt(3) - 1) * world.rand.nextInt(3) / 2, world.rand.nextInt(3) - 1);

                if (world.getBlockState(currentPos).getBlock() != baseBlock || world.isBlockFullCube(currentPos.up())) {
                    break;
                }
                j++;
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0,
                new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}
