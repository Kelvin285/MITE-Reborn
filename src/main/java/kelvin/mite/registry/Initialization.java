package kelvin.mite.registry;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.OptionalLong;
import java.util.UUID;

import com.google.common.collect.ImmutableMap;

import kelvin.mite.main.resources.Resources;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.dimension.DimensionType;

public class Initialization {
	public static String src = "resourcepacks/mite/";
	public static String en_us_lang = "{\n";
	
	private static EntityAttribute register_attribute(String id, EntityAttribute attribute) {
		return (EntityAttribute) Registry.register(Registry.ATTRIBUTE, id, attribute);
	}
	
	public static void Init() {
		BlockRegistry.StartRegisteringBlocks();
		ItemRegistry.RegisterItems();
		SurfaceBuilderRegistry.RegisterSurfaceBuilders();
		EntityRegistry.Register();
		BiomeFeatureRegistry.RegisterFeatures();
		VanillaTweaks.ApplyChanges();

		en_us_lang += "  \"block.minecraft.stone\": \"Limestone\",\n";
		en_us_lang += "  \"block.minecraft.gravel\": \"Limestone Gravel\",\n";
		en_us_lang += "}";
		en_us_lang = en_us_lang.replace(",\n}", "\n}");
		File json_state = new File(src+"assets/mite/lang/");
		json_state.mkdirs();
		json_state = new File(src+"assets/mite/lang/en_us.json");
		
		try {
			json_state.createNewFile();
			FileWriter block_model = new FileWriter(json_state);
			block_model.write(en_us_lang);
			block_model.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		WeightRegistry.Register();
	}
}
