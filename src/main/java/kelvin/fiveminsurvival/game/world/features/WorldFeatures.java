package kelvin.fiveminsurvival.game.world.features;

import java.util.List;
import java.util.function.Supplier;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;

import kelvin.fiveminsurvival.game.world.FlaxFeature;
import kelvin.fiveminsurvival.game.world.MITECaveCarver;
import kelvin.fiveminsurvival.game.world.SelectBlockPlacer;
import kelvin.fiveminsurvival.init.BlockRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.GenerationStage.Carving;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.carver.ConfiguredCarver;
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
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.feature.SphereReplaceConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class WorldFeatures {
    public static FlaxFeature FLAX_FEATURE = new FlaxFeature(NoFeatureConfig.field_236558_a_);
    public static final WorldCarver<ProbabilityConfig> CAVE = new MITECaveCarver(ProbabilityConfig.field_236576_b_, 256);
    public static SpiderDenFeature SPIDER_DEN_FEATURE = new SpiderDenFeature(NoFeatureConfig.field_236558_a_);
    public static BlockClusterFeatureConfig FLAX_CONFIG;
	public static BlockClusterFeatureConfig SHINING_GRAVEL_CONFIG;
	public static BlockClusterFeatureConfig SHINING_PEA_GRAVEL_CONFIG;


    @SubscribeEvent()
    public static void onBiomeRegistry(final FMLCommonSetupEvent event) {
        FLAX_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(BlockRegistry.FLAX.get().getDefaultState()), new SimpleBlockPlacer())).tries(2).build();
        SHINING_GRAVEL_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(BlockRegistry.SHINING_GRAVEL.get().getDefaultState()), new SelectBlockPlacer(Blocks.GRAVEL))).tries(4).build();
        SHINING_PEA_GRAVEL_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(BlockRegistry.SHINING_PEA_GRAVEL.get().getDefaultState()), new SelectBlockPlacer(BlockRegistry.SHINING_GRAVEL.get()))).tries(4).build();
        Biome.BIOMES_CODEC.map((Function<? super List<Supplier<Biome>>, ? extends Biome>) (a) -> {
        	Biome BIOME = null;
        	for (Supplier<Biome> supplier : a) {
        		BIOME = supplier.get();
            	Biome biomeIn = supplier.get();
            	List<List<Supplier<ConfiguredFeature<?, ?>>>> feature_map = biomeIn.getGenerationSettings().getFeatures();
            	
            	boolean hasSweetBerryBushFeature = false;
            	List<Supplier<ConfiguredFeature<?, ?>>> VEGETAL_DECORATION = null;
            	List<Supplier<ConfiguredFeature<?, ?>>> UNDERGROUND_ORES = null;
            	List<Supplier<ConfiguredFeature<?, ?>>> SURFACE_STRUCTURES = null;
            	for (List<Supplier<ConfiguredFeature<?, ?>>> features : feature_map) {
            		for (int i = 0; i < features.size(); i++) {
                		ConfiguredFeature<?, ?> feature = features.get(i).get();
                		if (feature.config == Features.PATCH_BERRY_BUSH) {
                			features.remove(features.get(i));
                			hasSweetBerryBushFeature = true;
                			VEGETAL_DECORATION = features;
                		}
                		if (feature.config == Features.ORE_COAL) {
                			UNDERGROUND_ORES = features;
                		}
                		if (feature.config == StructureFeatures.field_244136_b.field_236269_c_) {
                			SURFACE_STRUCTURES = features;
                		}
                	}
            	}
//            	PATCH_BERRY_DECORATED = register("patch_berry_decorated",
//        				(ConfiguredFeature) PATCH_BERRY_BUSH.withPlacement(Placements.PATCH_PLACEMENT).func_242729_a(12));
            	if (hasSweetBerryBushFeature) {
            		VEGETAL_DECORATION.add(() -> {return (ConfiguredFeature<?, ?>) Features.PATCH_BERRY_BUSH.withPlacement(Placements.PATCH_PLACEMENT).func_242729_a(5);});
            	}
            	
                if (biomeIn.getTemperature() <= 0.4f)
                	VEGETAL_DECORATION.add(() -> {return (ConfiguredFeature<?, ?>) Feature.RANDOM_PATCH.withConfiguration(FLAX_CONFIG).withPlacement(Placements.PATCH_PLACEMENT).func_242729_a(5);});

                if (biomeIn.getRegistryName().equals(Biomes.RIVER.getRegistryName())) {
                	UNDERGROUND_ORES.add(() -> {
                		return Feature.DISK
        						.withConfiguration(new SphereReplaceConfig(BlockRegistry.PEA_GRAVEL.get().getDefaultState(), FeatureSpread.func_242253_a(2, 3), 2,
        								ImmutableList.of(Blocks.DIRT.getDefaultState(), Blocks.GRASS.getDefaultState(), Blocks.SAND.getDefaultState(), Blocks.GRAVEL.getDefaultState())))
        						.withPlacement(Placements.SEAGRASS_DISK_PLACEMENT);
                	});
                	UNDERGROUND_ORES.add(() -> {
                		return Feature.DISK
        						.withConfiguration(new SphereReplaceConfig(BlockRegistry.SHINING_PEA_GRAVEL.get().getDefaultState(), FeatureSpread.func_242253_a(1, 1), 1,
        								ImmutableList.of(BlockRegistry.PEA_GRAVEL.get().getDefaultState())))
        						.withPlacement(Placements.SEAGRASS_DISK_PLACEMENT);
                	});                
                }
                
                UNDERGROUND_ORES.add(() -> {
            		return Feature.DISK
    						.withConfiguration(new SphereReplaceConfig(BlockRegistry.SHINING_GRAVEL.get().getDefaultState(), FeatureSpread.func_242253_a(1, 1), 1,
    								ImmutableList.of(Blocks.GRAVEL.getDefaultState())))
    						.withPlacement(Placements.SEAGRASS_DISK_PLACEMENT);
            	});
                
                UNDERGROUND_ORES.add(() -> {
                	return (ConfiguredFeature<?, ?>) ((ConfiguredFeature<?, ?>) ((ConfiguredFeature<?, ?>) Feature.ORE
    						.withConfiguration(new OreFeatureConfig(FillerBlockType.field_241882_a, BlockRegistry.COPPER_ORE.get().getDefaultState(), 9))
    						.func_242733_d(64)).func_242728_a()).func_242731_b(20);
                });
                
                UNDERGROUND_ORES.add(() -> {
                	return (ConfiguredFeature<?, ?>) ((ConfiguredFeature<?, ?>) ((ConfiguredFeature<?, ?>) Feature.ORE
    						.withConfiguration(new OreFeatureConfig(FillerBlockType.field_241882_a, BlockRegistry.SILVER_ORE.get().getDefaultState(), 9))
    						.func_242733_d(64)).func_242728_a()).func_242731_b(20);
                });
                //new CaveWorldCarver(ProbabilityConfig.field_236576_b_, 256)
                biomeIn.getGenerationSettings().getCarvers(Carving.AIR).add(() -> {
                	return CAVE.func_242761_a(new ProbabilityConfig(0.06666667F));
                });
                
                SURFACE_STRUCTURES.add(() -> {
                	return SPIDER_DEN_FEATURE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placements.HEIGHTMAP_PLACEMENT);
                });

            }
        	return BIOME;
        } );
        
    }
}

