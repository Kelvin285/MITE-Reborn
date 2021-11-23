package kelvin.mite.mixin;

import kelvin.mite.registry.ItemRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;

import kelvin.mite.main.resources.ToolDecayRates;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

@Mixin(Item.class)
public class ItemMixin {
	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		stack.damage(ToolDecayRates.GetDecayRateAgainstEnemy(stack.getItem()), attacker, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
		if (stack.getItem() == Items.STICK || stack.getItem() == ItemRegistry.BRANCH) {
			if (new Random().nextInt(5) == 0) {
				target.sendToolBreakStatus(Hand.MAIN_HAND);
				stack.decrement(1);
				attacker.world.playSound((PlayerEntity)attacker, attacker.getBlockPos(), SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.PLAYERS, 1.0f, 1.0f);
			}
		}
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
