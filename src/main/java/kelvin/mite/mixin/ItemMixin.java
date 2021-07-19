package kelvin.mite.mixin;

import org.spongepowered.asm.mixin.Mixin;

import kelvin.mite.main.resources.ToolDecayRates;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(Item.class)
public class ItemMixin {
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
}
