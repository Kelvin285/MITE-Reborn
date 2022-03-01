package kelvin.mite.mixin.block;

import kelvin.mite.main.Mite;
import kelvin.mite.main.resources.SaveableVec3;
import kelvin.mite.registry.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SugarCaneBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;
import java.util.Random;

@Mixin(SugarCaneBlock.class)
public abstract class SugarCaneBlockMixin extends Block {

    public SugarCaneBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(at=@At("HEAD"), method="randomTick", cancellable = true)
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo info) {
        if (random.nextInt((int)(13 * (2.0 - world.getBiome(pos).getTemperature())) + 13) != 0) {
            info.cancel();
        }
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos.down());
        if (blockState.isOf(this)) {
            return true;
        } else {
            if (BlockRegistry.grass_variants.containsKey(state.getBlock()) || BlockRegistry.grass_variants.containsValue(state.getBlock())) {
                BlockPos blockPos = pos.down();
                Iterator var6 = Direction.Type.HORIZONTAL.iterator();

                while(var6.hasNext()) {
                    Direction direction = (Direction)var6.next();
                    BlockState blockState2 = world.getBlockState(blockPos.offset(direction));
                    FluidState fluidState = world.getFluidState(blockPos.offset(direction));
                    if (fluidState.isIn(FluidTags.WATER) || blockState2.isOf(Blocks.FROSTED_ICE)) {
                        return true;
                    }
                }
            }

            return false;
        }
    }
}
