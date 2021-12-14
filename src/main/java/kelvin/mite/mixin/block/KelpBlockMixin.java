package kelvin.mite.mixin.block;

import net.minecraft.block.AbstractPlantStemBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.KelpBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(KelpBlock.class)
public abstract class KelpBlockMixin extends AbstractPlantStemBlock {
    protected KelpBlockMixin(Settings settings, Direction growthDirection, VoxelShape outlineShape, boolean tickWater, double growthChance) {
        super(settings, growthDirection, outlineShape, tickWater, growthChance);
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
       if (random.nextInt(25) == 0) {
            super.randomTick(state, world, pos, random);
       }
    }
}
