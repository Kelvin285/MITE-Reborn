package kelvin.mite.blocks;

import net.minecraft.block.CakeBlock;
import net.minecraft.block.Material;

public class MiteCakeBlock extends CakeBlock {

	public MiteCakeBlock(int hunger, double saturation, int happiness) {
		super(Settings.of(Material.CAKE));
	}

}
