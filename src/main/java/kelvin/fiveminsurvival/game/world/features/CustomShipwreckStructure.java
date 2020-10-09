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
//import net.minecraft.world.gen.feature.structure.OceanRuinConfig;
//import net.minecraft.world.gen.feature.structure.OceanRuinStructure;
//import net.minecraft.world.gen.feature.structure.ShipwreckConfig;
//import net.minecraft.world.gen.feature.structure.ShipwreckStructure;
//import net.minecraft.world.gen.feature.structure.VillageConfig;
//import net.minecraft.world.gen.feature.structure.VillageStructure;
//
//public class CustomShipwreckStructure extends ShipwreckStructure {
//
//	public CustomShipwreckStructure(Function<Dynamic<?>, ? extends ShipwreckConfig> p_i51440_1_) {
//		super(p_i51440_1_);
//	}
//
//	@Override
//	public boolean canBeGenerated(BiomeManager biomeManagerIn, ChunkGenerator<?> generatorIn, Random randIn, int chunkX, int chunkZ, Biome biomeIn) {
//
//		if (Math.abs(chunkX) < 2000 / 16 || Math.abs(chunkZ) < 2000 / 16) {
//			return false;
//		}
//		return super.canBeGenerated(biomeManagerIn, generatorIn, randIn, chunkX, chunkZ, biomeIn);
//	}
//
//}
