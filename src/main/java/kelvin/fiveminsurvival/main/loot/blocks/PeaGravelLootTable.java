package kelvin.fiveminsurvival.main.loot.blocks;

import kelvin.fiveminsurvival.init.ItemRegistry;
import kelvin.fiveminsurvival.main.loot.LootDrop;
import kelvin.fiveminsurvival.main.loot.LootTable;
import net.minecraft.item.Items;

public class PeaGravelLootTable extends LootTable {

	public PeaGravelLootTable(boolean overridesVanillaLoot) {
		super(overridesVanillaLoot);
		
		this.AddLoot(new LootDrop(100, Items.GRAVEL, 1, 1, true));
		this.AddLoot(new LootDrop(100, Items.GRAVEL, 1, 1, false));
		this.AddLoot(new LootDrop(50, ItemRegistry.SMOOTH_STONE.get(), 1, 1, false));
		this.AddLoot(new LootDrop(75, ItemRegistry.FLINT_SHARD.get(), 1, 1, false));
		this.AddLoot(new LootDrop(25, ItemRegistry.SMOOTH_STONE.get(), 1, 2, false));
		this.AddLoot(new LootDrop(35, Items.FLINT, 1, 2, false));
		this.AddLoot(new LootDrop(5, ItemRegistry.COPPER_NUGGET.get(), 1, 1, false));
		this.AddLoot(new LootDrop(5, ItemRegistry.SILVER_NUGGET.get(), 1, 1, false));
		this.AddLoot(new LootDrop(5, Items.PRISMARINE, 1, 1, false));
		this.AddLoot(new LootDrop(5, Items.PRISMARINE_CRYSTALS, 1, 1, false));
		this.AddLoot(new LootDrop(3, Items.IRON_NUGGET, 1, 1, false));
		this.AddLoot(new LootDrop(2, Items.GOLD_NUGGET, 1, 1, false));
	}

}
