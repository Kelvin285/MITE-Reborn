package kelvin.mite.registry;

import java.lang.reflect.Field;
import java.util.Set;

import kelvin.mite.items.*;
import kelvin.mite.main.resources.Resources;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Settings;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.StewItem;
import net.minecraft.item.ShieldItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemRegistry {
	
	public static Item  DRY_GRASS;// = ITEMS.Register("dry_grass", () -> new Item(new Settings().group(ItemGroup.MATERIALS).maxCount(32)));
	public static Item  FLAX_FIBERS;// = ITEMS.Register("flax_fibers", () -> new Item(new Settings().group(ItemGroup.MATERIALS).maxCount(8)));

	public static Item  ROCK;// = ITEMS.Register("smooth_stone", () -> new Item(new Settings().group(ItemGroup.MATERIALS).maxCount(8)));
	public static Item  STRIPPED_BARK;// = ITEMS.Register("stripped_bark", () -> new ItemBurnable(new Settings().group(ItemGroup.MATERIALS).maxCount(32), 20 * 4));
	public static Item  FLINT_SHARD;// = ITEMS.Register("flint_shard", () -> new Item(new Settings().group(ItemGroup.MATERIALS).maxCount(64)));
	public static Item  FLINT_HATCHET;// = ITEMS.Register("flint_hatchet", () -> new HatchetItem(SurvivalItemTier.FLINT_HATCHET, 2.0F, -3.0F, new Settings().group(ItemGroup.COMBAT).maxCount(1)));
	public static Item  FLINT_AXE;// = ITEMS.Register("flint_axe", () -> new AxeItem(SurvivalItemTier.FLINT_AXE, 2.0F, -3.0F, new Settings().group(ItemGroup.COMBAT).maxCount(1)));
	public static Item  FLINT_SHOVEL;// = ITEMS.Register("flint_shovel", () -> new ShovelItem(SurvivalItemTier.FLINT_SHOVEL, 1.5F, -3.0F, new Settings().group(ItemGroup.COMBAT).maxCount(1)));
	public static Item  FLINT_KNIFE;// = ITEMS.Register("flint_knife", () -> new ShortswordItem(SurvivalItemTier.FLINT_SHORTSWORD, 2, -0.5F, new Settings().group(ItemGroup.COMBAT).maxCount(1)));
	public static Item  WOODEN_CUDGEL;// = ITEMS.Register("wooden_cudgel", () -> new CudgelItem(SurvivalItemTier.WOOD_SHORTSWORD, 3, -0.5F, new Settings().group(ItemGroup.COMBAT).maxCount(1)));
	public static Item  WOODEN_CLUB;// = ITEMS.Register("wooden_club", () -> new ClubItem(SurvivalItemTier.WOOD_SWORD, 4, -2.5F, new Settings().group(ItemGroup.COMBAT).maxCount(1)));
	public static Item  SINEW;// = ITEMS.Register("sinew", () -> new Item(new Settings().group(ItemGroup.MATERIALS).maxCount(16)));
	public static Item  SPEAR;// = ITEMS.Register("spear", () -> new SpearItem(new Settings().group(ItemGroup.COMBAT).maxCount(1).maxDamage(10), 2.0D));
	public static Item  COPPER_SPEAR;// = ITEMS.Register("copper_spear", () -> new SpearItem(new Settings().group(ItemGroup.COMBAT).maxCount(1).maxDamage(30), 3.0D));
	public static Item  SILVER_SPEAR;// = ITEMS.Register("silver_spear", () -> new SpearItem(new Settings().group(ItemGroup.COMBAT).maxCount(1).maxDamage(30), 3.0D));
	public static Item  GOLD_SPEAR;// = ITEMS.Register("gold_spear", () -> new SpearItem(new Settings().group(ItemGroup.COMBAT).maxCount(1).maxDamage(40), 3.0D));
	public static Item  IRON_SPEAR;// = ITEMS.Register("iron_spear", () -> new SpearItem(new Settings().group(ItemGroup.COMBAT).maxCount(1).maxDamage(70), 4.0D));
	public static Item  MITHRIL_SPEAR;// = ITEMS.Register("mithril_spear", () -> new SpearItem(new Settings().group(ItemGroup.COMBAT).maxCount(1).maxDamage(120), 5.0D));
	public static Item  ANCIENT_METAL_SPEAR;// = ITEMS.Register("ancient_metal_spear", () -> new SpearItem(new Settings().group(ItemGroup.COMBAT).maxCount(1).maxDamage(150), 6.0D));
	public static Item  ADAMANTIUM_SPEAR;// = ITEMS.Register("adamantium_spear", () -> new SpearItem(new Settings().group(ItemGroup.COMBAT).maxCount(1).maxDamage(300), 7.0D));

	
	public static Item  WOODEN_SHIELD;// = ITEMS.Register("wooden_shield", () -> new ShieldItem(new Settings().group(ItemGroup.COMBAT).maxCount(1).maxDamage(15)));
	public static Item  FLAX_SEEDS;// = ITEMS.Register("flax_seeds", () -> new Item((new Settings()).group(ItemGroup.FOOD).maxCount(64).food(new FoodComponent.Builder().hunger(1).build())));
	public static Item  FLAX;// = ITEMS.Register("flax", () -> new BlockItem(BlockRegistry.FLAX.get(), (new Settings()).group(ItemGroup.MATERIALS).maxCount(8)));
	public static Item  FLINT_CRAFTING_TABLE;// = ITEMS.Register("flint_crafting_table", () -> new BlockItem(BlockRegistry.FLINT_CRAFTING_TABLE.get(), (new Settings()).group(ItemGroup.BUILDING_BLOCKS).maxCount(1)));
	public static Item  COPPER_CRAFTING_TABLE;// = ITEMS.Register("copper_crafting_table", () -> new BlockItem(BlockRegistry.COPPER_CRAFTING_TABLE.get(), (new Settings()).group(ItemGroup.BUILDING_BLOCKS).maxCount(1)));
	public static Item  SILVER_CRAFTING_TABLE;// = ITEMS.Register("silver_crafting_table", () -> new BlockItem(BlockRegistry.SILVER_CRAFTING_TABLE.get(), (new Settings()).group(ItemGroup.BUILDING_BLOCKS).maxCount(1)));
	public static Item  GOLD_CRAFTING_TABLE;// = ITEMS.Register("gold_crafting_table", () -> new BlockItem(BlockRegistry.GOLD_CRAFTING_TABLE.get(), (new Settings()).group(ItemGroup.BUILDING_BLOCKS).maxCount(1)));
	public static Item  IRON_CRAFTING_TABLE;// = ITEMS.Register("iron_crafting_table", () -> new BlockItem(BlockRegistry.IRON_CRAFTING_TABLE.get(), (new Settings()).group(ItemGroup.BUILDING_BLOCKS).maxCount(1)));
	public static Item  MITHRIL_CRAFTING_TABLE;// = ITEMS.Register("mithril_crafting_table", () -> new BlockItem(BlockRegistry.MITHRIL_CRAFTING_TABLE.get(), (new Settings()).group(ItemGroup.BUILDING_BLOCKS).maxCount(1)));
	public static Item  ANCIENT_METAL_CRAFTING_TABLE;// = ITEMS.Register("ancient_metal_crafting_table", () -> new BlockItem(BlockRegistry.ANCIENT_METAL_CRAFTING_TABLE.get(), (new Settings()).group(ItemGroup.BUILDING_BLOCKS).maxCount(1)));
	public static Item  ADAMANTIUM_CRAFTING_TABLE;// = ITEMS.Register("adamantium_crafting_table", () -> new BlockItem(BlockRegistry.ADAMANTIUM_CRAFTING_TABLE.get(), (new Settings()).group(ItemGroup.BUILDING_BLOCKS).maxCount(1)));
	public static Item  OBSIDIAN_CRAFTING_TABLE;// = ITEMS.Register("obsidian_crafting_table", () -> new BlockItem(BlockRegistry.OBSIDIAN_CRAFTING_TABLE.get(), (new Settings()).group(ItemGroup.BUILDING_BLOCKS).maxCount(1)));
	public static Item  BACON;// = ITEMS.Register("bacon", () -> new Item((new Settings()).group(ItemGroup.FOOD).maxCount(16).food((new FoodComponent.Builder()).hunger(2).saturationModifier(0.2F).meat().build())));
	public static Item  COOKED_BACON;// = ITEMS.Register("cooked_bacon", () -> new Item((new Settings()).group(ItemGroup.FOOD).maxCount(16).food((new FoodComponent.Builder()).hunger(5).saturationModifier(0.4F).meat().build())));
	public static Item TURKEY_RAW;
	public static Item TURKEY_COOKED;
	public static Item  TWIG;// = ITEMS.Register("twig", () -> new ItemBurnable((new Settings()).group(ItemGroup.MISC).maxCount(32), 50));
	public static Item  BRANCH;// = ITEMS.Register("twig", () -> new ItemBurnable((new Settings()).group(ItemGroup.MISC).maxCount(32), 50));
	public static Item  CHARRED_FOOD;// = ITEMS.Register("charred_food", () -> new Item((new Settings()).group(ItemGroup.MISC).maxCount(16)));
	public static Item  CLAY_OVEN;// = ITEMS.Register("clay_oven", () -> new BlockItem(BlockRegistry.CLAY_OVEN.get(), (new Settings()).group(ItemGroup.BUILDING_BLOCKS).maxCount(1)));
	public static Item  HARDENED_CLAY_OVEN;// = ITEMS.Register("hardened_clay_oven", () -> new BlockItem(BlockRegistry.HARDENED_CLAY_OVEN.get(), (new Settings()).group(ItemGroup.BUILDING_BLOCKS).maxCount(1)));
	public static Item  SANDSTONE_OVEN;// = ITEMS.Register("sandstone_oven", () -> new BlockItem(BlockRegistry.SANDSTONE_OVEN.get(), (new Settings()).group(ItemGroup.BUILDING_BLOCKS).maxCount(1)));
	public static Item  COBBLESTONE_FURNACE;// = ITEMS.Register("cobblestone_furnace", () -> new BlockItem(BlockRegistry.COBBLESTONE_FURNACE.get(), (new Settings()).group(ItemGroup.BUILDING_BLOCKS).maxCount(1)));
	public static Item  OBSIDIAN_FURNACE;// = ITEMS.Register("obsidian_furnace", () -> new BlockItem(BlockRegistry.OBSIDIAN_FURNACE.get(), (new Settings()).group(ItemGroup.BUILDING_BLOCKS).maxCount(1)));
	public static Item  NETHERRACK_FURNACE;// = ITEMS.Register("netherrack_furnace", () -> new BlockItem(BlockRegistry.NETHERRACK_FURNACE.get(), (new Settings()).group(ItemGroup.BUILDING_BLOCKS).maxCount(1)));
	public static Item  PEA_GRAVEL;// = ITEMS.Register("pea_gravel", () -> new BlockItem(BlockRegistry.PEA_GRAVEL.get(), (new Settings()).group(ItemGroup.BUILDING_BLOCKS)));
	public static Item  MANURE;// = ITEMS.Register("manure", () -> new ItemManure((new Settings()).group(ItemGroup.MISC).maxCount(16)));
	public static Item  COBWEB_BLOCK;// = ITEMS.Register("cobweb_block", () -> new BlockItem(BlockRegistry.COBWEB_BLOCK.get(), new Settings().group(ItemGroup.BUILDING_BLOCKS)));
	public static Item  SHINING_GRAVEL;// = ITEMS.Register("shining_gravel", () -> new BlockItem(BlockRegistry.SHINING_GRAVEL.get(), new Settings().group(ItemGroup.BUILDING_BLOCKS)));
	public static Item  SHINING_PEA_GRAVEL;// = ITEMS.Register("shining_pea_gravel", () -> new BlockItem(BlockRegistry.SHINING_PEA_GRAVEL.get(), new Settings().group(ItemGroup.BUILDING_BLOCKS)));
	public static Item  COPPER_ORE;// = ITEMS.Register("copper_ore", () -> new BlockItem(BlockRegistry.COPPER_ORE.get(), new Settings().group(ItemGroup.BUILDING_BLOCKS)));
	public static Item  SILVER_ORE;// = ITEMS.Register("silver_ore", () -> new BlockItem(BlockRegistry.SILVER_ORE.get(), new Settings().group(ItemGroup.BUILDING_BLOCKS)));
	public static Item  COPPER_NUGGET;// = ITEMS.Register("copper_nugget", () -> new Item((new Settings()).group(ItemGroup.MISC).maxCount(32)));
	public static Item  SILVER_NUGGET;// = ITEMS.Register("silver_nugget", () -> new Item((new Settings()).group(ItemGroup.MISC).maxCount(32)));
	public static Item  SILVER_INGOT;// = ITEMS.Register("silver_ingot", () -> new Item((new Settings()).group(ItemGroup.MISC).maxCount(16)));
	public static Item  COPPER_PICKAXE;// = ITEMS.Register("copper_pickaxe", () -> new PickaxeItem(SurvivalItemTier.COPPER_PICKAXE, 1, -2.0f, new Settings().group(ItemGroup.TOOLS)));
	public static Item  COPPER_AXE;// = ITEMS.Register("copper_axe", () -> new AxeItem(SurvivalItemTier.COPPER_AXE, 5, -3.1f, new Settings().group(ItemGroup.TOOLS)));
	public static Item  COPPER_SHOVEL;// = ITEMS.Register("copper_shovel", () -> new ShovelItem(SurvivalItemTier.COPPER_SHOVEL, 1.0f, -3.0f, new Settings().group(ItemGroup.TOOLS)));
	public static Item  COPPER_SWORD;// = ITEMS.Register("copper_sword", () -> new SwordItem(SurvivalItemTier.COPPER_SWORD, 3, -2.4F, new Settings().group(ItemGroup.COMBAT)));
	public static Item  COPPER_KNIFE;// = ITEMS.Register("copper_knife", () -> new SwordItem(SurvivalItemTier.COPPER_SHORTSWORD, 2, -1.0F, new Settings().group(ItemGroup.COMBAT)));
	public static Item  COPPER_HOE;// = ITEMS.Register("copper_hoe", () -> new HoeItem(SurvivalItemTier.COPPER_SHORTSWORD, 1, -1.0F, new Settings().group(ItemGroup.COMBAT)));
	public static Item  COPPER_HATCHET;// = ITEMS.Register("copper_hatchet", () -> new AxeItem(SurvivalItemTier.COPPER_HATCHET, 3, -2.5f, new Settings().group(ItemGroup.TOOLS)));

	public static Item  SILVER_PICKAXE;// = ITEMS.Register("silver_pickaxe", () -> new PickaxeItem(SurvivalItemTier.COPPER_PICKAXE, 1, -2.0f, new Settings().group(ItemGroup.TOOLS)));
	public static Item  SILVER_AXE;// = ITEMS.Register("silver_axe", () -> new AxeItem(SurvivalItemTier.COPPER_AXE, 5, -3.1f, new Settings().group(ItemGroup.TOOLS)));
	public static Item  SILVER_SHOVEL;// = ITEMS.Register("silver_shovel", () -> new ShovelItem(SurvivalItemTier.COPPER_SHOVEL, 1.0f, -3.0f, new Settings().group(ItemGroup.TOOLS)));
	public static Item  SILVER_SWORD;// = ITEMS.Register("silver_sword", () -> new SwordItem(SurvivalItemTier.COPPER_SWORD, 3, -2.4F, new Settings().group(ItemGroup.COMBAT)));
	public static Item  SILVER_KNIFE;// = ITEMS.Register("silver_knife", () -> new SwordItem(SurvivalItemTier.COPPER_SHORTSWORD, 2, -1.0F, new Settings().group(ItemGroup.COMBAT)));
	public static Item  SILVER_HOE;// = ITEMS.Register("silver_hoe", () -> new HoeItem(SurvivalItemTier.COPPER_SHORTSWORD, 1, -1.0F, new Settings().group(ItemGroup.COMBAT)));
	public static Item  SILVER_HATCHET;// = ITEMS.Register("silver_hatchet", () -> new AxeItem(SurvivalItemTier.COPPER_HATCHET, 3, -2.5f, new Settings().group(ItemGroup.TOOLS)));
	
	public static Item  SALAD;// = ITEMS.Register("salad", () -> new SoupItem((new Settings()).maxCount(1).group(ItemGroup.FOOD).food(buildStew(1, 0.1f))));
	public static Item  BANANA_SPLIT;// = ITEMS.Register("banana_split", () -> new SoupItem((new Settings()).maxCount(1).group(ItemGroup.FOOD).food(buildStew(7, 0.5f))));
	public static Item  MILK_BOWL;// = ITEMS.Register("bowl_milk", () -> new SoupItem((new Settings()).maxCount(1).group(ItemGroup.FOOD).food(buildStew(1, 0))));
	public static Item  WATER_BOWL;// = ITEMS.Register("bowl_water", () -> new SoupItem((new Settings()).maxCount(1).group(ItemGroup.FOOD).food(buildStew(0, 0))));
	public static Item  CEREAL;// = ITEMS.Register("cereal", () -> new SoupItem((new Settings()).maxCount(1).group(ItemGroup.FOOD).food(buildStew(5, 0.5f))));
	public static Item  CHICKEN_SOUP;// = ITEMS.Register("chicken_soup", () -> new SoupItem((new Settings()).maxCount(1).group(ItemGroup.FOOD).food(buildStew(8, 1))));
	public static Item  CREAM_OF_MUSHROOM_SOUP;// = ITEMS.Register("cream_of_mushroom_soup", () -> new SoupItem((new Settings()).maxCount(1).group(ItemGroup.FOOD).food(buildStew(4, 0.3f))));
	public static Item  CREAM_OF_VEGETABLE_SOUP;// = ITEMS.Register("cream_of_vegetable_soup", () -> new SoupItem((new Settings()).maxCount(1).group(ItemGroup.FOOD).food(buildStew(6, 0.5f))));
	public static Item  ICE_CREAM;// = ITEMS.Register("ice_cream", () -> new SoupItem((new Settings()).maxCount(1).group(ItemGroup.FOOD).food(buildStew(3, 0.4f))));
	public static Item  MASHED_POTATOES;// = ITEMS.Register("mashed_potatoes", () -> new SoupItem((new Settings()).maxCount(1).group(ItemGroup.FOOD).food(buildStew(5, 0.5f))));
	public static Item  PORRIDGE;// = ITEMS.Register("porridge", () -> new SoupItem((new Settings()).maxCount(1).group(ItemGroup.FOOD).food(buildStew(5, 0.5f))));
	public static Item  PUMPKIN_SOUP;// = ITEMS.Register("pumpkin_soup", () -> new SoupItem((new Settings()).maxCount(1).group(ItemGroup.FOOD).food(buildStew(2, 0.2f))));
	public static Item  SORBET;// = ITEMS.Register("sorbet", () -> new SoupItem((new Settings()).maxCount(1).group(ItemGroup.FOOD).food(buildStew(5, 0.6f))));
	public static Item  VEGETABLE_SOUP;// = ITEMS.Register("vegetable_soup", () -> new SoupItem((new Settings()).maxCount(1).group(ItemGroup.FOOD).food(buildStew(7, 0.7f))));
	public static Item  BEEF_STEW;// = ITEMS.Register("beef_stew", () -> new SoupItem((new Settings()).maxCount(1).group(ItemGroup.FOOD).food(buildStew(11, 1f))));
	public static Item  CHOCOLATE_ICE_CREAM;// = ITEMS.Register("chocolate_ice_cream", () -> new SoupItem((new Settings()).maxCount(1).group(ItemGroup.FOOD).food(buildStew(4, 0.5f))));

	
	public static Item  COOKED_EGG;// = ITEMS.Register("cooked_egg", () -> new Item((new Settings()).group(ItemGroup.FOOD).maxCount(16).food((new FoodComponent.Builder()).hunger(4).saturationModifier(0.3F).meat().build())));
	public static Item  BANANA;// = ITEMS.Register("banana", () -> new Item((new Settings()).group(ItemGroup.FOOD).maxCount(16).food((new FoodComponent.Builder()).hunger(2).saturationModifier(0.2F).build())));
	public static Item  BLUEBERRIES;// = ITEMS.Register("blueberries", () -> new Item((new Settings()).group(ItemGroup.FOOD).maxCount(16).food((new FoodComponent.Builder()).hunger(1).saturationModifier(0.1F).build())));
	public static Item  BLUEBERRY_MUFFIN;// = ITEMS.Register("blueberry_muffin", () -> new Item((new Settings()).group(ItemGroup.FOOD).maxCount(16).food((new FoodComponent.Builder()).hunger(5).saturationModifier(0.2F).build())));
	public static Item  BROWNIE;// = ITEMS.Register("brownie", () -> new Item((new Settings()).group(ItemGroup.FOOD).maxCount(16).food((new FoodComponent.Builder()).hunger(3).saturationModifier(0.2F).build())));
	public static Item  CHEESE;// = ITEMS.Register("cheese", () -> new Item((new Settings()).group(ItemGroup.FOOD).maxCount(16).food((new FoodComponent.Builder()).hunger(3).saturationModifier(0.4F).build())));
	public static Item  CHERRIES;// = ITEMS.Register("cherries", () -> new Item((new Settings()).group(ItemGroup.FOOD).maxCount(16).food((new FoodComponent.Builder()).hunger(2).saturationModifier(0.2F).build())));
	public static Item  CHOCOLATE;// = ITEMS.Register("chocolate", () -> new Item((new Settings()).group(ItemGroup.FOOD).maxCount(16).food((new FoodComponent.Builder()).hunger(3).saturationModifier(0.2F).build())));
	public static Item  COOKIE_DOUGH;// = ITEMS.Register("cookie_dough", () -> new Item((new Settings()).group(ItemGroup.FOOD).maxCount(16).food((new FoodComponent.Builder()).hunger(4).saturationModifier(0.4F).build())));
	public static Item  DOUGH;// = ITEMS.Register("dough", () -> new Item((new Settings()).group(ItemGroup.FOOD).maxCount(16).food((new FoodComponent.Builder()).hunger(5).saturationModifier(0.4F).build())));
	public static Item  FLOUR;// = ITEMS.Register("flour", () -> new Item((new Settings()).group(ItemGroup.FOOD).maxCount(16).food((new FoodComponent.Builder()).hunger(0).saturationModifier(0.2F).build())));
	public static Item  LEMON;// = ITEMS.Register("lemon", () -> new Item((new Settings()).group(ItemGroup.FOOD).maxCount(16).food((new FoodComponent.Builder()).hunger(2).saturationModifier(0.3F).build())));
	public static Item  ONION;// = ITEMS.Register("onion", () -> new Item((new Settings()).group(ItemGroup.FOOD).maxCount(16).food((new FoodComponent.Builder()).hunger(4).saturationModifier(0.4F).build())));
	public static Item  ORANGE;// = ITEMS.Register("orange", () -> new Item((new Settings()).group(ItemGroup.FOOD).maxCount(16).food((new FoodComponent.Builder()).hunger(3).saturationModifier(0.4F).build())));
	public static Item  RAW_WORM;// = ITEMS.Register("worm_raw", () -> new Item((new Settings()).group(ItemGroup.FOOD).maxCount(16).food((new FoodComponent.Builder()).hunger(1).saturationModifier(0.1F).meat().build())));
	public static Item  COOKED_WORM;// = ITEMS.Register("worm_cooked", () -> new Item((new Settings()).group(ItemGroup.FOOD).maxCount(16).food((new FoodComponent.Builder()).hunger(3).saturationModifier(0.3F).meat().build())));

	public static Item  DOWN_FEATHER;// = ITEMS.Register("down_feather", () -> new Item((new Settings()).group(ItemGroup.MATERIALS).maxCount(16)));
	public static Item  DOWN_FEATHER_BLOCK;// = ITEMS.Register("down_feather_block", () -> new BlockItem(BlockRegistry.DOWN_FEATHER_BLOCK.get(), new Settings().group(ItemGroup.BUILDING_BLOCKS)));

	public static Item THIN_OAK_LOG;
	public static Item THIN_BIRCH_LOG;
	public static Item THIN_SPRUCE_LOG;

	public static Item SIFTER;

//	public static Item COPPER_SHEARS, COPPER_WAR_HAMMER, COPPER_BATTLE_AXE, COPPER_MATTOCK;
//	public static Item SILVER_SHEARS, SILVER_WAR_HAMMER, SILVER_BATTLE_AXE, SILVER_MATTOCK;

	public static Item UNBAKED_CAKE;// = ITEMS.Register("unbaked_cake", () -> new BlockItem(BlockRegistry.UNBAKED_CAKE.get(), new Settings().group(ItemGroup.FOOD).maxCount(1)));

	private static FoodComponent buildStew(int hunger, float saturation) {
        return (new FoodComponent.Builder()).hunger(hunger).saturationModifier(saturation).build();
     }
	
	public static Item Register(String name, Item item) {
		AddLang(name);
		return Register(new Identifier("mite:"+name), item);
	}
	
	public static Item Register(Identifier id, Item item) {
		if (item instanceof BlockItem) {
			((BlockItem) item).appendBlocks(Item.BLOCK_ITEMS, item);
		}

		return (Item) Registry.register(Registry.ITEM, id, item);
	}
	
	public static Item Register(Block block, Item item) {
		return Register(Registry.BLOCK.getId(block), item);
	}
	
	public static Item Register(Block block, ItemGroup group) {
		return Register(block, new BlockItem(block, new Settings().group(group)));
	}
	
	public static Item Register(Block block) {
		return Register(block, ItemGroup.BUILDING_BLOCKS);
	}
	
	public static void RegisterAll(Block...blocks) {
		for (int i = 0; i < blocks.length; i++) {
			Register(blocks[i]);
		}
	}
	
	private static void AddLang(String name) {
		
		String block_name = name;
		
		String capitalized_name = "";
		String[] split_name = block_name.split("_");
		for (String str : split_name) {
			
			boolean first = true;
			if (str.equals("with") || str.length() <= 2 || str.equals("and")) {
				first = false;
			}
			for (char c : str.toCharArray()) {
				if (first) {
					capitalized_name += (""+c).toUpperCase();
				} else {
					capitalized_name += c;
				}
				first = false;
			}
			capitalized_name += " ";
		}
		capitalized_name = capitalized_name.substring(0, capitalized_name.length() - 1);
		
		Initialization.en_us_lang += "  \"item.mite."+block_name+"\": \""+capitalized_name+"\",\n";
		
	}
	
	public static void RegisterItems() {
		DRY_GRASS = Register("dry_grass", new Item(new Settings().group(ItemGroup.MATERIALS)));
		FLAX_FIBERS = Register("flax_fibers", new Item(new Settings().group(ItemGroup.MATERIALS)));

		STRIPPED_BARK = Register("stripped_bark", new ItemBurnable(new Settings().group(ItemGroup.MATERIALS), 20 * 4));
		FLINT_SHARD = Register("flint_shard", new Item(new Settings().group(ItemGroup.MATERIALS).maxCount(64)));
		FLINT_HATCHET = Register("flint_hatchet", new HatchetItem(SurvivalItemTier.FLINT_HATCHET, 2.0F, -3.0F, new Settings().group(ItemGroup.COMBAT).maxCount(1)));
		FLINT_AXE = Register("flint_axe", new MiteAxeItem(SurvivalItemTier.FLINT_AXE, 2.0F, -3.0F, new Settings().group(ItemGroup.COMBAT).maxCount(1)));
		FLINT_SHOVEL = Register("flint_shovel", new ShovelItem(SurvivalItemTier.FLINT_SHOVEL, 1.5F, -3.0F, new Settings().group(ItemGroup.COMBAT).maxCount(1)));
		FLINT_KNIFE = Register("flint_knife", new KnifeItem(SurvivalItemTier.FLINT_SHORTSWORD, 2, -0.5F, new Settings().group(ItemGroup.COMBAT).maxCount(1)));
		WOODEN_CUDGEL = Register("wooden_cudgel", new CudgelItem(SurvivalItemTier.WOOD_SHORTSWORD, 3, -0.5F, new Settings().group(ItemGroup.COMBAT).maxCount(1)));
		WOODEN_CLUB = Register("wooden_club", new ClubItem(SurvivalItemTier.WOOD_SWORD, 4, -2.5F, new Settings().group(ItemGroup.COMBAT).maxCount(1)));
		SINEW = Register("sinew", new Item(new Settings().group(ItemGroup.MATERIALS)));
		SPEAR = Register("spear", new SpearItem(new Settings().group(ItemGroup.COMBAT).maxCount(1).maxDamage(10), 2.0D));
		COPPER_SPEAR = Register("copper_spear", new SpearItem(new Settings().group(ItemGroup.COMBAT).maxCount(1).maxDamage(30), 3.0D));
		SILVER_SPEAR = Register("silver_spear", new SpearItem(new Settings().group(ItemGroup.COMBAT).maxCount(1).maxDamage(30), 3.0D));
		GOLD_SPEAR = Register("gold_spear", new SpearItem(new Settings().group(ItemGroup.COMBAT).maxCount(1).maxDamage(40), 3.0D));
		IRON_SPEAR = Register("iron_spear", new SpearItem(new Settings().group(ItemGroup.COMBAT).maxCount(1).maxDamage(70), 4.0D));
		MITHRIL_SPEAR = Register("mithril_spear", new SpearItem(new Settings().group(ItemGroup.COMBAT).maxCount(1).maxDamage(120), 5.0D));
		ANCIENT_METAL_SPEAR = Register("ancient_metal_spear", new SpearItem(new Settings().group(ItemGroup.COMBAT).maxCount(1).maxDamage(150), 6.0D));
		ADAMANTIUM_SPEAR = Register("adamantium_spear", new SpearItem(new Settings().group(ItemGroup.COMBAT).maxCount(1).maxDamage(300), 7.0D));

		WOODEN_SHIELD = Register("wooden_shield", new ShieldItem(new Settings().maxDamage(15).group(ItemGroup.COMBAT)));
		FLAX_SEEDS = Register("flax_seeds", new Item((new Settings()).group(ItemGroup.FOOD).food(new FoodComponent.Builder().hunger(1).build())));
		FLAX = Register("flax", new BlockItem(BlockRegistry.FLAX, (new Settings()).group(ItemGroup.MATERIALS)));
		FLINT_CRAFTING_TABLE = Register("flint_crafting_table", new BlockItem(BlockRegistry.FLINT_CRAFTING_TABLE, (new Settings()).group(ItemGroup.BUILDING_BLOCKS).maxCount(1)));
		COPPER_CRAFTING_TABLE = Register("copper_crafting_table", new BlockItem(BlockRegistry.COPPER_CRAFTING_TABLE, (new Settings()).group(ItemGroup.BUILDING_BLOCKS).maxCount(1)));
		SILVER_CRAFTING_TABLE = Register("silver_crafting_table", new BlockItem(BlockRegistry.SILVER_CRAFTING_TABLE, (new Settings()).group(ItemGroup.BUILDING_BLOCKS).maxCount(1)));
		GOLD_CRAFTING_TABLE = Register("gold_crafting_table", new BlockItem(BlockRegistry.GOLD_CRAFTING_TABLE, (new Settings()).group(ItemGroup.BUILDING_BLOCKS).maxCount(1)));
		IRON_CRAFTING_TABLE = Register("iron_crafting_table", new BlockItem(BlockRegistry.IRON_CRAFTING_TABLE, (new Settings()).group(ItemGroup.BUILDING_BLOCKS).maxCount(1)));
		MITHRIL_CRAFTING_TABLE = Register("mithril_crafting_table", new BlockItem(BlockRegistry.MITHRIL_CRAFTING_TABLE, (new Settings()).group(ItemGroup.BUILDING_BLOCKS).maxCount(1)));
		ANCIENT_METAL_CRAFTING_TABLE = Register("ancient_metal_crafting_table", new BlockItem(BlockRegistry.ANCIENT_METAL_CRAFTING_TABLE, (new Settings()).group(ItemGroup.BUILDING_BLOCKS).maxCount(1)));
		ADAMANTIUM_CRAFTING_TABLE = Register("adamantium_crafting_table", new BlockItem(BlockRegistry.ADAMANTIUM_CRAFTING_TABLE, (new Settings()).group(ItemGroup.BUILDING_BLOCKS).maxCount(1)));
		OBSIDIAN_CRAFTING_TABLE = Register("obsidian_crafting_table", new BlockItem(BlockRegistry.OBSIDIAN_CRAFTING_TABLE, (new Settings()).group(ItemGroup.BUILDING_BLOCKS).maxCount(1)));
		BACON = Register("bacon", new Item((new Settings()).group(ItemGroup.FOOD).food((new FoodComponent.Builder()).hunger(2).saturationModifier(0.2F).meat().build())));
		COOKED_BACON = Register("cooked_bacon", new Item((new Settings()).group(ItemGroup.FOOD).food((new FoodComponent.Builder()).hunger(5).saturationModifier(0.4F).meat().build())));

		TURKEY_RAW = Register("turkey_raw", new Item((new Settings()).group(ItemGroup.FOOD).food(new FoodComponent.Builder().hunger(4).saturationModifier(2).meat().build())));
		TURKEY_RAW = Register("turkey_cooked", new Item((new Settings()).group(ItemGroup.FOOD).food(new FoodComponent.Builder().hunger(6).saturationModifier(4).meat().build())));

		TWIG = Register("twig", new ItemBurnable((new Settings()).group(ItemGroup.MISC).maxCount(32), 50));
		SIFTER = Register("sifter", new SifterItem(new Settings().group(ItemGroup.TOOLS).maxCount(1)));
		BRANCH = Register("branch", new ItemBurnable((new Settings()).group(ItemGroup.MISC).maxCount(32), 100));
		CHARRED_FOOD = Register("charred_food", new Item((new Settings()).group(ItemGroup.MISC).maxCount(16)));
		CLAY_OVEN = Register("clay_oven", new BlockItem(BlockRegistry.CLAY_OVEN, (new Settings()).group(ItemGroup.BUILDING_BLOCKS).maxCount(1)));
		HARDENED_CLAY_OVEN = Register("hardened_clay_oven", new BlockItem(BlockRegistry.HARDENED_CLAY_OVEN, (new Settings()).group(ItemGroup.BUILDING_BLOCKS).maxCount(1)));
		SANDSTONE_OVEN = Register("sandstone_oven", new BlockItem(BlockRegistry.SANDSTONE_OVEN, (new Settings()).group(ItemGroup.BUILDING_BLOCKS).maxCount(1)));
		COBBLESTONE_FURNACE = Register("cobblestone_furnace", new BlockItem(BlockRegistry.COBBLESTONE_FURNACE, (new Settings()).group(ItemGroup.BUILDING_BLOCKS).maxCount(1)));
		OBSIDIAN_FURNACE = Register("obsidian_furnace", new BlockItem(BlockRegistry.OBSIDIAN_FURNACE, (new Settings()).group(ItemGroup.BUILDING_BLOCKS).maxCount(1)));
		NETHERRACK_FURNACE = Register("netherrack_furnace", new BlockItem(BlockRegistry.NETHERRACK_FURNACE, (new Settings()).group(ItemGroup.BUILDING_BLOCKS).maxCount(1)));
		PEA_GRAVEL = Register("pea_gravel", new BlockItem(BlockRegistry.PEA_GRAVEL, (new Settings()).group(ItemGroup.BUILDING_BLOCKS)));
		MANURE = Register("manure", new ItemManure((new Settings()).group(ItemGroup.MISC).maxCount(16)));
		COBWEB_BLOCK = Register("cobweb_block", new BlockItem(BlockRegistry.COBWEB_BLOCK, new Settings().group(ItemGroup.BUILDING_BLOCKS)));
		SHINING_GRAVEL = Register("shining_gravel", new BlockItem(BlockRegistry.SHINING_GRAVEL, new Settings().group(ItemGroup.BUILDING_BLOCKS)));
		SHINING_PEA_GRAVEL = Register("shining_pea_gravel", new BlockItem(BlockRegistry.SHINING_PEA_GRAVEL, new Settings().group(ItemGroup.BUILDING_BLOCKS)));
		SILVER_ORE = Register("silver_ore", new BlockItem(BlockRegistry.SILVER_ORE, new Settings().group(ItemGroup.BUILDING_BLOCKS)));
		COPPER_NUGGET = Register("copper_nugget", new Item((new Settings()).group(ItemGroup.MISC)));
		SILVER_NUGGET = Register("silver_nugget", new Item((new Settings()).group(ItemGroup.MISC)));
		SILVER_INGOT = Register("silver_ingot", new Item((new Settings()).group(ItemGroup.MISC)));
		COPPER_PICKAXE = Register("copper_pickaxe", new MitePickaxeItem(SurvivalItemTier.COPPER_PICKAXE, 1, -2.0f, new Settings().group(ItemGroup.TOOLS)));
		COPPER_AXE = Register("copper_axe", new MiteAxeItem(SurvivalItemTier.COPPER_AXE, 5, -3.1f, new Settings().group(ItemGroup.TOOLS)));
		COPPER_SHOVEL = Register("copper_shovel", new ShovelItem(SurvivalItemTier.COPPER_SHOVEL, 1.0f, -3.0f, new Settings().group(ItemGroup.TOOLS)));
		COPPER_SWORD = Register("copper_sword", new SwordItem(SurvivalItemTier.COPPER_SWORD, 3, -2.4F, new Settings().group(ItemGroup.COMBAT)));
		COPPER_KNIFE = Register("copper_knife", new SwordItem(SurvivalItemTier.COPPER_SHORTSWORD, 2, -1.0F, new Settings().group(ItemGroup.COMBAT)));
		COPPER_HOE = Register("copper_hoe", new MiteHoeItem(SurvivalItemTier.COPPER_SHORTSWORD, 1, -1.0F, new Settings().group(ItemGroup.COMBAT)));
		COPPER_HATCHET = Register("copper_hatchet", new MiteAxeItem(SurvivalItemTier.COPPER_HATCHET, 3, -2.5f, new Settings().group(ItemGroup.TOOLS)));

		SILVER_PICKAXE = Register("silver_pickaxe", new MitePickaxeItem(SurvivalItemTier.COPPER_PICKAXE, 1, -2.0f, new Settings().group(ItemGroup.TOOLS)));
		SILVER_AXE = Register("silver_axe", new MiteAxeItem(SurvivalItemTier.COPPER_AXE, 5, -3.1f, new Settings().group(ItemGroup.TOOLS)));
		SILVER_SHOVEL = Register("silver_shovel", new ShovelItem(SurvivalItemTier.COPPER_SHOVEL, 1.0f, -3.0f, new Settings().group(ItemGroup.TOOLS)));
		SILVER_SWORD = Register("silver_sword", new SwordItem(SurvivalItemTier.COPPER_SWORD, 3, -2.4F, new Settings().group(ItemGroup.COMBAT)));
		SILVER_KNIFE = Register("silver_knife", new SwordItem(SurvivalItemTier.COPPER_SHORTSWORD, 2, -1.0F, new Settings().group(ItemGroup.COMBAT)));
		SILVER_HOE = Register("silver_hoe", new MiteHoeItem(SurvivalItemTier.COPPER_SHORTSWORD, 1, -1.0F, new Settings().group(ItemGroup.COMBAT)));
		SILVER_HATCHET = Register("silver_hatchet", new MiteAxeItem(SurvivalItemTier.COPPER_HATCHET, 3, -2.5f, new Settings().group(ItemGroup.TOOLS)));
		
		SALAD = Register("salad", new StewItem((new Settings()).maxCount(1).group(ItemGroup.FOOD).food(buildStew(1, 0.1f))));
		BANANA_SPLIT = Register("banana_split", new StewItem((new Settings()).maxCount(1).group(ItemGroup.FOOD).food(buildStew(7, 0.5f))));
		MILK_BOWL = Register("bowl_milk", new StewItem((new Settings()).maxCount(1).group(ItemGroup.FOOD).food(buildStew(1, 0))));
		WATER_BOWL = Register("bowl_water", new StewItem((new Settings()).maxCount(1).group(ItemGroup.FOOD).food(buildStew(0, 0))));
		CEREAL = Register("cereal", new StewItem((new Settings()).maxCount(1).group(ItemGroup.FOOD).food(buildStew(5, 0.5f))));
		CHICKEN_SOUP = Register("chicken_soup", new StewItem((new Settings()).maxCount(1).group(ItemGroup.FOOD).food(buildStew(8, 1))));
		CREAM_OF_MUSHROOM_SOUP = Register("cream_of_mushroom_soup", new StewItem((new Settings()).maxCount(1).group(ItemGroup.FOOD).food(buildStew(4, 0.3f))));
		CREAM_OF_VEGETABLE_SOUP = Register("cream_of_vegetable_soup", new StewItem((new Settings()).maxCount(1).group(ItemGroup.FOOD).food(buildStew(6, 0.5f))));
		ICE_CREAM = Register("ice_cream", new StewItem((new Settings()).maxCount(1).group(ItemGroup.FOOD).food(buildStew(3, 0.4f))));
		MASHED_POTATOES = Register("mashed_potatoes", new StewItem((new Settings()).maxCount(1).group(ItemGroup.FOOD).food(buildStew(5, 0.5f))));
		PORRIDGE = Register("porridge", new StewItem((new Settings()).maxCount(1).group(ItemGroup.FOOD).food(buildStew(5, 0.5f))));
		PUMPKIN_SOUP = Register("pumpkin_soup", new StewItem((new Settings()).maxCount(1).group(ItemGroup.FOOD).food(buildStew(2, 0.2f))));
		SORBET = Register("sorbet", new StewItem((new Settings()).maxCount(1).group(ItemGroup.FOOD).food(buildStew(5, 0.6f))));
		VEGETABLE_SOUP = Register("vegetable_soup", new StewItem((new Settings()).maxCount(1).group(ItemGroup.FOOD).food(buildStew(7, 0.7f))));
		BEEF_STEW = Register("beef_stew", new StewItem((new Settings()).maxCount(1).group(ItemGroup.FOOD).food(buildStew(11, 1f))));
		CHOCOLATE_ICE_CREAM = Register("chocolate_ice_cream", new StewItem((new Settings()).maxCount(1).group(ItemGroup.FOOD).food(buildStew(4, 0.5f))));

		COOKED_EGG = Register("cooked_egg", new Item((new Settings()).group(ItemGroup.FOOD).maxCount(16).food((new FoodComponent.Builder()).hunger(4).saturationModifier(0.3F).meat().build())));
		BANANA = Register("banana", new Item((new Settings()).group(ItemGroup.FOOD).food((new FoodComponent.Builder()).hunger(2).saturationModifier(0.2F).build())));
		BLUEBERRIES = Register("blueberries", new Item((new Settings()).group(ItemGroup.FOOD).food((new FoodComponent.Builder()).hunger(1).saturationModifier(0.1F).build())));
		BLUEBERRY_MUFFIN = Register("blueberry_muffin", new Item((new Settings()).group(ItemGroup.FOOD).food((new FoodComponent.Builder()).hunger(5).saturationModifier(0.2F).build())));
		BROWNIE = Register("brownie", new Item((new Settings()).group(ItemGroup.FOOD).food((new FoodComponent.Builder()).hunger(3).saturationModifier(0.2F).build())));
		CHEESE = Register("cheese", new Item((new Settings()).group(ItemGroup.FOOD).food((new FoodComponent.Builder()).hunger(3).saturationModifier(0.4F).build())));
		CHERRIES = Register("cherries", new Item((new Settings()).group(ItemGroup.FOOD).food((new FoodComponent.Builder()).hunger(2).saturationModifier(0.2F).build())));
		CHOCOLATE = Register("chocolate", new Item((new Settings()).group(ItemGroup.FOOD).maxCount(16).food((new FoodComponent.Builder()).hunger(3).saturationModifier(0.2F).build())));
		COOKIE_DOUGH = Register("cookie_dough", new Item((new Settings()).group(ItemGroup.FOOD).maxCount(16).food((new FoodComponent.Builder()).hunger(4).saturationModifier(0.4F).build())));
		DOUGH = Register("dough", new Item((new Settings()).group(ItemGroup.FOOD).food((new FoodComponent.Builder()).hunger(5).saturationModifier(0.4F).build())));
		FLOUR = Register("flour", new Item((new Settings()).group(ItemGroup.FOOD).maxCount(16).food((new FoodComponent.Builder()).hunger(0).saturationModifier(0.2F).build())));
		LEMON = Register("lemon", new Item((new Settings()).group(ItemGroup.FOOD).food((new FoodComponent.Builder()).hunger(2).saturationModifier(0.3F).build())));
		ONION = Register("onion", new Item((new Settings()).group(ItemGroup.FOOD).food((new FoodComponent.Builder()).hunger(4).saturationModifier(0.4F).build())));
		ORANGE = Register("orange", new Item((new Settings()).group(ItemGroup.FOOD).food((new FoodComponent.Builder()).hunger(3).saturationModifier(0.4F).build())));
		RAW_WORM = Register("worm_raw", new Item((new Settings()).group(ItemGroup.FOOD).maxCount(16).food((new FoodComponent.Builder()).hunger(1).saturationModifier(0.1F).meat().build())));
		COOKED_WORM = Register("worm_cooked", new Item((new Settings()).group(ItemGroup.FOOD).maxCount(16).food((new FoodComponent.Builder()).hunger(3).saturationModifier(0.3F).meat().build())));

		DOWN_FEATHER = Register("down_feather", new Item((new Settings()).group(ItemGroup.MATERIALS).maxCount(16)));
		DOWN_FEATHER_BLOCK = Register("down_feather_block", new BlockItem(BlockRegistry.DOWN_FEATHER_BLOCK, new Settings().group(ItemGroup.BUILDING_BLOCKS)));

		UNBAKED_CAKE = Register("unbaked_cake", new BlockItem(BlockRegistry.UNBAKED_CAKE, new Settings().group(ItemGroup.FOOD).maxCount(1)));

		THIN_OAK_LOG = Register("thin_oak_log", new BlockItem(BlockRegistry.THIN_OAK_LOG, new Settings().group(ItemGroup.BUILDING_BLOCKS)));
		THIN_BIRCH_LOG = Register("thin_birch_log", new BlockItem(BlockRegistry.THIN_BIRCH_LOG, new Settings().group(ItemGroup.BUILDING_BLOCKS)));
		THIN_SPRUCE_LOG = Register("thin_spruce_log", new BlockItem(BlockRegistry.THIN_SPRUCE_LOG, new Settings().group(ItemGroup.BUILDING_BLOCKS)));
		
		ROCK = Register("rock", new Item(new Settings().group(ItemGroup.MATERIALS)));

		RegisterAll(
				BlockRegistry.ANDESITE_SAND, BlockRegistry.ANDESITE_GRAVEL,
				BlockRegistry.DIORITE_SAND, BlockRegistry.DIORITE_GRAVEL,
				BlockRegistry.GRANITE_SAND, BlockRegistry.GRANITE_GRAVEL,
				BlockRegistry.RED_SANDSTONE_GRAVEL, BlockRegistry.SANDSTONE_GRAVEL,
				BlockRegistry.LIMESTONE_SAND
				);
		//for (Block block : BlockRegistry.REGISTRY) {
		//	Register(block, new BlockItem(block, new Settings().group(ItemGroup.BUILDING_BLOCKS)));
		//}
	}
}
