//package kelvin.fiveminsurvival.game.world.features;
//
//import java.util.Random;
//import java.util.function.Function;
//
//import com.mojang.datafixers.Dynamic;
//
//import kelvin.fiveminsurvival.game.world.Seasons;
//import kelvin.fiveminsurvival.game.world.WorldStateHolder;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.IWorld;
//import net.minecraft.world.biome.Biome;
//import net.minecraft.world.biome.BiomeManager;
//import net.minecraft.world.gen.ChunkGenerator;
//import net.minecraft.world.gen.GenerationSettings;
//import net.minecraft.world.gen.feature.NoFeatureConfig;
//import net.minecraft.world.gen.feature.structure.DesertPyramidStructure;
//import net.minecraft.world.gen.feature.structure.JunglePyramidStructure;
//import net.minecraft.world.gen.feature.structure.OceanMonumentStructure;
//import net.minecraft.world.gen.feature.structure.SwampHutStructure;
//import net.minecraft.world.gen.feature.structure.VillageConfig;
//import net.minecraft.world.gen.feature.structure.VillageStructure;
//
//public class CustomSwampHutStructure extends SwampHutStructure {
//
//	
//	public CustomSwampHutStructure(Function<Dynamic<?>, ? extends NoFeatureConfig> p_i49888_1_) {
//		super(p_i49888_1_);
//	}
//	@Override
//	public boolean canBeGenerated(BiomeManager biomeManagerIn, ChunkGenerator<?> generatorIn, Random randIn, int chunkX, int chunkZ, Biome biomeIn) {
//		if (Math.abs(chunkX) < 1000 / 16 || Math.abs(chunkZ) < 1000 / 16) {
//			return false;
//		}
//		return super.canBeGenerated(biomeManagerIn, generatorIn, randIn, chunkX, chunkZ, biomeIn);
//	}
//
//}
