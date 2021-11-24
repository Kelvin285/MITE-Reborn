package kelvin.mite.mixin.block;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(FallingBlock.class)
public class FallingBlockMixin {
	
	@Shadow
	public void configureFallingBlockEntity(FallingBlockEntity entity) {
		
	}
	
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (FallingBlock.canFallThrough(world.getBlockState(pos.down())) && pos.getY() >= world.getBottomY()) {
			FallingBlockEntity fallingBlockEntity = new FallingBlockEntity(world, (double) pos.getX() + 0.5D,
					(double) pos.getY(), (double) pos.getZ() + 0.5D, world.getBlockState(pos));
			this.configureFallingBlockEntity(fallingBlockEntity);
			world.spawnEntity(fallingBlockEntity);
		}
	}
	
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		if (FallingBlock.canFallThrough(world.getBlockState(pos.down())) && pos.getY() >= world.getBottomY()) {
			FallingBlockEntity fallingBlockEntity = new FallingBlockEntity(world, (double) pos.getX() + 0.5D,
					(double) pos.getY(), (double) pos.getZ() + 0.5D, world.getBlockState(pos));
			this.configureFallingBlockEntity(fallingBlockEntity);
			world.spawnEntity(fallingBlockEntity);
		}
	}
	
}
