package kelvin.mite.mixin;

import kelvin.mite.main.resources.MiteHungerManager;
import kelvin.mite.registry.NutrientsRegistry;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HungerManager.class)
public class HungerManagerMixin implements MiteHungerManager {

	@Shadow
	private int foodLevel = 20;
	@Shadow
	private float foodSaturationLevel = 5.0F;
	@Shadow
	private float exhaustion;
	@Shadow
	private int foodTickTimer;
	@Shadow
	private int prevFoodLevel = 20;

	private float fruits = 8.0f;
	private float fruitsExhaustion = 0;

	private float vegetables = 8.0f;
	private float vegetablesExhaustion = 0;

	private float dairy = 8.0f;
	private float dairyExhaustion = 0;

	private float protein = 8.0f;
	private float proteinExhaustion = 0;

	private float grains = 8.0f;
	private float grainsExhaustion = 0;


	@Shadow
	public void add(int food, float saturationModifier) {
		this.foodLevel = Math.min(food + this.foodLevel, 20);
		this.foodSaturationLevel = Math.min(this.foodSaturationLevel + (float)food * saturationModifier * 2.0F, (float)this.foodLevel);
	}

	@Shadow
	public void eat(Item item, ItemStack stack) {
		if (item.isFood()) {
			FoodComponent foodComponent = item.getFoodComponent();
			this.add(foodComponent.getHunger(), foodComponent.getSaturationModifier());
		}

	}

	private int ticks = 0;

	private float max_saturation = 8;



	@Inject(at=@At("HEAD"),method="update",cancellable = true)
	public void update(PlayerEntity player, CallbackInfo info) {
		int max_food_level = (player.experienceLevel / 5 + 3) * 2;
		max_saturation = 8 + (player.experienceLevel / 5) * 3;

		if (this.foodSaturationLevel > 5) {
			this.foodSaturationLevel = 5;
		}

		if (ticks > 20 * 60 * 30) { // subtract food level by 1 every 30 minutes
			ticks = 0;
			foodLevel--;
		}

		if (fruitsExhaustion > 4.0f) {
			fruitsExhaustion -= 4.0f;
			if (fruits > 0) {
				fruits --;
			} else {
				exhaustion = 5;
			}
		}

		if (vegetablesExhaustion > 4.0f) {
			vegetablesExhaustion -= 4.0f;
			if (vegetables > 0) {
				vegetables --;
			} else {
				exhaustion = 5;
			}
		}

		if (dairyExhaustion > 4.0f) {
			dairyExhaustion -= 4.0f;
			if (dairy > 0) {
				dairy --;
			} else {
				exhaustion = 5;
			}
		}

		if (proteinExhaustion > 4.0f) {
			proteinExhaustion -= 4.0f;
			if (protein > 0) {
				protein --;
			} else {
				exhaustion = 5;
			}
		}

		if (grainsExhaustion > 4.0f) {
			grainsExhaustion -= 4.0f;
			if (grains > 0) {
				grains --;
			} else {
				exhaustion = 5;
			}
		}

		if (exhaustion > 4.0f) {
			exhaustion -= 4.0f;
			if (this.foodLevel > 0) {
				if (this.foodSaturationLevel > 0.0F) {
					this.foodSaturationLevel = Math.max(this.foodSaturationLevel - 1.0F, 0.0F);
				} else {
					this.foodLevel = Math.max(this.foodLevel - 1, 0);
				}
			} else {
				player.damage(DamageSource.STARVE, 1);
			}
		}

		foodLevel = (int)Math.max(Math.min(foodLevel, max_food_level), 0);
		foodSaturationLevel = (float)Math.max(Math.min(foodSaturationLevel, max_saturation), 0);
		info.cancel();
	}

	public float getMaxSaturation() {
		return max_saturation;
	}

	public void readNbt(NbtCompound nbt) {
		if (nbt.contains("foodLevel", 99)) {
			this.foodLevel = nbt.getInt("foodLevel");
			this.foodTickTimer = nbt.getInt("foodTickTimer");
			this.foodSaturationLevel = nbt.getFloat("foodSaturationLevel");
			this.exhaustion = nbt.getFloat("foodExhaustionLevel");
		}

	}



	public void writeNbt(NbtCompound nbt) {
		nbt.putInt("foodLevel", this.foodLevel);
		nbt.putInt("foodTickTimer", this.foodTickTimer);
		nbt.putFloat("foodSaturationLevel", this.foodSaturationLevel);
		nbt.putFloat("foodExhaustionLevel", this.exhaustion);
	}

	@Shadow
	public int getFoodLevel() {

		return 0;
	}

	@Shadow
	public int getPrevFoodLevel() {
		return 0;
	}

	public boolean isNotFull() {
		return this.foodSaturationLevel < 5;
	}

	public void addExhaustion(float exhaustion) {
		this.exhaustion += exhaustion * 1.5f;
		if (this.exhaustion > 40) this.exhaustion = 40;
		if (this.exhaustion < 0) this.exhaustion = 0;
	}

	@Shadow
	public float getExhaustion() {
		return 0;
	}

	@Shadow
	public float getSaturationLevel() {
		return 0;
	}

	@Shadow
	public void setFoodLevel(int foodLevel) {

	}

	@Shadow
	public void setSaturationLevel(float saturationLevel) {

	}

	@Shadow
	public void setExhaustion(float exhaustion) {

	}

	@Override
	public void eatFood(Item food) {
		NutrientsRegistry.Nutrients n = NutrientsRegistry.GetNutrientsFor(food);
		addSaturation(HungerCategory.FRUITS, n.fruits);
		addSaturation(HungerCategory.VEGETABLES, n.vegetables);
		addSaturation(HungerCategory.GRAIN, n.grains);
		addSaturation(HungerCategory.DAIRY, n.dairy);
		addSaturation(HungerCategory.PROTEIN, n.protein);
	}

	@Override
	public void addSaturation(HungerCategory category, int value) {
		switch (category) {
			case DAIRY:
				dairy += value;
				if (dairy > getMaxSaturation()) dairy = getMaxSaturation();
				break;
			case GRAIN:
				grains += value;
				if (grains > getMaxSaturation()) grains = getMaxSaturation();
				break;
			case FRUITS:
				fruits += value;
				if (fruits > getMaxSaturation()) fruits = getMaxSaturation();
				break;
			case VEGETABLES:
				vegetables += value;
				if (vegetables > getMaxSaturation()) vegetables = getMaxSaturation();
				break;
			case PROTEIN:
				protein += value;
				if (protein > getMaxSaturation()) protein = getMaxSaturation();
				break;
		}
	}

	@Override
	public float getSaturation(HungerCategory category) {
		switch (category) {
			case DAIRY:
				return dairy;
			case GRAIN:
				return grains;
			case FRUITS:
				return fruits;
			case VEGETABLES:
				return vegetables;
			case PROTEIN:
				return protein;
		}
		return 0;
	}

	@Override
	public void setSaturation(HungerCategory category, float value) {
		switch (category) {
			case DAIRY:
				dairy = value;
				break;
			case GRAIN:
				grains = value;
				break;
			case FRUITS:
				fruits = value;
				break;
			case VEGETABLES:
				vegetables = value;
				break;
			case PROTEIN:
				protein = value;
				break;
		}
	}

	@Override
	public void addExhaustion(HungerCategory category, float value) {
		switch (category) {
			case DAIRY:
				dairyExhaustion += value;
				break;
			case GRAIN:
				grainsExhaustion += value;
				break;
			case FRUITS:
				fruitsExhaustion += value;
				break;
			case VEGETABLES:
				vegetablesExhaustion += value;
				break;
			case PROTEIN:
				proteinExhaustion += value;
				break;
		}
	}
}
