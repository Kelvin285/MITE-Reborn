package kelvin.fiveminsurvival.main.loot;

import net.minecraft.item.Item;

public class LootDrop implements Comparable<LootDrop> {
	public double weight;
	public Item item;
	public int min;
	public int max;
	public boolean silk_touch;
	
	public LootDrop(double weight, Item item, int min, int max, boolean silk_touch) {
		this.weight = weight;
		this.item = item;
		this.min = min;
		this.max = max;
		this.silk_touch = silk_touch;
	}

	@Override
	public int compareTo(LootDrop drop) {
		
		return Double.compare(weight, drop.weight);
	}
}
