package kelvin.mite.registry;

import java.lang.reflect.Field;
import java.util.Map;

import kelvin.mite.main.resources.Resources;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.EntityRenderers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;

public class EntityRegistry {
	
	
	
	@SuppressWarnings("unchecked")
	private static <T extends Entity> void register_model(EntityType<? extends T> type, EntityRendererFactory<T> factory) {
		Map<EntityType<?>, EntityRendererFactory<?>> rendererFactories = null;
		Field rf = EntityRenderers.class.getDeclaredFields()[1];
		try {
			Resources.makeFieldAccessible(rf);
			
			rendererFactories = (Map<EntityType<?>, EntityRendererFactory<?>>) rf.get(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		rendererFactories.put(type, factory);
	}
	
	public static void Register() {
//		BASIC_TREE = Registry.register(
//				Registry.ENTITY_TYPE,
//				new Identifier("trewrite", "basic_tree"),
//				FabricEntityTypeBuilder.create(SpawnGroup.MISC, BasicTreeEntity::new).size(EntityDimensions.fixed(2, 8)).build()
//				);
		
		RegisterAttributes();
		RegisterModels();
	}
	
	public static void RegisterAttributes() {
//		FabricDefaultAttributeRegistry.register(BASIC_TREE, LivingEntity.createLivingAttributes());

	}
	
	public static void RegisterModels() {
//		EntityRendererRegistry.INSTANCE.register(BASIC_TREE,
//				(context) -> {
//					return new BasicTreeRenderer(context);
//				});
	}
}
