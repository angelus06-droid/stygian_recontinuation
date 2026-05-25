package fluke.stygian.block.item;

import fluke.stygian.util.Reference;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood; // Cambiado de Item a ItemFood
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemLunarCrumbs extends ItemFood {

    public static final String REG_NAME = "lunar_crumbs";

    public ItemLunarCrumbs() {
        super(2, 0.3F, false);
        setTranslationKey(Reference.MOD_ID + ".lunar_crumbs");
        setRegistryName(REG_NAME);

        setAlwaysEdible();
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
        if (!worldIn.isRemote) {
            player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 40, 1));
        }
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(
                this, 0,
                new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}