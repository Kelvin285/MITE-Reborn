package kelvin.mite.registry;

import java.util.List;
import java.util.function.Supplier;

import com.google.common.base.Suppliers;

import kelvin.mite.world.gen.MiteSimpleBlockStateProvider;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.*;
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

		//public Builder(BlockStateProvider trunkProvider, TrunkPlacer trunkPlacer, BlockStateProvider foliageProvider, FoliagePlacer foliagePlacer, FeatureSize minimumSize) {

		THIN_BIRCH = register("mite:thin_birch",
				Feature.TREE.configure((new TreeFeatureConfig.Builder(new MiteSimpleBlockStateProvider(BlockRegistry.THIN_BIRCH_LOG.getDefaultState()),
						new StraightTrunkPlacer(5, 2, 0), new MiteSimpleBlockStateProvider(Blocks.BIRCH_LEAVES.getDefaultState()), new BlobFoliagePlacer(

								ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3),
						new TwoLayersFeatureSize(1, 0, 1)))
								.ignoreVines().build()));

		for (Biome biome : BiomeRegistry.biomes) {
			for (List<Supplier<PlacedFeature>> list : biome.getGenerationSettings().getFeatures()) {
				for (Supplier<PlacedFeature> features : list) {
					if (
							features == TreeConfiguredFeatures.BIRCH ||
							features == TreeConfiguredFeatures.BIRCH_BEES_0002 ||
							features == TreeConfiguredFeatures.BIRCH_BEES_002 ||
							features == TreeConfiguredFeatures.BIRCH_BEES_005 ||
							features == TreeConfiguredFeatures.SUPER_BIRCH_BEES_0002) {
						if (!BiomeRegistry.has_birch.contains(biome)) {
							BiomeRegistry.has_birch.add(biome);
						}
					}
					
					if (features == TreeConfiguredFeatures.OAK ||
							features == TreeConfiguredFeatures.OAK_BEES_0002 ||
							features == TreeConfiguredFeatures.OAK_BEES_002 ||
							features == TreeConfiguredFeatures.OAK_BEES_005 ||
							features == TreeConfiguredFeatures.FANCY_OAK ||
							features == TreeConfiguredFeatures.FANCY_OAK_BEES_0002 ||
							features == TreeConfiguredFeatures.FANCY_OAK_BEES_002 ||
							features == TreeConfiguredFeatures.FANCY_OAK_BEES_005 ||
							features == TreeConfiguredFeatures.SWAMP_OAK) {
						if (!BiomeRegistry.has_oak.contains(biome)) {
							BiomeRegistry.has_oak.add(biome);
						}
					}
					
					if (features == TreeConfiguredFeatures.SPRUCE || features == TreeConfiguredFeatures.MEGA_SPRUCE) {
						if (!BiomeRegistry.has_spruce.contains(biome)) {
							BiomeRegistry.has_spruce.add(biome);
						}
					}
				}
			}
		}
	}
}
