package fluke.stygian.block.item;

import fluke.stygian.block.ModBlocks;
import fluke.stygian.entity.projectiles.EntityThrowdableBone;
import fluke.stygian.util.Reference;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemSupaikaBone extends ItemSword {

    public static final String REG_NAME = "supaika_bone";

    public static ToolMaterial END_BONE_MATERIAL = EnumHelper.addToolMaterial("END_BONE", 3, 1000, 8.0f, 4.5f, 10);

    public ItemSupaikaBone() {
        super(END_BONE_MATERIAL);
        setTranslationKey(Reference.MOD_ID + ".supaika_bone");
        setRegistryName(REG_NAME);

        END_BONE_MATERIAL.setRepairItem(new ItemStack(ModBlocks.endBone));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);

        world.playSound(null, player.posX, player.posY, player.posZ,
                SoundEvents.ENTITY_WITHER_SKELETON_HURT,
                net.minecraft.util.SoundCategory.PLAYERS, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!world.isRemote) {
            int sharpLvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.SHARPNESS, stack);
            int fireLvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_ASPECT, stack);
            int knockLvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.KNOCKBACK, stack);

            float extraDamage = (sharpLvl > 0) ? (sharpLvl * 0.5F + 0.5F) : 0.0F;

            for (int i = 0; i < 2; i++) {
                EntityThrowdableBone bone = new EntityThrowdableBone(world, player);

                bone.setEnchantmentDamage(extraDamage);
                bone.setFireLevel(fireLvl);
                bone.setKnockbackLevel(knockLvl);

                bone.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 1.0F);
                world.spawnEntity(bone);
            }

            player.getCooldownTracker().setCooldown(this, 100);
        }

        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(
                this, 0,
                new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(TextFormatting.GRAY + new TextComponentTranslation("item.stygian.supaika_bone.desc").getFormattedText());
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.EPIC;
    }
}