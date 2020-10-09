package kelvin.fiveminsurvival.main.loot.blocks;

import kelvin.fiveminsurvival.main.loot.LootDrop;
import kelvin.fiveminsurvival.main.loot.LootTable;
import net.minecraft.item.Item;

public class SingleItemLootTable extends LootTable {
	
	public SingleItemLootTable(Item item) {
		this.AddLoot(new LootDrop(100, item, 1, 1, false));
		this.AddLoot(new LootDrop(100, item, 1, 1, true));
	}
}
