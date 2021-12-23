package kelvin.mite.registry;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import kelvin.mite.entity.BearEntity;
import kelvin.mite.entity.TurkeyEntity;
import kelvin.mite.entity.rendering.turkey.TurkeyRenderer;
import kelvin.mite.main.Mite;
import kelvin.mite.main.resources.Resources;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.Weight;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.TreeConfiguredFeatures;

public class EntityRegistry {
	
	


	public static EntityType<TurkeyEntity> TURKEY;
	public static EntityType<BearEntity> BEAR;
	
	public static void Register() {
		TURKEY = Registry.register(
				Registry.ENTITY_TYPE,
				new Identifier("mite", "turkey"),
				FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, TurkeyEntity::new).size(EntityDimensions.fixed(0.5f, 0.5f)).build()
		);
		BEAR = Registry.register(
				Registry.ENTITY_TYPE,
				new Identifier("mite", "bear"),
				FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, BearEntity::new).size(EntityDimensions.fixed(0.5f, 0.5f)).build()

		);
//		BASIC_TREE = Registry.register(
//				Registry.ENTITY_TYPE,
//				new Identifier("trewrite", "basic_tree"),
//				FabricEntityTypeBuilder.create(SpawnGroup.MISC, BasicTreeEntity::new).size(EntityDimensions.fixed(2, 8)).build()
//				);
		
		RegisterAttributes();
		RegisterSpawns();
	}
	
	public static void RegisterAttributes() {
//		FabricDefaultAttributeRegistry.register(BASIC_TREE, LivingEntity.createLivingAttributes());
		FabricDefaultAttributeRegistry.register(TURKEY, TurkeyEntity.createChickenAttributes());
		FabricDefaultAttributeRegistry.register(BEAR, BearEntity.createPolarBearAttributes());
	}
	

	public static void RegisterSpawns() {

		BiomeModifications.addSpawn(BiomeSelectors.categories(Biome.Category.FOREST, Biome.Category.TAIGA), TURKEY.getSpawnGroup(), TURKEY, 4, 2, 4);
		BiomeModifications.addSpawn(BiomeSelectors.categories(Biome.Category.FOREST, Biome.Category.TAIGA), BEAR.getSpawnGroup(), BEAR, 8, 1, 2);

	}
}
