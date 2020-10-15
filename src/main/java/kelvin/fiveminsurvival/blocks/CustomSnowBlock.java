package kelvin.fiveminsurvival.blocks;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.block.SnowBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.server.ServerWorld;

public class CustomSnowBlock extends SnowBlock {

	public CustomSnowBlock(Properties properties) {
		super(properties);
	}
	
	public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
		super.tick(state, worldIn, pos, rand);
		
		if (worldIn.getBiome(pos).getCategory() != Category.ICY) {
	         spawnDrops(state, worldIn, pos);
	         worldIn.removeBlock(pos, false);
	      }
	}

}
