package kelvin.mite.items;

import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.MushroomBlock;
import net.minecraft.block.SaplingBlock;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;

public class MITEBoneMealItem extends BoneMealItem {

	public MITEBoneMealItem(Settings p_i50055_1_) {
		super(p_i50055_1_);
	}
	
	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		BlockState blockstate = context.getWorld().getBlockState(context.getBlockPos());
		if (blockstate.getBlock() instanceof CropBlock || blockstate.getBlock() instanceof MushroomBlock || blockstate.getBlock() instanceof SaplingBlock) return ActionResult.FAIL;
		return super.useOnBlock(context);
	}

}
