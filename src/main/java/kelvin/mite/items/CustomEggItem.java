package kelvin.mite.items;

import net.minecraft.item.EggItem;

public class CustomEggItem extends EggItem {

	public CustomEggItem(Settings builder) {
		super(builder);
	}

	
//   public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
//     if (playerIn.isSneaking()) {
//  	   ItemStack itemstack = playerIn.getHeldItem(handIn);
//         worldIn.playSound((PlayerEntity)null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.ENTITY_EGG_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
//         if (!worldIn.isRemote) {
//            EggEntity eggentity = new EggEntity(worldIn, playerIn);
//            eggentity.setItem(itemstack);
//            eggentity.func_234612_a_(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
//            worldIn.addEntity(eggentity);
//         }
//
//         playerIn.addStat(Stats.ITEM_USED.get(this));
//         if (!playerIn.abilities.isCreativeMode) {
//            itemstack.shrink(1);
//         }
//
//         return ActionResult.resultSuccess(itemstack);
//     }
//     if (this.isFood()) {
//         ItemStack itemstack = playerIn.getHeldItem(handIn);
//         if (playerIn.canEat(this.getFood().canEatWhenFull())) {
//            playerIn.setActiveHand(handIn);
//            return ActionResult.resultConsume(itemstack);
//         } else {
//            return ActionResult.resultFail(itemstack);
//         }
//      } else {
//         return ActionResult.resultPass(playerIn.getHeldItem(handIn));
//      }
//   }
	
}
