package kelvin.fiveminsurvival.game.world.features;

import java.util.List;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableList;

import kelvin.fiveminsurvival.game.world.FlaxFeature;
import kelvin.fiveminsurvival.game.world.MITECaveCarver;
import kelvin.fiveminsurvival.game.world.SelectBlockPlacer;
import kelvin.fiveminsurvival.init.BlockRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;
import net.minecraft.world.biome.Biome.TemperatureModifier;
import net.minecraft.world.gen.GenerationStage.Carving;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.Features.Placements;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig.FillerBlockType;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.feature.SphereReplaceConfig;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.FORGE)
public class WorldFeatures {
    public static FlaxFeature FLAX_FEATURE = new FlaxFeature(NoFeatureConfig.field_236558_a_);
    public static final WorldCarver<ProbabilityConfig> CAVE = new MITECaveCarver(ProbabilityConfig.field_236576_b_, 256);
    public static SpiderDenFeature SPIDER_DEN_FEATURE = new SpiderDenFeature(NoFeatureConfig.field_236558_a_);
    public static BlockClusterFeatureConfig FLAX_CONFIG;
	public static BlockClusterFeatureConfig SHINING_GRAVEL_CONFIG;
	public static BlockClusterFeatureConfig SHINING_PEA_GRAVEL_CONFIG;

	@SuppressWarnings("unchecked")
	@SubscribeEvent
    public static void RegisterBiomeFeatures(BiomeLoadingEvent event) {
    	
    	FLAX_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(BlockRegistry.FLAX.get().getDefaultState()), new SimpleBlockPlacer())).tries(2).build();
        SHINING_GRAVEL_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(BlockRegistry.SHINING_GRAVEL.get().getDefaultState()), new SelectBlockPlacer(Blocks.GRAVEL))).tries(4).build();
        SHINING_PEA_GRAVEL_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(BlockRegistry.SHINING_PEA_GRAVEL.get().getDefaultState()), new SelectBlockPlacer(BlockRegistry.SHINING_GRAVEL.get()))).tries(4).build();
        
        List<Supplier<ConfiguredFeature<?, ?>>> VEGETAL_DECORATION = event.getGeneration().getFeatures(Decoration.VEGETAL_DECORATION); 
        boolean hasSweetBerryBushFeature = false;
    	for (Supplier<ConfiguredFeature<?, ?>> supplier : VEGETAL_DECORATION) {
    		ConfiguredFeature<?, ?> feature = supplier.get();

    		if (feature.config == Features.PATCH_BERRY_BUSH) {
    			VEGETAL_DECORATION.remove(supplier);
    			hasSweetBerryBushFeature = true;
    		}
    	}
    	
//    	List<Supplier<StructureFeature<?, ?>>> STRUCTURES = event.getGeneration().getStructures();
//    	List<Supplier<StructureFeature<?, ?>>> NEW_STRUCTURES = new ArrayList<Supplier<StructureFeature<?, ?>>>();
//    	for (int i = 0; i < STRUCTURES.size(); i++) {
//    		Supplier<StructureFeature<?, ?>> supplier = STRUCTURES.get(i);
//    		StructureFeature<?, ?> structure = supplier.get();
//    		if (structure.field_236268_b_ instanceof VillageStructure) {
//    			NEW_STRUCTURES.add(() -> {return ((CustomVillageFeature)StructureRegistry.VILLAGE.get()).func_236391_a_((VillageConfig)structure.field_236269_c_);});
//    			STRUCTURES.remove(supplier);
//    		}
//    	}
//    	for (Supplier<StructureFeature<?, ?>> supplier : NEW_STRUCTURES) {
//    		STRUCTURES.add(supplier);
//    	}
//    	
//    	for (int i = 0; i < STRUCTURES.size(); i++) {
//    		Supplier<StructureFeature<?, ?>> supplier = STRUCTURES.get(i);
//    		StructureFeature<?, ?> structure = supplier.get();
//    		System.out.println(structure.field_236268_b_.getRegistryName());
//    	}
            	
    	if (hasSweetBerryBushFeature) {
    		event.getGeneration().withFeature(Decoration.VEGETAL_DECORATION, Features.PATCH_BERRY_BUSH.withPlacement(Placements.PATCH_PLACEMENT).func_242729_a(5));
    	}
    	
        if (event.getClimate().temperature <= 0.8f &&
        		event.getClimate().precipitation == RainType.RAIN &&
        		event.getClimate().temperatureModifier != TemperatureModifier.FROZEN &&
        		event.getCategory() != Category.DESERT)
        	event.getGeneration().withFeature(Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(FLAX_CONFIG).withPlacement(Placements.PATCH_PLACEMENT).func_242729_a(5));

        if (event.getCategory() == Category.RIVER) {
        	event.getGeneration().withFeature(Decoration.UNDERGROUND_ORES,
        			Feature.DISK
					.withConfiguration(new SphereReplaceConfig(BlockRegistry.PEA_GRAVEL.get().getDefaultState(), FeatureSpread.func_242253_a(2, 3), 2,
							ImmutableList.of(Blocks.DIRT.getDefaultState(), Blocks.GRASS.getDefaultState(), Blocks.SAND.getDefaultState(), Blocks.GRAVEL.getDefaultState())))
					.withPlacement(Placements.SEAGRASS_DISK_PLACEMENT)
        			);
        	
        	event.getGeneration().withFeature(Decoration.UNDERGROUND_ORES,
        			Feature.DISK
					.withConfiguration(new SphereReplaceConfig(BlockRegistry.SHINING_PEA_GRAVEL.get().getDefaultState(), FeatureSpread.func_242253_a(1, 1), 1,
							ImmutableList.of(BlockRegistry.PEA_GRAVEL.get().getDefaultState())))
					.withPlacement(Placements.SEAGRASS_DISK_PLACEMENT)
        			);         
        }
        event.getGeneration().withFeature(Decoration.UNDERGROUND_ORES,
        		Feature.DISK
				.withConfiguration(new SphereReplaceConfig(BlockRegistry.SHINING_GRAVEL.get().getDefaultState(), FeatureSpread.func_242253_a(1, 1), 1,
						ImmutableList.of(Blocks.GRAVEL.getDefaultState())))
				.withPlacement(Placements.SEAGRASS_DISK_PLACEMENT)
    			);
        
        event.getGeneration().withFeature(Decoration.UNDERGROUND_ORES,
        		(ConfiguredFeature<?, ?>) ((ConfiguredFeature<?, ?>) ((ConfiguredFeature<?, ?>) Feature.ORE
    					.withConfiguration(new OreFeatureConfig(FillerBlockType.field_241882_a, BlockRegistry.COPPER_ORE.get().getDefaultState(), 9))
    					.func_242733_d(64)).func_242728_a()).func_242731_b(20)
    			);
        
        event.getGeneration().withFeature(Decoration.UNDERGROUND_ORES,
        		(ConfiguredFeature<?, ?>) ((ConfiguredFeature<?, ?>) ((ConfiguredFeature<?, ?>) Feature.ORE
    					.withConfiguration(new OreFeatureConfig(FillerBlockType.field_241882_a, BlockRegistry.SILVER_ORE.get().getDefaultState(), 9))
    					.func_242733_d(64)).func_242728_a()).func_242731_b(20)
    			);
        
        event.getGeneration().withCarver(Carving.AIR, CAVE.func_242761_a(new ProbabilityConfig(0.06666667F)));
        
        event.getGeneration().withFeature(Decoration.SURFACE_STRUCTURES, SPIDER_DEN_FEATURE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placements.HEIGHTMAP_PLACEMENT));
        
    }
}

