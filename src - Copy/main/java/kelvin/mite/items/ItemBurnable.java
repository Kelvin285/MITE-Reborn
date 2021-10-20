package kelvin.mite.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemBurnable extends Item {

	public int burnTime;
	
	public ItemBurnable(Settings properties, int burnTime) {
		super(properties);
		this.burnTime = burnTime;
	}
	
	public int getBurnTime(ItemStack stack) {
		return this.burnTime;
	}
	
	
}
