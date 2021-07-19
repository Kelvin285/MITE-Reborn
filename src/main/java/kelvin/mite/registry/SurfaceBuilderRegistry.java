package kelvin.mite.registry;

import java.util.ArrayList;

import kelvin.mite.world.MiteSurfaceConfig;
import kelvin.mite.world.StoneConfiguration;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public class SurfaceBuilderRegistry {
	
	public static ArrayList<StoneConfiguration> stone_configs = new ArrayList<StoneConfiguration>();
	
	public static ArrayList<MiteSurfaceConfig> configs = new ArrayList<MiteSurfaceConfig>();
	
	public static void RegisterSurfaceBuilders() {
		

		Block[] stone = {Blocks.STONE, Blocks.ANDESITE, Blocks.DIORITE, Blocks.GRANITE, Blocks.STONE};
		Block[] sand = {Blocks.SAND, Blocks.RED_SAND, BlockRegistry.ANDESITE_SAND, BlockRegistry.DIORITE_SAND, BlockRegistry.GRANITE_SAND, BlockRegistry.LIMESTONE_SAND};
		Block[] gravel = {Blocks.GRAVEL, BlockRegistry.ANDESITE_GRAVEL, BlockRegistry.DIORITE_GRAVEL, BlockRegistry.DIORITE_SAND, BlockRegistry.SANDSTONE_GRAVEL, BlockRegistry.RED_SANDSTONE_GRAVEL};
		Block[] dirt = {Blocks.DIRT, Blocks.COARSE_DIRT};
		
		
		for (int i = 0; i < stone.length; i++) {
			for (int j = 0; j < sand.length; j++) {
				for (int k = 0; k < gravel.length; k++) {
					for (int l = 0; l < dirt.length; l++) {
						stone_configs.add(new StoneConfiguration(sand[j], stone[i], gravel[k], dirt[l]));
					}
				}
			}
		}
		
		
		for (int i = 0; i < stone_configs.size(); i++) {
			for (MiteSurfaceConfig.SurfaceType surface_type : MiteSurfaceConfig.SurfaceType.values()) {
				for (double j = 0; j < 1; j+= 0.1) {
					configs.add(new MiteSurfaceConfig(surface_type, stone_configs.get(i), j));
				}
			}
		}
	}
}
