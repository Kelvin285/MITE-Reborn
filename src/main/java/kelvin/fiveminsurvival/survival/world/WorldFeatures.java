package kelvin.fiveminsurvival.survival.world;

import com.google.common.collect.Lists;

import kelvin.fiveminsurvival.init.BlockRegistry;
import kelvin.fiveminsurvival.init.EntityRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.biome.Biome.TempCategory;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.feature.SphereReplaceConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class WorldFeatures {
    public static FlaxFeature FLAX_FEATURE = new FlaxFeature(NoFeatureConfig::deserialize);
    public static final WorldCarver<ProbabilityConfig> CAVE = new MITECaveCarver(ProbabilityConfig::deserialize, 256);
    public static SpiderDenFeature SPIDER_DEN_FEATURE = new SpiderDenFeature(NoFeatureConfig::deserialize);
    public static BlockClusterFeatureConfig FLAX_CONFIG;
	public static BlockClusterFeatureConfig SHINING_GRAVEL_CONFIG;
	public static BlockClusterFeatureConfig SHINING_PEA_GRAVEL_CONFIG;


    @SubscribeEvent()
    public static void onBiomeRegistry(final FMLCommonSetupEvent event) {
        FLAX_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(BlockRegistry.FLAX.get().getDefaultState()), new SimpleBlockPlacer())).tries(2).build();
        SHINING_GRAVEL_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(BlockRegistry.SHINING_GRAVEL.get().getDefaultState()), new SelectBlockPlacer(Blocks.GRAVEL))).tries(4).build();
        SHINING_PEA_GRAVEL_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(BlockRegistry.SHINING_PEA_GRAVEL.get().getDefaultState()), new SelectBlockPlacer(BlockRegistry.SHINING_GRAVEL.get()))).tries(4).build();

        for (Biome biomeIn : Biome.BIOMES) {
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

