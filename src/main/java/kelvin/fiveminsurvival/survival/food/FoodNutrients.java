package kelvin.fiveminsurvival.survival.food;

import java.io.Serializable;
import java.util.HashMap;

import kelvin.fiveminsurvival.init.ItemRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class FoodNutrients implements Serializable {
	public static HashMap<Item, FoodNutrients> list = new HashMap<>();
	
	public double protein = 0.0;
	public double phytonutrients = 0.0;
	public double sugars = 0.0;
	public double fatty_acids = 0.0;
	public double happiness = 0.0;
	public double sickness = 0.0;
	public double carbs = 0.0;
	
	public FoodNutrients setProtein(double protein) {this.protein = protein; return this;}
	public FoodNutrients setPhytonutrients(double phytonutrients) {this.phytonutrients = phytonutrients; return this;}
	public FoodNutrients setSugars(double sugars) {this.sugars = sugars; return this;}
	public FoodNutrients setFattyAcids(double fatty_acids) {this.fatty_acids = fatty_acids; return this;}
	public FoodNutrients setHappiness(double happiness) {this.happiness = happiness; if (this.happiness < 0) this.happiness /= 10; return this;}
	public FoodNutrients setSickness(double sickness) {this.sickness = sickness; return this;}
	public FoodNutrients setCarbs(double carbs) {this.carbs = carbs; return this;}
	
	
	public static void init() {
		register(Items.WHEAT_SEEDS, new FoodNutrients().setPhytonutrients(1.0).setFattyAcids(1.0).setCarbs(0.5));
		register(Items.PUMPKIN_SEEDS, new FoodNutrients().setPhytonutrients(3.0).setFattyAcids(3.0).setProtein(0.1).setHappiness(3.0));
		register(Items.MELON_SEEDS, new FoodNutrients().setPhytonutrients(2.0).setFattyAcids(1.0).setSugars(0.1).setHappiness(3.0));

		register(Items.BROWN_MUSHROOM, new FoodNutrients().setPhytonutrients(5.0).setProtein(3.0).setSugars(1.0).setFattyAcids(1.0).setHappiness(0.5));
		register(Items.RED_MUSHROOM, new FoodNutrients().setPhytonutrients(5.0).setProtein(3.0).setSugars(1.0).setFattyAcids(1.0).setHappiness(-30.0).setSickness(40.0));
		register(Items.APPLE, new FoodNutrients().setPhytonutrients(10.0).setSugars(1).setProtein(2.0));
		register(Items.COOKIE, new FoodNutrients().setHappiness(10.0).setSugars(10.0));
		register(Items.ROTTEN_FLESH, new FoodNutrients().setHappiness(-15).setProtein(15).setFattyAcids(10).setSickness(30));
		register(Items.COD, new FoodNutrients().setHappiness(-1).setProtein(10).setFattyAcids(5).setSickness(2));
		register(Items.SALMON, new FoodNutrients().setHappiness(-2).setProtein(10).setFattyAcids(5).setSickness(2));
		register(Items.TROPICAL_FISH, new FoodNutrients().setHappiness(-2).setProtein(10).setFattyAcids(5).setSickness(2));
		register(Items.PUFFERFISH, new FoodNutrients().setHappiness(-25).setProtein(10).setFattyAcids(3).setSickness(50));
		register(Items.DRIED_KELP, new FoodNutrients().setHappiness(5).setFattyAcids(5).setProtein(1.0).setPhytonutrients(7));
		register(Items.BEEF, new FoodNutrients().setHappiness(-5).setProtein(20).setSickness(5).setFattyAcids(5));
		register(Items.PORKCHOP, new FoodNutrients().setHappiness(-7).setProtein(15).setSickness(6).setFattyAcids(4));
		register(Items.PHANTOM_MEMBRANE, new FoodNutrients().setHappiness(15).setProtein(15).setFattyAcids(8).setSugars(0.3));
		register(Items.MUTTON, new FoodNutrients().setHappiness(-7).setProtein(10).setSickness(5).setFattyAcids(4));
		register(Items.RABBIT, new FoodNutrients().setHappiness(-7).setProtein(10).setSickness(5).setFattyAcids(4));
		register(Items.CHICKEN, new FoodNutrients().setHappiness(-15).setProtein(10).setSickness(13).setFattyAcids(2));
		register(Items.POISONOUS_POTATO, new FoodNutrients().setHappiness(-10).setCarbs(10).setSickness(35));
		register(Items.MELON_SLICE, new FoodNutrients().setPhytonutrients(7.0).setSugars(1).setHappiness(7));
		register(Items.PUMPKIN_PIE, new FoodNutrients().setPhytonutrients(50.0).setFattyAcids(10).setProtein(5.0).setSugars(15).setHappiness(30));
		register(Items.CHORUS_FRUIT, new FoodNutrients().setPhytonutrients(10.0).setFattyAcids(5.0).setSugars(3.5).setHappiness(-15));
		register(Items.SWEET_BERRIES, new FoodNutrients().setPhytonutrients(20.0).setSugars(1.5).setHappiness(5.0));
		register(Items.COOKED_CHICKEN, new FoodNutrients().setHappiness(7).setProtein(10));
		register(Items.COOKED_PORKCHOP, new FoodNutrients().setHappiness(15).setProtein(15));
		register(Items.COOKED_MUTTON, new FoodNutrients().setHappiness(15).setProtein(10));
		register(Items.COOKED_BEEF, new FoodNutrients().setHappiness(20).setProtein(20));
		register(Items.COOKED_RABBIT, new FoodNutrients().setHappiness(15).setProtein(10));
		register(Items.RABBIT_STEW, new FoodNutrients().setHappiness(20).setProtein(15).setFattyAcids(8).setPhytonutrients(8).setSugars(1));
		register(Items.MUSHROOM_STEW, new FoodNutrients().setHappiness(10).setProtein(5).setFattyAcids(8).setPhytonutrients(10).setSugars(1));
		register(Items.BREAD, new FoodNutrients().setHappiness(10).setPhytonutrients(8).setProtein(2.0).setSugars(0.5));
		register(Items.COOKED_COD, new FoodNutrients().setHappiness(5).setProtein(10).setFattyAcids(5).setCarbs(20));
		register(Items.POTATO, new FoodNutrients().setHappiness(5).setCarbs(10));
		register(Items.BEETROOT_SOUP, new FoodNutrients().setHappiness(7).setFattyAcids(5).setPhytonutrients(8).setSugars(1));
		register(Items.CARROT, new FoodNutrients().setHappiness(2).setFattyAcids(1).setProtein(2.0).setPhytonutrients(2).setSugars(1).setCarbs(2));
		register(Items.BEETROOT, new FoodNutrients().setHappiness(1).setFattyAcids(1).setProtein(2.0).setPhytonutrients(1.5).setSugars(0.2));
		register(Items.COOKED_SALMON, new FoodNutrients().setHappiness(8).setProtein(10).setFattyAcids(5));
		register(Items.SPIDER_EYE, new FoodNutrients().setHappiness(-10).setProtein(8).setFattyAcids(15).setSickness(15));
		register(Items.GOLDEN_CARROT, new FoodNutrients().setHappiness(30).setProtein(4).setFattyAcids(10).setPhytonutrients(10).setSugars(10));
		register(Items.GOLDEN_APPLE, new FoodNutrients().setHappiness(40).setProtein(4).setFattyAcids(15).setPhytonutrients(15).setSugars(15));
		register(Items.ENCHANTED_GOLDEN_APPLE, new FoodNutrients().setHappiness(40).setProtein(4).setFattyAcids(15).setPhytonutrients(15).setSugars(15));
		register(Items.SUGAR, new FoodNutrients().setHappiness(3).setSugars(1));
		register(ItemRegistry.FLAX_SEEDS.get(), new FoodNutrients().setHappiness(1).setPhytonutrients(3).setFattyAcids(4).setSugars(0.1).setCarbs(2));
		register(ItemRegistry.BACON.get(), new FoodNutrients().setHappiness(-3).setProtein(8).setSickness(2).setFattyAcids(2));
		register(ItemRegistry.COOKED_BACON.get(), new FoodNutrients().setHappiness(8).setProtein(8).setFattyAcids(4));
	}
	
	public static void register(Item item, FoodNutrients nutrients) {
		list.put(item, nutrients);
	}
}
