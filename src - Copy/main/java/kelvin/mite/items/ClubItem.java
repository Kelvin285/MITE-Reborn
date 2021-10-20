package kelvin.mite.items;

import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;

public class ClubItem extends SwordItem {

	public ClubItem(ToolMaterial tier, int attackDamageIn, float attackSpeedIn, Settings builder) {
		super(tier, attackDamageIn, attackSpeedIn, builder);
	}
	
	public int getBurnTime(ItemStack stack) {
		return 450;
	}

}
