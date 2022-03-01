package kelvin.mite.mixin.item;

import org.spongepowered.asm.mixin.Mixin;

import kelvin.mite.main.resources.ToolDecayRates;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MiningToolItem.class)
public class MiningToolItemMixin {

	@Inject(at=@At("RETURN"), method="postHit", cancellable = true)
	public void postHit(ItemStack stack, LivingEntity target, LivingEntity attacker, CallbackInfoReturnable<Boolean> info) {
		stack.damage(ToolDecayRates.GetDecayRateAgainstEnemy(stack.getItem()) - 2, attacker, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
	}

	@Inject(at=@At("RETURN"), method="postMine", cancellable = true)
	public void postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner, CallbackInfoReturnable<Boolean> info) {
		if (!world.isClient && state.getHardness(world, pos) != 0.0F)
		{
			int rate = ToolDecayRates.GetDecayRateAgainstBlock(stack.getItem(), state) - 1;
			if (rate < 0) rate = 0;
			stack.damage(rate, miner, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
		}
	}
}
