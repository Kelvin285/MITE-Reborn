package kelvin.fiveminsurvival.items;

import kelvin.fiveminsurvival.init.ItemRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class CustomBowlItem extends Item {

	public CustomBowlItem(Properties builder) {
		super(builder);
	}

	
   public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
	   double reach = playerIn.getAttribute(playerIn.REACH_DISTANCE).getBaseValue();
       RayTraceResult result = worldIn.rayTraceBlocks(new RayTraceContext(playerIn.getEyePosition(0), playerIn.getEyePosition(0).add(playerIn.getForward().mul(new Vec3d(reach, reach, reach))), RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.ANY, playerIn));
	   
       if (result != null) {
    	   if (result instanceof BlockRayTraceResult) {
    		  if (worldIn.getBlockState(((BlockRayTraceResult)result).getPos()).getBlock() == Blocks.WATER) {
    			  EquipmentSlotType type = EquipmentSlotType.MAINHAND;
    			  if (handIn == Hand.OFF_HAND) {
    				  type = EquipmentSlotType.OFFHAND;
    			  }
    			  playerIn.setItemStackToSlot(type, new ItemStack(playerIn.getHeldItem(handIn).getItem(), playerIn.getHeldItem(handIn).getCount() - 1));
    			  playerIn.addItemStackToInventory(new ItemStack(ItemRegistry.WATER_BOWL.get(), 1));
    		  }
    	   }
       }
       
	   return ActionResult.resultFail(playerIn.getHeldItem(handIn));
   }
	
}
