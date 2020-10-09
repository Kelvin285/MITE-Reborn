package kelvin.fiveminsurvival.blocks;

import net.minecraft.block.AirBlock;

public class CustomAirBlock extends AirBlock {

	public CustomAirBlock(String name, Properties properties) {
		super(properties);
		this.setRegistryName(name);
	}
	
}
