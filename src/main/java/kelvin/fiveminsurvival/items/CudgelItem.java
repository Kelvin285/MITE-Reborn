package kelvin.fiveminsurvival.items;

import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;

public class CudgelItem extends ShortswordItem {

	public CudgelItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
		super(tier, attackDamageIn, attackSpeedIn, builder);
	}
	
	public int getBurnTime(ItemStack stack) {
		return 200;
	}

}
