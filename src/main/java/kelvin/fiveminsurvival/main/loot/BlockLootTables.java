package kelvin.fiveminsurvival.main.loot;

import java.util.HashMap;

import kelvin.fiveminsurvival.init.BlockRegistry;
import kelvin.fiveminsurvival.init.ItemRegistry;
import kelvin.fiveminsurvival.main.loot.blocks.GravelLootTable;
import kelvin.fiveminsurvival.main.loot.blocks.PeaGravelLootTable;
import kelvin.fiveminsurvival.main.loot.blocks.ShiningGravelLootTable;
import kelvin.fiveminsurvival.main.loot.blocks.ShiningPeaGravelLootTable;
import kelvin.fiveminsurvival.main.loot.blocks.SingleItemLootTable;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public class BlockLootTables {
	public static HashMap<Block, LootTable> LOOT_TABLES = new HashMap<Block, LootTable>();
	
	public static void RegisterLootTables() {
		LOOT_TABLES.put(Blocks.GRAVEL, new GravelLootTable(true));
		LOOT_TABLES.put(BlockRegistry.PEA_GRAVEL.get(), new PeaGravelLootTable(true));
		LOOT_TABLES.put(BlockRegistry.SHINING_GRAVEL.get(), new ShiningGravelLootTable(true));
		LOOT_TABLES.put(BlockRegistry.SHINING_PEA_GRAVEL.get(), new ShiningPeaGravelLootTable(true));
		LOOT_TABLES.put(BlockRegistry.COPPER_ORE.get(), new SingleItemLootTable(ItemRegistry.COPPER_ORE.get()));
		LOOT_TABLES.put(BlockRegistry.SILVER_ORE.get(), new SingleItemLootTable(ItemRegistry.SILVER_ORE.get()));
	}
}
