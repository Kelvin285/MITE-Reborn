package kelvin.fiveminsurvival.game.world.features;

import com.mojang.serialization.Codec;

import kelvin.fiveminsurvival.game.world.Seasons;
import kelvin.fiveminsurvival.game.world.WorldStateHolder;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.feature.structure.VillageStructure;
import net.minecraft.world.gen.settings.StructureSeparationSettings;

public class CustomVillageFeature extends VillageStructure {

	
	public CustomVillageFeature(Codec<VillageConfig> p_i232001_1_) {
		super(p_i232001_1_);
	}

	@Override
	public BlockPos func_236388_a_(IWorldReader world, StructureManager manager, BlockPos pos, int i, boolean j, long k, StructureSeparationSettings settings) {
		System.out.println("eeeeehhhh");

//		if (world instanceof World) {
//			World worldIn = (World)world;
//					if (WorldStateHolder.get(worldIn).FoundAllVanillaCrops() || Seasons.getTrueMonth(worldIn.getDayTime() / 24000L) > 6 ||
//							pos.getX() > 7000 || pos.getZ() > 7000) {
//						return super.func_236388_a_(world, manager, pos, i, j, k, settings);
//					}
//		}
		
		return super.func_236388_a_(world, manager, pos, i, j, k, settings);
	}

}
