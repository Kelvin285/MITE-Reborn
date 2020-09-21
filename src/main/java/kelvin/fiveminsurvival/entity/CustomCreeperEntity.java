package kelvin.fiveminsurvival.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class CustomCreeperEntity extends CreeperEntity {

	public CustomCreeperEntity(EntityType<? extends CreeperEntity> type, World worldIn) {
		super(type, worldIn);
	}
	

	@Override
	public boolean canExplosionDestroyBlock(Explosion explosionIn, IBlockReader worldIn, BlockPos pos, BlockState blockStateIn, float f) {
		return blockStateIn.getMaterial() == Material.BAMBOO || 
				blockStateIn.getMaterial() == Material.WOOL || 
						blockStateIn.getMaterial() == Material.CARPET ||
								blockStateIn.getMaterial() == Material.CAKE ||
										blockStateIn.getMaterial() == Material.CACTUS || 
												blockStateIn.getMaterial() == Material.GLASS ||
														blockStateIn.getMaterial() == Material.GOURD ||
																blockStateIn.getMaterial() == Material.PLANTS ||
																		blockStateIn.getMaterial() == Material.REDSTONE_LIGHT || 
																				blockStateIn.getMaterial() == Material.TNT ||
																						blockStateIn.getMaterial() == Material.WEB ||
																								blockStateIn.getMaterial() == Material.SPONGE ||
																										blockStateIn.getMaterial() == Material.SHULKER ||
																												blockStateIn.getMaterial() == Material.SEA_GRASS ||
																														blockStateIn.getMaterial() == Material.OCEAN_PLANT;
	}
}
