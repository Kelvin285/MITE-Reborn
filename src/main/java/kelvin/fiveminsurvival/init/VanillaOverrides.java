package kelvin.fiveminsurvival.init;

import kelvin.fiveminsurvival.blocks.CustomAirBlock;
import kelvin.fiveminsurvival.blocks.CustomSnowBlock;
import kelvin.fiveminsurvival.blocks.MITECakeBlock;
import kelvin.fiveminsurvival.items.BurnableBlockItem;
import kelvin.fiveminsurvival.items.CustomBowlItem;
import kelvin.fiveminsurvival.items.CustomEggItem;
import kelvin.fiveminsurvival.items.CustomMilkBucketItem;
import kelvin.fiveminsurvival.items.ItemBurnable;
import kelvin.fiveminsurvival.items.MITEBoneMealItem;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.DeadBushBlock;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.block.SeaGrassBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.SugarCaneBlock;
import net.minecraft.block.TallGrassBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.AirItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class VanillaOverrides {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "minecraft");
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "minecraft");
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, "minecraft");
    
    public static final RegistryObject<Item> WHEAT_SEEDS = ITEMS.register("wheat_seeds", () -> new BlockNamedItem(Blocks.WHEAT, (new Item.Properties()).group(ItemGroup.MATERIALS).food(new Food.Builder().saturation(0.1F).build())));
    public static final RegistryObject<Item> PUMPKIN_SEEDS = ITEMS.register("pumpkin_seeds", () -> new BlockNamedItem(Blocks.PUMPKIN_STEM, (new Item.Properties()).group(ItemGroup.MATERIALS).food(new Food.Builder().hunger(2).saturation(0.1F).build())));
    public static final RegistryObject<Item> MELON_SEEDS = ITEMS.register("melon_seeds", () ->  new BlockNamedItem(Blocks.MELON_STEM, (new Item.Properties()).group(ItemGroup.MATERIALS).food(new Food.Builder().hunger(2).saturation(0.1F).build())));
    public static final RegistryObject<Item> MILK_BUCKET = ITEMS.register("milk_bucket", () ->  new CustomMilkBucketItem((new Item.Properties()).containerItem(Items.BUCKET).maxStackSize(1).group(ItemGroup.MISC).food(new Food.Builder().hunger(1).setAlwaysEdible().build())));
    public static final RegistryObject<Item> RED_MUSHROOM = ITEMS.register("red_mushroom", () ->  new BlockItem(Blocks.RED_MUSHROOM, (new Item.Properties()).group(ItemGroup.FOOD).food(new Food.Builder().hunger(1).saturation(0.1f).effect(() -> new EffectInstance(Effects.POISON, 10), 1.0F).effect(() -> new EffectInstance(Effects.NAUSEA, 40), 1.0F).build())));
    public static final RegistryObject<Item> BROWN_MUSHROOM = ITEMS.register("brown_mushroom", () ->  new BlockItem(Blocks.BROWN_MUSHROOM, (new Item.Properties()).group(ItemGroup.FOOD).food(new Food.Builder().hunger(1).saturation(0.1f).build())));
    public static final RegistryObject<Item> SUGAR = ITEMS.register("sugar", () ->  new Item((new Item.Properties()).group(ItemGroup.MATERIALS).food(new Food.Builder().saturation(0.1F).build())));
    public static final RegistryObject<Item> CHARCOAL = ITEMS.register("charcoal", () ->  new Item(new Item.Properties().group(ItemGroup.MISC)));
    public static final RegistryObject<Item> PAPER = ITEMS.register("paper", () ->  new ItemBurnable(new Item.Properties().group(ItemGroup.MISC), 25));
    public static final RegistryObject<Item> BONE_MEAL = ITEMS.register("bone_meal", () ->  new MITEBoneMealItem((new Item.Properties()).group(ItemGroup.MATERIALS)));
    public static final RegistryObject<Item> PHANTOM_MEMBRANE = ITEMS.register("phantom_membrane", () ->  new Item((new Item.Properties()).group(ItemGroup.BREWING).food(new Food.Builder().hunger(4).saturation(0.4f).build())));
    public static final RegistryObject<Block> CAKE = BLOCKS.register("cake", () -> new MITECakeBlock(4, 0.3f, 20));
    public static final RegistryObject<Item> EGG = ITEMS.register("egg", () ->  new CustomEggItem((new Item.Properties()).group(ItemGroup.MATERIALS).food(new Food.Builder().saturation(0.2F).hunger(2).build())));
    public static final RegistryObject<Item> BOWL = ITEMS.register("bowl", () ->  new CustomBowlItem((new Item.Properties()).group(ItemGroup.MATERIALS)));
    public static final RegistryObject<Block> GRASS = BLOCKS.register("grass", () -> new TallGrassBlock(AbstractBlock.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().hardnessAndResistance(0.08f).sound(SoundType.PLANT)));
    public static final RegistryObject<Block> TALL_GRASS = BLOCKS.register("tall_grass", () -> new DoublePlantBlock(AbstractBlock.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().hardnessAndResistance(0.08f).sound(SoundType.PLANT)));
    public static final RegistryObject<Block> DEAD_BUSH = BLOCKS.register("dead_bush", () -> new DeadBushBlock(AbstractBlock.Properties.create(Material.TALL_PLANTS, MaterialColor.WOOD).doesNotBlockMovement().hardnessAndResistance(0.08f).sound(SoundType.PLANT)));
    public static final RegistryObject<Block> SUGARCANE = BLOCKS.register("sugarcane", () -> new SugarCaneBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.25f).sound(SoundType.PLANT)));
    public static final RegistryObject<Block> SEA_GRASS = BLOCKS.register("sea_grass", () -> new SeaGrassBlock(AbstractBlock.Properties.create(Material.SEA_GRASS).doesNotBlockMovement().hardnessAndResistance(0.08f).sound(SoundType.WET_GRASS)));
    
    //Burnable Saplings
    public static final RegistryObject<Item> OAK_SAPLING = ITEMS.register("oak_sapling", () -> new BurnableBlockItem(Blocks.OAK_SAPLING, new Item.Properties().group(ItemGroup.MISC), 110));
    public static final RegistryObject<Item> SPRUCE_SAPLING = ITEMS.register("spruce_sapling", () -> new BurnableBlockItem(Blocks.SPRUCE_SAPLING, new Item.Properties().group(ItemGroup.MISC), 110));
    public static final RegistryObject<Item> BIRCH_SAPLING = ITEMS.register("birch_sapling", () -> new BurnableBlockItem(Blocks.BIRCH_SAPLING, new Item.Properties().group(ItemGroup.MISC), 110));
    public static final RegistryObject<Item> JUNGLE_SAPLING = ITEMS.register("jungle_sapling", () -> new BurnableBlockItem(Blocks.JUNGLE_SAPLING, new Item.Properties().group(ItemGroup.MISC), 110));
    public static final RegistryObject<Item> ACACIA_SAPLING = ITEMS.register("acacia_sapling", () -> new BurnableBlockItem(Blocks.ACACIA_SAPLING, new Item.Properties().group(ItemGroup.MISC), 110));
    public static final RegistryObject<Item> DARK_OAK_SAPLING = ITEMS.register("dark_oak_sapling", () -> new BurnableBlockItem(Blocks.DARK_OAK_SAPLING, new Item.Properties().group(ItemGroup.MISC), 110));

    public static final RegistryObject<Block> SNOW = BLOCKS.register("snow", () -> new CustomSnowBlock(Block.Properties.create(Material.SNOW).tickRandomly().hardnessAndResistance(0.1F).sound(SoundType.SNOW)));
    
//    public static final RegistryObject<Structure<VillageConfig>> VILLAGE = FEATURES.register("village", () -> new CustomVillageFeature(VillageConfig::deserialize));
//    public static final RegistryObject<Structure<NoFeatureConfig>> PILLAGER_OUTPOST = FEATURES.register("pillager_outpost", () -> new CustomPillagerOutpostFeature(NoFeatureConfig::deserialize));
//    public static final RegistryObject<Structure<NoFeatureConfig>> JUNGLE_TEMPLE = FEATURES.register("jungle_temple", () -> new CustomJunglePyramidStructure(NoFeatureConfig::deserialize));
//    public static final RegistryObject<Structure<NoFeatureConfig>> DESERT_PYRAMID = FEATURES.register("desert_pyramid", () -> new CustomDesertPyramidStructure(NoFeatureConfig::deserialize));
//    public static final RegistryObject<Structure<NoFeatureConfig>> OCEAN_MONUMENT = FEATURES.register("ocean_monument", () -> new CustomOceanMonumentStructure(NoFeatureConfig::deserialize));
//    public static final RegistryObject<Structure<OceanRuinConfig>> OCEAN_RUIN = FEATURES.register("ocean_ruin", () -> new CustomOceanRuinStructure(OceanRuinConfig::deserialize));
//    public static final RegistryObject<Structure<ShipwreckConfig>> SHIPWRECK = FEATURES.register("shipwreck", () -> new CustomShipwreckStructure(ShipwreckConfig::deserialize));
//    public static final RegistryObject<Structure<NoFeatureConfig>> SWAMP_HUT = FEATURES.register("swamp_hut", () -> new CustomSwampHutStructure(NoFeatureConfig::deserialize));

    public static final Object[] DELETED_ITEMS = {
//    		DeleteItem("wooden_sword"), DeleteItem("diamond_sword"), DeleteItem("wooden_hoe"), DeleteItem("wooden_axe"), DeleteItem("wooden_pickaxe"), DeleteItem("diamond_axe"), DeleteItem("diamond_hoe"),
//    		DeleteItem("diamond_pickaxe"), DeleteItem("diamond_shovel"), DeleteItem("diamond_helmet"), DeleteItem("diamond_chestplate"), DeleteItem("diamond_leggings"), DeleteItem("diamond_boots"), DeleteItem("stone_pickaxe"),
//    		DeleteItem("stone_axe"), DeleteItem("stone_shovel"), DeleteItem("stone_hoe"), DeleteItem("stone_sword")
    };
    
    public static RegistryObject<Item> DeleteItem(String item) {
    	Item air = new AirItem(new CustomAirBlock(item, AbstractBlock.Properties.create(Material.AIR)), new Item.Properties());
    	return ITEMS.register(item, () -> air);
    }
}
