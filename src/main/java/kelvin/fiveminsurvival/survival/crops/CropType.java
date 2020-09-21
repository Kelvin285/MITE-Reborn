package kelvin.fiveminsurvival.survival.crops;

public class CropType {
	public long growthTime;
	public boolean handlesHeat;
	public boolean handlesCold;
	public boolean requiresNutrients;
	
	public CropType(long growthTime, boolean requiresNutrients, boolean handlesHeat, boolean handlesCold) {
		this.growthTime = growthTime;
		this.requiresNutrients = requiresNutrients;
		this.handlesHeat = handlesHeat;
		this.handlesCold = handlesCold;
	}
}
