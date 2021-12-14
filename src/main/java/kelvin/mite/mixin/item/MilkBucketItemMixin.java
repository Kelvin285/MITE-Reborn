package kelvin.mite.mixin.item;

import kelvin.mite.main.resources.MiteHungerManager;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MilkBucketItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MilkBucketItem.class)
public class MilkBucketItemMixin {

    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (user instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)user;
            Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
            serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat((MilkBucketItem)(Object)this));
        }

        if (user instanceof PlayerEntity && !((PlayerEntity)user).getAbilities().creativeMode) {
            stack.decrement(1);
        }

        if (!world.isClient) {
            user.clearStatusEffects();
            if (user instanceof ServerPlayerEntity) {
                HungerManager hungerManager = ((ServerPlayerEntity)user).getHungerManager();
                hungerManager.setFoodLevel(hungerManager.getFoodLevel() + 3);
                ((MiteHungerManager)hungerManager).addSaturation(MiteHungerManager.HungerCategory.DAIRY, 4);
            }
        }

        return stack.isEmpty() ? new ItemStack(Items.BUCKET) : stack;
    }
}
