package kelvin.mite.main;

import kelvin.mite.main.resources.SaveableVec3;
import kelvin.mite.registry.Initialization;
import net.fabricmc.api.ModInitializer;
import net.minecraft.world.gen.feature.StructureFeature;

import java.lang.reflect.Field;
import java.util.HashMap;

public class Mite implements ModInitializer {
	public static boolean debug = false;

	public static boolean client = false;

	public static double time_increment = 20.0D / 40.0D;

	public static long ticks_per_day = 24000;

	public static final float TicksInYear = ticks_per_day * 120;
	public static final long StartingDay = ticks_per_day * 20; // sometime in spring
	public static long season_time = StartingDay;

	public static long day_time = 0;

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

		Package[] packages = Package.getPackages();

		for (int i = 0; i < packages.length; i++) {
			if (packages[i].getName().equals("net.minecraft.client.main")) {
				client = true;
			}
		}
		System.out.println("Hello Fabric world!");
		Initialization.Init();
	}
}
