package kelvin.mite.mixin.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(SweetBerryBushBlock.class)
public class SweetBerryBushBlockMixin {
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int i = (Integer)state.get(SweetBerryBushBlock.AGE);
        if (i < 3 && random.nextInt(50) == 0 && world.getBaseLightLevel(pos.up(), 0) >= 9) {
            world.setBlockState(pos, (BlockState)state.with(SweetBerryBushBlock.AGE, i + 1), 2);
        }

    }
}
