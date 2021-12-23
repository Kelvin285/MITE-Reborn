package kelvin.mite.blocks;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;

import kelvin.mite.registry.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.GrassBlock;
import net.minecraft.block.SnowBlock;
import net.minecraft.block.SpreadableBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.light.ChunkLightProvider;

public class MiteGrassBlock extends GrassBlock {
	
	public final Block base;
	public MiteGrassBlock(Settings settings, Block base) {
		super(settings);
		this.base = base;
	}
	
	private static boolean canSurvive(BlockState state, WorldView world, BlockPos pos) {
		BlockPos blockPos = pos.up();
		BlockState blockState = world.getBlockState(blockPos);
		if (blockState.isOf(Blocks.SNOW) && (Integer) blockState.get(SnowBlock.LAYERS) == 1) {
			return true;
		} else if (blockState.getFluidState().getLevel() == 8) {
			return false;
		} else {
			int i = ChunkLightProvider.getRealisticOpacity(world, state, pos, blockState, blockPos, Direction.UP,
					blockState.getOpacity(world, blockPos));
			return i < world.getMaxLightLevel();
		}
	}

	private static boolean canSpread(BlockState state, WorldView world, BlockPos pos) {
		BlockPos blockPos = pos.up();
		return canSurvive(state, world, pos) && !world.getFluidState(blockPos).isIn(FluidTags.WATER);
	}
	
	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (random.nextInt((int)(10 * (2.0 - world.getBiome(pos).getTemperature())) + 5) == 0) {
			if (!canSurvive(state, world, pos)) {
				world.setBlockState(pos, base.getDefaultState());
			} else {
				if (world.getLightLevel(pos.up()) >= 9) {
					BlockState blockState = this.getDefaultState();

					for (int i = 0; i < 4; ++i) {
						BlockPos blockPos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
						if (BlockRegistry.CanSwapWithGrass(world.getBlockState(blockPos).getBlock()) && canSpread(blockState, world, blockPos)) {
							world.setBlockState(blockPos, BlockRegistry.TrySwapWithGrass(world.getBlockState(blockPos).getBlock()).getDefaultState().with(SNOWY,
									world.getBlockState(blockPos.up()).isOf(Blocks.SNOW)));
						}
					}
				}

			}
		}
	}

}
