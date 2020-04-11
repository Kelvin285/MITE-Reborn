package kelvin.fiveminsurvival.init;

import kelvin.fiveminsurvival.items.CustomMilkBucketItem;
import kelvin.fiveminsurvival.items.ItemBurnable;
import kelvin.fiveminsurvival.items.MITEBoneMealItem;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;


public class ItemOverrides {
    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, "minecraft");

    public static final RegistryObject<Item> WHEAT_SEEDS = ITEMS.register("wheat_seeds", () -> new BlockNamedItem(Blocks.WHEAT, (new Item.Properties()).group(ItemGroup.MATERIALS).food(new Food.Builder().saturation(0.1F).build())));
    public static final RegistryObject<Item> PUMPKIN_SEEDS = ITEMS.register("pumpkin_seeds", () -> new BlockNamedItem(Blocks.PUMPKIN_STEM, (new Item.Properties()).group(ItemGroup.MATERIALS).food(new Food.Builder().hunger(2).saturation(0.1F).build())));
    public static final RegistryObject<Item> MELON_SEEDS = ITEMS.register("melon_seeds", () ->  new BlockNamedItem(Blocks.MELON_STEM, (new Item.Properties()).group(ItemGroup.MATERIALS).food(new Food.Builder().hunger(2).saturation(0.1F).build())));
    public static final RegistryObject<Item> MILK_BUCKET = ITEMS.register("milk_bucket", () ->  new CustomMilkBucketItem((new Item.Properties()).containerItem(Items.BUCKET).maxStackSize(1).group(ItemGroup.MISC).food(new Food.Builder().hunger(1).setAlwaysEdible().build())));
    public static final RegistryObject<Item> RED_MUSHROOM = ITEMS.register("red_mushroom", () ->  new BlockItem(Blocks.RED_MUSHROOM, (new Item.Properties()).group(ItemGroup.FOOD).food(new Food.Builder().hunger(1).saturation(0.1f).effect(new EffectInstance(Effects.POISON, 10), 1.0F).effect(new EffectInstance(Effects.NAUSEA, 40), 1.0F).build())));
    public static final RegistryObject<Item> BROWN_MUSHROOM = ITEMS.register("brown_mushroom", () ->  new BlockItem(Blocks.BROWN_MUSHROOM, (new Item.Properties()).group(ItemGroup.FOOD).food(new Food.Builder().hunger(1).saturation(0.1f).build())));
    public static final RegistryObject<Item> SUGAR = ITEMS.register("sugar", () ->  new Item((new Item.Properties()).group(ItemGroup.MATERIALS).food(new Food.Builder().saturation(0.1F).build())));
    public static final RegistryObject<Item> CHARCOAL = ITEMS.register("charcoal", () ->  new Item(new Item.Properties().group(ItemGroup.MISC)));
    public static final RegistryObject<Item> PAPER = ITEMS.register("paper", () ->  new ItemBurnable(new Item.Properties().group(ItemGroup.MISC), 25));
    public static final RegistryObject<Item> BONE_MEAL = ITEMS.register("bone_meal", () ->  new MITEBoneMealItem((new Item.Properties()).group(ItemGroup.MATERIALS)));
    public static final RegistryObject<Item>PHANTOM_MEMBRANE = ITEMS.register("phantom_membrane", () ->  new Item((new Item.Properties()).group(ItemGroup.BREWING).food(new Food.Builder().hunger(4).saturation(0.4f).build())));


}
