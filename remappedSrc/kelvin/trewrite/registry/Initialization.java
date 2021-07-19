package kelvin.trewrite.registry;

import java.lang.reflect.Field;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

import kelvin.trewrite.main.resources.Resources;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.registry.Registry;

public class Initialization {
	
	private static EntityAttribute register_attribute(String id, EntityAttribute attribute) {
		return (EntityAttribute) Registry.register(Registry.ATTRIBUTE, id, attribute);
	}
	
	public static final EntityAttribute MODIFIED_MAX_HEALTH = register_attribute("trewrite.max_health",
			(new ClampedEntityAttribute("attribute.name.trewrite.max_health", 20.0D, 1.0D, 1024.0D)).setTracked(true));
	
	public static void Init() {
		ModifyPlayerDimensions();
	}
	
	private static void ModifyPlayerDimensions() {

		EntityDimensions STANDING_DIMENSIONS = EntityDimensions.changing(1.2F, 2.8F);
		Map<EntityPose, EntityDimensions> POSE_DIMENSIONS = ImmutableMap.<EntityPose, EntityDimensions>builder().put(EntityPose.STANDING, STANDING_DIMENSIONS)
				.put(EntityPose.SLEEPING, EntityDimensions.fixed(0.2F, 0.2F))
				.put(EntityPose.FALL_FLYING, EntityDimensions.changing(1.2F, 1.2F))
				.put(EntityPose.SWIMMING, EntityDimensions.changing(1.2F, 1.2F))
				.put(EntityPose.SPIN_ATTACK, EntityDimensions.changing(1.2F, 1.2F))
				.put(EntityPose.CROUCHING, EntityDimensions.changing(1.2F, 2.5F))
				.put(EntityPose.DYING, EntityDimensions.fixed(0.2F, 0.2F)).build();
		
		Field[] dimension_fields = PlayerEntity.class.getDeclaredFields();
		
		for (int i = 0; i < dimension_fields.length; i++) {
			try {
				Resources.makeFieldAccessible(dimension_fields[i]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			dimension_fields[0].set(null, STANDING_DIMENSIONS);
			dimension_fields[1].set(null, POSE_DIMENSIONS);
		}catch (Exception e) {
			
		}
		
		Field[] size_fields = EntityDimensions.class.getDeclaredFields();
		for (int i = 0; i < size_fields.length; i++) {
			try {
				Resources.makeFieldAccessible(size_fields[i]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			size_fields[0].set(EntityType.PLAYER.getDimensions(), 1.2F);
			size_fields[1].set(EntityType.PLAYER.getDimensions(), 2.8F);
		}catch (Exception e) {
			
		}
	}
}
