package fluke.stygian.util;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IModelProvider {
    @SideOnly(Side.CLIENT)
    void initModel();
}