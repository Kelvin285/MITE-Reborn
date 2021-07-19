package kelvin.mite.items;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;

public class CudgelItem extends KnifeItem {

	public CudgelItem(ToolMaterial tier, int attackDamageIn, float attackSpeedIn, Settings builder) {
		super(tier, attackDamageIn, attackSpeedIn, builder);
	}
	
	public int getBurnTime(ItemStack stack) {
		return 200;
	}

}
