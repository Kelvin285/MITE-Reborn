package kelvin.mite.mixin.block;

import kelvin.mite.main.Mite;
import kelvin.mite.main.resources.SaveableVec3;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SugarCaneBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(SugarCaneBlock.class)
public abstract class SugarCaneBlockMixin extends Block {

    public SugarCaneBlockMixin(Settings settings) {
        super(settings);
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        SaveableVec3 sp = new SaveableVec3(pos.getX(), pos.getY(), pos.getZ());
        boolean instant = false;
        int speed = 30;
        float temperature = world.getBiome(pos).getTemperature();
        speed *= 1.0f - temperature + 1;

        if (world.random.nextInt((speed - 5) * 15) == 0 || instant == true) {
            if (world.isAir(pos.up())) {
                int i;
                for(i = 1; world.getBlockState(pos.down(i)).isOf((SugarCaneBlock)(Object)this); ++i) {
                }

                if (i < 3) {
                    int j = (Integer)state.get(SugarCaneBlock.AGE);
                    if (j == 15) {
                        world.setBlockState(pos.up(), ((SugarCaneBlock)(Object)this).getDefaultState());
                        world.setBlockState(pos, (BlockState)state.with(SugarCaneBlock.AGE, 0), 4);

                    } else {
                        world.setBlockState(pos, (BlockState)state.with(SugarCaneBlock.AGE, j + 1), 4);
                    }
                }
            }
        }
    }
}
