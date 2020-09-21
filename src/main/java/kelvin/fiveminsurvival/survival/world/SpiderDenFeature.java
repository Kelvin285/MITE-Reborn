package kelvin.fiveminsurvival.survival.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import kelvin.fiveminsurvival.init.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SkullBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.storage.loot.LootTables;

public class SpiderDenFeature extends Feature<NoFeatureConfig> {
   public SpiderDenFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> p_i49869_1_) {
      super(p_i49869_1_);
   }

	@Override
	public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config) {
		pos = new BlockPos(pos.getX(), 0, pos.getZ());
		if (rand.nextInt(100) == 0)
		for(int i = 0; i < 128; ++i) {
			BlockPos blockpos = pos.add(rand.nextInt(8) - rand.nextInt(8), i, rand.nextInt(8) - rand.nextInt(8));
			Block surface = worldIn.getBlockState(blockpos).getBlock();
			if (surface == Blocks.GRASS_BLOCK || surface == Blocks.PODZOL || surface == Blocks.SAND) {
//				System.out.println ("den " + blockpos);
				
				//check to see if the spider den can be placed
				int min_radius = 4;
				
				int max_radius = min_radius + 10;
				boolean can_place = true;
				
				BlockPos.Mutable pos2 = new BlockPos.Mutable();
				
				for (int x = -max_radius; x < max_radius; x++) {
					for (int z = -max_radius; z < max_radius; z++) {
						double distance = Math.sqrt(x * x + z * z);
						if (distance <= max_radius) {
							pos2.setPos(blockpos.getX() + x, blockpos.getY(), blockpos.getZ() + z);
							if (worldIn.getBlockState(pos2).getBlock() == Blocks.AIR || worldIn.getBlockState(pos2).getBlock() == Blocks.WATER || worldIn.getBlockState(pos2).getBlock() == Blocks.LAVA) {
								can_place = false;
								break;
							}
						}
					}
				}
				
				int depth = 15 + rand.nextInt(8);
				
				
				List<BlockPos> webs = new ArrayList<>();
				double r = rand.nextDouble() * 5 + 2;
				int chests = 0;
				
				if (can_place) {
					for (double x = -max_radius; x < max_radius; x+=0.1) {
						for (double z = -max_radius; z < max_radius; z+=0.1) {
							
							for (int y = -4; y < depth; y++) {
								double distance = Math.sqrt(x * x + z * z);
								
								double opening = Math.sin((y * Math.PI) / (depth * 2.0)) * 5;
								double D = rand.nextDouble() * 2;
								pos2.setPos(blockpos.getX() + x, blockpos.getY() - y, blockpos.getZ() + z);
								if (worldIn.getBlockState(pos2).getBlock() == Blocks.CHEST) {
									if (rand.nextBoolean())
										LockableLootTileEntity.setLootTable(worldIn, rand, pos2, LootTables.CHESTS_ABANDONED_MINESHAFT);
									else
										LockableLootTileEntity.setLootTable(worldIn, rand, pos2, LootTables.CHESTS_SIMPLE_DUNGEON);
									continue;
								}
								
								if (y == depth - 2 && ((int)x == 0 || (int)x == 1) && ((int)z == 0 || (int)z == 1)) {
									
									if (chests < 3) {
										chests++;
										worldIn.setBlockState(pos2, Blocks.CHEST.getDefaultState(), 2);
										if (rand.nextBoolean())
											LockableLootTileEntity.setLootTable(worldIn, rand, pos2, LootTables.CHESTS_ABANDONED_MINESHAFT);
										else
											LockableLootTileEntity.setLootTable(worldIn, rand, pos2, LootTables.CHESTS_SIMPLE_DUNGEON);
										continue;
									}
									
									
								}
								if (worldIn.getBlockState(pos2).getBlock() == Blocks.CHEST) {
									if (rand.nextBoolean())
										LockableLootTileEntity.setLootTable(worldIn, rand, pos2, LootTables.CHESTS_ABANDONED_MINESHAFT);
									else
										LockableLootTileEntity.setLootTable(worldIn, rand, pos2, LootTables.CHESTS_SIMPLE_DUNGEON);
									continue;
								}
								if (distance <= min_radius + D + opening) {
									if (worldIn.getBlockState(pos2).getBlock() != Blocks.CHEST)
									worldIn.setBlockState(pos2, Blocks.AIR.getDefaultState(), 2);
									if (rand.nextInt(10) == 0) {
										if (y > 0)
											if (worldIn.getBlockState(pos2).getBlock() != Blocks.CHEST)
										worldIn.setBlockState(pos2, Blocks.COBWEB.getDefaultState(), 2);
									}
								}
								
								if (y >= depth - rand.nextInt(3) && distance <= min_radius + D + opening) {
									
									pos2.setPos(blockpos.getX() + x, blockpos.getY() - y, blockpos.getZ() + z);
									if (worldIn.getBlockState(pos2).getBlock() != Blocks.CHEST)
									worldIn.setBlockState(pos2, BlockRegistry.COBWEB_BLOCK.get().getDefaultState(), 2);
									pos2.setPos(blockpos.getX() + x, blockpos.getY() - y + 1, blockpos.getZ() + z);
									
								}
								
								
								
								Block block = worldIn.getBlockState(pos2).getBlock();
								double angle = Math.atan2(z, x);
								
								
								if (distance <= min_radius + 8) {
									if (block == Blocks.GRASS_BLOCK) {
										//generate surface features
										
										if (rand.nextInt(1000) <= 3) {
											pos2.setPos(blockpos.getX() + x, blockpos.getY() - y + 1, blockpos.getZ() + z);
											
											
											int type = rand.nextInt(5);
											
											if (type == 0) {
												if (worldIn.getBlockState(pos2).getBlock() != Blocks.CHEST)
												worldIn.setBlockState(pos2, BlockRegistry.COBWEB_BLOCK.get().getDefaultState(), 2);
											}
											
											if (type == 1) {
												double RAD = rand.nextDouble() * 4;
												int ii = 0;
												for (double mul = 1.0; mul > 0; mul-=0.1) {
													for (double xx = -RAD; xx < RAD; xx++) {
														for (double zz = -RAD; zz < RAD; zz++) {
															double dist = Math.sqrt(xx * xx + zz * zz);
															if (dist <= RAD * mul) {
																pos2.setPos(blockpos.getX() + x + xx, blockpos.getY() - y + ii, blockpos.getZ() + z + zz);
																if (worldIn.getBlockState(pos2).getBlock() != Blocks.CHEST)
																worldIn.setBlockState(pos2, BlockRegistry.COBWEB_BLOCK.get().getDefaultState(), 2);
																mul -= 0.01 * rand.nextDouble();
															}
														}
													}
													ii++;
												}
												if (worldIn.getBlockState(pos2).getBlock() != Blocks.CHEST) {
												pos2.setPos(blockpos.getX() + x, blockpos.getY() - y + 1, blockpos.getZ() + z);
												worldIn.setBlockState(pos2, Blocks.SPAWNER.getDefaultState(), 2);
												TileEntity tileentity = worldIn.getTileEntity(pos2);
										         if (tileentity instanceof MobSpawnerTileEntity) {
										            ((MobSpawnerTileEntity)tileentity).getSpawnerBaseLogic().setEntityType(EntityType.SPIDER);
										         }
												}
											}
											
											if (type == 2) {
												if (worldIn.getBlockState(pos2).getBlock() != Blocks.CHEST)
												worldIn.setBlockState(pos2, Blocks.COBWEB.getDefaultState(), 2);
											}
											
											if (type == 3 && rand.nextInt(10) <= 1) {
												if (worldIn.getBlockState(pos2).getBlock() != Blocks.CHEST)
												worldIn.setBlockState(pos2, Blocks.SKELETON_SKULL.getDefaultState().with(SkullBlock.ROTATION, rand.nextInt(15)), 2);
											}
											
											if (type == 4 && rand.nextInt(10) <= 1) {
												if (worldIn.getBlockState(pos2).getBlock() != Blocks.CHEST)
												worldIn.setBlockState(pos2, Blocks.BONE_BLOCK.getDefaultState(), 2);
											}
											
											pos2.setPos(blockpos.getX() + x, blockpos.getY() - y, blockpos.getZ() + z);
										}
										
									}
								}
								
								double cos = Math.cos(angle * r) * 8;
								if (distance <= min_radius + Math.abs(cos) + 2) {
									if (cos > 0) {
										if (block == Blocks.GRASS_BLOCK) {
											if (worldIn.getBlockState(pos2).getBlock() != Blocks.CHEST)
											{
											worldIn.setBlockState(pos2, Blocks.COARSE_DIRT.getDefaultState(), 2);
											if (rand.nextInt(1000) <= 50) {
												webs.add(new BlockPos(pos2.getX(), pos2.getY(), pos2.getZ()));
											}
											}
										}
									} else {
										if (worldIn.getBlockState(pos2).getMaterial().blocksMovement() &&
												distance <= min_radius + D + opening + 1) {
											if (worldIn.getBlockState(pos2).getBlock() != Blocks.CHEST)
											{
											worldIn.setBlockState(pos2, BlockRegistry.COBWEB_BLOCK.get().getDefaultState(), 2);
											if (rand.nextInt(1000) <= 3) {
												webs.add(new BlockPos(pos2.getX(), pos2.getY(), pos2.getZ()));
											}
											}
										}
									}
								}
								
								
								
							}
						}
					}
					
					if (webs.size() > 1) {
						int num_webs = webs.size() / 2;
						if (num_webs >= webs.size()) num_webs--;
						for (int j = 0; j < num_webs; j++) {
							BlockPos a = webs.get(rand.nextInt(webs.size()));
							BlockPos b = webs.get(rand.nextInt(webs.size()));
							while (b == a) {
								b = webs.get(rand.nextInt(webs.size()));
							}
							
					
							double slope_X = b.getX() - a.getX();
							double slope_Y = b.getY() - a.getY();
							double slope_Z = b.getZ() - a.getZ();
							
							double distance = Math.sqrt(slope_X * slope_X + slope_Y * slope_Y + slope_Z * slope_Z);
							slope_X /= distance;
							slope_Y /= distance;
							slope_Z /= distance;
							double websag = (rand.nextDouble() + 1.0) * 3.0;
							for (double w = 0; w < distance; w+=0.1) {
								
								double sag = Math.sin((w / distance) * (Math.PI / 2.0));
								
								pos2.setPos(a.getX() + w * slope_X, a.getY() + w * slope_Y - sag * websag, a.getZ() + w * slope_Z);
								if (worldIn.getBlockState(pos2).getBlock() == Blocks.CHEST) continue;
								if (worldIn.isAreaLoaded(pos2, 1))
								if (worldIn.getBlockState(pos2).getBlock() != Blocks.SPAWNER)
									if (worldIn.getBlockState(pos2).getBlock() != Blocks.CHEST)
								worldIn.setBlockState(pos2, BlockRegistry.COBWEB_BLOCK.get().getDefaultState(), 2);
								
								if (rand.nextInt(500) == 0) {
									if (worldIn.getBlockState(pos2).getBlock() != Blocks.CHEST) {
									worldIn.setBlockState(pos2, Blocks.SPAWNER.getDefaultState(), 2);
									TileEntity tileentity = worldIn.getTileEntity(pos2);
							         if (tileentity instanceof MobSpawnerTileEntity) {
							            ((MobSpawnerTileEntity)tileentity).getSpawnerBaseLogic().setEntityType(EntityType.SPIDER);
							         }
									}
								}
								
								if (rand.nextInt((int)(distance * 10)) <= 2) {
									int length = rand.nextInt(4);
									double y = pos2.getY();
									for (double l = 0; l < length; l+=0.01) {
										pos2.setPos(pos2.getX(), y - l, pos2.getZ());
										if (worldIn.getBlockState(pos2).getBlock() == Blocks.CHEST) continue;
										if (worldIn.isAreaLoaded(pos2, 1))
											if (worldIn.getBlockState(pos2).getBlock() != Blocks.SPAWNER)
												if (worldIn.getBlockState(pos2).getBlock() != Blocks.CHEST)
										worldIn.setBlockState(pos2, BlockRegistry.COBWEB_BLOCK.get().getDefaultState(), 2);
									}
								}
							}
						}
					}
					
				}
				
				return true;
			}
		}
		return false; //spider den could not be placed
	}
}
