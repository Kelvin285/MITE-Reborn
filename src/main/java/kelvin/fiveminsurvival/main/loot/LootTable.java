package kelvin.fiveminsurvival.main.loot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;

public class LootTable {
	public List<LootDrop> table;
	
	public boolean overridesVanillaLoot = true;
	
	public LootTable() {
		table = new ArrayList<LootDrop>();
	}
	
	public LootTable(boolean overridesVanillaLoot) {
		this.overridesVanillaLoot = overridesVanillaLoot;
		table = new ArrayList<LootDrop>();
	}
	
	public LootTable AddLoot(LootDrop drop) {
		table.add(drop);
		table.sort((a, b) -> {return a.compareTo(b);});
		return this;
	}
	
	
	public ItemStack GetDrop(BlockPos pos, boolean silk_touch, Random random) {
		List<LootDrop> items = new ArrayList<LootDrop>();
		for (int i = 0; i < table.size(); i++) {
			if (table.get(i).silk_touch == silk_touch) {
				items.add(table.get(i));
			}
		}
		
		Item item = Items.AIR;
		int count = 1;
		
		double rand = random.nextDouble() * 100;
		ArrayList<LootDrop> drops = new ArrayList<LootDrop>();
		
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).weight >= rand) {
				drops.add(items.get(i));
			}
		}
		
		if (drops.size() > 0) {
			LootDrop drop = drops.get(random.nextInt(drops.size()));
			
			item = drop.item;
			if (drop.max - drop.min > 0) {
				count = random.nextInt(drop.max - drop.min) + drop.min;
			} else {
				count = drop.min;
			}
		}
		
		
		ItemStack stack = new ItemStack(item, count);

		return stack;
	}
	
}
