package kelvin.fiveminsurvival.survival.food;

import kelvin.fiveminsurvival.main.resources.Resources;
import kelvin.fiveminsurvival.survival.world.WorldStateHolder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.io.Serializable;
import java.lang.reflect.Field;

public class Nutrients implements Serializable {
	
	
	
	public double protein = 100.0;
	public double phytonutrients = 100.0;
	public double sugars = 0;
	public double insulin_resistance = 0.0;
	public double fatigue = 0.0;
	public double fatigueHolder = 0.0;
	public double fatty_acids = 100.0;
	public double happiness = 100.0;
	public double sickness = 0.0;
	public double carbs = 100.0;
	
	public double negativeXP;
	public int negativeLevel;
	public double xpRate = 25;
	
	public double foodRate = 1.0 / (20.0 * 10.0);
	
	
	public CustomFoodStats foodStats;
	
	public Nutrients(CustomFoodStats foodStats) {
		this.foodStats = foodStats;
	}
	
	public String toString() {
		return "Nutrients[protein:" + protein+",phytonutrients:"+phytonutrients+",sugars:"+sugars+",insulin_resistance:"+insulin_resistance+",fatigue:"+fatigue+
				",fatigueHolder:"+fatigueHolder+",fatty_acids:"+fatty_acids+",happiness:"+happiness+",sickness:"+sickness+"]";
	}
	
	public String getSaveString() {
		return protein+","+phytonutrients+","+sugars+","+insulin_resistance+","+fatigue+","+fatigueHolder+","+fatty_acids+","+happiness+","+sickness+","+carbs+","+negativeXP+","+negativeLevel;
	}
	
	public void loadFromString(String str) {
		String[] data = str.split(",");
		protein = getDouble(data[0]);
		phytonutrients = getDouble(data[1]);
		sugars = getDouble(data[2]);
		insulin_resistance = getDouble(data[3]);
		fatigue = getDouble(data[4]);
		fatigueHolder = getDouble(data[5]);
		fatty_acids = getDouble(data[6]);
		happiness = getDouble(data[7]);
		sickness = getDouble(data[8]);
		carbs = getDouble(data[9]);
		negativeXP = getDouble(data[10]);
		negativeLevel = getInt(data[11]);
	}
	public double getDouble(String str) {
		return Double.parseDouble(str);
	}
	public int getInt(String str) {
		return Integer.parseInt(str);
	}
	
	// An increase in nutrients will make your character stronger, but at the same time it will cause them to lose hunger faster
	public double getMetabolismRate(int level) {
		double levelRate = -level * 0.01;
		double rate = 1.0 - ((fatigue + insulin_resistance + happiness + carbs * 1.25) / 400.0 - sickness / 100.0);
		double finalRate = levelRate * 8.5 + rate * 16.0;
		if (finalRate < 3) finalRate = 3;
		return finalRate;
	}
	
	public double getHappinessReduction() {
		double rate = (1.0 - (protein + phytonutrients + sugars + fatty_acids) / 400.0 - (fatigue + insulin_resistance) / 200.0) * 0.08;
		if (rate < 0) rate = 0;
		return rate;
	}
	
	public double getSicknessModifier() {
		return 1.0 + sickness / 100.0;
	}
	
	public double getStrengthModifier() {
		return (protein + phytonutrients) / 200.0;
	}
	
	public double getRegenModifier() {
		return (protein + phytonutrients) / 200.0;
	}
	
	public double getWeaknessResistance() {
		return (protein + happiness) / 200.0;
	}
	
	public double getSlownessResistance() {
		return (phytonutrients + happiness) / 200.0;
	}
	
	public double getSpeedModifier() {
//		System.out.println(fatigue);
//		System.out.println(sugars);
//		System.out.println(0.1 + 0.01 * ((phytonutrients + sugars * 25) / 200.0 - (fatigue / 100.0) * 10) - sickness * 0.001);
		
		return 0.08 + 0.01 * ((protein - 75) / 50.0 + (sugars * 5) / 100.0 - (fatigue / 100.0) * 25) - sickness * 0.001;
	}
	
	public double getAttackSpeedModifier() {
		return (protein + phytonutrients + fatty_acids) / 300.0;
	}
	
	public double getPrecision() {
		return phytonutrients / 100.0;
	}
	
	public double getFireResistance() {
		return phytonutrients / 100.0;
	}
	
	public double getCraftingSpeedModifier() {
		return phytonutrients / 100.0;
	}
	
	public double getXPModifier() {
		return fatty_acids / 100.0;
	}
	
	public double getCraftingComplexity() {
		return fatty_acids;
	}
	
	public double getMentalEffectResistance() {
		return fatty_acids / 100.0;
	}
	double lastWalkedDistance = 0;
	//protein goes down 1 every 10 seconds
	public void tick(PlayerEntity player, CustomFoodStats foodStats) {
		double foodMul = 0.1;
		if (player.isSleeping()) {
			WorldStateHolder.get(player.getEntityWorld()).worldState.time+=2;
			try {
			Field sleepTimer = ObfuscationReflectionHelper.findField(PlayerEntity.class, "field_71076_b");
			Resources.makeFieldAccessible(sleepTimer);
			sleepTimer.set(player, 1);
			foodStats.foodTimer+=2;
			foodStats.foodTimer2+=2;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		long time = player.getEntityWorld().getDayTime();
		boolean night = (time % 24000 > 12000 && time % 24000 < 23000);
//		System.out.println(night);
		double nightMul = -2.0;
				
		if (player.getEntityWorld().getLight(player.getPosition()) > 0 || night) {
			nightMul = 2.0;
		}
		
		if (player.getEntityWorld().getLight(player.getPosition()) > 2) {
			nightMul = 1.0;
		}
		
		if (player.getEntityWorld().getLight(player.getPosition()) > 5) {
			nightMul = 0.0;
		}
		
		if (player.getEntityWorld().getLight(player.getPosition()) > 7) {
			nightMul = -1.0;
		}
		
		if (player.getEntityWorld().getLight(player.getPosition()) > 8) {
			nightMul = -2.0;
		}
		
		if (player.getActivePotionEffect(Effects.BLINDNESS) != null) {
			nightMul += 1.0;
		}
		
		if (player.getActivePotionEffect(Effects.WEAKNESS) != null) {
			nightMul += 0.5;
		}
		
		if (player.getActivePotionEffect(Effects.POISON) != null) {
			nightMul += 1.5;
		}
		
		if (player.getActivePotionEffect(Effects.SLOWNESS) != null) {
			nightMul += 0.5;
		}
		
		if (player.getActivePotionEffect(Effects.MINING_FATIGUE) != null) {
			nightMul += 0.5;
		}
		
		if (player.getActivePotionEffect(Effects.HUNGER) != null) {
			nightMul += 1.0;
		}
		
		if (player.getActivePotionEffect(Effects.NAUSEA) != null) {
			nightMul += 1.0;
		}
		
		if (player.getActivePotionEffect(Effects.WITHER) != null) {
			nightMul += 4.0;
		}
		
		if (player.getActivePotionEffect(Effects.UNLUCK) != null) {
			nightMul += 0.5;
		}
		
		if (player.getActivePotionEffect(Effects.BAD_OMEN) != null) {
			nightMul += 1.0;
		}
		
		if (player.getActivePotionEffect(Effects.ABSORPTION) != null) {
			nightMul -= 1.0;
		}
		
		if (player.getActivePotionEffect(Effects.REGENERATION) != null) {
			nightMul -= 2.0;
		}
		
		if (player.getActivePotionEffect(Effects.RESISTANCE) != null) {
			nightMul -= 1.0;
		}
		
		if (player.getActivePotionEffect(Effects.SPEED) != null) {
			nightMul -= 0.5;
		}
		
		if (player.getActivePotionEffect(Effects.HASTE) != null) {
			nightMul -= 0.5;
		}
		
		if (player.getActivePotionEffect(Effects.CONDUIT_POWER) != null) {
			nightMul -= 3.0;
		}
		
		if (player.getActivePotionEffect(Effects.DOLPHINS_GRACE) != null) {
			nightMul -= 2.0;
		}
		
		if (player.getActivePotionEffect(Effects.FIRE_RESISTANCE) != null) {
			nightMul -= 0.5;
		}
		
		if (player.getActivePotionEffect(Effects.LUCK) != null) {
			nightMul -= 0.5;
		}
		
		if (player.getActivePotionEffect(Effects.HERO_OF_THE_VILLAGE) != null) {
			nightMul -= 4.5;
		}
		
		if (player.getActivePotionEffect(Effects.HEALTH_BOOST) != null) {
			nightMul -= 1.5;
		}
		
		if (player.getActivePotionEffect(Effects.STRENGTH) != null) {
			nightMul -= 1.5;
		}
		
		if (player.getActivePotionEffect(Effects.WATER_BREATHING) != null) {
			nightMul -= 0.5;
		}
		
		if (sickness > 15) {
			player.addPotionEffect(new EffectInstance(Effects.WEAKNESS, 20 * 5, (int)sickness / 30));
			if (sickness > 30) {
				player.addPotionEffect(new EffectInstance(Effects.NAUSEA, 20 * 5, 1));
			}
			if (sickness > 60 && sickness <= 80) {
				player.addPotionEffect(new EffectInstance(Effects.POISON, 20 * 5, 0));
			}
			if (sickness > 80) {
				player.addPotionEffect(new EffectInstance(Effects.WITHER, 20 * 5, 1));
			}
		}
		
		
		double sugarMod = 1.0;
		
		double velocity = player.getMotion().length();
		
		if (player.distanceWalkedModified > this.lastWalkedDistance) {
			velocity = player.distanceWalkedModified - this.lastWalkedDistance;
			this.lastWalkedDistance = player.distanceWalkedModified;
		}
		
		double levelRate = player.experienceLevel * 0.0001;
//		System.out.println(getMetabolismRate(player.experienceLevel));
		protein -= foodRate * getSicknessModifier() * foodMul + levelRate;
		if (protein > 100) protein -= levelRate * 2.0;
		phytonutrients -= foodRate * getSicknessModifier() * foodMul + levelRate * 1.1;
		if (phytonutrients > 100) phytonutrients -= levelRate * 2.0;
		insulin_resistance -= foodRate * (2.0 - getSicknessModifier());
		fatty_acids -= (foodRate / 2.0) * getSicknessModifier() * foodMul + levelRate;
		if (fatty_acids > 100) fatty_acids -= levelRate * 2.0;
		happiness -= ((getHappinessReduction() / 4.0) * getSicknessModifier() * foodMul + levelRate) * nightMul * 0.25 + sickness * 0.00001;
		if (happiness > 100) happiness -= levelRate * 2.0;
		carbs -= foodRate * getSicknessModifier() * foodMul + levelRate;
		sickness -= 0.02;
		if (carbs > 100) carbs -= levelRate * 2.0;
		
		if (sugars > 0.0) {
			sugars -= foodRate * 4.0 + velocity * 0.01;
			fatigueHolder += foodRate * 4.0 + velocity * 0.01;
			insulin_resistance += foodRate * 1.1;
			if (insulin_resistance > 20) {
				player.addPotionEffect(new EffectInstance(Effects.NAUSEA, 20 * (int)insulin_resistance, 1));
			}
		} else {
			if (fatigueHolder > 0) {
//				System.out.println("bruh");
				fatigue += fatigueHolder;
				fatigueHolder = 0.0;
			}
			fatigue -= (1.0 - ((getHappinessReduction() / 4.0) * getSicknessModifier())) * 0.05;
		}
		
		protein = clamp(protein, 0, 100 + player.experienceLevel * 5);
		phytonutrients = clamp(phytonutrients, 0, 100 + player.experienceLevel * 5);
		sugars = clamp(sugars, 0, 100 + player.experienceLevel * 5);
		insulin_resistance = clamp(insulin_resistance, 0, 100);
		fatigue = clamp(fatigue, 0, 100 + player.experienceLevel * 5);
		fatty_acids = clamp(fatty_acids, 0, 100 + player.experienceLevel * 5);
		happiness = clamp(happiness, 0, 100 + player.experienceLevel * 5);
		carbs = clamp(carbs, 0, 100 + player.experienceLevel * 5);
		sickness = clamp(sickness, 0, 100);


	}
	
	private double clamp(double var, double min, double max) {
		return Math.min(Math.max(var, min), max);
	}

	public void reset() {
		protein = 100.0;
		phytonutrients = 100.0;
		sugars = 0;
		insulin_resistance = 0.0;
		fatigue = 0.0;
		fatigueHolder = 0.0;
		fatty_acids = 100.0;
		happiness = 100.0;
		sickness = 0.0;
		carbs = 100.0;
	}
}
