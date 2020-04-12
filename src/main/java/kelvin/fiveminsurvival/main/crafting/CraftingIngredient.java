package kelvin.fiveminsurvival.main.crafting;

public class CraftingIngredient {
	public static final int NO_WORKBENCH = 0, FLINT_CRAFTING_TABLE = 1, COPPER_CRAFTING_TABLE = 2, IRON_CRAFTING_TABLE = 3, MITHRIL_CRAFTING_TABLE = 4, ADAMANTIUM_CRAFTING_TABLE = 5;
	public int workbench;
	public int craftingTime;
	
	public CraftingIngredient(int workbench, int craftingTime) {
		this.workbench = workbench;
		this.craftingTime = craftingTime;
	}
}
