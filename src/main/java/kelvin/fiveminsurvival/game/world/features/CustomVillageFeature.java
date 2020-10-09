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
//import net.minecraft.world.gen.feature.structure.VillageConfig;
//import net.minecraft.world.gen.feature.structure.VillageStructure;
//
//public class CustomVillageFeature extends VillageStructure {
//
//	public CustomVillageFeature(Function<Dynamic<?>, ? extends VillageConfig> p_i51419_1_) {
//		super(p_i51419_1_);
//	}
//	
//	public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, VillageConfig config) {
//		if (WorldStateHolder.get(worldIn).FoundAllVanillaCrops() || Seasons.getTrueMonth(worldIn.getWorld().getDayTime() / 24000L) > 6) {
//			return super.place(worldIn, generator, rand, pos, config);
//		}
//		
//		return false;
//	}
//	
//	@Override
//	public boolean canBeGenerated(BiomeManager biomeManagerIn, ChunkGenerator<?> generatorIn, Random randIn, int chunkX, int chunkZ, Biome biomeIn) {
//		
//		if (Math.abs(chunkX) < 7000 / 16 || Math.abs(chunkZ) < 7000 / 16) {
//			return false;
//		}
//		return super.canBeGenerated(biomeManagerIn, generatorIn, randIn, chunkX, chunkZ, biomeIn);
//	}
//
//}
