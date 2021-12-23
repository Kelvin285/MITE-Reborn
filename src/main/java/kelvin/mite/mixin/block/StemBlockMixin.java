package kelvin.mite.mixin.block;

import kelvin.mite.blocks.MiteGrassBlock;
import kelvin.mite.registry.BlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.StemBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(StemBlock.class)
public class StemBlockMixin {
    @Inject(at = @At("RETURN"), method = "canPlantOnTop", cancellable = true)
    protected void canPlantOnTop(BlockState floor, BlockView world, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
        boolean ret = floor.isOf(Blocks.MOSS_BLOCK) || floor.isOf(Blocks.GRASS_BLOCK) || floor.isOf(Blocks.DIRT)
                || floor.isOf(Blocks.COARSE_DIRT) || floor.isOf(Blocks.PODZOL) || floor.isOf(Blocks.FARMLAND);
        ret |= BlockRegistry.CanSwapWithGrass(floor.getBlock());
        ret |= floor.getBlock() instanceof MiteGrassBlock;
        info.setReturnValue(ret);
    }

    @Inject(at=@At("HEAD"), method="randomTick")
    public void randomTick(BlockState state, BlockPos pos, ServerWorld world, Random random, CallbackInfo info) {
        if (random.nextInt((int)(10 * (2.0 - world.getBiome(pos).getTemperature())) + 10) != 0) {
            info.cancel();
        }
    }
}
