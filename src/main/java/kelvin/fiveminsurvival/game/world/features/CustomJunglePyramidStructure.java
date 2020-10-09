package kelvin.fiveminsurvival.game.world.features;

import com.mojang.serialization.Codec;

import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.JunglePyramidStructure;

public class CustomJunglePyramidStructure extends JunglePyramidStructure {

	
	public CustomJunglePyramidStructure(Codec<NoFeatureConfig> p_i49888_1_) {
		super(p_i49888_1_);
	}
	
	@Override
	public boolean func_230363_a_(ChunkGenerator generatorIn, BiomeProvider provider, long l, SharedSeedRandom rand, int i, int j, 
			Biome biome, ChunkPos pos, NoFeatureConfig config) {
		if (Math.abs(pos.x) < 1000 / 16 || Math.abs(pos.z) < 1000 / 16) {
			return false;
		}
		return super.func_230363_a_(generatorIn, provider, l, rand, i, j, biome, pos, config);
	}

}
