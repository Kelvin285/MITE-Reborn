package kelvin.mite.world;

import net.minecraft.block.Block;

public class StoneConfiguration {
	public final Block sand, stone, gravel, dirt;
	public StoneConfiguration(Block sand, Block stone, Block gravel, Block dirt) {
		this.sand = sand;
		this.stone = stone;
		this.gravel = gravel;
		this.dirt = dirt;
	}
}
