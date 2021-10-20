package kelvin.mite.blocks;

import net.minecraft.block.FurnaceBlock;

public class MITEFurnaceBlock extends FurnaceBlock {

	public int HEAT_LEVEL;
    public MITEFurnaceBlock(Settings settings, int HEAT_LEVEL) {
		super(settings);
		this.HEAT_LEVEL = HEAT_LEVEL;
	}

    
}