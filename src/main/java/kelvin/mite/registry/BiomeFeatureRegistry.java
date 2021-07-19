package kelvin.mite.registry;

import java.util.List;
import java.util.function.Supplier;

import com.google.common.base.Suppliers;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

public class BiomeFeatureRegistry {
	public static ConfiguredFeature<?, ?> THIN_BIRCH;
	
	private static <FC extends FeatureConfig> ConfiguredFeature<FC, ?> register(String id,
			ConfiguredFeature<FC, ?> configuredFeature) {
		return (ConfiguredFeature<FC, ?>) Registry.<ConfiguredFeature<?, ?>>register(BuiltinRegistries.CONFIGURED_FEATURE, id, configuredFeature);
	}
	
	public static void RegisterFeatures() {
		THIN_BIRCH = register("mite:thin_birch",
				Feature.TREE.configure((new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(BlockRegistry.THIN_BIRCH_LOG.getDefaultState()),
						new StraightTrunkPlacer(5, 2, 0), new SimpleBlockStateProvider(Blocks.BIRCH_LEAVES.getDefaultState()),
						new SimpleBlockStateProvider(Blocks.BIRCH_SAPLING.getDefaultState()), new BlobFoliagePlacer(

								ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3),
						new TwoLayersFeatureSize(1, 0, 1)))
								.ignoreVines().build()));
		
		for (Biome biome : BiomeRegistry.biomes) { 
			for (List<Supplier<ConfiguredFeature<?, ?>>> list : biome.getGenerationSettings().getFeatures()) {
				for (Supplier<ConfiguredFeature<?, ?>> features : list) {
					
					if (features.get() == ConfiguredFeatures.TREES_BIRCH ||
							features.get() == ConfiguredFeatures.BIRCH ||
							features.get() == ConfiguredFeatures.BIRCH_BEES_0002 ||
							features.get() == ConfiguredFeatures.BIRCH_BEES_002 ||
							features.get() == ConfiguredFeatures.BIRCH_BEES_005 ||
							features.get() == ConfiguredFeatures.BIRCH_OTHER || 
							features.get() == ConfiguredFeatures.SUPER_BIRCH_BEES_0002 ||
							features.get() == ConfiguredFeatures.BIRCH_TALL) {
						if (!BiomeRegistry.has_birch.contains(biome)) {
							BiomeRegistry.has_birch.add(biome);
						}
					}
					
					if (features.get() == ConfiguredFeatures.OAK ||
							features.get() == ConfiguredFeatures.OAK_BEES_0002 ||
							features.get() == ConfiguredFeatures.OAK_BEES_002 ||
							features.get() == ConfiguredFeatures.OAK_BEES_005 ||
							features.get() == ConfiguredFeatures.FANCY_OAK ||
							features.get() == ConfiguredFeatures.FANCY_OAK_BEES_0002 ||
							features.get() == ConfiguredFeatures.FANCY_OAK_BEES_002 ||
							features.get() == ConfiguredFeatures.FANCY_OAK_BEES_005 ||
							features.get() == ConfiguredFeatures.SWAMP_OAK) {
						if (!BiomeRegistry.has_oak.contains(biome)) {
							BiomeRegistry.has_oak.add(biome);
						}
					}
					
					if (features.get() == ConfiguredFeatures.SPRUCE || features.get() == ConfiguredFeatures.MEGA_SPRUCE ||
							features.get() == ConfiguredFeatures.TREES_GIANT_SPRUCE) {
						if (!BiomeRegistry.has_spruce.contains(biome)) {
							BiomeRegistry.has_spruce.add(biome);
						}
					}
				}
			}
		}
	}
}
