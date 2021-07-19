package kelvin.mite.main.resources;

import kelvin.mite.registry.ItemRegistry;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class ItemCraftingDifficulty {
	public static int GetDifficultyFor(Item item) {
		if (item instanceof DyeItem) {
			return 5;
		}
		
		if (item instanceof BlockItem) 
		{
			return ToolDecayRates.GetDefaultDecayRateAgainstBlock(((BlockItem)item).getBlock().getDefaultState());
		}
		
		if (item == Items.IRON_INGOT) { 
			return 400;
		}
		
		if (item == Items.COPPER_INGOT) { 
			return 400;
		}
		
		if (item == ItemRegistry.SILVER_INGOT) { 
			return 400;
		}
		
		if (item == Items.GOLD_INGOT) { 
			return 400;
		}
		
		if (item == Items.NETHERITE_INGOT) {
			return 1600;
		}
		
		return 25;
	}
}
