package kelvin.mite.main.resources;

import net.minecraft.block.AnvilBlock;
import net.minecraft.block.FallingBlock;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FallingBlockHelper {


    public static boolean canFall(World world, BlockPos pos) {
        return FallingBlock.canFallThrough(world.getBlockState(pos)) && pos.getY() >= world.getBottomY();
    }
    public static boolean isReadyToFall(World world, BlockPos pos) {
        boolean fall = false;
        if (canFall(world, pos.down())) {
            fall = true;
        } else if (!(world.getBlockState(pos).getBlock() instanceof AnvilBlock)){
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
        return fall;
    }

    public static void tryToFall(World world, BlockPos pos) {
        if (isReadyToFall(world, pos)) {
            FallingBlockEntity fallingBlockEntity = new FallingBlockEntity(world, (double) pos.getX() + 0.5D,
                    (double) pos.getY(), (double) pos.getZ() + 0.5D, world.getBlockState(pos));
            world.removeBlock(pos, false);
            world.spawnEntity(fallingBlockEntity);
        }
    }
}
