package kelvin.mite.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import kelvin.mite.blocks.MiteGrassBlock;
import kelvin.mite.main.resources.VoronoiNoise;
import kelvin.mite.registry.BlockRegistry;
import kelvin.mite.registry.SurfaceBuilderRegistry;
import kelvin.mite.world.MiteSurfaceConfig;
import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.Material;
import net.minecraft.block.enums.WallMountLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.gen.SimpleRandom;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

@Mixin(ChunkGenerator.class)
public class ChunkGeneratorMixin {
	
	private DoublePerlinNoiseSampler terrainNoise;
	private DoublePerlinNoiseSampler terrainNoise2;

	private Random random;
	
	
	@Inject(at = @At("HEAD"), method = "getWorldHeight", cancellable = true)
	public void getWorldHeight(CallbackInfoReturnable<Integer> info) {
		info.setReturnValue(512);
	}
	
	@Inject(at = @At("RETURN"), method = "generateFeatures", cancellable = true)
	public void generateFeatures(ChunkRegion region, StructureAccessor accessor, CallbackInfo info) {
		if (terrainNoise == null) {
			terrainNoise = DoublePerlinNoiseSampler.create(new SimpleRandom(region.getRandom().nextLong()), -8,
					new double[]{1.0D});
			terrainNoise2 = DoublePerlinNoiseSampler.create(new SimpleRandom(region.getRandom().nextLong()), -4,
					new double[]{2.0D});
			random = new Random(region.getRandom().nextLong());
			VoronoiNoise.SetSeed(region.getRandom().nextLong());
		}
		
		
		ChunkPos chunkPos = region.getCenterPos();
		BlockPos.Mutable pos = new BlockPos.Mutable();
		BlockPos.Mutable placing_pos = new BlockPos.Mutable();

		double biome_size = 1;
		
		for (int x = chunkPos.getStartX(); x <= chunkPos.getEndX(); x++) {
			for (int z = chunkPos.getStartZ(); z <= chunkPos.getEndZ(); z++) {
				double voronoi = VoronoiNoise.GetNoise(x + terrainNoise.sample(x, 0, z) * 25, z + terrainNoise2.sample(x, 0, z) * 25, biome_size);
				//double biome_noise = terrainNoise.sample(x / biome_size, 0, z / biome_size) + terrainNoise2.sample(z / biome_size, 0, x / biome_size);
				int biome_id = (int)Math.abs(voronoi * SurfaceBuilderRegistry.configs.size());
				MiteSurfaceConfig surface = SurfaceBuilderRegistry.configs.get(biome_id);
				
				
				for (int y = region.getBottomY(); y <= region.getTopY(); y++) {
					
					
					placing_pos.set(x, y, z);
					if (region.isChunkLoaded(placing_pos)) {
						
						if (surface.surface == MiteSurfaceConfig.SurfaceType.SAND_GRASS) { 
							if (region.getBlockState(placing_pos).getBlock() == Blocks.STONE) {
								region.setBlockState(placing_pos, surface.stone_config.stone.getDefaultState(), 0);
							} else if (region.getBlockState(placing_pos).getBlock() == Blocks.GRASS_BLOCK) {
								region.setBlockState(placing_pos, BlockRegistry.TrySwapWithGrass(surface.stone_config.sand).getDefaultState(), 0);
							} else if (region.getBlockState(placing_pos).getBlock() == Blocks.DIRT) {
								region.setBlockState(placing_pos, surface.stone_config.sand.getDefaultState(), 0);
							} else if (region.getBlockState(placing_pos).getBlock() == Blocks.GRAVEL) {
								region.setBlockState(placing_pos, surface.stone_config.gravel.getDefaultState(), 0);
							} else if (region.getBlockState(placing_pos).getBlock() == Blocks.SAND) {
								region.setBlockState(placing_pos, surface.stone_config.sand.getDefaultState(), 0);
							}
						} else if (surface.surface == MiteSurfaceConfig.SurfaceType.DIRT_GRASS) {
							if (region.getBlockState(placing_pos).getBlock() == Blocks.STONE) {
								region.setBlockState(placing_pos, surface.stone_config.stone.getDefaultState(), 0);
							} else if (region.getBlockState(placing_pos).getBlock() == Blocks.GRASS_BLOCK) {
								region.setBlockState(placing_pos, BlockRegistry.TrySwapWithGrass(Blocks.DIRT).getDefaultState(), 0);
							} else if (region.getBlockState(placing_pos).getBlock() == Blocks.DIRT) {
								region.setBlockState(placing_pos, Blocks.DIRT.getDefaultState(), 0);
							} else if (region.getBlockState(placing_pos).getBlock() == Blocks.GRAVEL) {
								region.setBlockState(placing_pos, surface.stone_config.gravel.getDefaultState(), 0);
							} else if (region.getBlockState(placing_pos).getBlock() == Blocks.SAND) {
								region.setBlockState(placing_pos, surface.stone_config.sand.getDefaultState(), 0);
							}
						}
						if (y <= 62) {
							if (region.getBlockState(placing_pos).getBlock() == Blocks.GRASS_BLOCK) {
								region.setBlockState(placing_pos, surface.stone_config.gravel.getDefaultState(), 0);
							} else if (region.getBlockState(placing_pos).getBlock() == Blocks.DIRT) {
								region.setBlockState(placing_pos, surface.stone_config.gravel.getDefaultState(), 0);
							}
						}
						
						GenerateTrees(region, x, y, z, pos, placing_pos, surface);
						GenerateRocks(region, x, y, z, pos, placing_pos);
						//GenerateOakTrees(region, x, y, z, pos, placing_pos);
					}
					
				}
				
			}
		}
		//info.cancel();
	}
	
	
	
	private void GenerateTrees(ChunkRegion region, int x, int y, int z, BlockPos.Mutable pos, BlockPos.Mutable placing_pos, MiteSurfaceConfig surface) {
		//ConfiguredFeatures.TREES_BIRCH.generate(region.toServerWorld(), null, random, placing_pos);
		Biome biome = region.getBiome(placing_pos);
		int oak = -1;
		int spruce = -1;
		int birch = -1;
		if (biome.getCategory() == Category.EXTREME_HILLS) {
			spruce = 300;
			oak = 700;
			birch = 1500;
		}
		else if (biome.getCategory() == Category.ICY) {
			spruce = 1000;
		}
		else if (biome.getCategory() == Category.FOREST) {
			oak = 400;
			birch = 300;
		}
		else if (biome.getCategory() == Category.PLAINS) {
			oak = 1000;
			birch = 1200;
		}
		else if (biome.getCategory() == Category.SWAMP) {
			oak = 500;
		}
		else if (biome.getCategory() == Category.JUNGLE) {
			oak = 700;
		}
		else if (biome.getCategory() == Category.TAIGA) {
			birch = 700;
			spruce = 500;
		}
		if (GenerateTree(oak, BlockRegistry.THIN_OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, true), region, x, y, z, pos, placing_pos, surface)) return;
		if (GenerateTree(birch, BlockRegistry.THIN_BIRCH_LOG.getDefaultState(), Blocks.BIRCH_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, true), region, x, y, z, pos, placing_pos, surface)) return;
		if (GenerateTree(spruce, BlockRegistry.THIN_SPRUCE_LOG.getDefaultState(), Blocks.SPRUCE_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, true), region, x, y, z, pos, placing_pos, surface)) return;

	}
	
	private boolean GenerateTree(int chance, BlockState log, BlockState leaf, ChunkRegion region, int x, int y, int z, BlockPos.Mutable pos, BlockPos.Mutable placing_pos, MiteSurfaceConfig surface) {
		if (chance == -1) return false;
		if (region.getRandom().nextInt(chance) == 0) {
			placing_pos.set(x, y, z);
			Block ground = region.getBlockState(placing_pos).getBlock();
			if (BlockRegistry.CanSwapWithGrass(ground) || ground instanceof MiteGrassBlock) {
				placing_pos.set(x, y + 1, z);
				if (region.isChunkLoaded(placing_pos)) {
					Block air = region.getBlockState(placing_pos).getBlock();
					if (air == Blocks.AIR) { 
						//suitable tree generation spot found!
						int height = region.getRandom().nextInt(3) + 1;
						for (int i = 0; i < height; i++) {
							placing_pos.set(x, y + i + 1, z);
							if (region.isChunkLoaded(placing_pos)) {
								region.setBlockState(placing_pos, log, 0);
							}
						}
						int leaf_height = region.getRandom().nextInt(2) + 2;
						for (int i = 0; i < leaf_height; i++) {
							if (i < leaf_height - 1) {
								placing_pos.set(x - 1, y + i + height, z);
								if (region.isChunkLoaded(placing_pos)) {
									region.setBlockState(placing_pos, leaf, 0);
								}
								placing_pos.set(x + 1, y + i + height, z);
								if (region.isChunkLoaded(placing_pos)) {
									region.setBlockState(placing_pos, leaf, 0);
								}
								placing_pos.set(x, y + i + height, z - 1);
								if (region.isChunkLoaded(placing_pos)) {
									region.setBlockState(placing_pos, leaf, 0);
								}
								placing_pos.set(x, y + i + height, z + 1);
								if (region.isChunkLoaded(placing_pos)) {
									region.setBlockState(placing_pos, leaf, 0);
								}
							}
							if (i > 0) {
								placing_pos.set(x, y + i + height, z);
								if (region.isChunkLoaded(placing_pos)) {
									region.setBlockState(placing_pos, leaf, 0);
								}
							}
						}
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private void GenerateRocks(ChunkRegion region, int x, int y, int z, BlockPos.Mutable pos, BlockPos.Mutable placing_pos) {
		pos.set(x, y, z);
		if (region.getBlockState(pos).getBlock() instanceof MiteGrassBlock || BlockRegistry.CanSwapWithGrass(region.getBlockState(pos).getBlock()) ||
				region.getBlockState(pos).getMaterial() == Material.STONE) {
			if (region.getRandom().nextInt(100) == 0) {
				placing_pos.set(x, y + 1, z);
				if (region.isChunkLoaded(placing_pos))
				if (region.getBlockState(pos.up()).getBlock() instanceof AirBlock) {
					region.setBlockState(placing_pos, BlockRegistry.ROCK.getDefaultState().with(AbstractButtonBlock.FACE, WallMountLocation.FLOOR), 0);
				}
			}
		}
	}
}
