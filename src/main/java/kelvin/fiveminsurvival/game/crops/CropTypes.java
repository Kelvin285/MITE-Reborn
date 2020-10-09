package kelvin.fiveminsurvival.game.crops;

import java.util.HashMap;

import kelvin.fiveminsurvival.game.world.PlantState;
import kelvin.fiveminsurvival.game.world.Seasons;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropsBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class CropTypes {
	
	public static HashMap<Block, CropType> cropTypes = new HashMap<>();
	
	public static final long WHEAT_TIME = 3; //7 growth stages.  Takes 24 days to grow without fertilization.
	
	
	//Temperatures: Hot = 2.0, Cold = 0.0.
	
	public static void tickForCrop(World world, BlockPos pos, BlockState state, long day, PlantState p) {
		Biome biome = world.getBiome(pos);
		double temp = biome.getTemperature(pos);
		CropType type = cropTypes.get(state.getBlock());
		int season = Seasons.getSeason(day);
		if (type != null) {
			long growthTime = type.growthTime;
			if (!type.handlesCold) {
				growthTime = (long)(growthTime + (2.0 - temp));
			}
			if (!type.handlesHeat) {
				growthTime = (long)(growthTime + (temp));
			}
			if (!type.handlesCold && !type.handlesHeat) {
				growthTime = growthTime + 1;
			}
			
			if (p.fertilized && growthTime > 1) {
				world.setBlockState(pos, ((CropsBlock)state.getBlock()).withAge((int) ((day - p.dayPlanted) / (growthTime - 1))));
			} else {
				world.setBlockState(pos, ((CropsBlock)state.getBlock()).withAge((int) ((day - p.dayPlanted) / growthTime)));
			}
		}
	}
	
	public static void registerCropTypes() {
		cropTypes.put(Blocks.WHEAT, new CropType(WHEAT_TIME, true, true, true)); // handles cold better than heat
	}
}
