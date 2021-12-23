package kelvin.mite.mixin.block;

import net.minecraft.block.BambooBlock;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.apache.logging.log4j.core.jmx.Server;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(BambooBlock.class)
public class BambooBlockMixin {

    @Shadow
    protected void updateLeaves(BlockState state, World world, BlockPos pos, Random random, int height) {

    }

    @Shadow
    protected int countBambooBelow(BlockView world, BlockPos pos) {
        return 0;
    }

    @Inject(at=@At("HEAD"), method="randomTick", cancellable = true)
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo info) {
        if (random.nextInt((int)(5 * (2.0 - world.getBiome(pos).getTemperature())) + 5) != 0) {
            info.cancel();
        }
    }

}
