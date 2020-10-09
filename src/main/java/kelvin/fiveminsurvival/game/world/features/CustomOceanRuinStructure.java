package kelvin.fiveminsurvival.game.world.features;

import com.mojang.serialization.Codec;

import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.OceanRuinConfig;
import net.minecraft.world.gen.feature.structure.OceanRuinStructure;

public class CustomOceanRuinStructure extends OceanRuinStructure {

	
	public CustomOceanRuinStructure(Codec<OceanRuinConfig> p_i51348_1_) {
		super(p_i51348_1_);
	}

	@Override
	public boolean func_230363_a_(ChunkGenerator generatorIn, BiomeProvider provider, long l, SharedSeedRandom rand, int i, int j, 
			Biome biome, ChunkPos pos, OceanRuinConfig config) {
		if (Math.abs(pos.x) < 2000 / 16 || Math.abs(pos.z) < 2000 / 16) {
			return false;
		}
		return super.func_230363_a_(generatorIn, provider, l, rand, i, j, biome, pos, config);
	}

}
