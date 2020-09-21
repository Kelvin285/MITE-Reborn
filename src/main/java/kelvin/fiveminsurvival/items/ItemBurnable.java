package kelvin.fiveminsurvival.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemBurnable extends Item {

	public int burnTime;
	
	public ItemBurnable(Properties properties, int burnTime) {
		super(properties);
		this.burnTime = burnTime;
	}
	
	public int getBurnTime(ItemStack stack) {
		return this.burnTime;
	}
	
	
}
