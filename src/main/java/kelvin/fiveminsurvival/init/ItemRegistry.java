package kelvin.fiveminsurvival.init;

import kelvin.fiveminsurvival.items.HatchetItem;
import kelvin.fiveminsurvival.items.ItemBurnable;
import kelvin.fiveminsurvival.items.ItemManure;
import kelvin.fiveminsurvival.items.ShortswordItem;
import kelvin.fiveminsurvival.items.SpearItem;
import kelvin.fiveminsurvival.items.SurvivalItemTier;
import kelvin.fiveminsurvival.main.FiveMinSurvival;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Food;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShieldItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SoupItem;
import net.minecraft.item.SwordItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemRegistry {
	
	public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, FiveMinSurvival.MODID);

	public static final RegistryObject<Item> SMOOTH_STONE = ITEMS.register("smooth_stone", () -> new Item(new Properties().group(ItemGroup.MATERIALS).maxStackSize(8)));
	public static final RegistryObject<Item> STRIPPED_BARK = ITEMS.register("stripped_bark", () -> new ItemBurnable(new Properties().group(ItemGroup.MATERIALS).maxStackSize(32), 20 * 4));
	public static final RegistryObject<Item> FLINT_SHARD = ITEMS.register("flint_shard", () -> new Item(new Properties().group(ItemGroup.MATERIALS).maxStackSize(64)));
	public static final RegistryObject<Item> FLINT_HATCHET = ITEMS.register("flint_hatchet", () -> new HatchetItem(SurvivalItemTier.FLINT_HATCHET, 2.0F, -3.0F, new Properties().group(ItemGroup.COMBAT).maxStackSize(1)));
	public static final RegistryObject<Item> FLINT_AXE = ITEMS.register("flint_axe", () -> new AxeItem(SurvivalItemTier.FLINT_AXE, 2.0F, -3.0F, new Properties().group(ItemGroup.COMBAT).maxStackSize(1)));
	public static final RegistryObject<Item> FLINT_SHOVEL = ITEMS.register("flint_shovel", () -> new ShovelItem(SurvivalItemTier.FLINT_SHOVEL, 1.5F, -3.0F, new Properties().group(ItemGroup.COMBAT).maxStackSize(1)));
	public static final RegistryObject<Item> FLINT_KNIFE = ITEMS.register("flint_knife", () -> new ShortswordItem(SurvivalItemTier.FLINT_SHORTSWORD, 2, -0.5F, new Properties().group(ItemGroup.COMBAT).maxStackSize(1)));
	public static final RegistryObject<Item> WOODEN_CUDGEL = ITEMS.register("wooden_cudgel", () -> new ShortswordItem(SurvivalItemTier.WOOD_SHORTSWORD, 3, -0.5F, new Properties().group(ItemGroup.COMBAT).maxStackSize(1)));
	public static final RegistryObject<Item> WOODEN_CLUB = ITEMS.register("wooden_club", () -> new SwordItem(SurvivalItemTier.WOOD_SWORD, 4, -2.5F, new Properties().group(ItemGroup.COMBAT).maxStackSize(1)));
	public static final RegistryObject<Item> SINEW = ITEMS.register("sinew", () -> new Item(new Properties().group(ItemGroup.MATERIALS).maxStackSize(16)));
	public static final RegistryObject<Item> SPEAR = ITEMS.register("spear", () -> new SpearItem(new Properties().group(ItemGroup.COMBAT).maxStackSize(1).maxDamage(10), 2.0D));
	public static final RegistryObject<Item> WOODEN_SHIELD = ITEMS.register("wooden_shield", () -> new ShieldItem(new Properties().group(ItemGroup.COMBAT).maxStackSize(1).maxDamage(15)));
	public static final RegistryObject<Item> FLAX_SEEDS = ITEMS.register("flax_seeds", () -> new Item((new Item.Properties()).group(ItemGroup.FOOD).maxStackSize(64).food(new Food.Builder().hunger(1).build())));
	public static final RegistryObject<Item> FLAX = ITEMS.register("flax", () -> new BlockItem(BlockRegistry.FLAX.get(), (new Item.Properties()).group(ItemGroup.MATERIALS).maxStackSize(8)));
	public static final RegistryObject<Item> FLINT_CRAFTING_TABLE = ITEMS.register("flint_crafting_table", () -> new BlockItem(BlockRegistry.FLINT_CRAFTING_TABLE.get(), (new Item.Properties()).group(ItemGroup.BUILDING_BLOCKS).maxStackSize(1)));
	public static final RegistryObject<Item> COPPER_CRAFTING_TABLE = ITEMS.register("copper_crafting_table", () -> new BlockItem(BlockRegistry.COPPER_CRAFTING_TABLE.get(), (new Item.Properties()).group(ItemGroup.BUILDING_BLOCKS).maxStackSize(1)));
	public static final RegistryObject<Item> SILVER_CRAFTING_TABLE = ITEMS.register("silver_crafting_table", () -> new BlockItem(BlockRegistry.SILVER_CRAFTING_TABLE.get(), (new Item.Properties()).group(ItemGroup.BUILDING_BLOCKS).maxStackSize(1)));
	public static final RegistryObject<Item> GOLD_CRAFTING_TABLE = ITEMS.register("gold_crafting_table", () -> new BlockItem(BlockRegistry.GOLD_CRAFTING_TABLE.get(), (new Item.Properties()).group(ItemGroup.BUILDING_BLOCKS).maxStackSize(1)));
	public static final RegistryObject<Item> IRON_CRAFTING_TABLE = ITEMS.register("iron_crafting_table", () -> new BlockItem(BlockRegistry.IRON_CRAFTING_TABLE.get(), (new Item.Properties()).group(ItemGroup.BUILDING_BLOCKS).maxStackSize(1)));
	public static final RegistryObject<Item> MITHRIL_CRAFTING_TABLE = ITEMS.register("mithril_crafting_table", () -> new BlockItem(BlockRegistry.MITHRIL_CRAFTING_TABLE.get(), (new Item.Properties()).group(ItemGroup.BUILDING_BLOCKS).maxStackSize(1)));
	public static final RegistryObject<Item> ANCIENT_METAL_CRAFTING_TABLE = ITEMS.register("ancient_metal_crafting_table", () -> new BlockItem(BlockRegistry.ANCIENT_METAL_CRAFTING_TABLE.get(), (new Item.Properties()).group(ItemGroup.BUILDING_BLOCKS).maxStackSize(1)));
	public static final RegistryObject<Item> ADAMANTIUM_CRAFTING_TABLE = ITEMS.register("adamantium_crafting_table", () -> new BlockItem(BlockRegistry.ADAMANTIUM_CRAFTING_TABLE.get(), (new Item.Properties()).group(ItemGroup.BUILDING_BLOCKS).maxStackSize(1)));
	public static final RegistryObject<Item> OBSIDIAN_CRAFTING_TABLE = ITEMS.register("obsidian_crafting_table", () -> new BlockItem(BlockRegistry.OBSIDIAN_CRAFTING_TABLE.get(), (new Item.Properties()).group(ItemGroup.BUILDING_BLOCKS).maxStackSize(1)));
	public static final RegistryObject<Item> BACON = ITEMS.register("bacon", () -> new Item((new Item.Properties()).group(ItemGroup.FOOD).maxStackSize(16).food((new Food.Builder()).hunger(2).saturation(0.2F).meat().build())));
	public static final RegistryObject<Item> COOKED_BACON = ITEMS.register("cooked_bacon", () -> new Item((new Item.Properties()).group(ItemGroup.FOOD).maxStackSize(16).food((new Food.Builder()).hunger(5).saturation(0.4F).meat().build())));
	public static final RegistryObject<Item> TWIG = ITEMS.register("twig", () -> new ItemBurnable((new Item.Properties()).group(ItemGroup.MISC).maxStackSize(32), 50));
	public static final RegistryObject<Item> CHARRED_FOOD = ITEMS.register("charred_food", () -> new Item((new Item.Properties()).group(ItemGroup.MISC).maxStackSize(16)));
	public static final RegistryObject<Item> CLAY_OVEN = ITEMS.register("clay_oven", () -> new BlockItem(BlockRegistry.CLAY_OVEN.get(), (new Item.Properties()).group(ItemGroup.BUILDING_BLOCKS).maxStackSize(1)));
	public static final RegistryObject<Item> HARDENED_CLAY_OVEN = ITEMS.register("hardened_clay_oven", () -> new BlockItem(BlockRegistry.HARDENED_CLAY_OVEN.get(), (new Item.Properties()).group(ItemGroup.BUILDING_BLOCKS).maxStackSize(1)));
	public static final RegistryObject<Item> SANDSTONE_OVEN = ITEMS.register("sandstone_oven", () -> new BlockItem(BlockRegistry.SANDSTONE_OVEN.get(), (new Item.Properties()).group(ItemGroup.BUILDING_BLOCKS).maxStackSize(1)));
	public static final RegistryObject<Item> COBBLESTONE_FURNACE = ITEMS.register("cobblestone_furnace", () -> new BlockItem(BlockRegistry.COBBLESTONE_FURNACE.get(), (new Item.Properties()).group(ItemGroup.BUILDING_BLOCKS).maxStackSize(1)));
	public static final RegistryObject<Item> OBSIDIAN_FURNACE = ITEMS.register("obsidian_furnace", () -> new BlockItem(BlockRegistry.OBSIDIAN_FURNACE.get(), (new Item.Properties()).group(ItemGroup.BUILDING_BLOCKS).maxStackSize(1)));
	public static final RegistryObject<Item> NETHERRACK_FURNACE = ITEMS.register("netherrack_furnace", () -> new BlockItem(BlockRegistry.NETHERRACK_FURNACE.get(), (new Item.Properties()).group(ItemGroup.BUILDING_BLOCKS).maxStackSize(1)));
	public static final RegistryObject<Item> PEA_GRAVEL = ITEMS.register("pea_gravel", () -> new BlockItem(BlockRegistry.PEA_GRAVEL.get(), (new Item.Properties()).group(ItemGroup.BUILDING_BLOCKS).maxStackSize(4)));
	public static final RegistryObject<Item> MANURE = ITEMS.register("manure", () -> new ItemManure((new Item.Properties()).group(ItemGroup.MISC).maxStackSize(16)));
	public static final RegistryObject<Item> COBWEB_BLOCK = ITEMS.register("cobweb_block", () -> new BlockItem(BlockRegistry.COBWEB_BLOCK.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS).maxStackSize(4)));
	public static final RegistryObject<Item> SHINING_GRAVEL = ITEMS.register("shining_gravel", () -> new BlockItem(BlockRegistry.SHINING_GRAVEL.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS).maxStackSize(4)));
	public static final RegistryObject<Item> SHINING_PEA_GRAVEL = ITEMS.register("shining_pea_gravel", () -> new BlockItem(BlockRegistry.SHINING_PEA_GRAVEL.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS).maxStackSize(4)));
	public static final RegistryObject<Item> COPPER_ORE = ITEMS.register("copper_ore", () -> new BlockItem(BlockRegistry.COPPER_ORE.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS).maxStackSize(4)));
	public static final RegistryObject<Item> SILVER_ORE = ITEMS.register("silver_ore", () -> new BlockItem(BlockRegistry.SILVER_ORE.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS).maxStackSize(4)));
	public static final RegistryObject<Item> COPPER_NUGGET = ITEMS.register("copper_nugget", () -> new Item((new Item.Properties()).group(ItemGroup.MISC).maxStackSize(32)));
	public static final RegistryObject<Item> SILVER_NUGGET = ITEMS.register("silver_nugget", () -> new Item((new Item.Properties()).group(ItemGroup.MISC).maxStackSize(32)));
	public static final RegistryObject<Item> COPPER_INGOT = ITEMS.register("copper_ingot", () -> new Item((new Item.Properties()).group(ItemGroup.MISC).maxStackSize(16)));
	public static final RegistryObject<Item> SILVER_INGOT = ITEMS.register("silver_ingot", () -> new Item((new Item.Properties()).group(ItemGroup.MISC).maxStackSize(16)));
	public static final RegistryObject<Item> COPPER_PICKAXE = ITEMS.register("copper_pickaxe", () -> new PickaxeItem(SurvivalItemTier.COPPER_PICKAXE, 1, -2.0f, new Item.Properties().group(ItemGroup.TOOLS)));
	public static final RegistryObject<Item> COPPER_AXE = ITEMS.register("copper_axe", () -> new AxeItem(SurvivalItemTier.COPPER_AXE, 5, -3.1f, new Item.Properties().group(ItemGroup.TOOLS)));
	public static final RegistryObject<Item> COPPER_SHOVEL = ITEMS.register("copper_shovel", () -> new ShovelItem(SurvivalItemTier.COPPER_SHOVEL, 1.0f, -3.0f, new Item.Properties().group(ItemGroup.TOOLS)));
	public static final RegistryObject<Item> COPPER_SWORD = ITEMS.register("copper_sword", () -> new SwordItem(SurvivalItemTier.COPPER_SWORD, 3, -2.4F, new Item.Properties().group(ItemGroup.COMBAT)));
	public static final RegistryObject<Item> COPPER_KNIFE = ITEMS.register("copper_knife", () -> new SwordItem(SurvivalItemTier.COPPER_SHORTSWORD, 2, -1.0F, new Item.Properties().group(ItemGroup.COMBAT)));
	public static final RegistryObject<Item> COPPER_HOE = ITEMS.register("copper_hoe", () -> new HoeItem(SurvivalItemTier.COPPER_SHORTSWORD, -1.0F, new Item.Properties().group(ItemGroup.COMBAT)));
	public static final RegistryObject<Item> COPPER_HATCHET = ITEMS.register("copper_hatchet", () -> new AxeItem(SurvivalItemTier.COPPER_HATCHET, 3, -2.5f, new Item.Properties().group(ItemGroup.TOOLS)));

	public static final RegistryObject<Item> SILVER_PICKAXE = ITEMS.register("silver_pickaxe", () -> new PickaxeItem(SurvivalItemTier.COPPER_PICKAXE, 1, -2.0f, new Item.Properties().group(ItemGroup.TOOLS)));
	public static final RegistryObject<Item> SILVER_AXE = ITEMS.register("silver_axe", () -> new AxeItem(SurvivalItemTier.COPPER_AXE, 5, -3.1f, new Item.Properties().group(ItemGroup.TOOLS)));
	public static final RegistryObject<Item> SILVER_SHOVEL = ITEMS.register("silver_shovel", () -> new ShovelItem(SurvivalItemTier.COPPER_SHOVEL, 1.0f, -3.0f, new Item.Properties().group(ItemGroup.TOOLS)));
	public static final RegistryObject<Item> SILVER_SWORD = ITEMS.register("silver_sword", () -> new SwordItem(SurvivalItemTier.COPPER_SWORD, 3, -2.4F, new Item.Properties().group(ItemGroup.COMBAT)));
	public static final RegistryObject<Item> SILVER_KNIFE = ITEMS.register("silver_knife", () -> new SwordItem(SurvivalItemTier.COPPER_SHORTSWORD, 2, -1.0F, new Item.Properties().group(ItemGroup.COMBAT)));
	public static final RegistryObject<Item> SILVER_HOE = ITEMS.register("silver_hoe", () -> new HoeItem(SurvivalItemTier.COPPER_SHORTSWORD, -1.0F, new Item.Properties().group(ItemGroup.COMBAT)));
	public static final RegistryObject<Item> SILVER_HATCHET = ITEMS.register("silver_hatchet", () -> new AxeItem(SurvivalItemTier.COPPER_HATCHET, 3, -2.5f, new Item.Properties().group(ItemGroup.TOOLS)));
	
	public static final RegistryObject<Item> SALAD = ITEMS.register("salad", () -> new SoupItem((new Item.Properties()).maxStackSize(1).group(ItemGroup.FOOD).food(buildStew(1, 0.1f))));
	public static final RegistryObject<Item> BANANA_SPLIT = ITEMS.register("banana_split", () -> new SoupItem((new Item.Properties()).maxStackSize(1).group(ItemGroup.FOOD).food(buildStew(7, 0.5f))));
	public static final RegistryObject<Item> MILK_BOWL = ITEMS.register("bowl_milk", () -> new SoupItem((new Item.Properties()).maxStackSize(1).group(ItemGroup.FOOD).food(buildStew(1, 0))));
	public static final RegistryObject<Item> WATER_BOWL = ITEMS.register("bowl_water", () -> new SoupItem((new Item.Properties()).maxStackSize(1).group(ItemGroup.FOOD).food(buildStew(0, 0))));
	public static final RegistryObject<Item> CEREAL = ITEMS.register("cereal", () -> new SoupItem((new Item.Properties()).maxStackSize(1).group(ItemGroup.FOOD).food(buildStew(5, 0.5f))));
	public static final RegistryObject<Item> CHICKEN_SOUP = ITEMS.register("chicken_soup", () -> new SoupItem((new Item.Properties()).maxStackSize(1).group(ItemGroup.FOOD).food(buildStew(8, 1))));
	public static final RegistryObject<Item> CREAM_OF_MUSHROOM_SOUP = ITEMS.register("cream_of_mushroom_soup", () -> new SoupItem((new Item.Properties()).maxStackSize(1).group(ItemGroup.FOOD).food(buildStew(4, 0.3f))));
	public static final RegistryObject<Item> CREAM_OF_VEGETABLE_SOUP = ITEMS.register("cream_of_vegetable_soup", () -> new SoupItem((new Item.Properties()).maxStackSize(1).group(ItemGroup.FOOD).food(buildStew(6, 0.5f))));
	public static final RegistryObject<Item> ICE_CREAM = ITEMS.register("ice_cream", () -> new SoupItem((new Item.Properties()).maxStackSize(1).group(ItemGroup.FOOD).food(buildStew(3, 0.4f))));
	public static final RegistryObject<Item> MASHED_POTATOES = ITEMS.register("mashed_potatoes", () -> new SoupItem((new Item.Properties()).maxStackSize(1).group(ItemGroup.FOOD).food(buildStew(5, 0.5f))));
	public static final RegistryObject<Item> PORRIDGE = ITEMS.register("porridge", () -> new SoupItem((new Item.Properties()).maxStackSize(1).group(ItemGroup.FOOD).food(buildStew(5, 0.5f))));
	public static final RegistryObject<Item> PUMPKIN_SOUP = ITEMS.register("pumpkin_soup", () -> new SoupItem((new Item.Properties()).maxStackSize(1).group(ItemGroup.FOOD).food(buildStew(2, 0.2f))));
	public static final RegistryObject<Item> SORBET = ITEMS.register("sorbet", () -> new SoupItem((new Item.Properties()).maxStackSize(1).group(ItemGroup.FOOD).food(buildStew(5, 0.6f))));
	public static final RegistryObject<Item> VEGETABLE_SOUP = ITEMS.register("vegetable_soup", () -> new SoupItem((new Item.Properties()).maxStackSize(1).group(ItemGroup.FOOD).food(buildStew(7, 0.7f))));
	public static final RegistryObject<Item> BEEF_STEW = ITEMS.register("beef_stew", () -> new SoupItem((new Item.Properties()).maxStackSize(1).group(ItemGroup.FOOD).food(buildStew(11, 1f))));
	public static final RegistryObject<Item> CHOCOLATE_ICE_CREAM = ITEMS.register("chocolate_ice_cream", () -> new SoupItem((new Item.Properties()).maxStackSize(1).group(ItemGroup.FOOD).food(buildStew(4, 0.5f))));

	
	public static final RegistryObject<Item> COOKED_EGG = ITEMS.register("cooked_egg", () -> new Item((new Item.Properties()).group(ItemGroup.FOOD).maxStackSize(16).food((new Food.Builder()).hunger(4).saturation(0.3F).meat().build())));
	public static final RegistryObject<Item> BANANA = ITEMS.register("banana", () -> new Item((new Item.Properties()).group(ItemGroup.FOOD).maxStackSize(16).food((new Food.Builder()).hunger(2).saturation(0.2F).build())));
	public static final RegistryObject<Item> BLUEBERRIES = ITEMS.register("blueberries", () -> new Item((new Item.Properties()).group(ItemGroup.FOOD).maxStackSize(16).food((new Food.Builder()).hunger(1).saturation(0.1F).build())));
	public static final RegistryObject<Item> BLUEBERRY_MUFFIN = ITEMS.register("blueberry_muffin", () -> new Item((new Item.Properties()).group(ItemGroup.FOOD).maxStackSize(16).food((new Food.Builder()).hunger(5).saturation(0.2F).build())));
	public static final RegistryObject<Item> BROWNIE = ITEMS.register("brownie", () -> new Item((new Item.Properties()).group(ItemGroup.FOOD).maxStackSize(16).food((new Food.Builder()).hunger(3).saturation(0.2F).build())));
	public static final RegistryObject<Item> CHEESE = ITEMS.register("cheese", () -> new Item((new Item.Properties()).group(ItemGroup.FOOD).maxStackSize(16).food((new Food.Builder()).hunger(3).saturation(0.4F).build())));
	public static final RegistryObject<Item> CHERRIES = ITEMS.register("cherries", () -> new Item((new Item.Properties()).group(ItemGroup.FOOD).maxStackSize(16).food((new Food.Builder()).hunger(2).saturation(0.2F).build())));
	public static final RegistryObject<Item> CHOCOLATE = ITEMS.register("chocolate", () -> new Item((new Item.Properties()).group(ItemGroup.FOOD).maxStackSize(16).food((new Food.Builder()).hunger(3).saturation(0.2F).build())));
	public static final RegistryObject<Item> COOKIE_DOUGH = ITEMS.register("cookie_dough", () -> new Item((new Item.Properties()).group(ItemGroup.FOOD).maxStackSize(16).food((new Food.Builder()).hunger(4).saturation(0.4F).build())));
	public static final RegistryObject<Item> DOUGH = ITEMS.register("dough", () -> new Item((new Item.Properties()).group(ItemGroup.FOOD).maxStackSize(16).food((new Food.Builder()).hunger(5).saturation(0.4F).build())));
	public static final RegistryObject<Item> FLOUR = ITEMS.register("flour", () -> new Item((new Item.Properties()).group(ItemGroup.FOOD).maxStackSize(16).food((new Food.Builder()).hunger(0).saturation(0.2F).build())));
	public static final RegistryObject<Item> LEMON = ITEMS.register("lemon", () -> new Item((new Item.Properties()).group(ItemGroup.FOOD).maxStackSize(16).food((new Food.Builder()).hunger(2).saturation(0.3F).build())));
	public static final RegistryObject<Item> ONION = ITEMS.register("onion", () -> new Item((new Item.Properties()).group(ItemGroup.FOOD).maxStackSize(16).food((new Food.Builder()).hunger(4).saturation(0.4F).build())));
	public static final RegistryObject<Item> ORANGE = ITEMS.register("orange", () -> new Item((new Item.Properties()).group(ItemGroup.FOOD).maxStackSize(16).food((new Food.Builder()).hunger(3).saturation(0.4F).build())));
	public static final RegistryObject<Item> RAW_WORM = ITEMS.register("worm_raw", () -> new Item((new Item.Properties()).group(ItemGroup.FOOD).maxStackSize(16).food((new Food.Builder()).hunger(1).saturation(0.1F).meat().build())));
	public static final RegistryObject<Item> COOKED_WORM = ITEMS.register("worm_cooked", () -> new Item((new Item.Properties()).group(ItemGroup.FOOD).maxStackSize(16).food((new Food.Builder()).hunger(3).saturation(0.3F).meat().build())));

//	public static Item COPPER_SHEARS, COPPER_WAR_HAMMER, COPPER_BATTLE_AXE, COPPER_MATTOCK;
//	public static Item SILVER_SHEARS, SILVER_WAR_HAMMER, SILVER_BATTLE_AXE, SILVER_MATTOCK;

	public static RegistryObject<Item> UNBAKED_CAKE = ITEMS.register("unbaked_cake", () -> new BlockItem(BlockRegistry.UNBAKED_CAKE.get(), new Item.Properties().group(ItemGroup.FOOD).maxStackSize(1)));

	private static Food buildStew(int hunger, float saturation) {
        return (new Food.Builder()).hunger(hunger).saturation(saturation).build();
     }
}

