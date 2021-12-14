package kelvin.mite.mixin.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.CactusBlock;
import net.minecraft.block.SugarCaneBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(CactusBlock.class)
public class CactusBlockMixin {
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextInt(25 * 5) == 0) {
            BlockPos blockPos = pos.up();
            if (world.isAir(blockPos)) {
                int i;
                for(i = 1; world.getBlockState(pos.down(i)).isOf((CactusBlock)(Object)this); ++i) {
                }

                if (i < 3) {
                    int j = (Integer)state.get(CactusBlock.AGE);
                    if (j == 15) {
                        world.setBlockState(blockPos, ((CactusBlock)(Object)this).getDefaultState());
                        BlockState blockState = (BlockState)state.with(CactusBlock.AGE, 0);
                        world.setBlockState(pos, blockState, 4);
                        blockState.neighborUpdate(world, blockPos, ((CactusBlock)(Object)this), pos, false);
                    } else {
                        world.setBlockState(pos, (BlockState)state.with(CactusBlock.AGE, j + 1), 4);
                    }

                }
            }
        }
    }
}
