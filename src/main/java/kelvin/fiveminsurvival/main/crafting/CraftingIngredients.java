package kelvin.fiveminsurvival.main.crafting;

import java.util.HashMap;

import kelvin.fiveminsurvival.init.ItemRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class CraftingIngredients {
	
	public static HashMap<Item, CraftingIngredient> ingredients = new HashMap<>();
	public static HashMap<Material, CraftingIngredient> block_ingredients = new HashMap<>();

	public static void init() {
		register(ItemRegistry.FLAX.get(), CraftingIngredient.NO_WORKBENCH, 20 * 5);
		register(ItemRegistry.SINEW.get(), CraftingIngredient.NO_WORKBENCH, 5);
		register(ItemRegistry.FLINT_SHARD.get(), CraftingIngredient.NO_WORKBENCH, 5);
		register(ItemRegistry.SMOOTH_STONE.get(), CraftingIngredient.NO_WORKBENCH, 10);
		register(ItemRegistry.STRIPPED_BARK.get(), CraftingIngredient.NO_WORKBENCH, 10);
		register(Items.STRIPPED_OAK_LOG, CraftingIngredient.NO_WORKBENCH, 60);
		register(Items.STRIPPED_ACACIA_LOG, CraftingIngredient.NO_WORKBENCH, 60);
		register(Items.STRIPPED_BIRCH_LOG, CraftingIngredient.NO_WORKBENCH, 60);
		register(Items.STRIPPED_DARK_OAK_LOG, CraftingIngredient.NO_WORKBENCH, 60);
		register(Items.STRIPPED_SPRUCE_LOG, CraftingIngredient.NO_WORKBENCH, 60);
		register(Items.STRIPPED_JUNGLE_LOG, CraftingIngredient.NO_WORKBENCH, 60);

		register(Items.OAK_LOG, CraftingIngredient.NO_WORKBENCH, 60);
		register(Items.ACACIA_LOG, CraftingIngredient.NO_WORKBENCH, 60);
		register(Items.BIRCH_LOG, CraftingIngredient.NO_WORKBENCH, 60);
		register(Items.DARK_OAK_LOG, CraftingIngredient.NO_WORKBENCH, 60);
		register(Items.SPRUCE_LOG, CraftingIngredient.NO_WORKBENCH, 60);
		register(Items.JUNGLE_LOG, CraftingIngredient.NO_WORKBENCH, 60);

		register(Items.LEATHER, CraftingIngredient.NO_WORKBENCH, 20);
		register(Items.FLINT, CraftingIngredient.NO_WORKBENCH, 20);
		register(Items.STRING, CraftingIngredient.NO_WORKBENCH, 10);
		register(Items.STICK, CraftingIngredient.NO_WORKBENCH, 5);
		register(Items.SUGAR_CANE, CraftingIngredient.NO_WORKBENCH, 10 * 20);
		register(Items.SUGAR, CraftingIngredient.NO_WORKBENCH, 10);
		register(Items.COCOA_BEANS, CraftingIngredient.NO_WORKBENCH, 20);
		register(Items.BOWL, CraftingIngredient.NO_WORKBENCH, 10);
		register(Items.DANDELION, CraftingIngredient.NO_WORKBENCH, 5);
		register(Items.SNOWBALL, CraftingIngredient.NO_WORKBENCH, 5);
		register(Items.CLAY_BALL, CraftingIngredient.NO_WORKBENCH, 10);
		register(Items.BRICK, CraftingIngredient.NO_WORKBENCH, 10);
		register(Items.ARROW, CraftingIngredient.NO_WORKBENCH, 20);
		register(Items.BOW, CraftingIngredient.NO_WORKBENCH, 40);
		register(Items.REDSTONE, CraftingIngredient.NO_WORKBENCH, 10);
		register(ItemRegistry.COPPER_INGOT.get(), CraftingIngredient.COPPER_CRAFTING_TABLE, 20 * 2);
		register(ItemRegistry.SILVER_INGOT.get(), CraftingIngredient.COPPER_CRAFTING_TABLE, 20 * 2);
		register(Items.IRON_INGOT, CraftingIngredient.COPPER_CRAFTING_TABLE, 20 * 3);
		register(Items.GOLD_INGOT, CraftingIngredient.COPPER_CRAFTING_TABLE, 20 * 3);
		register(Items.COAL, CraftingIngredient.NO_WORKBENCH, 10);
		register(Items.LAPIS_LAZULI, CraftingIngredient.NO_WORKBENCH, 10);
		register(Items.LAPIS_BLOCK, CraftingIngredient.NO_WORKBENCH, 10 * 9);
		register(Items.WHEAT, CraftingIngredient.NO_WORKBENCH, 20);
		register(Items.BEETROOT, CraftingIngredient.NO_WORKBENCH, 20);
		register(Items.CARROT, CraftingIngredient.NO_WORKBENCH, 20);
		register(Items.APPLE, CraftingIngredient.NO_WORKBENCH, 20);
		register(Items.POTATO, CraftingIngredient.NO_WORKBENCH, 20);
		register(Items.CHARCOAL, CraftingIngredient.NO_WORKBENCH, 10);
		register(Items.INK_SAC, CraftingIngredient.NO_WORKBENCH, 40);
		register(ItemRegistry.COPPER_NUGGET.get(), CraftingIngredient.NO_WORKBENCH, (int)((20 * 2) / 9.0));
		register(ItemRegistry.SILVER_NUGGET.get(), CraftingIngredient.NO_WORKBENCH, (int)((20 * 2) / 9.0));

		register(Items.IRON_NUGGET, CraftingIngredient.NO_WORKBENCH, (int)((20 * 3) / 9.0));
		register(Items.GOLD_NUGGET, CraftingIngredient.NO_WORKBENCH, (int)((15 * 3) / 9.0));
		register(Items.GOLD_BLOCK, CraftingIngredient.COPPER_CRAFTING_TABLE, 15 * 3 * 9);
		register(Items.COAL_BLOCK, CraftingIngredient.FLINT_CRAFTING_TABLE, 10 * 9);
		register(Items.OBSIDIAN, CraftingIngredient.NO_WORKBENCH, 20 * 5);

		register(Material.BAMBOO, CraftingIngredient.FLINT_CRAFTING_TABLE, 20);
		register(Material.CACTUS, CraftingIngredient.NO_WORKBENCH, 10);
		register(Material.CLAY, CraftingIngredient.NO_WORKBENCH, 15);
		register(Material.EARTH, CraftingIngredient.NO_WORKBENCH, 10);
		register(Material.GLASS, CraftingIngredient.NO_WORKBENCH, 10);
		register(Material.GOURD, CraftingIngredient.NO_WORKBENCH, 20 * 5);
		register(Material.IRON, CraftingIngredient.COPPER_CRAFTING_TABLE, 20 * 3 * 9);
		register(Material.PLANTS, CraftingIngredient.NO_WORKBENCH, 20);
		register(Material.OCEAN_PLANT, CraftingIngredient.NO_WORKBENCH, 20);
		register(Material.ROCK, CraftingIngredient.FLINT_CRAFTING_TABLE, 20);
		register(Material.SAND, CraftingIngredient.NO_WORKBENCH, 10);
		register(Material.SNOW, CraftingIngredient.NO_WORKBENCH, 5);
		register(Material.SNOW_BLOCK, CraftingIngredient.NO_WORKBENCH, 20);
		register(Material.WOOD, CraftingIngredient.NO_WORKBENCH, 40);
		register(Material.WOOL, CraftingIngredient.NO_WORKBENCH, 20);
	}
	
	public static void register(Material material, int workbench, int craftingDifficulty) {
		block_ingredients.put(material, new CraftingIngredient(workbench, craftingDifficulty));
	}
	
	
	public static void register(Item item, int workbench, int craftingDifficulty) {
		ingredients.put(item, new CraftingIngredient(workbench, craftingDifficulty));
	}
}
