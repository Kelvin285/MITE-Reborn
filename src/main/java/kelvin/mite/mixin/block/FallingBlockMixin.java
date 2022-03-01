package kelvin.mite.mixin.block;

import java.util.Random;

import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FallingBlock.class)
public abstract class FallingBlockMixin extends Block implements LandingBlock {

	public FallingBlockMixin(Settings settings) {
		super(settings);
	}

	@Shadow
	public void configureFallingBlockEntity(FallingBlockEntity entity) {
		MagmaBlock block;
	}

	/*
	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		world.createAndScheduleBlockTick(pos, this, 1);
		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}
	 */

	public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
		//world.createAndScheduleBlockTick(pos, this, this.getFallDelay());
	}

	public boolean canFall(World world, BlockPos pos) {
		return FallingBlock.canFallThrough(world.getBlockState(pos)) && pos.getY() >= world.getBottomY();
	}
	public boolean isReadyToFall(World world, BlockPos pos) {
		boolean fall = false;
		if (canFall(world, pos.down())) {
			fall = true;
		} else if (!(world.getBlockState(pos).getBlock() instanceof AnvilBlock)){
			if (world.getRandom().nextInt(3) == 0 && !world.isClient()) {

				if (canFall(world, pos.add(0, 1, 0))) {
					if (canFall(world, pos.add(-1, 0, 0)) && canFall(world, pos.add(-1, -1, 0))) {
						fall = true;
					} else if (canFall(world, pos.add(0, 0, -1)) && canFall(world, pos.add(0, -1, -1))) {
						fall = true;
					} else if (canFall(world, pos.add(1, 0, 0)) && canFall(world, pos.add(1, -1, 0))) {
						fall = true;
					} else if (canFall(world, pos.add(0, 0, 1)) && canFall(world, pos.add(0, -1, 1))) {
						fall = true;
					}
				}
			}

		}
		return fall;
	}

	public void tryToFall(World world, BlockPos pos) {
		if (isReadyToFall(world, pos)) {
			FallingBlockEntity fallingBlockEntity = new FallingBlockEntity(world, (double) pos.getX() + 0.5D,
					(double) pos.getY(), (double) pos.getZ() + 0.5D, world.getBlockState(pos));
			this.configureFallingBlockEntity(fallingBlockEntity);
			world.removeBlock(pos, false);
			world.spawnEntity(fallingBlockEntity);
		}
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		if (placer != null) {
			tryToFall(world, pos);
		}
	}

	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (!world.getBlockState(pos.down()).isSolidBlock(world, pos.down())) {
			tryToFall(world, pos);
		}
	}

	public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
		if (entity.getVelocity().length() > 0.0f) {
			tryToFall(world, pos);
		}
		super.onSteppedOn(world, pos, state, entity);
	}
	
}
