package kelvin.mite.items;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MilkBucketItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;

public class CustomMilkBucketItem extends MilkBucketItem {
   public CustomMilkBucketItem(Settings builder) {
      super(builder);
   }
   
   public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
      if (user instanceof ServerPlayerEntity) {
         ((ServerPlayerEntity)user).getHungerManager().add(1, 0);
      }
      return super.finishUsing(stack, world, user);
   }

}