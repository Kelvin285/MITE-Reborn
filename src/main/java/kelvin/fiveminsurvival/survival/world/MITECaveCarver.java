package kelvin.fiveminsurvival.survival.world;

import java.util.Random;
import java.util.function.Function;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.Dynamic;

import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.world.gen.carver.CaveWorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;

public class MITECaveCarver extends CaveWorldCarver {

	public MITECaveCarver(Function<Dynamic<?>, ? extends ProbabilityConfig> p_i49929_1_, int p_i49929_2_) {
		super(p_i49929_1_, p_i49929_2_);
		this.carvableBlocks = ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE, Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.PODZOL, Blocks.GRASS_BLOCK, Blocks.NETHERRACK, Blocks.GRASS);
	    this.carvableFluids = ImmutableSet.of(Fluids.LAVA, Fluids.WATER);
	}
	
	protected float generateCaveRadius(Random rand) {
		 return super.generateCaveRadius(rand) * 3.25F;
	}
	
	protected int generateCaveStartY(Random p_222726_1_) {
	      return p_222726_1_.nextInt(255);
	   }

}
