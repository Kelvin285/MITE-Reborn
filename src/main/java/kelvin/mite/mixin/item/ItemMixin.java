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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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
	private static final FoodComponent FOOD_ROTTEN_FLESH = new FoodComponent.Builder().hunger(4).saturationModifier(2.0f).statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 15), 1).statusEffect(new StatusEffectInstance(StatusEffects.POISON, 15 * 20), 1).statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 30 * 20), 1).build();

	@Shadow
	public FoodComponent getFoodComponent() {
		return null;
	}

	@Inject(at=@At("RETURN"), method="getFoodComponent", cancellable = true)
	public void getFoodComponent(CallbackInfoReturnable<FoodComponent> info) {
		Item item = ((Item)(Object)this);
		if (item == Items.WHEAT_SEEDS) {
			info.setReturnValue(FOOD_WHEAT_SEEDS);
			return;
		} else if (item == Items.PUMPKIN_SEEDS) {
			info.setReturnValue(FOOD_PUMPKIN_SEEDS);
			return;
		} else if (item == Items.RED_MUSHROOM) {
			info.setReturnValue(FOOD_RED_MUSHROOM);
			return;
		} else if (item == Items.BROWN_MUSHROOM) {
			info.setReturnValue(FOOD_BROWN_MUSHROOM);
			return;
		} else if (item == Items.EGG) {
			info.setReturnValue(FOOD_EGG);
			return;
		} else if (item == Items.HONEYCOMB) {
			info.setReturnValue(FOOD_HONEYCOMB);
			return;
		} else if (item == Items.SUGAR) {
			info.setReturnValue(FOOD_SUGAR);
			return;
		} else if (item == Items.ROTTEN_FLESH) {
			info.setReturnValue(FOOD_ROTTEN_FLESH);
			return;
		}
	}

	@Inject(at = @At("RETURN"), method="isFood", cancellable = true)
	public void isFood(CallbackInfoReturnable<Boolean> info) {
		info.setReturnValue(getFoodComponent() != null);
	}

	//    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
	//        return false;
	//    }
	@Inject(at=@At("RETURN"), method="postHit", cancellable = true)
	public void postHit(ItemStack stack, LivingEntity target, LivingEntity attacker, CallbackInfoReturnable<Boolean> info) {
		stack.damage(ToolDecayRates.GetDecayRateAgainstEnemy(stack.getItem()), attacker, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
		if (stack.getItem() == Items.STICK || stack.getItem() == ItemRegistry.BRANCH) {
			if (new Random().nextInt(5) == 0) {
				attacker.sendToolBreakStatus(Hand.MAIN_HAND);
				stack.decrement(1);
				attacker.world.playSound((PlayerEntity)attacker, attacker.getBlockPos(), SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.PLAYERS, 1.0f, 1.0f);
			}
		}
	}

	public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
		if (!world.isClient && state.getHardness(world, pos) != 0.0F)
		{
			stack.damage(ToolDecayRates.GetDecayRateAgainstBlock(stack.getItem(), state), miner, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
			
		}
		return true;
	}
}
