package kelvin.mite.main;

import kelvin.mite.registry.Initialization;
import net.fabricmc.api.ModInitializer;
import net.minecraft.world.gen.feature.StructureFeature;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Mite implements ModInitializer {
	public static boolean debug = false;
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		try {
			Field field = StructureFeature.class.getDeclaredField("LAND_MODIFYING_STRUCTURES");
			field.get(null);
			debug = true;
		} catch (Exception e) {
			debug = false;
		}

		System.out.println("Hello Fabric world!");
		Initialization.Init();
	}
}
