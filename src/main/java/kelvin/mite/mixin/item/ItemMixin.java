package kelvin.mite.mixin.item;

import kelvin.mite.registry.ItemRegistry;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;

import kelvin.mite.main.resources.ToolDecayRates;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(Item.class)
public class ItemMixin {

	@Shadow
	private FoodComponent foodComponent;

	private static final FoodComponent FOOD_WHEAT_SEEDS = new FoodComponent.Builder().hunger(0).saturationModifier(1.0f).build();
	private static final FoodComponent FOOD_PUMPKIN_SEEDS = new FoodComponent.Builder().hunger(2).saturationModifier(1.0f).build();
	private static final FoodComponent FOOD_RED_MUSHROOM = new FoodComponent.Builder().hunger(1).saturationModifier(1.0f).statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 10 * 20), 1).statusEffect(new StatusEffectInstance(StatusEffects.POISON, 30 * 20), 1).build();
	private static final FoodComponent FOOD_BROWN_MUSHROOM = new FoodComponent.Builder().hunger(1).saturationModifier(1.0f).build();
	private static final FoodComponent FOOD_EGG = new FoodComponent.Builder().hunger(2).saturationModifier(1.0f).meat().build();
	private static final FoodComponent FOOD_HONEYCOMB = new FoodComponent.Builder().hunger(3).saturationModifier(1.0f).build();
	private static final FoodComponent FOOD_SUGAR = new FoodComponent.Builder().hunger(0).saturationModifier(1.0f).build();

	public FoodComponent getFoodComponent() {
		Item item = ((Item)(Object)this);
		if (item == Items.WHEAT_SEEDS) {
			return FOOD_WHEAT_SEEDS;
		} else if (item == Items.PUMPKIN_SEEDS) {
			return FOOD_PUMPKIN_SEEDS;
		} else if (item == Items.RED_MUSHROOM) {
			return FOOD_RED_MUSHROOM;
		} else if (item == Items.BROWN_MUSHROOM) {
			return FOOD_BROWN_MUSHROOM;
		} else if (item == Items.EGG) {
			return FOOD_EGG;
		} else if (item == Items.HONEYCOMB) {
			return FOOD_HONEYCOMB;
		} else if (item == Items.SUGAR) {
			return FOOD_SUGAR;
		}
		return this.foodComponent;
	}

	public boolean isFood() {
		return getFoodComponent() != null;
	}


	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		stack.damage(ToolDecayRates.GetDecayRateAgainstEnemy(stack.getItem()), attacker, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
		if (stack.getItem() == Items.STICK || stack.getItem() == ItemRegistry.BRANCH) {
			if (new Random().nextInt(5) == 0) {
				attacker.sendToolBreakStatus(Hand.MAIN_HAND);
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
