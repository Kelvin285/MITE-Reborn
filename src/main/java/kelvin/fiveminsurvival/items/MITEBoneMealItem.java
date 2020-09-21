package kelvin.fiveminsurvival.items;

import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.MushroomBlock;
import net.minecraft.block.SaplingBlock;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;

public class MITEBoneMealItem extends BoneMealItem {

	public MITEBoneMealItem(Properties p_i50055_1_) {
		super(p_i50055_1_);
	}
	
	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		BlockState blockstate = context.getWorld().getBlockState(context.getPos());
		if (blockstate.getBlock() instanceof CropsBlock || blockstate.getBlock() instanceof MushroomBlock || blockstate.getBlock() instanceof SaplingBlock) return ActionResultType.FAIL;
		return super.onItemUse(context);
	}

}
