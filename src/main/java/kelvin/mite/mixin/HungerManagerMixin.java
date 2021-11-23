package kelvin.mite.mixin;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.Difficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;

@Mixin(HungerManager.class)
public class HungerManagerMixin {

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

	public void update(PlayerEntity player) {
		int max_food_level = (player.experienceLevel / 5 + 3) * 2;
		int max_saturation = 5;

		if (ticks > 20 * 60 * 30) { // subtract food level by 1 every 30 minutes
			ticks = 0;
			foodLevel--;
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
		return this.foodLevel;
	}

	@Shadow
	public int getPrevFoodLevel() {
		return this.prevFoodLevel;
	}

	@Shadow
	public boolean isNotFull() {
		return this.foodSaturationLevel < 5;
	}

	@Shadow
	public void addExhaustion(float exhaustion) {
		this.exhaustion = Math.min(this.exhaustion + exhaustion, 40.0F);
	}

	@Shadow
	public float getExhaustion() {
		return this.exhaustion;
	}

	@Shadow
	public float getSaturationLevel() {
		return this.foodSaturationLevel;
	}

	@Shadow
	public void setFoodLevel(int foodLevel) {
		this.foodLevel = foodLevel;
	}


	@Shadow
	public void setSaturationLevel(float saturationLevel) {
		this.foodSaturationLevel = saturationLevel;
	}

	@Shadow
	public void setExhaustion(float exhaustion) {
		this.exhaustion = exhaustion;
	}
	
}
