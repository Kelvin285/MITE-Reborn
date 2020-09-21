package kelvin.fiveminsurvival.init;

import kelvin.fiveminsurvival.blocks.CustomCampfireBlock;
import kelvin.fiveminsurvival.blocks.MITECakeBlock;
import kelvin.fiveminsurvival.blocks.MITECraftingTableBlock;
import kelvin.fiveminsurvival.blocks.MITEFurnaceBlock;
import kelvin.fiveminsurvival.blocks.TallPlantBlock;
import kelvin.fiveminsurvival.main.FiveMinSurvival;
import kelvin.fiveminsurvival.main.crafting.CraftingIngredient;
import kelvin.fiveminsurvival.main.gui.MITEFurnaceContainer;
import net.minecraft.block.Block;
import net.minecraft.block.GravelBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockRegistry {

    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, FiveMinSurvival.MODID);


    public static final RegistryObject<Block> FLAX = BLOCKS.register("flax", () -> new TallPlantBlock(Block.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().hardnessAndResistance(0.5f).sound(SoundType.PLANT)));
    public static final RegistryObject<Block> FLINT_CRAFTING_TABLE = BLOCKS.register("flint_crafting_table", () -> new MITECraftingTableBlock((Block.Properties.create(Material.WOOD).hardnessAndResistance(0.2F).sound(SoundType.WOOD)), CraftingIngredient.FLINT_CRAFTING_TABLE));
    public static final RegistryObject<Block> ADAMANTIUM_CRAFTING_TABLE = BLOCKS.register("adamantium_crafting_table", () -> new MITECraftingTableBlock((Block.Properties.create(Material.WOOD).hardnessAndResistance(0.2F).sound(SoundType.WOOD)), CraftingIngredient.ADAMANTIUM_CRAFTING_TABLE));
    public static final RegistryObject<Block> COPPER_CRAFTING_TABLE = BLOCKS.register("copper_crafting_table", () -> new MITECraftingTableBlock((Block.Properties.create(Material.WOOD).hardnessAndResistance(0.2F).sound(SoundType.WOOD)), CraftingIngredient.COPPER_CRAFTING_TABLE));
    public static final RegistryObject<Block> SILVER_CRAFTING_TABLE = BLOCKS.register("silver_crafting_table", () -> new MITECraftingTableBlock((Block.Properties.create(Material.WOOD).hardnessAndResistance(0.2F).sound(SoundType.WOOD)), CraftingIngredient.COPPER_CRAFTING_TABLE));
    public static final RegistryObject<Block> GOLD_CRAFTING_TABLE = BLOCKS.register("gold_crafting_table", () -> new MITECraftingTableBlock((Block.Properties.create(Material.WOOD).hardnessAndResistance(0.2F).sound(SoundType.WOOD)), CraftingIngredient.COPPER_CRAFTING_TABLE));
    public static final RegistryObject<Block> IRON_CRAFTING_TABLE = BLOCKS.register("iron_crafting_table", () -> new MITECraftingTableBlock((Block.Properties.create(Material.WOOD).hardnessAndResistance(0.2F).sound(SoundType.WOOD)), CraftingIngredient.IRON_CRAFTING_TABLE));

    public static final RegistryObject<Block> MITHRIL_CRAFTING_TABLE = BLOCKS.register("mithril_crafting_table", () -> new MITECraftingTableBlock((Block.Properties.create(Material.WOOD).hardnessAndResistance(0.2F).sound(SoundType.WOOD)), CraftingIngredient.MITHRIL_CRAFTING_TABLE));
    public static final RegistryObject<Block> ANCIENT_METAL_CRAFTING_TABLE = BLOCKS.register("ancient_metal_crafting_table", () -> new MITECraftingTableBlock((Block.Properties.create(Material.WOOD).hardnessAndResistance(0.2F).sound(SoundType.WOOD)), CraftingIngredient.MITHRIL_CRAFTING_TABLE));
    public static final RegistryObject<Block> OBSIDIAN_CRAFTING_TABLE = BLOCKS.register("obsidian_crafting_table", () -> new MITECraftingTableBlock((Block.Properties.create(Material.WOOD).hardnessAndResistance(0.2F).sound(SoundType.WOOD)), CraftingIngredient.FLINT_CRAFTING_TABLE));

    public static final RegistryObject<Block> CLAY_OVEN = BLOCKS.register("clay_oven", () -> new MITEFurnaceBlock((Block.Properties.create(Material.CLAY).hardnessAndResistance(0.2F).sound(SoundType.GROUND)), MITEFurnaceContainer.CLAY));
    public static final RegistryObject<Block> HARDENED_CLAY_OVEN = BLOCKS.register("hardened_clay_oven", () -> new MITEFurnaceBlock((Block.Properties.create(Material.CLAY).sound(SoundType.STONE).hardnessAndResistance(0.2F).sound(SoundType.GROUND)), MITEFurnaceContainer.HARDENED_CLAY));
    public static final RegistryObject<Block> SANDSTONE_OVEN = BLOCKS.register("sandstone_oven", () -> new MITEFurnaceBlock((Block.Properties.create(Material.CLAY).sound(SoundType.STONE).hardnessAndResistance(0.2F).sound(SoundType.GROUND)), MITEFurnaceContainer.SANDSTONE));
    public static final RegistryObject<Block> COBBLESTONE_FURNACE = BLOCKS.register("cobblestone_furnace", () -> new MITEFurnaceBlock((Block.Properties.create(Material.CLAY).sound(SoundType.STONE).hardnessAndResistance(0.2F).sound(SoundType.GROUND)), MITEFurnaceContainer.STONE));
    public static final RegistryObject<Block> OBSIDIAN_FURNACE = BLOCKS.register("obsidian_furnace", () -> new MITEFurnaceBlock((Block.Properties.create(Material.CLAY).sound(SoundType.STONE).hardnessAndResistance(0.2F).sound(SoundType.GROUND)), MITEFurnaceContainer.OBSIDIAN));
    public static final RegistryObject<Block> NETHERRACK_FURNACE = BLOCKS.register("netherrack_furnace", () -> new MITEFurnaceBlock((Block.Properties.create(Material.CLAY).sound(SoundType.STONE).hardnessAndResistance(0.2F).sound(SoundType.GROUND)), MITEFurnaceContainer.NETHERRACK));
    public static final RegistryObject<Block> PEA_GRAVEL = BLOCKS.register("pea_gravel", () -> new GravelBlock(Block.Properties.create(Material.SAND, MaterialColor.STONE).hardnessAndResistance(0.6F).sound(SoundType.GROUND)));
    public static final RegistryObject<Block> CAMPFIRE_LOW = BLOCKS.register("campfire_low", () -> new CustomCampfireBlock(Block.Properties.create(Material.WOOD, MaterialColor.OBSIDIAN).hardnessAndResistance(2.0F).sound(SoundType.WOOD).lightValue(15).tickRandomly().notSolid()));
    public static final RegistryObject<Block> SHINING_PEA_GRAVEL = BLOCKS.register("shining_pea_gravel", () -> new GravelBlock(Block.Properties.create(Material.SAND, MaterialColor.STONE).hardnessAndResistance(0.6F).sound(SoundType.GROUND)));
    public static final RegistryObject<Block> SHINING_GRAVEL = BLOCKS.register("shining_gravel", () -> new GravelBlock(Block.Properties.create(Material.SAND, MaterialColor.STONE).hardnessAndResistance(0.6F).sound(SoundType.GROUND)));

    public static final RegistryObject<Block> COPPER_ORE = BLOCKS.register("copper_ore", () -> new Block(Block.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(3.0F, 3.0F)));
    public static final RegistryObject<Block> SILVER_ORE = BLOCKS.register("silver_ore", () -> new Block(Block.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(3.0F, 3.0F)));

    public static final RegistryObject<Block> COBWEB_BLOCK = BLOCKS.register("cobweb_block", () -> new Block(Block.Properties.create(Material.WOOL, MaterialColor.IRON).hardnessAndResistance(0.25f).sound(SoundType.CLOTH)));

    public static final RegistryObject<Block> UNBAKED_CAKE = BLOCKS.register("unbaked_cake", () -> new MITECakeBlock(2, 0.2f, 5));
    public static final RegistryObject<Block> RED_CAKE = BLOCKS.register("red_cake", () -> new MITECakeBlock(4, 0.3f, 20));
    public static final RegistryObject<Block> GREEN_CAKE = BLOCKS.register("green_cake", () -> new MITECakeBlock(4, 0.3f, 20));
    public static final RegistryObject<Block> BLUE_CAKE = BLOCKS.register("blue_cake", () -> new MITECakeBlock(4, 0.3f, 20));
    public static final RegistryObject<Block> YELLOW_CAKE = BLOCKS.register("yellow_cake", () -> new MITECakeBlock(4, 0.3f, 20));
    public static final RegistryObject<Block> ORANGE_CAKE = BLOCKS.register("orange_cake", () -> new MITECakeBlock(4, 0.3f, 20));
    public static final RegistryObject<Block> BROWN_CAKE = BLOCKS.register("brown_cake", () -> new MITECakeBlock(4, 0.3f, 20));
    public static final RegistryObject<Block> PURPLE_CAKE = BLOCKS.register("purple_cake", () -> new MITECakeBlock(4, 0.3f, 20));
    public static final RegistryObject<Block> GRAY_CAKE = BLOCKS.register("gray_cake", () -> new MITECakeBlock(4, 0.3f, 20));
    public static final RegistryObject<Block> LIGHT_GRAY_CAKE = BLOCKS.register("light_gray_cake", () -> new MITECakeBlock(4, 0.3f, 20));
    public static final RegistryObject<Block> LIGHT_BLUE_CAKE = BLOCKS.register("light_blue_cake", () -> new MITECakeBlock(4, 0.3f, 20));
    public static final RegistryObject<Block> MAGENTA_CAKE = BLOCKS.register("magenta_cake", () -> new MITECakeBlock(4, 0.3f, 20));
    public static final RegistryObject<Block> LIME_CAKE = BLOCKS.register("lime_cake", () -> new MITECakeBlock(4, 0.3f, 20));
    public static final RegistryObject<Block> PINK_CAKE = BLOCKS.register("pink_cake", () -> new MITECakeBlock(4, 0.3f, 20));
    public static final RegistryObject<Block> BLACK_CAKE = BLOCKS.register("black_cake", () -> new MITECakeBlock(4, 0.3f, 20));
    public static final RegistryObject<Block> CYAN_CAKE = BLOCKS.register("cyan_cake", () -> new MITECakeBlock(4, 0.3f, 20));
}

