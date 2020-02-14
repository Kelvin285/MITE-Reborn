package kelvin.fiveminsurvival.survival;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.world.BlockEvent.NeighborNotifyEvent;
import net.minecraftforge.event.world.WorldEvent.Load;
import net.minecraftforge.event.world.WorldEvent.Unload;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class WorldEvents {
	
	@SubscribeEvent
	public static void handleLoadEvent(Load e) {
		if (e.getWorld() instanceof ServerWorld) {
			ServerWorld world = (ServerWorld)e.getWorld();
			world.getServer().sendMessage(new StringTextComponent("/datapack disable vanilla"));
		}
	}
	
	@SubscribeEvent
	public static void handleUnloadEvent(Unload e) {
		if (e.getWorld() instanceof ServerWorld) {
			ServerWorld world = (ServerWorld)e.getWorld();
			world.getServer().sendMessage(new StringTextComponent("/datapack disable vanilla"));
		}
	}
}
