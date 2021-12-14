package kelvin.mite.mixin.block;

import net.minecraft.block.BambooBlock;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

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


    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.random.nextInt(50) == 0) {
            if ((Integer)state.get(BambooBlock.STAGE) == 0) {
                if (random.nextInt(3) == 0 && world.isAir(pos.up()) && world.getBaseLightLevel(pos.up(), 0) >= 9) {
                    int i = this.countBambooBelow(world, pos) + 1;
                    if (i < 16) {
                        this.updateLeaves(state, world, pos, random, i);
                    }
                }

            }
        }
    }
}
