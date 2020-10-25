package kelvin.fiveminsurvival.items;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;

public class ClubItem extends SwordItem {

	public ClubItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
		super(tier, attackDamageIn, attackSpeedIn, builder);
	}
	
	public int getBurnTime(ItemStack stack) {
		return 450;
	}

}
