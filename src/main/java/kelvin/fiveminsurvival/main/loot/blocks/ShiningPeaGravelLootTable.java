package kelvin.fiveminsurvival.main.loot.blocks;

import kelvin.fiveminsurvival.init.ItemRegistry;
import kelvin.fiveminsurvival.main.loot.LootDrop;
import kelvin.fiveminsurvival.main.loot.LootTable;
import net.minecraft.item.Items;

public class ShiningPeaGravelLootTable extends LootTable {

	public ShiningPeaGravelLootTable(boolean overridesVanillaLoot) {
		super(overridesVanillaLoot);
		
		this.AddLoot(new LootDrop(100, ItemRegistry.PEA_GRAVEL.get(), 1, 1, true));
		this.AddLoot(new LootDrop(100, ItemRegistry.FLINT_SHARD.get(), 1, 1, false));
		this.AddLoot(new LootDrop(50, Items.FLINT, 1, 2, false));
		this.AddLoot(new LootDrop(15, ItemRegistry.COPPER_NUGGET.get(), 1, 1, false));
		this.AddLoot(new LootDrop(15, ItemRegistry.SILVER_NUGGET.get(), 1, 1, false));
		this.AddLoot(new LootDrop(3, Items.PRISMARINE, 1, 1, false));
		this.AddLoot(new LootDrop(3, Items.PRISMARINE_CRYSTALS, 1, 1, false));
		this.AddLoot(new LootDrop(10, Items.IRON_NUGGET, 1, 1, false));
		this.AddLoot(new LootDrop(7, Items.GOLD_NUGGET, 1, 1, false));
	}

}
