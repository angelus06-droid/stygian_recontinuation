package fluke.stygian.entity.render;

import fluke.stygian.entity.*;
import fluke.stygian.entity.projectiles.*;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class EntityRenderRegistry
{
    private static <T extends Entity> void reg(final Class<T> entityClass, final IRenderFactory<? super T> factory) {
        RenderingRegistry.registerEntityRenderingHandler(entityClass, factory);
    }

    public static void Load() {
        reg(EntityWatchling.class, RenderWatchling::new);
        reg(EntityBlastling.class, RenderBlastling::new);
        reg(EntitySnareling.class, RenderSnareling::new);
        reg(EntityEndbob.class, RenderEndbob::new);
        reg(EntityEndWyrm.class, RenderEndWyrm::new);
        reg(EntityEndSkeleton.class, RenderEndSkeleton::new);
        reg(EntityPix.class, RenderPix::new);
        reg(EntityLunarZorp.class, RenderLunarZorp::new);
        reg(EntityLunaling.class, RenderLunaling::new);
        reg(EntityCracky.class, RenderCracky::new);
        reg(EntitySupaika.class, RenderSupaika::new);
        reg(EntitySlush.class, RenderSlush::new);
        reg(EntitySkipper.class, RenderSkipper::new);
        reg(EntityBlastlingBall.class, manager -> new RenderBlastlingBall(manager, 0.6f));
        reg(EntitySnarelingBall.class, manager -> new RenderSnarelingBall(manager, 0.6f));
        reg(EntityThrowdableBone.class, manager -> new RenderThrowdableBone(manager, 0.8f));
    }
}