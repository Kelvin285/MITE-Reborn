package kelvin.mite.mixin.item;

import kelvin.mite.blocks.MiteFarmlandBlock;
import kelvin.mite.blocks.entity.FarmlandBlockEntity;
import kelvin.mite.main.Mite;
import kelvin.mite.main.resources.SaveableVec3;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BoneMealItem.class)
public class BoneMealItemMixin {

    @Inject(at = @At("HEAD"), method = "useOnFertilizable", cancellable = true)
    private static void useOnFertilizable(ItemStack stack, World world, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
        BlockState blockState = world.getBlockState(pos);

        boolean return_value = false;
        if (blockState.getBlock() instanceof GrassBlock) {
            Fertilizable fertilizable = (Fertilizable)blockState.getBlock();
            if (fertilizable.isFertilizable(world, pos, blockState, world.isClient)) {
                if (world instanceof ServerWorld) {
                    if (fertilizable.canGrow(world, world.random, pos, blockState)) {
                        fertilizable.grow((ServerWorld)world, world.random, pos, blockState);
                    }

                    stack.decrement(1);
                }

                return_value = true;
            }
        }
        else if (blockState.getBlock() instanceof CropBlock) {

            BlockEntity blockEntity = world.getBlockEntity(pos.down());
            if (blockEntity instanceof FarmlandBlockEntity) {
                FarmlandBlockEntity farmland = (FarmlandBlockEntity) blockEntity;
                if (farmland.phosphorus < 15) {
                    farmland.phosphorus++;
                    stack.decrement(1);

                    return_value = true;
                }
            }
        } else if (blockState.getBlock() instanceof MiteFarmlandBlock) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof FarmlandBlockEntity) {
                FarmlandBlockEntity farmland = (FarmlandBlockEntity) blockEntity;
                if (farmland.phosphorus < 15) {
                    farmland.phosphorus++;
                    stack.decrement(1);
                    return_value = true;
                }
            }
        }
        info.setReturnValue(return_value);
    }
}
