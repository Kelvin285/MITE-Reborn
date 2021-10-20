package kelvin.mite.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import kelvin.mite.blocks.MiteGrassBlock;
import kelvin.mite.registry.BlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PlantBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

@Mixin(PlantBlock.class)
public class PlantBlockMixin {

	@Inject(at = @At("RETURN"), method = "canPlantOnTop", cancellable = true)
	protected void canPlantOnTop(BlockState floor, BlockView world, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
		boolean ret = floor.isOf(Blocks.MOSS_BLOCK) || floor.isOf(Blocks.GRASS_BLOCK) || floor.isOf(Blocks.DIRT)
				|| floor.isOf(Blocks.COARSE_DIRT) || floor.isOf(Blocks.PODZOL) || floor.isOf(Blocks.FARMLAND);
		ret |= BlockRegistry.CanSwapWithGrass(floor.getBlock());
		ret |= floor.getBlock() instanceof MiteGrassBlock;
		info.setReturnValue(ret);
	}
}
