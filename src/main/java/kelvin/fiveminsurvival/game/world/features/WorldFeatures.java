package kelvin.fiveminsurvival.game.world.features;

import java.util.List;

import com.google.common.collect.Lists;

import kelvin.fiveminsurvival.game.world.FlaxFeature;
import kelvin.fiveminsurvival.game.world.MITECaveCarver;
import kelvin.fiveminsurvival.game.world.SelectBlockPlacer;
import kelvin.fiveminsurvival.init.BlockRegistry;
import kelvin.fiveminsurvival.init.VanillaOverrides;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.TempCategory;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.feature.SphereReplaceConfig;
import net.minecraft.world.gen.feature.structure.VillageStructure;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
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

        for (Biome biomeIn : Biome.BIOMES) {
        	
        	List<ConfiguredFeature<?, ?>> features = biomeIn.getFeatures(Decoration.VEGETAL_DECORATION);
        	
        	boolean hasSweetBerryBushFeature = false;
        	for (int i = 0; i < features.size(); i++) {
        		ConfiguredFeature<?, ?> feature = features.get(i);
        		if (feature.config == DefaultBiomeFeatures.SWEET_BERRY_BUSH_CONFIG) {
        			biomeIn.getFeatures(Decoration.VEGETAL_DECORATION).remove(feature);
        			hasSweetBerryBushFeature = true;
        		}
        	}
        	
        	if (hasSweetBerryBushFeature) {
        		DefaultBiomeFeatures.addSparseBerryBushes(biomeIn);
        	}
        	
        	
            if (biomeIn.getTempCategory() == TempCategory.COLD || biomeIn.getTempCategory() == TempCategory.MEDIUM)
                biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(FLAX_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP.configure(new FrequencyConfig(2))));

            if (biomeIn == Biomes.RIVER) {
                biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.DISK.withConfiguration(new SphereReplaceConfig(BlockRegistry.PEA_GRAVEL.get().getDefaultState(), 6, 2, Lists.newArrayList(Blocks.DIRT.getDefaultState(), Blocks.GRASS_BLOCK.getDefaultState(), Blocks.SAND.getDefaultState()))).withPlacement(Placement.COUNT_TOP_SOLID.configure(new FrequencyConfig(2))));
                biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.DISK.withConfiguration(new SphereReplaceConfig(BlockRegistry.SHINING_PEA_GRAVEL.get().getDefaultState(), 3, 2, Lists.newArrayList(BlockRegistry.PEA_GRAVEL.get().getDefaultState()))).withPlacement(Placement.COUNT_TOP_SOLID.configure(new FrequencyConfig(2))));
            }
            biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.DISK.withConfiguration(new SphereReplaceConfig(BlockRegistry.SHINING_GRAVEL.get().getDefaultState(), 3, 2, Lists.newArrayList(Blocks.GRAVEL.getDefaultState()))).withPlacement(Placement.COUNT_TOP_SOLID.configure(new FrequencyConfig(2))));

            biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.RANDOM_PATCH.withConfiguration(SHINING_GRAVEL_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(2))));
            biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.RANDOM_PATCH.withConfiguration(SHINING_PEA_GRAVEL_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(2))));

            biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockRegistry.COPPER_ORE.get().getDefaultState(), 9)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(20, 0, 0, 128))));
            biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockRegistry.SILVER_ORE.get().getDefaultState(), 9)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(20, 0, 0, 128))));
            biomeIn.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(CAVE, new ProbabilityConfig(0.14285715F)));
            biomeIn.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, SPIDER_DEN_FEATURE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.CHANCE_HEIGHTMAP.configure(new ChanceConfig(1))));

        }
    }
}

