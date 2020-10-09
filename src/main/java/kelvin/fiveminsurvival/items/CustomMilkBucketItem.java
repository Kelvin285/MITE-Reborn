package kelvin.fiveminsurvival.items;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MilkBucketItem;
import net.minecraft.item.UseAction;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class CustomMilkBucketItem extends MilkBucketItem {
   public CustomMilkBucketItem(Item.Properties builder) {
      super(builder);
   }
   
   public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
      if (!worldIn.isRemote) entityLiving.curePotionEffects(stack);

      if (entityLiving instanceof ServerPlayerEntity) {
         ServerPlayerEntity serverplayerentity = (ServerPlayerEntity)entityLiving;
         CriteriaTriggers.CONSUME_ITEM.trigger(serverplayerentity, stack);
         serverplayerentity.addStat(Stats.ITEM_USED.get(this));
         serverplayerentity.getFoodStats().addStats(1, 0);
      }

      if (entityLiving instanceof PlayerEntity && !((PlayerEntity)entityLiving).abilities.isCreativeMode) {
         stack.shrink(1);
      }

      if (!worldIn.isRemote) {
         entityLiving.clearActivePotions();
         
      }

      return stack.isEmpty() ? new ItemStack(Items.BUCKET) : stack;
   }

}