package kelvin.fiveminsurvival.items;

import kelvin.fiveminsurvival.survival.world.PlantState;
import kelvin.fiveminsurvival.survival.world.WorldStateHolder;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.MushroomBlock;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemManure extends Item {

	public ItemManure(Properties properties) {
		super(properties);
	}
	
	public ActionResultType onItemUse(ItemUseContext context) {
		World world = context.getWorld();
		BlockPos pos = context.getPos();
		
		if (pos != null) {
			if (world.getBlockState(pos).getBlock() instanceof MushroomBlock) {
				BlockPos blockpos = context.getPos();
			    BlockPos blockpos1 = blockpos.offset(context.getFace());
			      if (BoneMealItem.applyBonemeal(context.getItem(), world, blockpos, context.getPlayer())) {
			         if (!world.isRemote) {
			            world.playEvent(2005, blockpos, 0);
			         }
	
			         return ActionResultType.SUCCESS;
			      }
			}
			if (world.getBlockState(pos).getBlock() instanceof CropsBlock) {
				WorldStateHolder stateHolder = WorldStateHolder.get(world);
				for (int i = 0; i < stateHolder.crops.size(); i++) {
					PlantState state = stateHolder.crops.get(i);
					if (state.pos.equals(pos) && !state.fertilized) {
						state.fertilized = true;
						context.getItem().shrink(1);
						return ActionResultType.SUCCESS;
					}
				}
			}
		}
		return ActionResultType.FAIL;
	}
	
	public int getBurnTime(ItemStack stack) {
		return 50;
	}
	
}
