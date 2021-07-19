package kelvin.mite.blocks;

import net.minecraft.block.AbstractBlock.Settings;
import net.minecraft.block.CraftingTableBlock;

public class MITECraftingTableBlock extends CraftingTableBlock {

	public final int table_level;
	
	public MITECraftingTableBlock(Settings settings, int table_level) {
		super(settings);
		this.table_level = table_level;
	}
	
}
