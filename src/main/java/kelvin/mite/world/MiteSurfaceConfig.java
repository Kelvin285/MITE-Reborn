package kelvin.mite.world;

public class MiteSurfaceConfig {
	public enum SurfaceType {
		SAND_GRASS,
		DIRT_GRASS
	}
	
	public final SurfaceType surface;
	public final double tree_density;
	public final StoneConfiguration stone_config;
	
	public MiteSurfaceConfig(SurfaceType surface, StoneConfiguration stone_config, double tree_density) {
		this.surface = surface;
		this.tree_density = tree_density;
		this.stone_config = stone_config;
	}
}
