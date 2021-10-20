package kelvin.mite.items;

import net.minecraft.block.CropBlock;
import net.minecraft.block.MushroomBlock;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemManure extends Item {

	public ItemManure(Settings properties) {
		super(properties);
	}
	
	public ActionResult useOnBlock(ItemUsageContext context) {
		World world = context.getWorld();
		BlockPos pos = context.getBlockPos();
		
		if (pos != null) {
			if (world.getBlockState(pos).getBlock() instanceof MushroomBlock) {
				BlockPos blockpos = context.getBlockPos();
			    BlockPos blockpos1 = blockpos.offset(context.getSide());
			      if (BoneMealItem.useOnFertilizable(context.getStack(), world, blockpos)) {
			         if (!world.isClient) {
			            world.syncWorldEvent(2005, blockpos, 0);
			         }
	
			         return ActionResult.SUCCESS;
			      }
			}
			if (world.getBlockState(pos).getBlock() instanceof CropBlock) {
				return ActionResult.FAIL;
//				WorldStateHolder stateHolder = WorldStateHolder.get(world);
//				for (int i = 0; i < stateHolder.crops.size(); i++) {
//					PlantState state = stateHolder.crops.get(i);
//					if (state.pos.equals(pos) && !state.fertilized) {
//						state.fertilized = true;
//						context.getItem().shrink(1);
//						return ActionResultType.SUCCESS;
//					}
//				}
			}
		}
		return super.useOnBlock(context);
	}
	
	public int getBurnTime(ItemStack stack) {
		return 50;
	}
	
}
