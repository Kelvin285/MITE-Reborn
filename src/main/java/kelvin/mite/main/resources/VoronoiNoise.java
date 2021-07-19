package kelvin.mite.main.resources;

import java.util.Random;

public class VoronoiNoise {
	private static long seed = new Random().nextLong();
	private static Random random = new Random(seed);
	public static void SetSeed(long s) {
		seed = s;
	}
	public static double GetNoise(double x, double z, double biome_size) {
		x /= biome_size;
		z /= biome_size;
		random.setSeed((long)(seed + x + z));
		return random.nextDouble();
	}
}
