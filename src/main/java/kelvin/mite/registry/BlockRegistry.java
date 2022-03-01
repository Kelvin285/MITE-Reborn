package kelvin.mite.registry;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import kelvin.mite.blocks.*;
import kelvin.mite.main.Mite;
import kelvin.mite.screens.MITEFurnaceContainer;
import kelvin.mite.crafting.CraftingIngredient;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.*;
import net.minecraft.block.AbstractBlock.Settings;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;

public class BlockRegistry {
	
	public static String src = "resourcepacks/mite/";

	public static HashMap<Block, Block> grass_variants = new HashMap<Block, Block>();
	public static HashMap<Block, Block> farmland_variants = new HashMap<Block, Block>();

	public static Block SANDSTONE_GRAVEL, RED_SANDSTONE_GRAVEL, ANDESITE_GRAVEL, DIORITE_GRAVEL, GRANITE_GRAVEL;
	public static Block ANDESITE_SAND, DIORITE_SAND, GRANITE_SAND, LIMESTONE_SAND;
	
	public static Block FLAX; // = BLOCKS.Register("mite:flax", () -> new TallPlantBlock(Block.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().hardnessAndResistance(0.5f).sound(SoundType.PLANT)));
    public static Block FLINT_CRAFTING_TABLE; // = BLOCKS.Register("mite:flint_crafting_table", () -> new MITECraftingTableBlock((Block.Properties.create(Material.WOOD).hardnessAndResistance(0.2F).sound(SoundType.WOOD)), CraftingIngredient.FLINT_CRAFTING_TABLE));
    public static Block ADAMANTIUM_CRAFTING_TABLE; // = BLOCKS.Register("mite:adamantium_crafting_table", () -> new MITECraftingTableBlock((Block.Properties.create(Material.WOOD).hardnessAndResistance(0.2F).sound(SoundType.WOOD)), CraftingIngredient.ADAMANTIUM_CRAFTING_TABLE));
    public static Block COPPER_CRAFTING_TABLE; // = BLOCKS.Register("mite:copper_crafting_table", () -> new MITECraftingTableBlock((Block.Properties.create(Material.WOOD).hardnessAndResistance(0.2F).sound(SoundType.WOOD)), CraftingIngredient.COPPER_CRAFTING_TABLE));
    public static Block SILVER_CRAFTING_TABLE; // = BLOCKS.Register("mite:silver_crafting_table", () -> new MITECraftingTableBlock((Block.Properties.create(Material.WOOD).hardnessAndResistance(0.2F).sound(SoundType.WOOD)), CraftingIngredient.COPPER_CRAFTING_TABLE));
    public static Block GOLD_CRAFTING_TABLE; // = BLOCKS.Register("mite:gold_crafting_table", () -> new MITECraftingTableBlock((Block.Properties.create(Material.WOOD).hardnessAndResistance(0.2F).sound(SoundType.WOOD)), CraftingIngredient.COPPER_CRAFTING_TABLE));
    public static Block IRON_CRAFTING_TABLE; // = BLOCKS.Register("mite:iron_crafting_table", () -> new MITECraftingTableBlock((Block.Properties.create(Material.WOOD).hardnessAndResistance(0.2F).sound(SoundType.WOOD)), CraftingIngredient.IRON_CRAFTING_TABLE));

    public static Block MITHRIL_CRAFTING_TABLE; // = BLOCKS.Register("mite:mithril_crafting_table", () -> new MITECraftingTableBlock((Block.Properties.create(Material.WOOD).hardnessAndResistance(0.2F).sound(SoundType.WOOD)), CraftingIngredient.MITHRIL_CRAFTING_TABLE));
    public static Block ANCIENT_METAL_CRAFTING_TABLE; // = BLOCKS.Register("mite:ancient_metal_crafting_table", () -> new MITECraftingTableBlock((Block.Properties.create(Material.WOOD).hardnessAndResistance(0.2F).sound(SoundType.WOOD)), CraftingIngredient.MITHRIL_CRAFTING_TABLE));
    public static Block OBSIDIAN_CRAFTING_TABLE; // = BLOCKS.Register("mite:obsidian_crafting_table", () -> new MITECraftingTableBlock((Block.Properties.create(Material.WOOD).hardnessAndResistance(0.2F).sound(SoundType.WOOD)), CraftingIngredient.FLINT_CRAFTING_TABLE));

    public static Block CLAY_OVEN; // = BLOCKS.Register("mite:clay_oven", () -> new MITEFurnaceBlock((Block.Properties.create(Material.CLAY).hardnessAndResistance(0.2F).sound(SoundType.GROUND)), MITEFurnaceContainer.CLAY));
    public static Block HARDENED_CLAY_OVEN; // = BLOCKS.Register("mite:hardened_clay_oven", () -> new MITEFurnaceBlock((Block.Properties.create(Material.CLAY).sound(SoundType.STONE).hardnessAndResistance(0.2F).sound(SoundType.GROUND)), MITEFurnaceContainer.HARDENED_CLAY));
    public static Block SANDSTONE_OVEN; // = BLOCKS.Register("mite:sandstone_oven", () -> new MITEFurnaceBlock((Block.Properties.create(Material.CLAY).sound(SoundType.STONE).hardnessAndResistance(0.2F).sound(SoundType.GROUND)), MITEFurnaceContainer.SANDSTONE));
    public static Block COBBLESTONE_FURNACE; // = BLOCKS.Register("mite:cobblestone_furnace", () -> new MITEFurnaceBlock((Block.Properties.create(Material.CLAY).sound(SoundType.STONE).hardnessAndResistance(0.2F).sound(SoundType.GROUND)), MITEFurnaceContainer.STONE));
    public static Block OBSIDIAN_FURNACE; // = BLOCKS.Register("mite:obsidian_furnace", () -> new MITEFurnaceBlock((Block.Properties.create(Material.CLAY).sound(SoundType.STONE).hardnessAndResistance(0.2F).sound(SoundType.GROUND)), MITEFurnaceContainer.OBSIDIAN));
    public static Block NETHERRACK_FURNACE; // = BLOCKS.Register("mite:netherrack_furnace", () -> new MITEFurnaceBlock((Block.Properties.create(Material.CLAY).sound(SoundType.STONE).hardnessAndResistance(0.2F).sound(SoundType.GROUND)), MITEFurnaceContainer.NETHERRACK));
    public static Block PEA_GRAVEL; // = BLOCKS.Register("mite:pea_gravel", () -> new GravelBlock(Block.Properties.create(Material.SAND, MaterialColor.STONE).hardnessAndResistance(0.6F).sound(SoundType.GROUND)));
    public static Block CAMPFIRE_LOW; // = BLOCKS.Register("mite:campfire_low", () -> new CustomCampfireBlock(false, 1, Block.Properties.create(Material.WOOD, MaterialColor.OBSIDIAN).hardnessAndResistance(2.0F).setLightLevel((state) -> {return 15;} ).sound(SoundType.WOOD).tickRandomly().notSolid()));
    public static Block SHINING_PEA_GRAVEL; // = BLOCKS.Register("mite:shining_pea_gravel", () -> new GravelBlock(Block.Properties.create(Material.SAND, MaterialColor.STONE).hardnessAndResistance(0.6F).sound(SoundType.GROUND)));
    public static Block SHINING_GRAVEL; // = BLOCKS.Register("mite:shining_gravel", () -> new GravelBlock(Block.Properties.create(Material.SAND, MaterialColor.STONE).hardnessAndResistance(0.6F).sound(SoundType.GROUND)));

    public static Block SILVER_ORE; // = BLOCKS.Register("mite:silver_ore", () -> new Block(Block.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(3.0F, 3.0F)));

    public static Block COBWEB_BLOCK; // = BLOCKS.Register("mite:cobweb_block", () -> new Block(Block.Properties.create(Material.WOOL, MaterialColor.IRON).hardnessAndResistance(0.25f).sound(SoundType.CLOTH)));

    public static Block UNBAKED_CAKE; // = BLOCKS.Register("mite:unbaked_cake", () -> new MITECakeBlock(2, 0.2f, 5));
    public static Block RED_CAKE; // = BLOCKS.Register("mite:red_cake", () -> new MITECakeBlock(4, 0.3f, 20));
    public static Block GREEN_CAKE; // = BLOCKS.Register("mite:green_cake", () -> new MITECakeBlock(4, 0.3f, 20));
    public static Block BLUE_CAKE; // = BLOCKS.Register("mite:blue_cake", () -> new MITECakeBlock(4, 0.3f, 20));
    public static Block YELLOW_CAKE; // = BLOCKS.Register("mite:yellow_cake", () -> new MITECakeBlock(4, 0.3f, 20));
    public static Block ORANGE_CAKE; // = BLOCKS.Register("mite:orange_cake", () -> new MITECakeBlock(4, 0.3f, 20));
    public static Block BROWN_CAKE; // = BLOCKS.Register("mite:brown_cake", () -> new MITECakeBlock(4, 0.3f, 20));
    public static Block PURPLE_CAKE; // = BLOCKS.Register("mite:purple_cake", () -> new MITECakeBlock(4, 0.3f, 20));
    public static Block GRAY_CAKE; // = BLOCKS.Register("mite:gray_cake", () -> new MITECakeBlock(4, 0.3f, 20));
    public static Block LIGHT_GRAY_CAKE; // = BLOCKS.Register("mite:light_gray_cake", () -> new MITECakeBlock(4, 0.3f, 20));
    public static Block LIGHT_BLUE_CAKE; // = BLOCKS.Register("mite:light_blue_cake", () -> new MITECakeBlock(4, 0.3f, 20));
    public static Block MAGENTA_CAKE; // = BLOCKS.Register("mite:magenta_cake", () -> new MITECakeBlock(4, 0.3f, 20));
    public static Block LIME_CAKE; // = BLOCKS.Register("mite:lime_cake", () -> new MITECakeBlock(4, 0.3f, 20));
    public static Block PINK_CAKE; // = BLOCKS.Register("mite:pink_cake", () -> new MITECakeBlock(4, 0.3f, 20));
    public static Block BLACK_CAKE; // = BLOCKS.Register("mite:black_cake", () -> new MITECakeBlock(4, 0.3f, 20));
    public static Block CYAN_CAKE; // = BLOCKS.Register("mite:cyan_cake", () -> new MITECakeBlock(4, 0.3f, 20));
    
    public static Block DOWN_FEATHER_BLOCK; // = BLOCKS.Register("mite:down_feather_block", () -> new HayBlock(Block.Properties.create(Material.CARPET).hardnessAndResistance(0.1f)));
    public static Block CHICKEN_NEST; // = BLOCKS.Register("mite:chicken_nest", () -> new Block(Block.Properties.create(Material.PLANTS, MaterialColor.YELLOW).hardnessAndResistance(0.1f).doesNotBlockMovement()));
	
    public static Block THIN_OAK_LOG;
    public static Block THIN_BIRCH_LOG;
    public static Block THIN_SPRUCE_LOG;

    public static Block ROCK;

    public static Block BLUEBERRY_BUSH;

    public static Block FARMLAND_DIRT, FARMLAND_SANDSTONE, FARMLAND_RED_SANDSTONE,
	FARMLAND_ANDESITE, FARMLAND_GRANITE, FARMLAND_DIORITE, FARMLAND_LIMESTONE;

	public static ArrayList<Block> gravel_variants = new ArrayList<Block>();
	public static ArrayList<Block> sand_variants = new ArrayList<Block>();

	/*
	 * public BasicBakedModel(List<BakedQuad> quads, Map<Direction, List<BakedQuad>> faceQuads, boolean usesAo,
			boolean isSideLit, boolean hasDepth, Sprite sprite, ModelTransformation transformation,
			ModelOverrideList itemPropertyOverrides) {
	 */
	
	/*
	 * public BuiltinBakedModel(ModelTransformation transformation, ModelOverrideList itemPropertyOverrides, Sprite sprite,
			boolean sideLit) {
		this.transformation = transformation;
		this.itemPropertyOverrides = itemPropertyOverrides;
		this.sprite = sprite;
		this.sideLit = sideLit;
	}
	 */
	public static ArrayList<Block> REGISTRY = new ArrayList<Block>();
	
	public static boolean CanSwapWithGrass(Block block) {
		return grass_variants.containsKey(block);
	}

	public static boolean CanSwapWithFarmland(Block block) {
		return farmland_variants.containsKey(block);
	}

	public static Block TrySwapWithGrass(Block block) {
		return grass_variants.getOrDefault(block, block);
	}

	public static Block TrySwapWithFarmland(Block block) {
		return farmland_variants.getOrDefault(block, block);
	}
	
	public static Block TrySwapFromGrass(Block block) {
		for (Block b : grass_variants.keySet()) {
			if (grass_variants.get(b) == block) {
				return b;
			}
		}
		return block;
	}
	
	private static Block Register(String name, Block block) {
		AddLang(name.replace("mite:", ""));
		
		REGISTRY.add(block);
		return (Block) Registry.register(Registry.BLOCK, new Identifier(name), block);
	}
	
	private static Block Register(Identifier id, Block block, String name) {
		AddLang(name);
		
		REGISTRY.add(block);
		return (Block) Registry.register(Registry.BLOCK, id, block);
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
		
		Initialization.en_us_lang += "  \"block.mite."+block_name+"\": \""+capitalized_name+"\",\n";
		
	}
	
	private static void CreateGrassBlockJsons(String block_name, String particle, String bottom, String top, String side, String overlay) {
		
		String block_model_json = "{   \"parent\": \"block/block\",\r\n"
		+ "    \"textures\": {\r\n"
		+ "        \"particle\": \""+particle+"\",\r\n"
		+ "        \"bottom\": \""+bottom+"\",\r\n"
		+ "        \"top\": \""+top+"\",\r\n"
		+ "        \"side\": \""+side+"\",\r\n"
		+ "        \"overlay\": \""+overlay+"\"\r\n"
		+ "    },\r\n"
		+ "    \"elements\": [\r\n"
		+ "        {   \"from\": [ 0, 0, 0 ],\r\n"
		+ "            \"to\": [ 16, 16, 16 ],\r\n"
		+ "            \"faces\": {\r\n"
		+ "                \"down\":  { \"uv\": [ 0, 0, 16, 16 ], \"texture\": \"#bottom\", \"cullface\": \"down\" },\r\n"
		+ "                \"up\":    { \"uv\": [ 0, 0, 16, 16 ], \"texture\": \"#top\",    \"cullface\": \"up\", \"tintindex\": 0 },\r\n"
		+ "                \"north\": { \"uv\": [ 0, 0, 16, 16 ], \"texture\": \"#side\",   \"cullface\": \"north\" },\r\n"
		+ "                \"south\": { \"uv\": [ 0, 0, 16, 16 ], \"texture\": \"#side\",   \"cullface\": \"south\" },\r\n"
		+ "                \"west\":  { \"uv\": [ 0, 0, 16, 16 ], \"texture\": \"#side\",   \"cullface\": \"west\" },\r\n"
		+ "                \"east\":  { \"uv\": [ 0, 0, 16, 16 ], \"texture\": \"#side\",   \"cullface\": \"east\" }\r\n"
		+ "            }\r\n"
		+ "        },\r\n"
		+ "        {   \"from\": [ 0, 0, 0 ],\r\n"
		+ "            \"to\": [ 16, 16, 16 ],\r\n"
		+ "            \"faces\": {\r\n"
		+ "                \"north\": { \"uv\": [ 0, 0, 16, 16 ], \"texture\": \"#overlay\", \"tintindex\": 0, \"cullface\": \"north\" },\r\n"
		+ "                \"south\": { \"uv\": [ 0, 0, 16, 16 ], \"texture\": \"#overlay\", \"tintindex\": 0, \"cullface\": \"south\" },\r\n"
		+ "                \"west\":  { \"uv\": [ 0, 0, 16, 16 ], \"texture\": \"#overlay\", \"tintindex\": 0, \"cullface\": \"west\" },\r\n"
		+ "                \"east\":  { \"uv\": [ 0, 0, 16, 16 ], \"texture\": \"#overlay\", \"tintindex\": 0, \"cullface\": \"east\" }\r\n"
		+ "            }\r\n"
		+ "        }\r\n"
		+ "    ]\r\n"
		+ "}\r\n"
		+ "";
		
		String item_model_json = 
				"{\r\n"
				+ "  \"parent\": \"mite:block/"+block_name+"\"\r\n"
				+ "}";
		
		String blockstate_json = "{\r\n"
				+ "  \"variants\": {\r\n"
				+ "    \"snowy=false\": [\r\n"
				+ "      {\r\n"
				+ "        \"model\": \"mite:block/"+block_name+"\"\r\n"
				+ "      },\r\n"
				+ "      {\r\n"
				+ "        \"model\": \"mite:block/"+block_name+"\",\r\n"
				+ "        \"y\": 90\r\n"
				+ "      },\r\n"
				+ "      {\r\n"
				+ "        \"model\": \"mite:block/"+block_name+"\",\r\n"
				+ "        \"y\": 180\r\n"
				+ "      },\r\n"
				+ "      {\r\n"
				+ "        \"model\": \"mite:block/"+block_name+"\",\r\n"
				+ "        \"y\": 270\r\n"
				+ "      }\r\n"
				+ "    ],\r\n"
				+ "    \"snowy=true\": {\r\n"
				+ "      \"model\": \"mite:block/"+block_name+"_with_snow\"\r\n"
				+ "    }\r\n"
				+ "  }\r\n"
				+ "}";


		String snow = bottom+"_snow";
		if (bottom.equals("minecraft:block/dirt")) {
			snow = "minecraft:block/grass_block_snow";
		}
		else if (bottom.equals("minecraft:block/sand")) {
			snow = "mite:block/sand_snow";
		} else if (bottom.equals("minecraft:block/gravel")) {
			snow = "mite:block/gravel_snow";
		} else if (bottom.equals("minecraft:block/red_sand")) {
			snow = "mite:block/red_sand_snow";
		}

		String block_snow_json = "{   \"parent\": \"block/block\",\r\n"
				+ "    \"textures\": {\r\n"
				+ "        \"particle\": \""+particle+"\",\r\n"
				+ "        \"bottom\": \""+bottom+"\",\r\n"
				+ "        \"top\": \""+"block/snow"+"\",\r\n"
				+ "        \"side\": \""+snow+"\",\r\n"
				+ "        \"overlay\": \""+"mite:block/snow_overlay"+"\"\r\n"
				+ "    },\r\n"
				+ "    \"elements\": [\r\n"
				+ "        {   \"from\": [ 0, 0, 0 ],\r\n"
				+ "            \"to\": [ 16, 16, 16 ],\r\n"
				+ "            \"faces\": {\r\n"
				+ "                \"down\":  { \"uv\": [ 0, 0, 16, 16 ], \"texture\": \"#bottom\", \"cullface\": \"down\" },\r\n"
				+ "                \"up\":    { \"uv\": [ 0, 0, 16, 16 ], \"texture\": \"#top\",    \"cullface\": \"up\", \"tintindex\": 0 },\r\n"
				+ "                \"north\": { \"uv\": [ 0, 0, 16, 16 ], \"texture\": \"#side\",   \"cullface\": \"north\" },\r\n"
				+ "                \"south\": { \"uv\": [ 0, 0, 16, 16 ], \"texture\": \"#side\",   \"cullface\": \"south\" },\r\n"
				+ "                \"west\":  { \"uv\": [ 0, 0, 16, 16 ], \"texture\": \"#side\",   \"cullface\": \"west\" },\r\n"
				+ "                \"east\":  { \"uv\": [ 0, 0, 16, 16 ], \"texture\": \"#side\",   \"cullface\": \"east\" }\r\n"
				+ "            }\r\n"
				+ "        },\r\n"
				+ "        {   \"from\": [ 0, 0, 0 ],\r\n"
				+ "            \"to\": [ 16, 16, 16 ],\r\n"
				+ "            \"faces\": {\r\n"
				+ "                \"north\": { \"uv\": [ 0, 0, 16, 16 ], \"texture\": \"#overlay\", \"tintindex\": 0, \"cullface\": \"north\" },\r\n"
				+ "                \"south\": { \"uv\": [ 0, 0, 16, 16 ], \"texture\": \"#overlay\", \"tintindex\": 0, \"cullface\": \"south\" },\r\n"
				+ "                \"west\":  { \"uv\": [ 0, 0, 16, 16 ], \"texture\": \"#overlay\", \"tintindex\": 0, \"cullface\": \"west\" },\r\n"
				+ "                \"east\":  { \"uv\": [ 0, 0, 16, 16 ], \"texture\": \"#overlay\", \"tintindex\": 0, \"cullface\": \"east\" }\r\n"
				+ "            }\r\n"
				+ "        }\r\n"
				+ "    ]\r\n"
				+ "}\r\n"
				+ "";
		
		try {
			File json_bm = new File(src+"assets/mite/models/block/"+block_name+".json");
			json_bm.createNewFile();
			
			File json_sm = new File(src+"assets/mite/models/block/"+block_name+"_with_snow.json");
			json_sm.createNewFile();
			
			File json_im = new File(src+"assets/mite/models/item/"+block_name+".json");
			json_im.createNewFile();
			
			
			File json_state = new File(src+"assets/mite/blockstates/"+block_name+".json");
			json_state.createNewFile();
			
			FileWriter block_model = new FileWriter(src+"assets/mite/models/block/"+block_name+".json");
			FileWriter item_model = new FileWriter(src+"assets/mite/models/item/"+block_name+".json");
			FileWriter block_snow_model = new FileWriter(src+"assets/mite/models/block/"+block_name+"_with_snow.json");
			FileWriter blockstate = new FileWriter(src+"assets/mite/blockstates/"+block_name+".json");

			block_model.write(block_model_json);
			block_model.close();
			
			item_model.write(item_model_json);
			item_model.close();
			
			block_snow_model.write(block_snow_json);
			block_snow_model.close();
			
			blockstate.write(blockstate_json);
			blockstate.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean always(BlockState state, BlockView world, BlockPos pos) {
		return true;
	}
	
	public static void RegisterAllBlocks() {
		Color grass_color = new Color((int)(87 * 1.5), (int)(132 * 1.5), (int)(35 * 1.5));
		
		SANDSTONE_GRAVEL = Register(new Identifier("mite:sandstone_gravel"), new GravelBlock(
				Settings.of(Blocks.GRAVEL.getDefaultState().getMaterial()).ticksRandomly().strength(Blocks.GRAVEL.getDefaultState().getHardness(null, null)).sounds(Blocks.GRAVEL.getDefaultState().getSoundGroup())),
				"sandstone_gravel");
		RED_SANDSTONE_GRAVEL = Register(new Identifier("mite:red_sandstone_gravel"), new GravelBlock(
				Settings.of(Blocks.GRAVEL.getDefaultState().getMaterial()).ticksRandomly().strength(Blocks.GRAVEL.getDefaultState().getHardness(null, null)).sounds(Blocks.GRAVEL.getDefaultState().getSoundGroup())),
				"red_sandstone_gravel");
		ANDESITE_GRAVEL = Register(new Identifier("mite:andesite_gravel"), new GravelBlock(
				Settings.of(Blocks.GRAVEL.getDefaultState().getMaterial()).ticksRandomly().strength(Blocks.GRAVEL.getDefaultState().getHardness(null, null)).sounds(Blocks.GRAVEL.getDefaultState().getSoundGroup())),
				"andesite_gravel");
		GRANITE_GRAVEL = Register(new Identifier("mite:granite_gravel"), new GravelBlock(
				Settings.of(Blocks.GRAVEL.getDefaultState().getMaterial()).ticksRandomly().strength(Blocks.GRAVEL.getDefaultState().getHardness(null, null)).sounds(Blocks.GRAVEL.getDefaultState().getSoundGroup())),
				"granite_gravel");
		DIORITE_GRAVEL = Register(new Identifier("mite:diorite_gravel"), new GravelBlock(
				Settings.of(Blocks.GRAVEL.getDefaultState().getMaterial()).ticksRandomly().strength(Blocks.GRAVEL.getDefaultState().getHardness(null, null)).sounds(Blocks.GRAVEL.getDefaultState().getSoundGroup())),
				"diorite_gravel");
		ANDESITE_SAND = Register(new Identifier("mite:andesite_sand"), new FallingBlock(
				Settings.of(Blocks.SAND.getDefaultState().getMaterial()).ticksRandomly().strength(Blocks.SAND.getDefaultState().getHardness(null, null)).sounds(Blocks.SAND.getDefaultState().getSoundGroup())),
				"andesite_sand");
		GRANITE_SAND = Register(new Identifier("mite:granite_sand"), new FallingBlock(
				Settings.of(Blocks.SAND.getDefaultState().getMaterial()).ticksRandomly().strength(Blocks.SAND.getDefaultState().getHardness(null, null)).sounds(Blocks.SAND.getDefaultState().getSoundGroup())),
				"granite_sand");
		DIORITE_SAND = Register(new Identifier("mite:diorite_sand"), new FallingBlock(
				Settings.of(Blocks.SAND.getDefaultState().getMaterial()).ticksRandomly().strength(Blocks.SAND.getDefaultState().getHardness(null, null)).sounds(Blocks.SAND.getDefaultState().getSoundGroup())),
				"diorite_sand");
		LIMESTONE_SAND = Register(new Identifier("mite:limestone_sand"), new FallingBlock(
				Settings.of(Blocks.SAND.getDefaultState().getMaterial()).ticksRandomly().strength(Blocks.SAND.getDefaultState().getHardness(null, null)).sounds(Blocks.SAND.getDefaultState().getSoundGroup())),
				"limestone_sand");

		//        FARMLAND = register("farmland", new FarmlandBlock(Settings.of(Material.SOIL).ticksRandomly().strength(0.6F).sounds(BlockSoundGroup.GRAVEL).blockVision(Blocks::always).suffocates(Blocks::always)));
		FARMLAND_DIRT = Register(new Identifier("mite:farmland_dirt"), new MiteFarmlandBlock(
				Settings.of(Blocks.FARMLAND.getDefaultState().getMaterial()).ticksRandomly().strength(0.6F).sounds(BlockSoundGroup.GRAVEL).blockVision(BlockRegistry::always).suffocates(BlockRegistry::always), Blocks.DIRT),
				"dirt_farmland");
		FARMLAND_LIMESTONE = Register(new Identifier("mite:farmland_limestone"), new MiteFarmlandBlock(
						Settings.of(Blocks.FARMLAND.getDefaultState().getMaterial()).ticksRandomly().strength(0.6F).sounds(BlockSoundGroup.GRAVEL).blockVision(BlockRegistry::always).suffocates(BlockRegistry::always), LIMESTONE_SAND),
				"limestone_farmland");
		FARMLAND_ANDESITE = Register(new Identifier("mite:farmland_andesite"), new MiteFarmlandBlock(
						Settings.of(Blocks.FARMLAND.getDefaultState().getMaterial()).ticksRandomly().strength(0.6F).sounds(BlockSoundGroup.GRAVEL).blockVision(BlockRegistry::always).suffocates(BlockRegistry::always), ANDESITE_SAND),
				"andesite_farmland");
		FARMLAND_GRANITE = Register(new Identifier("mite:farmland_granite"), new MiteFarmlandBlock(
						Settings.of(Blocks.FARMLAND.getDefaultState().getMaterial()).ticksRandomly().strength(0.6F).sounds(BlockSoundGroup.GRAVEL).blockVision(BlockRegistry::always).suffocates(BlockRegistry::always), GRANITE_SAND),
				"granite_farmland");
		FARMLAND_DIORITE = Register(new Identifier("mite:farmland_diorite"), new MiteFarmlandBlock(
						Settings.of(Blocks.FARMLAND.getDefaultState().getMaterial()).ticksRandomly().strength(0.6F).sounds(BlockSoundGroup.GRAVEL).blockVision(BlockRegistry::always).suffocates(BlockRegistry::always), DIORITE_SAND),
				"diorite_farmland");
		FARMLAND_SANDSTONE = Register(new Identifier("mite:farmland_sandstone"), new MiteFarmlandBlock(
						Settings.of(Blocks.FARMLAND.getDefaultState().getMaterial()).ticksRandomly().strength(0.6F).sounds(BlockSoundGroup.GRAVEL).blockVision(BlockRegistry::always).suffocates(BlockRegistry::always), Blocks.SAND),
				"sandstone_farmland");
		FARMLAND_RED_SANDSTONE = Register(new Identifier("mite:farmland_red_sandstone"), new MiteFarmlandBlock(
						Settings.of(Blocks.FARMLAND.getDefaultState().getMaterial()).ticksRandomly().strength(0.6F).sounds(BlockSoundGroup.GRAVEL).blockVision(BlockRegistry::always).suffocates(BlockRegistry::always), Blocks.RED_SAND),
				"red_sandstone_farmland");
		
		 FLAX = Register("mite:flax", new MitePlantBlock(Block.Settings.of(Material.PLANT).noCollision().strength(0.5f).sounds(Blocks.GRASS.getDefaultState().getSoundGroup())));
	     FLINT_CRAFTING_TABLE = Register("mite:flint_crafting_table", new MITECraftingTableBlock((Block.Settings.of(Material.WOOD).strength(0.2F).sounds(BlockSoundGroup.WOOD)), CraftingIngredient.FLINT_CRAFTING_TABLE));
	     ADAMANTIUM_CRAFTING_TABLE = Register("mite:adamantium_crafting_table", new MITECraftingTableBlock((Block.Settings.of(Material.WOOD).strength(0.2F).sounds(BlockSoundGroup.WOOD)), CraftingIngredient.ADAMANTIUM_CRAFTING_TABLE));
	     COPPER_CRAFTING_TABLE = Register("mite:copper_crafting_table", new MITECraftingTableBlock((Block.Settings.of(Material.WOOD).strength(0.2F).sounds(BlockSoundGroup.WOOD)), CraftingIngredient.COPPER_CRAFTING_TABLE));
	     SILVER_CRAFTING_TABLE = Register("mite:silver_crafting_table", new MITECraftingTableBlock((Block.Settings.of(Material.WOOD).strength(0.2F).sounds(BlockSoundGroup.WOOD)), CraftingIngredient.COPPER_CRAFTING_TABLE));
	     GOLD_CRAFTING_TABLE = Register("mite:gold_crafting_table", new MITECraftingTableBlock((Block.Settings.of(Material.WOOD).strength(0.2F).sounds(BlockSoundGroup.WOOD)), CraftingIngredient.COPPER_CRAFTING_TABLE));
	     IRON_CRAFTING_TABLE = Register("mite:iron_crafting_table", new MITECraftingTableBlock((Block.Settings.of(Material.WOOD).strength(0.2F).sounds(BlockSoundGroup.WOOD)), CraftingIngredient.IRON_CRAFTING_TABLE));

	     MITHRIL_CRAFTING_TABLE = Register("mite:mithril_crafting_table", new MITECraftingTableBlock((Block.Settings.of(Material.WOOD).strength(0.2F).sounds(BlockSoundGroup.WOOD)), CraftingIngredient.MITHRIL_CRAFTING_TABLE));
	     ANCIENT_METAL_CRAFTING_TABLE = Register("mite:ancient_metal_crafting_table", new MITECraftingTableBlock((Block.Settings.of(Material.WOOD).strength(0.2F).sounds(BlockSoundGroup.WOOD)), CraftingIngredient.MITHRIL_CRAFTING_TABLE));
	     OBSIDIAN_CRAFTING_TABLE = Register("mite:obsidian_crafting_table", new MITECraftingTableBlock((Block.Settings.of(Material.WOOD).strength(0.2F).sounds(BlockSoundGroup.WOOD)), CraftingIngredient.FLINT_CRAFTING_TABLE));

	     CLAY_OVEN = Register("mite:clay_oven", new MITEFurnaceBlock((Block.Settings.of(Material.SOIL).strength(0.2F).sounds(BlockSoundGroup.GRAVEL)), MITEFurnaceContainer.CLAY));
	     HARDENED_CLAY_OVEN = Register("mite:hardened_clay_oven", new MITEFurnaceBlock((Block.Settings.of(Material.SOIL).sounds(BlockSoundGroup.STONE).strength(0.2F).sounds(BlockSoundGroup.GRAVEL)), MITEFurnaceContainer.HARDENED_CLAY));
	     SANDSTONE_OVEN = Register("mite:sandstone_oven", new MITEFurnaceBlock((Block.Settings.of(Material.SOIL).sounds(BlockSoundGroup.STONE).strength(0.2F).sounds(BlockSoundGroup.GRAVEL)), MITEFurnaceContainer.SANDSTONE));
	     COBBLESTONE_FURNACE = Register("mite:cobblestone_furnace", new MITEFurnaceBlock((Block.Settings.of(Material.SOIL).sounds(BlockSoundGroup.STONE).strength(0.2F).sounds(BlockSoundGroup.GRAVEL)), MITEFurnaceContainer.STONE));
	     OBSIDIAN_FURNACE = Register("mite:obsidian_furnace", new MITEFurnaceBlock((Block.Settings.of(Material.SOIL).sounds(BlockSoundGroup.STONE).strength(0.2F).sounds(BlockSoundGroup.GRAVEL)), MITEFurnaceContainer.OBSIDIAN));
	     NETHERRACK_FURNACE = Register("mite:netherrack_furnace", new MITEFurnaceBlock((Block.Settings.of(Material.SOIL).sounds(BlockSoundGroup.STONE).strength(0.2F).sounds(BlockSoundGroup.GRAVEL)), MITEFurnaceContainer.NETHERRACK));
	     PEA_GRAVEL = Register("mite:pea_gravel", new GravelBlock(Block.Settings.of(Material.SOIL, DyeColor.GRAY).strength(0.6F).sounds(BlockSoundGroup.GRAVEL)));
	     CAMPFIRE_LOW = Register("mite:campfire_low", new CampfireBlock(false, 1, Block.Settings.of(Material.WOOD, DyeColor.BLACK).strength(2.0F).luminance((state) -> {return 15;} ).sounds(BlockSoundGroup.WOOD).ticksRandomly().noCollision()));
	     SHINING_PEA_GRAVEL = Register("mite:shining_pea_gravel", new GravelBlock(Block.Settings.of(Material.SOIL, DyeColor.GRAY).strength(0.6F).sounds(BlockSoundGroup.GRAVEL)));
	     SHINING_GRAVEL = Register("mite:shining_gravel", new GravelBlock(Block.Settings.of(Material.SOIL, DyeColor.GRAY).strength(0.6F).sounds(BlockSoundGroup.GRAVEL)));
	     
	     SILVER_ORE = Register("mite:silver_ore", new Block(Block.Settings.of(Material.STONE, DyeColor.GRAY).strength(3.0F, 3.0F)));

	     COBWEB_BLOCK = Register("mite:cobweb_block", new Block(Block.Settings.of(Material.WOOL, DyeColor.WHITE).strength(0.25f).sounds(BlockSoundGroup.WOOL)));

	     UNBAKED_CAKE = Register("mite:unbaked_cake", new MiteCakeBlock(2, 0.2f, 5));
	     RED_CAKE = Register("mite:red_cake", new MiteCakeBlock(4, 0.3f, 20));
	     GREEN_CAKE = Register("mite:green_cake", new MiteCakeBlock(4, 0.3f, 20));
	     BLUE_CAKE = Register("mite:blue_cake", new MiteCakeBlock(4, 0.3f, 20));
	     YELLOW_CAKE = Register("mite:yellow_cake", new MiteCakeBlock(4, 0.3f, 20));
	     ORANGE_CAKE = Register("mite:orange_cake", new MiteCakeBlock(4, 0.3f, 20));
	     BROWN_CAKE = Register("mite:brown_cake", new MiteCakeBlock(4, 0.3f, 20));
	     PURPLE_CAKE = Register("mite:purple_cake", new MiteCakeBlock(4, 0.3f, 20));
	     GRAY_CAKE = Register("mite:gray_cake", new MiteCakeBlock(4, 0.3f, 20));
	     LIGHT_GRAY_CAKE = Register("mite:light_gray_cake", new MiteCakeBlock(4, 0.3f, 20));
	     LIGHT_BLUE_CAKE = Register("mite:light_blue_cake", new MiteCakeBlock(4, 0.3f, 20));
	     MAGENTA_CAKE = Register("mite:magenta_cake", new MiteCakeBlock(4, 0.3f, 20));
	     LIME_CAKE = Register("mite:lime_cake", new MiteCakeBlock(4, 0.3f, 20));
	     PINK_CAKE = Register("mite:pink_cake", new MiteCakeBlock(4, 0.3f, 20));
	     BLACK_CAKE = Register("mite:black_cake", new MiteCakeBlock(4, 0.3f, 20));
	     CYAN_CAKE = Register("mite:cyan_cake", new MiteCakeBlock(4, 0.3f, 20));
	    
	     DOWN_FEATHER_BLOCK = Register("mite:down_feather_block", new HayBlock(Block.Settings.of(Material.CARPET).strength(0.1f)));
	     CHICKEN_NEST = Register("mite:chicken_nest", new Block(Block.Settings.of(Material.PLANT, DyeColor.YELLOW).strength(0.1f).noCollision()));
	     
	     THIN_OAK_LOG = Register("mite:thin_oak_log", new FenceBlock(Block.Settings.of(Material.WOOD).strength(0.25f)));
	     THIN_BIRCH_LOG = Register("mite:thin_birch_log", new FenceBlock(Block.Settings.of(Material.WOOD).strength(0.25f)));
	     THIN_SPRUCE_LOG = Register("mite:thin_spruce_log", new FenceBlock(Block.Settings.of(Material.WOOD).strength(0.25f)));

	     ROCK = Register("mite:rock", new MiteRockBlock(AbstractBlock.Settings.copy(Blocks.STONE_BUTTON).breakInstantly()));

	     BLUEBERRY_BUSH = Register("mite:blueberry_bush", new SweetBerryBushBlock(AbstractBlock.Settings.copy(Blocks.SWEET_BERRY_BUSH)));

		Block[] soil_blocks = new Block[] {Blocks.DIRT, Blocks.ROOTED_DIRT, Blocks.COARSE_DIRT, Blocks.GRAVEL, Blocks.SAND, Blocks.CLAY, Blocks.RED_SAND,
				SANDSTONE_GRAVEL, RED_SANDSTONE_GRAVEL, ANDESITE_GRAVEL, DIORITE_GRAVEL, GRANITE_GRAVEL,
				ANDESITE_SAND, DIORITE_SAND, GRANITE_SAND, LIMESTONE_SAND};

		
		for (int i = 0; i < soil_blocks.length; i++) {
			String raw_name = soil_blocks[i].getName().toString().split("'")[1];
			
			String block_name = raw_name.split("\\.")[2];
			
			String modid = raw_name.split("\\.")[1];
			
			String grass_name = "mite:grassy_" + block_name;
			CreateGrassBlockJsons("grassy_"+block_name, modid+":block/"+block_name, modid+":block/"+block_name, "block/grass_block_top", modid+":block/"+block_name, "block/grass_block_side_overlay");
			Block grass = Register(new Identifier(grass_name), new MiteGrassBlock(
					Settings.of(soil_blocks[i].getDefaultState().getMaterial()).ticksRandomly().strength(soil_blocks[i].getDefaultState().getHardness(null, null)).dropsLike(soil_blocks[i]).sounds(soil_blocks[i].getDefaultState().getSoundGroup()), soil_blocks[i]),
					"grassy_"+block_name);

			if (Mite.client) {
				ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> ColorProviderRegistry.BLOCK.get(Blocks.GRASS_BLOCK).getColor(state, view, pos, tintIndex), grass);
			}
			Item grass_item = ItemRegistry.Register(grass, new BlockItem(grass, new net.minecraft.item.Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
			if (Mite.client) {
				ColorProviderRegistry.ITEM.register((stack, tintIndex) -> grass_color.getRGB(), grass_item);

				BlockRenderLayerMap.INSTANCE.putBlock(grass, RenderLayer.getCutout());
			}

			grass_variants.put(soil_blocks[i], grass);
		}



		farmland_variants.put(Blocks.DIRT, FARMLAND_DIRT);
		farmland_variants.put(grass_variants.get(Blocks.DIRT), FARMLAND_DIRT);

		/*
		farmland_variants.put(Blocks.SAND, FARMLAND_SANDSTONE);
		farmland_variants.put(grass_variants.get(Blocks.SAND), FARMLAND_SANDSTONE);

		farmland_variants.put(Blocks.RED_SAND, FARMLAND_RED_SANDSTONE);
		farmland_variants.put(grass_variants.get(Blocks.RED_SAND), FARMLAND_RED_SANDSTONE);

		farmland_variants.put(BlockRegistry.ANDESITE_SAND, FARMLAND_ANDESITE);
		farmland_variants.put(grass_variants.get(BlockRegistry.ANDESITE_SAND), FARMLAND_ANDESITE);

		farmland_variants.put(BlockRegistry.DIORITE_SAND, FARMLAND_DIORITE);
		farmland_variants.put(grass_variants.get(BlockRegistry.DIORITE_SAND), FARMLAND_DIORITE);

		farmland_variants.put(BlockRegistry.GRANITE_SAND, FARMLAND_GRANITE);
		farmland_variants.put(grass_variants.get(BlockRegistry.GRANITE_SAND), FARMLAND_GRANITE);

		farmland_variants.put(BlockRegistry.LIMESTONE_SAND, FARMLAND_LIMESTONE);
		farmland_variants.put(grass_variants.get(BlockRegistry.LIMESTONE_SAND), FARMLAND_LIMESTONE);
		*/

		gravel_variants.add(Blocks.GRAVEL);
		gravel_variants.add(ANDESITE_GRAVEL);
		gravel_variants.add(GRANITE_GRAVEL);
		gravel_variants.add(DIORITE_GRAVEL);
		gravel_variants.add(RED_SANDSTONE_GRAVEL);
		gravel_variants.add(SANDSTONE_GRAVEL);
		
		sand_variants.add(Blocks.SAND);
		sand_variants.add(LIMESTONE_SAND);
		sand_variants.add(ANDESITE_SAND);
		sand_variants.add(GRANITE_SAND);
		sand_variants.add(DIORITE_SAND);
		sand_variants.add(Blocks.RED_SAND);

		if (Mite.client) {
			BlockRenderLayerMap.INSTANCE.putBlock(CHICKEN_NEST, RenderLayer.getCutout());
			BlockRenderLayerMap.INSTANCE.putBlock(CAMPFIRE_LOW, RenderLayer.getCutout());
			BlockRenderLayerMap.INSTANCE.putBlock(FLAX, RenderLayer.getCutout());
			BlockRenderLayerMap.INSTANCE.putBlock(BLUEBERRY_BUSH, RenderLayer.getCutout());

			ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> ColorProviderRegistry.BLOCK.get(Blocks.GRASS).getColor(state, view, pos, tintIndex), FLAX);
		}
	}
	
	public static void StartRegisteringBlocks() {
		File file = new File(src+"assets/mite/models/");
		file.mkdirs();
		file = new File(src+"assets/mite/models/block");
		file.mkdirs();
		file = new File(src+"assets/mite/models/item");
		file.mkdirs();
		file = new File(src+"assets/mite/blockstates/");
		file.mkdirs();
		
		BufferedImage image = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
		try {
			ImageIO.write(image, "png", new File(src+"pack.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String mcmeta = "{\r\n"
				+ "  \"pack\": {\r\n"
				+ "    \"pack_format\": 6,\r\n"
				+ "    \"description\":\"Auto-generated MITE:Reborn resource pack\"\r\n"
				+ "  }\r\n"
				+ "}\r\n"
				+ "";
		FileWriter writer;
		try {
			writer = new FileWriter(src+"pack.mcmeta");
			writer.write(mcmeta);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		RegisterAllBlocks();
	}
}
