package kelvin.fiveminsurvival.items;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;

public class BurnableBlockItem extends BlockItem {

	public int burnTime;
	
	public BurnableBlockItem(Block block, Properties properties, int burnTime) {
		super(block, properties);
		this.burnTime = burnTime;
	}
	
	public int getBurnTime(ItemStack stack) {
		return this.burnTime;
	}
	
	
}
