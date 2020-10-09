package kelvin.fiveminsurvival.game.world;

import java.util.Random;

import com.mojang.serialization.Codec;

import kelvin.fiveminsurvival.init.BlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.StructureManager;

public class FlaxFeature extends Feature<NoFeatureConfig> {
	  

	public FlaxFeature(Codec<NoFeatureConfig> p_i231953_1_) {
		super(p_i231953_1_);
	}

	@Override
	public boolean func_230362_a_(ISeedReader worldIn, StructureManager manager, ChunkGenerator generator,
			Random rand, BlockPos pos, NoFeatureConfig config) {
		for(BlockState blockstate = worldIn.getBlockState(pos); (blockstate.isAir(worldIn, pos) || blockstate.isIn(BlockTags.LEAVES)) && pos.getY() > 0; blockstate = worldIn.getBlockState(pos)) {
	         pos = pos.down();
	      }
	      
	      int i = 0;

	      for(int j = 0; j < 128; ++j) {
	         BlockPos blockpos = pos.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));
	         if (rand.nextInt(500) == 0)
	         if (worldIn.isAirBlock(blockpos) && BlockRegistry.FLAX.get().getDefaultState().isValidPosition(worldIn, blockpos)) {
	            worldIn.setBlockState(blockpos, BlockRegistry.FLAX.get().getDefaultState(), 2);
	            ++i;
	         }
	      }

	      return i > 0;
	}
	}

