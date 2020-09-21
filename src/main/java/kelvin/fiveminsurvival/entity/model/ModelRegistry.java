package kelvin.fiveminsurvival.entity.model;

import kelvin.fiveminsurvival.entity.model.render.SpearRenderer;
import kelvin.fiveminsurvival.init.EntityRegistry;
<<<<<<< HEAD
import net.minecraft.client.Minecraft;
=======
>>>>>>> 2eba99c0d696ed96f3b32c8e0a922901b317922d
import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.client.renderer.entity.SquidRenderer;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModelRegistry {

    @SubscribeEvent
    public static void registerAllModels(FMLClientSetupEvent event)
    {
        //Entity
    	RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.SPEAR_ENTITY.get(), SpearRenderer::new);
    	RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.SKELETON_ENTITY.get(), SkeletonRenderer::new);
    	RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.ATTACK_SQUID.get(), SquidRenderer::new);
    	RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.CREEPER.get(), CreeperRenderer::new);
    	RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.ZOMBIE_ENTITY.get(), ZombieRenderer::new);
<<<<<<< HEAD
    	Minecraft.getInstance().getRenderManager().register(EntityRegistry.FEATHER.get(), new ItemRenderer(Minecraft.getInstance().getRenderManager(), Minecraft.getInstance().getItemRenderer()));
    	Minecraft.getInstance().getRenderManager().register(EntityRegistry.BOWL.get(), new ItemRenderer(Minecraft.getInstance().getRenderManager(), Minecraft.getInstance().getItemRenderer()));
=======
>>>>>>> 2eba99c0d696ed96f3b32c8e0a922901b317922d


    }

}