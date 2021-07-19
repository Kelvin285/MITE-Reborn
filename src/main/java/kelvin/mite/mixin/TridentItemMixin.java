package kelvin.mite.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import kelvin.mite.main.resources.ToolDecayRates;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(TridentItem.class)
public class TridentItemMixin {
	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		stack.damage(ToolDecayRates.GetDecayRateAgainstEnemy(stack.getItem()), attacker, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
		return true;
	}

	public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
		if (!world.isClient && state.getHardness(world, pos) != 0.0F)
		{
			stack.damage(ToolDecayRates.GetDecayRateAgainstBlock(stack.getItem(), state), miner, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
		}
		return true;
	}
	
	
	@Inject(at = @At("HEAD"), method = "onStoppedUsing")
	public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks, CallbackInfo info) {
		if (!(user instanceof PlayerEntity))
			return;
		PlayerEntity playerEntity = (PlayerEntity) user;
		int i = ((TridentItem)(Object)this).getMaxUseTime(stack) - remainingUseTicks;
		if (i < 10)
			return;
		int j = EnchantmentHelper.getRiptide(stack);
		if (j > 0 && !playerEntity.isTouchingWaterOrRain())
			return;
		if (!world.isClient) {
			stack.damage(ToolDecayRates.GetDecayRateAgainstEnemy(stack.getItem()), playerEntity, p -> p.sendToolBreakStatus(user.getActiveHand()));
		}
	}
}
