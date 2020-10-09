//package kelvin.fiveminsurvival.game.world.features;
//
//import com.mojang.serialization.Codec;
//
//import kelvin.fiveminsurvival.game.world.Seasons;
//import kelvin.fiveminsurvival.game.world.WorldStateHolder;
//import net.minecraft.util.SharedSeedRandom;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.ChunkPos;
//import net.minecraft.world.IWorldReader;
//import net.minecraft.world.biome.Biome;
//import net.minecraft.world.biome.provider.BiomeProvider;
//import net.minecraft.world.gen.ChunkGenerator;
//import net.minecraft.world.gen.feature.NoFeatureConfig;
//import net.minecraft.world.gen.feature.structure.PillagerOutpostStructure;
//import net.minecraft.world.gen.feature.structure.StructureManager;
//import net.minecraft.world.gen.settings.StructureSeparationSettings;
//
//public class CustomPillagerOutpostFeature extends PillagerOutpostStructure {
//
//	public CustomPillagerOutpostFeature(Codec<NoFeatureConfig> p_i51419_1_) {
//		super(p_i51419_1_);
//	}
//	
//	public BlockPos func_236388_a_(IWorldReader worldIn, StructureManager manager, BlockPos pos, int i, boolean b, long l, StructureSeparationSettings settings) {
//		
//		if (WorldStateHolder.get(worldIn).FoundAllVanillaCrops() || Seasons.getTrueMonth(worldIn.getWorld().getDayTime() / 24000L) > 6) {
//			return super.place(worldIn, generator, rand, pos, config);
//		}
//		
//		return pos;
//	}
//	
//	@Override
//	public boolean func_230363_a_(ChunkGenerator generatorIn, BiomeProvider provider, long l, SharedSeedRandom rand, int i, int j, 
//			Biome biome, ChunkPos pos, NoFeatureConfig config) {
//		
//		if (Math.abs(pos.x) < 7000 / 16 || Math.abs(pos.z) < 7000 / 16) {
//			return false;
//		}
//		return super.func_230363_a_(generatorIn, provider, l, rand, i, j, biome, pos, config);
//	}
//
//}
