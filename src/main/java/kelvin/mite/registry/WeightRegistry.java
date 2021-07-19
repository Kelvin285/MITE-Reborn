package kelvin.mite.registry;

import java.util.HashMap;

import kelvin.mite.items.SpearItem;
import net.minecraft.block.Material;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BookItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.MushroomStewItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.ToolItem;
import net.minecraft.util.registry.Registry;

public class WeightRegistry {
	public static HashMap<Item, Integer> weight = new HashMap<Item, Integer>();
	
	public static void RegisterBlockWeights() {
		Registry.BLOCK.forEach((block) -> {
			Material mat = block.getDefaultState().getMaterial(); 
			
			Item item = Item.fromBlock(block);

			int w = 0;
			if (mat == Material.STONE) w = 8;
			if (mat == Material.METAL) w = 20;
			if (mat == Material.WOOD) w = 4;
			if (mat == Material.ICE) w = 3;
			if (mat == Material.DENSE_ICE) w = 5;
			if (mat == Material.GOURD) w = 3;
			if (mat == Material.NETHER_WOOD) w = 7;
			if (mat == Material.PISTON) w = 10;
			if (mat == Material.REDSTONE_LAMP) w = 8;
			if (mat == Material.AMETHYST) w = 5;
			if (mat == Material.SHULKER_BOX) w = 40;
			if (mat == Material.SOIL) w = 10;
			if (mat == Material.WOOL) w = 3;
			
			if (block.getName().toString().contains("iron")) {
				w += 10;
			}
			else if (block.getName().toString().contains("copper")) {
				w += 5;
			}
			else if (block.getName().toString().contains("silver")) {
				w += 5;
			}
			else if (block.getName().toString().contains("deepslate")) {
				w += 2;
			}
			else if (block.getName().toString().contains("mithril")) {
				w += 10;
			}
			else if (block.getName().toString().contains("ancient_metal")) {
				w += 10;
			}
			else if (block.getName().toString().contains("netherrite")) {
				w += 20;
			} else if (block.getName().toString().contains("adamantium")) {
				w += 20;
			} else if (block.getName().toString().contains("log")) {
				w += 5;
			}
			
			if (block.getName().toString().contains("plank")) {
				w = 2;
			}
			
			if (block.getName().toString().contains("stair")) {
				w *= 0.5f;
			}
			
			if (block.getName().toString().contains("button")) {
				w = 1;
			}
			
			if (block.getName().toString().contains("slab")) {
				w *= 0.25f;
			}
			
			weight.put(item, w);
		});
	}
	
	public static void RegisterItemWeights() {
		Registry.ITEM.forEach((item) -> {
			if (item instanceof BlockItem) {
				return;
			}
			
			int w = 0;
			if (!item.getName().toString().contains("nugget") && !item.getName().toString().contains("chip")) {
				if (item.getName().toString().contains("iron")) {
					w += 7;
				}
				else if (item.getName().toString().contains("copper")) {
					w += 5;
				}
				else if (item.getName().toString().contains("silver")) {
					w += 5;
				}
				else if (item.getName().toString().contains("flint")) {
					w += 1;
				}
				else if (item.getName().toString().contains("gold")) {
					w += 12;
				}
				else if (item.getName().toString().contains("mithril")) {
					w += 8;
				}
				else if (item.getName().toString().contains("netherrite")) {
					w += 15;
				}
				else if (item.getName().toString().contains("adamantium")) {
					w += 15;
				}
				else if (item.getName().toString().contains("stone")) {
					w += 8;
				}
				else if (item.getName().toString().contains("wood")) {
					w += 3;
				}
				else if (item.getName().toString().contains("silver")) {
					w += 5;
				}
				else if (item.getName().toString().contains("lapis")) {
					w += 2;
				} else if (item.getName().toString().contains("nether")) {
					w += 3;
				} else if (item.getName().toString().contains("warped")) {
					w += 4;
				} else if (item.getName().toString().contains("crimson")) {
					w += 4;
				} else if (item.getName().toString().contains("leather")) {
					w += 3;
				}
			}
			
			if (item == Items.ENDER_PEARL) { 
				w = 1;
			}
			
			else if (item == Items.ENDER_EYE) {
				w = 2;
			}
			
			else if (item == Items.BLAZE_ROD) { 
				w = 1;
			}
			
			else if (item == Items.CLAY_BALL) {
				w = 1;
			}
			
			else if (item == Items.BRICK) {
				w = 2;
			}
			
			else if (item == Items.WHEAT) {
				w = 1;
			}
			
			else if (item == Items.BUCKET) {
				w = 4;
			}
			
			else if (item == Items.WATER_BUCKET) {
				w = 10;
			}
			
			else if (item == Items.MILK_BUCKET) {
				w = 13;
			}
			
			else if (item == Items.LAVA_BUCKET) {
				w = 15;
			}
			
			else if (item == Items.BOWL) {
				w = 1;
			}
			
			else if (item instanceof MushroomStewItem) {
				w = 3;
			}
			
			else if (item == Items.ELYTRA) { 
				w = 4;
			}
			
			else if (item instanceof BowItem) {
				w = 10;
			}
			
			else if (item == Items.TRIDENT) {
				w = 15;
			}
			
			else if (item == Items.NAUTILUS_SHELL) {
				w = 3;
			}
			
			else if (item instanceof BookItem) {
				w = 2;
			}
			
			else if (item == Items.WRITABLE_BOOK) {
				w = 2;
			}
			
			else if (item == Items.WRITTEN_BOOK) {
				w = 2;
			}
			
			else if (item == Items.ENCHANTED_BOOK) {
				w = 2;
			}
			
			else if (item == Items.GOLDEN_APPLE) {
				w = 50;
			}
			
			else if (item == Items.ENCHANTED_GOLDEN_APPLE) {
				w = 100;
			}
			
			else if (item instanceof FishingRodItem) {
				w = 8;
			} else if (item == Items.CARROT_ON_A_STICK) {
				w = 10;
			} else if (item == Items.WARPED_FUNGUS_ON_A_STICK) {
				w = 10;
			} else if (item == Items.SADDLE) {
				w = 5;
			} else if (item instanceof HorseArmorItem) {
				w += 5;
			} else if (item == Items.CHAIN) {
				w = 3;
			} else if (item == Items.LAPIS_LAZULI) { 
				w = 3;
			} else if (item == Items.SHEARS) {
				w = 4;
			} else if (item == Items.LEATHER) {
				w = 2;
			}
			
			if (item.getName().toString().contains("ingot")) {
				w *= 0.5f;
			}
			if (item.isFood()) {
				w += 2;
			}
			if (item instanceof ToolItem) {
				w *= 1.2f;
				if (item instanceof HoeItem) {
					w *= 0.75f;
				}
				if (item instanceof AxeItem) {
					w *= 1.1f;
				}
				if (item instanceof ShovelItem) {
					w *= 0.75f;
				}
			}
			if (item instanceof SpearItem) {
				w += 5;
			}
			if (item instanceof ArmorItem) {
				w *= 1.5f;
				ArmorItem armor = (ArmorItem)item;
				if (armor.getSlotType() == EquipmentSlot.HEAD) {
					w *= 0.5f;
				}
				if (armor.getSlotType() == EquipmentSlot.LEGS) {
					w *= 0.8f;
				}
				if (armor.getSlotType() == EquipmentSlot.FEET) {
					w *= 0.4f;
				}
			}
			weight.put(item, w);
		});
	}
	
	public static void Register() {
		RegisterBlockWeights();
		RegisterItemWeights();
	}
}
