package kelvin.fiveminsurvival.survival.world;

import java.util.Random;

import com.mojang.datafixers.Dynamic;

import kelvin.fiveminsurvival.init.BlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class FlaxFeature extends Feature<NoFeatureConfig> {
	  

	   public FlaxFeature(java.util.function.Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn) {
		super(configFactoryIn);
	}

	public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config) {
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

