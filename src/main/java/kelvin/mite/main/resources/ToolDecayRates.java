package kelvin.mite.main.resources;

import kelvin.mite.items.DaggerItem;
import kelvin.mite.items.KnifeItem;
import kelvin.mite.items.MiteBattleAxeItem;
import kelvin.mite.items.MiteMattockItem;
import kelvin.mite.items.MiteWarhammerItem;
import kelvin.mite.items.ScytheItem;
import kelvin.mite.items.SpearItem;
import kelvin.mite.registry.BlockRegistry;
import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CakeBlock;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.CarpetBlock;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.block.EnchantingTableBlock;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.GlassBlock;
import net.minecraft.block.InfestedBlock;
import net.minecraft.block.LanternBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.Material;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.SignBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SnowBlock;
import net.minecraft.block.StainedGlassPaneBlock;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.TridentItem;

public class ToolDecayRates {
	
	
	public static int GetDecayRateAgainstBlock(Item item, BlockState state) {
		if (item instanceof DaggerItem) {
			return GetDaggerDecayRateAgainstBlock(state);
		}
		if (item instanceof KnifeItem) {
			return GetKnifeDecayRateAgainstBlock(state);	
		}
		if (item instanceof ScytheItem) {
			return GetScytheDecayRateAgainstBlock(state);
		}
		if (item instanceof SpearItem || item instanceof TridentItem) {
			return GetSpearDecayRateAgainstBlock(state);
		}
		if (item instanceof MiteWarhammerItem) {
			return GetWarhammerDecayRateAgainstBlock(state);
		}
		if (item instanceof MiteMattockItem) 
		{
			return GetMattockDecayRateAgainstBlock(state);
		}
		if (item instanceof MiteBattleAxeItem) {
			return GetBattleAxeDecayRateAgainstBlock(state);
		}
		if (item instanceof SwordItem) {
			return GetSwordDecayRateAgainstBlock(state);
		}
		if (item instanceof ShovelItem) {
			return GetShovelDecayRateAgainstBlock(state);
		}
		return GetDefaultDecayRateAgainstBlock(state);
	}
	
	public static int GetDecayRateAgainstEnemy(Item item) {
		if (item instanceof DaggerItem) {
			return GetDaggerDecayRateAgainstEnemy();
		}
		if (item instanceof KnifeItem) {
			return GetKnifeDecayRateAgainstEnemy();	
		}
		if (item instanceof ScytheItem) {
			return GetScytheDecayRateAgainstEnemy();
		}
		if (item instanceof SpearItem || item instanceof TridentItem) {
			return GetSpearDecayRateAgainstEnemy();
		}
		if (item instanceof MiteWarhammerItem) {
			return GetWarhammerDecayRateAgainstEnemy();
		}
		if (item instanceof MiteMattockItem) 
		{
			return GetMattockDecayRateAgainstEnemy();
		}
		if (item instanceof MiteBattleAxeItem) {
			return GetBattleAxeDecayRateAgainstEnemy();
		}
		if (item instanceof SwordItem) {
			return GetSwordDecayRateAgainstEnemy();
		}
		if (item instanceof ShovelItem) {
			return GetShovelDecayRateAgainstEnemy();
		}
		return 100;
	}
	
	public static int GetDefaultDecayRateAgainstBlock(BlockState state) {
		if (state.getBlock().getHardness() == 0) {
			return 0;
		}
		if (state.getBlock() == Blocks.BLACK_WOOL ||
				state.getBlock() == Blocks.BLUE_WOOL ||
				state.getBlock() == Blocks.BROWN_WOOL ||
				state.getBlock() == Blocks.CYAN_WOOL ||
				state.getBlock() == Blocks.GREEN_WOOL ||
				state.getBlock() == Blocks.LIGHT_BLUE_WOOL ||
				state.getBlock() == Blocks.LIGHT_GRAY_WOOL ||
				state.getBlock() == Blocks.LIME_WOOL ||
				state.getBlock() == Blocks.MAGENTA_WOOL ||
				state.getBlock() == Blocks.ORANGE_WOOL ||
				state.getBlock() == Blocks.PINK_WOOL ||
				state.getBlock() == Blocks.PURPLE_WOOL ||
				state.getBlock() == Blocks.RED_WOOL ||
				state.getBlock() == Blocks.WHITE_WOOL ||
				state.getBlock() == Blocks.YELLOW_WOOL
				) {
			return 80;
		}
		if (state.getBlock() instanceof CarpetBlock) {
			return 10;
		}
		if (state.getBlock() == Blocks.HAY_BLOCK) { 
			return 50;
		}
		if (state.getBlock() == Blocks.COBWEB) { 
			return 10;
		}
		if (state.getBlock() instanceof BedBlock) {
			return 20;
		}
		if (state.getBlock() instanceof LeavesBlock) {
			return 20;
		}
		
		if (state.getBlock() instanceof LanternBlock) {
			return 80;
		}
		if (state.getBlock() instanceof CampfireBlock) {
			return 60;
		}
		if (state.getBlock() instanceof PlantBlock) {
			return 5;
		}
		if (state.getBlock() == Blocks.MYCELIUM) { 
			return 60;
		}
		if (state.getBlock() == Blocks.SOUL_SOIL) {
			return 60;
		}
		if (state.getBlock() instanceof SnowBlock) {
			return state.get(SnowBlock.LAYERS) * 10;
		}
		
		if (state.getBlock() instanceof FarmlandBlock) {
			return 60;
		}
		if (BlockRegistry.gravel_variants.contains(state.getBlock())) {
			return 60;
		}
		if (BlockRegistry.sand_variants.contains(state.getBlock())) {
			return 40;
		}
		if (BlockRegistry.grass_variants.containsKey(state.getBlock()) ) {
			return 50;
		}
		if (state.getBlock() == Blocks.BLACK_CONCRETE ||
				state.getBlock() == Blocks.BLUE_CONCRETE ||
				state.getBlock() == Blocks.BROWN_CONCRETE ||
				state.getBlock() == Blocks.CYAN_CONCRETE ||
				state.getBlock() == Blocks.GREEN_CONCRETE ||
				state.getBlock() == Blocks.LIGHT_BLUE_CONCRETE ||
				state.getBlock() == Blocks.LIGHT_GRAY_CONCRETE ||
				state.getBlock() == Blocks.LIME_CONCRETE ||
				state.getBlock() == Blocks.MAGENTA_CONCRETE ||
				state.getBlock() == Blocks.ORANGE_CONCRETE ||
				state.getBlock() == Blocks.PINK_CONCRETE ||
				state.getBlock() == Blocks.PURPLE_CONCRETE ||
				state.getBlock() == Blocks.RED_CONCRETE ||
				state.getBlock() == Blocks.WHITE_CONCRETE ||
				state.getBlock() == Blocks.YELLOW_CONCRETE
				) {
			return 75;
		}
		if (state.getBlock() == Blocks.VINE) {
			return 20;
		}
		if (state.getBlock() == Blocks.CAVE_VINES) { 
			return 20;
		}
		if (state.getBlock() == Blocks.CAVE_VINES_PLANT) {
			return 30;
		}
		if (state.getBlock() == Blocks.TWISTING_VINES) {
			return 20;
		}
		if (state.getBlock() == Blocks.TWISTING_VINES_PLANT) {
			return 30;
		}
		if (state.getBlock() == Blocks.WEEPING_VINES) {
			return 20;
		}
		if (state.getBlock() == Blocks.WEEPING_VINES_PLANT) {
			return 30;
		}
		if (state.getBlock() == Blocks.COCOA) { 
			return 20;
		}
		if (state.getBlock() == Blocks.LILY_PAD) { 
			return 5;
		}
		if (state.getBlock() == BlockRegistry.CLAY_OVEN) {
			return 25;
		}
		if (state.getBlock() == BlockRegistry.HARDENED_CLAY_OVEN) {
			return 50;
		}
		if (state.getBlock() == Blocks.TERRACOTTA ||
				state.getBlock() == Blocks.BLACK_GLAZED_TERRACOTTA ||
				state.getBlock() == Blocks.BLACK_TERRACOTTA ||
				state.getBlock() == Blocks.BLUE_GLAZED_TERRACOTTA ||
				state.getBlock() == Blocks.BLUE_TERRACOTTA ||
				state.getBlock() == Blocks.BROWN_GLAZED_TERRACOTTA ||
				state.getBlock() == Blocks.BROWN_TERRACOTTA ||
				state.getBlock() == Blocks.CYAN_GLAZED_TERRACOTTA ||
				state.getBlock() == Blocks.CYAN_TERRACOTTA ||
				state.getBlock() == Blocks.GRAY_GLAZED_TERRACOTTA ||
				state.getBlock() == Blocks.GREEN_GLAZED_TERRACOTTA ||
				state.getBlock() == Blocks.GREEN_TERRACOTTA ||
				state.getBlock() == Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA ||
				state.getBlock() == Blocks.LIGHT_BLUE_TERRACOTTA ||
				state.getBlock() == Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA ||
				state.getBlock() == Blocks.LIGHT_GRAY_TERRACOTTA ||
				state.getBlock() == Blocks.LIME_GLAZED_TERRACOTTA ||
				state.getBlock() == Blocks.LIME_TERRACOTTA ||
				state.getBlock() == Blocks.MAGENTA_GLAZED_TERRACOTTA ||
				state.getBlock() == Blocks.MAGENTA_TERRACOTTA ||
				state.getBlock() == Blocks.ORANGE_GLAZED_TERRACOTTA ||
				state.getBlock() == Blocks.ORANGE_TERRACOTTA ||
				state.getBlock() == Blocks.PINK_GLAZED_TERRACOTTA ||
				state.getBlock() == Blocks.PINK_TERRACOTTA ||
				state.getBlock() == Blocks.PURPLE_GLAZED_TERRACOTTA ||
				state.getBlock() == Blocks.PURPLE_TERRACOTTA ||
				state.getBlock() == Blocks.RED_GLAZED_TERRACOTTA ||
				state.getBlock() == Blocks.RED_TERRACOTTA ||
				state.getBlock() == Blocks.WHITE_GLAZED_TERRACOTTA ||
				state.getBlock() == Blocks.WHITE_TERRACOTTA ||
				state.getBlock() == Blocks.YELLOW_GLAZED_TERRACOTTA ||
				state.getBlock() == Blocks.YELLOW_TERRACOTTA
				) {
			return 180;
		}
		if (state.getBlock() == Blocks.DAYLIGHT_DETECTOR) {
			return 20;
		}
		if (state.getBlock() == Blocks.TRAPPED_CHEST) {
			return 20;
		}
		if (state.getBlock() == Blocks.BEACON) { 
			return 300;
		}
		if (state.getBlock() instanceof FenceGateBlock) {
			return 200;
		}
		if (state.getBlock() == Blocks.MELON) {
			return 60;
		}
		if (state.getBlock() == Blocks.MUSHROOM_STEM ||
				state.getBlock() == Blocks.RED_MUSHROOM_BLOCK ||
				state.getBlock() == Blocks.BROWN_MUSHROOM_BLOCK) {
			return 20;
		}
		if (state.getBlock() == Blocks.GLOWSTONE) { 
			return 30;
		}
		if (state.getBlock() instanceof CakeBlock) 
		{
			return 25;
		}
		if (state.getBlock() instanceof TrapdoorBlock) {
			return 80;
		}
		if (state.getBlock() instanceof InfestedBlock) {
			return 75;
		}
		if (state.getBlock() == Blocks.JACK_O_LANTERN) {
			return 100;
		}
		if (state.getBlock() == Blocks.CARVED_PUMPKIN) {
			return 40;
		}
		if (state.getBlock() == Blocks.PUMPKIN) {
			return 60;
		}
		if (state.getBlock() instanceof FenceBlock) {
			return 40;
		}
		if (state.getBlock() == Blocks.JUKEBOX) { 
			return 200;
		}
		if (state.getBlock() == Blocks.SUGAR_CANE) {
			return 8;
		}
		if (state.getBlock() == Blocks.CLAY) {
			return 80;
		}
		if (state.getBlock() == Blocks.CACTUS) { 
			return 40;
		}
		if (state.getBlock() == Blocks.BLUE_ICE) { 
			return 225;
		}
		if (state.getBlock() == Blocks.PACKED_ICE) {
			return 150;
		}
		if (state.getBlock() == Blocks.ICE) {
			return 100;
		}
		if (state.getBlock() instanceof PressurePlateBlock) {
			return 50;
		}
		if (state.getBlock() instanceof SignBlock) {
			return 10;
		}
		if (state.getBlock() == Blocks.BOOKSHELF) {
			return 150;
		}
		if (state.getBlock() instanceof StainedGlassPaneBlock) {
			return 10;
		}
		if (state.getBlock() instanceof GlassBlock) {
			return 200;
		}
		if (state.getBlock() instanceof ChestBlock) {
			return 20;
		}
		if (state.getBlock() instanceof CraftingTableBlock) {
			return 160;
		}
		if (state.getBlock() == Blocks.COBBLESTONE) {
			return 100;
		}
		
		if (state.getBlock() == Blocks.GOLD_ORE) {
			return 240;
		}
		if (state.getBlock() == Blocks.DEEPSLATE) {
			return 300;
		}
		if (state.getBlock() == Blocks.DEEPSLATE_GOLD_ORE) { 
			return 300;
		}
		if (state.getBlock() == Blocks.NETHERRACK) {
			return 160;
		}
		if (state.getBlock() == Blocks.NETHER_GOLD_ORE) {
			return 160;
		}
		if (state.getBlock() == Blocks.IRON_ORE) { 
			return 300;
		}
		if (state.getBlock() == Blocks.DEEPSLATE_IRON_ORE) {
			return 360;
		}
		if (state.getBlock() == Blocks.COAL_ORE) { 
			return 120;
		}
		if (state.getBlock() == Blocks.DEEPSLATE_COAL_ORE) {
			return 300 - 120;
		}
		if (state.getBlock() == Blocks.LAPIS_ORE) {
			return 300;
		}
		if (state.getBlock() == Blocks.LAPIS_BLOCK) {
			return 300;
		}
		if (state.getBlock() == Blocks.DEEPSLATE_LAPIS_ORE) {
			return 360;
		}
		if (state.getBlock() == Blocks.DISPENSER) {
			return 350;
		}
		if (state.getBlock() == Blocks.DROPPER) {
			return 350;
		}
		if (state.getBlock() == Blocks.CHISELED_SANDSTONE ||
				state.getBlock() == Blocks.SANDSTONE ||
				state.getBlock() == Blocks.SMOOTH_SANDSTONE ||
				state.getBlock() == Blocks.CHISELED_RED_SANDSTONE ||
				state.getBlock() == Blocks.RED_SANDSTONE ||
				state.getBlock() == Blocks.SMOOTH_RED_SANDSTONE) { 
			return 80;
		}
		if (state.getBlock() == Blocks.POWERED_RAIL ||
				state.getBlock() == Blocks.RAIL ||
				state.getBlock() == Blocks.ACTIVATOR_RAIL ||
				state.getBlock() == Blocks.DETECTOR_RAIL) {
			return 70;
		}
		if (state.getBlock() == Blocks.GOLD_BLOCK) {
			return 480;
		}
		if (state.getBlock() == Blocks.IRON_BLOCK) { 
			return 960;
		}
		if (state.getBlock() == Blocks.BRICKS) {
			return 200;
		}
		if (state.getBlock() == Blocks.MOSSY_COBBLESTONE) {
			return 200;
		}
		if (state.getBlock() == Blocks.OBSIDIAN) {
			return 240;
		}
		if (state.getBlock() == Blocks.SPAWNER) {
			return 300;
		}
		if (state.getBlock() == Blocks.DIAMOND_ORE) { 
			return 300;
		}
		if (state.getBlock() == Blocks.DEEPSLATE_DIAMOND_ORE) {
			return 360;
		}
		if (state.getBlock() == Blocks.LADDER) { 
			return 50;
		}
		if (state.getBlock() == Blocks.STONE_STAIRS) {
			return 200;
		}
		if (state.getBlock() == Blocks.LEVER) { 
			return 50;
		}
		if (state.getBlock() == Blocks.IRON_DOOR) { 
			return 400;
		}
		if (state.getBlock() == Blocks.REDSTONE_ORE) {
			return 300;
		}
		if (state.getBlock() == Blocks.DEEPSLATE_REDSTONE_ORE) {
			return 360;
		}
		if (state.getBlock() instanceof AbstractButtonBlock) {
			return 50;
		}
		if (state.getBlock() == Blocks.DIAMOND_BLOCK) { 
			return 1920;
		}
		if (state.getBlock() == Blocks.NETHERITE_BLOCK) { 
			return 3450;
		}
		if (state.getBlock() == Blocks.ANCIENT_DEBRIS) {
			return 3000;
		}
		if (state.getBlock() == Blocks.IRON_BARS) {
			return 500;
		}
		if (state.getBlock() == Blocks.STONE_BRICK_STAIRS) { 
			return 150;
		}
		if (state.getBlock() == Blocks.NETHER_BRICKS) {
			return 200;
		}
		if (state.getBlock() == Blocks.NETHER_BRICK_FENCE) {
			return 200;
		}
		if (state.getBlock() == Blocks.NETHER_BRICK_STAIRS) {
			return 200;
		}
		if (state.getBlock() instanceof EnchantingTableBlock) {
			return 240;
		}
		if (state.getBlock() == Blocks.BREWING_STAND) { 
			return 50;
		}
		if (state.getBlock() == Blocks.CAULDRON) {
			return 200;
		}
		if (state.getBlock() == Blocks.END_STONE) { 
			return 300;
		}
		if (state.getBlock() == Blocks.SANDSTONE_STAIRS ||
				state.getBlock() == Blocks.RED_SANDSTONE_STAIRS ||
				state.getBlock() == Blocks.SMOOTH_SANDSTONE_STAIRS ||
				state.getBlock() == Blocks.SMOOTH_RED_SANDSTONE_STAIRS) {
			return 80;
		}
		if (state.getBlock() == Blocks.EMERALD_ORE) {
			return 300;
		}
		if (state.getBlock() == Blocks.DEEPSLATE_EMERALD_ORE) {
			return 360;
		}
		if (state.getBlock() == Blocks.ENDER_CHEST) {
			return 2250;
		}
		if (state.getBlock() == Blocks.EMERALD_BLOCK) {
			return 960;
		}
		if (state.getBlock() == Blocks.COBBLESTONE_WALL) {
			return 150;
		}
		if (state.getBlock() == Blocks.MOSSY_COBBLESTONE_WALL) {
			return 150;
		}
		if (state.getBlock() == Blocks.REDSTONE_BLOCK) { 
			return 500;
		}
		if (state.getBlock() == Blocks.NETHER_QUARTZ_ORE) {
			return 300;
		}
		if (state.getBlock() == Blocks.HOPPER) {
			return 300;
		}
		if (state.getBlock() == Blocks.QUARTZ_BLOCK ||
				state.getBlock() == Blocks.CHISELED_QUARTZ_BLOCK ||
				state.getBlock() == Blocks.QUARTZ_PILLAR ||
				state.getBlock() == Blocks.QUARTZ_STAIRS) {
			return 80;
		}
		if (state.getBlock() == Blocks.COAL_BLOCK) {
			return 120;
		}
		if (state.getBlock() == Blocks.COPPER_ORE) {
			return 250;
		}
		if (state.getBlock() == Blocks.DEEPSLATE_COPPER_ORE) { 
			return 310;
		}
		if (state.getBlock() == BlockRegistry.SILVER_ORE) {
			return 250;
		}
		if (state.getBlock() == Blocks.COPPER_BLOCK ||
				state.getBlock() == Blocks.RAW_COPPER_BLOCK ||
				state.getBlock() == Blocks.WAXED_COPPER_BLOCK) { 
			return 480;
		}
		if (state.getBlock() instanceof SlabBlock) {
			if (state.getBlock() == Blocks.SANDSTONE_SLAB || 
					state.getBlock() == Blocks.CUT_SANDSTONE_SLAB ||
					state.getBlock() == Blocks.RED_SANDSTONE_SLAB ||
					state.getBlock() == Blocks.CUT_RED_SANDSTONE_SLAB) {
				return 100;
			}
			if (state.getBlock() == Blocks.STONE_BRICK_SLAB) {
				return 200;
			}
			if (state.getBlock() == Blocks.NETHER_BRICK_SLAB) { 
				return 200;
			}
			if (state.getBlock() == Blocks.BRICK_SLAB) {
				return 200;
			}
			if (state.getBlock() == Blocks.COBBLESTONE_SLAB) { 
				return 200;
			}
			if (state.getBlock() == Blocks.QUARTZ_SLAB) { 
				return 40;
			}
			
		}
		
		if (state.getMaterial() == Material.WOOD) {
			if (state.getBlock() instanceof PillarBlock) {
				return 120;
			}
			return 80;
		}
		if (state.getMaterial() == Material.NETHER_WOOD) {
			if (state.getBlock() instanceof PillarBlock) {
				return 360;
			}
			return 120;
		}
		if (state.getMaterial() == Material.STONE) { 
			return 240;
		}
		
		return 100;
	}
	
	public static int GetAxeDecayRateAgainstEnemy() { // general decay factor: 1.0f
		return 100;
	}
	
	public static int GetAxeDecayRateAgainstBlock(BlockState state) { // general decay factor: 1.0f
		return GetDefaultDecayRateAgainstBlock(state);
	}

	public static int GetBattleAxeDecayRateAgainstEnemy() { // general decay factor: 0.75f
		return 75;
	}
	
	public static int GetBattleAxeDecayRateAgainstBlock(BlockState state) { // general decay factor: 1.25f
		return (int)(GetAxeDecayRateAgainstBlock(state) * 1.25f);
	}
	
	public static int GetHatchetDecayRateAgainstEnemy() { // general decay factor: 1.33f
		return 133;
	}
	
	public static int GetHatchetDecayRateAgainstBlock(BlockState state) { // general decay factor: 1.33f;
		return (int)(GetAxeDecayRateAgainstBlock(state) * 1.33f);
	}
	
	public static int GetDaggerDecayRateAgainstEnemy() {
		return 50;
	}
	
	public static int GetDaggerDecayRateAgainstBlock(BlockState state) {
		return GetDefaultDecayRateAgainstBlock(state) * 2;
	}
	
	public static int GetPickaxeDecayRateAgainstEnemy() {
		return 100;
	}
	
	public static int GetPickaxeDecayRateAgainstBlock(BlockState state) {
		return GetDefaultDecayRateAgainstBlock(state);
	}
	
	public static int GetHoeDecayRateAgainstEnemy() {
		return 200;
	}
	
	public static int GetHoeDecayRateAgainstBlock(BlockState state) {
		return GetDefaultDecayRateAgainstBlock(state) * 2;
	}
	
	public static int GetKnifeDecayRateAgainstEnemy() {
		return 50;
	}
	
	public static int GetKnifeDecayRateAgainstBlock(BlockState state) {
		return GetDefaultDecayRateAgainstBlock(state);
	}
	
	public static int GetMattockDecayRateAgainstEnemy() {
		return 100;
	}
	
	public static int GetMattockDecayRateAgainstBlock(BlockState state) {
		return (int)(GetDefaultDecayRateAgainstBlock(state) * 0.4f);
	}
	
	public static int GetScytheDecayRateAgainstEnemy() {
		return 400;
	}
	
	public static int GetScytheDecayRateAgainstBlock(BlockState state) {
		return (int)(GetDefaultDecayRateAgainstBlock(state) * 2);
	}
	
	public static int GetSpearDecayRateAgainstEnemy() {
		return 250;
	}
	
	public static int GetSpearDecayRateAgainstBlock(BlockState state) {
		return (int)(GetDefaultDecayRateAgainstBlock(state) * 2);
	}
	
	public static int GetShearsDecayRateAgainstEnemy() {
		return 200;
	}
	
	public static int GetShearsDecayRateAgainstBlock(BlockState state) {
		return (int)(GetDefaultDecayRateAgainstBlock(state));
	}
	
	public static int GetShovelDecayRateAgainstEnemy() {
		return 100;
	}
	
	public static int GetShovelDecayRateAgainstBlock(BlockState state) {
		return (int)(GetDefaultDecayRateAgainstBlock(state) * 0.5f);
	}
	
	public static int GetSwordDecayRateAgainstEnemy() {
		return 50;
	}
	
	public static int GetSwordDecayRateAgainstBlock(BlockState state) {
		return (int)(GetDefaultDecayRateAgainstBlock(state) * 2);
	}
	
	public static int GetWarhammerDecayRateAgainstEnemy() {
		return 66;
	}
	
	public static int GetWarhammerDecayRateAgainstBlock(BlockState state) {
		return (int)(GetDefaultDecayRateAgainstBlock(state) * 0.67f);
	}
}
