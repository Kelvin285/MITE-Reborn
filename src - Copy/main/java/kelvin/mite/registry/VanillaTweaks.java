package kelvin.mite.registry;

import java.lang.reflect.Field;

import kelvin.mite.blocks.MITELogBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractBlock.AbstractBlockState;
import net.minecraft.block.AbstractBlock.Settings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarpetBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SkullBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StainedGlassPaneBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.MushroomStewItem;
import net.minecraft.util.registry.Registry;

public class VanillaTweaks {

	
	private static void ChangeStrength(Block block, float i) {
		try {
			Field PROPERTIES = AbstractBlock.class.getDeclaredFields()[10];
			PROPERTIES.setAccessible(true);
			Settings properties = (Settings) PROPERTIES.get(block);
			properties.strength(i, i);
			

			Field STATE_HARDNESS = AbstractBlockState.class.getDeclaredFields()[5];
			STATE_HARDNESS.setAccessible(true);
			for (BlockState state : block.getStateManager().getStates()) {
				STATE_HARDNESS.set(state, i);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	private static void ChangeStackSize(Item item, int size) {
		//maxcount = 9
		//maxdamage = 10
		Field MAX_COUNT = Item.class.getDeclaredFields()[9];
		MAX_COUNT.setAccessible(true);
		try {
			MAX_COUNT.set(item, size);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void LowerStackSize(Item item, int size) {
		if (item.getMaxCount() < size) return;
		ChangeStackSize(item, size);
	}
	
	private static void ChangeMaxDamage(Item item, int damage) {
		//maxcount = 9
		//maxdamage = 10
		Field MAX_DAMAGE = Item.class.getDeclaredFields()[10];
		MAX_DAMAGE.setAccessible(true);
		try {
			MAX_DAMAGE.set(item, damage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void ChangeBlockStrength() {
		ChangeStrength(Blocks.LILY_PAD, 0.08f);
		ChangeStrength(Blocks.GRASS, 0.08f);
		ChangeStrength(Blocks.TALL_GRASS, 0.08f);
		ChangeStrength(Blocks.DEAD_BUSH, 0.08f);
		ChangeStrength(Blocks.SUGAR_CANE, 0.25f);
		ChangeStrength(Blocks.SEAGRASS, 0.08f);
		ChangeStrength(Blocks.TALL_SEAGRASS, 0.08f);
		ChangeStrength(Blocks.KELP_PLANT, 0.08f);
		ChangeStrength(Blocks.KELP, 0.08f);
		ChangeStrength(Blocks.SWEET_BERRY_BUSH, 0.03f);
		ChangeStrength(Blocks.OBSIDIAN, 0.5f);
		ChangeStrength(Blocks.FURNACE, 0.2f);
		ChangeStrength(Blocks.CRAFTING_TABLE, 0.25f);
		ChangeStrength(Blocks.HAY_BLOCK, 0.1f);
		
	}
	
	public static void ChangeStackSizes() {
		Registry.ITEM.iterator().forEachRemaining((item) -> {
			if (item.getMaxCount() > 32) {
				LowerStackSize(item, 32);
			}
			if (item instanceof BlockItem) {
				LowerStackSize(item, 4);
				if (((BlockItem)item).getBlock() instanceof PlantBlock ||
						item == Items.GRASS ||
						item == Items.FERN ||
						item == Items.DEAD_BUSH ||
						//witherwood bush ||
						item == Items.SNOW ||
						item == Items.LILY_PAD) {
					LowerStackSize(item, 32);
				}
				if (item == Items.COBBLESTONE ||
						item == Items.OAK_PLANKS ||
						item == Items.ACACIA_PLANKS ||
						item == Items.BIRCH_PLANKS ||
						item == Items.CRIMSON_PLANKS ||
						item == Items.DARK_OAK_PLANKS ||
						item == Items.JUNGLE_PLANKS ||
						item == Items.WARPED_PLANKS ||
						item == Items.POWERED_RAIL ||
						item == Items.DETECTOR_RAIL ||
						item == Items.BLACK_WOOL ||
						item == Items.BLUE_WOOL ||
						item == Items.BROWN_WOOL ||
						item == Items.CYAN_WOOL ||
						item == Items.GRAY_WOOL ||
						item == Items.GREEN_WOOL ||
						item == Items.LIGHT_BLUE_WOOL ||
						item == Items.LIGHT_GRAY_WOOL ||
						item == Items.LIME_WOOL ||
						item == Items.MAGENTA_WOOL ||
						item == Items.ORANGE_WOOL ||
						item == Items.PINK_WOOL ||
						item == Items.PURPLE_WOOL ||
						item == Items.RED_WOOL ||
						item == Items.WHITE_WOOL ||
						item == Items.YELLOW_WOOL ||
						item == Items.SMALL_DRIPLEAF ||
						((BlockItem)item).getBlock() instanceof SlabBlock ||
						((BlockItem)item).getBlock() instanceof StairsBlock ||
						((BlockItem)item).getBlock() instanceof PressurePlateBlock ||
						((BlockItem) item).getBlock() instanceof FenceBlock ||
						item == Items.LADDER ||
						item == Items.RAIL ||
						item == Items.PUMPKIN ||
						item == Items.CARVED_PUMPKIN ||
						item == Items.JACK_O_LANTERN ||
						item == Items.SNOW ||
						item == Items.MELON ||
						item == Items.VINE ||
						item == Items.TWISTING_VINES ||
						item == Items.WEEPING_VINES ||
						item == Items.ACTIVATOR_RAIL ||
						((BlockItem)item).getBlock() instanceof CarpetBlock ||
						item == Items.CHAIN ||
						((BlockItem)item).getBlock() instanceof MITELogBlock) {
					LowerStackSize(item, 8);
				} else if (
						((BlockItem)item).getBlock() instanceof SaplingBlock ||
						item == Items.TORCH ||
						item == Items.REDSTONE_TORCH ||
						item == Items.LANTERN ||
						item == Items.SOUL_LANTERN ||
						item == Items.IRON_BARS ||
						((BlockItem)item).getBlock() instanceof StainedGlassPaneBlock ||
						item == Items.PAINTING ||
						item == Items.ACACIA_SIGN ||
						item == Items.REDSTONE ||
						item == Items.REPEATER ||
						item == Items.FLOWER_POT ||
						item == Items.ITEM_FRAME ||
						item == Items.CARROT ||
						item == Items.POTATO ||
						((BlockItem)item).getBlock() instanceof SkullBlock ||
						item == Items.COMPARATOR) {
						LowerStackSize(item, 16);
				}
					
			} else {
				if (item == Items.IRON_INGOT ||
						item == Items.GOLD_INGOT ||
						item == Items.COPPER_INGOT ||
						item == ItemRegistry.SILVER_INGOT ||
						item == Items.NETHERITE_INGOT ||
						item == Items.APPLE ||
						item == Items.COAL ||
						item == Items.CHARCOAL ||
						item == ItemRegistry.CHARRED_FOOD ||
						item == Items.BOWL ||
						item == Items.STRING ||
						item == Items.FEATHER ||
						item == Items.GUNPOWDER ||
						item == Items.WHEAT ||
						item == Items.BREAD ||
						item == Items.FLINT ||
						item == Items.PORKCHOP ||
						item == Items.COOKED_PORKCHOP ||
						item == Items.PAINTING ||
						item == Items.GOLDEN_APPLE ||
						item == Items.ENCHANTED_GOLDEN_APPLE ||
						item == Items.SNOWBALL ||
						item == Items.LEATHER ||
						item == Items.CLAY_BALL ||
						item == Items.SUGAR_CANE ||
						item == Items.BOOK ||
						item == Items.SLIME_BALL ||
						item == Items.EGG ||
						item == ItemRegistry.COOKED_EGG ||
						item == Items.COMPASS ||
						item == Items.CLOCK ||
						item == Items.GLOWSTONE_DUST ||
						item == Items.COD ||
						item == Items.COOKED_COD ||
						item.isFood() && !(item instanceof MushroomStewItem)||
						item instanceof DyeItem ||
						item == Items.BLAZE_ROD ||
						item == Items.GHAST_TEAR ||
						item == Items.SPIDER_EYE ||
						item == Items.FERMENTED_SPIDER_EYE ||
						!(item instanceof BlockItem) ||
						item == ItemRegistry.ROCK) {
					LowerStackSize(item, 16);
				} else if (item == Items.BUCKET) { 
					LowerStackSize(item, 8);
				}
			}
			if (item == Items.WHEAT_SEEDS ||
					item == Items.PAPER ||
					item == Items.PUMPKIN_SEEDS ||
					item == Items.MELON_SEEDS ||
					item == Items.GOLD_NUGGET ||
					item == ItemRegistry.COPPER_NUGGET ||
					item == ItemRegistry.SILVER_NUGGET ||
					item == Items.IRON_NUGGET ||
					item == ItemRegistry.FLINT_SHARD ||
					item == Items.NETHER_WART
					//add coins later
					) {
				LowerStackSize(item, 64);
			}
		});
	}
	
	public static void ChangeItemDurability() {
		ChangeMaxDamage(Items.ANVIL, 396800);
		ChangeMaxDamage(Items.IRON_SHOVEL, 3200);
		ChangeMaxDamage(Items.IRON_PICKAXE, 9600);
		ChangeMaxDamage(Items.IRON_AXE, 9600);
		ChangeMaxDamage(Items.FLINT_AND_STEEL, 16);
		ChangeMaxDamage(Items.BOW, 32);
		ChangeMaxDamage(Items.IRON_SWORD, 6400);
		ChangeMaxDamage(Items.WOODEN_SHOVEL, 200);
		ChangeMaxDamage(Items.GOLDEN_SWORD, 3200);
		ChangeMaxDamage(Items.GOLDEN_SHOVEL, 1600);
		ChangeMaxDamage(Items.GOLDEN_PICKAXE, 4800);
		ChangeMaxDamage(Items.GOLDEN_AXE, 4800);
		ChangeMaxDamage(Items.IRON_HOE, 6400);
		ChangeMaxDamage(Items.GOLDEN_HOE, 3200);
		ChangeMaxDamage(Items.LEATHER_HELMET, 10);
		ChangeMaxDamage(Items.LEATHER_CHESTPLATE, 16);
		ChangeMaxDamage(Items.LEATHER_LEGGINGS, 14);
		ChangeMaxDamage(Items.LEATHER_BOOTS, 8);
		ChangeMaxDamage(Items.CHAINMAIL_HELMET, 40);
		ChangeMaxDamage(Items.CHAINMAIL_CHESTPLATE, 64);
		ChangeMaxDamage(Items.CHAINMAIL_LEGGINGS, 56);
		ChangeMaxDamage(Items.CHAINMAIL_BOOTS, 32);
		ChangeMaxDamage(Items.GOLDEN_HELMET, 40);
		ChangeMaxDamage(Items.GOLDEN_CHESTPLATE, 64);
		ChangeMaxDamage(Items.GOLDEN_LEGGINGS, 56);
		ChangeMaxDamage(Items.GOLDEN_BOOTS, 32);
		ChangeMaxDamage(Items.FISHING_ROD, 16);
		ChangeMaxDamage(Items.SHEARS, 6400);
		ChangeMaxDamage(Items.CARROT_ON_A_STICK, 25);
		ChangeMaxDamage(Items.WARPED_FUNGUS_ON_A_STICK, 25);
		ChangeMaxDamage(ItemRegistry.COPPER_PICKAXE, 4800);
		ChangeMaxDamage(ItemRegistry.SILVER_PICKAXE, 4800);
		ChangeMaxDamage(ItemRegistry.FLINT_SHOVEL, 400);
		ChangeMaxDamage(ItemRegistry.COPPER_SHOVEL, 1600);
		ChangeMaxDamage(ItemRegistry.SILVER_SHOVEL, 1600);
		ChangeMaxDamage(ItemRegistry.FLINT_HATCHET, 400);
		ChangeMaxDamage(ItemRegistry.FLINT_AXE, 1200);
		ChangeMaxDamage(ItemRegistry.COPPER_AXE, 4800);
		ChangeMaxDamage(ItemRegistry.SILVER_AXE, 4800);
		ChangeMaxDamage(ItemRegistry.COPPER_HOE, 3200);
		ChangeMaxDamage(ItemRegistry.SILVER_HOE, 3200);
		ChangeMaxDamage(ItemRegistry.COPPER_SPEAR, 3200);
		ChangeMaxDamage(ItemRegistry.SILVER_SPEAR, 3200);
		ChangeMaxDamage(ItemRegistry.SPEAR, 1200);
		ChangeMaxDamage(ItemRegistry.FLINT_KNIFE, 400);
		ChangeMaxDamage(ItemRegistry.WOODEN_CUDGEL, 200);
		ChangeMaxDamage(ItemRegistry.WOODEN_CLUB, 400);
		ChangeMaxDamage(ItemRegistry.COPPER_SWORD, 3200);
		ChangeMaxDamage(ItemRegistry.SILVER_SWORD, 3200);
		ChangeMaxDamage(ItemRegistry.COPPER_KNIFE, 1600);
		ChangeMaxDamage(ItemRegistry.SILVER_KNIFE, 1600);
		ChangeMaxDamage(ItemRegistry.COPPER_HATCHET, 1600);
		ChangeMaxDamage(ItemRegistry.SILVER_HATCHET, 1600);
		

		ChangeMaxDamage(Items.STONE_PICKAXE, 900);
		ChangeMaxDamage(Items.STONE_AXE, 900);
		ChangeMaxDamage(Items.STONE_SHOVEL, 900);
	}
	
	public static void ApplyChanges() {
		ChangeBlockStrength();
		ChangeStackSizes();
		ChangeItemDurability();
	}
}
