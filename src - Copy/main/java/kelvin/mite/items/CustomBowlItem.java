package kelvin.mite.items;

import net.minecraft.item.Item;

public class CustomBowlItem extends Item {

	public CustomBowlItem(Settings builder) {
		super(builder);
	}

	
//   public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
//	   double reach = playerIn.getAttribute(net.minecraftforge.common.ForgeMod.REACH_DISTANCE.get()).getBaseValue();
//       RayTraceResult result = worldIn.rayTraceBlocks(new RayTraceContext(playerIn.getEyePosition(0), playerIn.getEyePosition(0).add(playerIn.getForward().mul(new Vector3d(reach, reach, reach))), RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.ANY, playerIn));
//	   
//       if (result != null) {
//    	   if (result instanceof BlockRayTraceResult) {
//    		  if (worldIn.getBlockState(((BlockRayTraceResult)result).getPos()).getBlock() == Blocks.WATER) {
//    			  EquipmentSlotType type = EquipmentSlotType.MAINHAND;
//    			  if (handIn == Hand.OFF_HAND) {
//    				  type = EquipmentSlotType.OFFHAND;
//    			  }
//    			  playerIn.setItemStackToSlot(type, new ItemStack(playerIn.getHeldItem(handIn).getItem(), playerIn.getHeldItem(handIn).getCount() - 1));
//    			  playerIn.addItemStackToInventory(new ItemStack(ItemRegistry.WATER_BOWL.get(), 1));
//    		  }
//    	   }
//       }
//       
//	   return ActionResult.resultFail(playerIn.getHeldItem(handIn));
//   }
	
}
