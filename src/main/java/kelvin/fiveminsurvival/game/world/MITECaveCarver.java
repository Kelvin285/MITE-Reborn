package kelvin.fiveminsurvival.game.world;

import java.util.Random;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;

import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.world.gen.carver.CaveWorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;

public class MITECaveCarver extends CaveWorldCarver {
	
	public MITECaveCarver(Codec<ProbabilityConfig> p_i231917_1_, int p_i231917_2_) {
		super(p_i231917_1_, p_i231917_2_);
		this.carvableBlocks = ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE, Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.PODZOL, Blocks.GRASS_BLOCK, Blocks.NETHERRACK, Blocks.GRASS);
	    this.carvableFluids = ImmutableSet.of(Fluids.LAVA, Fluids.WATER);
	}

	protected float generateCaveRadius(Random rand) {
		 return super.func_230359_a_(rand) * 3.25F;
	}
	
	protected int generateCaveStartY(Random p_222726_1_) {
	      return p_222726_1_.nextInt(255);
	   }

}
